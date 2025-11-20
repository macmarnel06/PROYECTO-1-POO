import javax.swing.*;
import java.awt.*;

public class VistaStyled extends JFrame {

    public VistaStyled(ControladorVista controlador) {
        setTitle("Gestión de Clubs - UVG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Look & Feel Nimbus si está disponible
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

        // Pestañas con paneles
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Usuarios", new UsuariosStyledPanel(controlador));
        tabs.addTab("Clubs", new ClubsStyledPanel(controlador));
        tabs.addTab("Ubicaciones", new UbicacionesStyledPanel(controlador));
        tabs.addTab("Eventos", new EventosStyledPanel(controlador));
        tabs.addTab("Reportes", new ReportesStyledPanel(controlador));

        add(tabs, BorderLayout.CENTER);
    }
}

