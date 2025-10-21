
import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String id_usuario;
    private String nombre;
    private String correo_estudiante;
    private List<Club> clubs;

    public Usuario(String id_usuario, String nombre, String correo_estudiante) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.correo_estudiante = correo_estudiante;
        this.clubs = new ArrayList<Club>();
    }

    public void unirse_club(Club club) {
        if (club == null) return;
        if (!clubs.contains(club)) {
            clubs.add(club);
            club.agregarMiembro(this);
            System.out.println(nombre + " se unió al club " + club.getNombre());
        }
    }

    public void abandonar_club(Club club) {
        if (club == null) return;
        if (clubs.contains(club)) {
            clubs.remove(club);
            club.removerMiembro(this);
            System.out.println(nombre + " salió del club " + club.getNombre());
        }
    }

    public void ver_info_club(Club club) {
        if (club == null) return;
        System.out.println("Club: " + club.getNombre());
        System.out.println("Descripción: " + club.getDescripcion());
    }

    public void ver_miembros_club(Club club) {
        if (club == null) return;
        System.out.println("Miembros del club " + club.getNombre() + ":");
        for (Usuario u : club.getMiembros()) {
            System.out.println("- " + u.getNombre());
        }
    }

    public String getId_usuario() { return id_usuario; }
    public String getNombre() { return nombre; }
    public String getCorreo_estudiante() { return correo_estudiante; }
    public List<Club> getClubs() { return clubs; }
}
