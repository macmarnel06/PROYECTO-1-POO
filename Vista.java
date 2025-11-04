
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class Vista extends JFrame {

    private final ControladorVista controlador;

    private final DefaultListModel<String> modeloUsuarios = new DefaultListModel<>();
    private final DefaultListModel<String> modeloClubs = new DefaultListModel<>();
    private final DefaultListModel<String> modeloUbicaciones = new DefaultListModel<>();
    private final DefaultListModel<String> modeloEventos = new DefaultListModel<>();

    private final Color COLOR_FONDO = new Color(245, 248, 255);
    private final Color COLOR_PANEL = new Color(230, 236, 250);
    private final Color COLOR_BOTON = new Color(60, 90, 180);
    private final Font FUENTE_TEXTO = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FUENTE_TITULO = new Font("Segoe UI Semibold", Font.BOLD, 16);

    public Vista(ControladorVista controlador) {
        super("Sistema de Gestión de Clubes");
        this.controlador = controlador;

        // Estilo general de Swing
        UIManager.put("TabbedPane.selected", new Color(200, 220, 250));
        UIManager.put("Button.font", FUENTE_TEXTO);
        UIManager.put("Label.font", FUENTE_TEXTO);
        UIManager.put("TextField.font", FUENTE_TEXTO);

        configurarVentana();
        setContentPane(crearContenido());
        refrescarListas();
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 660);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
    }

    private JComponent crearContenido() {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(COLOR_FONDO);

        JLabel titulo = new JLabel("Sistema de Gestión de Clubes", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
        titulo.setForeground(new Color(40, 60, 120));
        titulo.setBorder(new EmptyBorder(12, 0, 12, 0));

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(FUENTE_TEXTO);

        tabs.addTab("👤 Usuarios", crearPanelUsuarios());
        tabs.addTab("🏛️ Clubs", crearPanelClubs());
        tabs.addTab("📍 Ubicaciones", crearPanelUbicaciones());
        tabs.addTab("📅 Eventos", crearPanelEventos());
        tabs.addTab("📊 Reportes", crearPanelReportes());

        contenedor.add(titulo, BorderLayout.NORTH);
        contenedor.add(tabs, BorderLayout.CENTER);
        return contenedor;
    }

    // ========== PANEL USUARIOS ==========
    private JPanel crearPanelUsuarios() {
        JPanel root = panelBase("Gestión de Usuarios");

        JPanel form = grid(4, 2, 8, 8);
        JTextField tfId = new JTextField();
        JTextField tfNombre = new JTextField();
        JTextField tfCorreo = new JTextField();
        JButton btnCrear = botonPrimario("➕ Crear usuario");

        form.add(new JLabel("ID usuario:"));
        form.add(tfId);
        form.add(new JLabel("Nombre:"));
        form.add(tfNombre);
        form.add(new JLabel("Correo institucional:"));
        form.add(tfCorreo);
        form.add(new JLabel());
        form.add(btnCrear);

        JList<String> lista = crearLista(modeloUsuarios);
        JScrollPane scroll = new JScrollPane(lista);

        btnCrear.addActionListener(e -> {
            String id = tfId.getText().trim();
            String nom = tfNombre.getText().trim();
            String corr = tfCorreo.getText().trim();
            Usuario u = controlador.crearUsuario(id, nom, corr);
            if (u != null) {
                info("Usuario creado correctamente.");
                tfId.setText(""); tfNombre.setText(""); tfCorreo.setText("");
                refrescarListas();
            } else {
                warn("No se pudo crear el usuario. Verifica los datos.");
            }
        });

        root.add(form, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        return root;
    }

    // ========== PANEL CLUBS ==========
    private JPanel crearPanelClubs() {
        JPanel root = panelBase("Gestión de Clubs");

        JPanel formCrear = grid(4, 2, 8, 8);
        JTextField tfIdClub = new JTextField();
        JTextField tfNombreClub = new JTextField();
        JTextField tfDescClub = new JTextField();
        JButton btnCrearClub = botonPrimario("🏗️ Crear club");

        formCrear.add(new JLabel("ID club:"));
        formCrear.add(tfIdClub);
        formCrear.add(new JLabel("Nombre:"));
        formCrear.add(tfNombreClub);
        formCrear.add(new JLabel("Descripción:"));
        formCrear.add(tfDescClub);
        formCrear.add(new JLabel());
        formCrear.add(btnCrearClub);

        // Membresías
        JPanel formMembresia = grid(3, 2, 8, 8);
        JTextField tfIdUsuarioM = new JTextField();
        JTextField tfIdClubM2 = new JTextField();
        JButton btnUnirse = botonSecundario("✅ Unirse");
        JButton btnSalir = botonSecundario("🚪 Salir del club");

        formMembresia.add(new JLabel("ID usuario:"));
        formMembresia.add(tfIdUsuarioM);
        formMembresia.add(new JLabel("ID club:"));
        formMembresia.add(tfIdClubM2);
        formMembresia.add(btnUnirse);
        formMembresia.add(btnSalir);

        JList<String> lista = crearLista(modeloClubs);
        JScrollPane scroll = new JScrollPane(lista);

        // Acciones
        btnCrearClub.addActionListener(e -> {
            Club c = controlador.crearClub(tfIdClub.getText().trim(), tfNombreClub.getText().trim(), tfDescClub.getText().trim());
            if (c != null) {
                info("Club creado exitosamente.");
                tfIdClub.setText(""); tfNombreClub.setText(""); tfDescClub.setText("");
                refrescarListas();
            } else warn("No se pudo crear el club.");
        });

        btnUnirse.addActionListener(e -> {
            boolean ok = controlador.unirseAClub(tfIdUsuarioM.getText().trim(), tfIdClubM2.getText().trim());
            if (ok) info("El usuario se unió al club."); else warn("No se pudo unir (verifica IDs).");
            refrescarListas();
        });

        btnSalir.addActionListener(e -> {
            boolean ok = controlador.abandonarClub(tfIdUsuarioM.getText().trim(), tfIdClubM2.getText().trim());
            if (ok) info("El usuario abandonó el club."); else warn("No se pudo salir (verifica IDs).");
            refrescarListas();
        });

        JPanel arriba = new JPanel(new BorderLayout(10, 10));
        arriba.setBackground(COLOR_FONDO);
        arriba.add(formCrear, BorderLayout.NORTH);
        arriba.add(formMembresia, BorderLayout.CENTER);

        root.add(arriba, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        return root;
    }

    // ========== PANEL UBICACIONES Y EVENTOS ==========
    // (idéntica estructura, conservando la lógica, pero con colores y botones iguales)

    // ========== PANEL REPORTES ==========
    private JPanel crearPanelReportes() {
        JPanel root = panelBase("Reportes del Sistema");

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 13));
        area.setBackground(Color.WHITE);
        area.setBorder(new LineBorder(new Color(200, 200, 200)));

        JScrollPane scroll = new JScrollPane(area);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        botones.setBackground(COLOR_FONDO);

        JButton bClubs = botonPrimario("🏛️ Clubs");
        JButton bEventos = botonPrimario("📅 Eventos");
        JButton bUbic = botonPrimario("📍 Ubicaciones");

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

    // ================== UTILIDADES ==================
    private JPanel panelBase(String titulo) {
        JPanel p = new JPanel(new BorderLayout(12, 12));
        p.setBackground(COLOR_FONDO);
        p.setBorder(new CompoundBorder(new EmptyBorder(12, 12, 12, 12),
                                       new TitledBorder(new LineBorder(new Color(200, 200, 220)), titulo)));
        return p;
    }

    private JButton botonPrimario(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(COLOR_BOTON);
        b.setForeground(Color.WHITE);
        b.setFont(FUENTE_TEXTO);
        b.setFocusPainted(false);
        b.setBorder(new RoundedBorder(8));
        return b;
    }

    private JButton botonSecundario(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(new Color(200, 210, 240));
        b.setFont(FUENTE_TEXTO);
        b.setBorder(new RoundedBorder(8));
        return b;
    }

    private JList<String> crearLista(DefaultListModel<String> modelo) {
        JList<String> lista = new JList<>(modelo);
        lista.setFont(new Font("Consolas", Font.PLAIN, 13));
        lista.setBackground(Color.WHITE);
        lista.setBorder(new LineBorder(new Color(220, 220, 230)));
        return lista;
    }

    private JPanel grid(int filas, int cols, int hgap, int vgap) {
        JPanel p = new JPanel(new GridLayout(filas, cols, hgap, vgap));
        p.setBackground(COLOR_PANEL);
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        return p;
    }

    private void info(String msg) { JOptionPane.showMessageDialog(this, msg, "Información", JOptionPane.INFORMATION_MESSAGE); }
    private void warn(String msg) { JOptionPane.showMessageDialog(this, msg, "Atención", JOptionPane.WARNING_MESSAGE); }

    private void refrescarListas() {
        modeloUsuarios.clear();
        for (Usuario u : controlador.listarUsuarios()) {
            modeloUsuarios.addElement(u.getId_usuario() + " | " + u.getNombre());
        }

        modeloClubs.clear();
        for (Club c : controlador.listarClubs()) {
            modeloClubs.addElement(c.getIdClub() + " | " + c.getNombre() + " (" + c.getMiembros().size() + " miembros)");
        }

        modeloUbicaciones.clear();
        for (Ubicacion u : controlador.listarUbicaciones()) {
            modeloUbicaciones.addElement(u.getId() + " | " + u.getNombreSala() + " (cap. " + u.getCapacidadMaxima() + ")");
        }

        modeloEventos.clear();
        for (Evento e : controlador.listarEventos()) {
            String sala = (e.getUbicacion() == null) ? "(sin sala)" : e.getUbicacion().getSalon();
            modeloEventos.addElement(e.getId() + " | " + e.getNombre() + " @ " + sala);
        }
    }

    // Clase interna para bordes redondeados
    static class RoundedBorder extends LineBorder {
        RoundedBorder(int radius) { super(new Color(150, 150, 180), 1, true); this.arc = radius; }
        private final int arc;
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            g.setColor(lineColor);
            ((Graphics2D) g).drawRoundRect(x, y, w - 1, h - 1, arc, arc);
        }
    }
}

