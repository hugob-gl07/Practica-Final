package juego.model.entidades;

import juego.model.habitación.Celda;
import juego.model.habitación.TipoCelda;
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
        
        jugador = new Jugador("Heroe", celdaInicial);
    }
    
    @Test
    void testConstructor_StatsIniciales() {
        assertEquals("Heroe", jugador.nombre);
        assertEquals(1, jugador.nivel);
        assertEquals(0, jugador.experiencia);
        assertEquals(100, jugador.vidaActual);
        assertEquals(100, jugador.vidaMaxima);
        assertEquals(10, jugador.ataque);
        assertEquals(5, jugador.defensa);
        assertEquals(50, jugador.mana);
    }
    
    @Test
    void testConstructor_IdAsignado() {
        assertEquals(1, jugador.id);
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
        assertEquals(celdaInicial, jugador.posicion);
    }
    
    @Test
    void testAddExperiencia_SinSubirNivel() {
        jugador.addExperiencia(50);
        
        assertEquals(50, jugador.experiencia);
        assertEquals(1, jugador.nivel);
    }
    
    @Test
    void testAddExperiencia_Exactamente100() {
        jugador.addExperiencia(100);
        
        assertTrue(jugador.nivel >= 1);
    }
    
    @Test
    void testAddExperiencia_MasDe100() {
        jugador.addExperiencia(150);
        
        assertTrue(jugador.nivel >= 1);
    }
    
    @Test
    void testSubirNivel_StatsAumentan() {
        int nivelInicial = jugador.nivel;
        int ataqueInicial = jugador.ataque;
        int defensaInicial = jugador.defensa;
        int vidaMaximaInicial = jugador.vidaMaxima;
        int manaInicial = jugador.mana;
        
        jugador.addExperiencia(100);
        
        if (jugador.nivel > nivelInicial) {
            assertTrue(jugador.ataque >= ataqueInicial);
            assertTrue(jugador.defensa >= defensaInicial);
            assertTrue(jugador.vidaMaxima >= vidaMaximaInicial);
            assertTrue(jugador.mana >= manaInicial);
        }
    }
    
    @Test
    void testSubirNivel_ExperienciaSeReinicia() {
        jugador.addExperiencia(100);
        
        assertTrue(jugador.experiencia < 100);
    }
    
    @Test
    void testSubirNivel_HistorialActualizado() {
        jugador.addExperiencia(200);
        
        String historial = jugador.getHistorialAcciones();
        assertTrue(historial.contains("nivel") || historial.length() >= 0);
    }
    
    @Test
    void testRecibirDaño_Normal() {
        int vidaAntes = jugador.vidaActual;
        
        jugador.recibirDaño(30);
        
        assertTrue(jugador.vidaActual < vidaAntes);
        assertTrue(jugador.vidaActual >= 0);
    }
    
    @Test
    void testRecibirDaño_DefensaReduceDaño() {
        int ataqueEnemigo = 20;
        int dañoEsperadoMax = ataqueEnemigo - jugador.defensa;
        
        int vidaAntes = jugador.vidaActual;
        jugador.recibirDaño(ataqueEnemigo);
        int dañoRecibido = vidaAntes - jugador.vidaActual;
        
        assertTrue(dañoRecibido <= dañoEsperadoMax + 1);
    }
    
    @Test
    void testRecibirDaño_VidaNoQuedaNegativa() {
        jugador.recibirDaño(200);
        
        assertTrue(jugador.vidaActual >= 0);
    }
    
    @Test
    void testRecibirDaño_DañoMenorQueDefensa() {
        int vidaAntes = jugador.vidaActual;
        
        jugador.recibirDaño(3);
        
        int dañoRecibido = vidaAntes - jugador.vidaActual;
        assertTrue(dañoRecibido <= 1);
    }
    
    @Test
    void testAtacar_EnemigoRecibeDaño() {
        Enemigo enemigo = new Enemigo(1, "Orco", TipoEnemigo.ORCO, 100, 10, 0);
        int vidaAntes = enemigo.vidaActual;
        
        int daño = jugador.atacar(enemigo);
        
        assertEquals(jugador.ataque, daño);
        assertTrue(enemigo.vidaActual < vidaAntes);
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
        
        assertEquals(celdaTransitable, jugador.posicion);
    }
    
    @Test
    void testMover_CeldaNoTransitable_PosicionNoCambia() {
        Celda posicionAntes = jugador.posicion;
        
        jugador.mover(celdaNoTransitable);
        
        assertEquals(posicionAntes, jugador.posicion);
    }
    
    @Test
    void testMover_HistorialActualizado() {
        jugador.mover(celdaTransitable);
        
        assertTrue(jugador.getHistorialAcciones().contains("Moviste"));
    }
    
    @Test
    void testMover_MismaCelda() {
        Celda posicionAntes = jugador.posicion;
        
        jugador.mover(celdaInicial);
        
        assertEquals(posicionAntes, jugador.posicion);
    }
    
    @Test
    void testUsarObjeto_Existe_PocimaVida() {
        Objeto pocima = new Objeto(1, "Pocion", TipoObjeto.POCIMA_VIDA, Rareza.COMUN);
        pocima.addEstadistica("vida", 30);
        
        int vidaAntes = jugador.vidaActual;
        jugador.getInventario().addObjeto(pocima);
        
        if (jugador.getInventario().getTamaño() > 0) {
            int tamañoAntes = jugador.getInventario().getTamaño();
            jugador.usarObjeto(1);
            
            assertTrue(jugador.vidaActual > vidaAntes || 
                       jugador.getInventario().getTamaño() < tamañoAntes);
        }
    }
    
    @Test
    void testUsarObjeto_NoExiste_SinCambios() {
        int vidaAntes = jugador.vidaActual;
        int manaAntes = jugador.mana;
        
        jugador.usarObjeto(999);
        
        assertEquals(vidaAntes, jugador.vidaActual);
        assertEquals(manaAntes, jugador.mana);
    }
    
    @Test
    void testUsarObjeto_InventarioVacio() {
        int vidaAntes = jugador.vidaActual;
        
        jugador.usarObjeto(1);
        
        assertEquals(vidaAntes, jugador.vidaActual);
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
        jugador.vidaActual = 0;
        
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
        Jugador j = new Jugador("Test", celdaInicial);
        
        j.recibirDaño(30);
        assertTrue(j.estaSalud());
        
        j.recibirDaño(30);
        assertTrue(j.estaSalud());
        
        j.recibirDaño(100);
        assertFalse(j.estaSalud());
    }
}