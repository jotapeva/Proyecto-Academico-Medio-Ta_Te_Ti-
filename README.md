# 🧠 Medio Tateti — Juego de Estrategia en Java

> **Proyecto académico desarrollado en Java** para simular el juego “Medio Tateti”, combinando lógica, análisis de patrones y representación visual ASCII en consola.

---

## 🎯 Introducción

“**Medio Tateti**” es una variante del clásico *Tres en Línea*, diseñada para dos jugadores.  
A diferencia del Tateti tradicional, aquí cada jugador coloca **medias fichas**, que pueden combinarse para formar letras “X” u “O”, dependiendo de su orientación.

- El **jugador Blanco** busca formar **3 letras “O”** alineadas.
- El **jugador Negro** busca formar **3 letras “X”** alineadas.
- Gana quien logre su figura antes.
- Si ambos forman su figura en el mismo turno, gana el jugador del turno actual.

---

## 📏 Reglas del Juego

### 🧩 Tablero
- El tablero es una **matriz de 3 filas × 6 columnas**.
- Las filas se nombran **A, B y C**.
- Las columnas se numeran **1 a 6**.

### 🎲 Piezas
Cada pieza tiene dos atributos:
- **Color:** Blanco (B) o Negro (N)
- **Orientación:** “C” o “D”

Las posibles piezas son:
| Jugador | Orientación C | Orientación D |
|----------|----------------|----------------|
| Blanco | `BC` | `BD` |
| Negro | `NC` | `ND` |

---

## 🧱 Formaciones de Letras

Cada letra se forma con **2 medias fichas adyacentes horizontalmente**:

| Letra | Formación | Representa |
|--------|-------------|-------------|
| X | `D` + `C` | Jugador **Negro** |
| O | `C` + `D` | Jugador **Blanco** |

El color no importa para la victoria — solo la **combinación de orientaciones**.

---

## 🔁 Turnos y Jugadas

- Comienza siempre el **jugador Blanco**.
- En su turno, cada jugador puede:
  1. **Colocar** una ficha indicando la fila, columna y orientación → `A3C`, `B2D`.
  2. **Invertir** una ficha propia → `A3I` (invierte su orientación “C” ↔ “D”).

Jugadas especiales:
| Comando | Acción |
|----------|---------|
| `X` | Finaliza el juego y pierde el jugador actual |
| `T` | Solicita terminar de mutuo acuerdo (empate, requiere confirmación) |
| `H` | Solicita ayuda: el sistema indica si existe una jugada ganadora |
| `B` | Muestra el tablero con títulos (filas y columnas) |
| `N` | Muestra el tablero sin títulos |

Si una jugada es inválida, debe reingresarse.  
No se puede pasar turno.

---

## 🧮 Funcionamiento Interno (Lógica del Juego)

Internamente, el tablero real (`3x6`) se traduce a un **tablero lógico de 3x5**, donde cada posición representa una posible letra completa (formada por dos fichas consecutivas).

Ejemplo de conversión:

Tablero físico (3x6): Tablero lógico (3x5):
BD BC ND NC BD NC X O X O X

markdown
Copiar código

Luego, el sistema analiza todas las posibles **alineaciones de 3 letras iguales**:
- Horizontales
- Verticales
- Diagonales (↘ y ↗)

Si se detecta una secuencia ganadora:
1. Se identifica la letra ganadora (`X` u `O`).
2. Se marcan las coordenadas ganadoras.
3. El método `dibujar()` reemplaza las fichas visualmente por la letra ganadora.

---

## 🎨 Funcionamiento Visual (Representación en Consola)

El tablero se dibuja en texto usando caracteres ASCII.  
Cada celda tiene tres líneas de alto para mostrar la orientación de las fichas.

Símbolos usados:
- **Blanco:** `○`
- **Negro:** `●`

### Ejemplo visual:
1 2 3 4 5 6
+--+--+--+--+--+--+
A |○ |● |○ |● | | |
| ○|● | ○|● | | |
|○ |● |○ |● | | |
+--+--+--+--+--+--+
B | |○ |● |○ |● | |
| | ○|● | ○|● | |
| |○ |● |○ |● | |
+--+--+--+--+--+--+
C | | |○ |● |○ |● |
| | | ○|● | ○|● |
| | |○ |● |○ |● |
+--+--+--+--+--+--+

yaml
Copiar código

Si se detecta un ganador:
1 2 3 4 5 6
+--+--+--+--+--+--+
A | X| X| X| | | |
B | | | | | | |
C | | | | | | |
+--+--+--+--+--+--+

yaml
Copiar código

---

## ⚙️ Estructura del Código

src/
└── clases/
├── Tablero.java ← Lógica central del juego
├── Jugador.java ← Datos del jugador (nombre, edad, partidas)
├── Sistema.java ← Menú, control de flujo y ranking
└── Main.java ← Punto de entrada

yaml
Copiar código

### 🧩 Principales métodos del `Tablero`
| Método | Descripción |
|---------|-------------|
| `colocar()` | Coloca una ficha si la posición está libre |
| `invertir()` | Cambia orientación de una ficha del jugador actual |
| `dibujar()` | Dibuja el tablero con o sin títulos |
| `verificarGanador()` | Analiza el tablero lógico y retorna el ganador |
| `clonarTablero()` | Duplica el tablero actual |
| `estaLibre()` | Indica si una posición está disponible |

---

## 🧑‍💻 Menú del Programa

Al iniciar, se muestra:

Trabajo desarrollado por: [NOMBRES Y NÚMEROS DE LOS AUTORES]

a) Registrar un jugador
b) Comienzo de partida común
c) Continuación de partida
d) Mostrar ranking e invictos
e) Terminar el programa

markdown
Copiar código

### a) Registrar un jugador
- Se solicita nombre (único) y edad.  
- Se almacena en una lista ordenada alfabéticamente.

### b) Comienzo de partida común
- Se eligen dos jugadores de la lista.  
- Se alternan los turnos Blanco/Negro.  
- El programa controla jugadas, ayuda (`H`), fin (`X` o `T`) y muestra el tablero tras cada turno.

### c) Continuación de partida
- Permite ingresar una **secuencia de movimientos** (`A1C B3D C2I ...`)  
- Se ejecutan automáticamente y continúa la partida desde ese estado.

### d) Ranking e Invictos
- Muestra el **ranking de jugadores** ordenado por partidas ganadas.  
- Lista los **invictos** (nunca jugaron o nunca perdieron).

### e) Terminar
- Finaliza el programa.

---

## 💡 Consideraciones Técnicas

- Se usa salida UTF-8 para representar correctamente los símbolos `○` y `●`:
  ```java
  System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
Fuente recomendada en consola: Courier New

Entrada insensible a mayúsculas/minúsculas.

Todas las validaciones de jugadas se realizan antes de aplicarlas.

🧑‍🎓 Autores
Juan Pablo Curbelo
📍 Montevideo, Uruguay
💻 Facultad de Ingeniería - Universidad ORT Uruguay
✉️ [Agregar email si querés]
