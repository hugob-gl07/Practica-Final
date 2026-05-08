package juego.model.EstructurasUtilizadas.LDE;
import juego.model.Exceptions.ListaElementoNoEncontradoException;
import juego.model.Exceptions.ListaIndiceInvalidoExceptions;
import juego.model.Exceptions.ListaVaciaExceptions;

/**
 * Suite de tests manuales para ListaDoblementeEnlazada y LDEOrdenada.
 * No requiere JUnit. Ejecutar con el botón ▶️ de IntelliJ.
 */
public class LDETest {

    private static int totalTests  = 0;
    private static int totalPassed = 0;
    private static int totalFailed = 0;

    public static void main(String[] args) {

        // ── LISTA DOBLEMENTE ENLAZADA ──────────────────────────────────
        printHeader("juego.model.EstructurasUtilizadas.LDE — Lista vacía");
        test_isEmpty_listaVacia();
        test_getSize_listaVacia_es0();
        test_toString_listaVacia();
        test_getFirst_vacia_lanzaExcepcion();
        test_getLast_vacia_lanzaExcepcion();
        test_removeFirst_vacia_lanzaExcepcion();
        test_removeLast_vacia_lanzaExcepcion();
        test_del_vacia_lanzaExcepcion();
        test_getAt_vacia_lanzaExcepcion();
        test_excepcion_mensajes_descriptivos();

        printHeader("juego.model.EstructurasUtilizadas.LDE — add (inserción al final)");
        test_add_unElemento_noEstaVacia();
        test_add_size_correcto();
        test_add_varios_getFirst_correcto();
        test_add_varios_getLast_correcto();
        test_add_toString_correcto();

        printHeader("juego.model.EstructurasUtilizadas.LDE — addFirst (inserción al inicio)");
        test_addFirst_unElemento();
        test_addFirst_varios_ordenCorrecto();
        test_addFirst_getFirst_esSiempreElUltimoInsertado();
        test_addFirst_size_correcto();

        printHeader("juego.model.EstructurasUtilizadas.LDE — getFirst / getLast");
        test_getFirst_noModificaSize();
        test_getLast_noModificaSize();
        test_getFirst_unElemento_esMismoQueLast();

        printHeader("juego.model.EstructurasUtilizadas.LDE — get (búsqueda por valor)");
        test_get_elementoExistente();
        test_get_elementoInexistente_devuelveNull();
        test_get_listaVacia_devuelveNull();

        printHeader("juego.model.EstructurasUtilizadas.LDE — del (eliminación por valor)");
        test_del_primerElemento();
        test_del_ultimoElemento();
        test_del_elementoMedio();
        test_del_unicoElemento_listaVacia();
        test_del_elementoInexistente_lanzaExcepcion();
        test_del_size_decrece();

        printHeader("juego.model.EstructurasUtilizadas.LDE — getAt (acceso por índice)");
        test_getAt_indiceCero();
        test_getAt_indiceUltimo();
        test_getAt_indiceMedio();
        test_getAt_indiceNegativo_lanzaExcepcion();
        test_getAt_indiceFueraDerango_lanzaExcepcion();

        printHeader("juego.model.EstructurasUtilizadas.LDE — insertAt (inserción por índice)");
        test_insertAt_posicion0_esComoAddFirst();
        test_insertAt_posicionFinal_esComoAdd();
        test_insertAt_posicionMedio();
        test_insertAt_size_correcto();
        test_insertAt_fueraDerango_lanzaExcepcion();

        printHeader("juego.model.EstructurasUtilizadas.LDE — removeAt (eliminación por índice)");
        test_removeAt_posicion0();
        test_removeAt_posicionUltima();
        test_removeAt_posicionMedio();
        test_removeAt_size_decrece();
        test_removeAt_fueraDerango_lanzaExcepcion();
        test_removeAt_negativo_lanzaExcepcion();

        printHeader("juego.model.EstructurasUtilizadas.LDE — removeFirst / removeLast");
        test_removeFirst_devuelvePrimero();
        test_removeFirst_actualizaLista();
        test_removeLast_devuelveUltimo();
        test_removeLast_actualizaLista();
        test_removeFirst_unicoElemento_listaVacia();
        test_removeLast_unicoElemento_listaVacia();

        printHeader("juego.model.EstructurasUtilizadas.LDE — clear");
        test_clear_listaVacia();
        test_clear_size0();
        test_clear_reutilizacion();

        printHeader("juego.model.EstructurasUtilizadas.LDE — getIterador");
        test_iterador_listaVacia_noTieneNext();
        test_iterador_recorreTodosLosElementos();
        test_iterador_ordenCorrecto();

        printHeader("juego.model.EstructurasUtilizadas.LDE — toString");
        test_toString_unElemento();
        test_toString_variosElementos();

        printHeader("juego.model.EstructurasUtilizadas.LDE — tipos genéricos");
        test_genericos_conStrings();
        test_genericos_conDoubles();

        printHeader("juego.model.EstructurasUtilizadas.LDE — volumen");
        test_volumen_1000elementos();

        // ── juego.model.EstructurasUtilizadas.LDE ORDENADA ───────────────────────────────────────────────
        printHeader("LDEOrdenada — inserción ordenada");
        test_ordenada_add_siempreOrdenada();
        test_ordenada_add_insercionAscendente_siempreOrdenada();
        test_ordenada_add_insercionDescendente_siempreOrdenada();
        test_ordenada_add_insertaMenorAlInicio();
        test_ordenada_add_insertaMayorAlFinal();
        test_ordenada_add_insertaEnMedio();
        test_ordenada_add_elementosDuplicados();

        printHeader("LDEOrdenada — operaciones heredadas");
        test_ordenada_getFirst_esMenor();
        test_ordenada_getLast_esMayor();
        test_ordenada_removeFirst_extrae_menor();
        test_ordenada_removeLast_extrae_mayor();
        test_ordenada_get_encuentraElemento();
        test_ordenada_del_eliminaYSigueOrdenada();
        test_ordenada_size_correcto();
        test_ordenada_isEmpty_trasClear();
        test_ordenada_iterador_ordenAscendente();
        test_ordenada_toString_ordenAscendente();

        printHeader("LDEOrdenada — tipos genéricos");
        test_ordenada_conStrings();

        printHeader("LDEOrdenada — volumen");
        test_ordenada_volumen_siempreOrdenada();

        printResumen();
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE — Lista vacía
    // ==================================================================

    static void test_isEmpty_listaVacia() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        assertIsTrue("Vacía: isEmpty() es true", l.isEmpty());
    }

    static void test_getSize_listaVacia_es0() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        assertEqual("Vacía: getSize() es 0", 0, l.getSize());
    }

    static void test_toString_listaVacia() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        assertEqualString("Vacía: toString() es '[]'", "[]", l.toString());
    }

    static void test_getFirst_vacia_lanzaExcepcion() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        assertThrows("Vacía: getFirst() lanza ListaVaciaExceptions",
                ListaVaciaExceptions.class, () -> l.getFirst());
    }

    static void test_getLast_vacia_lanzaExcepcion() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        assertThrows("Vacía: getLast() lanza ListaVaciaExceptions",
                ListaVaciaExceptions.class, () -> l.getLast());
    }

    static void test_removeFirst_vacia_lanzaExcepcion() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        assertThrows("Vacía: removeFirst() lanza ListaVaciaExceptions",
                ListaVaciaExceptions.class, () -> l.removeFirst());
    }

    static void test_removeLast_vacia_lanzaExcepcion() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        assertThrows("Vacía: removeLast() lanza ListaVaciaExceptions",
                ListaVaciaExceptions.class, () -> l.removeLast());
    }

    static void test_del_vacia_lanzaExcepcion() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        assertThrows("Vacía: del() lanza ListaElementoNoEncontradoException",
                ListaElementoNoEncontradoException.class, () -> l.del(5));
    }

    static void test_getAt_vacia_lanzaExcepcion() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        assertThrows("Vacía: getAt(0) lanza ListaIndiceInvalidoExceptions",
                ListaIndiceInvalidoExceptions.class, () -> l.getAt(0));
    }

    static void test_excepcion_mensajes_descriptivos() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        try { l.getFirst(); registrarFallo("excepción getFirst: no se lanzó"); }
        catch (ListaVaciaExceptions e) {
            assertIsTrue("excepción getFirst: mensaje no vacío",
                    e.getMessage() != null && !e.getMessage().isBlank());
        }
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE — add
    // ==================================================================

    static void test_add_unElemento_noEstaVacia() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1);
        assertIsFalse("add: lista no vacía tras insertar", l.isEmpty());
    }

    static void test_add_size_correcto() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2); l.add(3);
        assertEqual("add: size es 3", 3, l.getSize());
    }

    static void test_add_varios_getFirst_correcto() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("add: getFirst() es 10 (el primero insertado)", 10, l.getFirst());
    }

    static void test_add_varios_getLast_correcto() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("add: getLast() es 30 (el último insertado)", 30, l.getLast());
    }

    static void test_add_toString_correcto() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2); l.add(3);
        assertEqualString("add: toString() es '[1, 2, 3]'", "[1, 2, 3]", l.toString());
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE — addFirst
    // ==================================================================

    static void test_addFirst_unElemento() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.addFirst(5);
        assertIsFalse("addFirst: lista no vacía", l.isEmpty());
        assertEqual("addFirst: getFirst() es 5", 5, l.getFirst());
    }

    static void test_addFirst_varios_ordenCorrecto() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.addFirst(3); l.addFirst(2); l.addFirst(1);
        assertEqualString("addFirst: toString() es '[1, 2, 3]'", "[1, 2, 3]", l.toString());
    }

    static void test_addFirst_getFirst_esSiempreElUltimoInsertado() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.addFirst(10); l.addFirst(5);
        assertEqual("addFirst: getFirst() es el último insertado (5)", 5, l.getFirst());
    }

    static void test_addFirst_size_correcto() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.addFirst(1); l.addFirst(2);
        assertEqual("addFirst: size es 2", 2, l.getSize());
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE — getFirst / getLast
    // ==================================================================

    static void test_getFirst_noModificaSize() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2);
        l.getFirst();
        assertEqual("getFirst: size no cambia", 2, l.getSize());
    }

    static void test_getLast_noModificaSize() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2);
        l.getLast();
        assertEqual("getLast: size no cambia", 2, l.getSize());
    }

    static void test_getFirst_unElemento_esMismoQueLast() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(42);
        assertEqual("getFirst == getLast con un elemento", l.getFirst(), l.getLast());
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE — get
    // ==================================================================

    static void test_get_elementoExistente() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2); l.add(3);
        assertEqual("get: encuentra elemento existente (2)", 2, l.get(2));
    }

    static void test_get_elementoInexistente_devuelveNull() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2);
        assertIsTrue("get: devuelve null para elemento inexistente", l.get(99) == null);
    }

    static void test_get_listaVacia_devuelveNull() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        assertIsTrue("get: devuelve null en lista vacía", l.get(5) == null);
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE — del
    // ==================================================================

    static void test_del_primerElemento() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2); l.add(3);
        l.del(1);
        assertEqual("del: primer elemento eliminado, getFirst() es 2", 2, l.getFirst());
    }

    static void test_del_ultimoElemento() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2); l.add(3);
        l.del(3);
        assertEqual("del: último elemento eliminado, getLast() es 2", 2, l.getLast());
    }

    static void test_del_elementoMedio() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2); l.add(3);
        l.del(2);
        assertEqualString("del: elemento medio eliminado, toString '[1, 3]'", "[1, 3]", l.toString());
    }

    static void test_del_unicoElemento_listaVacia() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(42);
        l.del(42);
        assertIsTrue("del: lista vacía tras eliminar único elemento", l.isEmpty());
    }

    static void test_del_elementoInexistente_lanzaExcepcion() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2);
        assertThrows("del: lanza ListaElementoNoEncontradoException si no existe",
                ListaElementoNoEncontradoException.class, () -> l.del(99));
    }

    static void test_del_size_decrece() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2); l.add(3);
        l.del(2);
        assertEqual("del: size decrece a 2", 2, l.getSize());
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE — getAt
    // ==================================================================

    static void test_getAt_indiceCero() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("getAt(0): devuelve 10", 10, l.getAt(0));
    }

    static void test_getAt_indiceUltimo() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("getAt(2): devuelve 30", 30, l.getAt(2));
    }

    static void test_getAt_indiceMedio() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("getAt(1): devuelve 20", 20, l.getAt(1));
    }

    static void test_getAt_indiceNegativo_lanzaExcepcion() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1);
        assertThrows("getAt(-1): lanza ListaIndiceInvalidoExceptions",
                ListaIndiceInvalidoExceptions.class, () -> l.getAt(-1));
    }

    static void test_getAt_indiceFueraDerango_lanzaExcepcion() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2);
        assertThrows("getAt(5): lanza ListaIndiceInvalidoExceptions",
                ListaIndiceInvalidoExceptions.class, () -> l.getAt(5));
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE — insertAt
    // ==================================================================

    static void test_insertAt_posicion0_esComoAddFirst() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(2); l.add(3);
        l.insertAt(0, 1);
        assertEqual("insertAt(0): getFirst() es 1", 1, l.getFirst());
    }

    static void test_insertAt_posicionFinal_esComoAdd() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2);
        l.insertAt(2, 3);
        assertEqual("insertAt(final): getLast() es 3", 3, l.getLast());
    }

    static void test_insertAt_posicionMedio() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(3);
        l.insertAt(1, 2);
        assertEqualString("insertAt(1): toString '[1, 2, 3]'", "[1, 2, 3]", l.toString());
    }

    static void test_insertAt_size_correcto() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(3);
        l.insertAt(1, 2);
        assertEqual("insertAt: size es 3", 3, l.getSize());
    }

    static void test_insertAt_fueraDerango_lanzaExcepcion() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1);
        assertThrows("insertAt(5): lanza ListaIndiceInvalidoExceptions",
                ListaIndiceInvalidoExceptions.class, () -> l.insertAt(5, 99));
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE — removeAt
    // ==================================================================

    static void test_removeAt_posicion0() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("removeAt(0): devuelve 10", 10, l.removeAt(0));
        assertEqual("removeAt(0): getFirst() es 20", 20, l.getFirst());
    }

    static void test_removeAt_posicionUltima() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("removeAt(2): devuelve 30", 30, l.removeAt(2));
        assertEqual("removeAt(2): getLast() es 20", 20, l.getLast());
    }

    static void test_removeAt_posicionMedio() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("removeAt(1): devuelve 20", 20, l.removeAt(1));
        assertEqualString("removeAt(1): toString '[10, 30]'", "[10, 30]", l.toString());
    }

    static void test_removeAt_size_decrece() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2); l.add(3);
        l.removeAt(1);
        assertEqual("removeAt: size decrece a 2", 2, l.getSize());
    }

    static void test_removeAt_fueraDerango_lanzaExcepcion() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1);
        assertThrows("removeAt(5): lanza ListaIndiceInvalidoExceptions",
                ListaIndiceInvalidoExceptions.class, () -> l.removeAt(5));
    }

    static void test_removeAt_negativo_lanzaExcepcion() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1);
        assertThrows("removeAt(-1): lanza ListaIndiceInvalidoExceptions",
                ListaIndiceInvalidoExceptions.class, () -> l.removeAt(-1));
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE — removeFirst / removeLast
    // ==================================================================

    static void test_removeFirst_devuelvePrimero() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("removeFirst: devuelve 10", 10, l.removeFirst());
    }

    static void test_removeFirst_actualizaLista() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(10); l.add(20); l.add(30);
        l.removeFirst();
        assertEqual("removeFirst: getFirst() pasa a ser 20", 20, l.getFirst());
        assertEqual("removeFirst: size decrece a 2", 2, l.getSize());
    }

    static void test_removeLast_devuelveUltimo() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(10); l.add(20); l.add(30);
        assertEqual("removeLast: devuelve 30", 30, l.removeLast());
    }

    static void test_removeLast_actualizaLista() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(10); l.add(20); l.add(30);
        l.removeLast();
        assertEqual("removeLast: getLast() pasa a ser 20", 20, l.getLast());
        assertEqual("removeLast: size decrece a 2", 2, l.getSize());
    }

    static void test_removeFirst_unicoElemento_listaVacia() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(42);
        l.removeFirst();
        assertIsTrue("removeFirst: lista vacía tras eliminar único elemento", l.isEmpty());
    }

    static void test_removeLast_unicoElemento_listaVacia() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(42);
        l.removeLast();
        assertIsTrue("removeLast: lista vacía tras eliminar único elemento", l.isEmpty());
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE — clear
    // ==================================================================

    static void test_clear_listaVacia() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2); l.add(3);
        l.clear();
        assertIsTrue("clear: isEmpty() true tras clear", l.isEmpty());
    }

    static void test_clear_size0() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2);
        l.clear();
        assertEqual("clear: size es 0 tras clear", 0, l.getSize());
    }

    static void test_clear_reutilizacion() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2);
        l.clear();
        l.add(99);
        assertEqual("clear: lista reutilizable tras clear", 99, l.getFirst());
        assertEqual("clear: size es 1 tras reutilizar", 1, l.getSize());
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE — getIterador
    // ==================================================================

    static void test_iterador_listaVacia_noTieneNext() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        Iterador<Integer> it = l.getIterador();
        assertIsFalse("Iterador: hasNext() false en lista vacía", it.hasNext());
    }

    static void test_iterador_recorreTodosLosElementos() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2); l.add(3);
        Iterador<Integer> it = l.getIterador();
        int count = 0;
        while (it.hasNext()) { it.next(); count++; }
        assertEqual("Iterador: recorre los 3 elementos", 3, count);
    }

    static void test_iterador_ordenCorrecto() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(10); l.add(20); l.add(30);
        Iterador<Integer> it = l.getIterador();
        assertEqual("Iterador: 1er elemento es 10", 10, it.next());
        assertEqual("Iterador: 2do elemento es 20", 20, it.next());
        assertEqual("Iterador: 3er elemento es 30", 30, it.next());
        assertIsFalse("Iterador: hasNext() false al terminar", it.hasNext());
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE — toString
    // ==================================================================

    static void test_toString_unElemento() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(42);
        assertEqualString("toString: un elemento '[42]'", "[42]", l.toString());
    }

    static void test_toString_variosElementos() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        l.add(1); l.add(2); l.add(3); l.add(4);
        assertEqualString("toString: '[1, 2, 3, 4]'", "[1, 2, 3, 4]", l.toString());
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE — tipos genéricos
    // ==================================================================

    static void test_genericos_conStrings() {
        ListaDoblementeEnlazada<String> l = new ListaDoblementeEnlazada<>();
        l.add("hola"); l.add("mundo");
        assertEqualString("String: getFirst() es 'hola'", "hola", l.getFirst());
        assertEqualString("String: getLast() es 'mundo'",  "mundo", l.getLast());
        assertEqualString("String: toString correcto", "[hola, mundo]", l.toString());
    }

    static void test_genericos_conDoubles() {
        ListaDoblementeEnlazada<Double> l = new ListaDoblementeEnlazada<>();
        l.add(1.1); l.add(2.2); l.add(3.3);
        assertEqualDouble("Double: getFirst() es 1.1", 1.1, l.getFirst());
        assertEqualDouble("Double: getLast() es 3.3",  3.3, l.getLast());
        assertEqual("Double: size es 3", 3, l.getSize());
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE — volumen
    // ==================================================================

    static void test_volumen_1000elementos() {
        ListaDoblementeEnlazada<Integer> l = new ListaDoblementeEnlazada<>();
        for (int i = 1; i <= 1000; i++) l.add(i);
        assertEqual("Volumen: size es 1000",         1000, l.getSize());
        assertEqual("Volumen: getFirst() es 1",         1, l.getFirst());
        assertEqual("Volumen: getLast() es 1000",    1000, l.getLast());
        assertEqual("Volumen: getAt(499) es 500",     500, l.getAt(499));
        assertIsFalse("Volumen: no está vacía",        l.isEmpty());
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE ORDENADA — inserción ordenada
    // ==================================================================

    static void test_ordenada_add_siempreOrdenada() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        l.add(5); l.add(2); l.add(8); l.add(1); l.add(6);
        assertIsTrue("LDEOrdenada: siempre ordenada ascendente", listaOrdenada(l));
    }

    static void test_ordenada_add_insercionAscendente_siempreOrdenada() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        for (int i = 1; i <= 10; i++) l.add(i);
        assertIsTrue("LDEOrdenada: inserción ascendente → ordenada", listaOrdenada(l));
    }

    static void test_ordenada_add_insercionDescendente_siempreOrdenada() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        for (int i = 10; i >= 1; i--) l.add(i);
        assertIsTrue("LDEOrdenada: inserción descendente → ordenada", listaOrdenada(l));
    }

    static void test_ordenada_add_insertaMenorAlInicio() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        l.add(10); l.add(20); l.add(1); // 1 debe quedar al inicio
        assertEqual("LDEOrdenada: getFirst() es 1 (el menor)", 1, l.getFirst());
    }

    static void test_ordenada_add_insertaMayorAlFinal() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        l.add(1); l.add(5); l.add(100); // 100 debe quedar al final
        assertEqual("LDEOrdenada: getLast() es 100 (el mayor)", 100, l.getLast());
    }

    static void test_ordenada_add_insertaEnMedio() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        l.add(1); l.add(3); l.add(2); // 2 va en el medio
        assertEqualString("LDEOrdenada: toString '[1, 2, 3]'", "[1, 2, 3]", l.toString());
    }

    static void test_ordenada_add_elementosDuplicados() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        l.add(5); l.add(5); l.add(5);
        assertEqual("LDEOrdenada: duplicados → size es 3", 3, l.getSize());
        assertIsTrue("LDEOrdenada: duplicados → sigue ordenada", listaOrdenada(l));
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE ORDENADA — operaciones heredadas
    // ==================================================================

    static void test_ordenada_getFirst_esMenor() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        l.add(5); l.add(1); l.add(9); l.add(3);
        assertEqual("LDEOrdenada: getFirst() es 1 (el menor)", 1, l.getFirst());
    }

    static void test_ordenada_getLast_esMayor() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        l.add(5); l.add(1); l.add(9); l.add(3);
        assertEqual("LDEOrdenada: getLast() es 9 (el mayor)", 9, l.getLast());
    }

    static void test_ordenada_removeFirst_extrae_menor() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        l.add(3); l.add(1); l.add(2);
        assertEqual("LDEOrdenada: removeFirst() extrae 1 (el menor)", 1, l.removeFirst());
        assertEqual("LDEOrdenada: nuevo getFirst() es 2", 2, l.getFirst());
    }

    static void test_ordenada_removeLast_extrae_mayor() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        l.add(3); l.add(1); l.add(2);
        assertEqual("LDEOrdenada: removeLast() extrae 3 (el mayor)", 3, l.removeLast());
        assertEqual("LDEOrdenada: nuevo getLast() es 2", 2, l.getLast());
    }

    static void test_ordenada_get_encuentraElemento() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        l.add(5); l.add(10); l.add(15);
        assertEqual("LDEOrdenada: get(10) devuelve 10", 10, l.get(10));
        assertIsTrue("LDEOrdenada: get(99) devuelve null", l.get(99) == null);
    }

    static void test_ordenada_del_eliminaYSigueOrdenada() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        l.add(1); l.add(3); l.add(5); l.add(7);
        l.del(3); // eliminamos un elemento del medio
        assertIsTrue("LDEOrdenada: del() → sigue ordenada",  listaOrdenada(l));
        assertIsTrue("LDEOrdenada: del() → 3 ya no existe",  l.get(3) == null);
    }

    static void test_ordenada_size_correcto() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        l.add(1); l.add(2); l.add(3);
        assertEqual("LDEOrdenada: size es 3", 3, l.getSize());
        l.removeFirst();
        assertEqual("LDEOrdenada: size decrece a 2", 2, l.getSize());
    }

    static void test_ordenada_isEmpty_trasClear() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        l.add(1); l.add(2);
        l.clear();
        assertIsTrue("LDEOrdenada: isEmpty() tras clear", l.isEmpty());
    }

    static void test_ordenada_iterador_ordenAscendente() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        l.add(4); l.add(2); l.add(1); l.add(3);
        assertIsTrue("LDEOrdenada: iterador recorre en orden ascendente", listaOrdenada(l));
    }

    static void test_ordenada_toString_ordenAscendente() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        l.add(3); l.add(1); l.add(2);
        assertEqualString("LDEOrdenada: toString '[1, 2, 3]'", "[1, 2, 3]", l.toString());
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE ORDENADA — tipos genéricos
    // ==================================================================

    static void test_ordenada_conStrings() {
        LDEOrdenada<String> l = new LDEOrdenada<>();
        l.add("pera"); l.add("manzana"); l.add("cereza");
        // Orden lexicográfico: cereza < manzana < pera
        assertEqualString("LDEOrdenada String: getFirst() es 'cereza'", "cereza", l.getFirst());
        assertEqualString("LDEOrdenada String: getLast() es 'pera'",    "pera",   l.getLast());
        assertEqualString("LDEOrdenada String: toString correcto",
                "[cereza, manzana, pera]", l.toString());
    }

    // ==================================================================
    // juego.model.EstructurasUtilizadas.LDE ORDENADA — volumen
    // ==================================================================

    static void test_ordenada_volumen_siempreOrdenada() {
        LDEOrdenada<Integer> l = new LDEOrdenada<>();
        // Insertamos en orden aleatorio simulado
        int[] vals = {50,10,90,30,70,20,80,40,60,100,5,95,15,85,25};
        for (int v : vals) l.add(v);
        assertEqual("LDEOrdenada volumen: size correcto", vals.length, l.getSize());
        assertIsTrue("LDEOrdenada volumen: siempre ordenada ascendente", listaOrdenada(l));
        assertEqual("LDEOrdenada volumen: getFirst() es 5",   5, l.getFirst());
        assertEqual("LDEOrdenada volumen: getLast() es 100", 100, l.getLast());
    }

    // ==================================================================
    // UTILIDADES
    // ==================================================================

    static boolean listaOrdenada(ListaDoblementeEnlazada<Integer> l) {
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
