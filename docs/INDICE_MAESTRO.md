# 📚 Índice Maestro - Documentación del Modelo UML

## 🎯 Inicio Rápido

**¿Dónde empezar?**
1. Lee: [`RESUMEN_MODELO_UML.txt`](RESUMEN_MODELO_UML.txt) (5 min)
2. Visualiza: [`docs/uml/modelo_juego.svg`](uml/modelo_juego.svg) (2 min)
3. Implementa: Sigue el orden en [`ESPECIFICACIONES_TECNICAS.md`](ESPECIFICACIONES_TECNICAS.md) (3.5 horas)

---

## 📄 Documentación Disponible

### 1. **RESUMEN_MODELO_UML.txt** ⭐ EMPEZAR AQUÍ
   - **Tamaño**: ~8 KB
   - **Tiempo de lectura**: 5-10 minutos
   - **Contenido**:
     - Resumen ejecutivo de las 6 nuevas clases
     - Tabla de métodos y complejidades
     - Orden de implementación recomendado
     - Restricciones críticas
     - Próximos pasos
   - **Para quién**: Todos (visión general rápida)

### 2. **uml/README_MODELO_UML.md**
   - **Tamaño**: ~7.7 KB
   - **Tiempo de lectura**: 15-20 minutos
   - **Contenido**:
     - Descripción detallada de cada clase
     - Métodos principales por clase
     - Tabla resumen de métodos
     - Orden de implementación
     - Restricciones y notas
   - **Para quién**: Desarrolladores (antes de implementar)

### 3. **ESPECIFICACIONES_TECNICAS.md** ⭐ REFERENCIA DURANTE IMPLEMENTACIÓN
   - **Tamaño**: ~15 KB
   - **Tiempo de lectura**: 30-45 minutos (consulta)
   - **Contenido**:
     - Especificación completa de cada clase
     - Atributos y tipos exactos
     - Constructores y métodos públicos
     - Detalles de implementación
     - Complejidades y excepciones
     - Restricciones por clase
   - **Para quién**: Desarrolladores (durante implementación)

### 4. **EJEMPLOS_Y_TESTS.md** ⭐ PARA TESTING
   - **Tamaño**: ~20 KB
   - **Tiempo de lectura**: 30-45 minutos (consulta)
   - **Contenido**:
     - Ejemplos de uso de cada clase
     - Casos de prueba JUnit completos
     - Escenarios integrados
     - Checklist de pruebas
   - **Para quién**: QA y desarrolladores (testing)

---

## 🖼️ Diagramas UML

### 1. **uml/modelo_juego.svg** ⭐ RECOMENDADO
   - **Formato**: SVG (escalable, interactivo en navegador)
   - **Tamaño**: 28 KB
   - **Contenido**: Diagrama UML completo con todas las clases y relaciones
   - **Ventajas**: 
     - Escalable sin pérdida de calidad
     - Abre en navegador
     - Ideal para presentaciones
   - **Cómo abrir**: Navegador web o editor de texto

### 2. **uml/modelo_juego.png**
   - **Formato**: PNG (rasterizado)
   - **Tamaño**: 285 KB
   - **Contenido**: Mismo diagrama que SVG
   - **Ventajas**: Compatible con cualquier aplicación
   - **Desventajas**: Puede pixelarse al ampliar

### 3. **uml/modelo_juego.dot**
   - **Formato**: Graphviz DOT (fuente)
   - **Tamaño**: 4.8 KB
   - **Contenido**: Código fuente del diagrama
   - **Uso**: Modificar y regenerar diagrama con `dot -Tsvg modelo_juego.dot -o modelo_juego.svg`

### 4. **uml/modelo_juego.puml**
   - **Formato**: PlantUML (fuente)
   - **Tamaño**: 4.2 KB
   - **Contenido**: Código fuente alternativo
   - **Uso**: Generar con PlantUML si se prefiere

---

## 🏗️ Estructura de Clases

```
NUEVAS CLASES A IMPLEMENTAR (6)

1. TipoCelda (Enum)
   ├─ Valores: VACIA, PUERTA, TRAMPA, PARED
   └─ Métodos: 0 (enum simple)

2. Celda
   ├─ Atributos: fila, columna, entidad, objeto, tipo
   ├─ Métodos: 14
   └─ Complejidad: O(1)

3. MatrizHabitacion
   ├─ Atributos: filas (LSE<LSE<Celda>>), numFilas, numColumnas
   ├─ Métodos: 11
   └─ Complejidad: O(n+m) acceso, O(n*m) filtrado

4. Puerta
   ├─ Atributos: habitacionDestino, etiqueta, abierta
   ├─ Métodos: 5
   └─ Complejidad: O(1)

5. Habitacion
   ├─ Atributos: id, nombre, matriz, puertas, turnsLimit, esExterior, visitada
   ├─ Métodos: 18
   └─ Complejidad: O(1-n*m)

6. GrafoHabitaciones
   ├─ Atributos: grafo, habitaciones, habitacionActual
   ├─ Métodos: 12
   └─ Complejidad: O(V+E) BFS, O((V+E)logV) Dijkstra

TOTAL: 54+ métodos públicos
```

---

## 📋 Orden de Implementación

```
FASE 1: TRIVIALES (sin dependencias)
  1️⃣  TipoCelda (enum)              ← Empezar aquí (5 min)
  2️⃣  Puerta                        ← Independiente (15 min)

FASE 2: BASE (dependen de Fase 1)
  3️⃣  Celda                         ← Usa TipoCelda (30 min)
  4️⃣  MatrizHabitacion              ← Usa Celda, LSE (60 min)

FASE 3: INTEGRACIÓN (dependen de Fase 2)
  5️⃣  Habitacion                    ← Usa MatrizHabitacion, Puerta (45 min)
  6️⃣  GrafoHabitaciones             ← Usa Habitacion, Grafo existente (45 min)

TOTAL: ~3.5 horas
```

---

## 🔍 Cómo Usar Esta Documentación

### Para Entender la Arquitectura
1. Lee: `RESUMEN_MODELO_UML.txt`
2. Visualiza: `uml/modelo_juego.svg`
3. Consulta: `uml/README_MODELO_UML.md`

### Para Implementar una Clase
1. Consulta: `ESPECIFICACIONES_TECNICAS.md` (tu clase específica)
2. Revisa: `EJEMPLOS_Y_TESTS.md` (ejemplos de uso)
3. Implementa: Sigue la especificación exactamente
4. Testa: Usa los casos de prueba de `EJEMPLOS_Y_TESTS.md`

### Para Revisar el Código
1. Consulta: `ESPECIFICACIONES_TECNICAS.md` (métodos esperados)
2. Verifica: Todos los métodos están implementados
3. Valida: Complejidades y restricciones
4. Usa: `@structures-guard` para detectar java.util.*

### Para Escribir Tests
1. Consulta: `EJEMPLOS_Y_TESTS.md` (casos de prueba)
2. Copia: Los tests JUnit proporcionados
3. Adapta: Según tu implementación
4. Ejecuta: `mvn test`

---

## 📊 Tabla de Referencia Rápida

| Clase | Métodos | Complejidad | Tiempo | Dependencias |
|-------|---------|-------------|--------|--------------|
| TipoCelda | 4 vals | N/A | 5 min | Ninguna |
| Celda | 14 | O(1) | 30 min | TipoCelda |
| MatrizHabitacion | 11 | O(n+m) | 60 min | Celda, LSE |
| Puerta | 5 | O(1) | 15 min | Ninguna |
| Habitacion | 18 | O(1-n*m) | 45 min | MatrizHabitacion, Puerta |
| GrafoHabitaciones | 12 | O(V+E) | 45 min | Habitacion, Grafo |

---

## ✅ Checklist de Implementación

### Antes de Empezar
- [ ] Leer `RESUMEN_MODELO_UML.txt`
- [ ] Visualizar `uml/modelo_juego.svg`
- [ ] Entender el orden de implementación
- [ ] Revisar restricciones críticas

### Para Cada Clase
- [ ] Leer especificación en `ESPECIFICACIONES_TECNICAS.md`
- [ ] Revisar ejemplos en `EJEMPLOS_Y_TESTS.md`
- [ ] Implementar clase
- [ ] Escribir tests JUnit
- [ ] Ejecutar tests (100% pass)
- [ ] Usar `@structures-guard` para verificar restricciones
- [ ] Usar `@revisor` para revisar código
- [ ] Usar `@docs` para añadir Javadoc
- [ ] Registrar en `diario-ia.md`

### Después de Todas las Clases
- [ ] Ejecutar `mvn test` (todos los tests pasan)
- [ ] Ejecutar `mvn clean compile` (sin errores)
- [ ] Verificar cobertura de tests (>80%)
- [ ] Revisar Javadoc (100% métodos públicos)
- [ ] Verificar sin java.util.* (0 importaciones prohibidas)

---

## 🚀 Próximos Pasos

1. **Hoy**: Implementar TipoCelda, Puerta, Celda
2. **Mañana**: Implementar MatrizHabitacion, Habitacion
3. **Pasado**: Implementar GrafoHabitaciones, tests completos
4. **Integración**: Conectar con controladores JavaFX

---

## 📞 Soporte

### Preguntas Frecuentes

**P: ¿Por qué O(n+m) en MatrizHabitacion.getCelda()?**
R: Porque LSE requiere búsqueda secuencial. Es aceptable para matrices pequeñas (10x10).

**P: ¿Puedo usar HashMap para búsqueda rápida?**
R: NO. Viola la restricción de no usar java.util.*. Usa LSE.

**P: ¿Debo extender Grafo en GrafoHabitaciones?**
R: NO. Usa composición: `private Grafo grafo;`

**P: ¿Qué pasa si un índice es inválido?**
R: Lanza `ListaIndiceInvalidoExceptions` (excepción personalizada).

### Agentes Disponibles

- 🤖 **@revisor** - Revisar código antes de continuar
- 🤖 **@structures-guard** - Detectar java.util.* prohibido
- 🤖 **@docs** - Añadir Javadoc automáticamente
- 🤖 **@debug** - Depurar errores en tests

---

## 📝 Registro de Cambios

| Fecha | Versión | Cambios |
|-------|---------|---------|
| 2026-05-07 | 1.0 | Documentación inicial completa |

---

## 📄 Licencia y Créditos

Documentación generada automáticamente por OpenCode.  
Proyecto: Juego de Exploración por Habitaciones  
Equipo: [Añadir nombres]  
Asignaturas: Estructuras de Datos + Metodología de la Programación

---

**Última actualización**: 2026-05-07  
**Versión**: 1.0  
**Estado**: ✅ Completo y listo para implementación
