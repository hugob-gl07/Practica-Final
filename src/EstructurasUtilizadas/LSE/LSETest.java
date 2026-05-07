package EstructurasUtilizadas.LSE;
import Exceptions.ListaElementoNoEncontradoException;
import Exceptions.ListaIndiceInvalidoExceptions;
import Exceptions.ListaVaciaExceptions;

/**
 * Suite de tests manuales para ListaSimplementeEnlazada y LSEOrdenada.
 * No requiere JUnit. Ejecutar con el botón ▶️ de IntelliJ.
 *
 * NOTAS DE ANÁLISIS PREVIO:
 *   - del() en lista VACÍA lanza ListaVaciaExceptions (no ElementoNoEncontrado).
 *   - del() con dato inexistente lanza ListaElementoNoEncontradoException.
 *   - addFirst() devuelve T (a diferencia de add() que es void).
 *   - LSEOrdenada.add() usa compareTo < 0, duplicados van DESPUÉS del existente.
 *   - Tipos genéricos siempre explícitos: new ListaSimplementeEnlazada<Integer>().
 */
public class LSETest {

    private static int totalTests  = 0;
    private static int totalPassed = 0;
    private static int totalFailed = 0;

    public static void main(String[] args) {

        // ── LISTA SIMPLEMENTE ENLAZADA ─────────────────────────────────
        printHeader("EstructurasUtilizadas.LSE — Lista vacía");
        test_isEmpty_listaVacia();
        test_getSize_listaVacia_es0();
        test_toString_listaVacia();
        test_iterador_listaVacia_noTieneNext();
        test_del_listaVacia_lanzaListaVaciaExceptions();
        test_getAt_listaVacia_lanzaIndiceInvalido();
        test_removeAt_listaVacia_lanzaIndiceInvalido();
        test_excepcion_del_vacia_mensaje_descriptivo();

        printHeader("EstructurasUtilizadas.LSE — add (inserción al final)");
        test_add_unElemento_noEstaVacia();
        test_add_size_incrementa();
        test_add_varios_getFirst_correcto();
        test_add_varios_getLast_correcto();
        test_add_toString_correcto();
        test_add_void_noDevuelveValor();

        printHeader("EstructurasUtilizadas.LSE — addFirst (inserción al inicio)");
        test_addFirst_unElemento_esElPrimero();
        test_addFirst_devuelveElDato();
        test_addFirst_varios_ordenCorrecto();
        test_addFirst_size_correcto();
        test_addFirst_combinadoConAdd();

        printHeader("EstructurasUtilizadas.LSE — get (búsqueda por valor)");
        test_get_elementoExistente_devuelveElDato();
        test_get_elementoInexistente_devuelveNull();
        test_get_listaVacia_devuelveNull();
        test_get_primerElemento();
        test_get_ultimoElemento();

        printHeader("EstructurasUtilizadas.LSE — del (eliminación por valor)");
        test_del_primerElemento_correcto();
        test_del_ultimoElemento_correcto();
        test_del_elementoMedio_correcto();
        test_del_unicoElemento_listaQuedaVacia();
        test_del_listaVacia_lanzaListaVaciaExceptions_repeticion();
        test_del_elementoInexistente_lanzaElementoNoEncontrado();
        test_del_size_decrece();
        test_del_devuelveElDatoEliminado();

        printHeader("EstructurasUtilizadas.LSE — getAt (acceso por índice)");
        test_getAt_posicion0();
        test_getAt_posicionUltima();
        test_getAt_posicionMedio();
        test_getAt_negativo_lanzaExcepcion();
        test_getAt_igualAlSize_lanzaExcepcion();
        test_getAt_noModificaSize();

        printHeader("EstructurasUtilizadas.LSE — insertAt (inserción por índice)");
        test_insertAt_posicion0_esComoAddFirst();
        test_insertAt_posicionFinal_esComoAdd();
        test_insertAt_posicionMedio_desplaza();
        test_insertAt_size_incrementa();
        test_insertAt_negativo_lanzaExcepcion();
        test_insertAt_mayorQueSize_lanzaExcepcion();
        test_insertAt_devuelveElDato();

        printHeader("EstructurasUtilizadas.LSE — removeAt (eliminación por índice)");
        test_removeAt_posicion0_devuelvePrimero();
        test_removeAt_posicionUltima_devuelveUltimo();
        test_removeAt_posicionMedio();
        test_removeAt_size_decrece();
        test_removeAt_negativo_lanzaExcepcion();
        test_removeAt_igualAlSize_lanzaExcepcion();
        test_removeAt_unicoElemento_listaVacia();

        printHeader("EstructurasUtilizadas.LSE — getIterador");
        test_iterador_recorreTodosLosElementos();
        test_iterador_ordenCorrecto();
        test_iterador_hasNext_falsoAlTerminar();

        printHeader("EstructurasUtilizadas.LSE — toString");
        test_toString_unElemento();
        test_toString_variosElementos();

        printHeader("EstructurasUtilizadas.LSE — tipos genéricos");
        test_genericos_conStrings();
        test_genericos_conDoubles();

        printHeader("EstructurasUtilizadas.LSE — volumen");
        test_volumen_500elementos();

        // ── EstructurasUtilizadas.LSE ORDENADA ───────────────────────────────────────────────
        printHeader("LSEOrdenada — inserción ordenada");
        test_ordenada_add_unElemento();
        test_ordenada_add_insertaMenorAlInicio();
        test_ordenada_add_insertaMayorAlFinal();
        test_ordenada_add_insertaEnMedio();
        test_ordenada_add_insercionAscendente_siempreOrdenada();
        test_ordenada_add_insercionDescendente_siempreOrdenada();
        test_ordenada_add_insercionAleatoria_siempreOrdenada();
        test_ordenada_add_duplicados_ambosPresentes();
        test_ordenada_add_duplicados_ordenCorrecto();

        printHeader("LSEOrdenada — operaciones heredadas");
        test_ordenada_isEmpty_listaVacia();
        test_ordenada_getSize_correcto();
        test_ordenada_get_encuentraElemento();
        test_ordenada_get_elementoInexistente_null();
        test_ordenada_del_elimina_sigeOrdenada();
        test_ordenada_del_primerElemento();
        test_ordenada_del_ultimoElemento();
        test_ordenada_del_vacia_lanzaListaVaciaExceptions();
        test_ordenada_del_inexistente_lanzaElementoNoEncontrado();
        test_ordenada_getAt_primerElementoEsMenor();
        test_ordenada_removeAt_posicion0_extraeMenor();
        test_ordenada_toString_ordenAscendente();
        test_ordenada_iterador_ordenAscendente();
        test_ordenada_addFirst_noMantienOrden_aviso();

        printHeader("LSEOrdenada — tipos genéricos");
        test_ordenada_conStrings();

        printHeader("LSEOrdenada — volumen");
        test_ordenada_volumen_siempreOrdenada();

        printResumen();
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE — LISTA VACÍA
    // ==================================================================

    static void test_isEmpty_listaVacia() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        assertIsTrue("Vacía: isEmpty() es true", l.isEmpty());
    }

    static void test_getSize_listaVacia_es0() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        assertEqual("Vacía: getSize() es 0", 0, l.getSize());
    }

    static void test_toString_listaVacia() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        assertEqualString("Vacía: toString() es '[]'", "[]", l.toString());
    }

    static void test_iterador_listaVacia_noTieneNext() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        assertIsFalse("Vacía: iterador.hasNext() es false", l.getIterador().hasNext());
    }

    static void test_del_listaVacia_lanzaListaVaciaExceptions() {
        // CRÍTICO: del() en lista vacía lanza ListaVaciaExceptions, NO ElementoNoEncontrado
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        assertThrows("Vacía: del() lanza ListaVaciaExceptions (no ElementoNoEncontrado)",
                ListaVaciaExceptions.class, () -> l.del(5));
    }

    static void test_getAt_listaVacia_lanzaIndiceInvalido() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        assertThrows("Vacía: getAt(0) lanza ListaIndiceInvalidoExceptions",
                ListaIndiceInvalidoExceptions.class, () -> l.getAt(0));
    }

    static void test_removeAt_listaVacia_lanzaIndiceInvalido() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        assertThrows("Vacía: removeAt(0) lanza ListaIndiceInvalidoExceptions",
                ListaIndiceInvalidoExceptions.class, () -> l.removeAt(0));
    }

    static void test_excepcion_del_vacia_mensaje_descriptivo() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        try {
            l.del(1);
            registrarFallo("del vacía: no se lanzó excepción");
        } catch (ListaVaciaExceptions e) {
            assertIsTrue("del vacía: mensaje no nulo ni vacío",
                    e.getMessage() != null && !e.getMessage().isBlank());
        }
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE — add
    // ==================================================================

    static void test_add_unElemento_noEstaVacia() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1);
        assertIsFalse("add: lista no vacía tras insertar", l.isEmpty());
    }

    static void test_add_size_incrementa() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2); l.add(3);
        assertEqual("add: size es 3 tras 3 inserciones", 3, l.getSize());
    }

    static void test_add_varios_getFirst_correcto() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("add: getAt(0) es 10 (el primero insertado)", 10, l.getAt(0));
    }

    static void test_add_varios_getLast_correcto() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("add: getAt(2) es 30 (el último insertado)", 30, l.getAt(2));
    }

    static void test_add_toString_correcto() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2); l.add(3);
        assertEqualString("add: toString() es '[1, 2, 3]'", "[1, 2, 3]", l.toString());
    }

    static void test_add_void_noDevuelveValor() {
        // add() es void — simplemente verificamos que no lanza excepción
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        try {
            l.add(42);
            registrarOk("add: es void y no lanza excepción");
            totalTests++;
        } catch (Exception e) {
            totalTests++;
            registrarFallo("add: lanzó excepción inesperada: " + e.getClass().getSimpleName());
        }
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE — addFirst
    // ==================================================================

    static void test_addFirst_unElemento_esElPrimero() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.addFirst(5);
        assertEqual("addFirst: getAt(0) es 5", 5, l.getAt(0));
    }

    static void test_addFirst_devuelveElDato() {
        // addFirst() devuelve T — diferencia importante respecto a add()
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        Integer resultado = l.addFirst(99);
        assertEqual("addFirst: devuelve el dato insertado (99)", 99, resultado);
    }

    static void test_addFirst_varios_ordenCorrecto() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.addFirst(3); l.addFirst(2); l.addFirst(1);
        assertEqualString("addFirst: toString '[1, 2, 3]'", "[1, 2, 3]", l.toString());
    }

    static void test_addFirst_size_correcto() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.addFirst(1); l.addFirst(2);
        assertEqual("addFirst: size es 2", 2, l.getSize());
    }

    static void test_addFirst_combinadoConAdd() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(2); l.add(3); l.addFirst(1);
        assertEqualString("addFirst+add: toString '[1, 2, 3]'", "[1, 2, 3]", l.toString());
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE — get
    // ==================================================================

    static void test_get_elementoExistente_devuelveElDato() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2); l.add(3);
        assertEqual("get: encuentra 2 y lo devuelve", 2, l.get(2));
    }

    static void test_get_elementoInexistente_devuelveNull() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2);
        assertIsTrue("get: devuelve null para elemento inexistente", l.get(99) == null);
    }

    static void test_get_listaVacia_devuelveNull() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        assertIsTrue("get: devuelve null en lista vacía (no lanza excepción)", l.get(5) == null);
    }

    static void test_get_primerElemento() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("get: encuentra el primer elemento (10)", 10, l.get(10));
    }

    static void test_get_ultimoElemento() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("get: encuentra el último elemento (30)", 30, l.get(30));
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE — del
    // ==================================================================

    static void test_del_primerElemento_correcto() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2); l.add(3);
        l.del(1);
        assertEqualString("del primer: toString '[2, 3]'", "[2, 3]", l.toString());
    }

    static void test_del_ultimoElemento_correcto() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2); l.add(3);
        l.del(3);
        assertEqualString("del último: toString '[1, 2]'", "[1, 2]", l.toString());
    }

    static void test_del_elementoMedio_correcto() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2); l.add(3);
        l.del(2);
        assertEqualString("del medio: toString '[1, 3]'", "[1, 3]", l.toString());
    }

    static void test_del_unicoElemento_listaQuedaVacia() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(42);
        l.del(42);
        assertIsTrue("del único: lista vacía tras eliminar", l.isEmpty());
        assertEqual("del único: size es 0", 0, l.getSize());
    }

    static void test_del_listaVacia_lanzaListaVaciaExceptions_repeticion() {
        // Confirmamos que es ListaVaciaExceptions y no otra excepción
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        assertThrows("del vacía: lanza exactamente ListaVaciaExceptions",
                ListaVaciaExceptions.class, () -> l.del(99));
    }

    static void test_del_elementoInexistente_lanzaElementoNoEncontrado() {
        // Lista con elementos pero el dato no existe → ListaElementoNoEncontradoException
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2); l.add(3);
        assertThrows("del inexistente: lanza ListaElementoNoEncontradoException",
                ListaElementoNoEncontradoException.class, () -> l.del(99));
    }

    static void test_del_size_decrece() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2); l.add(3);
        l.del(2);
        assertEqual("del: size decrece a 2", 2, l.getSize());
    }

    static void test_del_devuelveElDatoEliminado() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("del: devuelve el dato eliminado (20)", 20, l.del(20));
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE — getAt
    // ==================================================================

    static void test_getAt_posicion0() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("getAt(0): devuelve 10", 10, l.getAt(0));
    }

    static void test_getAt_posicionUltima() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("getAt(2): devuelve 30", 30, l.getAt(2));
    }

    static void test_getAt_posicionMedio() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("getAt(1): devuelve 20", 20, l.getAt(1));
    }

    static void test_getAt_negativo_lanzaExcepcion() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1);
        assertThrows("getAt(-1): lanza ListaIndiceInvalidoExceptions",
                ListaIndiceInvalidoExceptions.class, () -> l.getAt(-1));
    }

    static void test_getAt_igualAlSize_lanzaExcepcion() {
        // getAt(size) está fuera de rango → excepción
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2);
        assertThrows("getAt(size): lanza ListaIndiceInvalidoExceptions",
                ListaIndiceInvalidoExceptions.class, () -> l.getAt(2));
    }

    static void test_getAt_noModificaSize() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2);
        l.getAt(0);
        assertEqual("getAt: no modifica size", 2, l.getSize());
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE — insertAt
    // ==================================================================

    static void test_insertAt_posicion0_esComoAddFirst() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(2); l.add(3);
        l.insertAt(0, 1);
        assertEqual("insertAt(0): getAt(0) es 1", 1, l.getAt(0));
        assertEqualString("insertAt(0): toString '[1, 2, 3]'", "[1, 2, 3]", l.toString());
    }

    static void test_insertAt_posicionFinal_esComoAdd() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2);
        l.insertAt(2, 3);
        assertEqual("insertAt(final): getAt(2) es 3", 3, l.getAt(2));
    }

    static void test_insertAt_posicionMedio_desplaza() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(3);
        l.insertAt(1, 2);
        assertEqualString("insertAt(1): toString '[1, 2, 3]'", "[1, 2, 3]", l.toString());
    }

    static void test_insertAt_size_incrementa() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(3);
        l.insertAt(1, 2);
        assertEqual("insertAt: size es 3", 3, l.getSize());
    }

    static void test_insertAt_negativo_lanzaExcepcion() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1);
        assertThrows("insertAt(-1): lanza ListaIndiceInvalidoExceptions",
                ListaIndiceInvalidoExceptions.class, () -> l.insertAt(-1, 99));
    }

    static void test_insertAt_mayorQueSize_lanzaExcepcion() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1);
        assertThrows("insertAt(size+1): lanza ListaIndiceInvalidoExceptions",
                ListaIndiceInvalidoExceptions.class, () -> l.insertAt(5, 99));
    }

    static void test_insertAt_devuelveElDato() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(3);
        Integer resultado = l.insertAt(1, 2);
        assertEqual("insertAt: devuelve el dato insertado (2)", 2, resultado);
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE — removeAt
    // ==================================================================

    static void test_removeAt_posicion0_devuelvePrimero() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("removeAt(0): devuelve 10", 10, l.removeAt(0));
        assertEqual("removeAt(0): nuevo primero es 20", 20, l.getAt(0));
    }

    static void test_removeAt_posicionUltima_devuelveUltimo() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("removeAt(2): devuelve 30", 30, l.removeAt(2));
        assertEqual("removeAt(2): nuevo último es 20", 20, l.getAt(1));
    }

    static void test_removeAt_posicionMedio() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("removeAt(1): devuelve 20", 20, l.removeAt(1));
        assertEqualString("removeAt(1): toString '[10, 30]'", "[10, 30]", l.toString());
    }

    static void test_removeAt_size_decrece() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2); l.add(3);
        l.removeAt(1);
        assertEqual("removeAt: size decrece a 2", 2, l.getSize());
    }

    static void test_removeAt_negativo_lanzaExcepcion() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1);
        assertThrows("removeAt(-1): lanza ListaIndiceInvalidoExceptions",
                ListaIndiceInvalidoExceptions.class, () -> l.removeAt(-1));
    }

    static void test_removeAt_igualAlSize_lanzaExcepcion() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2);
        assertThrows("removeAt(size): lanza ListaIndiceInvalidoExceptions",
                ListaIndiceInvalidoExceptions.class, () -> l.removeAt(2));
    }

    static void test_removeAt_unicoElemento_listaVacia() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(42);
        l.removeAt(0);
        assertIsTrue("removeAt: lista vacía tras eliminar único", l.isEmpty());
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE — getIterador
    // ==================================================================

    static void test_iterador_recorreTodosLosElementos() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2); l.add(3);
        Iterador<Integer> it = l.getIterador();
        int count = 0;
        while (it.hasNext()) { it.next(); count++; }
        assertEqual("Iterador: recorre los 3 elementos", 3, count);
    }

    static void test_iterador_ordenCorrecto() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(10); l.add(20); l.add(30);
        Iterador<Integer> it = l.getIterador();
        assertEqual("Iterador: 1er elemento es 10", 10, it.next());
        assertEqual("Iterador: 2do elemento es 20", 20, it.next());
        assertEqual("Iterador: 3er elemento es 30", 30, it.next());
    }

    static void test_iterador_hasNext_falsoAlTerminar() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1);
        Iterador<Integer> it = l.getIterador();
        it.next();
        assertIsFalse("Iterador: hasNext() false tras recorrer todo", it.hasNext());
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE — toString
    // ==================================================================

    static void test_toString_unElemento() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(42);
        assertEqualString("toString: un elemento '[42]'", "[42]", l.toString());
    }

    static void test_toString_variosElementos() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        l.add(1); l.add(2); l.add(3); l.add(4);
        assertEqualString("toString: '[1, 2, 3, 4]'", "[1, 2, 3, 4]", l.toString());
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE — tipos genéricos
    // ==================================================================

    static void test_genericos_conStrings() {
        ListaSimplementeEnlazada<String> l = new ListaSimplementeEnlazada<String>();
        l.add("hola"); l.add("mundo");
        assertEqualString("String: getAt(0) es 'hola'",    "hola",  l.getAt(0));
        assertEqualString("String: getAt(1) es 'mundo'",   "mundo", l.getAt(1));
        assertEqualString("String: toString correcto", "[hola, mundo]", l.toString());
    }

    static void test_genericos_conDoubles() {
        ListaSimplementeEnlazada<Double> l = new ListaSimplementeEnlazada<Double>();
        l.add(1.1); l.add(2.2); l.add(3.3);
        assertEqualDouble("Double: getAt(0) es 1.1", 1.1, l.getAt(0));
        assertEqualDouble("Double: getAt(2) es 3.3", 3.3, l.getAt(2));
        assertEqual("Double: size es 3", 3, l.getSize());
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE — volumen
    // ==================================================================

    static void test_volumen_500elementos() {
        ListaSimplementeEnlazada<Integer> l = new ListaSimplementeEnlazada<Integer>();
        for (int i = 1; i <= 500; i++) l.add(i);
        assertEqual("Volumen: size es 500",        500, l.getSize());
        assertEqual("Volumen: getAt(0) es 1",        1, l.getAt(0));
        assertEqual("Volumen: getAt(499) es 500",  500, l.getAt(499));
        assertIsFalse("Volumen: no está vacía",      l.isEmpty());
        l.del(250);
        assertEqual("Volumen: size tras del es 499", 499, l.getSize());
        assertIsTrue("Volumen: 250 ya no existe",    l.get(250) == null);
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE ORDENADA — inserción ordenada
    // ==================================================================

    static void test_ordenada_add_unElemento() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(5);
        assertIsFalse("LSEOrdenada add: no vacía", l.isEmpty());
        assertEqual("LSEOrdenada add: getAt(0) es 5", 5, l.getAt(0));
    }

    static void test_ordenada_add_insertaMenorAlInicio() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(10); l.add(20); l.add(1);
        assertEqual("LSEOrdenada: el menor (1) está en posición 0", 1, l.getAt(0));
    }

    static void test_ordenada_add_insertaMayorAlFinal() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(10); l.add(5); l.add(100);
        assertEqual("LSEOrdenada: el mayor (100) está en última posición", 100, l.getAt(2));
    }

    static void test_ordenada_add_insertaEnMedio() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(1); l.add(3); l.add(2);
        assertEqualString("LSEOrdenada: insertaEnMedio → '[1, 2, 3]'", "[1, 2, 3]", l.toString());
    }

    static void test_ordenada_add_insercionAscendente_siempreOrdenada() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        for (int i = 1; i <= 10; i++) l.add(i);
        assertIsTrue("LSEOrdenada: inserción ascendente → siempre ordenada", listaOrdenada(l));
    }

    static void test_ordenada_add_insercionDescendente_siempreOrdenada() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        for (int i = 10; i >= 1; i--) l.add(i);
        assertIsTrue("LSEOrdenada: inserción descendente → siempre ordenada", listaOrdenada(l));
    }

    static void test_ordenada_add_insercionAleatoria_siempreOrdenada() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        int[] vals = {5, 2, 8, 1, 9, 3, 7, 4, 6, 10};
        for (int v : vals) l.add(v);
        assertIsTrue("LSEOrdenada: inserción aleatoria → siempre ordenada", listaOrdenada(l));
    }

    static void test_ordenada_add_duplicados_ambosPresentes() {
        // add() usa < (no <=) → duplicados se insertan después del existente
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(5); l.add(5); l.add(5);
        assertEqual("LSEOrdenada duplicados: size es 3", 3, l.getSize());
    }

    static void test_ordenada_add_duplicados_ordenCorrecto() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(3); l.add(1); l.add(3); l.add(2);
        assertIsTrue("LSEOrdenada duplicados: lista sigue ordenada", listaOrdenada(l));
        assertEqualString("LSEOrdenada duplicados: toString '[1, 2, 3, 3]'",
                "[1, 2, 3, 3]", l.toString());
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE ORDENADA — operaciones heredadas
    // ==================================================================

    static void test_ordenada_isEmpty_listaVacia() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        assertIsTrue("LSEOrdenada: isEmpty() en lista vacía", l.isEmpty());
    }

    static void test_ordenada_getSize_correcto() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(5); l.add(1); l.add(3);
        assertEqual("LSEOrdenada: getSize() es 3", 3, l.getSize());
    }

    static void test_ordenada_get_encuentraElemento() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("LSEOrdenada get: encuentra 20", 20, l.get(20));
    }

    static void test_ordenada_get_elementoInexistente_null() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(1); l.add(2);
        assertIsTrue("LSEOrdenada get: null para inexistente", l.get(99) == null);
    }

    static void test_ordenada_del_elimina_sigeOrdenada() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(1); l.add(3); l.add(5); l.add(7);
        l.del(3);
        assertIsTrue("LSEOrdenada del: sigue ordenada tras eliminar", listaOrdenada(l));
        assertIsTrue("LSEOrdenada del: 3 ya no existe", l.get(3) == null);
    }

    static void test_ordenada_del_primerElemento() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(1); l.add(2); l.add(3);
        l.del(1);
        assertEqual("LSEOrdenada del primer: nuevo primero es 2", 2, l.getAt(0));
    }

    static void test_ordenada_del_ultimoElemento() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(1); l.add(2); l.add(3);
        l.del(3);
        assertEqual("LSEOrdenada del último: nuevo último es 2", 2, l.getAt(l.getSize() - 1));
    }

    static void test_ordenada_del_vacia_lanzaListaVaciaExceptions() {
        // Igual que EstructurasUtilizadas.LSE: del() en vacía lanza ListaVaciaExceptions
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        assertThrows("LSEOrdenada del vacía: lanza ListaVaciaExceptions",
                ListaVaciaExceptions.class, () -> l.del(5));
    }

    static void test_ordenada_del_inexistente_lanzaElementoNoEncontrado() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(1); l.add(2);
        assertThrows("LSEOrdenada del inexistente: lanza ListaElementoNoEncontradoException",
                ListaElementoNoEncontradoException.class, () -> l.del(99));
    }

    static void test_ordenada_getAt_primerElementoEsMenor() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(5); l.add(1); l.add(9); l.add(3);
        assertEqual("LSEOrdenada getAt(0): es siempre el menor (1)", 1, l.getAt(0));
    }

    static void test_ordenada_removeAt_posicion0_extraeMenor() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(3); l.add(1); l.add(2);
        assertEqual("LSEOrdenada removeAt(0): extrae 1 (el menor)", 1, l.removeAt(0));
        assertEqual("LSEOrdenada removeAt(0): nuevo primero es 2",  2, l.getAt(0));
    }

    static void test_ordenada_toString_ordenAscendente() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(3); l.add(1); l.add(2);
        assertEqualString("LSEOrdenada toString: '[1, 2, 3]'", "[1, 2, 3]", l.toString());
    }

    static void test_ordenada_iterador_ordenAscendente() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(4); l.add(2); l.add(1); l.add(3);
        assertIsTrue("LSEOrdenada iterador: recorre en orden ascendente", listaOrdenada(l));
    }

    static void test_ordenada_addFirst_noMantienOrden_aviso() {
        /*
         * addFirst() es heredado de EstructurasUtilizadas.LSE y NO mantiene el orden.
         * Verificamos que si se usa addFirst() en LSEOrdenada,
         * el elemento queda al inicio independientemente de su valor.
         * Este test documenta el comportamiento, no lo considera un error.
         */
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        l.add(1); l.add(2); l.add(3);
        l.addFirst(99); // 99 al inicio, rompe el orden
        assertEqual("LSEOrdenada addFirst: inserta al inicio sin respetar orden",
                99, l.getAt(0));
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE ORDENADA — tipos genéricos
    // ==================================================================

    static void test_ordenada_conStrings() {
        LSEOrdenada<String> l = new LSEOrdenada<String>();
        l.add("pera"); l.add("manzana"); l.add("cereza");
        // Orden lexicográfico: cereza < manzana < pera
        assertEqualString("LSEOrdenada String: getAt(0) es 'cereza'",
                "cereza", l.getAt(0));
        assertEqualString("LSEOrdenada String: toString correcto",
                "[cereza, manzana, pera]", l.toString());
    }

    // ==================================================================
    // EstructurasUtilizadas.LSE ORDENADA — volumen
    // ==================================================================

    static void test_ordenada_volumen_siempreOrdenada() {
        LSEOrdenada<Integer> l = new LSEOrdenada<Integer>();
        int[] vals = {50,10,90,30,70,20,80,40,60,100,5,95,15,85,25,75,45,65,35,55};
        for (int v : vals) l.add(v);
        assertEqual("LSEOrdenada volumen: size correcto", vals.length, l.getSize());
        assertIsTrue("LSEOrdenada volumen: siempre ordenada ascendente", listaOrdenada(l));
        assertEqual("LSEOrdenada volumen: getAt(0) es 5",   5,   l.getAt(0));
        assertEqual("LSEOrdenada volumen: último es 100", 100, l.getAt(l.getSize() - 1));
    }

    // ==================================================================
    // UTILIDADES
    // ==================================================================

    static boolean listaOrdenada(ListaSimplementeEnlazada<Integer> l) {
        Iterador<Integer> it = l.getIterador();
        int anterior = Integer.MIN_VALUE;
        while (it.hasNext()) {
            int actual = it.next();
            if (actual < anterior) return false;
            anterior = actual;
        }
        return true;
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
