import juego.model.EstructurasUtilizadas.Colas.Cola.Cola;
import juego.model.Exceptions.ColaVaciaExceptions;

/**
 * Suite de tests manuales para Cola.
 * No requiere JUnit. Ejecutar con el botón ▶️ de IntelliJ.
 *
 * Resultado esperado: todos los tests deben mostrar [OK].
 */
public class TestColas {

    private static int totalTests  = 0;
    private static int totalPassed = 0;
    private static int totalFailed = 0;

    // ==================================================================
    // MAIN — punto de entrada
    // ==================================================================

    public static void main(String[] args) {

        printHeader("CASOS DE ÉXITO");
        test_enqueue_incrementaElementos();
        test_enqueue_ordenFIFO_unElemento();
        test_enqueue_ordenFIFO_variosElementos();
        test_dequeue_devuelvePrimero();
        test_dequeue_eliminaElemento();
        test_dequeue_vaciaCola();
        test_peek_devuelvePrimeroSinEliminar();
        test_peek_noModificaEstado();
        test_isEmpty_colaVacia();
        test_isEmpty_colaConElementos();
        test_isEmpty_trasDequeue();
        test_enqueue_yDequeue_intercalados();

        printHeader("CASOS DE ERROR");
        test_dequeue_colaVacia_lanzaExcepcion();
        test_peek_colaVacia_lanzaExcepcion();
        test_excepcion_mensajeDescriptivo_dequeue();
        test_excepcion_mensajeDescriptivo_peek();

        printHeader("CASOS DE BORDE");
        test_unElemento_enqueueDequeue();
        test_unElemento_peek();
        test_reutilizacion_trasVaciarCola();
        test_variosDequeue_hastaVaciar();
        test_genericos_conStrings();
        test_genericos_conDoubles();
        test_volumen(1);
        test_volumen(10);
        test_volumen(100);
        test_volumen(1000);
        test_fifo_estricto();

        printResumen();
    }

    // ==================================================================
    // CASOS DE ÉXITO
    // ==================================================================

    static void test_enqueue_incrementaElementos() {
        Cola<Integer> cola = new Cola<>();
        assertIsTrue("enqueue: cola vacía antes de insertar", cola.isEmpty());
        cola.enqueue(1);
        assertIsFalse("enqueue: cola no vacía tras insertar", cola.isEmpty());
    }

    static void test_enqueue_ordenFIFO_unElemento() {
        Cola<Integer> cola = new Cola<>();
        cola.enqueue(42);
        assertEqual("enqueue: peek devuelve el único elemento", 42, cola.peek());
    }

    static void test_enqueue_ordenFIFO_variosElementos() {
        Cola<Integer> cola = new Cola<>();
        cola.enqueue(1);
        cola.enqueue(2);
        cola.enqueue(3);
        assertEqual("FIFO: primer dequeue devuelve 1", 1, cola.dequeue());
        assertEqual("FIFO: segundo dequeue devuelve 2", 2, cola.dequeue());
        assertEqual("FIFO: tercer dequeue devuelve 3", 3, cola.dequeue());
    }

    static void test_dequeue_devuelvePrimero() {
        Cola<Integer> cola = new Cola<>();
        cola.enqueue(10);
        cola.enqueue(20);
        assertEqual("dequeue: devuelve el primero (10)", 10, cola.dequeue());
    }

    static void test_dequeue_eliminaElemento() {
        Cola<Integer> cola = new Cola<>();
        cola.enqueue(10);
        cola.enqueue(20);
        cola.dequeue();
        assertEqual("dequeue: tras eliminar, peek es 20", 20, cola.peek());
    }

    static void test_dequeue_vaciaCola() {
        Cola<Integer> cola = new Cola<>();
        cola.enqueue(5);
        cola.dequeue();
        assertIsTrue("dequeue: cola vacía tras extraer único elemento", cola.isEmpty());
    }

    static void test_peek_devuelvePrimeroSinEliminar() {
        Cola<Integer> cola = new Cola<>();
        cola.enqueue(99);
        cola.enqueue(100);
        assertEqual("peek: devuelve 99 sin eliminar", 99, cola.peek());
    }

    static void test_peek_noModificaEstado() {
        Cola<Integer> cola = new Cola<>();
        cola.enqueue(7);
        cola.enqueue(8);
        cola.peek(); // no debe modificar la cola
        assertEqual("peek: primer elemento sigue siendo 7 tras peek", 7, cola.dequeue());
        assertEqual("peek: segundo elemento sigue siendo 8",          8, cola.dequeue());
        assertIsTrue("peek: cola vacía al final", cola.isEmpty());
    }

    static void test_isEmpty_colaVacia() {
        Cola<Integer> cola = new Cola<>();
        assertIsTrue("isEmpty: cola nueva está vacía", cola.isEmpty());
    }

    static void test_isEmpty_colaConElementos() {
        Cola<Integer> cola = new Cola<>();
        cola.enqueue(1);
        assertIsFalse("isEmpty: cola con elementos no está vacía", cola.isEmpty());
    }

    static void test_isEmpty_trasDequeue() {
        Cola<Integer> cola = new Cola<>();
        cola.enqueue(1);
        cola.dequeue();
        assertIsTrue("isEmpty: vacía tras dequeue del único elemento", cola.isEmpty());
    }

    static void test_enqueue_yDequeue_intercalados() {
        Cola<Integer> cola = new Cola<>();
        cola.enqueue(1);
        cola.enqueue(2);
        assertEqual("intercalado: dequeue devuelve 1", 1, cola.dequeue());
        cola.enqueue(3);
        assertEqual("intercalado: dequeue devuelve 2", 2, cola.dequeue());
        assertEqual("intercalado: dequeue devuelve 3", 3, cola.dequeue());
        assertIsTrue("intercalado: vacía al final",        cola.isEmpty());
    }

    // ==================================================================
    // CASOS DE ERROR
    // ==================================================================

    static void test_dequeue_colaVacia_lanzaExcepcion() {
        Cola<Integer> cola = new Cola<>();
        assertThrows("dequeue en cola vacía lanza ColaVaciaExceptions",
                ColaVaciaExceptions.class, () -> cola.dequeue());
    }

    static void test_peek_colaVacia_lanzaExcepcion() {
        Cola<Integer> cola = new Cola<>();
        assertThrows("peek en cola vacía lanza ColaVaciaExceptions",
                ColaVaciaExceptions.class, () -> cola.peek());
    }

    static void test_excepcion_mensajeDescriptivo_dequeue() {
        Cola<Integer> cola = new Cola<>();
        try {
            cola.dequeue();
            registrarFallo("dequeue vacía: no lanzó excepción");
        } catch (ColaVaciaExceptions e) {
            assertIsTrue("dequeue vacía: mensaje no nulo ni vacío",
                    e.getMessage() != null && !e.getMessage().isBlank());
        }
    }

    static void test_excepcion_mensajeDescriptivo_peek() {
        Cola<Integer> cola = new Cola<>();
        try {
            cola.peek();
            registrarFallo("peek vacía: no lanzó excepción");
        } catch (ColaVaciaExceptions e) {
            assertIsTrue("peek vacía: mensaje no nulo ni vacío",
                    e.getMessage() != null && !e.getMessage().isBlank());
        }
    }

    // ==================================================================
    // CASOS DE BORDE
    // ==================================================================

    static void test_unElemento_enqueueDequeue() {
        Cola<Integer> cola = new Cola<>();
        cola.enqueue(42);
        assertEqual("Un elemento: dequeue devuelve 42", 42, cola.dequeue());
        assertIsTrue("Un elemento: vacía tras dequeue",    cola.isEmpty());
    }

    static void test_unElemento_peek() {
        Cola<Integer> cola = new Cola<>();
        cola.enqueue(42);
        assertEqual("Un elemento: peek devuelve 42",       42, cola.peek());
        assertIsFalse("Un elemento: no vacía tras peek", cola.isEmpty());
    }

    static void test_reutilizacion_trasVaciarCola() {
        Cola<Integer> cola = new Cola<>();
        cola.enqueue(1);
        cola.dequeue();
        // Reutilizamos la misma cola
        cola.enqueue(99);
        assertEqual("Reutilización: peek es 99 tras reinsertar", 99, cola.peek());
        assertIsFalse("Reutilización: cola no vacía",            cola.isEmpty());
    }

    static void test_variosDequeue_hastaVaciar() {
        Cola<Integer> cola = new Cola<>();
        cola.enqueue(1);
        cola.enqueue(2);
        cola.enqueue(3);
        cola.dequeue();
        cola.dequeue();
        cola.dequeue();
        assertIsTrue("Múltiples dequeue: cola vacía al final", cola.isEmpty());
    }

    static void test_genericos_conStrings() {
        Cola<String> cola = new Cola<>();
        cola.enqueue("primero");
        cola.enqueue("segundo");
        cola.enqueue("tercero");
        assertEqualString("Genérico String: dequeue1 es 'primero'", "primero", cola.dequeue());
        assertEqualString("Genérico String: dequeue2 es 'segundo'", "segundo", cola.dequeue());
        assertEqualString("Genérico String: dequeue3 es 'tercero'", "tercero", cola.dequeue());
        assertIsTrue("Genérico String: vacía al final", cola.isEmpty());
    }

    static void test_genericos_conDoubles() {
        Cola<Double> cola = new Cola<>();
        cola.enqueue(1.1);
        cola.enqueue(2.2);
        assertEqualDouble("Genérico Double: dequeue1 es 1.1", 1.1, cola.dequeue());
        assertEqualDouble("Genérico Double: dequeue2 es 2.2", 2.2, cola.dequeue());
    }

    static void test_volumen(int n) {
        Cola<Integer> cola = new Cola<>();
        for (int i = 0; i < n; i++) cola.enqueue(i);

        boolean ok = true;
        for (int i = 0; i < n; i++) {
            int val = cola.dequeue();
            if (val != i) { ok = false; break; }
        }
        assertIsTrue("Volumen(" + n + "): orden FIFO correcto", ok);
        assertIsTrue("Volumen(" + n + "): vacía al final",      cola.isEmpty());
    }

    static void test_fifo_estricto() {
        // Verifica que el orden FIFO se mantiene con enqueue/dequeue alternados
        Cola<Integer> cola = new Cola<>();
        cola.enqueue(100);
        cola.enqueue(200);
        cola.enqueue(300);
        assertEqual("FIFO estricto: peek es siempre el primero insertado", 100, cola.peek());
        assertEqual("FIFO estricto: dequeue1 es 100", 100, cola.dequeue());
        assertEqual("FIFO estricto: peek tras dequeue es 200", 200, cola.peek());
        assertEqual("FIFO estricto: dequeue2 es 200", 200, cola.dequeue());
        assertEqual("FIFO estricto: dequeue3 es 300", 300, cola.dequeue());
        assertIsTrue("FIFO estricto: vacía al final",     cola.isEmpty());
    }

    // ==================================================================
    // MOTOR DE ASERCIONES (sin JUnit)
    // ==================================================================

    @FunctionalInterface
    interface Accion { void ejecutar(); }

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

    static void assertIsTrue(String nombre, boolean condicion) {
        totalTests++;
        if (condicion) registrarOk(nombre);
        else registrarFallo(nombre + " → esperado: true, obtenido: false");
    }

    static void assertIsFalse(String nombre, boolean condicion) {
        totalTests++;
        if (!condicion) registrarOk(nombre);
        else registrarFallo(nombre + " → esperado: false, obtenido: true");
    }

    static void assertThrows(String nombre, Class<? extends Exception> tipo, Accion accion) {
        totalTests++;
        try {
            accion.ejecutar();
            registrarFallo(nombre + " → no se lanzó ninguna excepción");
        } catch (Exception e) {
            if (tipo.isInstance(e)) registrarOk(nombre);
            else registrarFallo(nombre + " → excepción incorrecta: " + e.getClass().getSimpleName());
        }
    }

    // ==================================================================
    // SALIDA POR CONSOLA
    // ==================================================================

    static void registrarOk(String nombre) {
        totalPassed++;
        System.out.println("  [OK]   " + nombre);
    }

    static void registrarFallo(String motivo) {
        totalFailed++;
        System.out.println("  [FAIL] " + motivo);
    }

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