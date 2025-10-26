package clases;
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
