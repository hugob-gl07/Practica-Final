package EstructurasUtilizadas.Colas.ColaPrioridad;
/**
 * Representa una cola de prioridad mínima genérica.
 * Los elementos se ordenan internamente usando una LDEOrdenada, de forma que el elemento con menor prioridad siempre está al inicio.
 */
import Exceptions.ColaPrioridadVaciaExceptions;
import EstructurasUtilizadas.LDE.LDEOrdenada;
public class ColaPrioridadMin <T extends Comparable<T>> {
    private LDEOrdenada<T> lista=new LDEOrdenada<>(); // Lista ordenada interna que gestiona la prioridad
    /** Inserta un dato en la cola manteniendo el orden por prioridad.*/
    public void enqueue(T dato){
        lista.add(dato); // Delegamos en la LDEOrdenada para insertar en orden
    }
    /** Elimina y devuelve el elemento con menor prioridad.*/
    public T dequeue(){
        if(lista.isEmpty()){
            throw new ColaPrioridadVaciaExceptions("Error: la cola de prioridad está vacia, no podemos eliminar ni devolver el elemento con menor prioridad ");// Si la cola esta vacía, lanzamos el error
        }
        return lista.removeFirst(); // El elemento de menor prioridad está al principio
    }
    /** Devuelve el elemento con mayor prioridad sin eliminarlo.*/
    public T peekMax(){
        if(lista.isEmpty()){
            throw new ColaPrioridadVaciaExceptions("Error: la cola de prioridad está vacia, no podemos devolver el elemento con mayor prioridad ");// Si la cola esta vacía, lanzamos el error
        }
        return lista.getLast(); // El elemento de mayor prioridad está al final
    }
    /** Devuelve true si la cola está vacía, false si no.*/
    public boolean isEmpty(){
        return lista.isEmpty(); // Delegamos en la LDEOrdenada
    }
    /** Devuelve el número de elementos de la cola.*/
    public int size(){
        return lista.getSize(); // Delegamos en la LDEOrdenada
    }
    /** Vacía la cola eliminando todos los elementos.*/
    public void clear(){
        lista.clear(); // Delegamos en la LDEOrdenada
    }

    /** Devuelve el elemento con menor prioridad sin eliminarlo.*/
    public T peekMin(){
        if(lista.isEmpty()){
            throw new ColaPrioridadVaciaExceptions("Error: la cola de prioridad está vacia, no podemos devolver el elemento con menor prioridad ");// Si la cola esta vacía, lanzamos el error
        }
        return lista.getFirst(); // El elemento de menor prioridad está al inicio
    }
    /** Devuelve una representación en texto de la cola.*/
    public String toString(){
        return lista.toString(); // Delegamos en la LDEOrdenada
    }
    /** Comprueba si un dato existe en la cola.*/
    public boolean contains(T dato){
        return lista.get(dato)!=null; // Buscamos el dato en la LDEOrdenada
    }
    /**
     * Reemplaza un dato existente por uno nuevo manteniendo el orden.
     * Si el dato viejo no existe devuelve null.
     */
    public T replace(T datoviejo, T datonuevo){
        if(lista.get(datoviejo)!=null){
            lista.del(datoviejo);    // Eliminamos el dato viejo
            lista.add(datonuevo);    // Insertamos el nuevo dato en su posición correcta
            return lista.get(datonuevo); // Devolvemos el nuevo dato insertado
        }
        return null; // Si el dato viejo no existe devolvemos null
    }
}
