import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class VentanaEvento extends JFrame {

    public VentanaEvento(controladorEvento ce) {
        setTitle("Evento");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea("Resumen de evento:\n");
        area.setEditable(false);

        Ubicacion u = new Ubicacion(1, "Edificio B", "Sala 202", 50);
        Club c = new Club("C1", "Club de Ciencia", "Exploración científica");
        Evento e = ce.crearEvento("Charla de Física", "Evento académico", LocalDateTime.now(), u, c);
        ce.mostrarResumen(e);
        area.append(e.resumenEvento());

        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}