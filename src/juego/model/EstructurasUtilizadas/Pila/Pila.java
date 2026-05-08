package juego.model.EstructurasUtilizadas.Pila;
import juego.model.Elemento.Elemento;
import juego.model.Exceptions.PilaVaciaExceptions;
/**
 * Representa una pila (LIFO) genérica.
 * El último elemento en entrar es el primero en salir.
 */
public class Pila<T extends Comparable<T>> {

    private Elemento<T> cabeza; // Puntero al elemento en la cima de la pila
    private int tamaño;         // Contador de elementos en la pila

    /** Inserta un dato en la cima de la pila (LIFO).*/
    public void push(T dato){
        Elemento<T> nuevo=new Elemento<>(dato); // Creamos un nuevo elemento con el dato
        nuevo.setSiguiente(cabeza); // El nuevo elemento apunta al que era la cima
        cabeza=nuevo;               // El nuevo elemento pasa a ser la nueva cima
        tamaño++;                   // Incrementamos el contador
    }

    /** Elimina y devuelve el dato de la cima de la pila (LIFO).*/
    public T pop(){
        if(cabeza==null){
            throw new PilaVaciaExceptions("Error: La pila esta vacía, no podemos eliminar ningún dato"); // Si la pila está vacía lanzamos el error
        }
        T dato=cabeza.getDato();        // Guardamos el dato de la cima para devolverlo
        cabeza=cabeza.getSiguiente();   // El siguiente elemento pasa a ser la nueva cima
        tamaño--;                       // Decrementamos el contador
        return dato;
    }

    /** Devuelve el dato de la cima de la pila sin eliminarlo.*/
    public T peek(){
        if(cabeza==null){
            throw new PilaVaciaExceptions("Error: La pila esta vacía, por lo tanto no puedo devolver el dato cima"); // Si la pila está vacía lanzamos el error
        }
        return cabeza.getDato(); // Devolvemos el dato de la cima sin eliminarlo
    }

    /** Devuelve true si la pila está vacía, false si no.*/
    public boolean isEmpty(){
        return cabeza==null; // Si la cabeza es null la pila está vacía
    }

    /** Devuelve el número de elementos que hay en la pila.*/
    public int size(){
        return tamaño; // Devolvemos el contador de elementos
    }

    /** Vacía la pila eliminando todos sus elementos.*/
    public void clear(){
        cabeza=null;  // El primer elemento ya no apunta a nada, el resto lo libera el GC
        tamaño=0;     // Reiniciamos el contador
    }

    /** Devuelve una representación en texto de la pila desde la cima hasta la base.*/
    @Override
    public String toString(){
        if(cabeza==null) return "[]"; // Si la pila está vacía devolvemos []
        StringBuilder resultado=new StringBuilder("["); // Usamos StringBuilder para no crear muchos Strings intermedios
        Elemento<T> actual=cabeza; // Empezamos desde la cima
        while(actual!=null){
            resultado.append(actual.getDato()); // Añadimos el dato al resultado
            actual=actual.getSiguiente();       // Avanzamos al siguiente elemento
            if(actual!=null){
                resultado.append(", "); // Añadimos separador si no es el último
            }
        }
        resultado.append("]");
        return resultado.toString();
    }
}