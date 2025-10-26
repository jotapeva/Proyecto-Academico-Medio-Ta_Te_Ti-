package clases;

import java.util.ArrayList;

public class Sistema {

    private ArrayList<Jugador> listaJugadores = new ArrayList<>();

    // Getters y Setters
    public ArrayList<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(ArrayList<Jugador> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    // Buscar jugador por nombre
    public Jugador buscarJugador(String nombre) {
        for (Jugador j : listaJugadores) {
            if (j.getNombre().equalsIgnoreCase(nombre)) {
                return j;
            }
        }
        return null;
    }

    // 🔹 Genera una cadena formateada para mostrar una lista
    public String obtenerListaFormateada(String[] lista, String titulo) {
        StringBuilder sb = new StringBuilder();
        sb.append("----- ").append(titulo).append(" -----\n");
        for (int i = 0; i < lista.length; i++) {
            sb.append((i + 1)).append(" - ").append(lista[i]).append("\n");
        }
        sb.append("----------------------------\n");
        return sb.toString();
    }

    // Listar jugadores ordenados alfabéticamente
    public ArrayList<Jugador> listarJugadoresOrdenados() {
        ArrayList<Jugador> lista = new ArrayList<>(listaJugadores);

        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                Jugador a = lista.get(i);
                Jugador b = lista.get(j);
                if (b.getNombre().compareToIgnoreCase(a.getNombre()) < 0) {
                    lista.set(i, b);
                    lista.set(j, a);
                }
            }
        }
        return lista;
    }

    // Listar jugadores por partidas ganadas (descendente)
    public String[] listarJugadoresPorPartidas() {
        if (listaJugadores.isEmpty()) {
            return new String[]{"No hay jugadores registrados."};
        }

        ArrayList<Jugador> lista = new ArrayList<>(listaJugadores);

        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                Jugador a = lista.get(i);
                Jugador b = lista.get(j);
                if (b.getPartidasGanadas() > a.getPartidasGanadas() ||
                   (b.getPartidasGanadas() == a.getPartidasGanadas() &&
                    b.getNombre().compareToIgnoreCase(a.getNombre()) < 0)) {
                    lista.set(i, b);
                    lista.set(j, a);
                }
            }
        }

        String[] resultado = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            Jugador j = lista.get(i);
            resultado[i] = j.getNombre() + " | Edad: " + j.getEdad() +
                           " | Ganadas: " + j.getPartidasGanadas() +
                           " | Perdidas: " + j.getPartidasPerdidas() +
                           " | Empates: " + j.getPartidasEmpatadas();
        }
        return resultado;
    }

    // Listar jugadores invictos
    public String[] listarJugadoresInvictos() {
        if (listaJugadores.isEmpty()) {
            return new String[]{"No hay jugadores registrados."};
        }

        ArrayList<String> invictos = new ArrayList<>();

        for (Jugador j : listaJugadores) {
            if (j.getPartidasPerdidas() == 0 &&
                j.getPartidasGanadas() > 0) {
                invictos.add(j.getNombre() + " | Edad: " + j.getEdad() +
                             " | Ganadas: " + j.getPartidasGanadas() +
                             " | Empates: " + j.getPartidasEmpatadas());
            }
        }

        if (invictos.isEmpty()) {
            return new String[]{"No hay jugadores invictos."};
        }

        return invictos.toArray(new String[0]);
    }
}
