package juego.model.EstructurasUtilizadas.Arboles.ArbolAVL;
import juego.model.EstructurasUtilizadas.Arboles.ArbolBusquedaBinaria.Nodo;
/**
 * Nodo especializado para el árbol AVL.
 * Extiende Nodo añadiendo el campo altura, necesario para calcular el factor de equilibrio sin recorrer el árbol.
 * @param <T> tipo de dato almacenado, debe implementar Comparable
 */
public class NodoAVL<T extends Comparable<T>> extends Nodo<T> {
    private int altura; // Altura del subárbol cuya raíz es este nodo
    /**
     * Crea un nuevo NodoAVL con el dato indicado y altura inicial 1.
     * @param dato el valor a almacenar en el nodo
     */
    public NodoAVL(T dato) {
        super(dato);      // Llamamos al constructor de Nodo para inicializar el dato y los hijos
        this.altura = 1;  // Un nodo recién creado siempre tiene altura 1 (es una hoja)
    }
    /**
     * Devuelve la altura almacenada en este nodo.
     * @return altura del subárbol cuya raíz es este nodo
     */
    public int getAltura() {
        return this.altura; // Devolvemos la altura almacenada en el nodo
    }
    /**
     * Actualiza la altura almacenada en este nodo.
     * @param altura nueva altura a asignar
     */
    public void setAltura(int altura) {
        this.altura = altura; // Actualizamos la altura tras una inserción o rotación
    }
}
