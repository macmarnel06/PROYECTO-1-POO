
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ControladorVista {

    private final SistemaClubs sistema;

    public ControladorVista(SistemaClubs sistema) {
        this.sistema = sistema;
    }

    public Usuario crearUsuario(String idUsuario, String nombre, String correo) {
        if (idUsuario == null || idUsuario.isBlank()) return null;
        Usuario u = new Usuario(idUsuario, nombre, correo);
        sistema.agregarUsuario(u);
        return u;
    }

    public Club crearClub(String idClub, String nombre, String descripcion) {
        if (idClub == null || idClub.isBlank()) return null;
        Club c = new Club(idClub, nombre, descripcion);
        sistema.agregarClub(c);
        return c;
    }

    public Ubicacion crearUbicacion(int id, String edificio, String nombreSala, int capacidad) {
        Ubicacion u = new Ubicacion(id, edificio, nombreSala, capacidad);
        sistema.agregarUbicacion(u);
        return u;
    }

    /** Crea evento con ID autoincremental simple (tamaño + 1). fechaHora en ISO local: "2025-10-20T14:30" */
    public Evento crearEvento(String nombre, String descripcion, String fechaHoraISO, Integer idUbicacion, String idClub) {
        LocalDateTime fechaHora = null;
        if (fechaHoraISO != null && !fechaHoraISO.isBlank()) {
            try { fechaHora = LocalDateTime.parse(fechaHoraISO); } catch (DateTimeParseException ignored) {}
        }
        Ubicacion u = (idUbicacion == null) ? null : buscarUbicacionPorId(idUbicacion);
        Club c = (idClub == null || idClub.isBlank()) ? null : buscarClubPorId(idClub);

        int nuevoId = sistema.getEventos().size() + 1;
        Evento ev = new Evento(nuevoId, nombre, descripcion, fechaHora, u, c);
        sistema.agregarEvento(ev);
        if (c != null) c.agregarEvento(ev);
        return ev;
    }

    public boolean unirseAClub(String idUsuario, String idClub) {
        Usuario u = buscarUsuarioPorId(idUsuario);
        Club c = buscarClubPorId(idClub);
        if (u == null || c == null) return false;
        u.unirse_club(c);
        return true;
    }

    public boolean abandonarClub(String idUsuario, String idClub) {
        Usuario u = buscarUsuarioPorId(idUsuario);
        Club c = buscarClubPorId(idClub);
        if (u == null || c == null) return false;
        u.abandonar_club(c);
        return true;
    }

    public boolean registrarAsistencia(int idEvento, String idUsuario) {
        Evento e = buscarEventoPorId(idEvento);
        Usuario u = buscarUsuarioPorId(idUsuario);
        if (e == null || u == null) return false;
        return e.registrar_asistencia(u);
    }

    public String reporteClubs() { return sistema.generar_reporte("clubs"); }
    public String reporteEventos() { return sistema.generar_reporte("eventos"); }
    public String reporteUbicaciones() { return sistema.generar_reporte("ubicaciones"); }

    public List<Usuario> listarUsuarios() { return sistema.getUsuarios(); }
    public List<Club> listarClubs() { return sistema.getClubs(); }
    public List<Ubicacion> listarUbicaciones() { return sistema.getUbicaciones(); }
    public List<Evento> listarEventos() { return sistema.getEventos(); }

    public Usuario buscarUsuarioPorId(String idUsuario) {
        for (Usuario u : sistema.getUsuarios()) if (u.getId_usuario().equals(idUsuario)) return u;
        return null;
    }

    public Club buscarClubPorId(String idClub) {
        for (Club c : sistema.getClubs()) if (c.getIdClub().equals(idClub)) return c;
        return null;
    }

    public Ubicacion buscarUbicacionPorId(int id) {
        for (Ubicacion u : sistema.getUbicaciones()) if (u.getId() == id) return u;
        return null;
    }

    public Evento buscarEventoPorId(int id) {
        for (Evento e : sistema.getEventos()) if (e.getId() == id) return e;
        return null;
    }
}
