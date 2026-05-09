package juego.model.EstructurasUtilizadas.LSE;
/**
 * Representa un iterador para recorrer una lista simplemente enlazada.
 * Implementa la interfaz Iterador para permitir recorrer la lista sin conocer su estructura interna.
 */
public class IteradorLSE<T> implements Iterador<T> {

    private ElementoSE<T> actual; // juego.model.Elemento.juego.model.Elemento actual en el recorrido
    /** Constructor con el elemento de inicio del recorrido.*/
    public IteradorLSE(ElementoSE<T> inicio){
        this.actual=inicio; // Inicializamos el iterador en el primer elemento
    }
    /** Devuelve el dato del elemento actual y avanza al siguiente.*/
    @Override
    public T next() {
        T dato= actual.dato;       // Guardamos el dato del elemento actual
        actual=actual.siguiente;   // Avanzamos al siguiente elemento
        return dato;               // Devolvemos el dato guardado
    }
    /** Devuelve true si quedan elementos por recorrer, false si no.*/
    @Override
    public boolean hasNext() {
        return actual != null; // Si actual es null hemos llegado al final de la lista
    }
}