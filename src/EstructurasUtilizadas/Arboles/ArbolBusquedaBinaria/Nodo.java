package EstructurasUtilizadas.Arboles.ArbolBusquedaBinaria;
/**
 * Representa un nodo de un árbol binario de búsqueda.
 * Cada nodo almacena un dato y referencias a sus hijos izquierdo y derecho.
 * @param <T> tipo de dato almacenado, debe implementar Comparable
 */
public class Nodo<T extends Comparable<T>> {
    private T dato;            // Dato almacenado en el nodo
    private Nodo<T> izquierdo; // Hijo izquierdo
    private Nodo<T> derecho;   // Hijo derecho
    /**
     * Crea un nuevo nodo con el dato indicado y sin hijos.
     * @param dato el valor a almacenar en el nodo
     */
    public Nodo(T dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }
    /**
     * Establece el hijo izquierdo del nodo.
     * @param izquierdo nodo que será el hijo izquierdo
     */
    public void setIzquierdo(Nodo<T> izquierdo) {
        this.izquierdo = izquierdo;
    }
    /**
     * Establece el hijo derecho del nodo.
     * @param derecho nodo que será el hijo derecho
     */
    public void setDerecho(Nodo<T> derecho) {
        this.derecho = derecho;
    }
    /**
     * Devuelve el hijo izquierdo del nodo.
     * @return hijo izquierdo, o null si no existe
     */
    public Nodo<T> getIzquierdo() {
        return izquierdo;
    }
    /**
     * Devuelve el hijo derecho del nodo.
     * @return hijo derecho, o null si no existe
     */
    public Nodo<T> getDerecho() {
        return derecho;
    }
    /**
     * Devuelve el dato almacenado en el nodo.
     * @return el dato del nodo
     */
    public T getDato() {
        return dato;
    }
    /**
     * Establece el dato almacenado en el nodo.
     * @param dato el nuevo valor a almacenar
     */
    public void setDato(T dato) {
        this.dato = dato;
    }
}

