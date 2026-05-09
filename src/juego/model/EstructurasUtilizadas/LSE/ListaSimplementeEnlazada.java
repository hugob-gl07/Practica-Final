package juego.model.EstructurasUtilizadas.LSE;
import juego.model.Exceptions.ListaElementoNoEncontradoException;
import juego.model.Exceptions.ListaIndiceInvalidoExceptions;
import juego.model.Exceptions.ListaVaciaExceptions;
/**
 * Representa una lista simplemente enlazada genérica y ordenable.
 * @param <T> tipo de dato que debe ser Comparable
 */
public class ListaSimplementeEnlazada<T extends Comparable<T>> {
    protected ElementoSE<T> primero;
    protected int tamaño;

    /** Constructor por defecto.*/
    public ListaSimplementeEnlazada(){
        this.primero=null; // El primer elemento no apunta a nada
        this.tamaño=0;     // La lista comienza vacía
    }

    /** Inserta un dato al final de la lista.*/
    public void add(T dato){
        ElementoSE<T> nuevo=new ElementoSE<>(dato); // Creamos un nuevo elemento con el dato
        if(primero==null){
            primero=nuevo; // Si la lista está vacía el nuevo elemento pasa a ser el primero
        }
        else{
            ElementoSE<T> actual=primero; // Empezamos desde el primer elemento
            while(actual.siguiente!=null){
                actual=actual.siguiente; // Avanzamos hasta llegar al último elemento
            }
            actual.siguiente=nuevo; // Conectamos el nuevo elemento después del último
        }
        tamaño++; // Incrementamos el tamaño de la lista
    }

    /** Inserta un dato al inicio de la lista.*/
    public T addFirst(T dato){
        ElementoSE<T> nuevo=new ElementoSE<>(dato); // Creamos un nuevo elemento con el dato
        nuevo.siguiente=primero; // El nuevo elemento apunta al que era el primero
        primero=nuevo;           // El nuevo elemento pasa a ser el primero
        tamaño++;                // Incrementamos el tamaño
        return nuevo.dato;
    }

    /** Busca y devuelve un dato por valor.*/
    public T get(T dato){
        ElementoSE<T> actual=primero; // Empezamos desde el primer elemento
        while(actual!=null){ // Recorremos hasta el final
            if(actual.dato.compareTo(dato)==0){ // Si el dato coincide lo devolvemos
                return actual.dato;
            }
            actual=actual.siguiente; // Avanzamos al siguiente elemento
        }
        return null; // Devolvemos null si no encontramos el dato en la lista
    }

    /** Busca y elimina un dato por valor.*/
    public T del(T dato){
        if(primero==null){
            throw new ListaVaciaExceptions("Error: La Lista Simplemente Enlazada está vacía, no podemos eliminar el dato buscado"); // Si la lista está vacía lanzamos el error
        }
        if(primero.dato.compareTo(dato)==0){
            // Si el dato a eliminar es el primero
            ElementoSE<T> actual=primero;  // Guardamos el primero para devolverlo
            primero=actual.siguiente;      // El segundo elemento pasa a ser el primero
            actual.siguiente=null;         // Desconectamos el elemento eliminado
            tamaño--;                      // Decrementamos el tamaño
            return actual.dato;
        }
        ElementoSE<T> anterior=primero;        // Empezamos con anterior apuntando al primero
        ElementoSE<T> actual=primero.siguiente; // Actual apunta al segundo elemento
        while(actual!=null){ // Recorremos hasta el final
            if(actual.dato.compareTo(dato)==0){
                // Si encontramos el dato hacemos un salto entre anterior y siguiente
                anterior.siguiente=actual.siguiente; // El anterior apunta al siguiente del actual
                actual.siguiente=null;               // Desconectamos el elemento eliminado
                tamaño--;                            // Decrementamos el tamaño
                return actual.dato;
            }
            anterior=actual;           // Anterior avanza al actual
            actual=actual.siguiente;   // Actual avanza al siguiente
        }
        throw new ListaElementoNoEncontradoException(); // Si no encontramos el elemento lanzamos el error
    }

    /** Devuelve true si la lista está vacía, false si no.*/
    public boolean isEmpty(){
        return tamaño==0; // Si el tamaño es 0 la lista está vacía
    }

    /** Devuelve el tamaño de la lista.*/
    public int getSize(){
        return tamaño; // Devolvemos el contador de elementos
    }

    /** Devuelve un iterador para recorrer la lista.*/
    public Iterador<T> getIterador(){
        return new IteradorLSE<T>(primero); // Creamos un iterador comenzando desde el primero
    }

    /** Devuelve el dato en una posición dada.*/
    public T getAt(int posición){
        if(posición>=tamaño || posición<0){
            throw new ListaIndiceInvalidoExceptions("Error: Estas intentado acceder a una posición fuera del rango "); // Si intentamos acceder fuera del rango lanzamos el error
        }
        ElementoSE<T> actual=primero; // Empezamos desde el primer elemento
        int contador=0;               // Inicializamos el contador
        while(contador<posición){
            actual=actual.siguiente; // Avanzamos al siguiente elemento
            contador++;              // Incrementamos el contador
        }
        return actual.dato; // Devolvemos el dato en la posición indicada
    }

    /** Inserta un dato en una posición dada.*/
    public T insertAt(int posición, T dato){
        if(posición>tamaño || posición<0){
            throw new ListaIndiceInvalidoExceptions("Error: Estas intentado acceder a una posición fuera del rango "); // Si intentamos acceder fuera del rango lanzamos el error
        }
        if(posición==0){
            // Insertamos al inicio
            ElementoSE<T> nuevo=new ElementoSE<>(dato);
            nuevo.siguiente=primero; // El nuevo apunta al que era el primero
            primero=nuevo;           // El nuevo pasa a ser el primero
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
            anterior=anterior.siguiente; // Avanzamos hasta el elemento anterior a la posición
            contador++;
        }
        ElementoSE<T> nuevo=new ElementoSE<>(dato);
        nuevo.siguiente=anterior.siguiente; // El nuevo apunta al elemento que estaba en esa posición
        anterior.siguiente=nuevo;           // El anterior apunta al nuevo elemento
        tamaño++;                           // Incrementamos el tamaño
        return dato;
    }

    /** Elimina y devuelve el dato en una posición dada.*/
    public T removeAt(int posición){
        if(posición<0 || posición>=tamaño){
            throw new ListaIndiceInvalidoExceptions("Error: Estas intentado acceder a una posición fuera del rango "); // Si intentamos acceder fuera del rango lanzamos el error
        }
        if(posición==0){
            // Eliminamos el primer elemento
            ElementoSE<T> actual=primero;  // Guardamos el primero para devolverlo
            primero=actual.siguiente;      // El segundo elemento pasa a ser el primero
            tamaño--;                      // Decrementamos el tamaño
            return actual.dato;
        }
        ElementoSE<T> anterior=primero; // Empezamos desde el primer elemento
        int contador=0;
        while(contador<posición-1){
            anterior=anterior.siguiente; // Avanzamos hasta el elemento anterior a la posición
            contador++;
        }
        ElementoSE<T> borrar=anterior.siguiente; // Guardamos el elemento a eliminar
        anterior.siguiente=borrar.siguiente;     // El anterior apunta al siguiente del eliminado
        tamaño--;                                // Decrementamos el tamaño
        return borrar.dato;
    }

    /**
     * Vaciar la lista eliminando todos sus elementos
     * Al poner primero a null, los nodos quedan sin referencia y el garbage collector los elimina automáticamente
     */
    public void clear(){
        primero=null;
        tamaño=0;
    }

    /** Devuelve una representación en texto de la lista.*/
    @Override
    public String toString(){
        if(tamaño==0) return "[]"; // Si la lista está vacía devolvemos []
        StringBuilder resultado=new StringBuilder("["); // Usamos StringBuilder para no crear muchos Strings intermedios
        ElementoSE<T> actual=primero; // Empezamos desde el primer elemento
        while(actual!=null){
            resultado.append(actual.dato); // Añadimos el dato al resultado
            actual=actual.siguiente;       // Avanzamos al siguiente elemento
            if(actual!=null){
                resultado.append(", "); // Añadimos separador si no es el último
            }
        }
        resultado.append("]");
        return resultado.toString();
    }
}