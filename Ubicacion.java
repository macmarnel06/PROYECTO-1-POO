import java.util.ArrayList;
import java.util.List;

public class Ubicacion {
    private int id;
    private String edificio;
    private String nombreSala;
    private int capacidadMaxima;

    private final List<String> disponibilidad = new ArrayList<>();

    public Ubicacion(int id, String edificio, String nombreSala, int capacidadMaxima) {
        this.id = id;
        this.edificio = edificio;
        this.nombreSala = nombreSala;
        this.capacidadMaxima = capacidadMaxima;
    }

    public Ubicacion() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEdificio() { return edificio; }
    public void setEdificio(String edificio) { this.edificio = edificio; }

    public String getNombreSala() { return nombreSala; }
    public void setNombreSala(String nombreSala) { this.nombreSala = nombreSala; }

    public int getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(int capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }

    public String getSalon() { return getNombreSala(); }
    public int ver_capacidad() { return getCapacidadMaxima(); }
    public List<String> getDisponibilidad() { return disponibilidad; }
    public List<String> ver_reservas_activas() { return disponibilidad; }
    public String getClub_que_esta() { return ""; }
    public boolean verificar_disponibilidad_salon() { return true; }

    public boolean estaDisponible(String fecha, String hora) { return true; }

    public String resumenUbicacion() {
        return String.format("Ubicación: %s - %s (Capacidad: %d)", edificio, nombreSala, capacidadMaxima);
    }
}


