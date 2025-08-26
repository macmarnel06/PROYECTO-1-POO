import java.util.ArrayList;
import java.util.List;

public class Ubicacion {

    //atributos
    private String edificio;
    private String salon;
    private int capacidad;
    private String club_que_esta;
    private List<String> disponibilidad; 

    public Ubicacion(String edificio, String salon, int capacidad) {
        this.edificio = edificio;
        this.salon = salon;
        this.capacidad = capacidad;
        this.club_que_esta = null;
        this.disponibilidad = new ArrayList<>();
    }

    //metodos
    public boolean verificar_disponibilidad_salon() {
        return disponibilidad.isEmpty();
    }

    public int ver_capacidad() {
        return capacidad;
    }

    public List<String> ver_reservas_activas() {
        return disponibilidad;
    }

    //getters
    public String getEdificio() { return edificio; }
    public void setEdificio(String edificio) { this.edificio = edificio; }

    public String getSalon() { return salon; }
    public void setSalon(String salon) { this.salon = salon; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public String getClub_que_esta() { return club_que_esta; }
    public void setClub_que_esta(String club_que_esta) { this.club_que_esta = club_que_esta; }

    public List<String> getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(List<String> disponibilidad) { this.disponibilidad = disponibilidad; }
}
