package juego.model.habitacion;

/**
 * Par clave-valor que asocia el identificador numérico de una habitación
 * con el nombre del nodo que la representa en el grafo de habitaciones.
 * Se usa internamente por GrafoHabitacion para convertir ids en nombres de nodo.
 */
class ParIdNombre implements Comparable<ParIdNombre> {
    private int idHabitacion;  // Identificador numérico de la habitación
    private String nombreNodo; // Nombre del nodo en el grafo (por ejemplo "HAB1")

    /**
     * Crea un par que asocia el id de habitación con el nombre de nodo indicados.
     * @param id     identificador numérico de la habitación
     * @param nombre nombre del nodo que representa la habitación en el grafo
     */
    public ParIdNombre(int id, String nombre) {
        this.idHabitacion = id;
        this.nombreNodo = nombre;
    }

    /**
     * Devuelve el identificador numérico de la habitación.
     * @return id de la habitación
     */
    public int getIdHabitacion() {
        return idHabitacion;
    }

    /**
     * Establece el identificador numérico de la habitación.
     * @param idHabitacion nuevo id de la habitación
     */
    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    /**
     * Devuelve el nombre del nodo asociado a esta habitación en el grafo.
     * @return nombre del nodo en el grafo
     */
    public String getNombreNodo() {
        return nombreNodo;
    }

    /**
     * Establece el nombre del nodo en el grafo.
     * @param nombreNodo nuevo nombre del nodo
     */
    public void setNombreNodo(String nombreNodo) {
        this.nombreNodo = nombreNodo;
    }

    /**
     * Compara este par con otro por el identificador numérico de la habitación.
     * @param o par con el que comparar
     * @return valor negativo si este va antes, 0 si son iguales, positivo si va después
     */
    @Override
    public int compareTo(ParIdNombre o) {
        if(o == null){
            throw new IllegalArgumentException("El par a comparar no puede ser nulo");
        }
        return Integer.compare(this.idHabitacion, o.idHabitacion);
    }
}
