package es.universidad.juego.model.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InventarioTest {
    
    private Inventario inventario;
    private Objeto objeto1;
    private Objeto objeto2;
    private Objeto objeto3;
    
    @BeforeEach
    void setUp() {
        inventario = new Inventario(3);
        objeto1 = new Objeto(1, "Espada", TipoObjeto.ARMA, Rareza.COMUN);
        objeto2 = new Objeto(2, "Escudo", TipoObjeto.ARMADURA, Rareza.POCO_COMUN);
        objeto3 = new Objeto(3, "Pocion", TipoObjeto.POCIMA_VIDA, Rareza.RARO);
    }
    
    @Test
    void testConstructor_CapacidadCorrecta() {
        Inventario inv = new Inventario(5);
        
        assertFalse(inv.estaLleno());
        assertEquals(0, inv.getTamaño());
    }
    
    @Test
    void testAddObjeto_Exito() {
        boolean resultado = inventario.addObjeto(objeto1);
        
        assertTrue(resultado);
        assertEquals(1, inventario.getTamaño());
    }
    
    @Test
    void testAddObjeto_Multiple() {
        assertTrue(inventario.addObjeto(objeto1));
        assertTrue(inventario.addObjeto(objeto2));
        assertEquals(2, inventario.getTamaño());
    }
    
    @Test
    void testAddObjeto_Null() {
        boolean resultado = inventario.addObjeto(null);
        
        assertFalse(resultado);
        assertEquals(0, inventario.getTamaño());
    }
    
    @Test
    void testAddObjeto_Lleno() {
        inventario.addObjeto(objeto1);
        inventario.addObjeto(new Objeto(2, "B", TipoObjeto.ARMA, Rareza.COMUN));
        inventario.addObjeto(new Objeto(3, "C", TipoObjeto.ARMA, Rareza.COMUN));
        
        assertTrue(inventario.estaLleno());
        assertFalse(inventario.addObjeto(new Objeto(4, "D", TipoObjeto.ARMA, Rareza.COMUN)));
    }
    
    @Test
    void testRemoveObjeto_Existe() {
        inventario.addObjeto(objeto1);
        inventario.addObjeto(objeto2);
        
        Objeto removed = inventario.removeObjeto(1);
        
        assertNotNull(removed);
        assertEquals(1, removed.getId());
        assertEquals("Espada", removed.getNombre());
        assertEquals(1, inventario.getTamaño());
    }
    
    @Test
    void testRemoveObjeto_PrimerElemento() {
        inventario.addObjeto(objeto1);
        inventario.addObjeto(objeto2);
        
        Objeto removed = inventario.removeObjeto(1);
        
        assertNotNull(removed);
        assertEquals(1, inventario.getTamaño());
        assertNotNull(inventario.getObjeto(2));
    }
    
    @Test
    void testRemoveObjeto_NoExiste() {
        inventario.addObjeto(objeto1);
        
        Objeto removed = inventario.removeObjeto(999);
        
        assertNull(removed);
        assertEquals(1, inventario.getTamaño());
    }
    
    @Test
    void testRemoveObjeto_InventarioVacio() {
        Objeto removed = inventario.removeObjeto(1);
        
        assertNull(removed);
    }
    
    @Test
    void testGetObjeto_Existe() {
        inventario.addObjeto(objeto1);
        inventario.addObjeto(objeto2);
        
        Objeto found = inventario.getObjeto(2);
        
        assertNotNull(found);
        assertEquals("Escudo", found.getNombre());
    }
    
    @Test
    void testGetObjeto_PrimerElemento() {
        inventario.addObjeto(objeto1);
        inventario.addObjeto(objeto2);
        
        Objeto found = inventario.getObjeto(1);
        
        assertNotNull(found);
        assertEquals("Espada", found.getNombre());
    }
    
    @Test
    void testGetObjeto_NoExiste() {
        inventario.addObjeto(objeto1);
        
        Objeto found = inventario.getObjeto(999);
        
        assertNull(found);
    }
    
    @Test
    void testGetObjeto_InventarioVacio() {
        Objeto found = inventario.getObjeto(1);
        
        assertNull(found);
    }
    
    @Test
    void testGetTamaño_CeroInicial() {
        assertEquals(0, inventario.getTamaño());
    }
    
    @Test
    void testGetTamaño_ActualizaCorrecto() {
        inventario.addObjeto(objeto1);
        inventario.addObjeto(objeto2);
        
        assertEquals(2, inventario.getTamaño());
    }
    
    @Test
    void testEstaLleno_FalseInicial() {
        assertFalse(inventario.estaLleno());
    }
    
    @Test
    void testEstaLleno_TrueEnLimite() {
        inventario.addObjeto(objeto1);
        inventario.addObjeto(new Objeto(2, "B", TipoObjeto.ARMA, Rareza.COMUN));
        inventario.addObjeto(new Objeto(3, "C", TipoObjeto.ARMA, Rareza.COMUN));
        
        assertTrue(inventario.estaLleno());
    }
    
    @Test
    void testListaObjetos_Vacio() {
        String lista = inventario.listaObjetos();
        
        assertNotNull(lista);
        assertEquals("", lista.trim());
    }
    
    @Test
    void testListaObjetos_ConObjetos() {
        inventario.addObjeto(objeto1);
        inventario.addObjeto(objeto2);
        
        String lista = inventario.listaObjetos();
        
        assertTrue(lista.contains("Espada"));
        assertTrue(lista.contains("Escudo"));
    }
    
    @Test
    void testVaciar_TodosEliminados() {
        inventario.addObjeto(objeto1);
        inventario.addObjeto(objeto2);
        
        inventario.vaciar();
        
        assertEquals(0, inventario.getTamaño());
        assertFalse(inventario.estaLleno());
    }
    
    @Test
    void testVaciar_InventarioVacio() {
        inventario.vaciar();
        
        assertEquals(0, inventario.getTamaño());
    }
    
    @Test
    void testAddObjeto_DespuesDeVaciar() {
        inventario.addObjeto(objeto1);
        inventario.vaciar();
        
        assertTrue(inventario.addObjeto(objeto2));
        assertEquals(1, inventario.getTamaño());
    }
}