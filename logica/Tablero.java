// Juan Pablo Curbelo (345971) Juan Ignacio Podestà (358049)
package logica;

import java.util.ArrayList;

public class Tablero {

    private String[][] tablero = new String[3][6]; // "", "BD","BC","ND","NC"
    private ArrayList<int[]> celdasGanadoras = new ArrayList<>();
    private char ganador = ' ';
    private int filas = 3;
    private int columnas = 6;
    
    public Tablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = "";
            }
        }
    }

    public String[][] getTablero() {
        return tablero;
    }

    public void setTablero(String[][] nuevoTablero) {
        for (int i = 0; i < filas; i++) {
            System.arraycopy(nuevoTablero[i], 0, this.tablero[i], 0, columnas);
        }
    }

    public String[][] clonarTablero() {
        String[][] copia = new String[filas][columnas];
        for (int i = 0; i < filas; i++) {
            System.arraycopy(this.tablero[i], 0, copia[i], 0, columnas);
        }
        return copia;
    }

    public boolean estaLibre(int fila, int columna) {
        return tablero[fila][columna].isEmpty();
    }

    public String colocar(int fila, int columna, String ficha) {
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas)
            return "Posición fuera del tablero.";
        if (!estaLibre(fila, columna))
            return "Esa celda ya está ocupada.";
        if (!(ficha.equals("BD") || ficha.equals("BC") || ficha.equals("ND") || ficha.equals("NC")))
            return "Ficha no válida.";

        tablero[fila][columna] = ficha;
        return "Ficha colocada correctamente.";
    }

    public String invertir(int fila, int columna, String fichaPropia) {
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas)
            return "Posición fuera del tablero.";
        String celda = tablero[fila][columna];
        if (celda.isEmpty()) return "No hay ficha en esa posición.";
        if (!celda.startsWith(fichaPropia.substring(0, 1)))
            return "Esa ficha no pertenece al jugador actual.";

        tablero[fila][columna] = switch (celda) {
            case "BD" -> "BC";
            case "BC" -> "BD";
            case "ND" -> "NC";
            case "NC" -> "ND";
            default -> celda;
        };
        return "Ficha invertida correctamente.";
    }

    // ----------------- DIBUJAR -----------------
    public String dibujar(boolean conTitulos) {
        StringBuilder sb = new StringBuilder();
        int filasCelda = 3;

        if (conTitulos) {
            sb.append("   ");
            for (int j = 1; j <= columnas; j++) sb.append(" ").append(j).append(" ");
            sb.append("\n");
        }

        for (int i = 0; i < filas; i++) {
            // Separador superior
            sb.append("  ");
            for (int j = 0; j < columnas; j++) sb.append("+--");
            sb.append("+\n");

            for (int filaCelda = 0; filaCelda < filasCelda; filaCelda++) {
                if (conTitulos) sb.append(filaCelda == 1 ? (char) ('A' + i) + " " : "  ");
                else sb.append("  ");

                for (int j = 0; j < columnas; j++) {
                    boolean esGanadora = false;
                    for (int[] pos : celdasGanadoras) {
                        if (pos[0] == i && pos[1] == j) {
                            esGanadora = true;
                            break;
                        }
                    }

                    if (esGanadora && ganador != ' ') {
                        // si es celda ganadora, llenamos toda la celda con el símbolo del ganador
                        sb.append("|").append(ganador).append(ganador);
                    } else {
                        sb.append("|").append(getLineaCelda(tablero[i][j], filaCelda));
                    }
                }
                sb.append("|\n");
            }
        }

        // Separador inferior
        sb.append("  ");
        for (int j = 0; j < columnas; j++) sb.append("+--");
        sb.append("+\n");

        return sb.toString();
    }

    private String getLineaCelda(String celda, int linea) {
        switch (celda) {
            case "BD": return linea == 1 ? " ○" : "○ ";
            case "BC": return linea == 1 ? "○ " : " ○";
            case "ND": return linea == 1 ? " ●" : "● ";
            case "NC": return linea == 1 ? "● " : " ●";
            default: return "  ";
        }
    }

    // ----------------- VERIFICAR GANADOR -----------------
    public char verificarGanador() {
        celdasGanadoras.clear();
        ganador = ' ';

        char[][] logico = new char[3][5];

        // construir el tablero lógico según las combinaciones BD/BC y ND/NC
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                String f1 = tablero[i][j];
                String f2 = tablero[i][j + 1];
                if (f1 == null) f1 = "";
                if (f2 == null) f2 = "";

                char p1 = f1.length() >= 2 ? f1.charAt(1) : ' ';
                char p2 = f2.length() >= 2 ? f2.charAt(1) : ' ';

                if (p1 == 'D' && p2 == 'C')
                    logico[i][j] = 'X'; // blanco
                else if (p1 == 'C' && p2 == 'D')
                    logico[i][j] = 'O'; // negro
                else
                    logico[i][j] = ' ';
            }
        }

        // ---- Verificar horizontales ----
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <= 2; j++) {
                char c = logico[i][j];
                if (c != ' ' && c == logico[i][j + 1] && c == logico[i][j + 2]) {
                    ganador = c;
                    for (int k = 0; k < 3; k++) {
                        celdasGanadoras.add(new int[]{i, j + k});
                        celdasGanadoras.add(new int[]{i, j + k + 1});
                    }
                    return ganador;
                }
            }
        }
        // ---- Verificar verticales ----
        for (int j = 0; j < 5; j++) {
            char c = logico[0][j];
            if (c != ' ' && c == logico[1][j] && c == logico[2][j]) {
                ganador = c;
                for (int k = 0; k < 3; k++) {
                    celdasGanadoras.add(new int[]{k, j});
                    celdasGanadoras.add(new int[]{k, j + 1});
                }
                return ganador;
            }
        }
        // ---- Verificar diagonales ↘ ----
        for (int j = 0; j <= 2; j++) {
            char c = logico[0][j];
            if (c != ' ' && c == logico[1][j + 1] && c == logico[2][j + 2]) {
                ganador = c;
                for (int k = 0; k < 3; k++) {
                    celdasGanadoras.add(new int[]{k, j + k});
                    celdasGanadoras.add(new int[]{k, j + k + 1});
                }
                return ganador;
            }
        }
        // ---- Verificar diagonales ↗ ----
        for (int j = 0; j <= 2; j++) {
            char c = logico[2][j];
            if (c != ' ' && c == logico[1][j + 1] && c == logico[0][j + 2]) {
                ganador = c;
                celdasGanadoras.add(new int[]{2, j});
                celdasGanadoras.add(new int[]{2, j + 1});
                celdasGanadoras.add(new int[]{1, j + 1});
                celdasGanadoras.add(new int[]{1, j + 2});
                celdasGanadoras.add(new int[]{0, j + 2});
                celdasGanadoras.add(new int[]{0, j + 3});
                return ganador;
            }
        }
        return ' ';
    }
}
