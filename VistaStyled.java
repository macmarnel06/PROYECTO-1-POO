import javax.swing.*;
import java.awt.*;

public class Vista extends JFrame {

    public Vista(ControladorVista controlador) {
        setTitle("Gestión de Clubs - UVG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ignored) {}

        setSize(1024, 640);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Usuarios", new UsuariosPanel(controlador));
        tabs.addTab("Clubs", new ClubsPanel(controlador));
        tabs.addTab("Ubicaciones", new UbicacionesPanel(controlador));
        tabs.addTab("Eventos", new EventosPanel(controlador));
        tabs.addTab("Reportes", new ReportesPanel(controlador));

        add(tabs, BorderLayout.CENTER);
    }
}

