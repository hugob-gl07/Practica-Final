package EstructurasUtilizadas.Arboles.ArbolB;
import EstructurasUtilizadas.LSE.LSEOrdenada;
import EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;

/**
 * Nodo de un Árbol B.
 * Cada nodo almacena una lista ordenada de claves y una lista de punteros a nodos hijos.
 * Un nodo hoja tiene la lista de hijos vacía.
 * @param <T> tipo de dato almacenado, debe implementar Comparable
 */
public class NodoB<T extends Comparable<T>> implements Comparable<NodoB<T>> {
    protected LSEOrdenada<T> datos;                    // Lista ordenada de claves almacenadas en el nodo
    protected ListaSimplementeEnlazada<NodoB<T>> hijos; // Punteros a los nodos hijos

    /**
     * Crea un nuevo NodoB vacío, sin datos ni hijos.
     */
    public NodoB(){
        datos = new LSEOrdenada<>();                       // Inicializamos la lista de datos vacía
        hijos = new ListaSimplementeEnlazada<NodoB<T>>(); // Inicializamos la lista de hijos vacía
    }

    /**
     * Devuelve la lista ordenada de claves almacenadas en este nodo.
     * @return lista de datos del nodo
     */
    public LSEOrdenada<T> getDatos() {
        return datos;
    }

    /**
     * Devuelve la lista de nodos hijos de este nodo.
     * @return lista de hijos del nodo
     */
    public ListaSimplementeEnlazada<NodoB<T>> getHijos() {
        return hijos;
    }

    /**
     * Compara este nodo con otro usando el primer dato de cada uno.
     * Si alguno de los nodos está vacío, se considera igual (devuelve 0).
     * @param otro nodo con el que comparar
     * @return valor negativo si este nodo es menor, 0 si son iguales, positivo si es mayor
     */
    @Override
    public int compareTo(NodoB<T> otro) {
        int respuesta=0;
        if (!this.datos.isEmpty() && !otro.datos.isEmpty()) {
            respuesta=this.datos.getAt(0).compareTo(otro.datos.getAt(0)); // Comparamos por el primer dato de cada nodo
        }
        return respuesta;
    }
}
