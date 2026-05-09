package juego.model.EstructurasUtilizadas.ListaCircular;
import juego.model.Elemento.Elemento;
import juego.model.Exceptions.ListaCircularExceptions;
/**
 * Representa una lista circular genérica.
 * El último elemento apunta de vuelta al primero formando un ciclo.
 */
public class ListaCircular<T extends Comparable<T>> {

    private Elemento<T> cabeza; // Puntero al primer elemento de la lista
    private int tamaño;         // Contador de elementos en la lista

    /** Inserta un dato al final de la lista circular.*/
    public void insertar(T dato){
        Elemento<T> nuevo=new Elemento<>(dato); // Creamos un nuevo elemento con el dato
        if(cabeza==null){
            // Si la lista está vacía el nuevo elemento apunta a sí mismo cerrando el ciclo
            cabeza=nuevo;
            nuevo.setSiguiente(nuevo);
        }
        else{
            Elemento<T> actual=cabeza; // Empezamos desde la cabeza
            while(actual.getSiguiente()!=cabeza){
                actual=actual.getSiguiente(); // Avanzamos hasta llegar al último elemento
            }
            actual.setSiguiente(nuevo);  // El último apunta al nuevo elemento
            nuevo.setSiguiente(cabeza);  // El nuevo apunta a la cabeza cerrando el ciclo
        }
        tamaño++; // Incrementamos el contador
    }

    /** Elimina y devuelve el primer dato de la lista circular.*/
    public T eliminar(){
        if(cabeza==null){
            throw new ListaCircularExceptions("Error: La lista circular esta vacía, no podemos eliminar ni devolver el dato"); // Si la lista está vacía lanzamos el error
        }
        T dato=cabeza.getDato(); // Guardamos el dato de la cabeza para devolverlo
        if(cabeza.getSiguiente()==cabeza){
            // Si solo hay un elemento vaciamos la lista
            cabeza=null;
        }
        else{
            Elemento<T> ultimo=cabeza; // Empezamos desde la cabeza
            while(ultimo.getSiguiente()!=cabeza){
                ultimo=ultimo.getSiguiente(); // Avanzamos hasta llegar al último elemento
            }
            cabeza=cabeza.getSiguiente(); // El segundo elemento pasa a ser la nueva cabeza
            ultimo.setSiguiente(cabeza);  // El último apunta a la nueva cabeza cerrando el ciclo
        }
        tamaño--; // Decrementamos el contador
        return dato;
    }

    /** Devuelve true si la lista está vacía, false si no.*/
    public boolean isEmpty(){
        return cabeza==null; // Si la cabeza es null la lista está vacía
    }

    /** Devuelve el número de elementos que hay en la lista.*/
    public int size(){
        return tamaño; // Devolvemos el contador de elementos
    }

    /** Devuelve una representación en texto de la lista circular desde la cabeza.*/
    @Override
    public String toString(){
        if(cabeza==null) return "[]"; // Si la lista está vacía devolvemos []
        StringBuilder resultado=new StringBuilder("["); // Usamos StringBuilder para no crear muchos Strings intermedios
        Elemento<T> actual=cabeza; // Empezamos desde la cabeza
        do{
            resultado.append(actual.getDato()); // Añadimos el dato al resultado
            actual=actual.getSiguiente();       // Avanzamos al siguiente elemento
            if(actual!=cabeza){
                resultado.append(", "); // Añadimos separador si no hemos vuelto a la cabeza
            }
        }while(actual!=cabeza); // Paramos cuando volvemos a la cabeza
        resultado.append("]");
        return resultado.toString();
    }
}