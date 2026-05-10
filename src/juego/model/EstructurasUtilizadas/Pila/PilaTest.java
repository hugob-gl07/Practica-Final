package juego.model.EstructurasUtilizadas.Pila;
import juego.model.Exceptions.PilaVaciaExceptions;
/**
 * Suite de tests manuales para juego.model.EstructurasUtilizadas.Pila.
 * No requiere JUnit. Ejecutar con el botón ▶️ de IntelliJ.
 *
 * NOTAS DE ANÁLISIS PREVIO:
 *   - Orden LIFO: el último en entrar es el primero en salir.
 *   - pop()  en vacía → PilaVaciaExceptions.
 *   - peek() en vacía → PilaVaciaExceptions.
 *   - push() es void, no devuelve nada.
 *   - No existe size() ni clear() — se trabaja solo con isEmpty() y pop().
 *   - peek() NO modifica la pila (verificable con pop posterior).
 *   - Tipos genéricos siempre explícitos: new juego.model.EstructurasUtilizadas.Pila<Integer>().
 */
public class PilaTest {

    private static int totalTests  = 0;
    private static int totalPassed = 0;
    private static int totalFailed = 0;

    public static void main(String[] args) {

        printHeader("PILA VACÍA");
        test_isEmpty_pilaVacia();
        test_pop_pilaVacia_lanzaExcepcion();
        test_peek_pilaVacia_lanzaExcepcion();
        test_pop_mensaje_descriptivo();
        test_peek_mensaje_descriptivo();

        printHeader("PUSH — inserción en cima");
        test_push_unElemento_noEstaVacia();
        test_push_esVoid_noLanzaExcepcion();
        test_push_varios_pilaNoVacia();
        test_push_lifoOrden_verificadoConPop();

        printHeader("POP — extracción LIFO");
        test_pop_devuelveUltimoInsertado();
        test_pop_ordenLIFO_estricto();
        test_pop_unicoElemento_pilaVacia();
        test_pop_variosHastaVaciar();
        test_pop_variosHastaVaciar_lanzaExcepcion();
        test_pop_cantidadExacta_deExtracciones();

        printHeader("PEEK — consulta sin extracción");
        test_peek_devuelveCima();
        test_peek_noModificaPila_puedoPopDespues();
        test_peek_repetido_siempreDevuelveLaMismaCima();
        test_peek_trasPush_esElNuevoElemento();

        printHeader("isEmpty — estado de la pila");
        test_isEmpty_trasPush_esFalse();
        test_isEmpty_trasPushYPop_esTrue();
        test_isEmpty_trasPushVariosYVaciar_esTrue();
        test_isEmpty_reutilizacion_trasPushYPop();

        printHeader("CICLO LIFO — push + pop intercalados");
        test_lifo_pushPopIntercalados();
        test_lifo_pushVarios_popTodos_enOrden();
        test_lifo_vaciar_reinsertar_funcionaCorrectamente();

        printHeader("TIPOS GENÉRICOS");
        test_genericos_conStrings();
        test_genericos_conDoubles();

        printHeader("CASOS DE BORDE");
        test_borde_pushPopUnicoElemento();
        test_borde_pushMismoValorVarias_lifoEstricto();
        test_borde_peekTrasPop_actualizaCima();
        test_borde_pushDespuesDeVaciar_funcionaCorrectamente();

        printHeader("VOLUMEN");
        test_volumen_100push_ordenLIFO();
        test_volumen_pushPop_alternados();

        printResumen();
    }

    // ==================================================================
    // PILA VACÍA
    // ==================================================================

    static void test_isEmpty_pilaVacia() {
        Pila<Integer> p = new Pila<Integer>();
        assertIsTrue("Vacía: isEmpty() es true", p.isEmpty());
    }

    static void test_pop_pilaVacia_lanzaExcepcion() {
        Pila<Integer> p = new Pila<Integer>();
        assertThrows("Vacía: pop() lanza PilaVaciaExceptions",
                PilaVaciaExceptions.class, () -> p.pop());
    }

    static void test_peek_pilaVacia_lanzaExcepcion() {
        Pila<Integer> p = new Pila<Integer>();
        assertThrows("Vacía: peek() lanza PilaVaciaExceptions",
                PilaVaciaExceptions.class, () -> p.peek());
    }

    static void test_pop_mensaje_descriptivo() {
        Pila<Integer> p = new Pila<Integer>();
        try {
            p.pop();
            registrarFallo("pop vacía: no se lanzó excepción");
        } catch (PilaVaciaExceptions e) {
            assertIsTrue("pop vacía: mensaje no nulo ni vacío",
                    e.getMessage() != null && !e.getMessage().isBlank());
        }
    }

    static void test_peek_mensaje_descriptivo() {
        Pila<Integer> p = new Pila<Integer>();
        try {
            p.peek();
            registrarFallo("peek vacía: no se lanzó excepción");
        } catch (PilaVaciaExceptions e) {
            assertIsTrue("peek vacía: mensaje no nulo ni vacío",
                    e.getMessage() != null && !e.getMessage().isBlank());
        }
    }

    // ==================================================================
    // PUSH
    // ==================================================================

    static void test_push_unElemento_noEstaVacia() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(1);
        assertIsFalse("push: pila no vacía tras insertar", p.isEmpty());
    }

    static void test_push_esVoid_noLanzaExcepcion() {
        // push() es void — verificamos que no lanza excepción
        Pila<Integer> p = new Pila<Integer>();
        try {
            p.push(42);
            registrarOk("push: es void y no lanza excepción");
            totalTests++;
        } catch (Exception e) {
            totalTests++;
            registrarFallo("push: lanzó excepción inesperada: " + e.getClass().getSimpleName());
        }
    }

    static void test_push_varios_pilaNoVacia() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(1); p.push(2); p.push(3);
        assertIsFalse("push varios: pila no vacía", p.isEmpty());
    }

    static void test_push_lifoOrden_verificadoConPop() {
        // Insertamos 1, 2, 3 → pop debe devolver 3, 2, 1
        Pila<Integer> p = new Pila<Integer>();
        p.push(1); p.push(2); p.push(3);
        assertEqual("push LIFO: 1er pop es 3 (último insertado)", 3, p.pop());
        assertEqual("push LIFO: 2do pop es 2",                    2, p.pop());
        assertEqual("push LIFO: 3er pop es 1",                    1, p.pop());
    }

    // ==================================================================
    // POP
    // ==================================================================

    static void test_pop_devuelveUltimoInsertado() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(10); p.push(20); p.push(30);
        assertEqual("pop: devuelve 30 (último insertado)", 30, p.pop());
    }

    static void test_pop_ordenLIFO_estricto() {
        Pila<Integer> p = new Pila<Integer>();
        int[] vals = {5, 15, 25, 35, 45};
        for (int v : vals) p.push(v);
        boolean lifoOk = true;
        for (int i = vals.length - 1; i >= 0; i--) {
            int extraido = p.pop();
            if (extraido != vals[i]) { lifoOk = false; break; }
        }
        assertIsTrue("pop: orden LIFO estricto respetado", lifoOk);
    }

    static void test_pop_unicoElemento_pilaVacia() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(99);
        p.pop();
        assertIsTrue("pop único: pila vacía tras extraer", p.isEmpty());
    }

    static void test_pop_variosHastaVaciar() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(1); p.push(2); p.push(3);
        p.pop(); p.pop(); p.pop();
        assertIsTrue("pop hasta vaciar: isEmpty() es true", p.isEmpty());
    }

    static void test_pop_variosHastaVaciar_lanzaExcepcion() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(1); p.push(2);
        p.pop(); p.pop();
        assertThrows("pop tras vaciar: lanza PilaVaciaExceptions",
                PilaVaciaExceptions.class, () -> p.pop());
    }

    static void test_pop_cantidadExacta_deExtracciones() {
        // Tras N push, deben ser posibles exactamente N pop
        Pila<Integer> p = new Pila<Integer>();
        int n = 5;
        for (int i = 0; i < n; i++) p.push(i);
        int count = 0;
        try {
            while (true) { p.pop(); count++; }
        } catch (PilaVaciaExceptions e) { /* esperada */ }
        assertEqual("pop: exactamente N extracciones posibles", n, count);
    }

    // ==================================================================
    // PEEK
    // ==================================================================

    static void test_peek_devuelveCima() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(10); p.push(20); p.push(30);
        assertEqual("peek: devuelve 30 (la cima)", 30, p.peek());
    }

    static void test_peek_noModificaPila_puedoPopDespues() {
        // peek no extrae → el siguiente pop debe devolver el mismo valor
        Pila<Integer> p = new Pila<Integer>();
        p.push(10); p.push(20);
        int vistoPorPeek = p.peek();
        int vistoPorPop  = p.pop();
        assertEqual("peek: devuelve el mismo valor que el pop posterior", vistoPorPeek, vistoPorPop);
        // Además, la pila aún tiene elementos (el 10)
        assertIsFalse("peek: pila no vacía tras peek+pop", p.isEmpty());
    }

    static void test_peek_repetido_siempreDevuelveLaMismaCima() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(5); p.push(99);
        assertEqual("peek x1: devuelve 99", 99, p.peek());
        assertEqual("peek x2: sigue devolviendo 99", 99, p.peek());
        assertEqual("peek x3: sigue devolviendo 99", 99, p.peek());
        assertIsFalse("peek repetido: pila no vacía", p.isEmpty());
    }

    static void test_peek_trasPush_esElNuevoElemento() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(1); p.push(2);
        assertEqual("peek antes de push: cima es 2", 2, p.peek());
        p.push(3);
        assertEqual("peek tras push(3): cima pasa a ser 3", 3, p.peek());
    }

    // ==================================================================
    // isEmpty
    // ==================================================================

    static void test_isEmpty_trasPush_esFalse() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(1);
        assertIsFalse("isEmpty: false tras push", p.isEmpty());
    }

    static void test_isEmpty_trasPushYPop_esTrue() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(1); p.pop();
        assertIsTrue("isEmpty: true tras push+pop del único", p.isEmpty());
    }

    static void test_isEmpty_trasPushVariosYVaciar_esTrue() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(1); p.push(2); p.push(3);
        p.pop(); p.pop(); p.pop();
        assertIsTrue("isEmpty: true tras vaciar pila de 3", p.isEmpty());
    }

    static void test_isEmpty_reutilizacion_trasPushYPop() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(1); p.pop(); // vacía
        p.push(2);          // reinsertamos
        assertIsFalse("isEmpty: false tras reinsertar en pila vaciada", p.isEmpty());
        assertEqual("peek tras reinsertar: cima es 2", 2, p.peek());
    }

    // ==================================================================
    // CICLO LIFO
    // ==================================================================

    static void test_lifo_pushPopIntercalados() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(1); p.push(2);
        assertEqual("LIFO intercalado: pop es 2", 2, p.pop());
        p.push(3);
        assertEqual("LIFO intercalado: pop es 3", 3, p.pop());
        assertEqual("LIFO intercalado: pop es 1", 1, p.pop());
        assertIsTrue("LIFO intercalado: vacía al final", p.isEmpty());
    }

    static void test_lifo_pushVarios_popTodos_enOrden() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(100); p.push(200); p.push(300); p.push(400);
        assertEqual("LIFO orden: 1er pop es 400", 400, p.pop());
        assertEqual("LIFO orden: 2do pop es 300", 300, p.pop());
        assertEqual("LIFO orden: 3er pop es 200", 200, p.pop());
        assertEqual("LIFO orden: 4to pop es 100", 100, p.pop());
        assertIsTrue("LIFO orden: vacía al terminar", p.isEmpty());
    }

    static void test_lifo_vaciar_reinsertar_funcionaCorrectamente() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(1); p.push(2);
        p.pop(); p.pop(); // vaciamos
        p.push(10); p.push(20);
        assertEqual("LIFO reinsertar: 1er pop es 20", 20, p.pop());
        assertEqual("LIFO reinsertar: 2do pop es 10", 10, p.pop());
        assertIsTrue("LIFO reinsertar: vacía al final", p.isEmpty());
    }

    // ==================================================================
    // TIPOS GENÉRICOS
    // ==================================================================

    static void test_genericos_conStrings() {
        Pila<String> p = new Pila<String>();
        p.push("primero"); p.push("segundo"); p.push("tercero");
        assertEqualString("String: peek es 'tercero'",         "tercero", p.peek());
        assertEqualString("String: 1er pop es 'tercero'",      "tercero", p.pop());
        assertEqualString("String: 2do pop es 'segundo'",      "segundo", p.pop());
        assertEqualString("String: 3er pop es 'primero'",      "primero", p.pop());
        assertIsTrue("String: vacía al terminar", p.isEmpty());
    }

    static void test_genericos_conDoubles() {
        Pila<Double> p = new Pila<Double>();
        p.push(1.1); p.push(2.2); p.push(3.3);
        assertEqualDouble("Double: peek es 3.3",    3.3, p.peek());
        assertEqualDouble("Double: 1er pop es 3.3", 3.3, p.pop());
        assertEqualDouble("Double: 2do pop es 2.2", 2.2, p.pop());
        assertEqualDouble("Double: 3er pop es 1.1", 1.1, p.pop());
    }

    // ==================================================================
    // CASOS DE BORDE
    // ==================================================================

    static void test_borde_pushPopUnicoElemento() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(42);
        assertEqual("Borde único: pop devuelve 42", 42, p.pop());
        assertIsTrue("Borde único: vacía tras pop", p.isEmpty());
        assertThrows("Borde único: siguiente pop lanza excepción",
                PilaVaciaExceptions.class, () -> p.pop());
    }

    static void test_borde_pushMismoValorVarias_lifoEstricto() {
        // Duplicados: cada push apila uno nuevo, el LIFO sigue siendo correcto
        Pila<Integer> p = new Pila<Integer>();
        p.push(7); p.push(7); p.push(7);
        assertEqual("Borde duplicados: 1er pop es 7", 7, p.pop());
        assertEqual("Borde duplicados: 2do pop es 7", 7, p.pop());
        assertEqual("Borde duplicados: 3er pop es 7", 7, p.pop());
        assertIsTrue("Borde duplicados: vacía al final", p.isEmpty());
    }

    static void test_borde_peekTrasPop_actualizaCima() {
        // Tras un pop, peek debe reflejar la nueva cima
        Pila<Integer> p = new Pila<Integer>();
        p.push(1); p.push(2); p.push(3);
        assertEqual("Borde peek: cima inicial es 3",    3, p.peek());
        p.pop();
        assertEqual("Borde peek: tras pop, cima es 2",  2, p.peek());
        p.pop();
        assertEqual("Borde peek: tras 2 pops, cima es 1", 1, p.peek());
    }

    static void test_borde_pushDespuesDeVaciar_funcionaCorrectamente() {
        Pila<Integer> p = new Pila<Integer>();
        p.push(1); p.push(2); p.push(3);
        p.pop(); p.pop(); p.pop(); // vaciamos
        assertIsTrue("Borde vaciar: isEmpty true", p.isEmpty());
        p.push(99);
        assertIsFalse("Borde vaciar: no vacía tras reinsertar", p.isEmpty());
        assertEqual("Borde vaciar: peek es 99", 99, p.peek());
        assertEqual("Borde vaciar: pop es 99",  99, p.pop());
        assertIsTrue("Borde vaciar: vacía al final", p.isEmpty());
    }

    // ==================================================================
    // VOLUMEN
    // ==================================================================

    static void test_volumen_100push_ordenLIFO() {
        Pila<Integer> p = new Pila<Integer>();
        for (int i = 1; i <= 100; i++) p.push(i);

        // El peek debe ser el último insertado (100)
        assertEqual("Volumen 100: peek es 100 (último insertado)", 100, p.peek());

        // Los pops deben salir en orden descendente: 100, 99, 98, ...
        boolean lifoOk = true;
        for (int i = 100; i >= 1; i--) {
            int val = p.pop();
            if (val != i) { lifoOk = false; break; }
        }
        assertIsTrue("Volumen 100: orden LIFO correcto (100→1)", lifoOk);
        assertIsTrue("Volumen 100: vacía al terminar", p.isEmpty());
    }

    static void test_volumen_pushPop_alternados() {
        /*
         * ANÁLISIS DEL COMPORTAMIENTO REAL (aprendiendo de la juego.model.EstructurasUtilizadas.ListaCircular):
         *
         * En cada iteración:
         *   - push(i)        → cima es i
         *   - push(i + 100)  → cima es i+100
         *   - pop()          → devuelve i+100 (el último insertado)
         *   - pop()          → devuelve i     (el penúltimo)
         *
         * No queda estado residual entre iteraciones porque hacemos 2 push + 2 pop.
         */
        Pila<Integer> p = new Pila<Integer>();
        boolean ok = true;

        for (int i = 1; i <= 50; i++) {
            p.push(i);
            p.push(i + 100);
            int primero = p.pop(); // debe ser i+100
            int segundo = p.pop(); // debe ser i
            if (primero != i + 100 || segundo != i) { ok = false; break; }
        }
        assertIsTrue("Volumen alternado: 2 push + 2 pop por ciclo, LIFO correcto", ok);
        assertIsTrue("Volumen alternado: pila vacía al terminar", p.isEmpty());

        // Verificamos que la pila sigue usable tras los 50 ciclos
        p.push(999);
        assertEqual("Volumen alternado: pila reutilizable tras ciclos", 999, p.pop());
    }

    // ==================================================================
    // MOTOR DE ASERCIONES (sin JUnit)
    // ==================================================================

    @FunctionalInterface interface Accion { void ejecutar(); }

    static void assertEqual(String nombre, int esperado, int actual) {
        totalTests++;
        if (esperado == actual) registrarOk(nombre);
        else registrarFallo(nombre + " → esperado: " + esperado + ", obtenido: " + actual);
    }

    static void assertEqualString(String nombre, String esperado, String actual) {
        totalTests++;
        if (esperado.equals(actual)) registrarOk(nombre);
        else registrarFallo(nombre + " → esperado: '" + esperado + "', obtenido: '" + actual + "'");
    }

    static void assertEqualDouble(String nombre, double esperado, double actual) {
        totalTests++;
        if (Math.abs(esperado - actual) < 0.0001) registrarOk(nombre);
        else registrarFallo(nombre + " → esperado: " + esperado + ", obtenido: " + actual);
    }

    static void assertIsTrue(String nombre, boolean c) {
        totalTests++;
        if (c) registrarOk(nombre);
        else   registrarFallo(nombre + " → esperado: true, obtenido: false");
    }

    static void assertIsFalse(String nombre, boolean c) {
        totalTests++;
        if (!c) registrarOk(nombre);
        else    registrarFallo(nombre + " → esperado: false, obtenido: true");
    }

    static void assertThrows(String nombre, Class<? extends Exception> tipo, Accion accion) {
        totalTests++;
        try { accion.ejecutar(); registrarFallo(nombre + " → no se lanzó excepción"); }
        catch (Exception e) {
            if (tipo.isInstance(e)) registrarOk(nombre);
            else registrarFallo(nombre + " → excepción incorrecta: " + e.getClass().getSimpleName());
        }
    }

    static void registrarOk(String n)    { totalPassed++; System.out.println("  [OK]   " + n); }
    static void registrarFallo(String n) { totalFailed++; System.out.println("  [FAIL] " + n); }

    static void printHeader(String titulo) {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  " + titulo);
        System.out.println("══════════════════════════════════════════");
    }

    static void printResumen() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  RESUMEN FINAL");
        System.out.println("══════════════════════════════════════════");
        System.out.println("  Total  : " + totalTests);
        System.out.println("  Passed : " + totalPassed + " ✅");
        System.out.println("  Failed : " + totalFailed + (totalFailed > 0 ? " ❌" : ""));
        System.out.println("══════════════════════════════════════════");
        if (totalFailed == 0) System.out.println("  🎉 ¡Todos los tests han pasado!");
        else                  System.out.println("  ⚠️  Revisa los tests marcados [FAIL].");
        System.out.println("══════════════════════════════════════════\n");
    }
}
