package EstructurasUtilizadas.Arboles.ArbolB;
import Exceptions.ListaIndiceInvalidoExceptions;
import EstructurasUtilizadas.LSE.ElementoSE;
/**
 * Lista simplemente enlazada auxiliar usada internamente por el Árbol B
 * para almacenar los nodos hijos de cada NodoB.
 * Ofrece las operaciones básicas de acceso por índice, inserción y eliminación.
 * @param <T> tipo de dato almacenado en la lista
 */
public class ListaNodos<T> {
    protected ElementoSE<T> primero; // Primer elemento de la lista enlazada
    protected int tamaño;            // Número de elementos almacenados en la lista

    /**
     * Crea una lista vacía con el primer elemento a null y tamaño 0.
     */
    public ListaNodos(){
        this.primero=null; // El primer elemento no apunta a nada
        this.tamaño=0;     // La lista comienza vacía
    }

    /**
     * Inserta un dato al final de la lista.
     * @param dato valor a insertar
     */
    public void add(T dato){
        ElementoSE<T> nuevo=new ElementoSE<>(dato); // Creamos un nuevo elemento con el dato
        if(primero==null){
            primero=nuevo; // Si la lista está vacía el nuevo elemento pasa a ser el primero
        }
        else{
            ElementoSE<T> actual=primero; // Empezamos desde el primer elemento
            while(actual.getSiguiente()!=null){
                actual=actual.getSiguiente(); // Avanzamos hasta llegar al último elemento
            }
            actual.setSiguiente(nuevo); // Conectamos el nuevo elemento después del último
        }
        tamaño++; // Incrementamos el tamaño de la lista
    }

    /**
     * Indica si la lista está vacía.
     * @return true si no hay elementos, false en caso contrario
     */
    public boolean isEmpty(){
        return tamaño==0; // Si el tamaño es 0 la lista está vacía
    }

    /**
     * Devuelve el número de elementos almacenados en la lista.
     * @return número de elementos
     */
    public int getSize(){
        return tamaño; // Devolvemos el contador de elementos
    }

    /**
     * Devuelve el dato almacenado en la posición indicada.
     * @param posición índice del elemento a obtener (0 = primero)
     * @return dato en esa posición
     * @throws ListaIndiceInvalidoExceptions si la posición está fuera del rango
     */
    public T getAt(int posición){
        if(posición>=tamaño || posición<0){
            throw new ListaIndiceInvalidoExceptions("Error: Estas intentado acceder a una posición fuera del rango "); // Si intentamos acceder fuera del rango lanzamos el error
        }
        ElementoSE<T> actual=primero; // Empezamos desde el primer elemento
        int contador=0;               // Inicializamos el contador
        while(contador<posición){
            actual=actual.getSiguiente(); // Avanzamos al siguiente elemento
            contador++;                   // Incrementamos el contador
        }
        return actual.getDato(); // Devolvemos el dato en la posición indicada
    }

    /**
     * Elimina y devuelve el dato almacenado en la posición indicada.
     * @param posición índice del elemento a eliminar (0 = primero)
     * @return dato eliminado
     * @throws ListaIndiceInvalidoExceptions si la posición está fuera del rango
     */
    public T removeAt(int posición){
        if(posición<0 || posición>=tamaño){
            throw new ListaIndiceInvalidoExceptions("Error: Estas intentado acceder a una posición fuera del rango "); // Si intentamos acceder fuera del rango lanzamos el error
        }
        if(posición==0){
            // Eliminamos el primer elemento
            ElementoSE<T> actual=primero;          // Guardamos el primero para devolverlo
            primero=actual.getSiguiente();         // El segundo elemento pasa a ser el primero
            tamaño--;                              // Decrementamos el tamaño
            return actual.getDato();
        }
        ElementoSE<T> anterior=primero; // Empezamos desde el primer elemento
        int contador=0;
        while(contador<posición-1){
            anterior=anterior.getSiguiente(); // Avanzamos hasta el elemento anterior a la posición
            contador++;
        }
        ElementoSE<T> borrar=anterior.getSiguiente();    // Guardamos el elemento a eliminar
        anterior.setSiguiente(borrar.getSiguiente());    // El anterior apunta al siguiente del eliminado
        tamaño--;                                        // Decrementamos el tamaño
        return borrar.getDato();
    }

    /**
     * Inserta un dato en la posición indicada desplazando los elementos siguientes.
     * Si la posición es 0 inserta al inicio; si es igual al tamaño, inserta al final.
     * @param posición índice donde insertar el nuevo dato (0 = inicio, tamaño = final)
     * @param dato     valor a insertar
     * @return el dato insertado
     * @throws ListaIndiceInvalidoExceptions si la posición está fuera del rango
     */
    public T insertAt(int posición, T dato){
        if(posición>tamaño || posición<0){
            throw new ListaIndiceInvalidoExceptions("Error: Estas intentado acceder a una posición fuera del rango "); // Si intentamos acceder fuera del rango lanzamos el error
        }
        if(posición==0){
            // Insertamos al inicio
            ElementoSE<T> nuevo=new ElementoSE<>(dato);
            nuevo.setSiguiente(primero); // El nuevo apunta al que era el primero
            primero=nuevo;               // El nuevo pasa a ser el primero
            tamaño++;
            return dato;
        }
        if(posición==tamaño){
            add(dato); // Insertamos al final reutilizando add
            return dato;
        }
        ElementoSE<T> anterior=primero; // Empezamos desde el primer elemento
        int contador=0;
        while(contador<posición-1){
            anterior=anterior.getSiguiente(); // Avanzamos hasta el elemento anterior a la posición (BUG CORREGIDO: era anterior.setSiguiente(anterior))
            contador++;
        }
        ElementoSE<T> nuevo=new ElementoSE<>(dato);
        nuevo.setSiguiente(anterior.getSiguiente()); // El nuevo apunta al elemento que estaba en esa posición
        anterior.setSiguiente(nuevo);                // El anterior apunta al nuevo elemento
        tamaño++;                                    // Incrementamos el tamaño
        return dato;
    }
}