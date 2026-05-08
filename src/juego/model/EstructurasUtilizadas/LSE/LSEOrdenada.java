package juego.model.EstructurasUtilizadas.LSE;
/**
 * Representa una lista simplemente enlazada que mantiene los elementos ordenados.
 * Extiende ListaSimplementeEnlazada sobrescribiendo el metodo add para insertar en orden.
 */
public class LSEOrdenada<T extends Comparable<T>> extends ListaSimplementeEnlazada<T> {

    /**
     * Inserta un dato en la posición correcta para mantener el orden ascendente.
     * Sobrescribe el metodo add de ListaSimplementeEnlazada.*/
    @Override
    public void add(T dato) {
        ElementoSE<T> nuevo= new ElementoSE<>(dato); // Creamos un nuevo nodo con el dato

        if(primero==null || dato.compareTo(primero.dato)<0){
            // Caso 1: La lista está vacía o el dato es menor al primero, insertamos al inicio
            nuevo.siguiente=primero; // El nuevo apunta al que era el primero
            primero=nuevo;           // El nuevo pasa a ser el primero
            tamaño++;                // Incrementamos el tamaño
        }
        else {
            // Caso 2: Buscamos la posición correcta en la lista
            ElementoSE<T>actual= primero; // Empezamos desde el primer elemento

            // Avanzamos mientras haya siguiente y el siguiente sea menor al dato nuevo
            while (actual.siguiente!=null && actual.siguiente.dato.compareTo(dato)<0){
                actual=actual.siguiente; // Avanzamos al siguiente elemento
            }

            // Insertamos el nuevo elemento entre actual y actual.siguiente
            nuevo.siguiente=actual.siguiente; // El nuevo apunta al siguiente del actual
            actual.siguiente=nuevo;           // El actual apunta al nuevo elemento
            tamaño++;                         // Incrementamos el tamaño
        }
    }
}