package juego.model.EstructurasUtilizadas.LDE;
/**
 * Interface que define el contrato para recorrer una estructura de datos
 * sin conocer su implementación interna.
 */
public interface Iterador<T> {
    /** Comprueba si quedan elementos por recorrer.*/
    public boolean hasNext();
    /**Devuelve el dato del elemento actual y avanza al siguiente.*/
    public T next();
}