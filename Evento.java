
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Evento {
    private int id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaHora;
    private Ubicacion ubicacion;
    private Club clubAsociado;
    private final Set<Usuario> asistencia = new LinkedHashSet<>();

    public Evento() {}

    public Evento(int id, String nombre, String descripcion, LocalDateTime fechaHora, Ubicacion ubicacion, Club clubAsociado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.ubicacion = ubicacion;
        this.clubAsociado = clubAsociado;
    }

    public boolean registrar_asistencia(Usuario usuario) {
        Objects.requireNonNull(usuario, "usuario");
        if (ubicacion != null && ubicacion.getCapacidadMaxima() > 0) {
            if (asistencia.size() >= ubicacion.getCapacidadMaxima()) return false;
        }
        return asistencia.add(usuario);
    }

    public boolean verificar_cupo() {
        if (ubicacion == null || ubicacion.getCapacidadMaxima() <= 0) return true;
        return asistencia.size() < ubicacion.getCapacidadMaxima();
    }

    public String resumenEvento() {
        String lugar = (ubicacion != null) ? ubicacion.getNombreSala() : "Ubicación por confirmar";
        return String.format("Evento: %s\nDescripción: %s\nFecha y hora: %s\nUbicación: %s\nClub: %s",
                nombre,
                descripcion,
                (fechaHora != null ? fechaHora.toString() : "s/f"),
                lugar,
                (clubAsociado != null ? clubAsociado.getNombre() : "s/c"));
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public Ubicacion getUbicacion() { return ubicacion; }
    public void setUbicacion(Ubicacion ubicacion) { this.ubicacion = ubicacion; }
    public Club getClubAsociado() { return clubAsociado; }
    public void setClubAsociado(Club clubAsociado) { this.clubAsociado = clubAsociado; }
    public Set<Usuario> getAsistencia() { return java.util.Collections.unmodifiableSet(asistencia); }
}
