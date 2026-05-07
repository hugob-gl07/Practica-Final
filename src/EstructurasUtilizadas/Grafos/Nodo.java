package EstructurasUtilizadas.Grafos;

/**
 * Representa un vértice del grafo.
 * Cada nodo tiene un identificador numérico único y un nombre que lo identifica semánticamente.
 */
public class Nodo implements Comparable<Nodo>{

    private int id;       // Id único que identifica al nodo dentro del grafo
    private String nombre; // Nombre del nodo (usado para búsquedas y comparaciones)

    /**
     * Crea un nodo con el identificador y el nombre indicados.
     * @param id     identificador numérico único del nodo
     * @param nombre nombre del nodo
     */
    public Nodo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Devuelve el identificador numérico del nodo.
     * @return id del nodo
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve el nombre del nodo.
     * @return nombre del nodo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del nodo.
     * @param nombre nuevo nombre del nodo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el identificador del nodo.
     * @param id nuevo id del nodo
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Compara este nodo con otro por su nombre en orden alfabético.
     * @param otro nodo con el que comparar
     * @return valor negativo si este va antes, 0 si son iguales, positivo si va después
     */
    @Override
    public int compareTo(Nodo otro){
        return this.nombre.compareTo(otro.nombre); // Delegamos la comparación en el orden natural de String
    }
}
