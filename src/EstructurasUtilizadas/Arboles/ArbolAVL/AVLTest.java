import EstructurasUtilizadas.Arboles.ArbolAVL.ArbolAVL;
import EstructurasUtilizadas.LSE.Iterador;
import EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;

/**
 * Suite de tests manuales para ArbolAVL.
 * No requiere JUnit. Ejecutar con el botón ▶️ de IntelliJ.
 *
 * Propiedad clave AVL: |altura_izq - altura_der| <= 1 en cada nodo.
 * Inserción en orden ascendente no genera árbol degenerado (a diferencia del BST).
 */
public class AVLTest {

    private static int totalTests  = 0;
    private static int totalPassed = 0;
    private static int totalFailed = 0;

    public static void main(String[] args) {

        printHeader("ÁRBOL VACÍO");
        test_avl_vacio_altura0();
        test_avl_vacio_inorden_vacio();

        printHeader("INSERCIÓN Y EQUILIBRIO — caso LL (rotación derecha)");
        test_avl_LL_insercion_ascendente_equilibrado();
        test_avl_LL_altura_menor_que_BST();

        printHeader("INSERCIÓN Y EQUILIBRIO — caso RR (rotación izquierda)");
        test_avl_RR_insercion_descendente_equilibrado();
        test_avl_RR_altura_menor_que_BST();

        printHeader("INSERCIÓN Y EQUILIBRIO — caso LR (rotación doble izq-der)");
        test_avl_LR_equilibrado();

        printHeader("INSERCIÓN Y EQUILIBRIO — caso RL (rotación doble der-izq)");
        test_avl_RL_equilibrado();

        printHeader("INORDEN SIEMPRE ORDENADO");
        test_avl_inorden_ordenado_insercionAscendente();
        test_avl_inorden_ordenado_insercionDescendente();
        test_avl_inorden_ordenado_insercionAleatoria();

        printHeader("RECORRIDOS — suma consistente");
        test_avl_suma_inorden_preorden_postorden_igual();

        printHeader("ALTURA AVL vs BST");
        test_avl_alturaLogaritmica_insercionAscendente();
        test_avl_alturaLogaritmica_insercionDescendente();

        printHeader("PROPIEDADES ESTRUCTURALES");
        test_avl_grado_arbolBalanceado();
        test_avl_subarboles_suma_correcta();

        printHeader("VOLUMEN — 0 al 128 en orden ascendente");
        test_avl_volumen_ascendente_suma();
        test_avl_volumen_ascendente_altura_logaritmica();
        test_avl_volumen_ascendente_inorden_size();
        test_avl_volumen_ascendente_inordenOrdenado();

        printHeader("VOLUMEN — 0 al 128 en orden descendente");
        test_avl_volumen_descendente_suma();
        test_avl_volumen_descendente_altura_logaritmica();

        printResumen();
    }

    // ==================================================================
    // ÁRBOL VACÍO
    // ==================================================================

    static void test_avl_vacio_altura0() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        assertEqual("AVL vacío: altura es 0", 0, avl.getAltura());
    }

    static void test_avl_vacio_inorden_vacio() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        assertEqual("AVL vacío: inorden size 0", 0, avl.getListaOrdenadaCentral().getSize());
    }

    // ==================================================================
    // CASO LL — rotación simple derecha
    // ==================================================================

    static void test_avl_LL_insercion_ascendente_equilibrado() {
        // Insertar 1, 2, 3 en BST daría árbol degenerado (altura 3)
        // En AVL se reequilibra → altura 2
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        avl.add(1); avl.add(2); avl.add(3);
        assertEqual("LL: altura tras insertar 1,2,3 → 2 (reequilibrado)", 2, avl.getAltura());
    }

    static void test_avl_LL_altura_menor_que_BST() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        for (int i = 1; i <= 7; i++) avl.add(i);
        assertIsTrue("LL: altura AVL < 7 (BST daría 7)", avl.getAltura() < 7);
    }

    // ==================================================================
    // CASO RR — rotación simple izquierda
    // ==================================================================

    static void test_avl_RR_insercion_descendente_equilibrado() {
        // Insertar 3, 2, 1 → rotación RR
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        avl.add(3); avl.add(2); avl.add(1);
        assertEqual("RR: altura tras insertar 3,2,1 → 2 (reequilibrado)", 2, avl.getAltura());
    }

    static void test_avl_RR_altura_menor_que_BST() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        for (int i = 7; i >= 1; i--) avl.add(i);
        assertIsTrue("RR: altura AVL < 7 (BST daría 7)", avl.getAltura() < 7);
    }

    // ==================================================================
    // CASO LR — rotación doble izquierda-derecha
    // ==================================================================

    static void test_avl_LR_equilibrado() {
        // Insertar 3, 1, 2 provoca caso LR
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        avl.add(3); avl.add(1); avl.add(2);
        assertEqual("LR: altura tras insertar 3,1,2 → 2 (reequilibrado)", 2, avl.getAltura());
        // Verificar inorden correcto tras rotación doble
        ListaSimplementeEnlazada<Integer> lista = avl.getListaOrdenadaCentral();
        assertIsTrue("LR: inorden correcto [1,2,3]", listaOrdenada(lista));
    }

    // ==================================================================
    // CASO RL — rotación doble derecha-izquierda
    // ==================================================================

    static void test_avl_RL_equilibrado() {
        // Insertar 1, 3, 2 provoca caso RL
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        avl.add(1); avl.add(3); avl.add(2);
        assertEqual("RL: altura tras insertar 1,3,2 → 2 (reequilibrado)", 2, avl.getAltura());
        assertIsTrue("RL: inorden correcto [1,2,3]", listaOrdenada(avl.getListaOrdenadaCentral()));
    }

    // ==================================================================
    // INORDEN SIEMPRE ORDENADO
    // ==================================================================

    static void test_avl_inorden_ordenado_insercionAscendente() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        for (int i = 1; i <= 15; i++) avl.add(i);
        assertIsTrue("Inorden ascendente: siempre ordenado", listaOrdenada(avl.getListaOrdenadaCentral()));
    }

    static void test_avl_inorden_ordenado_insercionDescendente() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        for (int i = 15; i >= 1; i--) avl.add(i);
        assertIsTrue("Inorden descendente: siempre ordenado", listaOrdenada(avl.getListaOrdenadaCentral()));
    }

    static void test_avl_inorden_ordenado_insercionAleatoria() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        int[] vals = {8, 3, 10, 1, 6, 14, 4, 7, 13};
        for (int v : vals) avl.add(v);
        assertIsTrue("Inorden aleatorio: siempre ordenado", listaOrdenada(avl.getListaOrdenadaCentral()));
    }

    // ==================================================================
    // SUMA CONSISTENTE EN LOS 3 RECORRIDOS
    // ==================================================================

    static void test_avl_suma_inorden_preorden_postorden_igual() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        int[] vals = {5, 3, 7, 1, 4, 6, 8};
        for (int v : vals) avl.add(v);
        int sumaIn   = sumarLista(avl.getListaOrdenadaCentral());
        int sumaPre  = sumarLista(avl.getListaPreOrden());
        int sumaPost = sumarLista(avl.getListaPostOrden());
        assertIsTrue("AVL: suma inorden == preorden == postorden",
                sumaIn == sumaPre && sumaPre == sumaPost);
    }

    // ==================================================================
    // ALTURA AVL vs BST
    // ==================================================================

    static void test_avl_alturaLogaritmica_insercionAscendente() {
        // Con 15 nodos en orden ascendente, BST daría altura 15, AVL debe dar <= 5
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        for (int i = 1; i <= 15; i++) avl.add(i);
        assertIsTrue("AVL altura logarítmica ascendente: <= 5 para 15 nodos", avl.getAltura() <= 5);
    }

    static void test_avl_alturaLogaritmica_insercionDescendente() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        for (int i = 15; i >= 1; i--) avl.add(i);
        assertIsTrue("AVL altura logarítmica descendente: <= 5 para 15 nodos", avl.getAltura() <= 5);
    }

    // ==================================================================
    // PROPIEDADES ESTRUCTURALES
    // ==================================================================

    static void test_avl_grado_arbolBalanceado() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        avl.add(4); avl.add(2); avl.add(6); avl.add(1); avl.add(3); avl.add(5); avl.add(7);
        assertEqual("AVL grado: árbol perfecto → grado 2", 2, avl.getGrado());
    }

    static void test_avl_subarboles_suma_correcta() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        for (int i = 1; i <= 7; i++) avl.add(i);
        int total = sumarLista(avl.getListaOrdenadaCentral());
        assertEqual("AVL subárboles: suma total 1..7 → 28", 28, total);
    }

    // ==================================================================
    // VOLUMEN 0..128 ASCENDENTE
    // ==================================================================

    static void test_avl_volumen_ascendente_suma() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        for (int i = 0; i <= 128; i++) avl.add(i);
        assertEqual("AVL volumen ascendente: suma es 8256", 8256, sumarLista(avl.getListaOrdenadaCentral()));
    }

    static void test_avl_volumen_ascendente_altura_logaritmica() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        for (int i = 0; i <= 128; i++) avl.add(i);
        // BST daría 129, AVL debe dar aproximadamente log2(129) ≈ 7-8
        assertIsTrue("AVL volumen ascendente: altura << 129 (logarítmica)", avl.getAltura() <= 15);
        System.out.println("    → Altura AVL con 129 nodos en orden ascendente: " + avl.getAltura());
    }

    static void test_avl_volumen_ascendente_inorden_size() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        for (int i = 0; i <= 128; i++) avl.add(i);
        assertEqual("AVL volumen ascendente: inorden size 129", 129, avl.getListaOrdenadaCentral().getSize());
    }

    static void test_avl_volumen_ascendente_inordenOrdenado() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        for (int i = 0; i <= 128; i++) avl.add(i);
        assertIsTrue("AVL volumen ascendente: inorden ordenado", listaOrdenada(avl.getListaOrdenadaCentral()));
    }

    // ==================================================================
    // VOLUMEN 0..128 DESCENDENTE
    // ==================================================================

    static void test_avl_volumen_descendente_suma() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        for (int i = 128; i >= 0; i--) avl.add(i);
        assertEqual("AVL volumen descendente: suma es 8256", 8256, sumarLista(avl.getListaOrdenadaCentral()));
    }

    static void test_avl_volumen_descendente_altura_logaritmica() {
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        for (int i = 128; i >= 0; i--) avl.add(i);
        assertIsTrue("AVL volumen descendente: altura logarítmica", avl.getAltura() <= 15);
        System.out.println("    → Altura AVL con 129 nodos en orden descendente: " + avl.getAltura());
    }

    // ==================================================================
    // UTILIDADES
    // ==================================================================

    static int sumarLista(ListaSimplementeEnlazada<Integer> lista) {
        Iterador<Integer> it = lista.getIterador();
        int suma = 0;
        while (it.hasNext()) suma += it.next();
        return suma;
    }

    static boolean listaOrdenada(ListaSimplementeEnlazada<Integer> lista) {
        Iterador<Integer> it = lista.getIterador();
        int anterior = Integer.MIN_VALUE;
        while (it.hasNext()) {
            int actual = it.next();
            if (actual < anterior) return false;
            anterior = actual;
        }
        return true;
    }

    // ==================================================================
    // MOTOR DE ASERCIONES
    // ==================================================================

    static void assertEqual(String nombre, int esperado, int actual) {
        totalTests++;
        if (esperado == actual) registrarOk(nombre);
        else registrarFallo(nombre + " → esperado: " + esperado + ", obtenido: " + actual);
    }

    static void assertIsTrue(String nombre, boolean c) {
        totalTests++;
        if (c) registrarOk(nombre);
        else registrarFallo(nombre + " → esperado: true, obtenido: false");
    }

    static void assertIsFalse(String nombre, boolean c) {
        totalTests++;
        if (!c) registrarOk(nombre);
        else registrarFallo(nombre + " → esperado: false, obtenido: true");
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
