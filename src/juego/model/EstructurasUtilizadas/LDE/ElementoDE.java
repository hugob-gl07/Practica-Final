package juego.model.EstructurasUtilizadas.LDE;
/**
 * Representa un nodo de una lista doblemente enlazada.
 * Contiene un dato y punteros al elemento anterior y siguiente.
 */
public class ElementoDE<T> {

    protected T dato;                  // Dato almacenado en el nodo
    protected ElementoDE<T> siguiente; // Puntero al siguiente nodo
    protected ElementoDE<T> anterior;  // Puntero al nodo anterior

    /**
     * Constructor con dato dado.
     * Los punteros anterior y siguiente se inicializan a null.
     */
    public ElementoDE(T dato){
        this.dato=dato;        // Almacenamos el dato
        this.siguiente=null;   // El nodo no apunta a ningún siguiente
        this.anterior=null;    // El nodo no apunta a ningún anterior
    }

    /** Devuelve el dato almacenado en el nodo.*/
    public T getDato() { return dato; }

    /** Modifica el dato almacenado en el nodo.*/
    public void setDato(T dato) { this.dato = dato; }

    /** Devuelve el siguiente nodo.*/
    public ElementoDE<T> getSiguiente() { return siguiente; }

    /** Modifica el puntero al siguiente nodo.*/
    public void setSiguiente(ElementoDE<T> siguiente) { this.siguiente = siguiente; }

    /** Devuelve el nodo anterior.*/
    public ElementoDE<T> getAnterior() { return anterior; }

    /** Modifica el puntero al nodo anterior.*/
    public void setAnterior(ElementoDE<T> anterior) { this.anterior = anterior; }
}