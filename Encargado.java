
public class Encargado extends Usuario {

    public Encargado(String id_usuario, String nombre, String correo_estudiante) {
        super(id_usuario, nombre, correo_estudiante);
    }

    public boolean reservar(Ubicacion ubicacion, String etiquetaReserva) {
        if (ubicacion == null) return false;
        if (etiquetaReserva == null || etiquetaReserva.isBlank()) {
            etiquetaReserva = "Reserva realizada por " + getNombre();
        }
        if (!ubicacion.getDisponibilidad().contains(etiquetaReserva)) {
            ubicacion.getDisponibilidad().add(etiquetaReserva);
            System.out.println("Reserva creada en " + ubicacion.getSalon() + " por " + getNombre());
            return true;
        }
        return false;
    }

    public boolean cancelar_reserva(Ubicacion ubicacion, String etiquetaReserva) {
        if (ubicacion == null || etiquetaReserva == null) return false;
        boolean ok = ubicacion.getDisponibilidad().remove(etiquetaReserva);
        if (ok) {
            System.out.println("Reserva cancelada en " + ubicacion.getSalon() + " por " + getNombre());
        }
        return ok;
    }

    public boolean registrar_asistencia(Evento evento, Usuario usuario) {
        if (evento == null || usuario == null) return false;
        boolean ok = evento.registrar_asistencia(usuario);
        if (ok) {
            System.out.println(usuario.getNombre() + " fue registrado en el evento " + evento.getNombre());
        }
        return ok;
    }

    public boolean ver_disponibilidad(Ubicacion ubicacion) {
        if (ubicacion == null) return false;
        return ubicacion.verificar_disponibilidad_salon();
    }
}
