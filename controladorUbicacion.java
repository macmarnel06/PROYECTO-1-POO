
public class controladorUbicacion {

    public Ubicacion crearUbicacion(String edificio, String nombreSala, int capacidadMaxima) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setEdificio(edificio);
        ubicacion.setNombreSala(nombreSala);
        ubicacion.setCapacidadMaxima(capacidadMaxima);
        return ubicacion;
    }

    public boolean verificarDisponibilidad(Ubicacion ubicacion, String fecha, String hora) {
        return ubicacion.estaDisponible(fecha, hora);
    }

    public void mostrarResumen(Ubicacion ubicacion) {
        System.out.println(ubicacion.resumenUbicacion());
    }
}
