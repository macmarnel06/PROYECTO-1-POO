package PROYECTO1;

import java.time.LocalDateTime;

public class Evento {
    private int id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaHora;
    private Ubicacion ubicacion;
    private Club clubAsociado;

    public Evento(int id, String nombre, String descripcion, LocalDateTime fechaHora, Ubicacion ubicacion, Club clubAsociado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.ubicacion = ubicacion;
        this.clubAsociado = clubAsociado;
    }

    public Evento() {}

    // Getters y Setters
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

    public String resumenEvento() {
        return String.format("Evento: %s\nDescripción: %s\nFecha y hora: %s\nUbicación: %s\nClub: %s",
                nombre, descripcion, fechaHora.toString(), ubicacion.getNombreSala(), clubAsociado.getNombre());
    }
}