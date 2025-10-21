
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class Vista extends JFrame {

    private final ControladorVista controlador;

    private final DefaultListModel<String> modeloUsuarios = new DefaultListModel<>();
    private final DefaultListModel<String> modeloClubs = new DefaultListModel<>();
    private final DefaultListModel<String> modeloUbicaciones = new DefaultListModel<>();
    private final DefaultListModel<String> modeloEventos = new DefaultListModel<>();

    public Vista(ControladorVista controlador) {
        super("Sistema de Gestión de Clubes");
        this.controlador = controlador;
        configurarVentana();
        setContentPane(crearContenido());
        refrescarListas(); 
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 640);
        setLocationRelativeTo(null);
    }

    private JComponent crearContenido() {
        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Usuarios", crearPanelUsuarios());
        tabs.addTab("Clubs", crearPanelClubs());
        tabs.addTab("Ubicaciones", crearPanelUbicaciones());
        tabs.addTab("Eventos", crearPanelEventos());
        tabs.addTab("Reportes", crearPanelReportes());

        return tabs;
    }

    private JPanel crearPanelUsuarios() {
        JPanel root = panelConPadding();
        root.setLayout(new BorderLayout(12, 12));

        JPanel form = grid(4, 2, 8, 8);
        JTextField tfId = new JTextField();
        JTextField tfNombre = new JTextField();
        JTextField tfCorreo = new JTextField();
        JButton btnCrear = new JButton("Crear usuario");

        form.add(new JLabel("ID usuario:"));
        form.add(tfId);
        form.add(new JLabel("Nombre:"));
        form.add(tfNombre);
        form.add(new JLabel("Correo institucional:"));
        form.add(tfCorreo);
        form.add(new JLabel());
        form.add(btnCrear);

        JList<String> lista = new JList<>(modeloUsuarios);
        JScrollPane scroll = new JScrollPane(lista);

        btnCrear.addActionListener(e -> {
            String id = tfId.getText().trim();
            String nom = tfNombre.getText().trim();
            String corr = tfCorreo.getText().trim();
            Usuario u = controlador.crearUsuario(id, nom, corr);
            if (u != null) {
                info("Usuario creado.");
                tfId.setText(""); tfNombre.setText(""); tfCorreo.setText("");
                refrescarListas();
            } else {
                warn("No se pudo crear el usuario.");
            }
        });

        root.add(form, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        return root;
    }

private JPanel crearPanelClubs() {
    JPanel root = panelConPadding();
    root.setLayout(new BorderLayout(12, 12));

    JPanel formCrear = grid(4, 2, 8, 8);
    JTextField tfIdClub = new JTextField();
    JTextField tfNombreClub = new JTextField();
    JTextField tfDescClub = new JTextField();
    JButton btnCrearClub = new JButton("Crear club");

    formCrear.add(new JLabel("ID club:"));
    formCrear.add(tfIdClub);
    formCrear.add(new JLabel("Nombre:"));
    formCrear.add(tfNombreClub);
    formCrear.add(new JLabel("Descripción:"));
    formCrear.add(tfDescClub);
    formCrear.add(new JLabel());
    formCrear.add(btnCrearClub);

    JPanel formMembresia = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(4, 4, 4, 4);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JTextField tfIdUsuarioM = new JTextField();
    JTextField tfIdClubM2   = new JTextField();
    JButton btnUnirse       = new JButton("Unirse");
    JButton btnSalir        = new JButton("Abandonar");

    gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; formMembresia.add(new JLabel("ID usuario:"), gbc);
    gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1; formMembresia.add(tfIdUsuarioM, gbc);

    gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; formMembresia.add(new JLabel("ID club:"), gbc);
    gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1; formMembresia.add(tfIdClubM2, gbc);

    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
    panelBotones.add(btnUnirse);
    panelBotones.add(btnSalir);
    gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.gridwidth = 2;
    formMembresia.add(panelBotones, gbc);
    gbc.gridwidth = 1;

    JList<String> lista = new JList<>(modeloClubs);
    JScrollPane scroll = new JScrollPane(lista);

    btnCrearClub.addActionListener(e -> {
        Club c = controlador.crearClub(tfIdClub.getText().trim(), tfNombreClub.getText().trim(), tfDescClub.getText().trim());
        if (c != null) {
            info("Club creado.");
            tfIdClub.setText(""); tfNombreClub.setText(""); tfDescClub.setText("");
            refrescarListas();
        } else {
            warn("No se pudo crear el club.");
        }
    });

    btnUnirse.addActionListener(e -> {
        boolean ok = controlador.unirseAClub(tfIdUsuarioM.getText().trim(), tfIdClubM2.getText().trim());
        if (ok) info("El usuario se unió al club."); else warn("No se pudo unir (verifica IDs).");
        refrescarListas();
    });

    btnSalir.addActionListener(e -> {
        boolean ok = controlador.abandonarClub(tfIdUsuarioM.getText().trim(), tfIdClubM2.getText().trim());
        if (ok) info("El usuario salió del club."); else warn("No se pudo salir (verifica IDs).");
        refrescarListas();
    });

    JPanel arriba = new JPanel(new BorderLayout(8, 8));
    arriba.add(formCrear, BorderLayout.NORTH);
    arriba.add(formMembresia, BorderLayout.CENTER);

    root.add(arriba, BorderLayout.NORTH);
    root.add(scroll, BorderLayout.CENTER);
    return root;
}


    private JPanel crearPanelUbicaciones() {
        JPanel root = panelConPadding();
        root.setLayout(new BorderLayout(12, 12));

        JPanel form = grid(5, 2, 8, 8);
        JTextField tfId = new JTextField();
        JTextField tfEdificio = new JTextField();
        JTextField tfSala = new JTextField();
        JTextField tfCap = new JTextField();
        JButton btnCrear = new JButton("Crear ubicación");

        form.add(new JLabel("ID:"));
        form.add(tfId);
        form.add(new JLabel("Edificio:"));
        form.add(tfEdificio);
        form.add(new JLabel("Nombre de sala:"));
        form.add(tfSala);
        form.add(new JLabel("Capacidad:"));
        form.add(tfCap);
        form.add(new JLabel());
        form.add(btnCrear);

        JList<String> lista = new JList<>(modeloUbicaciones);
        JScrollPane scroll = new JScrollPane(lista);

        btnCrear.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                int cap = Integer.parseInt(tfCap.getText().trim());
                Ubicacion u = controlador.crearUbicacion(id, tfEdificio.getText().trim(), tfSala.getText().trim(), cap);
                if (u != null) {
                    info("Ubicación creada.");
                    tfId.setText(""); tfEdificio.setText(""); tfSala.setText(""); tfCap.setText("");
                    refrescarListas();
                } else warn("No se pudo crear la ubicación.");
            } catch (NumberFormatException ex) {
                warn("ID y Capacidad deben ser enteros.");
            }
        });

        root.add(form, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        return root;
    }

private JPanel crearPanelEventos() {
    JPanel root = panelConPadding();
    root.setLayout(new BorderLayout(12, 12));

    JPanel formCrear = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(4, 4, 4, 4);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JTextField tfNombre = new JTextField();
    JTextField tfDesc = new JTextField();
    JTextField tfFechaHora = new JTextField(); 
    JTextField tfIdUbic = new JTextField();   
    JTextField tfIdClub = new JTextField();   
    JButton btnCrear = new JButton("Crear evento");

    int fila = 0;

    gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0;
    formCrear.add(new JLabel("Nombre:"), gbc);
    gbc.gridx = 1; gbc.gridy = fila; gbc.weightx = 1;
    formCrear.add(tfNombre, gbc);
    fila++;

    gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0;
    formCrear.add(new JLabel("Descripción:"), gbc);
    gbc.gridx = 1; gbc.gridy = fila; gbc.weightx = 1;
    formCrear.add(tfDesc, gbc);
    fila++;

    gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0;
    formCrear.add(new JLabel("Fecha/Hora:"), gbc);
    gbc.gridx = 1; gbc.gridy = fila; gbc.weightx = 1;
    formCrear.add(tfFechaHora, gbc);
    fila++;

    gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0;
    formCrear.add(new JLabel("ID Ubicación:"), gbc);
    gbc.gridx = 1; gbc.gridy = fila; gbc.weightx = 1;
    formCrear.add(tfIdUbic, gbc);
    fila++;

    gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0;
    formCrear.add(new JLabel("ID Club (opcional):"), gbc);
    gbc.gridx = 1; gbc.gridy = fila; gbc.weightx = 1;
    formCrear.add(tfIdClub, gbc);
    fila++;

    gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2; gbc.weightx = 0;
    formCrear.add(btnCrear, gbc);
    gbc.gridwidth = 1;
    fila++;

    JPanel formAsistencia = new JPanel(new GridBagLayout());
    GridBagConstraints gbcA = new GridBagConstraints();
    gbcA.insets = new Insets(4, 4, 4, 4);
    gbcA.fill = GridBagConstraints.HORIZONTAL;

    JTextField tfIdEventoA = new JTextField();
    JTextField tfIdUsuarioA = new JTextField();
    JButton btnRegistrarAsistencia = new JButton("Registrar asistencia");

    int filaA = 0;

    gbcA.gridx = 0; gbcA.gridy = filaA; gbcA.weightx = 0;
    formAsistencia.add(new JLabel("ID Evento:"), gbcA);
    gbcA.gridx = 1; gbcA.gridy = filaA; gbcA.weightx = 1;
    formAsistencia.add(tfIdEventoA, gbcA);
    filaA++;

    gbcA.gridx = 0; gbcA.gridy = filaA; gbcA.weightx = 0;
    formAsistencia.add(new JLabel("ID Usuario:"), gbcA);
    gbcA.gridx = 1; gbcA.gridy = filaA; gbcA.weightx = 1;
    formAsistencia.add(tfIdUsuarioA, gbcA);
    filaA++;

    gbcA.gridx = 0; gbcA.gridy = filaA; gbcA.gridwidth = 2; gbcA.weightx = 0;
    formAsistencia.add(btnRegistrarAsistencia, gbcA);
    gbcA.gridwidth = 1;

    JList<String> lista = new JList<>(modeloEventos);
    JScrollPane scroll = new JScrollPane(lista);

    btnCrear.addActionListener(e -> {
        String nombre = tfNombre.getText().trim();
        String desc = tfDesc.getText().trim();
        String fecha = tfFechaHora.getText().trim();
        if (fecha.isBlank()) fecha = null;

        Integer idUbic = null;
        String sIdUbi = tfIdUbic.getText().trim();
        if (!sIdUbi.isBlank()) {
            try { idUbic = Integer.parseInt(sIdUbi); }
            catch (NumberFormatException ex) { warn("ID de ubicación debe ser entero o vacío."); return; }
        }
        String idClub = tfIdClub.getText().trim();
        if (idClub.isBlank()) idClub = null;

        Evento ev = controlador.crearEvento(nombre, desc, fecha, idUbic, idClub);
        if (ev != null) {
            info("Evento creado. ID: " + ev.getId());
            tfNombre.setText(""); tfDesc.setText(""); tfFechaHora.setText(""); tfIdUbic.setText(""); tfIdClub.setText("");
            refrescarListas();
        } else {
            warn("No se pudo crear el evento.");
        }
    });

    btnRegistrarAsistencia.addActionListener(e -> {
        try {
            int idEv = Integer.parseInt(tfIdEventoA.getText().trim());
            String idUsu = tfIdUsuarioA.getText().trim();
            boolean ok = controlador.registrarAsistencia(idEv, idUsu);
            if (ok) info("Asistencia registrada.");
            else warn("No se pudo registrar (verifica IDs y cupo).");
            refrescarListas();
        } catch (NumberFormatException ex) {
            warn("ID Evento debe ser entero.");
        }
    });


    JPanel arriba = new JPanel(new BorderLayout(8, 8));
    arriba.add(formCrear, BorderLayout.NORTH);
    arriba.add(formAsistencia, BorderLayout.CENTER);

    root.add(arriba, BorderLayout.NORTH);
    root.add(scroll, BorderLayout.CENTER);
    return root;
}


    private JPanel crearPanelReportes() {
        JPanel root = panelConPadding();
        root.setLayout(new BorderLayout(12, 12));

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(area);

        JPanel botones = new JPanel(new GridLayout(1, 3, 8, 8));
        JButton bClubs = new JButton("Reporte de Clubs");
        JButton bEventos = new JButton("Reporte de Eventos");
        JButton bUbic = new JButton("Reporte de Ubicaciones");
        botones.add(bClubs);
        botones.add(bEventos);
        botones.add(bUbic);

        bClubs.addActionListener(e -> area.setText(controlador.reporteClubs()));
        bEventos.addActionListener(e -> area.setText(controlador.reporteEventos()));
        bUbic.addActionListener(e -> area.setText(controlador.reporteUbicaciones()));

        root.add(botones, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        return root;
    }

    private JPanel panelConPadding() {
        JPanel p = new JPanel();
        p.setBorder(new EmptyBorder(12, 12, 12, 12));
        return p;
    }

    private JPanel grid(int filas, int cols, int hgap, int vgap) {
        JPanel p = new JPanel(new GridLayout(filas, cols, hgap, vgap));
        p.setBorder(new EmptyBorder(4, 4, 4, 4));
        return p;
    }

    private void info(String msg) { JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE); }
    private void warn(String msg) { JOptionPane.showMessageDialog(this, msg, "Atención", JOptionPane.WARNING_MESSAGE); }

    private void refrescarListas() {
        modeloUsuarios.clear();
        List<Usuario> usuarios = controlador.listarUsuarios();
        for (Usuario u : usuarios) {
            modeloUsuarios.addElement(u.getId_usuario() + " | " + u.getNombre());
        }

        modeloClubs.clear();
        List<Club> clubs = controlador.listarClubs();
        for (Club c : clubs) {
            modeloClubs.addElement(c.getIdClub() + " | " + c.getNombre() + " (miembros: " + c.getMiembros().size() + ")");
        }

        modeloUbicaciones.clear();
        List<Ubicacion> ubicaciones = controlador.listarUbicaciones();
        for (Ubicacion u : ubicaciones) {
            modeloUbicaciones.addElement(u.getId() + " | " + u.getNombreSala() + " (cap " + u.getCapacidadMaxima() + ")");
        }

        modeloEventos.clear();
        List<Evento> eventos = controlador.listarEventos();
        for (Evento e : eventos) {
            String sala = (e.getUbicacion() == null) ? "(sala ?)" : e.getUbicacion().getSalon();
            modeloEventos.addElement(e.getId() + " | " + e.getNombre() + " @ " + sala);
        }
    }
}
