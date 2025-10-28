
import javax.swing.*;//DANIEL

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
            SistemaClubs sistema = new SistemaClubs();
            ControladorVista controlador = new ControladorVista(sistema);
            Vista vista = new Vista(controlador);
            vista.setVisible(true);
        });
    }
}
