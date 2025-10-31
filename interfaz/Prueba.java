// Juan Pablo Curbelo (347159) Juan Ignacio Podestà (358049)
package interfaz;

import logica.Juego;
import logica.Jugador;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import logica.Sistema;



public class Prueba {

    public static Sistema s = new Sistema();
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        // Forzar UTF-8
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
            System.out.print("Opción: ");
            String entrada = in.nextLine().trim();
            try {
                op = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                op = -99; // entrada inválida
            }
            switch (op) {
                case 1:
                    Jugador.crearJugador();
                    break;
                case 2:
                    Juego.iniciarPartida();
                    break;
                case 3:
                    Juego.continuarPartida();
                    break;
                case 4:
                    String[] lista = s.listarJugadoresPorPartidas();
                    System.out.println(s.obtenerListaFormateada(lista, "Ranking de jugadores"));
                    break;
                case 5:
                    String[] invictos = s.listarJugadoresInvictos();
                    System.out.println(s.obtenerListaFormateada(invictos, "Jugadores invictos"));
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
    // FIN DEL MAIN
   
}
