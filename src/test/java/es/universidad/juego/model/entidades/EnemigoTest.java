package es.universidad.juego.model.entidades;

import juego.model.entidades.Enemigo;
import juego.model.entidades.TipoEnemigo;
import juego.model.entidades.Jugador;
import es.universidad.juego.model.habitación.Celda;
import es.universidad.juego.model.habitación.TipoCelda;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnemigoTest {

    @Test
    void testConstructor_StatsIniciales() {
        Enemigo enemigo = new Enemigo(1, "Orco", TipoEnemigo.ORCO, 100, 15, 5);

        assertEquals("Orco", enemigo.getNombre());
        assertEquals(TipoEnemigo.ORCO, enemigo.getTipo());
        assertEquals(100, enemigo.getVidaActual());
        assertEquals(100, enemigo.getVidamaxima());
        assertEquals(15, enemigo.getAtaque());
        assertEquals(5, enemigo.getDefensa());
    }

    @Test
    void testAtacar_RetornaAtaque() {
        Enemigo enemigo = new Enemigo(1, "Orco", TipoEnemigo.ORCO, 100, 15, 5);
        
        // Crear un jugador mock para probar el ataque
        Celda celda = new Celda(0, 0, TipoCelda.SUELO);
        Jugador jugador = new Jugador("Test", celda, 1);
        jugador.setDefensa(0); // Sin defensa para simplificar
        
        int daño = enemigo.atacar(jugador);
        
        // El daño debe ser mayor que 0 (ataque * aleatorio * 2)
        assertTrue(daño > 0);
    }

    @Test
    void testObtenerDefensa_RetornaDefensa() {
        Enemigo enemigo = new Enemigo(1, "Orco", TipoEnemigo.ORCO, 100, 15, 5);

        assertEquals(5, enemigo.obtenerDefensa());
    }

    @Test
    void testGetNombre_RetornaNombre() {
        Enemigo enemigo = new Enemigo(1, "Goblin", TipoEnemigo.GOBLIN, 50, 8, 2);

        assertEquals("Goblin", enemigo.getNombre());
    }

    @Test
    void testGetObjeto_IndiceValido_InicialNull() {
        Enemigo enemigo = new Enemigo(1, "Orco", TipoEnemigo.ORCO, 100, 15, 5);

        assertNull(enemigo.getObjeto(0));
        assertNull(enemigo.getObjeto(1));
        assertNull(enemigo.getObjeto(2));
    }

    @Test
    void testGetObjeto_IndiceFueraDeRango() {
        Enemigo enemigo = new Enemigo(1, "Orco", TipoEnemigo.ORCO, 100, 15, 5);

        assertNull(enemigo.getObjeto(10));
        assertNull(enemigo.getObjeto(100));
    }

    @Test
    void testGetObjeto_IndiceNegativo() {
        Enemigo enemigo = new Enemigo(1, "Orco", TipoEnemigo.ORCO, 100, 15, 5);

        assertNull(enemigo.getObjeto(-1));
    }

    @Test
    void testRecibirDano_DanoMenorQueVida() {
        Enemigo enemigo = new Enemigo(1, "Test", TipoEnemigo.GOBLIN, 100, 10, 0);

        enemigo.recibirDaño(30);

        assertTrue(enemigo.getVidaActual() <= 70);
        assertTrue(enemigo.getVidaActual() >= 0);
    }

    @Test
    void testRecibirDano_DanoIgualVida() {
        Enemigo enemigo = new Enemigo(1, "Test", TipoEnemigo.GOBLIN, 100, 10, 0);

        enemigo.recibirDaño(100);

        assertTrue(enemigo.getVidaActual() <= 0);
    }

    @Test
    void testRecibirDano_VidaNoQuedaNegativa() {
        Enemigo enemigo = new Enemigo(1, "Test", TipoEnemigo.GOBLIN, 50, 10, 0);

        enemigo.recibirDaño(60);

        assertTrue(enemigo.getVidaActual() >= 0,
            "BUG: vidaActual = " + enemigo.getVidaActual() + " (deberia ser >= 0)");
    }

    @Test
    void testRecibirDano_MultiplesDanosAcumulados() {
        Enemigo enemigo = new Enemigo(1, "Test", TipoEnemigo.GOBLIN, 30, 10, 0);

        enemigo.recibirDaño(20);
        enemigo.recibirDaño(20);
        enemigo.recibirDaño(20);

        assertTrue(enemigo.getVidaActual() >= 0,
            "BUG: vidaActual = " + enemigo.getVidaActual() + " despues de 3 danos de 20");
    }

    @Test
    void testEstaMuerto_TrueCuandoVidaCero() {
        Enemigo enemigo = new Enemigo(1, "Test", TipoEnemigo.GOBLIN, 100, 10, 0);
        enemigo.recibirDaño(100);

        assertTrue(enemigo.estaMuerto());
    }

    @Test
    void testEstaMuerto_FalseCuandoVidaPositiva() {
        Enemigo enemigo = new Enemigo(1, "Test", TipoEnemigo.GOBLIN, 100, 10, 0);
        enemigo.recibirDaño(50);

        assertFalse(enemigo.estaMuerto());
    }

    @Test
    void testEstaMuerto_TrueCuandoVidaNegativa() {
        Enemigo enemigo = new Enemigo(1, "Test", TipoEnemigo.GOBLIN, 100, 10, 0);
        enemigo.recibirDaño(150);

        assertTrue(enemigo.estaMuerto(),
            "BUG: vidaActual = " + enemigo.vidaActual + " pero estaMuerto() = false");
    }

    @Test
    void testConstructor_DiferentesTiposEnemigo() {
        assertDoesNotThrow(() -> new Enemigo(1, "G", TipoEnemigo.GOBLIN, 50, 8, 2));
        assertDoesNotThrow(() -> new Enemigo(2, "O", TipoEnemigo.ORCO, 100, 15, 5));
        assertDoesNotThrow(() -> new Enemigo(3, "D", TipoEnemigo.DRAGON, 200, 30, 10));
        assertDoesNotThrow(() -> new Enemigo(4, "E", TipoEnemigo.ESQUELETO, 60, 12, 3));
        assertDoesNotThrow(() -> new Enemigo(5, "B", TipoEnemigo.BANDOLERO, 70, 10, 4));
    }

    @Test
    void testSetPosicionYGetPosicion() {
        Enemigo enemigo = new Enemigo(1, "Test", TipoEnemigo.GOBLIN, 50, 10, 0);

        assertNull(enemigo.getPosicion());
    }
}