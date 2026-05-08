# 📊 DIAGRAMAS UML - CLASES DEL JUEGO

Documentación de diagramas UML para todas las clases implementadas del sistema de exploración de habitaciones.

---

## 1️⃣ TIPCELDA (Enumeración)

```
╔════════════════════════════════════════════════════════════════╗
║                    <<enumeration>>                             ║
║                      TipoCelda                                 ║
╠════════════════════════════════════════════════════════════════╣
║ VALORES:                                                       ║
║  • VACIO          → Celda transitable, sin obstáculos          ║
║  • PARED          → Celda no transitable, obstáculo            ║
║  • ENTRADA        → Puerta de entrada a la habitación          ║
║  • SALIDA         → Puerta de salida a otra habitación         ║
║  • OBJETO         → Celda con objeto recolectable              ║
║  • ENEMIGO        → Celda con enemigo                          ║
║  • JUGADOR        → Celda con el jugador                       ║
╠════════════════════════════════════════════════════════════════╣
║ MÉTODOS:                                                       ║
║  + esTransitable(): boolean                                    ║
║    → Retorna true si VACIO, ENTRADA, SALIDA, OBJETO, ENEMIGO  ║
║    → Retorna false si PARED                                    ║
║                                                                ║
║  + puedeContenerObjetos(): boolean                             ║
║    → Retorna true si OBJETO, VACIO                             ║
║                                                                ║
║  + esObstaculo(): boolean                                      ║
║    → Retorna true si PARED                                     ║
║                                                                ║
║  + obtenerCaracterRepresentativo(): char                       ║
║    → VACIO: ' '  | PARED: '#'  | ENTRADA: 'E'                 ║
║    → SALIDA: 'S' | OBJETO: 'O' | ENEMIGO: 'X'                 ║
║    → JUGADOR: 'J'                                              ║
║                                                                ║
║  + obtenerColorRepresentativo(): String                        ║
║    → Retorna color hexadecimal para renderizado JavaFX         ║
║    → VACIO: "#FFFFFF" | PARED: "#000000"                      ║
║    → ENTRADA: "#00FF00" | SALIDA: "#FF0000"                   ║
║    → OBJETO: "#FFFF00" | ENEMIGO: "#FF00FF"                   ║
║    → JUGADOR: "#0000FF"                                        ║
║                                                                ║
║  + puedeContenerEntidad(): boolean                             ║
║    → Retorna true si VACIO, ENTRADA, SALIDA, OBJETO           ║
║    → Retorna false si PARED, ENEMIGO, JUGADOR                 ║
║                                                                ║
║  + requiereInteraccion(): boolean                              ║
║    → Retorna true si ENTRADA, SALIDA, OBJETO                  ║
║    → Retorna false en otros casos                              ║
║                                                                ║
║  + obtenerTodosLosTipos(): TipoCelda[]                         ║
║    → Retorna array con todos los valores del enum              ║
║                                                                ║
║  + obtenerTiposTransitables(): TipoCelda[]                     ║
║    → Retorna array solo con tipos transitables                 ║
╚════════════════════════════════════════════════════════════════╝
```

**Ubicación esperada:** `src/EstructurasUtilizadas/Celda/TipoCelda.java`

**Características:**
- Enumeración con 7 constantes
- Métodos de utilidad para validaciones y representación
- Sin dependencias externas
- Complejidad: O(1) para todos los métodos

---

## 2️⃣ CELDA (Clase)

```
╔════════════════════════════════════════════════════════════════╗
║                        Celda                                   ║
╠════════════════════════════════════════════════════════════════╣
║ ATRIBUTOS (privados):                                          ║
║  - x: int                    → Coordenada X (columna)          ║
║  - y: int                    → Coordenada Y (fila)             ║
║  - tipo: TipoCelda           → Tipo de celda                   ║
╠════════════════════════════════════════════════════════════════╣
║ CONSTRUCTORES:                                                 ║
║  + Celda(x: int, y: int, tipo: TipoCelda)                      ║
║    → Inicializa una celda con posición y tipo                  ║
║                                                                ║
║  + Celda(x: int, y: int)                                       ║
║    → Inicializa con tipo por defecto VACIO                     ║
╠════════════════════════════════════════════════════════════════╣
║ GETTERS:                                                       ║
║  + getX(): int               → Retorna coordenada X            ║
║  + getY(): int               → Retorna coordenada Y            ║
║  + getTipo(): TipoCelda      → Retorna tipo de celda           ║
╠════════════════════════════════════════════════════════════════╣
║ SETTERS:                                                       ║
║  + setX(x: int): void        → Modifica coordenada X           ║
║  + setY(y: int): void        → Modifica coordenada Y           ║
║  + setTipo(tipo: TipoCelda): void → Modifica tipo              ║
╠════════════════════════════════════════════════════════════════╣
║ MÉTODOS DE UTILIDAD:                                           ║
║  + mismaPosicion(otra: Celda): boolean                         ║
║    → Retorna true si x e y coinciden con otra celda            ║
║                                                                ║
║  + esTransitable(): boolean                                    ║
║    → Delegación: return tipo.esTransitable()                   ║
║                                                                ║
║  + equals(obj: Object): boolean                                ║
║    → Compara por posición (x, y) y tipo                        ║
║                                                                ║
║  + hashCode(): int                                             ║
║    → Genera hash basado en x, y, tipo                          ║
║                                                                ║
║  + toString(): String                                          ║
║    → Formato: "Celda(x=5, y=3, tipo=VACIO)"                    ║
║    → O con carácter: "Celda(5,3) [' ']"                        ║
╚════════════════════════════════════════════════════════════════╝
```

**Ubicación esperada:** `src/EstructurasUtilizadas/Celda/Celda.java`

**Características:**
- Clase simple con 3 atributos
- Métodos de acceso y utilidad
- Comparable por posición (para búsquedas)
- Complejidad: O(1) para todos los métodos

---

## 3️⃣ MATRIZHABITACION (Clase)

```
╔════════════════════════════════════════════════════════════════╗
║                   MatrizHabitacion                             ║
╠════════════════════════════════════════════════════════════════╣
║ ATRIBUTOS (privados):                                          ║
║  - matriz: Celda[][]         → Matriz 2D de celdas             ║
║  - filas: int                → Número de filas                 ║
║  - columnas: int             → Número de columnas              ║
╠════════════════════════════════════════════════════════════════╣
║ CONSTRUCTOR:                                                   ║
║  + MatrizHabitacion(filas: int, columnas: int)                 ║
║    → Crea matriz de filas × columnas                           ║
║    → Inicializa todas las celdas como VACIO                    ║
║    → Valida: filas > 0 && columnas > 0                         ║
║    → Lanza IllegalArgumentException si no cumple               ║
╠════════════════════════════════════════════════════════════════╣
║ GETTERS:                                                       ║
║  + getCelda(fila: int, columna: int): Celda                    ║
║    → Retorna la celda en posición (fila, columna)              ║
║    → Valida límites: 0 <= fila < filas && 0 <= col < columnas ║
║    → Lanza IndexOutOfBoundsException si está fuera             ║
║                                                                ║
║  + getFilas(): int           → Retorna número de filas         ║
║  + getColumnas(): int        → Retorna número de columnas      ║
╠════════════════════════════════════════════════════════════════╣
║ SETTERS:                                                       ║
║  + setCelda(fila: int, columna: int, celda: Celda): void       ║
║    → Asigna una celda en posición (fila, columna)              ║
║    → Valida límites y null                                     ║
║    → Actualiza coordenadas de la celda (x=columna, y=fila)     ║
╠════════════════════════════════════════════════════════════════╣
║ MÉTODOS DE UTILIDAD:                                           ║
║  + esTransitable(fila: int, columna: int): boolean             ║
║    → Retorna true si la celda es transitable                   ║
║    → Retorna false si está fuera de límites                    ║
║                                                                ║
║  + esValida(fila: int, columna: int): boolean                  ║
║    → Retorna true si (fila, columna) está dentro de límites    ║
║                                                                ║
║  + buscarCeldasPorTipo(tipo: TipoCelda): Lista<Celda>          ║
║    → Retorna lista de todas las celdas del tipo especificado   ║
║    → Usa ListaSimplementeEnlazada (estructura propia)          ║
║                                                                ║
║  + toString(): String                                          ║
║    → Imprime matriz con caracteres representativos             ║
║    → Ejemplo:                                                  ║
║      ╔═════════╗                                               ║
║      ║ # # # # ║                                               ║
║      ║ #     # ║                                               ║
║      ║ # J   # ║                                               ║
║      ║ # # # # ║                                               ║
║      ╚═════════╝                                               ║
╚════════════════════════════════════════════════════════════════╝
```

**Ubicación esperada:** `src/EstructurasUtilizadas/Celda/MatrizHabitacion.java`

**Características:**
- Matriz 2D de Celda[][]
- Validación de límites en todos los accesos
- Método de búsqueda por tipo usando estructura propia
- Complejidad: O(1) acceso, O(n²) búsqueda

---

## 4️⃣ HABITACION (Clase)

```
╔════════════════════════════════════════════════════════════════╗
║                      Habitacion                                ║
╠════════════════════════════════════════════════════════════════╣
║ ATRIBUTOS (privados):                                          ║
║  - id: int                   → Identificador único             ║
║  - nombre: String            → Nombre descriptivo              ║
║  - matriz: MatrizHabitacion  → Mapa interno de la habitación   ║
╠════════════════════════════════════════════════════════════════╣
║ CONSTRUCTOR:                                                   ║
║  + Habitacion(id: int, nombre: String, filas: int, cols: int)  ║
║    → Crea habitación con ID, nombre y matriz                   ║
║    → Valida: id > 0, nombre != null, filas > 0, cols > 0       ║
║    → Lanza IllegalArgumentException si no cumple               ║
╠════════════════════════════════════════════════════════════════╣
║ GETTERS:                                                       ║
║  + getId(): int              → Retorna ID de habitación        ║
║  + getNombre(): String       → Retorna nombre                  ║
║  + getMatriz(): MatrizHabitacion → Retorna matriz interna      ║
║  + getFilas(): int           → Retorna filas de matriz         ║
║  + getColumnas(): int        → Retorna columnas de matriz      ║
╠════════════════════════════════════════════════════════════════╣
║ SETTERS:                                                       ║
║  + setId(id: int): void      → Modifica ID (valida > 0)        ║
║  + setNombre(nombre: String): void → Modifica nombre           ║
╠════════════════════════════════════════════════════════════════╣
║ MÉTODOS DE ACCESO A MATRIZ:                                    ║
║  + getCelda(fila: int, columna: int): Celda                    ║
║    → Delegación: return matriz.getCelda(fila, columna)         ║
║                                                                ║
║  + setCelda(fila: int, columna: int, celda: Celda): void       ║
║    → Delegación: matriz.setCelda(fila, columna, celda)         ║
║                                                                ║
║  + esTransitable(fila: int, columna: int): boolean             ║
║    → Delegación: return matriz.esTransitable(fila, columna)    ║
╠════════════════════════════════════════════════════════════════╣
║ MÉTODOS DE BÚSQUEDA:                                           ║
║  + tieneCelda(tipoCelda: TipoCelda): boolean                   ║
║    → Retorna true si existe al menos una celda del tipo        ║
║                                                                ║
║  + buscarCelda(tipoCelda: TipoCelda): Celda                    ║
║    → Retorna la primera celda del tipo especificado            ║
║    → Retorna null si no existe                                 ║
║                                                                ║
║  + buscarCeldasPorTipo(tipo: TipoCelda): Lista<Celda>          ║
║    → Delegación: return matriz.buscarCeldasPorTipo(tipo)       ║
║                                                                ║
║  + buscarEntrada(): Celda                                      ║
║    → Retorna la celda de ENTRADA                               ║
║    → Retorna null si no existe                                 ║
║                                                                ║
║  + buscarSalida(): Celda                                       ║
║    → Retorna la celda de SALIDA                                ║
║    → Retorna null si no existe                                 ║
╠════════════════════════════════════════════════════════════════╣
║ MÉTODOS DE UTILIDAD:                                           ║
║  + equals(obj: Object): boolean                                ║
║    → Compara por ID (identificador único)                      ║
║                                                                ║
║  + hashCode(): int                                             ║
║    → Basado en ID                                              ║
║                                                                ║
║  + toString(): String                                          ║
║    → Formato: "Habitacion{id=1, nombre='Sala Principal'}"      ║
║    → Incluye representación visual de la matriz                ║
╚════════════════════════════════════════════════════════════════╝
```

**Ubicación esperada:** `src/EstructurasUtilizadas/Celda/Habitacion.java`

**Características:**
- Contenedor de MatrizHabitacion
- Delegación de métodos a matriz
- Métodos de búsqueda especializados (entrada, salida)
- Complejidad: O(n²) búsqueda

---

## 5️⃣ PARIDNOMBRE (Clase Auxiliar)

```
╔════════════════════════════════════════════════════════════════╗
║                    ParIdNombre                                 ║
║              (Clase Auxiliar - Mapeo ID → Nombre)              ║
╠════════════════════════════════════════════════════════════════╣
║ ATRIBUTOS (privados):                                          ║
║  - idHabitacion: int         → ID de la habitación             ║
║  - nombreNodo: String        → Nombre del nodo en Grafo        ║
║                               → Formato: "HAB" + id            ║
╠════════════════════════════════════════════════════════════════╣
║ CONSTRUCTOR:                                                   ║
║  + ParIdNombre(id: int, nombre: String)                        ║
║    → Crea par ID-Nombre                                        ║
║    → Valida: id > 0, nombre != null                            ║
╠════════════════════════════════════════════════════════════════╣
║ GETTERS:                                                       ║
║  + getIdHabitacion(): int    → Retorna ID                      ║
║  + getNombreNodo(): String   → Retorna nombre del nodo         ║
╠════════════════════════════════════════════════════════════════╣
║ SETTERS:                                                       ║
║  + setIdHabitacion(id: int): void → Modifica ID                ║
║  + setNombreNodo(nombre: String): void → Modifica nombre       ║
╠════════════════════════════════════════════════════════════════╣
║ MÉTODOS DE COMPARACIÓN:                                        ║
║  + compareTo(o: ParIdNombre): int                              ║
║    → Implementa Comparable<ParIdNombre>                        ║
║    → Compara por ID (orden ascendente)                         ║
║    → Retorna: this.id - o.id                                   ║
║                                                                ║
║  + equals(obj: Object): boolean                                ║
║    → Compara por ID                                            ║
║                                                                ║
║  + hashCode(): int                                             ║
║    → Basado en ID                                              ║
║                                                                ║
║  + toString(): String                                          ║
║    → Formato: "ParIdNombre{id=1, nombre='HAB1'}"               ║
╚════════════════════════════════════════════════════════════════╝

PROPÓSITO:
  Mapear IDs de habitaciones a nombres de nodos en el Grafo.
  Permite búsquedas rápidas en ListaSimplementeEnlazada.
  
EJEMPLO DE USO:
  ParIdNombre par = new ParIdNombre(1, "HAB1");
  // Usado en GrafoHabitacion para traducir ID → nombre de nodo
```

**Ubicación esperada:** `src/EstructurasUtilizadas/Celda/ParIdNombre.java`

**Características:**
- Clase simple con 2 atributos
- Implementa Comparable<ParIdNombre>
- Complejidad: O(1) para todos los métodos

---

## 6️⃣ PARNOMBREHABITACION (Clase Auxiliar)

```
╔════════════════════════════════════════════════════════════════╗
║                 ParNombreHabitacion                            ║
║          (Clase Auxiliar - Mapeo Nombre → Habitacion)          ║
╠════════════════════════════════════════════════════════════════╣
║ ATRIBUTOS (privados):                                          ║
║  - nombreNodo: String        → Nombre del nodo en Grafo        ║
║                               → Formato: "HAB" + id            ║
║  - habitacion: Habitacion    → Referencia a objeto Habitacion  ║
╠════════════════════════════════════════════════════════════════╣
║ CONSTRUCTOR:                                                   ║
║  + ParNombreHabitacion(nombre: String, hab: Habitacion)        ║
║    → Crea par Nombre-Habitacion                                ║
║    → Valida: nombre != null, hab != null                       ║
╠════════════════════════════════════════════════════════════════╣
║ GETTERS:                                                       ║
║  + getNombreNodo(): String   → Retorna nombre del nodo         ║
║  + getHabitacion(): Habitacion → Retorna objeto Habitacion     ║
╠════════════════════════════════════════════════════════════════╣
║ SETTERS:                                                       ║
║  + setNombreNodo(nombre: String): void → Modifica nombre       ║
║  + setHabitacion(hab: Habitacion): void → Modifica habitación  ║
╠════════════════════════════════════════════════════════════════╣
║ MÉTODOS DE COMPARACIÓN:                                        ║
║  + compareTo(o: ParNombreHabitacion): int                      ║
║    → Implementa Comparable<ParNombreHabitacion>                ║
║    → Compara por nombre (orden alfabético)                     ║
║    → Retorna: this.nombreNodo.compareTo(o.nombreNodo)          ║
║                                                                ║
║  + equals(obj: Object): boolean                                ║
║    → Compara por nombre                                        ║
║                                                                ║
║  + hashCode(): int                                             ║
║    → Basado en nombre                                          ║
║                                                                ║
║  + toString(): String                                          ║
║    → Formato: "ParNombreHabitacion{nombre='HAB1', hab=...}"    ║
╚════════════════════════════════════════════════════════════════╝

PROPÓSITO:
  Mapear nombres de nodos a objetos Habitacion.
  Permite búsquedas rápidas en ListaSimplementeEnlazada.
  
EJEMPLO DE USO:
  Habitacion hab = new Habitacion(1, "Sala", 10, 10);
  ParNombreHabitacion par = new ParNombreHabitacion("HAB1", hab);
  // Usado en GrafoHabitacion para traducir nombre de nodo → Habitacion
```

**Ubicación esperada:** `src/EstructurasUtilizadas/Celda/ParNombreHabitacion.java`

**Características:**
- Clase simple con 2 atributos
- Implementa Comparable<ParNombreHabitacion>
- Complejidad: O(1) para todos los métodos

---

## 7️⃣ GRAFOHABITACION (Clase Principal)

```
╔════════════════════════════════════════════════════════════════╗
║                   GrafoHabitacion                              ║
║        (Grafo de Habitaciones - Nivel Superior)                ║
╠════════════════════════════════════════════════════════════════╣
║ ATRIBUTOS (privados):                                          ║
║  - grafo: Grafo              → Grafo subyacente (delegación)    ║
║  - habitaciones: Lista<ParNombreHabitacion>                     ║
║                               → Mapeo: nombre → Habitacion     ║
║  - idnombre: Lista<ParIdNombre>                                 ║
║                               → Mapeo: ID → nombre de nodo     ║
╠════════════════════════════════════════════════════════════════╣
║ CONSTRUCTOR:                                                   ║
║  + GrafoHabitacion()                                            ║
║    → Inicializa grafo vacío                                    ║
║    → Inicializa listas de mapeo vacías                         ║
╠════════════════════════════════════════════════════════════════╣
║ MÉTODOS DE GESTIÓN:                                            ║
║  + agregarHabitacion(habitacion: Habitacion): void             ║
║    → Agrega habitación al grafo                                ║
║    → Crea nodo con nombre "HAB" + id                           ║
║    → Agrega pares a listas de mapeo                            ║
║    → Valida: habitacion != null, id único                      ║
║    → Lanza IllegalArgumentException si ya existe               ║
║                                                                ║
║  + conectarHabitaciones(hab1: Habitacion, hab2: Habitacion): void
║    → Conecta dos habitaciones con arista bidireccional         ║
║    → Busca nombres de nodos usando buscarNombrePorId           ║
║    → Agrega arista con peso 1                                  ║
║    → Valida: ambas habitaciones existen                        ║
║    → Lanza IllegalArgumentException si no existen              ║
╠════════════════════════════════════════════════════════════════╣
║ MÉTODOS DE BÚSQUEDA:                                           ║
║  + obtenerHabitacion(id: int): Habitacion                      ║
║    → Busca habitación por ID                                   ║
║    → Retorna null si no existe                                 ║
║                                                                ║
║  + obtenerHabitaciones(habitacion: Habitacion): Lista<Habitacion>
║    → Retorna lista de habitaciones adyacentes                  ║
║    → Usa estructura propia ListaSimplementeEnlazada            ║
║    → Retorna lista vacía si no hay adyacentes                  ║
║                                                                ║
║  - buscarNombrePorId(idBuscado: int): String                   ║
║    → PRIVADO - Método auxiliar                                 ║
║    → Busca en lista idnombre                                   ║
║    → Retorna nombre del nodo o null                            ║
║    → Complejidad: O(n)                                         ║
║                                                                ║
║  - buscarHabitacionPorNombre(nombre: String): Habitacion       ║
║    → PRIVADO - Método auxiliar                                 ║
║    → Busca en lista habitaciones                               ║
║    → Retorna objeto Habitacion o null                          ║
║    → Complejidad: O(n)                                         ║
╠════════════════════════════════════════════════════════════════╣
║ MÉTODOS DE ALGORITMOS:                                         ║
║  + buscarRuta(habInicio: Habitacion, habFin: Habitacion):      ║
║    Lista<Habitacion>                                           ║
║    → Implementa BFS para encontrar ruta más corta              ║
║    → Retorna lista de habitaciones en orden                    ║
║    → Retorna lista vacía si no hay ruta                        ║
║    → Usa Cola (estructura propia)                              ║
║                                                                ║
║  + calcularDistancia(hab1: Habitacion, hab2: Habitacion): int  ║
║    → Calcula distancia mínima entre dos habitaciones           ║
║    → Usa Dijkstra del grafo subyacente                         ║
║    → Retorna -1 si no hay ruta                                 ║
║                                                                ║
║  + esConexo(): boolean                                         ║
║    → Verifica si el grafo es conexo                            ║
║    → Retorna true si todas las habitaciones están conectadas   ║
║    → Usa BFS desde primera habitación                          ║
╠════════════════════════════════════════════════════════════════╣
║ MÉTODOS DE UTILIDAD:                                           ║
║  + equals(obj: Object): boolean                                ║
║    → Compara por contenido del grafo                           ║
║                                                                ║
║  + hashCode(): int                                             ║
║    → Basado en grafo subyacente                                ║
║                                                                ║
║  + toString(): String                                          ║
║    → Formato: "GrafoHabitacion{nodos=5, aristas=4}"            ║
║    → Incluye lista de habitaciones y conexiones                ║
╚════════════════════════════════════════════════════════════════╝

FLUJO DE DATOS:
  
  Habitacion (ID=1, nombre="Sala")
         ↓
  agregarHabitacion()
         ↓
  Crea ParIdNombre(1, "HAB1")
  Crea ParNombreHabitacion("HAB1", habitacion)
         ↓
  Agrega nodo "HAB1" a Grafo
  Agrega pares a listas de mapeo
         ↓
  conectarHabitaciones(hab1, hab2)
         ↓
  buscarNombrePorId(hab1.id) → "HAB1"
  buscarNombrePorId(hab2.id) → "HAB2"
         ↓
  Agrega arista "HAB1" ↔ "HAB2" en Grafo
```

**Ubicación esperada:** `src/EstructurasUtilizadas/Celda/GrafoHabitacion.java`

**Características:**
- Delegación sobre Grafo existente
- Dos listas de mapeo (ID→nombre, nombre→Habitacion)
- Métodos auxiliares privados para búsquedas
- Algoritmos BFS y Dijkstra
- Complejidad: O(n) búsqueda, O(n+m) BFS

---

## 📊 DIAGRAMA DE RELACIONES GLOBALES

```
                         ┌─────────────────┐
                         │   TipoCelda     │
                         │  <<enumeration>>│
                         └────────┬────────┘
                                  │ usa
                                  ▼
                         ┌─────────────────┐
                         │     Celda       │
                         │ (x, y, tipo)    │
                         └────────┬────────┘
                                  │ contiene (*)
                                  ▼
                    ┌─────────────────────────────┐
                    │   MatrizHabitacion          │
                    │ (matriz 2D de Celda)        │
                    └────────┬────────────────────┘
                             │ contiene (1)
                             ▼
                    ┌─────────────────────────────┐
                    │      Habitacion             │
                    │ (id, nombre, matriz)        │
                    └────────┬────────────────────┘
                             │ referencia (*)
                             ▼
            ┌────────────────────────────────────┐
            │   ParNombreHabitacion              │
            │ (nombre → Habitacion)              │
            └────────┬───────────────────────────┘
                     │ contiene (*)
                     ▼
            ┌────────────────────────────────────┐
            │    GrafoHabitacion                 │
            │                                    │
            │ - grafo: Grafo                     │
            │ - habitaciones: Lista<ParNombre>   │
            │ - idnombre: Lista<ParIdNombre>     │
            └────────┬───────────────────────────┘
                     │ usa (1)
                     ▼
                ┌──────────────┐
                │  Grafo       │
                │ (nodos,      │
                │  aristas)    │
                └──────────────┘

TAMBIÉN CONTIENE:
  ParIdNombre (ID → nombre de nodo)
  ↓
  Usado internamente en GrafoHabitacion
```

---

## 📋 TABLA RESUMEN DE ESPECIFICACIONES

| Clase | Atributos | Métodos | Complejidad | Ubicación |
|-------|-----------|---------|-------------|-----------|
| **TipoCelda** | 7 constantes | 8 métodos | O(1) | `Celda/TipoCelda.java` |
| **Celda** | 3 (x, y, tipo) | 11 métodos | O(1) | `Celda/Celda.java` |
| **MatrizHabitacion** | 3 (matriz, filas, cols) | 8 métodos | O(1) acceso, O(n²) búsqueda | `Celda/MatrizHabitacion.java` |
| **Habitacion** | 3 (id, nombre, matriz) | 13 métodos | O(n²) búsqueda | `Celda/Habitacion.java` |
| **ParIdNombre** | 2 (id, nombre) | 6 métodos | O(1) | `Celda/ParIdNombre.java` |
| **ParNombreHabitacion** | 2 (nombre, hab) | 6 métodos | O(1) | `Celda/ParNombreHabitacion.java` |
| **GrafoHabitacion** | 3 (grafo, habitaciones, idnombre) | 12 métodos | O(n) búsqueda, O(n+m) BFS | `Celda/GrafoHabitacion.java` |

---

## 🎯 ORDEN DE IMPLEMENTACIÓN RECOMENDADO

1. **TipoCelda** - Enumeración base
2. **Celda** - Depende de TipoCelda
3. **MatrizHabitacion** - Depende de Celda
4. **Habitacion** - Depende de MatrizHabitacion
5. **ParIdNombre** - Clase auxiliar independiente
6. **ParNombreHabitacion** - Depende de Habitacion
7. **GrafoHabitacion** - Depende de todas las anteriores

---

**Documento generado:** 08/05/2026  
**Versión:** 1.0  
**Estado:** Diagramas UML completos - Listos para implementación
