import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

public class UbicacionesPanel extends JPanel {

    private final ControladorVista controlador;
    private final DefaultListModel<String> modelo = new DefaultListModel<>();
    private final JList<String> lista = new JList<>(modelo);

    public UbicacionesPanel(ControladorVista controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel panelCrear = new JPanel(new GridBagLayout());
        panelCrear.setBorder(new TitledBorder(new EtchedBorder(), "Gestión de Ubicaciones"));
        GridBagConstraints c = gbc();

        JTextField tfId = new JTextField();
        JTextField tfEdificio = new JTextField();
        JTextField tfSala = new JTextField();
        JTextField tfCap = new JTextField();
        JButton bCrear = new JButton("Crear ubicación");

        addRow(panelCrear, c, new JLabel("ID (entero):"), tfId);
        addRow(panelCrear, c, new JLabel("Edificio:"), tfEdificio);
        addRow(panelCrear, c, new JLabel("Nombre de sala:"), tfSala);
        addRow(panelCrear, c, new JLabel("Capacidad:"), tfCap);
        addRow(panelCrear, c, new JLabel(), bCrear);

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(new TitledBorder("Ubicaciones"));

        JButton bDetalle = new JButton("Ver detalle");
        JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        sur.add(bDetalle);

        bCrear.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                int cap = Integer.parseInt(tfCap.getText().trim());
                if (controlador.crearUbicacion(id, tfEdificio.getText().trim(), tfSala.getText().trim(), cap) != null) {
                    info("Ubicación creada.");
                    tfId.setText(""); tfEdificio.setText(""); tfSala.setText(""); tfCap.setText("");
                    refrescar();
                } else warn("No se pudo crear la ubicación.");
            } catch (NumberFormatException ex) {
                warn("ID y Capacidad deben ser enteros.");
            }
        });

        bDetalle.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel == null) { warn("Selecciona una ubicación."); return; }
            String idStr = controlador.extraerIdDeLinea(sel);
            try {
                int id = Integer.parseInt(idStr);
                Ubicacion u = controlador.buscarUbicacionPorId(id);
                if (u == null) { warn("No se encontró la ubicación."); return; }
                JOptionPane.showMessageDialog(this, u.resumenUbicacion(), "Detalle de ubicación", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                warn("No pude leer el ID.");
            }
        });

        add(panelCrear, BorderLayout.WEST);
        add(scroll, BorderLayout.CENTER);
        add(sur, BorderLayout.SOUTH);

        refrescar();
    }

    private void refrescar() {
        modelo.clear();
        List<Ubicacion> ubicaciones = controlador.listarUbicaciones();
        for (Ubicacion u : ubicaciones) {
            modelo.addElement(u.getId() + " | " + u.getNombreSala() + " (cap " + u.getCapacidadMaxima() + ")");
        }
    }

    private GridBagConstraints gbc(){ GridBagConstraints c=new GridBagConstraints(); c.insets=new Insets(8,8,8,8); c.fill=GridBagConstraints.HORIZONTAL; c.gridx=0; c.gridy=0; return c; }
    private void addRow(JPanel p, GridBagConstraints c, JComponent left, JComponent right){ c.gridx=0; p.add(left,c); c.gridx=1; c.weightx=1; p.add(right,c); c.gridy++; }
    private void info(String m){ JOptionPane.showMessageDialog(this,m,"Info",JOptionPane.INFORMATION_MESSAGE); }
    private void warn(String m){ JOptionPane.showMessageDialog(this,m,"Atención",JOptionPane.WARNING_MESSAGE); }
}

