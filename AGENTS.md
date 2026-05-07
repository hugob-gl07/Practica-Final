# AGENTS.md — Guía de agentes IA del proyecto

## Contexto del proyecto

Juego de exploración por habitaciones en JavaFX. El jugador se mueve por un grafo de habitaciones,
cada una representada como una matriz bidimensional. El proyecto se evalúa en dos asignaturas:

- **Metodología de la Programación**: diseño OOP, UML, excepciones, JSON, JavaFX, MVC y uso de IA
- **Estructuras de Datos**: implementación propia de Listas, Pilas, Colas, Árboles y Grafos — **sin java.util.***

## Restricción crítica

**Prohibido usar `java.util.*` para estructuras evaluadas.** Usar ArrayList, LinkedList, Stack,
Queue, HashMap, TreeMap o similares en las estructuras evaluadas implica 0 puntos en Estructuras de Datos.
Todas las estructuras deben ser implementaciones propias del equipo.

## Estructura del proyecto

```
src/main/java/es/universidad/juego/
├── model/
│   ├── estructuras/     ← Estructuras de datos propias (MyList, MyQueue, MyGraph...)
│   ├── entidades/       ← Jugador, Enemigo, Objeto
│   ├── habitacion/      ← Habitacion, Celda, MapaJuego
│   └── juego/           ← Lógica principal, turnos, combate
├── controller/          ← Controladores JavaFX (MVC)
└── view/                ← Vistas FXML
```

## Agentes disponibles

### Agentes primarios (Tab para cambiar)

| Agente | Modo | Cuándo usarlo |
|--------|------|---------------|
| `build` | Escritura total | Implementar clases, editar código, ejecutar |
| `plan`  | Solo lectura | Diseñar arquitectura, decidir estructuras, analizar antes de implementar |

### Subagentes (invocar con @nombre)

| Agente | Cuándo usarlo |
|--------|---------------|
| `@explorador` | Ver qué clases existen, buscar métodos, entender la estructura actual |
| `@revisor` | Revisar una clase terminada antes de continuar. Redactar entradas del diario |
| `@debug` | Investigar errores en tiempo de ejecución o tests que fallan |
| `@docs` | Añadir Javadoc, escribir README, preparar la memoria |
| `@structures-guard` | Detectar usos prohibidos de java.util.* — usar antes de cada entrega |
| `@graph-algo` | Implementar BFS, Dijkstra, DFS usando solo estructuras propias |

## Flujo de trabajo recomendado

Para cada tarea nueva seguir este orden:

```
1. plan       → diseñar la clase o algoritmo antes de escribir código
2. build      → implementar lo diseñado
3. @revisor   → revisar el resultado y anotar en el diario
4. @structures-guard → verificar que no hay java.util.* prohibido
5. @docs      → añadir Javadoc y documentación
```

## Algoritmos requeridos por el enunciado

- **BFS**: calcular casillas alcanzables en el rango de movimiento del jugador
- **Dijkstra**: ruta óptima entre habitaciones del grafo de habitaciones
- **DFS o BFS**: movimiento de enemigos hacia el jugador

El grafo tiene dos niveles:
1. **Grafo de habitaciones**: nodos = habitaciones, aristas = puertas entre ellas
2. **Matriz interna**: cada habitación es una cuadrícula donde se mueve el jugador

## Notas para el diario de IA

El profesor exige un diario de utilización del agente con:
- Qué se le pidió al agente
- Qué generó
- Qué fue correcto y qué se corrigió manualmente
- Aprendizaje metodológico extraído
- Ajustes realizados para la siguiente sesión

Registrar cada sesión relevante en `diario-ia.md`.
