package juego.model.entidades;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ObjetoTest {
    
    @Test
    void testConstructor_ValoresValidos() {
        Objeto obj = new Objeto(1, "Espada", TipoObjeto.ARMA, Rareza.RARO);
        
        assertEquals(1, obj.getId());
        assertEquals("Espada", obj.getNombre());
        assertEquals(TipoObjeto.ARMA, obj.getTipo());
        assertEquals(Rareza.RARO, obj.getRareza());
    }
    
    @Test
    void testGetId_RetornaCorrecto() {
        Objeto obj = new Objeto(42, "Daga", TipoObjeto.ARMA, Rareza.COMUN);
        
        assertEquals(42, obj.getId());
    }
    
    @Test
    void testGetNombre_RetornaCorrecto() {
        Objeto obj = new Objeto(1, "Escudo", TipoObjeto.ARMADURA, Rareza.POCO_COMUN);
        
        assertEquals("Escudo", obj.getNombre());
    }
    
    @Test
    void testGetTipo_RetornaEnum() {
        Objeto obj = new Objeto(1, "Llave", TipoObjeto.LLAVE, Rareza.RARO);
        
        assertEquals(TipoObjeto.LLAVE, obj.getTipo());
    }
    
    @Test
    void testGetRareza_RetornaEnum() {
        Objeto obj = new Objeto(1, "Anillo", TipoObjeto.CONSUMIBLE, Rareza.LEGENDARIO);
        
        assertEquals(Rareza.LEGENDARIO, obj.getRareza());
    }
    
    @Test
    void testAddEstadistica_UnaEstadistica() {
        Objeto obj = new Objeto(1, "Arma", TipoObjeto.ARMA, Rareza.COMUN);
        obj.addEstadistica("ataque", 10);
        
        assertEquals(10, obj.getValorEstadisticas("ataque"));
    }
    
    @Test
    void testAddEstadistica_MultiplesEstadisticas() {
        Objeto obj = new Objeto(1, "Arma", TipoObjeto.ARMA, Rareza.EPICO);
        obj.addEstadistica("ataque", 15);
        obj.addEstadistica("critico", 5);
        obj.addEstadistica("velocidad", 3);
        
        assertEquals(15, obj.getValorEstadisticas("ataque"));
        assertEquals(5, obj.getValorEstadisticas("critico"));
        assertEquals(3, obj.getValorEstadisticas("velocidad"));
    }
    
    @Test
    void testGetValorEstadisticas_NoExiste_RetornaCero() {
        Objeto obj = new Objeto(1, "Arma", TipoObjeto.ARMA, Rareza.COMUN);
        
        assertEquals(0, obj.getValorEstadisticas("inexistente"));
        assertEquals(0, obj.getValorEstadisticas(""));
        assertEquals(0, obj.getValorEstadisticas(null));
    }
    
    @Test
    void testCompareTo_OrdenamientoPorId() {
        Objeto obj1 = new Objeto(1, "A", TipoObjeto.ARMA, Rareza.COMUN);
        Objeto obj2 = new Objeto(5, "B", TipoObjeto.ARMA, Rareza.COMUN);
        Objeto obj3 = new Objeto(3, "C", TipoObjeto.ARMA, Rareza.COMUN);
        
        assertTrue(obj1.compareTo(obj2) < 0);
        assertTrue(obj2.compareTo(obj3) > 0);
        assertTrue(obj1.compareTo(obj3) < 0);
    }
    
    @Test
    void testCompareTo_MismoId() {
        Objeto obj1 = new Objeto(5, "Primero", TipoObjeto.ARMA, Rareza.COMUN);
        Objeto obj2 = new Objeto(5, "Segundo", TipoObjeto.ARMA, Rareza.COMUN);
        
        assertEquals(0, obj1.compareTo(obj2));
    }
    
    @Test
    void testToString_ContieneDatos() {
        Objeto obj = new Objeto(1, "Espada", TipoObjeto.ARMA, Rareza.RARO);
        String str = obj.toString();
        
        assertTrue(str.contains("id="));
        assertTrue(str.contains("Espada"));
        assertTrue(str.contains("ARMA"));
    }
    
    @Test
    void testGetDescripcion_RetornaNuloInicial() {
        Objeto obj = new Objeto(1, "Arma", TipoObjeto.ARMA, Rareza.COMUN);
        
        assertNull(obj.getDescripcion());
    }
    
    @Test
    void testGetEfecto_RetornaNuloInicial() {
        Objeto obj = new Objeto(1, "Arma", TipoObjeto.ARMA, Rareza.COMUN);
        
        assertNull(obj.getEfecto());
    }
    
    @Test
    void testGetPeso_RetornaCeroInicial() {
        Objeto obj = new Objeto(1, "Arma", TipoObjeto.ARMA, Rareza.COMUN);
        
        assertEquals(0f, obj.getPeso());
    }
}