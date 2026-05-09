package juego.model.EstructurasUtilizadas.LDE;
import juego.model.Exceptions.ListaElementoNoEncontradoException;
import juego.model.Exceptions.ListaIndiceInvalidoExceptions;
import juego.model.Exceptions.ListaVaciaExceptions;
/**
 * Representa una lista doblemente enlazada genérica y ordenable.
 * @param <T> tipo de dato que debe ser Comparable
 */
public class ListaDoblementeEnlazada<T extends Comparable<T>> {
    protected ElementoDE<T> primero;
    protected ElementoDE<T> ultimo;
    protected int tamaño;

    /** Constructor por defecto.*/
    public ListaDoblementeEnlazada(){
        this.primero=null;
        this.ultimo=null;
        this.tamaño=0;
    }

    /** Inserta un dato al final de la lista.*/
    public T add(T dato){
        ElementoDE<T> nuevo=new ElementoDE<>(dato); // Creamos un nuevo elemento con el dato
        if(tamaño==0){ // Si la lista está vacía el nuevo elemento es tanto el primero como el último
            primero=nuevo;
            ultimo=nuevo;
        }
        else{
            ultimo.siguiente=nuevo; // El último apunta al nuevo elemento
            nuevo.anterior=ultimo;  // El nuevo elemento apunta hacia atrás al último
            ultimo=nuevo;           // El nuevo elemento pasa a ser el último
        }
        tamaño++; // Incrementamos el tamaño
        return dato;
    }

    /** Inserta un dato al inicio de la lista.*/
    public T addFirst(T dato){
        ElementoDE<T> nuevo=new ElementoDE<>(dato); // Creamos un nuevo elemento con el dato
        if(tamaño==0){ // Si la lista está vacía el nuevo elemento es tanto el primero como el último
            primero=nuevo;
            ultimo=nuevo;
        }
        else{
            nuevo.siguiente=primero;  // El nuevo elemento apunta al que era el primero
            primero.anterior=nuevo;   // El que era el primero apunta hacia atrás al nuevo
            primero=nuevo;            // El nuevo elemento pasa a ser el primero
        }
        tamaño++; // Incrementamos el tamaño
        return nuevo.dato;
    }

    /** Busca y devuelve un dato por valor.*/
    public T get(T dato){
        ElementoDE<T> actual=primero; // Empezamos desde el primer elemento
        while(actual!=null){ // Recorremos hasta el final
            if(actual.dato.compareTo(dato)==0){ // Si el dato coincide lo devolvemos
                return actual.dato;
            }
            actual=actual.siguiente; // Avanzamos al siguiente elemento
        }
        return null; // Devolvemos null si no encontramos el elemento
    }

    /** Busca y elimina un dato por valor.*/
    public T del(T dato){
        ElementoDE<T> actual=primero; // Empezamos desde el primer elemento
        while(actual!=null && actual.dato.compareTo(dato)!=0){
            actual=actual.siguiente; // Avanzamos hasta encontrar el dato
        }
        if(actual==null){
            throw new ListaElementoNoEncontradoException(); // Si no encontramos el elemento lanzamos el error
        }
        if(actual==primero){ // Si el elemento a eliminar es el primero
            primero=actual.siguiente; // El segundo elemento pasa a ser el primero
            if(primero!=null){
                primero.anterior=null; // El nuevo primero no tiene anterior
            }
            else{
                ultimo=null; // Si la lista queda vacía también vaciamos el último
            }
        }
        else if(actual==ultimo){ // Si el elemento a eliminar es el último
            ultimo=actual.anterior; // El penúltimo pasa a ser el último
            if(ultimo!=null){
                ultimo.siguiente=null; // El nuevo último no tiene siguiente
            }
            else{
                primero=null; // Si la lista queda vacía también vaciamos el primero
            }
        }
        else{ // Si el elemento está en medio hacemos un salto entre anterior y siguiente
            actual.anterior.siguiente=actual.siguiente;
            actual.siguiente.anterior=actual.anterior;
        }
        tamaño--; // Decrementamos el tamaño
        return actual.dato;
    }

    /**
     * Devuelve el dato en una posición dada.
     * Busca desde el extremo más cercano para optimizar O(n/2).
     */
    public T getAt(int posicion){
        if(posicion<0 || posicion>=tamaño){
            throw new ListaIndiceInvalidoExceptions("Error: Estas intentado acceder a una posición fuera del rango "); // Si intentamos acceder fuera del rango lanzamos el error
        }
        ElementoDE<T> actual=null;
        if(posicion<tamaño/2){ // Si la posición está en la primera mitad buscamos desde el principio
            actual=primero;
            int contador=0;
            while(contador<posicion){ actual=actual.siguiente; contador++; }
        }
        else{ // Si la posición está en la segunda mitad buscamos desde el final
            actual=ultimo;
            int contador=tamaño-1;
            while(contador>posicion){ actual=actual.anterior; contador--; }
        }
        return actual.dato;
    }

    /**
     * Inserta un dato en una posición dada.
     * Busca desde el extremo más cercano para optimizar O(n/2).
     */
    public T insertAt(int posicion, T dato){
        if(posicion<0 || posicion>tamaño){
            throw new ListaIndiceInvalidoExceptions("Error: Estas intentado acceder a una posición fuera del rango "); // Si intentamos acceder fuera del rango lanzamos el error
        }
        if(posicion==0) return addFirst(dato);   // Insertamos al inicio reutilizando addFirst
        if(posicion==tamaño) return add(dato);   // Insertamos al final reutilizando add
        ElementoDE<T> actual=null;
        if(posicion<tamaño/2){
            actual=primero;
            int contador=0;
            while(contador<posicion){ actual=actual.siguiente; contador++; }
        }
        else{
            actual=ultimo;
            int contador=tamaño-1;
            while(contador>posicion){ actual=actual.anterior; contador--; }
        }
        ElementoDE<T> nuevo=new ElementoDE<>(dato);
        nuevo.siguiente=actual;              // El nuevo apunta al elemento que estaba en esa posición
        nuevo.anterior=actual.anterior;      // El nuevo apunta hacia atrás al elemento anterior
        actual.anterior.siguiente=nuevo;     // El anterior apunta al nuevo elemento
        actual.anterior=nuevo;               // El elemento actual apunta hacia atrás al nuevo
        tamaño++;
        return nuevo.dato;
    }

    /**
     * Elimina y devuelve el dato en una posición dada.
     * Busca desde el extremo más cercano para optimizar O(n/2).
     */
    public T removeAt(int posicion){
        if(posicion<0 || posicion>=tamaño){
            throw new ListaIndiceInvalidoExceptions("Error: Estas intentado acceder a una posición fuera del rango "); // Si intentamos acceder fuera del rango lanzamos el error
        }
        ElementoDE<T> actual=null;
        if(posicion<tamaño/2){
            actual=primero;
            int contador=0;
            while(contador<posicion){ actual=actual.siguiente; contador++; }
        }
        else{
            actual=ultimo;
            int contador=tamaño-1;
            while(contador>posicion){ actual=actual.anterior; contador--; }
        }
        if(actual==primero){
            primero=primero.siguiente;
            if(primero!=null) primero.anterior=null;
            else              ultimo=null;
        }
        else if(actual==ultimo){
            ultimo=ultimo.anterior;
            if(ultimo!=null) ultimo.siguiente=null;
            else             primero=null;
        }
        else{
            actual.anterior.siguiente=actual.siguiente;
            actual.siguiente.anterior=actual.anterior;
        }
        tamaño--;
        return actual.dato;
    }

    /** Devuelve un iterador para recorrer la lista.*/
    public Iterador<T> getIterador(){
        return new IteradorLDE<T>(this.primero); // Creamos un iterador comenzando desde el primero
    }

    /** Devuelve true si la lista está vacía, false si no.*/
    public boolean isEmpty(){
        return primero==null; // Si el primero es null la lista está vacía
    }

    /** Devuelve el tamaño de la lista.*/
    public int getSize(){
        return tamaño; // Devolvemos el contador de elementos
    }

    /** Devuelve el primer dato de la lista sin eliminarlo.*/
    public T getFirst(){
        if(primero==null){
            throw new ListaVaciaExceptions("Error: La Lista Doblemente Enlazada está vacía, no podemos devolver el primer elemento"); // Si la lista está vacía lanzamos el error
        }
        return primero.dato; // Devolvemos el dato del primer elemento directamente
    }

    /** Devuelve el último dato de la lista sin eliminarlo.*/
    public T getLast(){
        if(primero==null){
            throw new ListaVaciaExceptions("Error: La Lista Doblemente Enlazada está vacía, no podemos devolver el último elemento"); // Si la lista está vacía lanzamos el error
        }
        return ultimo.dato; // Devolvemos el dato del último elemento directamente
    }

    /** Vacía la lista eliminando todos los elementos.*/
    public T clear(){
        primero=null; // El primer elemento ya no apunta a nada
        ultimo=null;  // El último elemento ya no apunta a nada
        tamaño=0;     // Reiniciamos el contador
        return null;
    }

    /** Elimina y devuelve el primer dato de la lista.*/
    public T removeFirst(){
        if(primero==null){
            throw new ListaVaciaExceptions("..."); // Si la lista está vacía lanzamos el error
        }
        ElementoDE<T> actual=primero;    // Guardamos el primero para devolverlo
        primero=primero.siguiente;       // El segundo elemento pasa a ser el primero
        if(primero!=null){
            primero.anterior=null;       // El nuevo primero no tiene anterior
        }
        else{
            ultimo=null;                 // Si la lista queda vacía vaciamos el último
        }
        actual.siguiente=null;           // Desconectamos el elemento eliminado
        tamaño--;
        return actual.dato;
    }

    /** Elimina y devuelve el último dato de la lista.*/
    public T removeLast(){
        if(primero==null){
            throw new ListaVaciaExceptions("Error: La Lista Doblemente Enlazada está vacía, no podemos eliminar el último elemento"); // Si la lista está vacía lanzamos el error
        }
        if(primero.siguiente==null){
            // Si solo hay un elemento lo eliminamos y vaciamos la lista
            ElementoDE<T> actual=primero;
            tamaño--;
            primero=null;
            ultimo=null;
            return actual.dato;
        }
        T dato=ultimo.dato;      // Guardamos el dato del último para devolverlo
        ultimo=ultimo.anterior;  // El penúltimo pasa a ser el último
        ultimo.siguiente=null;   // El nuevo último no tiene siguiente
        tamaño--;
        return dato;
    }

    /** Devuelve una representación en texto de la lista.*/
    @Override
    public String toString(){
        if(tamaño==0) return "[]"; // Si la lista está vacía devolvemos []
        StringBuilder resultado=new StringBuilder("["); // Usamos StringBuilder para no crear muchos Strings intermedios
        ElementoDE<T> actual=primero; // Empezamos desde el primer elemento
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
