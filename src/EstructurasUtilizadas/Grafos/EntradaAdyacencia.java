package EstructurasUtilizadas.Grafos;
import EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;

/**
 * Fila de la lista de adyacencia del grafo.
 * Asocia un nodo con la lista de aristas dirigidas que salen de él.
 * El grafo mantiene una EntradaAdyacencia por cada nodo que contiene.
 */
public class EntradaAdyacencia implements Comparable<EntradaAdyacencia>{

    private Nodo nodo;                             // Nodo al que pertenece esta entrada
    private ListaSimplementeEnlazada<Arista> aristas; // Lista de aristas que salen del nodo

    /**
     * Crea una entrada para el nodo indicado con la lista de aristas vacía.
     * @param nodo nodo propietario de esta entrada
     */
    public EntradaAdyacencia(Nodo nodo){
        this.nodo = nodo;
        this.aristas = new ListaSimplementeEnlazada<>(); // La lista de aristas empieza vacía
    }

    /**
     * Devuelve el nodo propietario de esta entrada.
     * @return nodo de la entrada
     */
    public Nodo getNodo(){
        return this.nodo;
    }

    /**
     * Establece el nodo propietario de esta entrada.
     * @param nodo nuevo nodo
     */
    public void setNodo(Nodo nodo){
        this.nodo = nodo;
    }

    /**
     * Devuelve la lista de aristas que salen del nodo.
     * @return lista de aristas del nodo
     */
    public ListaSimplementeEnlazada<Arista> getAristas(){
        return this.aristas;
    }

    /**
     * Reemplaza la lista de aristas del nodo por la indicada.
     * @param aristas nueva lista de aristas
     */
    public void setAristas(ListaSimplementeEnlazada aristas){
        this.aristas = aristas;
    }

    /**
     * Compara esta entrada con otra por el nombre del nodo en orden alfabético.
     * @param o entrada con la que comparar
     * @return valor negativo si esta va antes, 0 si son iguales, positivo si va después
     */
    @Override
    public int compareTo(EntradaAdyacencia o) {
        return this.nodo.compareTo(o.nodo); // Delega la comparación en el compareTo del nodo
    }
}
