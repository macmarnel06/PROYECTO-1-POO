public class controladorClub {

    public Club crearClub(String idClub, String nombre, String descripcion) {
        return new Club(idClub, nombre, descripcion);
    }

    public void mostrarResumen(Club club) {
        if (club == null) return;
        System.out.println("Club: " + club.getNombre());
        System.out.println("Descripción: " + club.getDescripcion());
        System.out.println("Miembros: " + club.getMiembros().size());
        System.out.println("Eventos: " + club.getEventos().size());
    }
}