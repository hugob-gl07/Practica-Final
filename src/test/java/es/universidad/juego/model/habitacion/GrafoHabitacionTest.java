package test.java.es.universidad.juego.model.habitacion;

import juego.model.habitacion.GrafoHabitacion;
import juego.model.habitacion.Habitacion;
import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para la clase GrafoHabitacion.
 * Verifica la funcionalidad de adición de habitaciones, conexiones bidireccionales,
 * búsqueda de rutas mediante BFS, cálculo de distancias y conectividad del grafo.
 */
class GrafoHabitacionTest {

    private GrafoHabitacion grafo;
    private Habitacion hab1, hab2, hab3, hab4, hab5;

    /**
     * Configuración inicial para cada test.
     * Crea un grafo vacío y 5 habitaciones de prueba.
     */
    @BeforeEach
    void setUp() {
        grafo = new GrafoHabitacion();
        
        // Crear 5 habitaciones de prueba
        hab1 = crearHabitacion(1, "Sala Principal");
        hab2 = crearHabitacion(2, "Sala Secundaria");
        hab3 = crearHabitacion(3, "Sala Tercera");
        hab4 = crearHabitacion(4, "Sala Cuarta");
        hab5 = crearHabitacion(5, "Sala Quinta");
    }

    /**
     * Método auxiliar para crear una habitación de prueba.
     * @param id identificador de la habitación
     * @param nombre nombre de la habitación
     * @return habitación creada
     */
    private Habitacion crearHabitacion(int id, String nombre) {
        return new Habitacion(id, nombre, 5, 5);
    }

    // ==================== TESTS DE ADICIÓN DE HABITACIONES ====================

    /**
     * Test: Añadir una habitación válida al grafo.
     * Verifica que la habitación se añade correctamente.
     */
    @Test
    void testAgregarHabitacion_Exitoso() {
        grafo.agregarHabitacion(hab1);
        
        Habitacion recuperada = grafo.obtenerHabitacion(hab1.getId());
        assertNotNull(recuperada, "La habitación debe existir en el grafo");
        assertEquals(hab1.getId(), recuperada.getId(), "El ID debe coincidir");
        assertEquals(hab1.getNombre(), recuperada.getNombre(), "El nombre debe coincidir");
    }

    /**
     * Test: Intentar añadir una habitación null.
     * Verifica que no lanza excepción y simplemente no hace nada.
     */
    @Test
    void testAgregarHabitacion_Null() {
        assertDoesNotThrow(() -> grafo.agregarHabitacion(null),
                "No debe lanzar excepción al añadir null");
        
        assertNull(grafo.obtenerHabitacion(-1),
                "No debe haber habitación con ID -1");
        assertNull(grafo.obtenerHabitacion(0),
                "No debe haber habitación con ID 0");
        assertNull(grafo.obtenerHabitacion(1),
                "No debe haber habitación con ID 1");
    }

    /**
     * Test: Añadir múltiples habitaciones al grafo.
     * Verifica que todas se añaden correctamente.
     */
    @Test
    void testAgregarHabitacion_Multiple() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        grafo.agregarHabitacion(hab3);
        
        assertNotNull(grafo.obtenerHabitacion(1), "Habitación 1 debe existir");
        assertNotNull(grafo.obtenerHabitacion(2), "Habitación 2 debe existir");
        assertNotNull(grafo.obtenerHabitacion(3), "Habitación 3 debe existir");
    }

    // ==================== TESTS DE CONEXIONES BIDIRECCIONALES ====================

    /**
     * Test: Conectar dos habitaciones exitosamente.
     * Verifica que la conexión se crea correctamente.
     */
    @Test
    void testConectarHabitaciones_Exitoso() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        
        assertDoesNotThrow(() -> grafo.conectarHabitaciones(hab1, hab2),
                "Debe conectar dos habitaciones válidas");
    }

    /**
     * Test: Intentar conectar con habitación null.
     * Verifica que lanza IllegalArgumentException.
     */
    @Test
    void testConectarHabitaciones_HabitacionNula() {
        grafo.agregarHabitacion(hab1);
        
        assertThrows(IllegalArgumentException.class,
                () -> grafo.conectarHabitaciones(hab1, null),
                "Debe lanzar excepción si habitacion2 es null");
        
        assertThrows(IllegalArgumentException.class,
                () -> grafo.conectarHabitaciones(null, hab1),
                "Debe lanzar excepción si habitacion1 es null");
    }

    /**
     * Test: Intentar conectar habitaciones que no existen en el grafo.
     * Verifica que lanza IllegalArgumentException.
     */
    @Test
    void testConectarHabitaciones_NoExiste() {
        grafo.agregarHabitacion(hab1);
        // hab2 NO se añade al grafo
        
        assertThrows(IllegalArgumentException.class,
                () -> grafo.conectarHabitaciones(hab1, hab2),
                "Debe lanzar excepción si una habitación no existe en el grafo");
    }

    /**
     * Test: Verificar que las conexiones son bidireccionales.
     * Verifica que conectar(h1, h2) permite ir en ambas direcciones.
     */
    @Test
    void testConectarHabitaciones_Bidireccional() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        grafo.conectarHabitaciones(hab1, hab2);
        
        // Debe haber ruta de hab1 a hab2
        ListaSimplementeEnlazada<Habitacion> ruta1a2 = grafo.buscarRuta(hab1, hab2);
        assertNotNull(ruta1a2, "Debe haber ruta de hab1 a hab2");
        assertEquals(2, ruta1a2.getSize(), "Ruta debe tener 2 habitaciones");
        
        // Debe haber ruta de hab2 a hab1 (bidireccional)
        ListaSimplementeEnlazada<Habitacion> ruta2a1 = grafo.buscarRuta(hab2, hab1);
        assertNotNull(ruta2a1, "Debe haber ruta de hab2 a hab1 (bidireccional)");
        assertEquals(2, ruta2a1.getSize(), "Ruta debe tener 2 habitaciones");
    }

    // ==================== TESTS DE BÚSQUEDA DE HABITACIONES ====================

    /**
     * Test: Obtener una habitación por su ID.
     * Verifica que se recupera correctamente.
     */
    @Test
    void testObtenerHabitacion_PorId() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        
        Habitacion recuperada = grafo.obtenerHabitacion(1);
        assertNotNull(recuperada, "Debe recuperar la habitación");
        assertEquals("Sala Principal", recuperada.getNombre(), "El nombre debe coincidir");
    }

    /**
     * Test: Intentar obtener una habitación con ID que no existe.
     * Verifica que retorna null.
     */
    @Test
    void testObtenerHabitacion_IdNoExiste() {
        grafo.agregarHabitacion(hab1);
        
        Habitacion recuperada = grafo.obtenerHabitacion(999);
        assertNull(recuperada, "Debe retornar null si el ID no existe");
    }

    /**
     * Test: Obtener las habitaciones vecinas (conectadas directamente).
     * Verifica que retorna la lista correcta de vecinos.
     */
    @Test
    void testObtenerHabitaciones_Vecinas() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        grafo.agregarHabitacion(hab3);
        grafo.conectarHabitaciones(hab1, hab2);
        grafo.conectarHabitaciones(hab1, hab3);
        
        ListaSimplementeEnlazada<Habitacion> vecinas = grafo.obtenerHabitaciones(hab1);
        assertNotNull(vecinas, "Debe retornar lista de vecinas");
        assertEquals(2, vecinas.getSize(), "Debe haber 2 habitaciones vecinas");
    }

    /**
     * Test: Obtener vecinas de una habitación sin conexiones.
     * Verifica que retorna lista vacía.
     */
    @Test
    void testObtenerHabitaciones_SinConexiones() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        // No conectamos hab1 con hab2
        
        ListaSimplementeEnlazada<Habitacion> vecinas = grafo.obtenerHabitaciones(hab1);
        assertNotNull(vecinas, "Debe retornar lista (vacía)");
        assertEquals(0, vecinas.getSize(), "Debe haber 0 habitaciones vecinas");
    }

    /**
     * Test: Obtener vecinas de una habitación null.
     * Verifica que lanza IllegalArgumentException.
     */
    @Test
    void testObtenerHabitaciones_Null() {
        assertThrows(IllegalArgumentException.class,
                () -> grafo.obtenerHabitaciones(null),
                "Debe lanzar excepción si la habitación es null");
    }

    // ==================== TESTS DE BÚSQUEDA DE RUTAS (BFS) ====================

    /**
     * Test: Buscar ruta directa entre dos habitaciones conectadas.
     * Verifica que retorna la ruta correcta.
     */
    @Test
    void testBuscarRuta_Directa() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        grafo.conectarHabitaciones(hab1, hab2);
        
        ListaSimplementeEnlazada<Habitacion> ruta = grafo.buscarRuta(hab1, hab2);
        assertNotNull(ruta, "Debe encontrar ruta");
        assertEquals(2, ruta.getSize(), "Ruta debe tener 2 habitaciones");
        assertEquals(hab1.getId(), ruta.getAt(0).getId(), "Primera debe ser hab1");
        assertEquals(hab2.getId(), ruta.getAt(1).getId(), "Segunda debe ser hab2");
    }

    /**
     * Test: Buscar ruta indirecta a través de múltiples habitaciones.
     * Verifica que el BFS encuentra el camino más corto.
     */
    @Test
    void testBuscarRuta_Indirecta() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        grafo.agregarHabitacion(hab3);
        grafo.conectarHabitaciones(hab1, hab2);
        grafo.conectarHabitaciones(hab2, hab3);
        
        ListaSimplementeEnlazada<Habitacion> ruta = grafo.buscarRuta(hab1, hab3);
        assertNotNull(ruta, "Debe encontrar ruta indirecta");
        assertEquals(3, ruta.getSize(), "Ruta debe tener 3 habitaciones");
        assertEquals(hab1.getId(), ruta.getAt(0).getId(), "Primera debe ser hab1");
        assertEquals(hab2.getId(), ruta.getAt(1).getId(), "Segunda debe ser hab2");
        assertEquals(hab3.getId(), ruta.getAt(2).getId(), "Tercera debe ser hab3");
    }

    /**
     * Test: Buscar ruta cuando no existe conexión.
     * Verifica que retorna null.
     */
    @Test
    void testBuscarRuta_NoExiste() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        // No conectamos hab1 con hab2
        
        ListaSimplementeEnlazada<Habitacion> ruta = grafo.buscarRuta(hab1, hab2);
        assertNull(ruta, "Debe retornar null si no hay ruta");
    }

    /**
     * Test: Buscar ruta de una habitación a sí misma.
     * Verifica que retorna lista con esa habitación.
     */
    @Test
    void testBuscarRuta_MismaHabitacion() {
        grafo.agregarHabitacion(hab1);
        
        ListaSimplementeEnlazada<Habitacion> ruta = grafo.buscarRuta(hab1, hab1);
        assertNotNull(ruta, "Debe retornar lista (no null)");
        assertEquals(1, ruta.getSize(), "Ruta debe tener 1 habitación");
        assertEquals(hab1.getId(), ruta.getAt(0).getId(), "Debe ser hab1");
    }

    /**
     * Test: Buscar ruta con habitación null.
     * Verifica que maneja el caso correctamente.
     */
    @Test
    void testBuscarRuta_Null() {
        grafo.agregarHabitacion(hab1);
        
        assertThrows(IllegalArgumentException.class,
                () -> grafo.buscarRuta(null, hab1),
                "Debe lanzar IllegalArgumentException si habitacioninicio es null");
        
        assertThrows(IllegalArgumentException.class,
                () -> grafo.buscarRuta(hab1, null),
                "Debe lanzar IllegalArgumentException si habitacionfinal es null");
    }

    // ==================== TESTS DE DISTANCIAS ====================

    /**
     * Test: Calcular distancia directa entre dos habitaciones.
     * Verifica que retorna 1 (una arista).
     */
    @Test
    void testCalcularDistancia_Directa() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        grafo.conectarHabitaciones(hab1, hab2);
        
        int distancia = grafo.calcularDistancia(hab1, hab2);
        assertEquals(1, distancia, "Distancia directa debe ser 1");
    }

    /**
     * Test: Calcular distancia indirecta a través de múltiples habitaciones.
     * Verifica que retorna el número correcto de aristas.
     */
    @Test
    void testCalcularDistancia_Indirecta() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        grafo.agregarHabitacion(hab3);
        grafo.conectarHabitaciones(hab1, hab2);
        grafo.conectarHabitaciones(hab2, hab3);
        
        int distancia = grafo.calcularDistancia(hab1, hab3);
        assertEquals(2, distancia, "Distancia indirecta debe ser 2");
    }

    /**
     * Test: Calcular distancia cuando no existe ruta.
     * Verifica que retorna -1.
     */
    @Test
    void testCalcularDistancia_NoExiste() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        // No conectamos
        
        int distancia = grafo.calcularDistancia(hab1, hab2);
        assertEquals(-1, distancia, "Distancia debe ser -1 si no hay ruta");
    }

    /**
     * Test: Calcular distancia de una habitación a sí misma.
     * Verifica que retorna 0.
     */
    @Test
    void testCalcularDistancia_MismaHabitacion() {
        grafo.agregarHabitacion(hab1);
        
        int distancia = grafo.calcularDistancia(hab1, hab1);
        assertEquals(0, distancia, "Distancia a sí misma debe ser 0");
    }

    // ==================== TESTS DE CONECTIVIDAD ====================

    /**
     * Test: Verificar que el grafo es conexo cuando todas las habitaciones están conectadas.
     * Verifica que esConexo() retorna true.
     */
    @Test
    void testEsConexo_Verdadero() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        grafo.agregarHabitacion(hab3);
        grafo.conectarHabitaciones(hab1, hab2);
        grafo.conectarHabitaciones(hab2, hab3);
        
        assertTrue(grafo.esConexo(), "El grafo debe ser conexo");
    }

    /**
     * Test: Verificar que el grafo NO es conexo cuando hay habitaciones aisladas.
     * Verifica que esConexo() retorna false.
     */
    @Test
    void testEsConexo_Falso() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        grafo.agregarHabitacion(hab3);
        grafo.conectarHabitaciones(hab1, hab2);
        // hab3 está aislada
        
        assertFalse(grafo.esConexo(), "El grafo NO debe ser conexo");
    }

    /**
     * Test: Verificar conectividad de un grafo vacío.
     * Verifica el comportamiento con grafo sin habitaciones.
     */
    @Test
    void testEsConexo_Vacio() {
        assertDoesNotThrow(() -> grafo.esConexo(),
                "No debe lanzar excepción con grafo vacío");
        assertTrue(grafo.esConexo(),
                "Un grafo vacío debe considerarse conexo");
    }

    // ==================== TESTS DE CASOS ESPECIALES ====================

    /**
     * Test: Verificar que el toString() no lanza excepción.
     * Verifica que la representación en texto funciona.
     */
    @Test
    void testToString_NoLanzaExcepcion() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        
        assertDoesNotThrow(() -> grafo.toString(),
                "toString() no debe lanzar excepción");
        
        String resultado = grafo.toString();
        assertNotNull(resultado, "toString() debe retornar un valor");
        assertFalse(resultado.isEmpty(), "toString() no debe estar vacío");
    }

    /**
     * Test: Verificar que el grafo maneja múltiples conexiones correctamente.
     * Verifica que una habitación puede conectarse con varias otras.
     */
    @Test
    void testMultiplesConexiones() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        grafo.agregarHabitacion(hab3);
        grafo.agregarHabitacion(hab4);
        
        // hab1 conectada con hab2, hab3 y hab4
        grafo.conectarHabitaciones(hab1, hab2);
        grafo.conectarHabitaciones(hab1, hab3);
        grafo.conectarHabitaciones(hab1, hab4);
        
        ListaSimplementeEnlazada<Habitacion> vecinas = grafo.obtenerHabitaciones(hab1);
        assertEquals(3, vecinas.getSize(), "hab1 debe tener 3 vecinas");
    }

    /**
     * Test: Verificar que el BFS encuentra el camino más corto.
     * Verifica que no toma rutas más largas cuando hay alternativas.
     */
    @Test
    void testBuscarRuta_CaminoMasCorto() {
        grafo.agregarHabitacion(hab1);
        grafo.agregarHabitacion(hab2);
        grafo.agregarHabitacion(hab3);
        grafo.agregarHabitacion(hab4);
        
        // Crear dos rutas: una corta (1->2->4) y una larga (1->3->4)
        grafo.conectarHabitaciones(hab1, hab2);
        grafo.conectarHabitaciones(hab2, hab4);
        grafo.conectarHabitaciones(hab1, hab3);
        grafo.conectarHabitaciones(hab3, hab4);
        
        ListaSimplementeEnlazada<Habitacion> ruta = grafo.buscarRuta(hab1, hab4);
        assertEquals(3, ruta.getSize(), "Debe tomar la ruta más corta (3 nodos)");
    }
}
