package juego.model.habitacion;

/**
 * Par clave-valor que asocia el nombre del nodo en el grafo de habitaciones
 * con el objeto Habitacion correspondiente.
 * Se usa internamente por GrafoHabitacion para recuperar una habitación a partir de su nombre de nodo.
 */
class ParNombreHabitacion implements Comparable<ParNombreHabitacion> {
    private String nombreNodo;    // Nombre del nodo en el grafo (por ejemplo "HAB1")
    private Habitacion habitacion; // Habitación asociada a ese nodo

    /**
     * Crea un par que asocia el nombre del nodo con la habitación indicados.
     * @param nombreNodo nombre del nodo en el grafo
     * @param habitacion habitación representada por ese nodo
     */
    public ParNombreHabitacion(String nombreNodo, Habitacion habitacion) {
        this.nombreNodo = nombreNodo;
        this.habitacion = habitacion;
    }

    /**
     * Devuelve el nombre del nodo en el grafo.
     * @return nombre del nodo
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
     * Devuelve la habitación asociada a este nodo.
     * @return habitación del par
     */
    public Habitacion getHabitacion() {
        return habitacion;
    }

    /**
     * Establece la habitación asociada a este nodo.
     * @param habitacion nueva habitación a asociar
     */
    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    /**
     * Compara este par con otro por el nombre del nodo en orden alfabético.
     * @param o par con el que comparar
     * @return valor negativo si este va antes, 0 si son iguales, positivo si va después
     */
    @Override
    public int compareTo(ParNombreHabitacion o) {
        if(o == null){
            throw new IllegalArgumentException("El par a comparar no puede ser nulo");
        }
        return this.nombreNodo.compareTo(o.nombreNodo);
    }
}
