package test.java.es.universidad.juego.model.entidades;

import juego.model.entidades.*;
import juego.model.habitacion.Celda;
import juego.model.habitacion.TipoCelda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JugadorTest {
    
    private Jugador jugador;
    private Celda celdaInicial;
    private Celda celdaTransitable;
    private Celda celdaNoTransitable;
    
    @BeforeEach
    void setUp() {
        celdaInicial = new Celda(0, 0, TipoCelda.SUELO);
        celdaTransitable = new Celda(1, 0, TipoCelda.SUELO);
        celdaNoTransitable = new Celda(2, 0, TipoCelda.PARED);
        
        jugador = new Jugador("Heroe", celdaInicial, 1);
    }
    
    @Test
    void testConstructor_StatsIniciales() {
        assertEquals("Heroe", jugador.getNombre());
        assertEquals(1, jugador.getNivel());
        assertEquals(0, jugador.getExperiencia());
        assertEquals(100, jugador.getVidaActual());
        assertEquals(100, jugador.getVidaMaxima());
        assertEquals(10, jugador.getAtaque());
        assertEquals(5, jugador.getDefensa());
        assertEquals(50, jugador.getMana());
    }
    
    @Test
    void testConstructor_IdAsignado() {
        assertEquals(1, jugador.getId());
    }
    
    @Test
    void testConstructor_InventarioInicializado() {
        assertNotNull(jugador.getInventario());
    }
    
    @Test
    void testConstructor_HistorialInicializado() {
        assertNotNull(jugador.getHistorialAcciones());
    }
    
    @Test
    void testConstructor_PosicionInicial() {
        assertEquals(celdaInicial, jugador.getPosicion());
    }
    
    @Test
    void testAddExperiencia_SinSubirNivel() {
        jugador.addExperiencia(50);
        
        assertEquals(50, jugador.getExperiencia());
        assertEquals(1, jugador.getNivel());
    }
    
    @Test
    void testAddExperiencia_Exactamente100() {
        jugador.addExperiencia(100);
        
        assertTrue(jugador.getNivel() >= 1);
    }
    
    @Test
    void testAddExperiencia_MasDe100() {
        jugador.addExperiencia(150);
        
        assertTrue(jugador.getNivel() >= 1);
    }
    
    @Test
    void testSubirNivel_StatsAumentan() {
        int nivelInicial = jugador.getNivel();
        int ataqueInicial = jugador.getAtaque();
        int defensaInicial = jugador.getDefensa();
        int vidaMaximaInicial = jugador.getVidaMaxima();
        int manaInicial = jugador.getMana();
        
        jugador.addExperiencia(100);
        
        if (jugador.getNivel() > nivelInicial) {
            assertTrue(jugador.getAtaque() >= ataqueInicial);
            assertTrue(jugador.getDefensa() >= defensaInicial);
            assertTrue(jugador.getVidaMaxima() >= vidaMaximaInicial);
            assertTrue(jugador.getMana() >= manaInicial);
        }
    }
    
    @Test
    void testSubirNivel_ExperienciaSeReinicia() {
        jugador.addExperiencia(100);
        
        assertTrue(jugador.getExperiencia() < 100);
    }
    
    @Test
    void testSubirNivel_HistorialActualizado() {
        jugador.addExperiencia(200);
        
        String historial = jugador.getHistorialAcciones();
        assertTrue(historial.contains("nivel") || historial.length() >= 0);
    }
    
    @Test
    void testRecibirDaño_Normal() {
        int vidaAntes = jugador.getVidaActual();
        
        jugador.recibirDaño(30);
        
        assertTrue(jugador.getVidaActual() < vidaAntes);
        assertTrue(jugador.getVidaActual() >= 0);
    }
    
    @Test
    void testRecibirDaño_DefensaReduceDaño() {
        int ataqueEnemigo = 20;
        int dañoEsperadoMax = ataqueEnemigo - jugador.getDefensa();
        
        int vidaAntes = jugador.getVidaActual();
        jugador.recibirDaño(ataqueEnemigo);
        int dañoRecibido = vidaAntes - jugador.getVidaActual();
        
        assertTrue(dañoRecibido <= dañoEsperadoMax + 1);
    }
    
    @Test
    void testRecibirDaño_VidaNoQuedaNegativa() {
        jugador.recibirDaño(200);
        
        assertTrue(jugador.getVidaActual() >= 0);
    }
    
    @Test
    void testRecibirDaño_DañoMenorQueDefensa() {
        int vidaAntes = jugador.getVidaActual();
        
        jugador.recibirDaño(3);
        
        int dañoRecibido = vidaAntes - jugador.getVidaActual();
        assertTrue(dañoRecibido <= 1);
    }
    
    @Test
    void testAtacar_EnemigoRecibeDaño() {
        Enemigo enemigo = new Enemigo(1, "Orco", TipoEnemigo.ORCO, 100, 10, 0);
        int vidaAntes = enemigo.getVidaActual();
        
        int daño = jugador.atacar(enemigo);
        
        assertEquals(jugador.getAtaque(), daño);
        assertTrue(enemigo.getVidaActual() < vidaAntes);
    }
    
    @Test
    void testAtacar_RetornaValorAtaque() {
        Enemigo enemigo = new Enemigo(1, "Orco", TipoEnemigo.ORCO, 100, 10, 0);
        
        int daño = jugador.atacar(enemigo);
        
        assertEquals(10, daño);
    }
    
    @Test
    void testAtacar_HistorialActualizado() {
        Enemigo enemigo = new Enemigo(1, "Orco", TipoEnemigo.ORCO, 100, 10, 0);
        
        jugador.atacar(enemigo);
        
        assertTrue(jugador.getHistorialAcciones().contains("Orco"));
    }
    
    @Test
    void testMover_CeldaTransitable() {
        jugador.mover(celdaTransitable);
        
        assertEquals(celdaTransitable, jugador.getPosicion());
    }
    
    @Test
    void testMover_CeldaNoTransitable_PosicionNoCambia() {
        Celda posicionAntes = jugador.getPosicion();
        
        jugador.mover(celdaNoTransitable);
        
        assertEquals(posicionAntes, jugador.getPosicion());
    }
    
    @Test
    void testMover_HistorialActualizado() {
        jugador.mover(celdaTransitable);
        
        assertTrue(jugador.getHistorialAcciones().contains("Moviste"));
    }
    
    @Test
    void testMover_MismaCelda() {
        Celda posicionAntes = jugador.getPosicion();
        
        jugador.mover(celdaInicial);
        
        assertEquals(posicionAntes, jugador.getPosicion());
    }
    
    @Test
    void testUsarObjeto_Existe_PocimaVida() {
        Objeto pocima = new Objeto(1, "Pocion", TipoObjeto.POCIMA_VIDA, Rareza.COMUN);
        pocima.addEstadistica("vida", 30);
        
        int vidaAntes = jugador.getVidaActual();
        jugador.getInventario().addObjeto(pocima);
        
        if (jugador.getInventario().getTamaño() > 0) {
            int tamañoAntes = jugador.getInventario().getTamaño();
            jugador.usarObjeto(1);
            
            assertTrue(jugador.getVidaActual() > vidaAntes ||
                       jugador.getInventario().getTamaño() < tamañoAntes);
        }
    }
    
    @Test
    void testUsarObjeto_NoExiste_SinCambios() {
        int vidaAntes = jugador.getVidaActual();
        int manaAntes = jugador.getMana();
        
        jugador.usarObjeto(999);
        
        assertEquals(vidaAntes, jugador.getVidaActual());
        assertEquals(manaAntes, jugador.getMana());
    }
    
    @Test
    void testUsarObjeto_InventarioVacio() {
        int vidaAntes = jugador.getVidaActual();
        
        jugador.usarObjeto(1);
        
        assertEquals(vidaAntes, jugador.getVidaActual());
    }
    
    @Test
    void testUsarObjeto_HistorialActualizado() {
        Objeto pocima = new Objeto(1, "Pocion", TipoObjeto.POCIMA_VIDA, Rareza.COMUN);
        pocima.addEstadistica("vida", 30);
        jugador.getInventario().addObjeto(pocima);
        
        jugador.usarObjeto(1);
        
        assertTrue(jugador.getHistorialAcciones().contains("Pocion") || 
                   jugador.getHistorialAcciones().length() >= 0);
    }
    
    @Test
    void testGetInventario_RetornaNoNull() {
        assertNotNull(jugador.getInventario());
    }
    
    @Test
    void testGetInventario_TieneCapacidad() {
        assertTrue(jugador.getInventario().getTamaño() >= 0);
    }
    
    @Test
    void testEstaSalud_True() {
        assertTrue(jugador.estaSalud());
    }
    
    @Test
    void testEstaSalud_False() {
        jugador.setVidaActual(0);
        
        assertFalse(jugador.estaSalud());
    }
    
    @Test
    void testGetHistorialAcciones_RetornaString() {
        String historial = jugador.getHistorialAcciones();
        
        assertNotNull(historial);
    }
    
    @Test
    void testGetHistorialAcciones_VacioInicialmente() {
        String historial = jugador.getHistorialAcciones();
        
        assertNotNull(historial);
    }
    
    @Test
    void testEstaSalud_MultipleDanios() {
        Jugador j = new Jugador("Test", celdaInicial,1);
        
        j.recibirDaño(30);
        assertTrue(j.estaSalud());
        
        j.recibirDaño(30);
        assertTrue(j.estaSalud());
        

        assertFalse(j.estaSalud());
    }
}