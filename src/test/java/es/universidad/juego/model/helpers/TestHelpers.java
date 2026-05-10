package es.universidad.juego.model.helpers;

import juego.model.entidades.Objeto;
import juego.model.entidades.Enemigo;
import juego.model.entidades.TipoObjeto;
import juego.model.entidades.TipoEnemigo;
import juego.model.entidades.Rareza;

public class TestHelpers {
    
    public static Objeto crearObjetoSimple(int id, String nombre) {
        return new Objeto(id, nombre, TipoObjeto.ARMA, Rareza.COMUN);
    }
    
    public static Objeto crearPocimaVida(int id, int curacion) {
        Objeto obj = new Objeto(id, "Pocion", TipoObjeto.POCIMA_VIDA, Rareza.COMUN);
        obj.addEstadistica("vida", curacion);
        return obj;
    }
    
    public static Objeto crearPocimaMana(int id, int mana) {
        Objeto obj = new Objeto(id, "Pocion", TipoObjeto.POCIMA_MANA, Rareza.COMUN);
        obj.addEstadistica("mana", mana);
        return obj;
    }
    
    public static Enemigo crearEnemigoSimple(int id, String nombre) {
        return new Enemigo(id, nombre, TipoEnemigo.GOBLIN, 100, 10, 5);
    }
    
    public static Enemigo crearEnemigoConStats(int id, String nombre, int vida, int ataque, int defensa) {
        return new Enemigo(id, nombre, TipoEnemigo.ORCO, vida, ataque, defensa);
    }
}