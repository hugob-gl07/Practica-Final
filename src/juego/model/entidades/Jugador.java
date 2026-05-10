package juego.model.entidades;

import juego.model.EstructurasUtilizadas.Grafos.Grafo;
import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;
import juego.model.EstructurasUtilizadas.ListaCircular.ListaCircular;
import juego.model.habitación.Celda;
import juego.model.habitación.GrafoHabitacion;
import juego.model.habitación.Habitacion;
import juego.model.habitación.TipoCelda;

/**
 * Representa al personaje controlado por el jugador.
 * Gestiona las estadísticas de combate, el inventario, la progresión de nivel
 * y el historial de acciones realizadas durante la partida.
 */
public class Jugador {
    protected int id;                                    // Identificador único del jugador
    protected String nombre;                             // Nombre del personaje
    protected int nivel;                                 // Nivel actual del jugador
    protected int experiencia;                           // Experiencia acumulada hacia el siguiente nivel
    protected int vidaActual;                            // Puntos de vida actuales (decrece al recibir daño)
    protected int vidaMaxima;                            // Puntos de vida máximos (aumenta al subir nivel)
    protected int ataque;                                // Daño base que inflige al atacar
    protected int defensa;                               // Reducción de daño que aplica al recibir golpes
    protected int mana;                                  // Puntos de maná disponibles para habilidades
    protected Celda posicion;                            // Celda de la matriz donde se encuentra el jugador
    protected Inventario inventario;                     // Colección de objetos del jugador
    protected ListaCircular<String> historialAcciones;   // Registro circular de las últimas acciones realizadas
    protected int rangoMovimiento;                       // Número de casillas que puede moverse por turno
    protected int habitacionActual;
    /**
     * Crea un jugador con el nombre y posición inicial indicados.
     * Inicializa todas las estadísticas con los valores base del nivel 1.
     * @param nombre   nombre del personaje
     * @param posicion celda inicial del jugador en la habitación
     */
    public Jugador(String nombre, Celda posicion, int habitacioninicial){
        this.id=1;
        this.nombre=nombre;
        this.nivel=1;
        this.experiencia=0;
        this.vidaActual=100;
        this.vidaMaxima=100;
        this.ataque=10;
        this.defensa=5;
        this.mana=50;
        this.posicion=posicion;
        this.inventario=new Inventario(10);         // Inventario con capacidad para 10 objetos
        this.historialAcciones=new ListaCircular<>(); // Lista circular para las acciones recientes
        this.rangoMovimiento=3;                     // El jugador puede moverse hasta 3 casillas por turno
        this.habitacionActual=habitacioninicial;
    }

    /**
     * Añade experiencia al jugador y sube de nivel si supera el umbral (100 × nivel actual).
     * @param exp cantidad de experiencia a añadir
     */
    public void addExperiencia(int exp){
        experiencia+=exp;
        if(experiencia>=100*nivel){
            subirNivel(); // Si superamos el umbral de experiencia subimos de nivel
        }
    }

    /**
     * Incrementa el nivel del jugador y mejora todas sus estadísticas base.
     * Reinicia la experiencia a 0 y restaura la vida al nuevo máximo.
     */
    public void subirNivel(){
        this.nivel++;
        experiencia=0;       // Reiniciamos la experiencia para el nuevo nivel
        vidaMaxima+=20;      // Aumentamos la vida máxima
        vidaActual=vidaMaxima; // Restauramos la vida al nuevo máximo
        ataque+=5;           // Aumentamos el daño base
        defensa+=3;          // Aumentamos la reducción de daño
        mana+=10;            // Aumentamos el maná disponible
        historialAcciones.insertar("Subistes al nivel"+ nivel); // Registramos el evento
    }

    /**
     * Aplica el daño recibido restando la defensa del jugador.
     * El daño real nunca es negativo y la vida nunca cae por debajo de 0.
     * @param daño cantidad de daño bruto recibido antes de aplicar la defensa
     */
    public void recibirDaño(int daño){
        int dañoreal= daño-defensa; // La defensa reduce el daño recibido
        if(dañoreal<0){
            dañoreal= 0; // La defensa no puede curar al jugador
        }
        vidaActual-=dañoreal;
        if(vidaActual<0){
            vidaActual=0; // La vida no puede ser negativa
        }
    }

    /**
     * Ataca al enemigo indicado aplicándole el daño de ataque del jugador y registra la acción.
     * @param enemigo enemigo objetivo del ataque
     * @return daño infligido al enemigo
     */
    public int atacar(Enemigo enemigo){
        double aleatorio = Math.random();  // Número aleatorio entre 0 y 1
        int dañoReal = Math.max(0, (int)(this.ataque * (aleatorio * 2) - enemigo.obtenerDefensa()));
        enemigo.recibirDaño(dañoReal);
        historialAcciones.insertar("Atacaste a " + enemigo.getNombre() + " con " + dañoReal + " de daño");
        return dañoReal;
    }

    /**
     * Mueve al jugador a la celda indicada si es transitable y registra la acción.
     * Si la celda no es transitable el jugador no se mueve.
     * @param nuevaCelda celda de destino del movimiento
     */
    public void mover(Celda nuevaCelda){
        if(nuevaCelda.esTransitable()){
            posicion=nuevaCelda; // Solo nos movemos si la celda es transitable
        }
        historialAcciones.insertar("Moviste a "+ nuevaCelda.toString()); // Registramos el movimiento
    }

    /**
     * Usa el objeto con el identificador indicado aplicando su efecto al jugador.
     * Actualmente gestiona pócimas de vida y de maná. Tras usarlo lo elimina del inventario.
     * @param idObjeto identificador del objeto a usar
     */
    public void usarObjeto(int idObjeto){
        Objeto objeto= inventario.getObjeto(idObjeto);
        if(objeto==null){
            return; // El objeto no existe en el inventario, no hacemos nada
        }
        switch(objeto.getTipo()){
            case POCIMA_VIDA -> vidaActual+=objeto.getValorEstadisticas("vida"); // Restauramos vida
            case POCIMA_MANA-> mana+=objeto.getValorEstadisticas("mana");        // Restauramos maná
        }
        inventario.removeObjeto(idObjeto);                           // Consumimos el objeto
        historialAcciones.insertar("Usaste "+ objeto.getNombre());    // Registramos el uso
    }

    public boolean cambiardeHabitacion(GrafoHabitacion grafo, int indiceDestino){
        if(posicion.getTipo()!= TipoCelda.SALIDA){
            return false;
        }
        Habitacion actual= grafo.obtenerHabitacion(habitacionActual);
        ListaSimplementeEnlazada<Habitacion> conexiones= grafo.obtenerHabitaciones(actual);
        if(conexiones==null){
            return false;
        }
        else if(indiceDestino<0 || indiceDestino>=conexiones.getSize()){
            return false;
        }
        Habitacion destino= conexiones.getAt(indiceDestino);
        Celda entrada= destino.buscarcelda(TipoCelda.ENTRADA);
        posicion=entrada;
        habitacionActual=destino.getId();
        return true;
    }

    /**
     * Devuelve el inventario del jugador.
     * @return inventario del jugador
     */
    public Inventario getInventario() {
        return inventario;
    }

    /**
     * Comprueba si el jugador sigue con vida.
     * @return true si la vida actual es mayor que 0, false si el jugador ha muerto
     */
    public boolean estaSalud(){
        return vidaActual>0; // El jugador está vivo mientras tenga puntos de vida
    }

    /**
     * Devuelve una representación en texto del historial de acciones del jugador.
     * @return cadena con todas las acciones registradas en la lista circular
     */
    public String getHistorialAcciones(){
        return historialAcciones.toString(); // Delegamos en el toString de la lista circular
    }

    /**
     * Devuelve el identificador único del jugador.
     * @return id del jugador
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del jugador.
     * @param id nuevo id del jugador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre del jugador.
     * @return nombre del personaje
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del jugador.
     * @param nombre nuevo nombre del personaje
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el nivel actual del jugador.
     * @return nivel actual
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Establece el nivel del jugador directamente.
     * @param nivel nuevo nivel
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * Devuelve la experiencia acumulada del jugador.
     * @return experiencia acumulada hacia el siguiente nivel
     */
    public int getExperiencia() {
        return experiencia;
    }

    /**
     * Establece la experiencia acumulada del jugador.
     * @param experiencia nueva experiencia acumulada
     */
    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    /**
     * Devuelve los puntos de vida actuales del jugador.
     * @return vida actual
     */
    public int getVidaActual() {
        return vidaActual;
    }

    /**
     * Establece los puntos de vida actuales del jugador.
     * @param vidaActual nueva vida actual
     */
    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    /**
     * Devuelve los puntos de vida máximos del jugador.
     * @return vida máxima
     */
    public int getVidaMaxima() {
        return vidaMaxima;
    }

    /**
     * Establece los puntos de vida máximos del jugador.
     * @param vidaMaxima nueva vida máxima
     */
    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    /**
     * Devuelve el daño base de ataque del jugador.
     * @return valor de ataque
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * Establece el daño base de ataque del jugador.
     * @param ataque nuevo valor de ataque
     */
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    /**
     * Devuelve la reducción de daño del jugador.
     * @return valor de defensa
     */
    public int getDefensa() {
        return defensa;
    }

    /**
     * Establece la reducción de daño del jugador.
     * @param defensa nuevo valor de defensa
     */
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    /**
     * Devuelve los puntos de maná actuales del jugador.
     * @return maná disponible
     */
    public int getMana() {
        return mana;
    }

    /**
     * Establece los puntos de maná del jugador.
     * @param mana nuevo valor de maná
     */
    public void setMana(int mana) {
        this.mana = mana;
    }

    /**
     * Devuelve la celda donde está situado el jugador.
     * @return celda actual del jugador
     */
    public Celda getPosicion() {
        return posicion;
    }

    /**
     * Establece la celda donde se sitúa el jugador.
     * @param posicion nueva celda del jugador
     */
    public void setPosicion(Celda posicion) {
        this.posicion = posicion;
    }

    /**
     * Devuelve el objeto de lista circular que almacena el historial de acciones.
     * @return lista circular del historial de acciones
     */
    public ListaCircular<String> getHistorialAccionesObj() {
        return historialAcciones;
    }

    /**
     * Devuelve el rango de movimiento del jugador en casillas por turno.
     * @return rango de movimiento
     */
    public int getRangoMovimiento() {
        return rangoMovimiento;
    }

    /**
     * Establece el rango de movimiento del jugador en casillas por turno.
     * @param rangoMovimiento nuevo rango de movimiento
     */
    public void setRangoMovimiento(int rangoMovimiento) {
        this.rangoMovimiento = rangoMovimiento;
    }
}
