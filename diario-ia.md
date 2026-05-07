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

### Sesión 2 — 06/05/2026 — Agente: plan

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
