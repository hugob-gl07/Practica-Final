package juego.model.entidades;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnemigoTest {

    @Test
    void testConstructor_StatsIniciales() {
        Enemigo enemigo = new Enemigo(1, "Orco", TipoEnemigo.ORCO, 100, 15, 5);

        assertEquals("Orco", enemigo.getNombre());
        assertEquals(TipoEnemigo.ORCO, enemigo.tipo);
        assertEquals(100, enemigo.vidaActual);
        assertEquals(100, enemigo.vidamaxima);
        assertEquals(15, enemigo.ataque);
        assertEquals(5, enemigo.defensa);
    }

    @Test
    void testAtacar_RetornaAtaque() {
        Enemigo enemigo = new Enemigo(1, "Orco", TipoEnemigo.ORCO, 100, 15, 5);

        assertEquals(15, enemigo.atacar());
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

        assertTrue(enemigo.vidaActual <= 70);
        assertTrue(enemigo.vidaActual >= 0);
    }

    @Test
    void testRecibirDano_DanoIgualVida() {
        Enemigo enemigo = new Enemigo(1, "Test", TipoEnemigo.GOBLIN, 100, 10, 0);

        enemigo.recibirDaño(100);

        assertTrue(enemigo.vidaActual <= 0);
    }

    @Test
    void testRecibirDano_VidaNoQuedaNegativa() {
        Enemigo enemigo = new Enemigo(1, "Test", TipoEnemigo.GOBLIN, 50, 10, 0);

        enemigo.recibirDaño(60);

        assertTrue(enemigo.vidaActual >= 0,
            "BUG: vidaActual = " + enemigo.vidaActual + " (deberia ser >= 0)");
    }

    @Test
    void testRecibirDano_MultiplesDanosAcumulados() {
        Enemigo enemigo = new Enemigo(1, "Test", TipoEnemigo.GOBLIN, 30, 10, 0);

        enemigo.recibirDaño(20);
        enemigo.recibirDaño(20);
        enemigo.recibirDaño(20);

        assertTrue(enemigo.vidaActual >= 0,
            "BUG: vidaActual = " + enemigo.vidaActual + " despues de 3 danos de 20");
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