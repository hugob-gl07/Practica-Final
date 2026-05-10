package juego.model.entidades;

import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;

/**
 * Gestiona la colección de objetos que posee el jugador.
 * Impone un límite de capacidad máxima y lleva el control del peso total acumulado.
 * Los objetos se almacenan internamente en una lista simplemente enlazada.
 */
public class Inventario {
    protected int capacidadesmaxima;                      // Número máximo de objetos que puede almacenar
    protected ListaSimplementeEnlazada<Objeto> objetos;   // Lista de objetos almacenados
    protected float pesoTotal;                            // Suma del peso de todos los objetos

    /**
     * Crea un inventario vacío con la capacidad máxima indicada.
     * @param capacidad número máximo de objetos que puede almacenar
     */
    public Inventario(int capacidad) {
        this.capacidadesmaxima = capacidad;
        this.objetos = new ListaSimplementeEnlazada(); // La lista empieza vacía
        this.pesoTotal = 0;                            // Sin objetos, el peso es 0
    }

    /**
     * Intenta añadir un objeto al inventario.
     * No añade el objeto si el inventario está lleno o si el objeto es null.
     * @param objeto objeto a añadir
     * @return true si se añadió correctamente, false si el inventario está lleno o el objeto es null
     */
    public boolean addObjeto(Objeto objeto) {
        if(estaLleno()) {
            return false; // No podemos añadir más objetos si el inventario está lleno
        }
        if(objeto == null) {
            return false; // No añadimos objetos nulos
        }
        objetos.add(objeto); // Añadimos el objeto al final de la lista
        return true;
    }

    /**
     * Busca y elimina del inventario el objeto con el identificador indicado.
     * También descuenta su peso del total.
     * @param id identificador del objeto a eliminar
     * @return el objeto eliminado, o null si no se encontró
     */
    public Objeto removeObjeto(int id) {
        Objeto objeto = null;
        boolean encontrado = false;
        for(int i = 0; i < objetos.getSize() && !encontrado; i++){
            if(objetos.getAt(i).getId() == id) {
                objeto = objetos.getAt(i);            // Guardamos el objeto para devolverlo
                objetos.removeAt(i);                  // Lo eliminamos de la lista
                pesoTotal -= objeto.getPeso();         // Actualizamos el peso total
                encontrado = true;                    // Terminamos la búsqueda
            }
        }
        return objeto; // null si no encontramos ningún objeto con ese id
    }

    /**
     * Busca y devuelve el objeto con el identificador indicado sin eliminarlo.
     * @param id identificador del objeto a buscar
     * @return el objeto encontrado, o null si no existe en el inventario
     */
    public Objeto getObjeto(int id) {
        for(int i=0;i<objetos.getSize();i++) {
            if(objetos.getAt(i).getId()==id) {
                return objetos.getAt(i); // Objeto encontrado: lo devolvemos
            }
        }
        return null; // No hay ningún objeto con ese id en el inventario
    }

    /**
     * Devuelve el número de objetos almacenados actualmente en el inventario.
     * @return número de objetos
     */
    public int getTamaño() {
        return objetos.getSize(); // Delegamos en la lista
    }

    /**
     * Comprueba si el inventario ha alcanzado su capacidad máxima.
     * @return true si el inventario está lleno, false si aún hay espacio
     */
    public boolean estaLleno(){
        return objetos.getSize()>=capacidadesmaxima; // Comparamos con el límite de capacidad
    }

    /**
     * Devuelve una representación en texto de todos los objetos del inventario.
     * Cada objeto ocupa una línea separada.
     * @return cadena con la lista de objetos
     */
    public String listaObjetos(){
        String lista = "";
        for(int i=0;i<objetos.getSize();i++){
            lista += objetos.getAt(i).toString(); // Añadimos la representación de cada objeto
            lista += "\n";
        }
        return lista;
    }

    /**
     * Vacía el inventario eliminando todos los objetos y reiniciando el peso total.
     */
    public void vaciar(){
        objetos.clear(); // Eliminamos todos los objetos de la lista
        pesoTotal = 0;   // Reiniciamos el peso acumulado
    }

    /**
     * Devuelve la capacidad máxima de objetos del inventario.
     * @return capacidad máxima
     */
    public int getCapacidadMaxima() {
        return capacidadesmaxima;
    }

    /**
     * Devuelve el peso total acumulado de todos los objetos del inventario.
     * @return peso total
     */
    public float getPesoTotal() {
        return pesoTotal;
    }

    /**
     * Establece manualmente el peso total acumulado del inventario.
     * @param pesoTotal nuevo peso total
     */
    public void setPesoTotal(float pesoTotal) {
        this.pesoTotal = pesoTotal;
    }
}
