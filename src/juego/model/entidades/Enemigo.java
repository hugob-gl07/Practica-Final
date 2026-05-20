package juego.model.entidades;
import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;
import juego.model.habitacion.Celda;
import juego.model.habitacion.Habitacion;

/**
 * Representa un enemigo del juego con sus estadísticas de combate y posición en la habitación.
 * Los enemigos se comparan entre sí por su valor de ataque, lo que permite ordenarlos por dificultad.
 */
public class Enemigo implements Comparable<Enemigo> {
    protected int id;           // Identificador único del enemigo
    protected String nombre;    // Nombre del enemigo mostrado en combate
    protected TipoEnemigo tipo; // Categoría del enemigo que determina sus estadísticas base
    protected int vidaActual;   // Puntos de vida actuales (decrece al recibir daño)
    protected int vidamaxima;   // Puntos de vida máximos (usados para calcular porcentaje de salud)
    protected int ataque;       // Daño que inflige al jugador en cada turno de ataque
    protected int defensa;      // Reducción de daño que aplica cuando recibe golpes
    protected Celda posicion;   // Celda de la matriz donde se encuentra el enemigo
    protected Objeto[] loot;    // Array de objetos que puede dejar al morir (hasta 3)
    protected int velocidad;    // Número de casillas que puede moverse por turno

    /**
     * Crea un enemigo con sus atributos de combate y lo inicia con la vida máxima completa.
     * @param id         identificador único
     * @param nombre     nombre visible del enemigo
     * @param tipo       categoría del enemigo
     * @param vidamaxima puntos de vida máximos
     * @param ataque     daño base por ataque
     * @param defensa    reducción de daño base
     */
    public Enemigo(int id, String nombre, TipoEnemigo tipo, int vidamaxima, int ataque, int defensa) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.vidamaxima = vidamaxima;
        this.vidaActual = vidamaxima; // El enemigo empieza con la vida máxima completa
        this.ataque = ataque;
        this.defensa = defensa;
        this.loot = new Objeto[3]; // Capacidad para hasta 3 objetos de botín
    }

    /**
     * Aplica el daño indicado a la vida actual del enemigo.
     * La vida nunca cae por debajo de 0.
     * @param daño cantidad de daño a aplicar
     */
    public void recibirDaño(int daño){
        vidaActual -= daño;
        if(vidaActual < 0){
            vidaActual = 0; // La vida no puede ser negativa
        }
    }

    /**
     * Devuelve el daño que inflige el enemigo al atacar.
     * @return daño base del ataque
     */
    public int atacar(Jugador jugador){
        if(jugador == null){
            throw new IllegalArgumentException("El jugador no puede ser nulo");
        }
        double aleatorio = Math.random();  // Número aleatorio entre 0 y 1
        int dañoReal = Math.max(0, (int)(this.ataque * (aleatorio * 2) - jugador.getDefensa()));
        jugador.recibirDaño(dañoReal);
        return dañoReal;
    }

    /**
     * Devuelve la defensa del enemigo.
     * @return reducción de daño del enemigo
     */
    public int obtenerDefensa(){
        return defensa;
    }

    /**
     * Comprueba si el enemigo ha sido derrotado.
     * @return true si la vida actual es 0 o menos, false si sigue vivo
     */
    public boolean estaMuerto(){
        return vidaActual <= 0; // Si la vida llegó a 0 el enemigo está muerto
    }

    /**
     * Devuelve el objeto de botín en el índice indicado.
     * @param indice posición en el array de loot (0-2)
     * @return objeto de botín, o null si el índice es inválido o esa posición está vacía
     */
    public Objeto getObjeto(int indice){
        if(indice>=0 && indice<loot.length){
            return loot[indice]; // Devolvemos el objeto si el índice es válido
        }
        return null; // Índice fuera del rango del array de loot
    }

    /**
     * Mueve el enemigo hacia el jugador eligiendo la casilla alcanzable más cercana a él.
     * Calcula la distancia Manhattan desde cada casilla alcanzable hasta la posición del jugador
     * y se desplaza a la que minimiza esa distancia.
     * @param jugador   jugador hacia el que se dirige el enemigo
     * @param habitacion habitación donde se calcula el movimiento
     */
    public void moverHacia(Jugador jugador, Habitacion habitacion){
        if(jugador == null || habitacion == null){
            throw new IllegalArgumentException("El jugador y la habitación no pueden ser nulos");
        }
        if(posicion == null){
            throw new IllegalArgumentException("El enemigo no tiene posición asignada");
        }
        Celda posJugador=jugador.getPosicion();
        int menordistancia= Integer.MAX_VALUE;
        Celda celdamasCercana= null;
        int distanciadirecta= Math.abs((posicion.getX())-posJugador.getX()) +  Math.abs((posicion.getY())-posJugador.getY());
        if(distanciadirecta>velocidad){
           Celda resultado=obtenerCeldaAdyacenteMasCercana(habitacion,posJugador);
           if(resultado!=null&& !resultado.equals(posJugador)){
               posicion=resultado;
           }
        }
        else{
            ListaSimplementeEnlazada<Celda> casillas= habitacion.obtenerCasillasAlcanzables(posicion.getX(),posicion.getY(),velocidad);
            for(int i=0; i<casillas.getSize(); i++){
                Celda celda= casillas.getAt(i);
                if(!celda.equals(posJugador)){
                    int dist= Math.abs(celda.getX()-posJugador.getX())+Math.abs(celda.getY()-posJugador.getY());
                    if(dist<menordistancia){
                        menordistancia= dist;
                        celdamasCercana= celda;
                    }
                }
            }
            if(celdamasCercana!=null){
                posicion=celdamasCercana;
            }
        }
    }

    private Celda obtenerCeldaAdyacenteMasCercana(Habitacion habitacion,Celda posJugador){
        int[] df= {-1,1,0,0};
        int[] dc= {0,0,-1,1};
        Celda mejorarCelda= null;
        int menorDistancia= Integer.MAX_VALUE;
        for(int i=0; i<4; i++){
            int nuevaFila= posicion.getX()+df[i];
            int nuevaColumna= posicion.getY()+dc[i];
            if(habitacion.esTransitable(nuevaFila,nuevaColumna)){
                int dist= Math.abs(nuevaFila-posJugador.getX())+Math.abs(nuevaColumna-posJugador.getY());
                if(dist<menorDistancia){
                    menorDistancia= dist;
                    mejorarCelda= habitacion.getCelda(nuevaFila,nuevaColumna);
                }
            }
        }
        return mejorarCelda;
    }

    /**
     * Devuelve la celda donde está situado el enemigo.
     * @return celda actual del enemigo
     */
    public Celda getPosicion(){
        return posicion;
    }

    /**
     * Establece la celda donde se sitúa el enemigo.
     * @param posicion nueva celda del enemigo
     */
    public void setPosicion(Celda posicion){
        this.posicion = posicion;
    }

    /**
     * Devuelve el nombre del enemigo.
     * @return nombre del enemigo
     */
    public String getNombre(){
        return nombre;
    }

    /**
     * Devuelve el identificador único del enemigo.
     * @return id del enemigo
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve el tipo del enemigo.
     * @return categoría del enemigo
     */
    public TipoEnemigo getTipo() {
        return tipo;
    }

    /**
     * Devuelve los puntos de vida actuales del enemigo.
     * @return vida actual
     */
    public int getVidaActual() {
        return vidaActual;
    }

    /**
     * Establece los puntos de vida actuales del enemigo.
     * @param vidaActual nueva vida actual
     */
    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    /**
     * Devuelve los puntos de vida máximos del enemigo.
     * @return vida máxima
     */
    public int getVidamaxima() {
        return vidamaxima;
    }

    /**
     * Devuelve el daño base del ataque del enemigo.
     * @return valor de ataque
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * Establece el daño base del ataque del enemigo.
     * @param ataque nuevo valor de ataque
     */
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    /**
     * Devuelve la reducción de daño del enemigo.
     * @return valor de defensa
     */
    public int getDefensa() {
        return defensa;
    }

    /**
     * Establece la reducción de daño del enemigo.
     * @param defensa nuevo valor de defensa
     */
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    /**
     * Devuelve el array de objetos de botín del enemigo.
     * @return array de objetos de loot
     */
    public Objeto[] getLoot() {
        return loot;
    }

    /**
     * Reemplaza el array de objetos de botín del enemigo.
     * @param loot nuevo array de objetos de loot
     */
    public void setLoot(Objeto[] loot) {
        this.loot = loot;
    }

    /**
     * Devuelve los puntos de vida máximos del enemigo.
     * @return vida máxima
     */
    public int getVidaMaxima() {
        return vidamaxima;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Compara este enemigo con otro por su valor de ataque de menor a mayor.
     * Permite ordenar enemigos por dificultad de combate.
     * @param enemigo enemigo con el que comparar
     * @return valor negativo si este enemigo ataca menos, 0 si igual, positivo si ataca más
     */
    @Override
    public int compareTo(Enemigo enemigo) {
        if(enemigo == null){
            throw new IllegalArgumentException("El enemigo a comparar no puede ser nulo");
        }
        return Integer.compare(this.ataque, enemigo.getAtaque());
    }
}
