package EstructurasUtilizadas.LDE;
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
}