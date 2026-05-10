# Diario de IA - Hugo

**Proyecto**: Juego de exploración por habitaciones — Práctica Conjunta ED + MP
**Fecha**: 10/05/2026
**Herramienta**: OpenCode 1.14.39

---

## Sesión 10 — 10/05/2026 — Revisión y corrección de código

**Objetivo**: Revisar todo el proyecto, identificar errores y corregir tests

**Correcciones realizadas**:
1. **Fórmula de combate**: Corregida según documento de prácticas
   - Jugador.atacar(): máximo(0, ataque * aleatorio * 2 - defensaEnemigo)
   - Enemigo.atacar(Jugador): máximo(0, ataque * aleatorio * 2 - defensaJugador)

2. **Tests de Jugador**: Corregido constructor (ahora requiere 3 parámetros)
   - Añadido parámetro habitacioninicial

3. **Packages de tests**: Corregidos de juego.model.entidades a es.universidad.juego.model.entidades

4. **Imports en tests**: Añadidos imports de clases del modelo (Enemigo, Jugador, Objeto, etc.)

5. **Test Enemigo.atacar()**: Corregido para usar el nuevo método con parámetro Jugador

**Aprendizaje metodológico**:
- Importante mantener sincronización con GitHub antes de hacer cambios
- Los tests deben actualizarse cuando los métodos cambian su firma
- Los packages deben coincidir entre código fuente y tests

---

## Sesión 7 — 09/05/2026 — Agente: build + @docs

**Objetivo**: Implementar tests unitarios para las clases de entidades y documentar la sesión

**Instrucción dada al agente**:
> Implementa todos los tests unitarios para Objeto, Inventario, Enemigo y Jugador. Incluye detección de bugs en Enemigo (vida negativa, estaMuerto incorrecto).

**Qué generó**:
- 89 tests unitarios en 5 archivos:
  - `ObjetoTest.java` (14 tests)
  - `InventarioTest.java` (23 tests)
  - `EnemigoTest.java` (17 tests con detección de bugs)
  - `JugadorTest.java` (35 tests)
  - `TestHelpers.java` (utilidades)
- Añadido `TipoCelda.SUELO` al enum
- Cambiado `private` → `protected` en las 4 clases de entidades
- Añadidos getters/setters para mantener encapsulamiento

**Qué fue correcto**:
- Tests organizados en `src/test/java/es/universidad/juego/model/entidades/`
- Tests de Enemigo incluyen detección de 2 bugs (vida negativa, estaMuerto)
- Getters/setters permiten tests sin exponer variables públicas

**Qué se corrigió manualmente**:
- Sincronización con GitHub (uso de `git reset --hard origin/main`)
- Problema de codificación con caracteres especiales (ñ)
- Error de TipoCelda.SUELO inexistente → se añadió al enum
- Tests accedían directamente a campos protected → se actualizaron para usar getters

**Aprendizaje metodológico**:
1. **Sincronización**: Siempre hacer `git pull` antes de trabajar, especialmente cuando hay cambios de otros colaboradores
2. **Tests en paquete correcto**: Los tests deben estar en el mismo paquete que las clases que prueban para acceder a `protected`
3. **Encapsulamiento**: Mejor usar getters/setters que hacer campos públicos
4. **Detección de bugs**: Los tests deben fallar intencionalmente para detectar bugs reales

**Ajuste para la próxima sesión**:
- Ejecutar los tests con `mvn test` para verificar que funcionan
- Verificar que los tests de detección de bugs fallan (comportamiento esperado)
- Corregir los bugs detectados en Enemigo.java una vez validados

---

## Sesión 8 — 10/05/2026 — Agente: build

**Objetivo**: Corregir los bugs detectados en Enemigo.java por los tests

**Qué se detectó**:
- 2 bugs en Enemigo.java identificados por los tests de EnemigoTest.java:
  1. `recibirDaño()`: verificaba `vidaActual < 0` ANTES de restar el daño (lógica incorrecta)
  2. `estaMuerto()`: solo retornaba true si `vidaActual == 0`, no manejaba valores negativos

**Correcciones aplicadas**:
1. `recibirDaño()` (línea 25-30):
   - Antes: verificaba negativo antes de restar, luego restaba sin limitar
   - Después: resta primero `vidaActual -= daño`, luego limita a 0 si es negativo

2. `estaMuerto()` (línea 37-39):
   - Antes: `return vidaActual == 0;`
   - Después: `return vidaActual <= 0;`

**Tests que deberían pasar ahora**:
- `testRecibirDano_VidaNoQuedaNegativa`
- `testRecibirDano_MultiplesDanosAcumulados`
- `testEstaMuerto_TrueCuandoVidaNegativa`

**Aprendizaje metodológico**:
1. **Lógica de validación**: Siempre validar DESPUÉS de modificar el valor, no antes
2. **Tests como documentación**: Los mensajes de error en los tests explican exactamente qué falla
3. **Corrección proactiva**: Los bugs detectados por tests deben corregirse inmediatamente

---

## Sesión 8 (continuación) — 10/05/2026 — Revisión de todos los tests

**Objetivo**: Revisar Jugador, Inventario y Objeto para detectar más bugs

**Correcciones adicionales aplicadas**:

1. **Objeto.java - getValorEstadisticas()** (línea 61-68):
   - Bug: NullPointerException cuando `nombre` es null
   - Corrección: Añadir verificación de null al inicio y verificar que el array no sea null

2. **Inventario.java - removeObjeto()** (línea 27-37):
   - Bug: No usaba `break` después de encontrar y eliminar el objeto
   - Corrección inicial: Añadir `break` (INCORRECTO - break/continue prohibidos)
   - Corrección final: Usar variable booleana `encontrado` en la condición del for
   - **ERROR REGISTRADO**: No usar `break` ni `continue` - usar variables de control

---

## Sesión 9 — 10/05/2026 — Corregir tests de Jugador

**Problema**: Tests de Jugador fallaban porque faltaban getters/setters

**Corrección aplicada en Jugador.java**:
- Añadido `getNombre()`
- Añadido `setNombre()`
- Añadido `getAtaque()`
- Añadido `getDefensa()`

**Resultado**: Los tests ahora pueden acceder a los campos protected mediante getters

---

## Sesión 9 (continuación) — 10/05/2026 — Corregir tests incorrectos

**Problema**: Algunos tests estaban mal escritos, no el código

**Correcciones aplicadas**:

1. **InventarioTest.java línea 84**:
   - Error: `assertEquals(2, ...)` después de eliminar 1 de 2 objetos
   - Corrección: `assertEquals(1, ...)`

2. **JugadorTest.java línea 307**:
   - Error: Daño de 50 con defensa 5 deja vida=5, no mata al jugador
   - Corrección: Daño de 55 con defensa 5 deja vida=0, sí mata

3. **EnemigoTest**: Sin cambios en código - necesita Rebuild Project en IntelliJ

**Revisión completada**:
- Jugador.java: ✅ Sin bugs detectados (recibirDaño ya estaba correcto)
- Inventario.java: ✅ Corregido
- Objeto.java: ✅ Corregido

---

## Notas sobre Enums (TipoObjeto, Rareza, TipoEnemigo)

**Aprendizaje extraído sobre enums en Java:**

Los enums implementados tienen:
- **Atributos**: Cada valor del enum puede tener datos asociados
- **Constructor privado**: El constructor es `private` (implícito o explícito)
- **Métodos**: Los enums pueden tener métodos propios

**Ejemplo de TipoObjeto:**
```java
public enum TipoObjeto {
    ARMA("Aumenta el daño"),
    POCIMA_VIDA("Curación"),
    // ...
    
    private final String descripcion;
    
    private TipoObjeto(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}
```

**Ventajas de este enfoque:**
- Valores con comportamiento asociado
- No se pueden crear instancias externas del enum
- IDE autocomplete para todos los valores
- Tipo seguro (no admite valores arbitrarios)

---

## Sesión 10 — 10/05/2026 — Enums con atributos y GitHub

**Aprendizaje sobre enums con atributos**:

Los enums pueden tener atributos asociados como multiplicadores y descripciones:

```java
public enum TipoObjeto {
    ARMA("Aumenta el daño", 1.5),
    POCIMA_VIDA("Curación", 1.0),
    ARMADURA("Aumenta la defensa", 1.3),
    LLAVE("Abre puertas", 1.0),
    TESORO("Objeto de valor", 2.0),
    CONSUMIBLE("Se agota al usar", 1.0),
    POCIMA_MANA("Restaura maná", 1.0);

    private final String descripcion;
    private final double multiplicador;

    private TipoObjeto(String descripcion, double multiplicador) {
        this.descripcion = descripcion;
        this.multiplicador = multiplicador;
    }

    public String getDescripcion() { return descripcion; }
    public double getMultiplicador() { return multiplicador; }
}
```

**Aprendizaje sobre constructores privados en enums**:
- Los constructores de enum son siempre `private` (implícito o explícito)
- No se puede instanciar un enum con `new`
- Se usan para inicializar los atributos de cada constante

**Aprendizaje sobre GitHub**:
- Los cambios se suben con: `git add .`, `git commit -m "mensaje"`, `git push origin main`
- Si hay conflictos, hacer `git pull origin main --rebase` antes de push
- Commit automático: "Bypassed rule violations" indica que se saltó el pull request

---

## Notas sobre GitHub y ramas

**Comandos útiles**:
- `git status` - Ver cambios pendientes
- `git add .` - Añadir todos los cambios
- `git commit -m "mensaje"` - Crear commit
- `git push origin main` - Subir a GitHub
- `git pull origin main` - Descargar cambios
- `git pull origin main --rebase` - Descargar con rebase si hay conflictos