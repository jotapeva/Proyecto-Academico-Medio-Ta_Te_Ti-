# Juego de Medias Fichas — Tateti Extendido

Este proyecto implementa un juego de tablero 3x6 en **Java**, inspirado en el clásico *Tateti* (Tres en línea), pero con una mecánica innovadora basada en **medias fichas** que pueden combinarse, invertirse y formar patrones ganadores.

---

## Descripción General

Cada casilla del tablero puede contener una **media ficha**:
- `BD` → Blanco Derecha  
- `BC` → Blanco Centro  
- `ND` → Negro Derecha  
- `NC` → Negro Centro  

Al combinar dos medias fichas adyacentes (por ejemplo, `BD` + `BC`), se forma una ficha completa, que puede representar una **X (jugador blanco)** o una **O (jugador negro)**, dependiendo de su orientación.

El tablero se representa visualmente como un grid de **3 filas × 6 columnas**, pero el análisis lógico del ganador se realiza sobre un tablero virtual de **3 × 5** celdas, donde se detectan las combinaciones válidas.

---

## Objetivo del Juego

- **Jugador Blanco (Jota):** busca formar **tres “X”** alineadas.
- **Jugador Negro (Lo):** busca formar **tres “O”** alineadas.

Se puede ganar de forma:
- Horizontal  
- Vertical  
- Diagonal (ascendente o descendente)

Cuando se detecta una victoria, las celdas ganadoras se **rellenan completamente** con el símbolo (`X` u `O`) para destacarlas visualmente en el tablero.

---

## Funcionalidades Principales

- Colocación de medias fichas con validación de posición.
- Inversión de fichas propias (`BD` ↔ `BC` o `ND` ↔ `NC`).
- Detección automática de ganador (horizontal, vertical y diagonal).
- Renderizado visual del tablero con caracteres ASCII.
- Destacado de la línea ganadora.
- Sistema de turnos entre jugadores.

---

## 🧠 Lógica de Verificación

1. Se genera un **tablero lógico** de 3×5 que representa las posibles fichas completas formadas por dos medias fichas consecutivas.
2. Se analiza si hay 3 símbolos iguales (`X` u `O`) consecutivos en filas, columnas o diagonales.
3. En caso de victoria:
   - Se almacenan las coordenadas ganadoras.
   - El método `dibujar()` reemplaza las celdas por el símbolo correspondiente.
