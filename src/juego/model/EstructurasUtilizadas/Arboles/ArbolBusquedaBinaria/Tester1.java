import juego.model.EstructurasUtilizadas.Arboles.ArbolBusquedaBinaria.ArbolBusquedaBinaria;
import juego.model.EstructurasUtilizadas.Arboles.ArbolBusquedaBinaria.ArbolBusquedaBinariaEnteros;
import juego.model.EstructurasUtilizadas.LSE.Iterador;
import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;

/**
 * Suite de tests manuales para ArbolBusquedaBinaria y ArbolBusquedaBinariaEnteros.
 * No requiere JUnit. Ejecutar con el botón ▶️ de IntelliJ.
 */
public class Tester1 {

    private static int totalTests  = 0;
    private static int totalPassed = 0;
    private static int totalFailed = 0;

    public static void main(String[] args) {

        printHeader("ÁRBOL VACÍO");
        test_arbolVacio_altura0();
        test_arbolVacio_grado0();
        test_arbolVacio_subarboles_null();
        test_arbolVacio_inorden_listaVacia();
        test_arbolVacio_isHomogeneo();
        test_arbolVacio_isCompleto();
        test_arbolVacio_isSemiCompleto();

        printHeader("INSERCIÓN Y RECORRIDOS");
        test_add_unElemento_altura1();
        test_add_ordenBST_inordenOrdenado();
        test_add_preordenPrimeroEsRaiz();
        test_add_postordenUltimoEsRaiz();
        test_add_inorden_vs_preorden_vs_postorden_mismaSuma();
        test_recorridos_tamañoCorrecto();

        printHeader("ALTURA");
        test_altura_arbolDegeneradoDerecha();
        test_altura_arbolDegeneradaIzquierda();
        test_altura_arbolBalanceado();
        test_altura_unNodo_es1();

        printHeader("GRADO");
        test_grado_soloRaiz_es0();
        test_grado_raizConDosHijos_es2();
        test_grado_arbolDegenerado_es1();

        printHeader("SUBÁRBOLES");
        test_subArbolIzquierdo_existe();
        test_subArbolDerecho_existe();
        test_subArbolIzquierdo_null_siNoExiste();
        test_subArbolDerecho_null_siNoExiste();
        test_subarboles_suma_masRaiz_igualTotal();

        printHeader("NIVEL Y CAMINO");
        test_nivel_raizEsNivel0();
        test_nivel_hijosEnNivel1();
        test_camino_raiz();
        test_camino_elementoExistente();
        test_camino_elementoInexistente_noTerminaEnBuscado();

        printHeader("PROPIEDADES ESTRUCTURALES");
        test_isHomogeneo_arbolPerfecto();
        test_isHomogeneo_conUnSoloHijo_false();
        test_isCompleto_arbolPerfecto();
        test_isCompleto_arbolDegenerado_false();
        test_isSemiCompleto_arbolBalanceado();

        printHeader("ÁRBOL ENTEROS — getSuma()");
        test_getSuma_arbolVacio();
        test_getSuma_unElemento();
        test_getSuma_variosElementos();
        test_getSuma_igualSumaInorden();
        test_getSuma_0_al_128_es8256();

        printHeader("VOLUMEN — inserción ascendente (peor caso BST)");
        test_volumen_ascendente();

        printHeader("VOLUMEN — inserción equilibrada");
        test_volumen_equilibrado();

        printResumen();
    }

    // ==================================================================
    // ÁRBOL VACÍO
    // ==================================================================

    static void test_arbolVacio_altura0() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        assertEqual("Vacío: altura es 0", 0, arbol.getAltura());
    }

    static void test_arbolVacio_grado0() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        assertEqual("Vacío: grado es 0", 0, arbol.getGrado());
    }

    static void test_arbolVacio_subarboles_null() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        assertIsTrue("Vacío: subárbol izquierdo es null", arbol.getSubArbolIzquierdo() == null);
        assertIsTrue("Vacío: subárbol derecho es null",   arbol.getSubArbolDerecho()   == null);
    }

    static void test_arbolVacio_inorden_listaVacia() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        assertEqual("Vacío: inorden size es 0", 0, arbol.getListaOrdenadaCentral().getSize());
    }

    static void test_arbolVacio_isHomogeneo() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        assertIsTrue("Vacío: isHomogeneo true", arbol.isArbolHomogeneo());
    }

    static void test_arbolVacio_isCompleto() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        assertIsTrue("Vacío: isCompleto true", arbol.isArbolCompleto());
    }

    static void test_arbolVacio_isSemiCompleto() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        assertIsTrue("Vacío: isSemiCompleto true", arbol.isArbolSemiCompleto());
    }

    // ==================================================================
    // INSERCIÓN Y RECORRIDOS
    // ==================================================================

    static void test_add_unElemento_altura1() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(10);
        assertEqual("add: un elemento → altura 1", 1, arbol.getAltura());
    }

    static void test_add_ordenBST_inordenOrdenado() {
        // Inorden de un BST siempre produce la secuencia ordenada de menor a mayor
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(5); arbol.add(3); arbol.add(7); arbol.add(1); arbol.add(4);
        ListaSimplementeEnlazada<Integer> lista = arbol.getListaOrdenadaCentral();
        Iterador<Integer> it = lista.getIterador();
        int anterior = Integer.MIN_VALUE;
        boolean ordenado = true;
        while (it.hasNext()) {
            int actual = it.next();
            if (actual < anterior) { ordenado = false; break; }
            anterior = actual;
        }
        assertIsTrue("Inorden: secuencia siempre ordenada ascendente", ordenado);
    }

    static void test_add_preordenPrimeroEsRaiz() {
        // En preorden, el primer elemento siempre es la raíz
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(10); arbol.add(5); arbol.add(15);
        Iterador<Integer> it = arbol.getListaPreOrden().getIterador();
        assertEqual("Preorden: primer elemento es la raíz (10)", 10, it.next());
    }

    static void test_add_postordenUltimoEsRaiz() {
        // En postorden, el último elemento siempre es la raíz
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(10); arbol.add(5); arbol.add(15);
        ListaSimplementeEnlazada<Integer> lista = arbol.getListaPostOrden();
        Iterador<Integer> it = lista.getIterador();
        int ultimo = -1;
        while (it.hasNext()) ultimo = it.next();
        assertEqual("Postorden: último elemento es la raíz (10)", 10, ultimo);
    }

    static void test_add_inorden_vs_preorden_vs_postorden_mismaSuma() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        int[] vals = {5, 3, 7, 1, 4, 6, 8};
        for (int v : vals) arbol.add(v);
        int sumaIn = sumarLista(arbol.getListaOrdenadaCentral());
        int sumaPre = sumarLista(arbol.getListaPreOrden());
        int sumaPost = sumarLista(arbol.getListaPostOrden());
        assertIsTrue("Recorridos: inorden == preorden == postorden (misma suma)",
                sumaIn == sumaPre && sumaPre == sumaPost);
    }

    static void test_recorridos_tamañoCorrecto() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        int[] vals = {5, 3, 7, 1, 4, 6, 8};
        for (int v : vals) arbol.add(v);
        assertEqual("Recorridos: inorden size == 7",   7, arbol.getListaOrdenadaCentral().getSize());
        assertEqual("Recorridos: preorden size == 7",  7, arbol.getListaPreOrden().getSize());
        assertEqual("Recorridos: postorden size == 7", 7, arbol.getListaPostOrden().getSize());
    }

    // ==================================================================
    // ALTURA
    // ==================================================================

    static void test_altura_arbolDegeneradoDerecha() {
        // Insertar en orden ascendente → árbol degenerado hacia la derecha
        // Altura = número de nodos insertados
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        for (int i = 1; i <= 5; i++) arbol.add(i);
        assertEqual("Altura: árbol degenerado derecha con 5 nodos → altura 5", 5, arbol.getAltura());
    }

    static void test_altura_arbolDegeneradaIzquierda() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        for (int i = 5; i >= 1; i--) arbol.add(i);
        assertEqual("Altura: árbol degenerado izquierda con 5 nodos → altura 5", 5, arbol.getAltura());
    }

    static void test_altura_arbolBalanceado() {
        // Árbol perfecto con 7 nodos → altura 3
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(4); arbol.add(2); arbol.add(6); arbol.add(1); arbol.add(3); arbol.add(5); arbol.add(7);
        assertEqual("Altura: árbol perfecto 7 nodos → altura 3", 3, arbol.getAltura());
    }

    static void test_altura_unNodo_es1() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(1);
        assertEqual("Altura: un nodo → altura 1", 1, arbol.getAltura());
    }

    // ==================================================================
    // GRADO
    // ==================================================================

    static void test_grado_soloRaiz_es0() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(5);
        assertEqual("Grado: solo raíz → grado 0", 0, arbol.getGrado());
    }

    static void test_grado_raizConDosHijos_es2() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(5); arbol.add(3); arbol.add(7);
        assertEqual("Grado: raíz con 2 hijos → grado 2", 2, arbol.getGrado());
    }

    static void test_grado_arbolDegenerado_es1() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(1); arbol.add(2); arbol.add(3);
        assertEqual("Grado: árbol degenerado → grado 1", 1, arbol.getGrado());
    }

    // ==================================================================
    // SUBÁRBOLES
    // ==================================================================

    static void test_subArbolIzquierdo_existe() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(5); arbol.add(3); arbol.add(7);
        assertIsTrue("SubÁrbol izquierdo: existe y no es null", arbol.getSubArbolIzquierdo() != null);
    }

    static void test_subArbolDerecho_existe() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(5); arbol.add(3); arbol.add(7);
        assertIsTrue("SubÁrbol derecho: existe y no es null", arbol.getSubArbolDerecho() != null);
    }

    static void test_subArbolIzquierdo_null_siNoExiste() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(5); arbol.add(7); // solo hijo derecho
        assertIsTrue("SubÁrbol izquierdo: null si no existe", arbol.getSubArbolIzquierdo() == null);
    }

    static void test_subArbolDerecho_null_siNoExiste() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(5); arbol.add(3); // solo hijo izquierdo
        assertIsTrue("SubÁrbol derecho: null si no existe", arbol.getSubArbolDerecho() == null);
    }

    static void test_subarboles_suma_masRaiz_igualTotal() {
        // suma(izq) + suma(der) + raiz = suma total del árbol
        ArbolBusquedaBinariaEnteros arbol = new ArbolBusquedaBinariaEnteros();
        for (int i = 0; i <= 10; i++) arbol.add(i); // raíz = 0
        int total = arbol.getSuma();
        int sumaIzq = 0, sumaDer = 0;
        ArbolBusquedaBinaria<Integer> izq = arbol.getSubArbolIzquierdo();
        ArbolBusquedaBinaria<Integer> der = arbol.getSubArbolDerecho();
        if (izq != null) sumaIzq = sumarLista(izq.getListaOrdenadaCentral());
        if (der != null) sumaDer = sumarLista(der.getListaOrdenadaCentral());
        // La raíz es 0 cuando insertamos en orden ascendente
        assertEqual("Subárboles: izq + der + raíz(0) == total(55)", total, sumaIzq + sumaDer);
    }

    // ==================================================================
    // NIVEL Y CAMINO
    // ==================================================================

    static void test_nivel_raizEsNivel0() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(5); arbol.add(3); arbol.add(7);
        ListaSimplementeEnlazada<Integer> nivel0 = arbol.getListaDatosNivel(0);
        assertEqual("Nivel 0: solo la raíz (size 1)", 1, nivel0.getSize());
        assertEqual("Nivel 0: valor es 5", 5, (int) nivel0.getIterador().next());
    }

    static void test_nivel_hijosEnNivel1() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(5); arbol.add(3); arbol.add(7);
        ListaSimplementeEnlazada<Integer> nivel1 = arbol.getListaDatosNivel(1);
        assertEqual("Nivel 1: dos hijos (size 2)", 2, nivel1.getSize());
    }

    static void test_camino_raiz() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(5); arbol.add(3); arbol.add(7);
        ListaSimplementeEnlazada<Integer> camino = arbol.getListaCamino(5);
        assertEqual("Camino a raíz: size 1", 1, camino.getSize());
        assertEqual("Camino a raíz: único elemento es 5", 5, (int) camino.getIterador().next());
    }

    static void test_camino_elementoExistente() {
        // Árbol: raíz=5, izq=3, der=7 → camino a 7 es [5, 7]
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(5); arbol.add(3); arbol.add(7);
        ListaSimplementeEnlazada<Integer> camino = arbol.getListaCamino(7);
        assertEqual("Camino a 7: size 2 (pasa por raíz y derecha)", 2, camino.getSize());
    }

    static void test_camino_elementoInexistente_noTerminaEnBuscado() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(5); arbol.add(3); arbol.add(7);
        ListaSimplementeEnlazada<Integer> camino = arbol.getListaCamino(99);
        // getListaCamino recorre hasta donde puede; el último nodo del camino no debe ser 99
        Iterador<Integer> it99 = camino.getIterador();
        int ultimo99 = -1;
        while (it99.hasNext()) ultimo99 = it99.next();
        assertIsFalse("Camino a 99 (inexistente): último nodo del camino no es 99", ultimo99 == 99);
    }

    // ==================================================================
    // PROPIEDADES ESTRUCTURALES
    // ==================================================================

    static void test_isHomogeneo_arbolPerfecto() {
        // Árbol perfecto: todos los nodos internos tienen 2 hijos
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(4); arbol.add(2); arbol.add(6); arbol.add(1); arbol.add(3); arbol.add(5); arbol.add(7);
        assertIsTrue("isHomogeneo: árbol perfecto → true", arbol.isArbolHomogeneo());
    }

    static void test_isHomogeneo_conUnSoloHijo_false() {
        // Raíz con solo hijo derecho → no es homogéneo
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(5); arbol.add(7);
        assertIsFalse("isHomogeneo: raíz con un solo hijo → false", arbol.isArbolHomogeneo());
    }

    static void test_isCompleto_arbolPerfecto() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(4); arbol.add(2); arbol.add(6); arbol.add(1); arbol.add(3); arbol.add(5); arbol.add(7);
        assertIsTrue("isCompleto: árbol perfecto → true", arbol.isArbolCompleto());
    }

    static void test_isCompleto_arbolDegenerado_false() {
        // Árbol: raíz=5, izq=3, der=7, der.der=9
        // Las hojas están en distintos niveles (3 en nivel 1, 9 en nivel 2) → no es completo
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(5); arbol.add(3); arbol.add(7); arbol.add(9);
        assertIsFalse("isCompleto: hojas en distintos niveles → false", arbol.isArbolCompleto());
    }

    static void test_isSemiCompleto_arbolBalanceado() {
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        arbol.add(4); arbol.add(2); arbol.add(6); arbol.add(1); arbol.add(3);
        assertIsTrue("isSemiCompleto: árbol casi completo → true", arbol.isArbolSemiCompleto());
    }

    // ==================================================================
    // ÁRBOL ENTEROS — getSuma()
    // ==================================================================

    static void test_getSuma_arbolVacio() {
        ArbolBusquedaBinariaEnteros arbol = new ArbolBusquedaBinariaEnteros();
        assertEqual("getSuma: árbol vacío → 0", 0, arbol.getSuma());
    }

    static void test_getSuma_unElemento() {
        ArbolBusquedaBinariaEnteros arbol = new ArbolBusquedaBinariaEnteros();
        arbol.add(42);
        assertEqual("getSuma: un elemento (42) → 42", 42, arbol.getSuma());
    }

    static void test_getSuma_variosElementos() {
        ArbolBusquedaBinariaEnteros arbol = new ArbolBusquedaBinariaEnteros();
        arbol.add(1); arbol.add(2); arbol.add(3); arbol.add(4); arbol.add(5);
        assertEqual("getSuma: 1+2+3+4+5 → 15", 15, arbol.getSuma());
    }

    static void test_getSuma_igualSumaInorden() {
        ArbolBusquedaBinariaEnteros arbol = new ArbolBusquedaBinariaEnteros();
        int[] vals = {10, 5, 15, 3, 7, 12, 20};
        for (int v : vals) arbol.add(v);
        int sumaInorden = sumarLista(arbol.getListaOrdenadaCentral());
        assertEqual("getSuma: igual a suma inorden", sumaInorden, arbol.getSuma());
    }

    static void test_getSuma_0_al_128_es8256() {
        // Caso del Tester1 original: suma 0+1+...+128 = 8256
        ArbolBusquedaBinariaEnteros arbol = new ArbolBusquedaBinariaEnteros();
        for (int i = 0; i <= 128; i++) arbol.add(i);
        assertEqual("getSuma: 0..128 → 8256", 8256, arbol.getSuma());
    }

    // ==================================================================
    // VOLUMEN
    // ==================================================================

    static void test_volumen_ascendente() {
        // Peor caso BST: inserción en orden ascendente → árbol degenerado
        ArbolBusquedaBinariaEnteros arbol = new ArbolBusquedaBinariaEnteros();
        for (int i = 0; i <= 128; i++) arbol.add(i);
        assertEqual("Volumen ascendente: suma es 8256",    8256, arbol.getSuma());
        assertEqual("Volumen ascendente: altura es 129",    129, arbol.getAltura());
        assertEqual("Volumen ascendente: grado es 1",         1, arbol.getGrado());
        assertEqual("Volumen ascendente: inorden size 129",  129, arbol.getListaOrdenadaCentral().getSize());
        // Camino a 110 pasa por todos los nodos anteriores
        assertEqual("Volumen ascendente: camino a 110 tiene 111 nodos", 111, arbol.getListaCamino(110).getSize());
    }

    static void test_volumen_equilibrado() {
        // Árbol perfecto de 7 nodos insertados en orden BFS: 4,2,6,1,3,5,7
        // Todos los nodos internos tienen 2 hijos → altura 3, homogéneo y completo
        ArbolBusquedaBinaria<Integer> arbol = new ArbolBusquedaBinaria<>();
        int[] bfs = {4, 2, 6, 1, 3, 5, 7};
        for (int v : bfs) arbol.add(v);
        assertEqual("Volumen equilibrado: suma 1+2+..+7 → 28", 28, sumarLista(arbol.getListaOrdenadaCentral()));
        assertEqual("Volumen equilibrado: altura árbol perfecto 7 nodos → 3", 3, arbol.getAltura());
        assertEqual("Volumen equilibrado: inorden size 7", 7, arbol.getListaOrdenadaCentral().getSize());
        assertIsTrue("Volumen equilibrado: isHomogeneo → true", arbol.isArbolHomogeneo());
        assertIsTrue("Volumen equilibrado: isCompleto → true", arbol.isArbolCompleto());
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

    // ==================================================================
    // MOTOR DE ASERCIONES
    // ==================================================================

    @FunctionalInterface interface Accion { void ejecutar(); }

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

    static void assertThrows(String nombre, Class<? extends Exception> tipo, Accion accion) {
        totalTests++;
        try { accion.ejecutar(); registrarFallo(nombre + " → no se lanzó excepción"); }
        catch (Exception e) {
            if (tipo.isInstance(e)) registrarOk(nombre);
            else registrarFallo(nombre + " → excepción incorrecta: " + e.getClass().getSimpleName());
        }
    }

    static void registrarOk(String n)     { totalPassed++; System.out.println("  [OK]   " + n); }
    static void registrarFallo(String n)  { totalFailed++; System.out.println("  [FAIL] " + n); }

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
