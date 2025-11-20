import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

public class EventosPanel extends JPanel {

    private final ControladorVista controlador;

    private final DefaultListModel<String> modeloEventos = new DefaultListModel<>();
    private final DefaultListModel<String> modeloAsis    = new DefaultListModel<>();
    private final JList<String> listaEventos = new JList<>(modeloEventos);
    private final JList<String> listaAsistentes = new JList<>(modeloAsis);

    public EventosPanel(ControladorVista controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel crear = new JPanel(new GridBagLayout());
        crear.setBorder(new TitledBorder(new EtchedBorder(), "Crear Evento"));
        GridBagConstraints c = gbc();

        JTextField tfNombre = new JTextField();
        JTextField tfDesc = new JTextField();
        JTextField tfFechaHora = new JTextField();
        JTextField tfIdUbi = new JTextField();
        JTextField tfIdClub = new JTextField();
        JButton bCrear = new JButton("Crear evento");

        addRow(crear, c, new JLabel("Nombre:"), tfNombre);
        addRow(crear, c, new JLabel("Descripción:"), tfDesc);
        addRow(crear, c, new JLabel("Fecha/Hora (ISO):"), tfFechaHora);
        addRow(crear, c, new JLabel("ID Ubicación:"), tfIdUbi);
        addRow(crear, c, new JLabel("ID Club (opcional):"), tfIdClub);
        addRow(crear, c, new JLabel(), bCrear);

        JScrollPane scEventos = new JScrollPane(listaEventos);
        scEventos.setBorder(new TitledBorder("Eventos"));

        JPanel asistencia = new JPanel(new GridBagLayout());
        asistencia.setBorder(new TitledBorder("Registrar Asistencia"));
        GridBagConstraints a = gbc();

        JTextField tfIdEventoA = new JTextField();
        JTextField tfIdUsuarioA = new JTextField();
        JButton bAsistir = new JButton("Registrar");
        JScrollPane scAsis = new JScrollPane(listaAsistentes);
        scAsis.setPreferredSize(new Dimension(240, 220));

        addRow(asistencia, a, new JLabel("ID Evento:"), tfIdEventoA);
        addRow(asistencia, a, new JLabel("ID Usuario:"), tfIdUsuarioA);
        addRow(asistencia, a, new JLabel(), bAsistir);
        a.gridx = 0; a.gridwidth = 2; asistencia.add(scAsis, a);

        bCrear.addActionListener(e -> {
            String nombre = tfNombre.getText().trim();
            String desc = tfDesc.getText().trim();
            String fecha = tfFechaHora.getText().trim();
            if (fecha.isBlank()) fecha = null;

            Integer idUbic = null;
            String sIdUbi = tfIdUbi.getText().trim();
            if (!sIdUbi.isBlank()) {
                try { idUbic = Integer.parseInt(sIdUbi); }
                catch (NumberFormatException ex) { warn("ID de ubicación debe ser entero o vacío."); return; }
            }
            String idClub = tfIdClub.getText().trim();
            if (idClub.isBlank()) idClub = null;

            Evento ev = controlador.crearEvento(nombre, desc, fecha, idUbic, idClub);
            if (ev != null) {
                info("Evento creado. ID: " + ev.getId());
                tfNombre.setText(""); tfDesc.setText(""); tfFechaHora.setText(""); tfIdUbi.setText(""); tfIdClub.setText("");
                refrescarEventos();
            } else warn("No se pudo crear el evento.");
        });

        bAsistir.addActionListener(e -> {
            try {
                int idEv = Integer.parseInt(tfIdEventoA.getText().trim());
                String idUsu = tfIdUsuarioA.getText().trim();
                boolean ok = controlador.registrarAsistencia(idEv, idUsu);
                if (ok) { info("Asistencia registrada."); refrescarAsistentes(idEv); }
                else warn("No se pudo registrar (IDs/cupo).");
            } catch (NumberFormatException ex) {
                warn("ID de evento debe ser entero.");
            }
        });

        listaEventos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String linea = listaEventos.getSelectedValue();
                if (linea == null) { modeloAsis.clear(); return; }
                String idStr = controlador.extraerIdDeLinea(linea);
                try {
                    int idEv = Integer.parseInt(idStr);
                    tfIdEventoA.setText(idStr);
                    refrescarAsistentes(idEv);
                } catch (NumberFormatException ignored) {
                    modeloAsis.clear();
                }
            }
        });

        add(crear, BorderLayout.WEST);
        add(scEventos, BorderLayout.CENTER);
        add(asistencia, BorderLayout.EAST);

        refrescarEventos();
    }

    private void refrescarEventos() {
        modeloEventos.clear();
        List<Evento> eventos = controlador.listarEventos();
        for (Evento e : eventos) {
            String sala = (e.getUbicacion() == null ? "(sala ?)" : e.getUbicacion().getSalon());
            modeloEventos.addElement(e.getId() + " | " + e.getNombre() + " @ " + sala);
        }
    }

    private void refrescarAsistentes(int idEvento) {
        modeloAsis.clear();
        Evento e = controlador.buscarEventoPorId(idEvento);
        if (e == null) return;
        for (Usuario u : e.getAsistencia()) {
            modeloAsis.addElement(u.getId_usuario() + " | " + u.getNombre());
        }
    }

    private GridBagConstraints gbc(){ GridBagConstraints c=new GridBagConstraints(); c.insets=new Insets(8,8,8,8); c.fill=GridBagConstraints.HORIZONTAL; c.gridx=0; c.gridy=0; return c; }
    private void addRow(JPanel p, GridBagConstraints c, JComponent left, JComponent right){ c.gridx=0; p.add(left,c); c.gridx=1; c.weightx=1; p.add(right,c); c.gridy++; }
    private void info(String m){ JOptionPane.showMessageDialog(this,m,"Info",JOptionPane.INFORMATION_MESSAGE); }
    private void warn(String m){ JOptionPane.showMessageDialog(this,m,"Atención",JOptionPane.WARNING_MESSAGE); }
}
