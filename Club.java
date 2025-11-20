import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public Club(String idClub, String nombre, String descripcion) {
        this.idClub = Objects.requireNonNull(idClub);
        this.nombre = Objects.requireNonNull(nombre);
        this.descripcion = Objects.requireNonNull(descripcion);
        this.categoria = "";
        this.requisitos = "";
        this.miembros = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.horario = "";
        this.encargados = new ArrayList<>();
        this.capacidadMaxima = 0;
    }

    public boolean agregarMiembro(Usuario u) {
        if (u == null) return false;
        if (miembros.contains(u)) return false;
        miembros.add(u);
        return true;
    }

    public boolean removerMiembro(Usuario u) {
        if (u == null) return false;
        return miembros.remove(u);
    }

    public List<Usuario> getMiembros() { return miembros; }

    public void agregarEvento(Evento e) {
        if (e != null && !eventos.contains(e)) {
            eventos.add(e);
        }
    }

    public List<Evento> getEventos() { return eventos; }

    public String getIdClub() { return idClub; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = Objects.requireNonNull(nombre); }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = Objects.requireNonNull(descripcion); }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = Objects.requireNonNullElse(categoria, ""); }
    public String getRequisitos() { return requisitos; }
    public void setRequisitos(String requisitos) { this.requisitos = Objects.requireNonNullElse(requisitos, ""); }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = Objects.requireNonNullElse(horario, ""); }
    public List<Usuario> getEncargados() { return encargados; }
    public int getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(int capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }
}
