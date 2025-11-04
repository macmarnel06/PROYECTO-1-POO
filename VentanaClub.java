import javax.swing.*;
import java.awt.*;

public class VentanaClub extends JFrame {

    public VentanaClub(controladorClub cc) {
        setTitle("Club");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea("Resumen de club:\n");
        area.setEditable(false);

        Club c = cc.crearClub("C2", "Club de Robótica", "Diseño y programación de robots");
        cc.mostrarResumen(c);
        area.append("Nombre: " + c.getNombre() + "\n");
        area.append("Descripción: " + c.getDescripcion());

        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}