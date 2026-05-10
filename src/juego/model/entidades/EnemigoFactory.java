package juego.model.entidades;

import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;
import juego.model.habitacion.Celda;
import juego.model.habitacion.Habitacion;
import juego.model.habitacion.TipoCelda;

import java.util.Random;

/**
 * Factoría estática para crear enemigos con estadísticas predefinidas según su tipo.
 * Centraliza la lógica de creación y configuración de enemigos, incluyendo
 * la asignación aleatoria de botín y la búsqueda de posiciones libres en la habitación.
 */
public class EnemigoFactory {

    /**
     * Crea un enemigo individual con estadísticas predefinidas según su tipo
     * y lo coloca en la posición de la matriz indicada.
     * @param tipo    tipo de enemigo (GOBLIN, ORCO, etc.) que determina sus estadísticas base
     * @param id      identificador único del enemigo
     * @param fila    fila inicial en la matriz de la habitación
     * @param columna columna inicial en la matriz de la habitación
     * @return enemigo configurado con estadísticas, posición y loot según su tipo
     */
    public static Enemigo crearEnemigo(TipoEnemigo tipo, int id, int fila, int columna) {
        // Obtener estadísticas bases del tipo
        int vida = tipo.getVidaBase();
        int ataque = tipo.getAtaqueBase();
        int defensa = tipo.getDefensaBase();

        // Crear enemigo con esas estadísticas
        Enemigo enemigo = new Enemigo(id, tipo.name(), tipo, vida, ataque, defensa);

        // Crear celda de posición
        Celda posicion = new Celda(fila, columna, TipoCelda.ENEMIGO);
        enemigo.setPosicion(posicion);

        // Asignar loot aleatorio y velocidad según tipo
        asignarLoot(enemigo, tipo);
        asignarVelocidad(enemigo, tipo);
        return enemigo;
    }

    /**
     * Crea y coloca múltiples enemigos en posiciones libres de la habitación indicada.
     * Si no hay suficientes posiciones libres, algunos tipos pueden quedar sin crear.
     * @param tipos      array de tipos de enemigos a instanciar
     * @param habitacion habitación donde se buscarán posiciones libres y se colocarán
     * @return lista con todos los enemigos creados
     */
    public static ListaSimplementeEnlazada<Enemigo> crearEnemigos(TipoEnemigo[] tipos, Habitacion habitacion, Celda posicionJugador) {

        ListaSimplementeEnlazada<Enemigo> enemigos = new ListaSimplementeEnlazada<>();
        int id = 1;

        for (TipoEnemigo tipo : tipos) {
            int[] pos = buscarPosicionLibre(habitacion, posicionJugador); // Buscamos una celda libre en la habitación
            if (pos != null) {
                Enemigo enemigo = crearEnemigo(tipo, id++, pos[0], pos[1]);
                habitacion.setCelda(pos[0], pos[1],
                        new Celda(pos[0], pos[1], TipoCelda.ENEMIGO)); // Marcamos la celda como ENEMIGO
                enemigos.add(enemigo);
            }
        }
        return enemigos;
    }

    /**
     * Asigna botín aleatorio al enemigo según su tipo.
     * Cada tipo tiene una probabilidad distinta de dejar un objeto al morir.
     * @param enemigo enemigo al que asignar el botín
     * @param tipo    tipo del enemigo que determina el objeto y la probabilidad
     */
    private static void asignarLoot(Enemigo enemigo, TipoEnemigo tipo) {
        Random random = new Random();
        int baseId= enemigo.getId()*10;
        switch (tipo) {
            case GOBLIN -> {
                enemigo.loot[0] = new Objeto(baseId, "Monedas", TipoObjeto.TESORO, Rareza.COMUN);
                if (random.nextDouble() < 0.5) { // 50% de probabilidad de soltar una poción pequeña
                    enemigo.loot[1] = new Objeto(baseId+1, "Poción Pequeña", TipoObjeto.POCIMA_VIDA, Rareza.COMUN);
                } else if (random.nextDouble() < 0.2) { // 20% de probabilidad de soltar una Daga Robada

                    enemigo.loot[2] = new Objeto(baseId+2, "Daga Robada", TipoObjeto.ARMA, Rareza.POCO_COMUN);
                }
            }
            case ORCO -> {
                enemigo.loot[0] = new Objeto(baseId, "Hacha Rota", TipoObjeto.ARMA, Rareza.POCO_COMUN);
                if (random.nextDouble() < 0.5) { // 50% de probabilidad de soltar un Escudo Viejo
                    enemigo.loot[1] = new Objeto(baseId+1, "Escudo Viejo", TipoObjeto.ARMADURA, Rareza.COMUN);
                } else if (random.nextDouble() < 0.2) { // 20% de probabilidad de soltar una Poción de Orco
                    enemigo.loot[2] = new Objeto(baseId+2, "Poción Orco", TipoObjeto.POCIMA_VIDA, Rareza.POCO_COMUN);
                }
            }
            case ESQUELETO -> {
                enemigo.loot[0] = new Objeto(baseId, "Hueso", TipoObjeto.TESORO, Rareza.COMUN);
                if (random.nextDouble() < 0.5) { // 50% de probabilidad de soltar un hueso
                    enemigo.loot[1] = new Objeto(baseId+1, "Espada Oxidada", TipoObjeto.ARMA, Rareza.COMUN);
                } else if (random.nextDouble() < 0.2) {// 20% de probabilidad de soltar una LLave Antigua
                    enemigo.loot[2] = new Objeto(baseId+2, "LLave Antigua", TipoObjeto.LLAVE, Rareza.POCO_COMUN);
                }
            }
            case BANDOLERO -> {
                enemigo.loot[0] = new Objeto(baseId, "Pocion Robada", TipoObjeto.POCIMA_VIDA, Rareza.POCO_COMUN);
                if (random.nextDouble() < 0.5) { // 50% de probabilidad de soltar una Bolsa Monedas
                    enemigo.loot[1] = new Objeto(baseId+1, "Bolsa Monedas", TipoObjeto.TESORO, Rareza.POCO_COMUN);
                }
                if (random.nextDouble() < 0.2) { // 20% de probabilidad de soltar una Daga Envenenada
                enemigo.loot[2] = new Objeto(baseId+2, "Daga Envenenada", TipoObjeto.ARMA, Rareza.RARO);
                }
            }
            case DRAGON -> {
                enemigo.loot[0] = new Objeto(baseId, "Escama Dragón", TipoObjeto.TESORO, Rareza.LEGENDARIO);
                if (random.nextDouble() < 0.5) { // 50% de probabilidad de soltar una Garra de Dragon
                    enemigo.loot[1] = new Objeto(baseId+1, "Garra Dragón", TipoObjeto.ARMA, Rareza.EPICO);
                }
                else if (random.nextDouble() < 0.2) {  // 20% de probabilidad de soltar una Esencia de Dragon
                    enemigo.loot[2]= new Objeto(baseId+2,"Esencia Dragón", TipoObjeto.POCIMA_MANA, Rareza.LEGENDARIO);
                }
            }
        }
    }

    /**
     * Busca la primera posición libre (celda transitable) en la habitación.
     * Recorre la matriz de izquierda a derecha y de arriba a abajo.
     *
     * @param habitacion habitación en la que buscar una posición libre
     * @return array [fila, columna] de la primera celda libre, o null si no hay espacio
     */
    private static int[] buscarPosicionLibre(Habitacion habitacion , Celda posicionJugador) {
        for (int i = 0; i < habitacion.getFila(); i++) {
            for (int j = 0; j < habitacion.getColumna(); j++) {
                int dist= Math.abs(i-posicionJugador.getX()) + Math.abs(j-posicionJugador.getY());
                if (habitacion.esTransitable(i, j) && dist>3) {
                    return new int[]{i, j}; // Primera celda transitable encontrada
                }
            }
        }
        return null; // No hay ninguna celda libre en la habitación
    }

    private static void asignarVelocidad(Enemigo enemigo, TipoEnemigo tipo) {
        switch (tipo) {
            case GOBLIN -> enemigo.setVelocidad(3);
            case ORCO -> enemigo.setVelocidad(2);
            case ESQUELETO -> enemigo.setVelocidad(2);
            case BANDOLERO -> enemigo.setVelocidad(3);
            case DRAGON -> enemigo.setVelocidad(1);
            }
        }
    }

