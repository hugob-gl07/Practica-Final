package juego.model.EstructurasUtilizadas.LSE;
/**
 * Representa un nodo de una lista simplemente enlazada.
 * Contiene un dato y un puntero al elemento siguiente.
 */
public class ElementoSE<T> {
    protected T dato;                  // Dato almacenado en el nodo
    protected ElementoSE<T> siguiente; // Puntero al siguiente nodo
    /**
     * Constructor con dato dado.
     * El puntero siguiente se inicializa a null.
     */
    public ElementoSE(T dato){
        this.dato=dato;      // Almacenamos el dato
        this.siguiente=null; // El nodo no apunta a ningún siguiente
    }
    public T getDato() { return dato; }
    public ElementoSE<T> getSiguiente() { return siguiente; }
    public void setSiguiente(ElementoSE<T> siguiente) {
        this.siguiente = siguiente;
    }
}