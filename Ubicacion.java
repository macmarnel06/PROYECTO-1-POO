package PROYECTO1;

public class Ubicacion {
    private int id;
    private String edificio;
    private String nombreSala;
    private int capacidadMaxima;

    public Ubicacion(int id, String edificio, String nombreSala, int capacidadMaxima) {
        this.id = id;
        this.edificio = edificio;
        this.nombreSala = nombreSala;
        this.capacidadMaxima = capacidadMaxima;
    }

    public Ubicacion() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEdificio() { return edificio; }
    public void setEdificio(String edificio) { this.edificio = edificio; }

    public String getNombreSala() { return nombreSala; }
    public void setNombreSala(String nombreSala) { this.nombreSala = nombreSala; }

    public int getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(int capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }

    public boolean estaDisponible(String fecha, String hora) {
        // Simulación de disponibilidad
        return true;
    }

    public String resumenUbicacion() {
        return String.format("Ubicación: %s - %s (Capacidad: %d)", edificio, nombreSala, capacidadMaxima);
    }
}