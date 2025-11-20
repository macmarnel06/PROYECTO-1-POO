import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaEncargadoAula extends JFrame {

    public VentanaEncargadoAula(controladorUbicacion cu, controladorEvento ce, controladorClub cc) {
        setTitle("Encargado de Aula");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton botonUbicacion = new JButton("Gestionar Ubicación");
        JButton botonEvento = new JButton("Gestionar Evento");
        JButton botonClub = new JButton("Gestionar Club");
        JButton botonSalir = new JButton("Salir");

        botonUbicacion.addActionListener((ActionEvent e) -> new VentanaUbicacion(cu).setVisible(true));
        botonEvento.addActionListener((ActionEvent e) -> new VentanaEvento(ce).setVisible(true));
        botonClub.addActionListener((ActionEvent e) -> new VentanaClub(cc).setVisible(true));
        botonSalir.addActionListener((ActionEvent e) -> dispose());

        add(botonUbicacion);
        add(botonEvento);
        add(botonClub);
        add(botonSalir);
    }
}