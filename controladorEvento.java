
import java.time.LocalDateTime;

public class controladorEvento {

    public Evento crearEvento(String nombre, String descripcion, LocalDateTime fechaHora, Ubicacion ubicacion, Club club) {
        Evento evento = new Evento();
        evento.setNombre(nombre);
        evento.setDescripcion(descripcion);
        evento.setFechaHora(fechaHora);
        evento.setUbicacion(ubicacion);
        evento.setClubAsociado(club);
        return evento;
    }

    public void mostrarResumen(Evento evento) {
        System.out.println(evento.resumenEvento());
    }
}
