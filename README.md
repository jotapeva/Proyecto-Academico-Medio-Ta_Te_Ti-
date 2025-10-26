# 🧠 Medio Tateti (Java)

**Autores:** *[Agrega tus nombres y números]*  
**Lenguaje:** Java  
**Tipo de proyecto:** Consola interactiva  

---

## 🎯 Introducción

Este proyecto implementa el juego **“Medio Tateti”**, una variación del clásico Ta-Te-Ti (Tres en línea).  
Es un juego para **dos jugadores** que se alternan turnos con el objetivo de formar **3 letras iguales (O o X)** alineadas **en cualquier color**.

- El **jugador Blanco** debe formar tres **“O”**.  
- El **jugador Negro** debe formar tres **“X”**.  
- El tablero es de **3 filas (A, B, C)** por **6 columnas (1–6)**.  
- Cada pieza tiene orientación:  
  - **C** (↘) o **D** (↙)  
- Se puede colocar o invertir fichas propias, y también solicitar ayuda, mostrar títulos, o rendirse.

---

## 🎮 Reglas del Juego

- Comienza el **jugador Blanco**.
- Cada turno, el jugador puede:
  - Colocar una ficha libre: `A3C`, `B2D`, etc.
  - Invertir una ficha propia: `A2I`
  - Mostrar títulos: `B`
  - Ocultar títulos: `N`
  - Solicitar ayuda (jugada ganadora): `H`
  - Terminar de mutuo acuerdo: `T`
  - Rendirse (pierde): `X`

- Gana quien forme **tres letras iguales alineadas** (horizontal, vertical o diagonal).
- Si se forman ambas (X y O), gana **la letra correspondiente al jugador que realizó la jugada**.

---

## 🎨 Funcionamiento Visual (Representación en Consola)

El tablero se dibuja en texto usando caracteres ASCII.  
Cada celda tiene tres líneas de alto para mostrar la orientación de las fichas.

**Símbolos usados:**
- Blanco: `○`
- Negro: `●`

---

### Ejemplo de tablero común

```
    1  2  3  4  5  6
   +--+--+--+--+--+--+
A  |○ |● |○ |● |  |  |
   |○ |● |○ |● |  |  |
   +--+--+--+--+--+--+
B  |  |○ |● |○ |● |  |
   |  |○ |● |○ |● |  |
   +--+--+--+--+--+--+
C  |  |  |○ |● |○ |● |
   |  |  |○ |● |○ |● |
   +--+--+--+--+--+--+
```

---

### Si se detecta un ganador

```
    1  2  3  4  5  6
   +--+--+--+--+--+--+
A  | X| X| X|  |  |  |
   +--+--+--+--+--+--+
B  |  |  |  |  |  |  |
C  |  |  |  |  |  |  |
   +--+--+--+--+--+--+
```

Las celdas ganadoras se **rellenan completamente con la letra ganadora (X u O)** para destacar la alineación.

---

## ⚙️ Estructura del Código

```
src/
└── clases/
    ├── Tablero.java    ← Lógica central del juego (tablero, fichas, ganador)
    ├── Jugador.java    ← Datos de cada jugador (nombre, edad, partidas)
    ├── Sistema.java    ← Menú principal, ranking e interacción de consola
    └── Main.java       ← Punto de entrada (main)
```

---

## 🧩 Principales Métodos del Tablero

| Método              | Descripción |
|---------------------|-------------|
| `colocar()`         | Coloca una ficha si la posición está libre |
| `invertir()`        | Cambia orientación de una ficha del jugador actual |
| `dibujar()`         | Dibuja el tablero (con o sin títulos) |
| `verificarGanador()`| Analiza las letras formadas y retorna si hay ganador |
| `clonarTablero()`   | Duplica el estado actual del tablero |
| `estaLibre()`       | Indica si una posición está disponible |

---

## 🏆 Ranking e Invictos

El sistema mantiene estadísticas de cada jugador:
- **Ranking:** Ordenado por cantidad de partidas ganadas.
- **Invictos:** Jugadores que nunca perdieron o nunca jugaron (orden alfabético).

---

## 🧠 Detalles Técnicos

- Se utiliza **UTF-8** para mostrar correctamente los símbolos `○` y `●`.  
  Se recomienda usar la fuente **Courier New** en la consola.  

```java
System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
```

- Insensible a mayúsculas/minúsculas en los comandos.

---

## 💡 Ejemplo de jugadas de prueba

```
A1C  B2D  C3C  A2I  B4C  X
```

Esto mostrará el tablero paso a paso, hasta que un jugador se rinda (`X`).

---

## 🧰 Requisitos

- Java 17 o superior.
- Consola que soporte UTF-8.
- Compilar con:
  ```bash
  javac src/clases/*.java -encoding UTF-8
  ```
- Ejecutar con:
  ```bash
  java -cp src clases.Main
  ```

---

## 📜 Créditos

Trabajo desarrollado por:  
**[Tu Nombre] – [Número de Estudiante]**

Facultad de Ingeniería – Universidad ORT Uruguay  
Montevideo, 2025

---

> 💬 *“Medio Tateti: más estrategia, menos suerte.”*
