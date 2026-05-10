package juego.model.entidades;

import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;
import juego.model.habitación.Celda;
import juego.model.habitación.Habitacion;
import juego.model.habitación.TipoCelda;

import java.util.Random;

public class EnemigoFactory {

    /**
     * Crea un enemigo con estadísticas predefinidas según su tipo
     * @param tipo Tipo de enemigo (GOBLIN, ORCO, etc.)
     * @param id Identificador único del enemigo
     * @param fila Fila inicial en la matriz
     * @param columna Columna inicial en la matriz
     * @return Enemigo configurado con stats según tipo
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

        // Asignar loot aleatorio según tipo
        asignarLoot(enemigo, tipo);

        return enemigo;
    }

    /**
     * Crea múltiples enemigos para una habitación
     * @param tipos Array de tipos de enemigos a crear
     * @param habitacion Habitación donde se colocarán
     * @return Lista de enemigos creados
     */
    public static ListaSimplementeEnlazada<Enemigo> crearEnemigos(
            TipoEnemigo[] tipos, Habitacion habitacion) {

        ListaSimplementeEnlazada<Enemigo> enemigos = new ListaSimplementeEnlazada<>();
        int id = 1;

        for (TipoEnemigo tipo : tipos) {
            // Buscar posición libre en la habitación
            int[] pos = buscarPosicionLibre(habitacion);
            if (pos != null) {
                Enemigo enemigo = crearEnemigo(tipo, id++, pos[0], pos[1]);
                habitacion.setCelda(pos[0], pos[1],
                        new Celda(pos[0], pos[1], TipoCelda.ENEMIGO));
                enemigos.add(enemigo);
            }
        }
        return enemigos;
    }

    /**
     * Asigna loot aleatorio según el tipo de enemigo
     */
    private static void asignarLoot(Enemigo enemigo, TipoEnemigo tipo) {
        Random random = new Random();
        double probabilidad = random.nextDouble();
        switch (tipo) {
            case GOBLIN -> {
                if(probabilidad <0.3){
                    enemigo.loot[0]=new Objeto(1,"Monedas",TipoObjeto.TESORO,Rareza.COMUN);
                }
            }
            case ORCO -> {
               if(probabilidad< 0.5){
                   enemigo.loot[0]=new Objeto(2,"Hacha Rota", TipoObjeto.ARMA,Rareza.POCO_COMUN);
               }
            }
            case ESQUELETO ->{
               if(probabilidad <0.4){
                   enemigo.loot[0]=new Objeto(3,"Hueso",TipoObjeto.TESORO,Rareza.COMUN);
               }
            }
            case BANDOLERO -> {
                if (probabilidad < 0.6) {
                    enemigo.loot[0] = new Objeto(4, "Pocion Robada", TipoObjeto.POCIMA_VIDA, Rareza.POCO_COMUN);
                }
            }
            case DRAGON -> {
                enemigo.loot[0]=new Objeto(5,"Escama Dragón",TipoObjeto.TESORO,Rareza.LEGENDARIO);
                }
            }
        }
    }

    /**
     * Busca una posición libre en la habitación
     * @return Array [fila, columna] o null si no hay espacio
     */
    private static int[] buscarPosicionLibre(Habitacion habitacion) {
        // Recorrer la matriz buscando celda vacía/transitable
        // Retornar primera posición libre encontrada
    }
}