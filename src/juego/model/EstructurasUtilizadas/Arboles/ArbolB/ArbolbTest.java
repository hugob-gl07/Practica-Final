import juego.model.EstructurasUtilizadas.Arboles.ArbolB.ArbolB;

/**
 * Suite de tests manuales para ArbolB.
 * No requiere JUnit. Ejecutar con el botón ▶️ de IntelliJ.
 * Propiedades clave del Árbol B de orden n:
 *   - Cada nodo tiene como máximo n-1 claves y n hijos.
 *   - Todas las hojas están al mismo nivel.
 *   - Tras insertar se hace split si el nodo se llena.
 *   - Tras eliminar se reequilibra por rotación o fusión.
 */
public class ArbolbTest {

    private static int totalTests  = 0;
    private static int totalPassed = 0;
    private static int totalFailed = 0;

    public static void main(String[] args) {

        printHeader("ÁRBOL VACÍO");
        test_arbolVacio_isEmpty();
        test_arbolVacio_buscar_devuelveFalse();
        test_remove_enArbolVacio_noLanzaExcepcion();

        printHeader("INSERCIÓN BÁSICA — orden 3");
        test_add_unElemento_noEstaVacio();
        test_add_unElemento_buscarLoEncuentra();
        test_add_elementoNoInsertado_buscarDevuelveFalse();
        test_add_variosElementos_todosEncontrados();
        test_add_elementosDuplicados_seInsertan();

        printHeader("SPLIT — división de nodo lleno (orden 3)");
        test_split_orden3_tresElementos_sinDesbordamiento();
        test_split_orden3_cuartoElemento_provocaSplit();
        test_split_orden3_todosElementosAccesibles_trasSplit();
        test_split_raiz_seCreaNuevaRaiz();

        printHeader("INSERCIÓN — orden 4");
        test_orden4_hasta3elementos_sinSplit();
        test_orden4_cuartoElemento_provocaSplit();
        test_orden4_volumen_todosAccesibles();

        printHeader("INSERCIÓN — orden 5");
        test_orden5_volumen_todosAccesibles();
        test_orden5_insercionAscendente();
        test_orden5_insercionDescendente();

        printHeader("ELIMINACIÓN — nodo hoja");
        test_remove_unicoElemento_arbolVacio();
        test_remove_elementoHoja_yaNoSeEncuentra();
        test_remove_elementoInexistente_sinEfecto();
        test_remove_variosElementos_restantesAccesibles();

        printHeader("ELIMINACIÓN — nodo interno (sucesor)");
        test_remove_nodoInterno_sucesorSustituye();
        test_remove_nodoInterno_restantesAccesibles();

        printHeader("ELIMINACIÓN — reequilibrio por rotación");
        test_remove_rotacionIzquierda();
        test_remove_rotacionDerecha();

        printHeader("ELIMINACIÓN — reequilibrio por fusión");
        test_remove_fusion_arbolSigueConsistente();

        printHeader("ORDEN 3 — ciclo completo add + remove");
        test_cicloCompleto_orden3();

        printHeader("ORDEN 5 — ciclo completo add + remove");
        test_cicloCompleto_orden5();

        printHeader("VOLUMEN — orden 3, 100 elementos");
        test_volumen_orden3_100elementos();

        printHeader("VOLUMEN — orden 7, 200 elementos");
        test_volumen_orden7_200elementos();

        printHeader("VOLUMEN — eliminar todos los elementos");
        test_volumen_eliminarTodos_arbolVacio();

        printResumen();
    }

    // ==================================================================
    // ÁRBOL VACÍO
    // ==================================================================

    static void test_arbolVacio_isEmpty() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        assertIsTrue("Vacío: isEmpty() es true", arbol.isEmpty());
    }

    static void test_arbolVacio_buscar_devuelveFalse() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        assertIsFalse("Vacío: buscar(5) devuelve false", arbol.buscar(5));
    }

    static void test_remove_enArbolVacio_noLanzaExcepcion() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        try {
            arbol.remove(99);
            registrarOk("remove en vacío: no lanza excepción");
            totalTests++;
        } catch (Exception e) {
            totalTests++;
            registrarFallo("remove en vacío: lanzó excepción inesperada: " + e.getClass().getSimpleName());
        }
    }

    // ==================================================================
    // INSERCIÓN BÁSICA — orden 3
    // ==================================================================

    static void test_add_unElemento_noEstaVacio() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        arbol.add(10);
        assertIsFalse("add: árbol no vacío tras insertar", arbol.isEmpty());
    }

    static void test_add_unElemento_buscarLoEncuentra() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        arbol.add(10);
        assertIsTrue("add: buscar(10) devuelve true", arbol.buscar(10));
    }

    static void test_add_elementoNoInsertado_buscarDevuelveFalse() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        arbol.add(10);
        assertIsFalse("add: buscar(99) devuelve false (no insertado)", arbol.buscar(99));
    }

    static void test_add_variosElementos_todosEncontrados() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        int[] vals = {5, 10, 15, 20, 25};
        for (int v : vals) arbol.add(v);
        boolean todos = true;
        for (int v : vals) if (!arbol.buscar(v)) { todos = false; break; }
        assertIsTrue("add: todos los elementos insertados son encontrados", todos);
    }

    static void test_add_elementosDuplicados_seInsertan() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        arbol.add(5);
        arbol.add(5);
        assertIsTrue("add: elemento duplicado se inserta y se encuentra", arbol.buscar(5));
    }

    // ==================================================================
    // SPLIT — orden 3
    // ==================================================================

    static void test_split_orden3_tresElementos_sinDesbordamiento() {
        // Orden 3: max 2 claves por nodo. Con 2 elementos no hay split aún
        ArbolB<Integer> arbol = new ArbolB<>(3);
        arbol.add(10); arbol.add(20);
        assertIsTrue("Split orden 3: 2 elementos sin split, buscar(10)", arbol.buscar(10));
        assertIsTrue("Split orden 3: 2 elementos sin split, buscar(20)", arbol.buscar(20));
    }

    static void test_split_orden3_cuartoElemento_provocaSplit() {
        // El tercer elemento provoca split en orden 3
        ArbolB<Integer> arbol = new ArbolB<>(3);
        arbol.add(10); arbol.add(20); arbol.add(30);
        assertIsTrue("Split orden 3: después de split, buscar(10)", arbol.buscar(10));
        assertIsTrue("Split orden 3: después de split, buscar(20)", arbol.buscar(20));
        assertIsTrue("Split orden 3: después de split, buscar(30)", arbol.buscar(30));
    }

    static void test_split_orden3_todosElementosAccesibles_trasSplit() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        int[] vals = {1, 2, 3, 4, 5, 6, 7};
        for (int v : vals) arbol.add(v);
        boolean todos = true;
        for (int v : vals) if (!arbol.buscar(v)) { todos = false; break; }
        assertIsTrue("Split orden 3: 7 elementos todos accesibles tras múltiples splits", todos);
    }

    static void test_split_raiz_seCreaNuevaRaiz() {
        // Cuando la raíz se divide, el árbol crece en altura
        // Verificamos que tras el split todos los datos siguen accesibles
        ArbolB<Integer> arbol = new ArbolB<>(3);
        arbol.add(10); arbol.add(20); arbol.add(30); // Aquí se divide la raíz
        assertIsFalse("Split raíz: árbol no vacío", arbol.isEmpty());
        assertIsTrue("Split raíz: todos los datos accesibles", arbol.buscar(10) && arbol.buscar(20) && arbol.buscar(30));
    }

    // ==================================================================
    // INSERCIÓN — orden 4
    // ==================================================================

    static void test_orden4_hasta3elementos_sinSplit() {
        ArbolB<Integer> arbol = new ArbolB<>(4);
        arbol.add(5); arbol.add(10); arbol.add(15);
        assertIsTrue("Orden 4: 3 elementos accesibles sin split",
                arbol.buscar(5) && arbol.buscar(10) && arbol.buscar(15));
    }

    static void test_orden4_cuartoElemento_provocaSplit() {
        ArbolB<Integer> arbol = new ArbolB<>(4);
        arbol.add(5); arbol.add(10); arbol.add(15); arbol.add(20);
        assertIsTrue("Orden 4: 4 elementos accesibles tras split",
                arbol.buscar(5) && arbol.buscar(10) && arbol.buscar(15) && arbol.buscar(20));
    }

    static void test_orden4_volumen_todosAccesibles() {
        ArbolB<Integer> arbol = new ArbolB<>(4);
        for (int i = 1; i <= 20; i++) arbol.add(i * 3);
        boolean todos = true;
        for (int i = 1; i <= 20; i++) if (!arbol.buscar(i * 3)) { todos = false; break; }
        assertIsTrue("Orden 4: 20 elementos todos accesibles", todos);
    }

    // ==================================================================
    // INSERCIÓN — orden 5
    // ==================================================================

    static void test_orden5_volumen_todosAccesibles() {
        ArbolB<Integer> arbol = new ArbolB<>(5);
        for (int i = 1; i <= 30; i++) arbol.add(i);
        boolean todos = true;
        for (int i = 1; i <= 30; i++) if (!arbol.buscar(i)) { todos = false; break; }
        assertIsTrue("Orden 5: 30 elementos todos accesibles", todos);
    }

    static void test_orden5_insercionAscendente() {
        ArbolB<Integer> arbol = new ArbolB<>(5);
        for (int i = 0; i <= 50; i++) arbol.add(i);
        assertIsTrue("Orden 5 ascendente: buscar(0)",  arbol.buscar(0));
        assertIsTrue("Orden 5 ascendente: buscar(25)", arbol.buscar(25));
        assertIsTrue("Orden 5 ascendente: buscar(50)", arbol.buscar(50));
        assertIsFalse("Orden 5 ascendente: buscar(99) es false", arbol.buscar(99));
    }

    static void test_orden5_insercionDescendente() {
        ArbolB<Integer> arbol = new ArbolB<>(5);
        for (int i = 50; i >= 0; i--) arbol.add(i);
        assertIsTrue("Orden 5 descendente: buscar(0)",  arbol.buscar(0));
        assertIsTrue("Orden 5 descendente: buscar(25)", arbol.buscar(25));
        assertIsTrue("Orden 5 descendente: buscar(50)", arbol.buscar(50));
    }

    // ==================================================================
    // ELIMINACIÓN — nodo hoja
    // ==================================================================

    static void test_remove_unicoElemento_arbolVacio() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        arbol.add(42);
        arbol.remove(42);
        assertIsTrue("remove: árbol vacío tras eliminar único elemento", arbol.isEmpty());
    }

    static void test_remove_elementoHoja_yaNoSeEncuentra() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        arbol.add(10); arbol.add(20); arbol.add(30);
        arbol.remove(30);
        assertIsFalse("remove hoja: elemento eliminado ya no se encuentra", arbol.buscar(30));
    }

    static void test_remove_elementoInexistente_sinEfecto() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        arbol.add(10); arbol.add(20);
        arbol.remove(99); // no existe
        assertIsTrue("remove inexistente: resto del árbol intacto",
                arbol.buscar(10) && arbol.buscar(20));
        assertIsFalse("remove inexistente: árbol no vacío", arbol.isEmpty());
    }

    static void test_remove_variosElementos_restantesAccesibles() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        for (int i = 1; i <= 10; i++) arbol.add(i);
        arbol.remove(3); arbol.remove(7); arbol.remove(1);
        assertIsFalse("remove varios: 3 eliminado", arbol.buscar(3));
        assertIsFalse("remove varios: 7 eliminado", arbol.buscar(7));
        assertIsFalse("remove varios: 1 eliminado", arbol.buscar(1));
        assertIsTrue("remove varios: 5 sigue accesible", arbol.buscar(5));
        assertIsTrue("remove varios: 10 sigue accesible", arbol.buscar(10));
    }

    // ==================================================================
    // ELIMINACIÓN — nodo interno
    // ==================================================================

    static void test_remove_nodoInterno_sucesorSustituye() {
        // En árbol orden 3 con varios niveles, eliminar un nodo interno
        // debe sustituirlo por su sucesor y el árbol debe seguir consistente
        ArbolB<Integer> arbol = new ArbolB<>(3);
        int[] vals = {10, 20, 30, 40, 50, 60, 70};
        for (int v : vals) arbol.add(v);
        arbol.remove(20); // nodo que puede quedar en nodo interno
        assertIsFalse("remove interno: 20 ya no se encuentra", arbol.buscar(20));
        assertIsTrue("remove interno: árbol no vacío", !arbol.isEmpty());
    }

    static void test_remove_nodoInterno_restantesAccesibles() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        int[] vals = {10, 20, 30, 40, 50};
        for (int v : vals) arbol.add(v);
        arbol.remove(30);
        boolean restoOk = arbol.buscar(10) && arbol.buscar(20) && arbol.buscar(40) && arbol.buscar(50);
        assertIsTrue("remove interno: resto de elementos accesibles", restoOk);
    }

    // ==================================================================
    // ELIMINACIÓN — rotaciones
    // ==================================================================

    static void test_remove_rotacionIzquierda() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        int[] vals = {10, 20, 30, 40, 50};
        for (int v : vals) arbol.add(v);
        arbol.remove(10);
        assertIsFalse("Rotación izq: 10 eliminado", arbol.buscar(10));
        assertIsTrue("Rotación izq: 20 accesible", arbol.buscar(20));
        assertIsTrue("Rotación izq: 50 accesible", arbol.buscar(50));
    }

    static void test_remove_rotacionDerecha() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        int[] vals = {10, 20, 30, 40, 50};
        for (int v : vals) arbol.add(v);
        arbol.remove(50);
        assertIsFalse("Rotación der: 50 eliminado", arbol.buscar(50));
        assertIsTrue("Rotación der: 10 accesible", arbol.buscar(10));
        assertIsTrue("Rotación der: 40 accesible", arbol.buscar(40));
    }

    // ==================================================================
    // ELIMINACIÓN — fusión
    // ==================================================================

    static void test_remove_fusion_arbolSigueConsistente() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        int[] vals = {5, 10, 15, 20, 25, 30};
        for (int v : vals) arbol.add(v);
        // Eliminamos elementos que fuerzan fusión de nodos
        arbol.remove(5); arbol.remove(10);
        assertIsFalse("Fusión: 5 eliminado",  arbol.buscar(5));
        assertIsFalse("Fusión: 10 eliminado", arbol.buscar(10));
        assertIsTrue("Fusión: 15 accesible",  arbol.buscar(15));
        assertIsTrue("Fusión: 30 accesible",  arbol.buscar(30));
    }

    // ==================================================================
    // CICLO COMPLETO add + remove
    // ==================================================================

    static void test_cicloCompleto_orden3() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        // Insertamos 15 elementos
        for (int i = 1; i <= 15; i++) arbol.add(i);
        // Eliminamos todos los pares
        for (int i = 2; i <= 15; i += 2) arbol.remove(i);
        // Los impares deben seguir presentes
        boolean imparesOk = true;
        for (int i = 1; i <= 15; i += 2) if (!arbol.buscar(i)) { imparesOk = false; break; }
        // Los pares no deben estar
        boolean paresEliminados = true;
        for (int i = 2; i <= 15; i += 2) if (arbol.buscar(i)) { paresEliminados = false; break; }
        assertIsTrue("Ciclo orden 3: impares aún accesibles",  imparesOk);
        assertIsTrue("Ciclo orden 3: pares correctamente eliminados", paresEliminados);
        assertIsFalse("Ciclo orden 3: árbol no vacío", arbol.isEmpty());
    }

    static void test_cicloCompleto_orden5() {
        ArbolB<Integer> arbol = new ArbolB<>(5);
        for (int i = 1; i <= 30; i++) arbol.add(i);
        for (int i = 1; i <= 30; i += 3) arbol.remove(i); // eliminamos 1,4,7,10,...
        boolean eliminadosOk = true;
        for (int i = 1; i <= 30; i += 3) if (arbol.buscar(i)) { eliminadosOk = false; break; }
        assertIsTrue("Ciclo orden 5: múltiplos de 3 eliminados", eliminadosOk);
        assertIsTrue("Ciclo orden 5: 2 accesible",  arbol.buscar(2));
        assertIsTrue("Ciclo orden 5: 29 accesible", arbol.buscar(29));
    }

    // ==================================================================
    // VOLUMEN
    // ==================================================================

    static void test_volumen_orden3_100elementos() {
        ArbolB<Integer> arbol = new ArbolB<>(3);
        for (int i = 1; i <= 100; i++) arbol.add(i);
        boolean todos = true;
        for (int i = 1; i <= 100; i++) if (!arbol.buscar(i)) { todos = false; break; }
        assertIsTrue("Volumen orden 3: 100 elementos todos accesibles", todos);
        assertIsFalse("Volumen orden 3: buscar(101) false", arbol.buscar(101));
    }

    static void test_volumen_orden7_200elementos() {
        ArbolB<Integer> arbol = new ArbolB<>(7);
        for (int i = 1; i <= 200; i++) arbol.add(i * 2); // pares del 2 al 400
        boolean todos = true;
        for (int i = 1; i <= 200; i++) if (!arbol.buscar(i * 2)) { todos = false; break; }
        assertIsTrue("Volumen orden 7: 200 elementos accesibles", todos);
        assertIsFalse("Volumen orden 7: buscar(1) false (impar no insertado)", arbol.buscar(1));
        assertIsFalse("Volumen orden 7: buscar(401) false",                    arbol.buscar(401));
    }

    static void test_volumen_eliminarTodos_arbolVacio() {
        ArbolB<Integer> arbol = new ArbolB<>(4);
        int[] vals = {5, 10, 15, 20, 25, 30, 35, 40};
        for (int v : vals) arbol.add(v);
        for (int v : vals) arbol.remove(v);
        assertIsTrue("Volumen eliminar todos: árbol vacío", arbol.isEmpty());
        assertIsFalse("Volumen eliminar todos: buscar cualquier valor es false", arbol.buscar(10));
    }

    // ==================================================================
    // MOTOR DE ASERCIONES (sin JUnit)
    // ==================================================================

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