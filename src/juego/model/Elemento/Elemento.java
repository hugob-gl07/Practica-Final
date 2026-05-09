package juego.model.Elemento;

/**
 * Representa un nodo genérico usado por las estructuras de datos.
 * Contiene un dato y un puntero al elemento siguiente.
 */
public class Elemento<T> {
    private T dato;                  // Dato almacenado en el nodo
    private Elemento<T> siguiente;   // Puntero al siguiente nodo

    /**
     * Constructor con dato dado.
     * El puntero siguiente se inicializa a null.
    */
    public Elemento(T dato){
        this.dato=dato;      // Almacenamos el dato
        this.siguiente=null; // El nodo no apunta a ningún siguiente
    }
    /** Devuelve el dato almacenado en el nodo.*/
    public T getDato() {
        return dato;
    }
    /** Modifica el dato almacenado en el nodo.*/
    public void setDato(T dato){
        this.dato=dato; // Reemplazamos el dato actual por el nuevo
    }
    /** Devuelve el siguiente nodo.*/
    public Elemento<T> getSiguiente(){
        return siguiente;
    }
    /** Modifica el puntero al siguiente nodo.*/
    public void setSiguiente(Elemento<T> siguiente){
        this.siguiente=siguiente; // Apuntamos al nuevo nodo siguiente
    }
}