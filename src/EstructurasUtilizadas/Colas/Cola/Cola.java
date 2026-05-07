package EstructurasUtilizadas.Colas.Cola;
import Elemento.Elemento;
import Exceptions.ColaVaciaExceptions;
/**
 * Representa una cola (FIFO) genérica.
 * El primer elemento en entrar es el primero en salir.
 */
public class Cola<T>{
    private Elemento<T> cabeza; // Puntero al primer elemento de la cola
    private Elemento<T> cola;   // Puntero al último elemento de la cola

    /** Inserta un dato al final de la cola (FIFO).*/
    public void enqueue(T dato){
        Elemento<T> nuevo=new Elemento<>(dato); // Creamos un nuevo elemento con el dato
        if (cabeza==null){
            // Si la cola está vacía el nuevo elemento es tanto la cabeza como la cola
            this.cabeza=nuevo;
            this.cola=nuevo;
        }
        else{
            cola.setSiguiente(nuevo); // El último elemento apunta al nuevo
            cola=nuevo;              // El nuevo elemento pasa a ser el último
        }
    }
    /** Elimina y devuelve el primer dato de la cola (FIFO).*/
    public T dequeue(){
        if (cabeza==null){
            throw new ColaVaciaExceptions("Error: La cola esta vacía: No podemos eliminar el primer dato de la cola"); // Si la cola está vacía lanzamos el error
        }
        T dato= cabeza.getDato();        // Guardamos el dato de la cabeza para devolverlo
        cabeza=cabeza.getSiguiente();    // El segundo elemento pasa a ser la nueva cabeza
        if (cabeza==null){
            cola=null; // Si la cola queda vacía también vaciamos el puntero cola
        }
        return dato;
    }
    /** Devuelve el primer dato de la cola sin eliminarlo.*/
    public T peek(){
        if (cabeza==null){
            throw new ColaVaciaExceptions("Error: La cola esta vacía: No podemos devolver el primer dato de la cola"); // Si la cola está vacía lanzamos el error
        }
        return cabeza.getDato(); // Devolvemos el dato de la cabeza sin eliminarlo
    }
    /** Devuelve true si la cola está vacía, false si no.*/
    public boolean isEmpty(){
        return cabeza==null; // Si la cabeza es null la cola está vacía
    }
}