package PROYECTO1;

import java.util.ArrayList;
import java.util.List;

public class SistemaClubs {

    
    private List<Usuario> usuarios;
    private List<Club> clubs;
    private List<Evento> eventos;
    private List<Ubicacion> ubicaciones;

   
    public SistemaClubs() {
        this.usuarios = new ArrayList<Usuario>();
        this.clubs = new ArrayList<Club>();
        this.eventos = new ArrayList<Evento>();
        this.ubicaciones = new ArrayList<Ubicacion>();
    }

   
    public void agregarUsuario(Usuario u) {
        if (u == null) return;
        if (!usuarios.contains(u)) {
            usuarios.add(u);
        }
    }

    public void agregarClub(Club c) {
        if (c == null) return;
        if (!clubs.contains(c)) {
            clubs.add(c);
        }
    }

    public void agregarEvento(Evento e) {
        if (e == null) return;
        if (!eventos.contains(e)) {
            eventos.add(e);
        }
    }

    
    public String generar_reporte(String tipo) {
        if (tipo == null) tipo = "";
        String t = tipo.toLowerCase();

        if (t.equals("clubes")) {
            String s = "REPORTE DE CLUBES\n";
            s += "Total de clubes: " + clubs.size() + "\n";
            for (int i = 0; i < clubs.size(); i++) {
                Club c = clubs.get(i);
                int cant = 0;
                
                for (int j = 0; j < eventos.size(); j++) {
                    Evento e = eventos.get(j);
                    if (e.getClubAsociado() == c) {
                        cant++;
                    }
                }
                s += (i + 1) + ". " + c.getNombre() + " — eventos: " + cant + "\n";
            }
            return s;
        }
        else if (t.equals("eventos")) {
            String s = "REPORTE DE EVENTOS\n";
            s += "Total de eventos: " + eventos.size() + "\n";
            int limite = eventos.size();
            if (limite > 3) limite = 3;
            for (int i = 0; i < limite; i++) {
                Evento e = eventos.get(i);
                s += "- " + e.getNombre()
                     + " (" + e.getFecha() + " " + e.getHorario()
                     + " @ " + (e.getUbicacion() == null ? "N/A" : e.getUbicacion())
                     + ")\n";
            }
            return s;
        }
        else if (t.equals("ubicaciones")) {
            String s = "REPORTE DE UBICACIONES\n";
            s += "Total de ubicaciones: " + ubicaciones.size() + "\n";
            for (int i = 0; i < ubicaciones.size(); i++) {
                Ubicacion u = ubicaciones.get(i);
                String etiqueta = u.getEdificio() + " - " + u.getSalon();
                int reservas = u.ver_reservas_activas().size();
                s += "- " + etiqueta
                     + " — capacidad: " + u.ver_capacidad()
                     + " — reservas: " + reservas + "\n";
            }
            return s;
        }
        else {
            return "Tipo de reporte no reconocido. Use: clubes | eventos | ubicaciones.";
        }
    }

    
    public void agregar_ubicacion(Ubicacion ubicacion) {
        if (ubicacion == null) return;
        for (int i = 0; i < ubicaciones.size(); i++) {
            Ubicacion u = ubicaciones.get(i);
            if (igualSala(u, ubicacion)) {
                return; // ya existe
            }
        }
        ubicaciones.add(ubicacion);
    }

    
    public void eliminar_ubicacion(String salon) {
        if (salon == null) return;
        for (int i = 0; i < ubicaciones.size(); i++) {
            if (salon.equals(ubicaciones.get(i).getSalon())) {
                ubicaciones.remove(i);
                i--; 
            }
        }
    }

   
    public List<Ubicacion> filtrar_por_ubicacion(String criterio) {
        List<Ubicacion> out = new ArrayList<Ubicacion>();
        if (criterio == null || criterio.trim().length() == 0) {
            for (int i = 0; i < ubicaciones.size(); i++) out.add(ubicaciones.get(i));
            return out;
        }
        String q = criterio.toLowerCase();
        for (int i = 0; i < ubicaciones.size(); i++) {
            Ubicacion u = ubicaciones.get(i);
            String edificio = safe(u.getEdificio());
            String salon = safe(u.getSalon());
            String club = safe(u.getClub_que_esta());
            if (edificio.indexOf(q) >= 0 || salon.indexOf(q) >= 0 || club.indexOf(q) >= 0) {
                out.add(u);
            }
        }
        return out;
    }

   
    public List<Evento> ver_eventos_por_ubi(Ubicacion ubicacion) {
        List<Evento> out = new ArrayList<Evento>();
        if (ubicacion == null) return out;

        String etiquetaCompleta = safe(ubicacion.getEdificio()) + " - " + safe(ubicacion.getSalon());
        String soloSalon = safe(ubicacion.getSalon());

        for (int i = 0; i < eventos.size(); i++) {
            Evento e = eventos.get(i);
            String uEv = safe(e.getUbicacion());
            if (uEv.equals(etiquetaCompleta) || uEv.equals(soloSalon)) {
                out.add(e);
            }
        }
        return out;
    }

    
    private static boolean igualSala(Ubicacion a, Ubicacion b) {
        String ea = safe(a.getEdificio());
        String sa = safe(a.getSalon());
        String eb = safe(b.getEdificio());
        String sb = safe(b.getSalon());
        return ea.equalsIgnoreCase(eb) && sa.equalsIgnoreCase(sb);
    }

    private static String safe(String s) {
        if (s == null) return "";
        return s.toLowerCase();
    }

    
    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Club> getClubs() { return clubs; }
    public List<Evento> getEventos() { return eventos; }
    public List<Ubicacion> getUbicaciones() { return ubicaciones; }
}
