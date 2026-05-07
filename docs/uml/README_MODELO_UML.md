# Diagrama UML - Modelo del Juego

## Descripción General

Este diagrama UML representa la arquitectura del modelo del juego de exploración por habitaciones. Muestra las 6 nuevas clases que necesitan ser implementadas y sus relaciones con las clases existentes.

## Archivos Generados

- **modelo_juego.png** - Diagrama en formato PNG (285 KB)
- **modelo_juego.svg** - Diagrama en formato SVG (28 KB, recomendado para web)
- **modelo_juego.dot** - Fuente en formato Graphviz DOT
- **modelo_juego.puml** - Fuente en formato PlantUML

## Nuevas Clases a Implementar

### 1. **TipoCelda** (Enum)
```
Valores: VACIA, PUERTA, TRAMPA, PARED
```
Define el tipo de cada celda en la matriz de la habitación.

---

### 2. **Celda**
**Responsabilidad**: Representar una posición en la matriz de una habitación.

**Atributos**:
- `fila: int` - Posición fila
- `columna: int` - Posición columna
- `entidad: Entidad` - Enemigo o null
- `objeto: Objeto` - Arma, Poción, Llave o null
- `tipo: TipoCelda` - Tipo de celda

**Métodos principales** (14):
- Constructores: `Celda(fila, columna)`, `Celda(fila, columna, tipo)`
- Getters/Setters para todos los atributos
- Métodos booleanos: `estaVacia()`, `tienePuerta()`, `tieneTrampa()`
- `limpiar()` - Vacía entidad y objeto

**Complejidad**: O(1) para todas las operaciones

---

### 3. **MatrizHabitacion**
**Responsabilidad**: Representar la cuadrícula 2D de una habitación usando `LSE<LSE<Celda>>`.

**Atributos**:
- `filas: LSE<LSE<Celda>>` - Matriz 2D como lista de listas
- `numFilas: int` - Número de filas
- `numColumnas: int` - Número de columnas

**Métodos principales** (11):
- Constructores: `MatrizHabitacion(numFilas, numColumnas)`
- `getCelda(fila, columna): Celda` - O(n+m) con acceso secuencial
- `setCelda(fila, columna, celda): void`
- `obtenerCeldasAlcanzables(fila, col, rango): LSE<Celda>` - BFS desde posición
- Filtros: `obtenerCeldasConEnemigos()`, `obtenerCeldasConObjetos()`, `obtenerCeldasConPuertas()`
- `limpiar()` - Vacía toda la matriz
- `iterarFilas()` - Iterador sobre filas

**Complejidad**:
- Acceso: O(n+m) (búsqueda secuencial en LSE)
- Iteración completa: O(n*m)
- BFS: O(n*m)

**Nota**: Para habitaciones típicas (10x10 a 20x20), O(n+m) es aceptable.

---

### 4. **Puerta**
**Responsabilidad**: Representar una puerta que conecta dos habitaciones.

**Atributos**:
- `habitacionDestino: int` - ID de la habitación destino
- `etiqueta: String` - Descripción (ej: "Puerta Norte")
- `abierta: boolean` - Estado de la puerta

**Métodos principales** (5):
- Constructor: `Puerta(habitacionDestino, etiqueta)`
- Getters: `getHabitacionDestino()`, `getEtiqueta()`, `estaAbierta()`
- `abrir()`, `cerrar()` - Cambiar estado

**Complejidad**: O(1) para todas las operaciones

---

### 5. **Habitacion**
**Responsabilidad**: Encapsular una habitación con su matriz, metadatos y puertas.

**Atributos**:
- `id: int` - Identificador único
- `nombre: String` - Nombre descriptivo
- `matriz: MatrizHabitacion` - Cuadrícula 2D
- `puertas: LSE<Puerta>` - Puertas de salida (típicamente 2-4)
- `turnsLimit: int` - Límite de turnos (opcional)
- `esExterior: boolean` - ¿Es la salida final?
- `visitada: boolean` - Control de exploración

**Métodos principales** (18):
- Constructor: `Habitacion(id, nombre, numFilas, numColumnas)`
- Getters/Setters para todos los atributos
- `agregarPuerta(Puerta)`, `obtenerPuerta(destino)`
- `marcarVisitada()`, `estaVisitada()`
- `obtenerEnemigos(): LSE<Enemigo>` - Filtra enemigos de la matriz
- `obtenerObjetos(): LSE<Objeto>` - Filtra objetos de la matriz
- `limpiar()` - Limpia matriz y puertas

**Complejidad**:
- Acceso a propiedades: O(1)
- Búsqueda de puerta: O(k) donde k = número de puertas (típicamente 2-4)
- Filtrado de enemigos/objetos: O(n*m)

---

### 6. **GrafoHabitaciones**
**Responsabilidad**: Gestionar el grafo de habitaciones y proporcionar algoritmos de navegación.

**Atributos**:
- `grafo: Grafo` - Grafo existente (composición)
- `habitaciones: LSE<Habitacion>` - Lista de todas las habitaciones
- `habitacionActual: Habitacion` - Habitación donde está el jugador

**Métodos principales** (12):
- Constructor: `GrafoHabitaciones()`
- `agregarHabitacion(Habitacion)` - Añade nodo al grafo
- `conectarHabitaciones(h1, h2, etiqueta)` - Crea arista entre habitaciones
- `obtenerHabitacion(id): Habitacion` - Búsqueda O(n)
- `obtenerHabitacionActual()`, `cambiarHabitacion(id)`
- **`caminoMinimoHabitaciones(origen, destino): LSE<Habitacion>`** - BFS (O(V+E))
- **`dijkstraHabitaciones(origen, destino): LSE<Habitacion>`** - Dijkstra (O((V+E)logV))
- `obtenerHabitacionesAdyacentes(id): LSE<Habitacion>` - Vecinos del nodo
- `cargarDesdeJson(ruta)`, `guardarEnJson(ruta)` - Persistencia
- `obtenerTodasHabitaciones(): LSE<Habitacion>`

**Complejidad**:
- BFS: O(V+E) donde V = habitaciones, E = puertas
- Dijkstra: O((V+E)logV)
- Típicamente: V ≈ 5-20, E ≈ 2-4 por habitación

**Nota**: Reutiliza completamente la clase `Grafo` existente.

---

## Relaciones Entre Clases

### Composición (contiene)
- `Celda` contiene `Entidad` y `Objeto`
- `MatrizHabitacion` contiene `Celda` (LSE[LSE[Celda]])
- `Habitacion` contiene `MatrizHabitacion` y `Puerta` (LSE[Puerta])
- `GrafoHabitaciones` contiene `Habitacion` (LSE[Habitacion])

### Herencia (es-un)
- `Enemigo` extiende `Entidad`
- `Jugador` extiende `Entidad`
- `Arma`, `Pocion`, `Llave` extienden `Objeto`

### Uso (utiliza)
- `Celda` utiliza `TipoCelda`
- `GrafoHabitaciones` utiliza `Grafo` (composición)
- `Puerta` conecta a `Habitacion`
- `Enemigo` persigue a `Jugador`

---

## Tabla Resumen de Métodos

| Clase | Métodos | Complejidad | Prioridad |
|-------|---------|-------------|-----------|
| **TipoCelda** | 4 valores | N/A | ⭐⭐⭐ Trivial |
| **Celda** | 14 | O(1) | ⭐⭐⭐ Base |
| **MatrizHabitacion** | 11 | O(n+m) acceso | ⭐⭐⭐ Crítica |
| **Puerta** | 5 | O(1) | ⭐⭐ Simple |
| **Habitacion** | 18 | O(1-n*m) | ⭐⭐⭐ Crítica |
| **GrafoHabitaciones** | 12 | O(V+E) BFS | ⭐⭐⭐ Crítica |

---

## Orden de Implementación Recomendado

```
1️⃣  TipoCelda (enum)              ← Empezar aquí (trivial, sin dependencias)
2️⃣  Celda                         ← Base para MatrizHabitacion
3️⃣  MatrizHabitacion              ← Usa Celda
4️⃣  Puerta                        ← Simple, independiente
5️⃣  Habitacion                    ← Usa MatrizHabitacion + Puerta
6️⃣  GrafoHabitaciones             ← Usa Habitacion + Grafo existente
```

---

## Restricciones Críticas

✅ **NO usar java.util.*** en ninguna clase  
✅ **Usar solo estructuras propias**: LSE, Grafo, etc.  
✅ **Cada clase debe tener tests JUnit**  
✅ **Javadoc en todos los métodos públicos**  
✅ **Separación MVC**: Estas clases son modelo puro (sin JavaFX)

---

## Notas de Implementación

### MatrizHabitacion
- Implementar con `LSE<LSE<Celda>>` para cumplir restricción
- Considerar cache de fila actual para optimización futura
- Método `obtenerCeldasAlcanzables()` debe usar BFS

### GrafoHabitaciones
- Adaptar `Grafo` existente sin modificarlo
- Usar composición: `private Grafo grafo;`
- Métodos de camino mínimo delegan a `grafo.caminoMinimo()` y `grafo.dijkstra()`
- Convertir resultados de `LSE<Nodo>` a `LSE<Habitacion>`

### Persistencia
- Cargar/guardar desde JSON con formato de tripletas RDF
- Usar `GestorJSON` existente si está disponible

---

## Archivos Relacionados

- Estructuras de datos: `/workspace/proyecto-final/src/main/java/es/universidad/juego/model/estructuras/src/`
- Clases existentes: `/workspace/proyecto-final/src/main/java/es/universidad/juego/model/`
- Tests: `/workspace/proyecto-final/src/test/java/es/universidad/juego/`

---

**Generado**: 2026-05-07  
**Herramienta**: Graphviz DOT  
**Versión**: 1.0
