import javax.swing.*;
import java.awt.*;

public class VentanaUbicacion extends JFrame {

    public VentanaUbicacion(controladorUbicacion cu) {
        setTitle("Ubicación");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea("Resumen de ubicación:\n");
        area.setEditable(false);

        Ubicacion u = cu.crearUbicacion("Edificio A", "Sala 101", 30);
        cu.mostrarResumen(u);
        area.append(u.resumenUbicacion());

        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}