package vista;

import controlador.controladorClub;
import controlador.controladorEvento;
import controlador.controladorUbicacion;

import javax.swing.*;
import java.awt.*;

public class VentanaEncargadoAula extends JFrame {
    public VentanaEncargadoAula(controladorUbicacion cu, controladorEvento ce, controladorClub cc) {
        setTitle("Panel Encargado de Aula");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnUbicaciones = new JButton("Gestionar Ubicaciones");
        JButton btnEventos = new JButton("Gestionar Eventos");
        JButton btnClubs = new JButton("Gestionar Clubs");
        JButton btnSalir = new JButton("Salir");

        btnUbicaciones.addActionListener(e -> new VentanaUbicacion(cu).setVisible(true));
        btnEventos.addActionListener(e -> new VentanaEvento(ce).setVisible(true));
        btnClubs.addActionListener(e -> new VentanaClub(cc).setVisible(true));
        btnSalir.addActionListener(e -> dispose());

        panel.add(btnUbicaciones);
        panel.add(btnEventos);
        panel.add(btnClubs);
        panel.add(btnSalir);

        add(panel);
    }
}