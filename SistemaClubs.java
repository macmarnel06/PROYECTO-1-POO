import java.util.ArrayList;
import java.util.List;

public class SistemaClubs {

    private final List<Usuario> usuarios;
    private final List<Club> clubs;
    private final List<Evento> eventos;
    private final List<Ubicacion> ubicaciones;

    public SistemaClubs() {
        this.usuarios = new ArrayList<>();
        this.clubs = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.ubicaciones = new ArrayList<>();
    }

    public void agregarUsuario(Usuario u) { if (u != null) usuarios.add(u); }
    public void agregarClub(Club c) { if (c != null) clubs.add(c); }
    public void agregarEvento(Evento e) { if (e != null) eventos.add(e); }
    public void agregarUbicacion(Ubicacion u) { if (u != null) ubicaciones.add(u); }

    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Club> getClubs() { return clubs; }
    public List<Evento> getEventos() { return eventos; }
    public List<Ubicacion> getUbicaciones() { return ubicaciones; }

    public String generar_reporte(String tipo) {
        if ("clubs".equalsIgnoreCase(tipo)) {
            StringBuilder sb = new StringBuilder("REPORTE DE CLUBES\n");
            sb.append("Total de clubes: ").append(clubs.size()).append("\n");
            for (int i = 0; i < clubs.size(); i++) {
                Club c = clubs.get(i);
                sb.append(i + 1).append(". ").append(c.getNombre())
                  .append(" — miembros: ").append(c.getMiembros().size()).append("\n");
            }
            return sb.toString();
        } else if ("eventos".equalsIgnoreCase(tipo)) {
            StringBuilder sb = new StringBuilder("REPORTE DE EVENTOS\n");
            sb.append("Total de eventos: ").append(eventos.size()).append("\n");
            for (int i = 0; i < eventos.size(); i++) {
                Evento e = eventos.get(i);
                String salon = (e.getUbicacion() == null ? "" : e.getUbicacion().getSalon());
                sb.append(i + 1).append(". ").append(e.getNombre()).append(" @ ").append(salon).append("\n");
            }
            return sb.toString();
        } else if ("ubicaciones".equalsIgnoreCase(tipo)) {
            StringBuilder sb = new StringBuilder("REPORTE DE UBICACIONES\n");
            sb.append("Total de ubicaciones: ").append(ubicaciones.size()).append("\n");
            for (int i = 0; i < ubicaciones.size(); i++) {
                Ubicacion u = ubicaciones.get(i);
                sb.append(i + 1).append(". ").append(u.getNombreSala())
                  .append(" (cap: ").append(u.getCapacidadMaxima()).append(")\n");
            }
            return sb.toString();
        }
        return "Tipo de reporte no soportado";
    }
}

