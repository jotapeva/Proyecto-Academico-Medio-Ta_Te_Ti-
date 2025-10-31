// Juan Pablo Curbelo (347159) Juan Ignacio Podestà (358049)
package logica;

import static interfaz.Prueba.in;
import static interfaz.Prueba.s;

public class Jugador {
    private String nombre;
    private int edad;
    private int partidasGanadas;
    private int partidasPerdidas;
    private int partidasEmpatadas;

    public Jugador(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
        this.partidasGanadas = 0;
        this.partidasPerdidas = 0;
        this.partidasEmpatadas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPartidasEmpatadas() {
        return partidasEmpatadas;
    }

    public void setPartidasEmpatadas(int partidasEmpatadas) {
        this.partidasEmpatadas = partidasEmpatadas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(short edad) {
        this.edad = edad;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }

    public void setPartidasPerdidas(int partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }
    
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

    public boolean esInvicto() {
        return partidasPerdidas == 0;
    }

    public void sumarGanada() {
        partidasGanadas++;
    }

    public void sumarPerdida() {
        partidasPerdidas++;
    }

    public void sumarEmpate() {
        partidasEmpatadas++;
    }

    @Override
    public String toString() {
        return nombre + " (" + edad + " años) - G:" + partidasGanadas + " P:" + partidasPerdidas;
    }

}
