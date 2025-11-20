import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ReportesPanel extends JPanel {

    private final ControladorVista controlador;

    public ReportesPanel(ControladorVista controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(12, 12, 12, 12));

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        JPanel botones = new JPanel(new GridLayout(1, 3, 8, 8));
        JButton bClubs = new JButton("Reporte de Clubs");
        JButton bEventos = new JButton("Reporte de Eventos");
        JButton bUbic = new JButton("Reporte de Ubicaciones");
        botones.add(bClubs); botones.add(bEventos); botones.add(bUbic);

        bClubs.addActionListener(e -> area.setText(controlador.reporteClubs()));
        bEventos.addActionListener(e -> area.setText(controlador.reporteEventos()));
        bUbic.addActionListener(e -> area.setText(controlador.reporteUbicaciones()));

        add(botones, BorderLayout.NORTH);
        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}
