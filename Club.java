package PROYECTO1;

import java.util.*;

public class Club {
    private final String idClub;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String requisitos;
    private final List<Usuario> miembros;
    private final List<Evento> eventos;
    private String horario;
    private final List<Usuario> encargados;
    private int capacidadMaxima;

    public Club(String nombre, String descripcion, String categoria, String requisitos, String horario, int capacidadMaxima) {
        this.idClub = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.requisitos = requisitos;
        this.horario = horario;
        this.capacidadMaxima = Math.max(0, capacidadMaxima);
        this.miembros = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.encargados = new ArrayList<>();
    }

    public String getIdClub() { return idClub; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getRequisitos() { return requisitos; }
    public void setRequisitos(String requisitos) { this.requisitos = requisitos; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
    public int getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(int capacidadMaxima) {
        if (capacidadMaxima < 0) throw new IllegalArgumentException("Capacidad no puede ser negativa");
        this.capacidadMaxima = capacidadMaxima;
    }
    public List<Usuario> getMiembros() { return Collections.unmodifiableList(miembros); }
    public List<Evento> getEventos() { return Collections.unmodifiableList(eventos); }
    public List<Usuario> getEncargados() { return Collections.unmodifiableList(encargados); }

    public boolean agregarMiembro(Usuario usuario) {
        Objects.requireNonNull(usuario, "usuario no puede ser null");
        if (miembros.contains(usuario)) return false;
        if (!verificarCupo()) return false;
        miembros.add(usuario);
        usuario.addClub(this);
        return true;
    }

    public boolean quitarMiembro(Usuario usuario) {
        Objects.requireNonNull(usuario, "usuario no puede ser null");
        boolean removed = miembros.remove(usuario);
        if (removed) usuario.removeClub(this);
        return removed;
    }

    public boolean verificarCupo() {
        return miembros.size() < capacidadMaxima;
    }

    public void publicarAviso(String mensaje) {
        Objects.requireNonNull(mensaje, "mensaje no puede ser null");
        String aviso = String.format("AVISO en '%s' (%s): %s", nombre, idClub, mensaje);
        System.out.println(aviso);
    }

    public boolean agregarEvento(Evento evento) {
        Objects.requireNonNull(evento, "evento no puede ser null");
        if (eventos.contains(evento)) return false;
        eventos.add(evento);
        evento.setClub(this);
        return true;
    }

    public boolean removerEvento(Evento evento) {
        Objects.requireNonNull(evento, "evento no puede ser null");
        boolean removed = eventos.remove(evento);
        if (removed) evento.setClub(null);
        return removed;
    }

    public List<String> listarMiembros() {
        List<String> lista = new ArrayList<>();
        for (Usuario u : miembros) lista.add(u.getIdUsuario() + " - " + u.getNombre());
        return lista;
    }

    public boolean agregarEncargado(Usuario encargado) {
        Objects.requireNonNull(encargado, "encargado no puede ser null");
        if (encargados.contains(encargado)) return false;
        encargados.add(encargado);
        return true;
    }

    public boolean quitarEncargado(Usuario encargado) {
        Objects.requireNonNull(encargado, "encargado no puede ser null");
        return encargados.remove(encargado);
    }

    @Override
    public String toString() {
        return "Club{" +
                "idClub='" + idClub + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", miembros=" + miembros.size() +
                ", eventos=" + eventos.size() +
                ", horario='" + horario + '\'' +
                ", capacidadMaxima=" + capacidadMaxima +
                '}';
    }

    public static void main(String[] args) {
        Club clubProg = new Club("Club de Programación", "Practicar y aprender algoritmos", "Académico", "Interés en programar", "Mié 17:00-19:00", 5);

        Usuario u1 = new Usuario("u25499", "Marielos Marroquín", "mmarroquin@uvg.edu.gt");
        Usuario u2 = new Usuario("u25682", "Daniel Leal", "dleal@uvg.edu.gt");

        clubProg.agregarEncargado(u1);
        clubProg.agregarMiembro(u1);
        clubProg.agregarMiembro(u2);

        System.out.println("Miembros del club:");
        for (String s : clubProg.listarMiembros()) System.out.println(" - " + s);

        clubProg.publicarAviso("Reunión extra el viernes 18:00 en sala B");
        Evento evento = new Evento("e1", "Hackathon UVG", "Competencia 24h", new Date(), "Sala B");
        clubProg.agregarEvento(evento);

        System.out.println(clubProg);
    }
}

class Usuario {
    private final String idUsuario;
    private String nombre;
    private String correo;
    private final Set<Club> clubs;

    public Usuario(String idUsuario, String nombre, String correo) {
        this.idUsuario = Objects.requireNonNull(idUsuario);
        this.nombre = nombre;
        this.correo = correo;
        this.clubs = new HashSet<>();
    }

    public String getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }

    void addClub(Club club) {
        if (club != null) clubs.add(club);
    }
    void removeClub(Club club) {
        if (club != null) clubs.remove(club);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return idUsuario.equals(usuario.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario);
    }

    @Override
    public String toString() {
        return nombre + " <" + idUsuario + ">";
    }
}

class Evento {
    private final String idEvento;
    private String nombre;
    private String descripcion;
    private Date fecha;
    private String ubicacion;
    private Club club;
    private final List<Usuario> asistentes;

    public Evento(String idEvento, String nombre, String descripcion, Date fecha, String ubicacion) {
        this.idEvento = Objects.requireNonNull(idEvento);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.asistentes = new ArrayList<>();
    }

    public String getIdEvento() { return idEvento; }
    public String getNombre() { return nombre; }
    public Date getFecha() { return fecha; }
    public String getUbicacion() { return ubicacion; }
    public void setClub(Club club) { this.club = club; }

    public boolean registrarAsistencia(Usuario u) {
        Objects.requireNonNull(u);
        if (asistentes.contains(u)) return false;
        asistentes.add(u);
        return true;
    }

    public boolean verificarCupo(int capacidadLugar) {
        return asistentes.size() < capacidadLugar;
    }

    @Override
    public String toString() {
        return "Evento{" + idEvento + " - " + nombre + "}";
    }
}
