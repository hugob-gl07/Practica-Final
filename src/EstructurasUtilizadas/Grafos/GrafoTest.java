package EstructurasUtilizadas.Grafos;
import EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;

public class GrafoTest {

    private static int totalTests  = 0;
    private static int totalPassed = 0;
    private static int totalFailed = 0;

    public static void main(String[] args) {

        printHeader("AGREGAR NODO");
        test_agregarNodo_nuevo();
        test_agregarNodo_noDuplicados();
        test_agregarNodo_variosNodos();
        test_agregarNodo_contadorIncrementa();

        printHeader("BUSCAR ENTRADA");
        test_buscarEntrada_existente();
        test_buscarEntrada_inexistente();
        test_buscarEntrada_nombreCorrecto();

        printHeader("AGREGAR ARISTA");
        test_agregarArista_creaArista();
        test_agregarArista_creaNodosAutomaticamente();
        test_agregarArista_variosDestinos();
        test_agregarArista_origenYDestinoCorrectos();
        test_agregarArista_etiquetaCorrecta();
        test_agregarArista_destinoNoTieneAristaSaliente();
        test_agregarArista_nodosYaExistentes();

        printHeader("CAMINO MINIMO — casos normales");
        test_caminoMinimo_caminoDirecto();
        test_caminoMinimo_caminoConIntermedios();
        test_caminoMinimo_caminoMasCorto();

        printHeader("CAMINO MINIMO — casos especiales");
        test_caminoMinimo_origenIgualDestino();
        test_caminoMinimo_sinCamino();
        test_caminoMinimo_nodoOrigenInexistente();
        test_caminoMinimo_nodoDestinoInexistente();

        printHeader("ES DISJUNTO");
        test_esDisjunto_grafoVacio();
        test_esDisjunto_unSoloNodo();
        test_esDisjunto_grafoConexo();
        test_esDisjunto_grafoDisjunto();
        test_esDisjunto_conexoPorAristasEntrantes();

        printHeader("GET VALOR");
        test_getValor_relacionExistente();
        test_getValor_relacionInexistente();
        test_getValor_nodoInexistente();
        test_getValor_variosVecinos();

        printHeader("BUSCAR POR RELACION Y VALOR");
        test_buscarPorRelacionYValor_unResultado();
        test_buscarPorRelacionYValor_variosResultados();
        test_buscarPorRelacionYValor_sinResultados();
        test_buscarPorRelacionYValor_relacionInexistente();

        printHeader("LISTAR VALORES POR RELACION");
        test_listarValoresPorRelacion_unValor();
        test_listarValoresPorRelacion_variosValores();
        test_listarValoresPorRelacion_relacionInexistente();

        printHeader("GET TIPOS DE NODOS");
        test_getTiposDeNodos_unTipo();
        test_getTiposDeNodos_variosTipos();
        test_getTiposDeNodos_sinDuplicados();
        test_getTiposDeNodos_nodoSinPrefijo();

        printResumen();
    }

    // ==================================================================
    // AGREGAR NODO
    // ==================================================================

    static void test_agregarNodo_nuevo() {
        Grafo g = new Grafo(0);
        g.agregarNodo("persona:Einstein");
        assertIsTrue("agregarNodo: el nodo puede encontrarse tras añadirlo",
                g.buscarEntrada("persona:Einstein") != null);
    }

    static void test_agregarNodo_noDuplicados() {
        Grafo g = new Grafo(0);
        g.agregarNodo("persona:Einstein");
        int idOriginal = g.buscarEntrada("persona:Einstein").getNodo().getId();
        g.agregarNodo("persona:Einstein");
        int idDespues = g.buscarEntrada("persona:Einstein").getNodo().getId();
        assertEqual("agregarNodo: nodo duplicado no cambia su id", idOriginal, idDespues);
    }

    static void test_agregarNodo_variosNodos() {
        Grafo g = new Grafo(0);
        g.agregarNodo("persona:Einstein");
        g.agregarNodo("persona:Curie");
        g.agregarNodo("lugar:Ulm");
        assertIsTrue("agregarNodo varios: Einstein encontrado", g.buscarEntrada("persona:Einstein") != null);
        assertIsTrue("agregarNodo varios: Curie encontrado",    g.buscarEntrada("persona:Curie")    != null);
        assertIsTrue("agregarNodo varios: Ulm encontrado",      g.buscarEntrada("lugar:Ulm")        != null);
    }

    static void test_agregarNodo_contadorIncrementa() {
        Grafo g = new Grafo(0);
        g.agregarNodo("A");
        g.agregarNodo("B");
        g.agregarNodo("C");
        assertEqual("agregarNodo: id de A es 0", 0, g.buscarEntrada("A").getNodo().getId());
        assertEqual("agregarNodo: id de B es 1", 1, g.buscarEntrada("B").getNodo().getId());
        assertEqual("agregarNodo: id de C es 2", 2, g.buscarEntrada("C").getNodo().getId());
    }

    // ==================================================================
    // BUSCAR ENTRADA
    // ==================================================================

    static void test_buscarEntrada_existente() {
        Grafo g = new Grafo(0);
        g.agregarNodo("persona:Einstein");
        assertIsTrue("buscarEntrada: devuelve entrada no nula",
                g.buscarEntrada("persona:Einstein") != null);
    }

    static void test_buscarEntrada_inexistente() {
        Grafo g = new Grafo(0);
        g.agregarNodo("persona:Einstein");
        assertIsTrue("buscarEntrada: nombre inexistente devuelve null",
                g.buscarEntrada("persona:Curie") == null);
    }

    static void test_buscarEntrada_nombreCorrecto() {
        Grafo g = new Grafo(0);
        g.agregarNodo("persona:Einstein");
        EntradaAdyacencia e = g.buscarEntrada("persona:Einstein");
        assertEqualString("buscarEntrada: el nodo tiene el nombre correcto",
                "persona:Einstein", e.getNodo().getNombre());
    }

    // ==================================================================
    // AGREGAR ARISTA
    // ==================================================================

    static void test_agregarArista_creaArista() {
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm", "nace_en");
        assertEqual("agregarArista: el origen tiene 1 arista saliente", 1,
                g.buscarEntrada("persona:Einstein").getAristas().getSize());
    }

    static void test_agregarArista_creaNodosAutomaticamente() {
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm", "nace_en");
        assertIsTrue("agregarArista: crea nodo origen automaticamente",  g.buscarEntrada("persona:Einstein") != null);
        assertIsTrue("agregarArista: crea nodo destino automaticamente", g.buscarEntrada("lugar:Ulm")        != null);
    }

    static void test_agregarArista_variosDestinos() {
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm",    "nace_en");
        g.agregarArista("persona:Einstein", "premio:Nobel", "gana");
        g.agregarArista("persona:Einstein", "campo:Fisica", "trabaja_en");
        assertEqual("agregarArista: Einstein tiene 3 aristas salientes", 3,
                g.buscarEntrada("persona:Einstein").getAristas().getSize());
    }

    static void test_agregarArista_origenYDestinoCorrectos() {
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm", "nace_en");
        Arista a = g.buscarEntrada("persona:Einstein").getAristas().getAt(0);
        assertEqualString("agregarArista: origen correcto",  "persona:Einstein", a.getOrigen().getNombre());
        assertEqualString("agregarArista: destino correcto", "lugar:Ulm",        a.getDestino().getNombre());
    }

    static void test_agregarArista_etiquetaCorrecta() {
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm", "nace_en");
        Arista a = g.buscarEntrada("persona:Einstein").getAristas().getAt(0);
        assertEqualString("agregarArista: etiqueta correcta", "nace_en", a.getEtiqueta());
    }

    static void test_agregarArista_destinoNoTieneAristaSaliente() {
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm", "nace_en");
        assertEqual("agregarArista: destino no tiene aristas salientes", 0,
                g.buscarEntrada("lugar:Ulm").getAristas().getSize());
    }

    static void test_agregarArista_nodosYaExistentes() {
        Grafo g = new Grafo(0);
        g.agregarNodo("persona:Einstein");
        g.agregarNodo("lugar:Ulm");
        int idEinstein = g.buscarEntrada("persona:Einstein").getNodo().getId();
        g.agregarArista("persona:Einstein", "lugar:Ulm", "nace_en");
        assertEqual("agregarArista: nodos preexistentes conservan su id", idEinstein,
                g.buscarEntrada("persona:Einstein").getNodo().getId());
    }

    // ==================================================================
    // CAMINO MINIMO — casos normales
    // ==================================================================

    static void test_caminoMinimo_caminoDirecto() {
        Grafo g = new Grafo(0);
        g.agregarArista("A", "B", "r");
        ListaSimplementeEnlazada<Nodo> camino = g.caminoMínimo("A", "B");
        assertIsTrue("caminoMinimo directo: devuelve camino no nulo", camino != null);
        assertEqual("caminoMinimo directo: tiene 2 nodos", 2, camino.getSize());
        assertEqualString("caminoMinimo directo: primer nodo es A", "A", camino.getAt(0).getNombre());
        assertEqualString("caminoMinimo directo: segundo nodo es B", "B", camino.getAt(1).getNombre());
    }

    static void test_caminoMinimo_caminoConIntermedios() {
        Grafo g = new Grafo(0);
        g.agregarArista("A", "B", "r1");
        g.agregarArista("B", "C", "r2");
        g.agregarArista("C", "D", "r3");
        ListaSimplementeEnlazada<Nodo> camino = g.caminoMínimo("A", "D");
        assertIsTrue("caminoMinimo intermedios: devuelve camino no nulo", camino != null);
        assertEqual("caminoMinimo intermedios: tiene 4 nodos", 4, camino.getSize());
        assertEqualString("caminoMinimo intermedios: empieza en A", "A", camino.getAt(0).getNombre());
        assertEqualString("caminoMinimo intermedios: termina en D", "D", camino.getAt(3).getNombre());
    }

    static void test_caminoMinimo_caminoMasCorto() {
        // A->B->D (2 saltos) y A->C->D (2 saltos) son mas cortos que A->B->C->D (3 saltos)
        Grafo g = new Grafo(0);
        g.agregarArista("A", "B", "r1");
        g.agregarArista("A", "C", "r2");
        g.agregarArista("B", "D", "r3");
        g.agregarArista("C", "D", "r4");
        g.agregarArista("B", "C", "r5");
        ListaSimplementeEnlazada<Nodo> camino = g.caminoMínimo("A", "D");
        assertIsTrue("caminoMinimo mas corto: devuelve camino no nulo", camino != null);
        assertEqual("caminoMinimo mas corto: tiene 3 nodos (2 saltos)", 3, camino.getSize());
        assertEqualString("caminoMinimo mas corto: empieza en A", "A", camino.getAt(0).getNombre());
        assertEqualString("caminoMinimo mas corto: termina en D", "D", camino.getAt(2).getNombre());
    }

    // ==================================================================
    // CAMINO MINIMO — casos especiales
    // ==================================================================

    static void test_caminoMinimo_origenIgualDestino() {
        // Origen = destino: debe devolver una lista con un solo nodo
        Grafo g = new Grafo(0);
        g.agregarNodo("A");
        ListaSimplementeEnlazada<Nodo> camino = g.caminoMínimo("A", "A");
        assertIsTrue("caminoMinimo origen=destino: devuelve camino no nulo", camino != null);
        assertEqual("caminoMinimo origen=destino: tiene 1 nodo", 1, camino.getSize());
        assertEqualString("caminoMinimo origen=destino: el nodo es A", "A", camino.getAt(0).getNombre());
    }

    static void test_caminoMinimo_sinCamino() {
        // A->B y C->D son dos componentes separadas: no hay camino de A a D
        Grafo g = new Grafo(0);
        g.agregarArista("A", "B", "r1");
        g.agregarArista("C", "D", "r2");
        ListaSimplementeEnlazada<Nodo> camino = g.caminoMínimo("A", "D");
        assertIsTrue("caminoMinimo sin camino: devuelve null", camino == null);
    }

    static void test_caminoMinimo_nodoOrigenInexistente() {
        Grafo g = new Grafo(0);
        g.agregarNodo("B");
        ListaSimplementeEnlazada<Nodo> camino = g.caminoMínimo("A", "B");
        assertIsTrue("caminoMinimo: origen inexistente devuelve null", camino == null);
    }

    static void test_caminoMinimo_nodoDestinoInexistente() {
        Grafo g = new Grafo(0);
        g.agregarNodo("A");
        ListaSimplementeEnlazada<Nodo> camino = g.caminoMínimo("A", "B");
        assertIsTrue("caminoMinimo: destino inexistente devuelve null", camino == null);
    }

    // ==================================================================
    // ES DISJUNTO
    // ==================================================================

    static void test_esDisjunto_grafoVacio() {
        Grafo g = new Grafo(0);
        assertIsTrue("esDisjunto: grafo vacio devuelve false", !g.esDisjunto());
    }

    static void test_esDisjunto_unSoloNodo() {
        Grafo g = new Grafo(0);
        g.agregarNodo("A");
        assertIsTrue("esDisjunto: un solo nodo no es disjunto", !g.esDisjunto());
    }

    static void test_esDisjunto_grafoConexo() {
        Grafo g = new Grafo(0);
        g.agregarArista("A", "B", "r1");
        g.agregarArista("B", "C", "r2");
        g.agregarArista("C", "D", "r3");
        assertIsTrue("esDisjunto: grafo totalmente conexo devuelve false", !g.esDisjunto());
    }

    static void test_esDisjunto_grafoDisjunto() {
        // A->B y C->D son dos componentes separadas
        Grafo g = new Grafo(0);
        g.agregarArista("A", "B", "r1");
        g.agregarArista("C", "D", "r2");
        assertIsTrue("esDisjunto: grafo con dos componentes devuelve true", g.esDisjunto());
    }

    static void test_esDisjunto_conexoPorAristasEntrantes() {
        // A->B y C->B: sin BFS bidireccional C no seria alcanzable desde A
        Grafo g = new Grafo(0);
        g.agregarArista("A", "B", "r1");
        g.agregarArista("C", "B", "r2");
        assertIsTrue("esDisjunto: conexo por aristas entrantes devuelve false", !g.esDisjunto());
    }

    // ==================================================================
    // GET VALOR
    // ==================================================================

    static void test_getValor_relacionExistente() {
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm", "nace_en");
        assertEqualString("getValor: devuelve el nodo destino correcto",
                "lugar:Ulm", g.getValor("persona:Einstein", "nace_en"));
    }

    static void test_getValor_relacionInexistente() {
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm", "nace_en");
        assertIsTrue("getValor: relacion inexistente devuelve null",
                g.getValor("persona:Einstein", "trabaja_en") == null);
    }

    static void test_getValor_nodoInexistente() {
        Grafo g = new Grafo(0);
        assertIsTrue("getValor: nodo inexistente devuelve null",
                g.getValor("persona:Curie", "nace_en") == null);
    }

    static void test_getValor_variosVecinos() {
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm",    "nace_en");
        g.agregarArista("persona:Einstein", "premio:Nobel", "gana");
        assertEqualString("getValor: con varios vecinos devuelve nace_en correcto",
                "lugar:Ulm", g.getValor("persona:Einstein", "nace_en"));
        assertEqualString("getValor: con varios vecinos devuelve gana correcto",
                "premio:Nobel", g.getValor("persona:Einstein", "gana"));
    }

    // ==================================================================
    // BUSCAR POR RELACION Y VALOR
    // ==================================================================

    static void test_buscarPorRelacionYValor_unResultado() {
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm", "nace_en");
        ListaSimplementeEnlazada<String> resultado = g.buscarPorRelacionYValor("nace_en", "lugar:Ulm");
        assertEqual("buscarPorRelacionYValor: devuelve 1 resultado", 1, resultado.getSize());
        assertEqualString("buscarPorRelacionYValor: el resultado es Einstein",
                "persona:Einstein", resultado.getAt(0));
    }

    static void test_buscarPorRelacionYValor_variosResultados() {
        // Einstein y Lorentz nacen en Ulm: los dos deben aparecer
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm",      "nace_en");
        g.agregarArista("persona:Lorentz",  "lugar:Ulm",      "nace_en");
        g.agregarArista("persona:Curie",    "lugar:Varsovia",  "nace_en");
        ListaSimplementeEnlazada<String> resultado = g.buscarPorRelacionYValor("nace_en", "lugar:Ulm");
        assertEqual("buscarPorRelacionYValor varios: devuelve 2 resultados", 2, resultado.getSize());
    }

    static void test_buscarPorRelacionYValor_sinResultados() {
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm", "nace_en");
        ListaSimplementeEnlazada<String> resultado = g.buscarPorRelacionYValor("nace_en", "lugar:Paris");
        assertEqual("buscarPorRelacionYValor: sin resultados devuelve lista vacia", 0, resultado.getSize());
    }

    static void test_buscarPorRelacionYValor_relacionInexistente() {
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm", "nace_en");
        ListaSimplementeEnlazada<String> resultado = g.buscarPorRelacionYValor("trabaja_en", "lugar:Ulm");
        assertEqual("buscarPorRelacionYValor: relacion inexistente devuelve lista vacia", 0, resultado.getSize());
    }

    // ==================================================================
    // LISTAR VALORES POR RELACION
    // ==================================================================

    static void test_listarValoresPorRelacion_unValor() {
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm", "nace_en");
        ListaSimplementeEnlazada<String> resultado = g.listarValoresPorRelación("nace_en");
        assertEqual("listarValoresPorRelacion: devuelve 1 valor", 1, resultado.getSize());
        assertEqualString("listarValoresPorRelacion: el valor es Ulm", "lugar:Ulm", resultado.getAt(0));
    }

    static void test_listarValoresPorRelacion_variosValores() {
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm",         "nace_en");
        g.agregarArista("persona:Curie",    "lugar:Varsovia",    "nace_en");
        g.agregarArista("persona:Newton",   "lugar:Woolsthorpe", "nace_en");
        ListaSimplementeEnlazada<String> resultado = g.listarValoresPorRelación("nace_en");
        assertEqual("listarValoresPorRelacion: devuelve 3 valores", 3, resultado.getSize());
    }

    static void test_listarValoresPorRelacion_relacionInexistente() {
        Grafo g = new Grafo(0);
        g.agregarArista("persona:Einstein", "lugar:Ulm", "nace_en");
        ListaSimplementeEnlazada<String> resultado = g.listarValoresPorRelación("trabaja_en");
        assertEqual("listarValoresPorRelacion: relacion inexistente devuelve lista vacia", 0, resultado.getSize());
    }

    // ==================================================================
    // GET TIPOS DE NODOS
    // ==================================================================

    static void test_getTiposDeNodos_unTipo() {
        Grafo g = new Grafo(0);
        g.agregarNodo("persona:Einstein");
        g.agregarNodo("persona:Curie");
        ListaSimplementeEnlazada<String> tipos = g.getTiposdeNodos();
        assertEqual("getTiposdeNodos: un solo tipo devuelve 1", 1, tipos.getSize());
        assertEqualString("getTiposdeNodos: el tipo es persona", "persona", tipos.getAt(0));
    }

    static void test_getTiposDeNodos_variosTipos() {
        Grafo g = new Grafo(0);
        g.agregarNodo("persona:Einstein");
        g.agregarNodo("lugar:Ulm");
        g.agregarNodo("premio:Nobel");
        ListaSimplementeEnlazada<String> tipos = g.getTiposdeNodos();
        assertEqual("getTiposdeNodos: tres tipos distintos", 3, tipos.getSize());
    }

    static void test_getTiposDeNodos_sinDuplicados() {
        Grafo g = new Grafo(0);
        g.agregarNodo("persona:Einstein");
        g.agregarNodo("persona:Curie");
        g.agregarNodo("persona:Newton");
        g.agregarNodo("lugar:Ulm");
        ListaSimplementeEnlazada<String> tipos = g.getTiposdeNodos();
        assertEqual("getTiposdeNodos: sin duplicados de tipo", 2, tipos.getSize());
    }

    static void test_getTiposDeNodos_nodoSinPrefijo() {
        // Un nodo sin : devuelve su nombre completo como prefijo
        Grafo g = new Grafo(0);
        g.agregarNodo("Einstein");
        ListaSimplementeEnlazada<String> tipos = g.getTiposdeNodos();
        assertEqual("getTiposdeNodos: nodo sin prefijo cuenta como tipo", 1, tipos.getSize());
        assertEqualString("getTiposdeNodos: nodo sin prefijo devuelve nombre completo",
                "Einstein", tipos.getAt(0));
    }

    // ==================================================================
    // MOTOR DE ASERCIONES (sin JUnit)
    // ==================================================================

    static void assertEqual(String nombre, int esperado, int actual) {
        totalTests++;
        if(esperado == actual) registrarOk(nombre);
        else registrarFallo(nombre + " -> esperado: " + esperado + ", obtenido: " + actual);
    }

    static void assertEqualString(String nombre, String esperado, String actual) {
        totalTests++;
        if(esperado.equals(actual)) registrarOk(nombre);
        else registrarFallo(nombre + " -> esperado: '" + esperado + "', obtenido: '" + actual + "'");
    }

    static void assertIsTrue(String nombre, boolean condicion) {
        totalTests++;
        if(condicion) registrarOk(nombre);
        else          registrarFallo(nombre + " -> esperado: true, obtenido: false");
    }

    static void registrarOk(String n)    { totalPassed++; System.out.println("  [OK]   " + n); }
    static void registrarFallo(String n) { totalFailed++; System.out.println("  [FAIL] " + n); }

    static void printHeader(String titulo) {
        System.out.println("\n==========================================");
        System.out.println("  " + titulo);
        System.out.println("==========================================");
    }

    static void printResumen() {
        System.out.println("\n==========================================");
        System.out.println("  RESUMEN FINAL");
        System.out.println("==========================================");
        System.out.println("  Total  : " + totalTests);
        System.out.println("  Passed : " + totalPassed + " OK");
        System.out.println("  Failed : " + totalFailed + (totalFailed > 0 ? " FAIL" : ""));
        System.out.println("==========================================");
        if(totalFailed == 0) System.out.println("  Todos los tests han pasado!");
        else                 System.out.println("  Revisa los tests marcados con [FAIL].");
        System.out.println("==========================================\n");
    }
}
