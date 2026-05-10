# Diario de utilización de agentes IA

**Proyecto**: Juego de exploración por habitaciones — Práctica Conjunta ED + MP  
**Equipo**: <!-- añadir nombres -->  
**Herramienta**: OpenCode 1.14.39  

---

## Cómo rellenar este diario

Después de cada sesión relevante con un agente, añadir una entrada siguiendo
la plantilla de abajo. No hace falta documentar cada mensaje, solo las sesiones
donde se generó, revisó o corrigió algo significativo.

---

## Plantilla de entrada

```
### Sesión N — [fecha] — Agente: [nombre]

**Objetivo**: qué se quería conseguir en esta sesión

**Instrucción dada al agente**: 
> copia aquí el prompt principal que le diste

**Qué generó**:
descripción de lo que produjo el agente

**Qué fue correcto**:
partes del resultado que se aceptaron sin cambios

**Qué se corrigió manualmente**:
partes que tuvieron que modificarse y por qué

**Aprendizaje metodológico**:
qué has aprendido sobre cómo usar mejor el agente

**Ajuste para la próxima sesión**:
cómo cambiarás las instrucciones o el flujo la próxima vez
```

---

## Entradas del diario

### Sesión 1 — 06/05/2026 — Agente: build + plan

**Objetivo**: Configurar los agentes del proyecto y establecer el flujo de trabajo

**Instrucción dada al agente**:
> Configuración inicial del proyecto: creación de agentes personalizados en opencode.json
> y archivos de soporte AGENTS.md y diario-ia.md

**Qué generó**:
- 8 agentes configurados en `opencode.json`: build, plan, revisor, explorador, debug, docs, structures-guard, graph-algo
- Archivo `AGENTS.md` con guía de uso de cada agente y flujo de trabajo recomendado
- Archivo `diario-ia.md` (este archivo) para registro de sesiones

**Qué fue correcto**:
- La estructura del `opencode.json` con permisos diferenciados por agente
- La separación entre agentes primarios (Tab) y subagentes (@nombre)

**Qué se corrigió manualmente**:
- El archivo JSON tuvo que copiarse fuera del contenedor Docker para editarlo,
  ya que nano no estaba instalado en la imagen. Se usó `docker cp` para
  extraerlo, editarlo en VS Code y devolverlo al contenedor.

**Aprendizaje metodológico**:
Definir los agentes antes de empezar a implementar obliga a pensar en el flujo
de trabajo completo. Tener un agente específico para detectar java.util.* es
útil como red de seguridad antes de cada entrega.

**Ajuste para la próxima sesión**:
Usar siempre `plan` antes de `build` para diseñar la clase antes de implementarla.
Invocar `@structures-guard` después de cada clase de estructuras de datos.

---

### Sesión 3 — 07/05/2026 — Agente: plan + build (doculación)

**Objetivo**: Crear modelo UML completo con diagramas y documentación técnica para el juego de exploración por habitaciones.

**Instrucción dada al agente**: 
> Analizar las estructuras de datos existentes y crear el modelo UML de las 4 nuevas clases (Celda, MatrizHabitacion, Habitacion, GrafoHabitaciones). Generar diagrama UML visual, especificaciones técnicas, ejemplos de uso, casos de prueba JUnit y documentación completa.

**Qué generó**:
- Diagrama UML en 4 formatos (SVG, PNG, DOT, PUML)
- 11 archivos de documentación (~60 KB total)
- Especificaciones técnicas detalladas para 6 clases (54+ métodos)
- Ejemplos de código y 30+ casos de prueba JUnit
- Orden de implementación y restricciones críticas

**Qué fue correcto**:
- Análisis de dependencias entre clases basado en estructuras existentes
- Recomendación de uso de composición (no herencia) para GrafoHabitaciones
- Orden de implementación lógico: TipoCelda → Celda → MatrizHabitacion → Habitacion → GrafoHabitaciones
- Restricciones claramente documentadas (sin java.util.*)

**Qué se corrigió manualmente**:
- Generé diagrama con Graphviz (no PlantUML) por problemas de sintaxis
- Creé archivo INICIO_RAPIDO.txt para navegación rápida

**Aprendizaje metodológico**:
- Documentar primero (especificaciones) reduce errores de implementación
- Usar múltiples formatos de diagrama aumenta accesibilidad
- Proporcionar tests JUnit junto con especificaciones facilita el desarrollo

**Ajuste para la próxima sesión**:
- Implementar las clases siguiendo el orden recomendado
- Usar @structures-guard después de cada clase
- Registrar inicio de implementación en diario-ia.md

**Objetivo**: Diseñar las estructuras de datos propias (MyList, MyQueue, MyStack, MyGraph) necesarias para el juego, especificando operaciones y justificando su uso.

**Instrucción dada al agente**:
> Basándote en el AGENTS.md y la estructura del proyecto, diseña las clases de estructuras de datos propias que necesito implementar: MyList, MyQueue, MyStack y MyGraph. Para cada una indica qué operaciones debe tener y por qué la necesita en el juego.

**Qué generó**:
- Análisis completo de 4 estructuras de datos con interfaces genéricas:
  - **MyList<T>**: Lista enlazada con 10 operaciones (add, remove, get, size, isEmpty, contains, clear, iterator)
  - **MyQueue<T>**: Cola FIFO con 6 operaciones (enqueue, dequeue, peek, isEmpty, size, clear)
  - **MyStack<T>**: Pila LIFO con 6 operaciones (push, pop, peek, isEmpty, size, clear)
  - **MyGraph<T>**: Grafo con 13 operaciones incluyendo algoritmos BFS, DFS y Dijkstra
- Tabla de dependencias mostrando que MyList es estructura base (crítica)
- Casos de uso concretos: inventario, gestor de turnos, historial de acciones, mapa de habitaciones
- 5 preguntas de clarificación sobre dirigibilidad, pesos, necesidad de Stack, búsquedas e iteradores

**Qué fue correcto**:
- Las interfaces están bien definidas, son genéricas y siguen convenciones Java
- La justificación de cada estructura está vinculada directamente a mecánicas del juego
- Los casos de uso son concretos y visuales (diagramas ASCII)
- La tabla de dependencias identifica correctamente que MyList es base para todas las demás
- Las preguntas de clarificación son pertinentes y evitarán refactorización posterior
- El análisis de implementación (matriz vs lista de adyacencia) es técnicamente sólido

**Qué se corrigió manualmente**:
- Nada. El diseño fue completo y no requirió correcciones.

**Aprendizaje metodológico**:
1. **Diseño antes de código**: Especificar operaciones antes de implementar evita sobre-ingeniería y refactorización
2. **Dependencias claras**: Identificar que MyList es base permite implementar en orden correcto
3. **Casos de uso reales**: Vincular cada estructura a mecánicas del juego (turnos, inventario, mapa) hace el diseño más robusto
4. **Preguntas sin respuesta**: Es mejor identificar ambigüedades en diseño que descubrirlas durante implementación
5. **Genéricos desde el inicio**: Usar <T> en las interfaces permite reutilización sin duplicación de código

**Ajuste para la próxima sesión**:
1. **Antes de implementar**: Responder las 5 preguntas de clarificación (dirigibilidad del grafo, pesos, necesidad de Stack, métodos de búsqueda, iteradores)
2. **Orden de implementación**: MyList → MyQueue → MyStack → MyGraph (respetando dependencias)
3. **Verificación de restricciones**: Invocar `@structures-guard` después de cada estructura para garantizar que no hay java.util.*
4. **Tests desde el inicio**: Escribir tests JUnit para cada estructura antes de pasar a la siguiente
5. **Documentación**: Añadir Javadoc a cada método conforme se implementa
6. **Revisión**: Usar `@revisor` después de cada estructura completada para validar diseño e implementación

---

<!-- añadir nuevas sesiones aquí -->

### Sesión 5 — 08/05/2026 — Agente: plan + @docs

**Objetivo**: Generar diagramas UML en formato PlantUML separados por clase y definir la estructura de carpetas del proyecto.

**Instrucción dada al agente**: 
> Quiero que me des los diagramas UML de manera separada para cada clase del juego. También necesito saber cuál es la estructura de carpetas propuesta para organizar todo el proyecto.

**Qué generó**:
- 8 archivos PlantUML separados (uno por clase + 1 de relaciones globales):
  - `TipoCelda.puml`, `Celda.puml`, `MatrizHabitacion.puml`, `Habitacion.puml`
  - `ParIdNombre.puml`, `ParNombreHabitacion.puml`, `GrafoHabitacion.puml`, `RelacionesGlobales.puml`
- Archivo `DIAGRAMAS_UML.md` con documentación completa de cada clase
- Dos esquemas visuales de estructura de carpetas (estado actual y propuesta)
- Commit exitoso en GitHub con los diagramas

**Qué fue correcto**:
- Separar cada diagrama en su propio archivo .puml facilita la visualización individual en IntelliJ
- Proporcionar el código PlantUML completo (con @startuml/@enduml) permite copiar-pegar directamente
- El esquema visual en formato de árbol (tree view) es claro y fácil de seguir
- La estructura propuesta (`es/universidad/juego/model/habitacion/`) respeta las convenciones de AGENTS.md

**Qué se corrigió manualmente**:
- Se corrigieron problemas de sincronización con el repositorio remoto (git pull antes de push)
- Se actualizó el estado de implementación en DIAGRAMAS_UML.md tras обнаружения de clases ya implementadas en el merge

**Aprendizaje metodológico**:
1. **Pedir diagramas separados**: Cada clase en su propio archivo .puml mejora la mantenibilidad y permite visualizar individualmente
2. **Verificar estado del repo antes de hacer push**: Hacer `git pull` antes de `git push` evita conflictos de rechazo
3. **Esquemas visuales > texto**: Un árbol de carpetas (tree view) es más intuitivo que descripciones textuales
4. **PlantUML en IntelliJ**: La extensión PlantUML permite vista previa en tiempo real, facilitando iteraciones rápidas

**Ajuste para la próxima sesión**:
- Mantener los diagramas .puml separados y actualizarlos junto con el código fuente
- Usar git status y git log antes de hacer commits para evitar conflictos
- Considerar migrar a Maven/Gradle para facilitar compilación y gestión de dependencias
- Antes de reorganizar carpetas, crear backup del repositorio o confirmar con el equipo

---

### Sesión 6 — 09/05/2026 — Agente: plan + @docs

**Objetivo**: Revisar progreso acumulado, verificar estado del repositorio, y definir prioridad clara para la siguiente fase de implementación.

**Instrucción dada al agente**: 
> Lee AGENTS.md y diario-ia.md. Resumen de dónde estamos, qué falta, opciones de próximos pasos y qué conviene priorizar.

**Qué generó**:
- Resumen estructurado del estado actual (✅ completado vs ⏳ pendiente)
- Identificación de 6 opciones de próximos pasos:
  - A. Clases auxiliares (ParIdNombre, ParNombreHabitacion)
  - B. GrafoHabitacion (integra todo)
  - C. Entidades (Jugador, Enemigo, Objeto)
  - D. Reorganizar estructura de carpetas
  - E. Tests unitarios
  - F. Otra cosa
- Propuesta de continuar con flujo: **plan → build → @revisor → @structures-guard → @docs**

**Qué fue correcto**:
- Lectura clara de AGENTS.md y diario-ia.md facilitó comprensión del estado
- Clasificación de tareas pendientes (auxiliares, entidades, lógica, reorganización)
- Flujograma de opciones permite al usuario elegir prioridad sin ambigüedad
- Reiteración del flujo de trabajo recomendado refuerza convenciones del proyecto

**Qué se corrigió manualmente**:
- Se detectó entrada duplicada de Sesión 3 al final del diario (líneas 196-228)
- Se corrigió orden lógico: debe estar Sesión 5 → Sesión 6 (no Sesión 3 duplicada)

**Aprendizaje metodológico**:
1. **Revisar antes de continuar**: Leer documentación existente (AGENTS.md, diario-ia.md) en cada nueva sesión evita repetir trabajo
2. **Checkpoint regularmente**: Después de cada entrega significativa (diagramas, documentación), hacer un resumen de estado
3. **Opciones explícitas**: Presentar opciones claras al usuario reduce incertidumbre y acelera toma de decisiones
4. **Flujo consistente**: Reiterar el flujo recomendado (plan → build → revisor → structures-guard → docs) en cada sesión

**Ajuste para la próxima sesión**:
- Esperar que el usuario seleccione una opción (A-F) para continuar
- Una vez elegida opción, comenzar con **modo plan**: diseñar la clase/tarea antes de implementar
- Documentar cada paso en diario-ia.md conforme avanza
- Invocar @structures-guard tras implementar cualquier estructura de datos
- Mantener diagramas .puml sincronizados con cambios de código
