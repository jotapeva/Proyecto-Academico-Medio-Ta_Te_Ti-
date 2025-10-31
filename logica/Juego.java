
package logica;

import static interfaz.Prueba.in;
import static interfaz.Prueba.s;
import java.util.ArrayList;

public class Juego {


    public Juego() {
       
    }
    // ---- NUEVA PARTIDA ----
    public static void iniciarPartida() {
        if (s.getListaJugadores().size() < 2) {
            System.out.println("Debe haber al menos 2 jugadores registrados.");
            return;
        }

        ArrayList<Jugador> lista = s.listarJugadoresOrdenados();
        System.out.println("Jugadores disponibles:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i).getNombre());
        }

        System.out.print("Seleccione número de jugador Blanco (○): ");
        int j1Index = leerIndice(lista.size());
        System.out.print("Seleccione número de jugador Negro (●): ");
        int j2Index = leerIndice(lista.size());

        if (j1Index == j2Index) {
            System.out.println("Los jugadores deben ser distintos.");
            return;
        }

        Jugador blanco = lista.get(j1Index);
        Jugador negro = lista.get(j2Index);
        System.out.println("Comienza el jugador BLANCO (" + blanco.getNombre() + ")");
        Tablero t = new Tablero();
        partidaComun(blanco, negro, t, blanco);
    }

    // ---- CONTINUAR PARTIDA ----
    public static void continuarPartida() {
        if (s.getListaJugadores().size() < 2) {
            System.out.println("Debe haber al menos 2 jugadores registrados.");
            return;
        }

        ArrayList<Jugador> lista = s.listarJugadoresOrdenados();
        System.out.println("Jugadores disponibles:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i).getNombre());
        }

        System.out.print("Seleccione número de jugador Blanco (○): ");
        int j1Index = leerIndice(lista.size());
        System.out.print("Seleccione número de jugador Negro (●): ");
        int j2Index = leerIndice(lista.size());

        if (j1Index == j2Index) {
            System.out.println("Los jugadores deben ser distintos.");
            return;
        }

        Jugador j1 = lista.get(j1Index);
        Jugador j2 = lista.get(j2Index);
        Tablero t = new Tablero();

        System.out.println("Ingrese la secuencia de movimientos (ej: A1C B2D C3I). Comienza con Blanco (○):");
        String secuencia = in.nextLine().trim().toUpperCase();

        Jugador turno = j1;
        if (!secuencia.isEmpty()) {
            String[] movimientos = secuencia.split("\\s+");
            for (String mov : movimientos) {
                if (!esJugadaValida(mov)) {
                    System.out.println("Movimiento inválido: " + mov);
                }
                int fila = mov.charAt(0) - 'A';
                int columna = mov.charAt(1) - '1';
                char accion = mov.charAt(2);
                String fichaPropia = (turno == j1) ? "B" : "N";
                String resultado = (accion == 'I')
                        ? t.invertir(fila, columna, fichaPropia) : t.colocar(fila, columna, fichaPropia + accion);
                if (!esMensajeExito(resultado)) {
                    System.out.println("No se pudo realizar el movimiento: " + mov + " -> " + resultado);
                }
                System.out.println(t.dibujar(true));
                turno = (turno == j1) ? j2 : j1;
            }
        }

        System.out.println("Secuencia cargada. Continuar partida...");
        System.out.println("///////////////////////////////////////");
        partidaComun(j1, j2, t, turno);
    }

    // ---- PARTIDA COMÚN ----
    public static void partidaComun(Jugador blanco, Jugador negro, Tablero t, Jugador turno) {
        boolean conTitulos = true;
        boolean partidaActiva = true;

        while (partidaActiva) {
            System.out.println(t.dibujar(conTitulos));
            System.out.println("Turno de " + turno.getNombre());
            System.out.print("Ingrese jugada (A1C/B2D/C3I) o comando (X=Rendirse, T=Empate, B=Mostrar, N=Ocultar, H=Ayuda): ");

            String entrada = in.nextLine().trim().toUpperCase();

            switch (entrada) {
                case "X" -> {
                    System.out.println(turno.getNombre() + " se rindió. Pierde la partida.");
                    turno.sumarPerdida();
                    (turno == blanco ? negro : blanco).sumarGanada();
                    partidaActiva = false;
                }
                case "B" ->
                    conTitulos = true;
                case "N" ->
                    conTitulos = false;
                case "T" -> {
                    System.out.print("Confirmar empate? S/N: ");
                    if (in.nextLine().trim().equalsIgnoreCase("S")) {
                        blanco.sumarEmpate();
                        negro.sumarEmpate();
                        System.out.println("Partida terminada en empate.");
                        partidaActiva = false;
                    }
                }
                case "H" ->
                    mostrarJugadasGanadoras(t, turno, blanco);
                default -> {
                    if (!esJugadaValida(entrada)) {
                        System.out.println("Jugada inválida. Use formato A1C, B2D o C3I.");
                        break;
                    }

                    int fila = entrada.charAt(0) - 'A';
                    int columna = entrada.charAt(1) - '1';
                    char accion = entrada.charAt(2);
                    String fichaPropia = (turno == blanco) ? "B" : "N";

                    String resultado = (accion == 'I')
                            ? t.invertir(fila, columna, fichaPropia)
                            : t.colocar(fila, columna, fichaPropia + accion);

                    if (!esMensajeExito(resultado)) {
                        System.out.println("Movimiento inválido: " + resultado);
                        break;
                    }

                    char ganador = t.verificarGanador();
                    if (ganador != ' ') {
                        Jugador ganadorJ = (ganador == 'X') ? blanco : negro;
                        Jugador perdedor = (ganador == 'X') ? negro : blanco;
                        System.out.println("Gana " + ganadorJ.getNombre() + "!");
                        ganadorJ.sumarGanada();
                        perdedor.sumarPerdida();
                        partidaActiva = false;
                    } else {
                        turno = (turno == blanco) ? negro : blanco;
                    }
                }
            }
        }

        System.out.println(t.dibujar(conTitulos));
        System.out.println("Fin de la partida.");
    }

    // ---- AUXILIARES ----
    private static int leerIndice(int size) {
        while (true) {
            String entrada = in.nextLine().trim();
            if (entrada.matches("\\d+")) {
                int idx = Integer.parseInt(entrada) - 1;
                if (idx >= 0 && idx < size) {
                    return idx;
                }
            }
            System.out.print("Número inválido, reintente: ");
        }
    }

    private static boolean esJugadaValida(String jugada) {
        if (jugada == null || jugada.length() != 3) {
            return false;
        }
        char f = jugada.charAt(0);
        char c = jugada.charAt(1);
        char acc = jugada.charAt(2);
        return f >= 'A' && f <= 'C' && c >= '1' && c <= '6' && (acc == 'C' || acc == 'D' || acc == 'I');
    }

    // Comprueba si el mensaje devuelto por Tablero indica éxito
    private static boolean esMensajeExito(String msg) {
        if (msg == null) {
            return false;
        }
        String m = msg.toLowerCase();
        return m.contains("correctamente") || m.contains("colocada") || m.contains("invertida");
    }

    private static void mostrarJugadasGanadoras(Tablero t, Jugador turno, Jugador blanco) {
        boolean hayGanadora = false;
        String ficha = (turno == blanco) ? "B" : "N";
        String[][] copia = t.clonarTablero();

        for (int i = 0; i < 3 && !hayGanadora; i++) {
            for (int j = 0; j < 6 && !hayGanadora; j++) {
                for (char accion : new char[]{'C', 'D', 'I'}) {
                    // crear tablero temporal y restaurar desde copia
                    Tablero temp = new Tablero();
                    temp.setTablero(copia);
                    String resultado = (accion == 'I')
                            ? temp.invertir(i, j, ficha)
                            : temp.colocar(i, j, ficha + accion);

                    if (esMensajeExito(resultado)) {
                        char ganadorPosible = temp.verificarGanador();
                        if (ganadorPosible == (turno == blanco ? 'O' : 'X')) {
                            System.out.println("Jugada ganadora posible: " + (char) ('A' + i) + (j + 1) + accion);
                            hayGanadora = true;
                            break;
                        }
                    }
                }
            }
        }

        if (!hayGanadora) {
            System.out.println("No hay jugadas ganadoras disponibles para este turno.");
        }
    }
}
