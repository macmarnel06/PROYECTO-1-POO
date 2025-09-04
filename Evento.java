package PROYECTO1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


public class Evento {

    private static final int DURACION_MINUTOS_POR_DEFECTO = 120;

    // Atributos
    private final String id_evento;             // Identificador único
    private String nombre;                      // Nombre del evento
    private String descripcion_evento;          // Detalles
    private LocalDate fecha;                    // Fecha programada
    private LocalTime horario;                  // Hora de inicio
    private Ubicacion ubicacion;                // Lugar (objeto)
    private final Set<Usuario> asistencia;      // Lista de usuarios asistentes
    
    private Club club;

   
    public Evento(String id_evento, String nombre, String descripcion_evento,
                  LocalDate fecha, LocalTime horario) {
        this.id_evento = Objects.requireNonNull(id_evento);
        this.nombre = Objects.requireNonNull(nombre);
        this.descripcion_evento = Objects.requireNonNull(descripcion_evento);
        this.fecha = Objects.requireNonNull(fecha);
        this.horario = Objects.requireNonNull(horario);
        this.asistencia = new LinkedHashSet<>();
    }

    

    
    public boolean verificar_cupo() {
        if (ubicacion == null) return true; 
        return asistencia.size() < ubicacion.getCapacidad();
    }

    
    public String publicar_recordatorio() {
        String lugar = (ubicacion != null) ? ubicacion.getNombre() : "Ubicación por confirmar";
        return String.format(
            "Recordatorio: \"%s\" el %s a las %s en %s.",
            nombre,
            fecha,
            horario,
            lugar
        );
    }

    
    public boolean registrar_asistencia(Usuario usuario) {
        Objects.requireNonNull(usuario, "usuario");
        if (!verificar_cupo()) {
            throw new IllegalStateException("Capacidad del salón alcanzada.");
        }
        return asistencia.add(usuario);
    }

    
    public void registrar_evento(Club club) {
        this.club = Objects.requireNonNull(club, "club");
        club.agregarEvento(this);
    }

    
    public Reserva reservar_salon(Ubicacion ubicacion) {
        Objects.requireNonNull(ubicacion, "ubicacion");
        LocalDateTime inicio = LocalDateTime.of(this.fecha, this.horario);
        LocalDateTime fin = inicio.plusMinutes(DURACION_MINUTOS_POR_DEFECTO);
        Reserva r = ubicacion.reservar(inicio, fin, this, null); // creador null (sistema)
        this.ubicacion = ubicacion;
        return r;
    }

    
    public LocalDateTime getInicioDateTime() {
        return LocalDateTime.of(fecha, horario);
    }
    public LocalDateTime getFinDateTime() {
        return getInicioDateTime().plusMinutes(DURACION_MINUTOS_POR_DEFECTO);
    }

    
    public String getId_evento() { return id_evento; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = Objects.requireNonNull(nombre); }
    public String getDescripcion_evento() { return descripcion_evento; }
    public void setDescripcion_evento(String d) { this.descripcion_evento = Objects.requireNonNull(d); }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = Objects.requireNonNull(fecha); }
    public LocalTime getHorario() { return horario; }
    public void setHorario(LocalTime horario) { this.horario = Objects.requireNonNull(horario); }
    public Ubicacion getUbicacion() { return ubicacion; }
    public Set<Usuario> getAsistencia() { return Set.copyOf(asistencia); }
    public Club getClub() { return club; }
}