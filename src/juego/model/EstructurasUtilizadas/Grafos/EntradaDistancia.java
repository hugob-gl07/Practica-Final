package juego.model.EstructurasUtilizadas.Grafos;

/**
 * Registro auxiliar usado en algoritmos de camino mínimo (p.ej. Dijkstra).
 * Asocia un nodo con su distancia acumulada desde el origen y la arista padre
 * que permitió llegar a él, necesaria para reconstruir el camino.
 */
public class EntradaDistancia implements Comparable<EntradaDistancia> {
    private String nodo;    // Nombre del nodo al que pertenece esta entrada
    private int distancia;  // Distancia acumulada desde el nodo origen hasta este nodo
    private Arista padre;   // Arista que llevó a este nodo durante el recorrido (para reconstruir el camino)

    /**
     * Crea una entrada de distancia para el nodo y la distancia indicados.
     * La arista padre se asigna posteriormente si es necesario.
     * @param nodo      nombre del nodo
     * @param distancia distancia acumulada desde el origen
     */
    public EntradaDistancia(String nodo, int distancia){
        this.nodo = nodo;
        this.distancia = distancia; // Inicialmente no hay arista padre (null)
    }
    /**
     * Devuelve el nombre del nodo al que pertenece esta entrada.
     * @return nombre del nodo
     */
    public String getNodo() {
        return nodo;
    }

    /**
     * Establece el nombre del nodo de esta entrada.
     * @param nodo nuevo nombre del nodo
     */
    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    /**
     * Devuelve la distancia acumulada desde el origen hasta este nodo.
     * @return distancia acumulada (Integer.MAX_VALUE si aún no se ha alcanzado)
     */
    public int getDistancia() {
        return distancia;
    }

    /**
     * Actualiza la distancia acumulada cuando se encuentra un camino más corto.
     * @param distancia nueva distancia acumulada
     */
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    /**
     * Devuelve la arista que llevó a este nodo en el recorrido de Dijkstra.
     * Necesaria para reconstruir el camino mínimo al finalizar el algoritmo.
     * @return arista padre, o null si es el nodo origen o aún no ha sido alcanzado
     */
    public Arista getPadre() {
        return padre;
    }

    /**
     * Establece la arista que llevó a este nodo al encontrar un camino más corto.
     * @param padre arista que produce la distancia mínima actual
     */
    public void setPadre(Arista padre) {
        this.padre = padre;
    }
    /**
     * Compara esta entrada con otra por distancia de menor a mayor.
     * Se usa para ordenar los nodos en la cola de prioridad de Dijkstra.
     * @param otro entrada con la que comparar
     * @return valor negativo si esta tiene menor distancia, 0 si igual, positivo si mayor
     */
    public int compareTo(EntradaDistancia otro){
        return Integer.compare(this.distancia, otro.distancia); // Ordenamos por distancia ascendente
    }
}
