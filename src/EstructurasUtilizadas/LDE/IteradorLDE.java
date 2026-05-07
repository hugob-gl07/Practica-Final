package EstructurasUtilizadas.LDE;
/**
 * Representa un iterador para recorrer una lista doblemente enlazada.
 * Implementa la interfaz Iterador para permitir recorrer la listasin conocer su estructura interna.
 */
public class IteradorLDE<T> implements Iterador<T> {

    private ElementoDE<T> actual; // Elemento.Elemento actual en el recorrido

    /** Constructor con el elemento de inicio del recorrido.*/
    public IteradorLDE(ElementoDE<T> inicio) {
        this.actual = inicio; // Inicializamos el iterador en el primer elemento
    }

    /** Devuelve el dato del elemento actual y avanza al siguiente.*/
    @Override
    public T next() {
        T dato = actual.dato;        // Guardamos el dato del elemento actual
        actual = actual.siguiente;   // Avanzamos al siguiente elemento
        return dato;                 // Devolvemos el dato guardado
    }

    /** Devuelve true si quedan elementos por recorrer, false si no.*/
    @Override
    public boolean hasNext() {
        return actual != null; // Si actual es null hemos llegado al final de la lista
    }
}