import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

public class ClubsPanel extends JPanel {

    private final ControladorVista controlador;

    private final DefaultListModel<String> modeloClubs = new DefaultListModel<>();
    private final JList<String> listaClubs = new JList<>(modeloClubs);

    public ClubsPanel(ControladorVista controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel panelCrear = new JPanel(new GridBagLayout());
        panelCrear.setBorder(new TitledBorder(new EtchedBorder(), "Gestión de Clubs"));
        GridBagConstraints c = gbc();

        JTextField tfId = new JTextField();
        JTextField tfNombre = new JTextField();
        JTextField tfDesc = new JTextField();
        JButton bCrear = new JButton("Crear club");

        addRow(panelCrear, c, new JLabel("ID club:"), tfId);
        addRow(panelCrear, c, new JLabel("Nombre:"), tfNombre);
        addRow(panelCrear, c, new JLabel("Descripción:"), tfDesc);
        addRow(panelCrear, c, new JLabel(), bCrear);

        JScrollPane scroll = new JScrollPane(listaClubs);
        scroll.setBorder(new TitledBorder("Clubs disponibles"));

        JPanel panelMem = new JPanel(new GridBagLayout());
        panelMem.setBorder(new TitledBorder("Membresía / Acciones"));
        GridBagConstraints m = gbc();

        JTextField tfIdUsuario = new JTextField();
        JButton bUnirse = new JButton("Unirse");
        JButton bSalir = new JButton("Abandonar");
        JButton bEditar = new JButton("Editar seleccionado");
        JButton bEliminar = new JButton("Eliminar seleccionado");
        JButton bVerMiembros = new JButton("Ver miembros");

        addRow(panelMem, m, new JLabel("ID usuario:"), tfIdUsuario);
        m.gridx = 0; m.weightx = 0; panelMem.add(bUnirse, m);
        m.gridx = 1; m.weightx = 1; panelMem.add(bSalir, m);
        m.gridy++;

        addRow(panelMem, m, new JLabel(), bVerMiembros);
        addRow(panelMem, m, new JLabel(), bEditar);
        addRow(panelMem, m, new JLabel(), bEliminar);

        bCrear.addActionListener(e -> {
            Club c1 = controlador.crearClub(tfId.getText().trim(), tfNombre.getText().trim(), tfDesc.getText().trim());
            if (c1 != null) { info("Club creado."); tfId.setText(""); tfNombre.setText(""); tfDesc.setText(""); refrescar(); }
            else warn("No se pudo crear el club.");
        });

        bUnirse.addActionListener(e -> {
            String idUsuario = tfIdUsuario.getText().trim();
            String idClubSel = obtenerIdSeleccionado();
            if (controlador.unirseAClub(idUsuario, idClubSel)) info("Usuario unido."); else warn("No se pudo unir.");
            refrescar();
        });

        bSalir.addActionListener(e -> {
            String idUsuario = tfIdUsuario.getText().trim();
            String idClubSel = obtenerIdSeleccionado();
            if (controlador.abandonarClub(idUsuario, idClubSel)) info("Usuario salió del club."); else warn("No se pudo salir.");
            refrescar();
        });

        bEditar.addActionListener(e -> {
            String id = obtenerIdSeleccionado();
            if (id == null) { warn("Selecciona un club."); return; }
            String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre (opcional):");
            String nuevaDesc   = JOptionPane.showInputDialog(this, "Nueva descripción (opcional):");
            if (controlador.actualizarClub(id, nuevoNombre, nuevaDesc)) { info("Club actualizado."); refrescar(); }
            else warn("No se pudo actualizar.");
        });

        bEliminar.addActionListener(e -> {
            String id = obtenerIdSeleccionado();
            if (id == null) { warn("Selecciona un club."); return; }
            int r = JOptionPane.showConfirmDialog(this, "¿Eliminar club " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                if (controlador.eliminarClub(id)) { info("Club eliminado."); refrescar(); }
                else warn("No se pudo eliminar.");
            }
        });

        bVerMiembros.addActionListener(e -> {
            String id = obtenerIdSeleccionado();
            if (id == null) { warn("Selecciona un club."); return; }
            Club c1 = controlador.buscarClubPorId(id);
            if (c1 == null) { warn("No se encontró el club."); return; }
            StringBuilder sb = new StringBuilder("Miembros de " + c1.getNombre() + ":\n");
            for (Usuario u : c1.getMiembros()) sb.append("- ").append(u.getNombre()).append(" (").append(u.getId_usuario()).append(")\n");
            JOptionPane.showMessageDialog(this, sb.toString(), "Miembros", JOptionPane.INFORMATION_MESSAGE);
        });

        add(panelCrear, BorderLayout.WEST);
        add(scroll, BorderLayout.CENTER);
        add(panelMem, BorderLayout.EAST);

        refrescar();
    }

    private void refrescar() {
        modeloClubs.clear();
        List<Club> clubs = controlador.listarClubs();
        for (Club c : clubs) {
            modeloClubs.addElement(c.getIdClub() + " | " + c.getNombre() + " (miembros: " + c.getMiembros().size() + ")");
        }
    }

    private String obtenerIdSeleccionado() {
        String linea = listaClubs.getSelectedValue();
        if (linea == null) return null;
        return controlador.extraerIdDeLinea(linea);
    }

    private GridBagConstraints gbc() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 0; c.weightx = 0;
        return c;
    }
    private void addRow(JPanel p, GridBagConstraints c, JComponent left, JComponent right) {
        c.gridx = 0; c.weightx = 0; p.add(left, c);
        c.gridx = 1; c.weightx = 1; p.add(right, c);
        c.gridy++;
    }
    private void info(String m){ JOptionPane.showMessageDialog(this, m,"Info",JOptionPane.INFORMATION_MESSAGE); }
    private void warn(String m){ JOptionPane.showMessageDialog(this, m,"Atención",JOptionPane.WARNING_MESSAGE); }
}
