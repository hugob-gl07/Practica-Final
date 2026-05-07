# Especificaciones Técnicas - Nuevas Clases del Modelo

## 1. TipoCelda (Enum)

```java
public enum TipoCelda {
    VACIA,      // Celda vacía, transitable
    PUERTA,     // Puerta a otra habitación
    TRAMPA,     // Trampa que daña al jugador
    PARED       // Pared, no transitable
}
```

**Uso**: Clasificar el tipo de cada celda en la matriz.

---

## 2. Celda

### Especificación Completa

**Paquete**: `es.universidad.juego.model.habitacion`

**Responsabilidad**: Representar una posición (fila, columna) en la matriz de una habitación.

### Atributos

| Atributo | Tipo | Visibilidad | Descripción |
|----------|------|-------------|-------------|
| `fila` | int | private | Posición fila (0-indexed) |
| `columna` | int | private | Posición columna (0-indexed) |
| `entidad` | Entidad | private | Enemigo o null |
| `objeto` | Objeto | private | Arma, Poción, Llave o null |
| `tipo` | TipoCelda | private | Tipo de celda |

### Constructores

```java
// Constructor 1: Celda vacía
public Celda(int fila, int columna)
    // Inicializa: entidad=null, objeto=null, tipo=VACIA

// Constructor 2: Celda con tipo específico
public Celda(int fila, int columna, TipoCelda tipo)
    // Inicializa: entidad=null, objeto=null, tipo=tipo
```

### Métodos Públicos

| Método | Parámetros | Retorno | Descripción |
|--------|-----------|---------|-------------|
| `getFila` | - | int | Obtiene fila |
| `getColumna` | - | int | Obtiene columna |
| `getEntidad` | - | Entidad | Obtiene entidad (null si vacía) |
| `setEntidad` | Entidad | void | Asigna entidad |
| `getObjeto` | - | Objeto | Obtiene objeto (null si no hay) |
| `setObjeto` | Objeto | void | Asigna objeto |
| `getTipo` | - | TipoCelda | Obtiene tipo |
| `setTipo` | TipoCelda | void | Asigna tipo |
| `estaVacia` | - | boolean | ¿Sin entidad ni objeto? |
| `tienePuerta` | - | boolean | ¿tipo == PUERTA? |
| `tieneTrampa` | - | boolean | ¿tipo == TRAMPA? |
| `limpiar` | - | void | Vacía entidad y objeto |

### Complejidad

- Todas las operaciones: **O(1)**

### Restricciones

- No usar `java.util.*`
- Entidad y Objeto pueden ser null

---

## 3. MatrizHabitacion

### Especificación Completa

**Paquete**: `es.universidad.juego.model.habitacion`

**Responsabilidad**: Representar la cuadrícula 2D de una habitación usando `LSE<LSE<Celda>>`.

### Atributos

| Atributo | Tipo | Visibilidad | Descripción |
|----------|------|-------------|-------------|
| `filas` | LSE<LSE<Celda>> | private | Matriz 2D como lista de listas |
| `numFilas` | int | private | Número de filas |
| `numColumnas` | int | private | Número de columnas |

### Constructores

```java
// Constructor: Crea matriz vacía de tamaño numFilas x numColumnas
public MatrizHabitacion(int numFilas, int numColumnas)
    // Inicializa LSE con numFilas filas
    // Cada fila es LSE<Celda> con numColumnas celdas VACIA
    // Complejidad: O(numFilas * numColumnas)
```

### Métodos Públicos

| Método | Parámetros | Retorno | Descripción | Complejidad |
|--------|-----------|---------|-------------|-------------|
| `getCelda` | fila, columna | Celda | Obtiene celda en (fila, columna) | O(n+m) |
| `setCelda` | fila, columna, celda | void | Asigna celda en (fila, columna) | O(n+m) |
| `getNumFilas` | - | int | Obtiene #filas | O(1) |
| `getNumColumnas` | - | int | Obtiene #columnas | O(1) |
| `obtenerCeldasAlcanzables` | fila, columna, rango | LSE<Celda> | BFS desde (fila,col) con rango | O(n*m) |
| `obtenerCeldasConEnemigos` | - | LSE<Celda> | Filtra celdas con enemigos | O(n*m) |
| `obtenerCeldasConObjetos` | - | LSE<Celda> | Filtra celdas con objetos | O(n*m) |
| `obtenerCeldasConPuertas` | - | LSE<Celda> | Filtra celdas con puertas | O(n*m) |
| `limpiar` | - | void | Vacía toda la matriz | O(n*m) |
| `iterarFilas` | - | Iterador | Itera sobre filas | O(1) |

### Métodos Privados (Helpers)

```java
// Helper: Obtiene la fila i-ésima
private LSE<Celda> obtenerFila(int i)
    // Complejidad: O(i)

// Helper: Valida índices
private void validarIndices(int fila, int columna)
    // Lanza excepción si fila < 0 || fila >= numFilas || columna < 0 || columna >= numColumnas
```

### Detalles de Implementación

#### `getCelda(fila, columna)`
```
1. Validar índices
2. Obtener fila i-ésima de filas (O(fila))
3. Obtener columna j-ésima de esa fila (O(columna))
4. Retornar celda
Complejidad total: O(fila + columna) ≈ O(n+m)
```

#### `obtenerCeldasAlcanzables(fila, columna, rango)`
```
1. Usar BFS desde (fila, columna)
2. Explorar celdas a distancia Manhattan <= rango
3. Retornar LSE<Celda> con celdas alcanzables
Complejidad: O(n*m) en peor caso (toda la matriz)
```

#### `obtenerCeldasConEnemigos()`
```
1. Iterar todas las filas
2. Para cada fila, iterar todas las celdas
3. Si celda.getEntidad() != null, añadir a resultado
4. Retornar LSE<Celda>
Complejidad: O(n*m)
```

### Excepciones

- `ListaIndiceInvalidoExceptions` - Si índices fuera de rango
- `ListaVaciaExceptions` - Si matriz vacía

### Restricciones

- No usar `java.util.*`
- Usar solo `LSE<T>` para almacenamiento
- Habitaciones típicamente 10x10 a 20x20

---

## 4. Puerta

### Especificación Completa

**Paquete**: `es.universidad.juego.model.habitacion`

**Responsabilidad**: Representar una puerta que conecta dos habitaciones.

### Atributos

| Atributo | Tipo | Visibilidad | Descripción |
|----------|------|-------------|-------------|
| `habitacionDestino` | int | private | ID de habitación destino |
| `etiqueta` | String | private | Descripción (ej: "Puerta Norte") |
| `abierta` | boolean | private | Estado de la puerta |

### Constructores

```java
// Constructor: Crea puerta cerrada
public Puerta(int habitacionDestino, String etiqueta)
    // Inicializa: abierta=false
```

### Métodos Públicos

| Método | Parámetros | Retorno | Descripción |
|--------|-----------|---------|-------------|
| `getHabitacionDestino` | - | int | Obtiene ID destino |
| `getEtiqueta` | - | String | Obtiene etiqueta |
| `estaAbierta` | - | boolean | ¿Puerta abierta? |
| `abrir` | - | void | Abre la puerta |
| `cerrar` | - | void | Cierra la puerta |

### Complejidad

- Todas las operaciones: **O(1)**

### Restricciones

- No usar `java.util.*`
- habitacionDestino debe ser válido (validar en GrafoHabitaciones)

---

## 5. Habitacion

### Especificación Completa

**Paquete**: `es.universidad.juego.model.habitacion`

**Responsabilidad**: Encapsular una habitación con su matriz, metadatos y puertas.

### Atributos

| Atributo | Tipo | Visibilidad | Descripción |
|----------|------|-------------|-------------|
| `id` | int | private | Identificador único |
| `nombre` | String | private | Nombre descriptivo |
| `matriz` | MatrizHabitacion | private | Cuadrícula 2D |
| `puertas` | LSE<Puerta> | private | Puertas de salida |
| `turnsLimit` | int | private | Límite de turnos (0 = sin límite) |
| `esExterior` | boolean | private | ¿Es la salida final? |
| `visitada` | boolean | private | ¿Fue visitada? |

### Constructores

```java
// Constructor: Crea habitación
public Habitacion(int id, String nombre, int numFilas, int numColumnas)
    // Inicializa:
    // - matriz = new MatrizHabitacion(numFilas, numColumnas)
    // - puertas = new LSE<Puerta>()
    // - turnsLimit = 0 (sin límite)
    // - esExterior = false
    // - visitada = false
```

### Métodos Públicos

| Método | Parámetros | Retorno | Descripción | Complejidad |
|--------|-----------|---------|-------------|-------------|
| `getId` | - | int | Obtiene ID | O(1) |
| `getNombre` | - | String | Obtiene nombre | O(1) |
| `getMatriz` | - | MatrizHabitacion | Obtiene matriz | O(1) |
| `getPuertas` | - | LSE<Puerta> | Obtiene puertas | O(1) |
| `agregarPuerta` | Puerta | void | Añade puerta | O(1) |
| `obtenerPuerta` | destino | Puerta | Busca puerta a destino | O(k) |
| `getTurnsLimit` | - | int | Obtiene límite turnos | O(1) |
| `setTurnsLimit` | int | void | Asigna límite turnos | O(1) |
| `esExterior` | - | boolean | ¿Es salida final? | O(1) |
| `setExterior` | boolean | void | Marca como exterior | O(1) |
| `estaVisitada` | - | boolean | ¿Fue visitada? | O(1) |
| `marcarVisitada` | - | void | Marca como visitada | O(1) |
| `obtenerEnemigos` | - | LSE<Enemigo> | Filtra enemigos | O(n*m) |
| `obtenerObjetos` | - | LSE<Objeto> | Filtra objetos | O(n*m) |
| `limpiar` | - | void | Limpia matriz y puertas | O(n*m) |

### Detalles de Implementación

#### `obtenerPuerta(destino)`
```
1. Iterar puertas (LSE<Puerta>)
2. Si puerta.getHabitacionDestino() == destino, retornar
3. Si no encontrada, retornar null
Complejidad: O(k) donde k = número de puertas (típicamente 2-4)
```

#### `obtenerEnemigos()`
```
1. Llamar matriz.obtenerCeldasConEnemigos()
2. Filtrar solo Enemigo (no Jugador)
3. Retornar LSE<Enemigo>
Complejidad: O(n*m)
```

### Excepciones

- `ListaVaciaExceptions` - Si no hay puertas

### Restricciones

- No usar `java.util.*`
- Usar solo `LSE<T>` para puertas

---

## 6. GrafoHabitaciones

### Especificación Completa

**Paquete**: `es.universidad.juego.model.mapa`

**Responsabilidad**: Gestionar el grafo de habitaciones y proporcionar algoritmos de navegación.

### Atributos

| Atributo | Tipo | Visibilidad | Descripción |
|----------|------|-------------|-------------|
| `grafo` | Grafo | private | Grafo existente (composición) |
| `habitaciones` | LSE<Habitacion> | private | Lista de todas las habitaciones |
| `habitacionActual` | Habitacion | private | Habitación donde está el jugador |

### Constructores

```java
// Constructor: Crea grafo vacío
public GrafoHabitaciones()
    // Inicializa:
    // - grafo = new Grafo()
    // - habitaciones = new LSE<Habitacion>()
    // - habitacionActual = null
```

### Métodos Públicos

| Método | Parámetros | Retorno | Descripción | Complejidad |
|--------|-----------|---------|-------------|-------------|
| `agregarHabitacion` | Habitacion | void | Añade habitación como nodo | O(1) |
| `conectarHabitaciones` | h1, h2, etiqueta | void | Crea arista entre habitaciones | O(1) |
| `obtenerHabitacion` | id | Habitacion | Busca habitación por ID | O(n) |
| `obtenerHabitacionActual` | - | Habitacion | Obtiene habitación actual | O(1) |
| `cambiarHabitacion` | id | void | Cambia habitación actual | O(n) |
| `caminoMinimoHabitaciones` | origen, destino | LSE<Habitacion> | BFS entre habitaciones | O(V+E) |
| `dijkstraHabitaciones` | origen, destino | LSE<Habitacion> | Dijkstra entre habitaciones | O((V+E)logV) |
| `obtenerHabitacionesAdyacentes` | id | LSE<Habitacion> | Habitaciones conectadas | O(E) |
| `cargarDesdeJson` | ruta | void | Carga mapa desde JSON | O(V+E) |
| `guardarEnJson` | ruta | void | Guarda mapa a JSON | O(V+E) |
| `obtenerTodasHabitaciones` | - | LSE<Habitacion> | Lista todas las habitaciones | O(1) |

### Detalles de Implementación

#### `agregarHabitacion(habitacion)`
```
1. Añadir habitacion a lista habitaciones
2. Llamar grafo.agregarNodo(habitacion.getNombre())
Complejidad: O(1)
```

#### `conectarHabitaciones(h1, h2, etiqueta)`
```
1. Llamar grafo.agregarArista(h1.getNombre(), h2.getNombre(), etiqueta)
2. Crear Puerta en h1 apuntando a h2.getId()
3. Crear Puerta en h2 apuntando a h1.getId()
Complejidad: O(1)
```

#### `caminoMinimoHabitaciones(origen, destino)`
```
1. Obtener Habitacion origen por ID
2. Obtener Habitacion destino por ID
3. Llamar grafo.caminoMinimo(origen.getNombre(), destino.getNombre())
4. Convertir LSE<Nodo> a LSE<Habitacion>
Complejidad: O(V+E) del BFS
```

#### `dijkstraHabitaciones(origen, destino)`
```
1. Obtener Habitacion origen por ID
2. Obtener Habitacion destino por ID
3. Llamar grafo.dijkstra(origen.getNombre(), destino.getNombre())
4. Convertir LSE<Nodo> a LSE<Habitacion>
Complejidad: O((V+E)logV) del Dijkstra
```

### Excepciones

- `ListaVaciaExceptions` - Si grafo vacío
- `RuntimeException` - Si habitación no encontrada

### Restricciones

- No usar `java.util.*`
- Usar solo `LSE<T>` para almacenamiento
- Reutilizar completamente `Grafo` existente (no modificar)

### Notas de Implementación

- **Composición sobre herencia**: No extender Grafo, usar composición
- **Conversión de tipos**: Convertir `LSE<Nodo>` a `LSE<Habitacion>` en métodos de camino
- **Persistencia**: Usar formato JSON con tripletas RDF (compatible con Grafo)

---

## Tabla Resumen de Complejidades

| Clase | Método | Complejidad | Notas |
|-------|--------|-------------|-------|
| **Celda** | Todos | O(1) | Trivial |
| **MatrizHabitacion** | getCelda | O(n+m) | Búsqueda secuencial en LSE |
| | obtenerCeldasAlcanzables | O(n*m) | BFS en matriz |
| | obtenerCeldasConEnemigos | O(n*m) | Iteración completa |
| **Puerta** | Todos | O(1) | Trivial |
| **Habitacion** | obtenerPuerta | O(k) | k = #puertas (típicamente 2-4) |
| | obtenerEnemigos | O(n*m) | Delega a matriz |
| **GrafoHabitaciones** | caminoMinimo | O(V+E) | BFS en grafo |
| | dijkstra | O((V+E)logV) | Dijkstra en grafo |
| | obtenerHabitacion | O(n) | Búsqueda en LSE |

---

## Restricciones Críticas

✅ **NO usar java.util.*** en ninguna clase  
✅ **Usar solo estructuras propias**: LSE, Grafo, etc.  
✅ **Cada clase debe tener tests JUnit**  
✅ **Javadoc en todos los métodos públicos**  
✅ **Separación MVC**: Estas clases son modelo puro (sin JavaFX)  
✅ **Validación de índices**: En MatrizHabitacion y accesos a LSE  

---

## Orden de Implementación

```
1️⃣  TipoCelda (enum)              ← Sin dependencias
2️⃣  Celda                         ← Usa TipoCelda
3️⃣  MatrizHabitacion              ← Usa Celda, LSE
4️⃣  Puerta                        ← Independiente
5️⃣  Habitacion                    ← Usa MatrizHabitacion, Puerta, LSE
6️⃣  GrafoHabitaciones             ← Usa Habitacion, Grafo existente
```

---

**Documento**: Especificaciones Técnicas  
**Versión**: 1.0  
**Fecha**: 2026-05-07
