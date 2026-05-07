package EstructurasUtilizadas.ListaCircular;
import Exceptions.ListaCircularExceptions;

/**
 * Suite de tests manuales para EstructurasUtilizadas.ListaCircular.
 * No requiere JUnit. Ejecutar con el botón ▶️ de IntelliJ.
 *
 * Propiedades clave de la Lista Circular:
 *   - El último elemento siempre apunta de vuelta a la cabeza.
 *   - insertar() añade al final manteniendo el ciclo.
 *   - eliminar() extrae siempre la cabeza (FIFO).
 *   - eliminar() en lista vacía lanza ListaCircularExceptions.
 */
public class ListaCircularTest {

    private static int totalTests  = 0;
    private static int totalPassed = 0;
    private static int totalFailed = 0;

    public static void main(String[] args) {

        printHeader("LISTA VACÍA");
        test_eliminar_listaVacia_lanzaExcepcion();
        test_excepcion_mensajeDescriptivo();

        printHeader("INSERCIÓN — insertar()");
        test_insertar_unElemento_eliminaCorrectamente();
        test_insertar_dosElementos_ordenFIFO();
        test_insertar_variosElementos_ordenFIFO();
        test_insertar_despuesDeEliminar_funcionaCorrectamente();

        printHeader("ELIMINACIÓN — eliminar()");
        test_eliminar_devuelvePrimeroInsertado();
        test_eliminar_unicoElemento_listaVacia();
        test_eliminar_variosHastaVaciar_lanzaExcepcion();
        test_eliminar_orden_FIFO_estricto();
        test_eliminar_size_implicito();

        printHeader("CICLO CIRCULAR — propiedad estructural");
        test_circular_reutilizacion_trasSingleEliminar();
        test_circular_insertar_eliminar_intercalados();
        test_circular_eliminar_todo_reinsertar();

        printHeader("TIPOS GENÉRICOS");
        test_genericos_conStrings();
        test_genericos_conDoubles();

        printHeader("CASOS DE BORDE");
        test_borde_unSoloElemento_insertar_eliminar();
        test_borde_dobleInsercion_mismoValor();
        test_borde_eliminar_hastaVaciar_exactamente();
        test_borde_insertar_despuesDeVaciar();

        printHeader("VOLUMEN");
        test_volumen_100elementos_ordenFIFO();
        test_volumen_insertar_eliminar_alternados();

        printResumen();
    }

    // ==================================================================
    // LISTA VACÍA
    // ==================================================================

    static void test_eliminar_listaVacia_lanzaExcepcion() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        assertThrows("Vacía: eliminar() lanza ListaCircularExceptions",
                ListaCircularExceptions.class, () -> l.eliminar());
    }

    static void test_excepcion_mensajeDescriptivo() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        try {
            l.eliminar();
            registrarFallo("excepción: no se lanzó");
        } catch (ListaCircularExceptions e) {
            assertIsTrue("excepción: mensaje no nulo ni vacío",
                    e.getMessage() != null && !e.getMessage().isBlank());
        }
    }

    // ==================================================================
    // INSERCIÓN
    // ==================================================================

    static void test_insertar_unElemento_eliminaCorrectamente() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        l.insertar(42);
        assertEqual("insertar: un elemento → eliminar devuelve 42", 42, l.eliminar());
    }

    static void test_insertar_dosElementos_ordenFIFO() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        l.insertar(1); l.insertar(2);
        assertEqual("insertar dos: 1er eliminar devuelve 1", 1, l.eliminar());
        assertEqual("insertar dos: 2do eliminar devuelve 2", 2, l.eliminar());
    }

    static void test_insertar_variosElementos_ordenFIFO() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        l.insertar(10); l.insertar(20); l.insertar(30); l.insertar(40);
        assertEqual("insertar varios: 1er eliminar es 10", 10, l.eliminar());
        assertEqual("insertar varios: 2do eliminar es 20", 20, l.eliminar());
        assertEqual("insertar varios: 3er eliminar es 30", 30, l.eliminar());
        assertEqual("insertar varios: 4to eliminar es 40", 40, l.eliminar());
    }

    static void test_insertar_despuesDeEliminar_funcionaCorrectamente() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        l.insertar(1); l.eliminar();
        l.insertar(99);
        assertEqual("insertar tras eliminar: devuelve 99", 99, l.eliminar());
    }

    // ==================================================================
    // ELIMINACIÓN
    // ==================================================================

    static void test_eliminar_devuelvePrimeroInsertado() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        l.insertar(5); l.insertar(10); l.insertar(15);
        assertEqual("eliminar: siempre devuelve la cabeza (5)", 5, l.eliminar());
    }

    static void test_eliminar_unicoElemento_listaVacia() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        l.insertar(7); l.eliminar();
        assertThrows("eliminar único: siguiente eliminar lanza excepción",
                ListaCircularExceptions.class, () -> l.eliminar());
    }

    static void test_eliminar_variosHastaVaciar_lanzaExcepcion() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        l.insertar(1); l.insertar(2); l.insertar(3);
        l.eliminar(); l.eliminar(); l.eliminar();
        assertThrows("eliminar hasta vaciar: siguiente lanza excepción",
                ListaCircularExceptions.class, () -> l.eliminar());
    }

    static void test_eliminar_orden_FIFO_estricto() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        int[] vals = {7, 3, 9, 1, 5};
        for (int v : vals) l.insertar(v);
        boolean fifoOk = true;
        for (int v : vals) {
            int extraido = l.eliminar();
            if (extraido != v) { fifoOk = false; break; }
        }
        assertIsTrue("eliminar: orden FIFO estricto respetado", fifoOk);
    }

    static void test_eliminar_size_implicito() {
        // Tras N inserciones deben ser posibles exactamente N eliminaciones
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        int n = 5;
        for (int i = 0; i < n; i++) l.insertar(i);
        int count = 0;
        try {
            while (true) { l.eliminar(); count++; }
        } catch (ListaCircularExceptions e) { /* esperada */ }
        assertEqual("eliminar: exactamente N extracciones posibles", n, count);
    }

    // ==================================================================
    // CICLO CIRCULAR — propiedad estructural
    // ==================================================================

    static void test_circular_reutilizacion_trasSingleEliminar() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        l.insertar(1); l.insertar(2); l.insertar(3);
        l.eliminar();        // extrae 1, queda [2, 3]
        l.insertar(4);       // añade 4, queda [2, 3, 4]
        assertEqual("circular: tras eliminar+insertar, 1er es 2", 2, l.eliminar());
        assertEqual("circular: tras eliminar+insertar, 2do es 3", 3, l.eliminar());
        assertEqual("circular: tras eliminar+insertar, 3er es 4", 4, l.eliminar());
    }

    static void test_circular_insertar_eliminar_intercalados() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        l.insertar(1); l.insertar(2);
        assertEqual("intercalado: eliminar 1", 1, l.eliminar());
        l.insertar(3);
        assertEqual("intercalado: eliminar 2", 2, l.eliminar());
        l.insertar(4);
        assertEqual("intercalado: eliminar 3", 3, l.eliminar());
        assertEqual("intercalado: eliminar 4", 4, l.eliminar());
    }

    static void test_circular_eliminar_todo_reinsertar() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        l.insertar(10); l.insertar(20);
        l.eliminar(); l.eliminar();      // lista vacía
        l.insertar(30); l.insertar(40); // reinsertamos
        assertEqual("circular reinsertar: 1er es 30", 30, l.eliminar());
        assertEqual("circular reinsertar: 2do es 40", 40, l.eliminar());
    }

    // ==================================================================
    // TIPOS GENÉRICOS
    // ==================================================================

    static void test_genericos_conStrings() {
        ListaCircular<String> l = new ListaCircular<String>();
        l.insertar("hola"); l.insertar("mundo"); l.insertar("circular");
        assertEqualString("String: 1er es 'hola'",     "hola",     l.eliminar());
        assertEqualString("String: 2do es 'mundo'",    "mundo",    l.eliminar());
        assertEqualString("String: 3er es 'circular'", "circular", l.eliminar());
    }

    static void test_genericos_conDoubles() {
        ListaCircular<Double> l = new ListaCircular<Double>();
        l.insertar(1.1); l.insertar(2.2); l.insertar(3.3);
        assertEqualDouble("Double: 1er es 1.1", 1.1, l.eliminar());
        assertEqualDouble("Double: 2do es 2.2", 2.2, l.eliminar());
        assertEqualDouble("Double: 3er es 3.3", 3.3, l.eliminar());
    }

    // ==================================================================
    // CASOS DE BORDE
    // ==================================================================

    static void test_borde_unSoloElemento_insertar_eliminar() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        l.insertar(99);
        assertEqual("Borde: único → eliminar devuelve 99", 99, l.eliminar());
        assertThrows("Borde: vacía tras eliminar único",
                ListaCircularExceptions.class, () -> l.eliminar());
    }

    static void test_borde_dobleInsercion_mismoValor() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        l.insertar(5); l.insertar(5);
        assertEqual("Borde: duplicados → 1er es 5", 5, l.eliminar());
        assertEqual("Borde: duplicados → 2do es 5", 5, l.eliminar());
    }

    static void test_borde_eliminar_hastaVaciar_exactamente() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        l.insertar(1); l.insertar(2); l.insertar(3);
        l.eliminar(); l.eliminar(); l.eliminar();
        assertThrows("Borde: vacía exactamente tras 3 eliminaciones",
                ListaCircularExceptions.class, () -> l.eliminar());
    }

    static void test_borde_insertar_despuesDeVaciar() {
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        l.insertar(1); l.insertar(2);
        l.eliminar(); l.eliminar();
        l.insertar(100);
        assertEqual("Borde: insertar tras vaciar → eliminar es 100", 100, l.eliminar());
        assertThrows("Borde: vacía de nuevo tras eliminar 100",
                ListaCircularExceptions.class, () -> l.eliminar());
    }

    // ==================================================================
    // VOLUMEN — CORREGIDOS
    // ==================================================================

    static void test_volumen_100elementos_ordenFIFO() {
        /*
         * Insertamos 1..100 en orden y verificamos que eliminar
         * devuelve exactamente el mismo orden (FIFO puro).
         * Al terminar la lista debe quedar vacía.
         */
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        for (int i = 1; i <= 100; i++) l.insertar(i);

        boolean fifoOk = true;
        for (int i = 1; i <= 100; i++) {
            int val = l.eliminar();
            if (val != i) { fifoOk = false; break; }
        }
        assertIsTrue("Volumen 100: orden FIFO correcto (1→100)", fifoOk);
        assertThrows("Volumen 100: lista vacía al final",
                ListaCircularExceptions.class, () -> l.eliminar());
    }

    static void test_volumen_insertar_eliminar_alternados() {
        /*
         * ANÁLISIS DEL COMPORTAMIENTO REAL:
         *
         * Insertamos de uno en uno y eliminamos cuando hay 2 en lista.
         * Secuencia para i = 2..50:
         *   - Lista antes: contiene el elemento anterior no eliminado
         *   - insertar(i) → lista tiene 2 elementos
         *   - eliminar()  → devuelve el más antiguo (esperado = i-1)
         *
         * Ejemplo:
         *   insertar(1)          → lista: [1]
         *   insertar(2), elim()  → devuelve 1,  lista: [2]
         *   insertar(3), elim()  → devuelve 2,  lista: [3]
         *   insertar(4), elim()  → devuelve 3,  lista: [4]
         *   ...
         *   insertar(50), elim() → devuelve 49, lista: [50]
         *   eliminar()           → devuelve 50, lista vacía
         */
        ListaCircular<Integer> l = new ListaCircular<Integer>();
        boolean ok = true;

        l.insertar(1); // arrancamos con el primero
        for (int i = 2; i <= 50; i++) {
            l.insertar(i);
            int extraido = l.eliminar(); // debe ser i-1 (el más antiguo)
            if (extraido != i - 1) { ok = false; break; }
        }
        assertIsTrue("Volumen alternado: cada eliminar devuelve el elemento más antiguo", ok);

        // Queda el último insertado (50)
        assertEqual("Volumen alternado: último elemento restante es 50", 50, l.eliminar());

        // La lista debe estar vacía
        assertThrows("Volumen alternado: lista vacía al terminar",
                ListaCircularExceptions.class, () -> l.eliminar());
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
