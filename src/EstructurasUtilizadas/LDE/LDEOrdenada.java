package EstructurasUtilizadas.LDE;
/**
 * Representa una lista doblemente enlazada que mantiene los elementos ordenados.*/
public class LDEOrdenada<T extends Comparable<T>> extends ListaDoblementeEnlazada<T> {

    /**
     * Inserta un dato en la posición correcta para mantener el orden ascendente.
     * Sobrescribe el metodo add de ListaDoblementeEnlazada.
     */
    @Override
    public T add(T dato) {
        ElementoDE<T> nuevo = new ElementoDE<>(dato); // Creamos un nuevo elemento con el dato
        // La lista está vacía, el nuevo elemento es primero y último
        if (isEmpty()) {
            primero = nuevo;
            ultimo = nuevo;
        }
        // El dato es menor o igual al primero, insertamos al inicio
        else if (nuevo.dato.compareTo(primero.dato) <= 0) {
            nuevo.siguiente = primero;   // El nuevo apunta al que era el primero
            primero.anterior = nuevo;    // El que era el primero apunta hacia atrás al nuevo
            primero = nuevo;             // El nuevo pasa a ser el primero
        }
        // El dato es mayor o igual al último, insertamos al final
        else if (nuevo.dato.compareTo(ultimo.dato) >= 0) {
            nuevo.anterior = ultimo;     // El nuevo apunta hacia atrás al que era el último
            ultimo.siguiente = nuevo;    // El que era el último apunta al nuevo
            ultimo = nuevo;              // El nuevo pasa a ser el último
        }
        // El dato va en una posición intermedia
        else {
            ElementoDE<T> actual = primero; // Empezamos desde el primer elemento

            // Avanzamos hasta encontrar el primer elemento mayor al dato nuevo
            while (actual != null && actual.dato.compareTo(dato) < 0) {
                actual = actual.siguiente; // Avanzamos al siguiente elemento
            }

            // Enlazamos el nuevo elemento justo antes del elemento actual
            nuevo.siguiente = actual;          // El nuevo apunta al elemento actual
            nuevo.anterior = actual.anterior;  // El nuevo apunta hacia atrás al anterior del actual

            // Actualizamos los punteros de los elementos vecinos
            actual.anterior.siguiente = nuevo; // El anterior al actual apunta al nuevo
            actual.anterior = nuevo;           // El actual apunta hacia atrás al nuevo
        }

        tamaño++; // Incrementamos el contador heredado de ListaDoblementeEnlazada
        return dato; // Devolvemos el dato
    }
}