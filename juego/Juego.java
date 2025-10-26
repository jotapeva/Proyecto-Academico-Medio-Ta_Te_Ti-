package juego;

import clases.Sistema;
import clases.Jugador;
import clases.Tablero;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Juego {

    public static Sistema s = new Sistema();
    public static Scanner in = new Scanner(System.in);
    

    public static void main(String[] args) throws Exception {
        // Forzar UTF-8 en salida
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
        System.out.println("|--------------------------------------|");
        System.out.println("|-----Bienvenido a 'Medio Tateti'------|");
        System.out.println("|--------------------------------------|");

    int op;
    do {
    System.out.println("//////////////////////////////////////////");
    System.out.println("¿Qué desea realizar?");
    System.out.println("1 - Crear Jugador");
    System.out.println("2 - Nueva Partida");
    System.out.println("3 - Continuar Partida");
    System.out.println("4 - Mostrar Ranking");
    System.out.println("5 - Mostrar Invictos");
    System.out.println("0 - Salir");
    System.out.println("-1 - Cargar jugadores de prueba");
    System.out.println("-2 - Mostrar ejemplo de tablero cargado con piezas");
    System.out.print("Opción: ");
    String entrada = in.nextLine().trim();
    try {
        op = Integer.parseInt(entrada);
    } catch (NumberFormatException e) {
        op = -99; // entrada inválida
    }

    switch (op) {
        case 1:
            crearJugador();
            break;
        case 2:
            iniciarPartida();
            break;
        case 3:
            continuarPartida();
            break;
        case 4:
            String[] lista = s.listarJugadoresPorPartidas();
            System.out.println(s.obtenerListaFormateada(lista, "Ranking de jugadores"));
            break;
        case 5:
            String[] invictos = s.listarJugadoresInvictos();
            System.out.println(s.obtenerListaFormateada(invictos, "Jugadores invictos"));
            break;
        case -1:
            cargarDatosPrueba();
            break;
        case -2:
            mostrarEjemploTablero();
            break;
        case 0:
            System.out.println("Adiós, gracias por jugar.");
            break;
        default:
            System.out.println("Ingrese una opción válida.");
            break;
    }

    System.out.println("//////////////////////////////////////////");
} while (op != 0);

    }

    // ---- CREAR JUGADOR ----
    public static void crearJugador() {
        String nombre;
        do {
            System.out.print("Ingrese nombre (único): ");
            nombre = in.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
            } else if (s.buscarJugador(nombre) != null) {
                System.out.println("El nombre ya está en uso, por favor ingrese otro.");
            } else {
                break;
            }
        } while (true);

        int edad;
        while (true) {
            System.out.print("Ingrese edad: ");
            String entrada = in.nextLine().trim();
            if (entrada.matches("\\d+")) {
                edad = Integer.parseInt(entrada);
                break;
            } else {
                System.out.println("Por favor ingrese una edad válida.");
            }
        }

        Jugador j = new Jugador(nombre, edad);
        s.getListaJugadores().add(j);
        System.out.println("Jugador " + nombre + " agregado correctamente!");
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
                if (!esJugadaValidaFormado(mov)) {
                    System.out.println("Movimiento inválido: " + mov);
                    continue;
                }

                int fila = mov.charAt(0) - 'A';
                int columna = mov.charAt(1) - '1';
                char accion = mov.charAt(2);
                String fichaPropia = (turno == j1) ? "B" : "N";

                String resultado = (accion == 'I')
                        ? t.invertir(fila, columna, fichaPropia)
                        : t.colocar(fila, columna, fichaPropia + accion);

                if (!esMensajeExito(resultado)) {
                    System.out.println("No se pudo realizar el movimiento: " + mov + " -> " + resultado);
                }

                turno = (turno == j1) ? j2 : j1;
            }
        }

        System.out.println("Secuencia cargada. Continuar partida...");
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
                case "B" -> conTitulos = true;
                case "N" -> conTitulos = false;
                case "T" -> {
                    System.out.print("Confirmar empate? S/N: ");
                    if (in.nextLine().trim().equalsIgnoreCase("S")) {
                        blanco.sumarEmpate();
                        negro.sumarEmpate();
                        System.out.println("Partida terminada en empate.");
                        partidaActiva = false;
                    }
                }
                case "H" -> mostrarJugadasGanadorasPosibles(t, turno, blanco);
                default -> {
                    if (!esJugadaValidaFormado(entrada)) {
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
                if (idx >= 0 && idx < size) return idx;
            }
            System.out.print("Número inválido, reintente: ");
        }
    }

    private static boolean esJugadaValidaFormado(String jugada) {
        if (jugada == null || jugada.length() != 3) return false;
        char f = jugada.charAt(0);
        char c = jugada.charAt(1);
        char acc = jugada.charAt(2);
        return f >= 'A' && f <= 'C' && c >= '1' && c <= '6' && (acc == 'C' || acc == 'D' || acc == 'I');
    }

    // Comprueba si el mensaje devuelto por Tablero indica éxito
    private static boolean esMensajeExito(String msg) {
        if (msg == null) return false;
        String m = msg.toLowerCase();
        return m.contains("correctamente") || m.contains("colocada") || m.contains("invertida");
    }

    private static void mostrarJugadasGanadorasPosibles(Tablero t, Jugador turno, Jugador blanco) {
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

    // ---- DATOS DE PRUEBA ----
    public static void cargarDatosPrueba() {
        s.getListaJugadores().clear();

        Jugador j1 = new Jugador("Lo", 18);
        j1.setPartidasGanadas(3);
        j1.setPartidasPerdidas(1);

        Jugador j2 = new Jugador("Santi", 17);

        Jugador j3 = new Jugador("Jota", 19);
        j3.setPartidasGanadas(2);
        j3.setPartidasEmpatadas(1);

        Jugador j4 = new Jugador("Maria", 18);
        j4.setPartidasGanadas(1);
        j4.setPartidasPerdidas(2);

        Jugador j5 = new Jugador("Pedro", 18);
        j5.setPartidasEmpatadas(2);

        s.getListaJugadores().add(j1);
        s.getListaJugadores().add(j2);
        s.getListaJugadores().add(j3);
        s.getListaJugadores().add(j4);
        s.getListaJugadores().add(j5);

        System.out.println("Jugadores de prueba cargados correctamente.");
    }

    // ---- DEMO TABLERO ----
    public static void mostrarEjemploTablero() {
        System.out.println("Ejemplo de tablero cargado con piezas:");
        Tablero t = new Tablero();

        // Piezas blancas
        System.out.println(t.colocar(0, 0, "BC"));
        System.out.println(t.colocar(0, 3, "BD"));
        System.out.println(t.colocar(1, 1, "BC"));
        System.out.println(t.colocar(1, 5, "BD"));
        System.out.println(t.colocar(2, 2, "BC"));
        System.out.println(t.colocar(2, 4, "BD"));

        // Piezas negras
        System.out.println(t.colocar(0, 2, "ND"));
        System.out.println(t.colocar(0, 5, "NC"));
        System.out.println(t.colocar(1, 0, "ND"));
        System.out.println(t.colocar(1, 4, "NC"));
        System.out.println(t.colocar(2, 1, "ND"));
        System.out.println(t.colocar(2, 3, "NC"));

        System.out.println(t.dibujar(true));
        System.out.println("--------------------------------------");
    }
}
