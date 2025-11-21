import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

public class UsuariosPanel extends JPanel {

    private final ControladorVista controlador;
    private final DefaultListModel<String> modelo = new DefaultListModel<>();
    private final JList<String> lista = new JList<>(modelo);

    public UsuariosPanel(ControladorVista controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel panelCrear = new JPanel(new GridBagLayout());
        panelCrear.setBorder(new TitledBorder(new EtchedBorder(), "Gestión de Usuarios"));
        GridBagConstraints c = gbc();

        JTextField tfId = new JTextField();
        JTextField tfNombre = new JTextField();
        JTextField tfCorreo = new JTextField();
        JButton bCrear = new JButton("Crear usuario");

        addRow(panelCrear, c, new JLabel("ID usuario:"), tfId);
        addRow(panelCrear, c, new JLabel("Nombre:"), tfNombre);
        addRow(panelCrear, c, new JLabel("Correo institucional:"), tfCorreo);
        addRow(panelCrear, c, new JLabel(), bCrear);

        JPanel panelAcciones = new JPanel(new BorderLayout(8, 8));
        panelAcciones.setBorder(new TitledBorder("Buscar / Acciones"));

        JPanel filaBuscar = new JPanel(new BorderLayout(8, 8));
        JTextField tfBuscar = new JTextField();
        JButton bBuscar = new JButton("Buscar");
        JButton bLimpiar = new JButton("Limpiar");
        JPanel botonesBuscar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botonesBuscar.add(bBuscar); botonesBuscar.add(bLimpiar);
        filaBuscar.add(new JLabel("Buscar:"), BorderLayout.WEST);
        filaBuscar.add(tfBuscar, BorderLayout.CENTER);
        filaBuscar.add(botonesBuscar, BorderLayout.EAST);

        JPanel filaAccion = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton bEditar = new JButton("Editar seleccionado");
        JButton bEliminar = new JButton("Eliminar seleccionado");
        filaAccion.add(bEditar); filaAccion.add(bEliminar);

        panelAcciones.add(filaBuscar, BorderLayout.NORTH);
        panelAcciones.add(filaAccion, BorderLayout.SOUTH);

        lista.setVisibleRowCount(14);
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(new TitledBorder("Usuarios"));

        bCrear.addActionListener(e -> {
            var u = controlador.crearUsuario(tfId.getText().trim(), tfNombre.getText().trim(), tfCorreo.getText().trim());
            if (u != null) { info("Usuario creado."); tfId.setText(""); tfNombre.setText(""); tfCorreo.setText(""); refrescar(); }
            else warn("No se pudo crear el usuario.");
        });

        bBuscar.addActionListener(e -> {
            String q = tfBuscar.getText().trim();
            modelo.clear();
            for (var u : controlador.buscarUsuariosPorTexto(q)) {
                modelo.addElement(u.getId_usuario() + " | " + u.getNombre());
            }
        });
        bLimpiar.addActionListener(e -> { tfBuscar.setText(""); refrescar(); });

        bEditar.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel == null) { warn("Selecciona un usuario."); return; }
            String id = controlador.extraerIdDeLinea(sel);
            String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre (opcional):");
            String nuevoCorreo = JOptionPane.showInputDialog(this, "Nuevo correo (opcional):");
            if (controlador.actualizarUsuario(id, nuevoNombre, nuevoCorreo)) { info("Usuario actualizado."); refrescar(); }
            else warn("No se pudo actualizar.");
        });

        bEliminar.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel == null) { warn("Selecciona un usuario."); return; }
            String id = controlador.extraerIdDeLinea(sel);
            int r = JOptionPane.showConfirmDialog(this, "¿Eliminar usuario " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                if (controlador.eliminarUsuario(id)) { info("Usuario eliminado."); refrescar(); }
                else warn("No se pudo eliminar.");
            }
        });

        add(panelCrear, BorderLayout.WEST);
        add(scroll, BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);

        refrescar();
    }

    private void refrescar() {
        modelo.clear();
        List<Usuario> usuarios = controlador.listarUsuarios();
        for (Usuario u : usuarios) modelo.addElement(u.getId_usuario() + " | " + u.getNombre());
    }

    private GridBagConstraints gbc() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 0; c.weightx = 0; c.gridwidth = 1;
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

