package EstructurasUtilizadas.Arboles.ArbolB;

/**
 * Contenedor de transporte usado durante el split (división) de un nodo en el Árbol B.
 * Cuando un nodo lleno se divide, esta clase encapsula el dato central que sube al padre
 * y el nuevo nodo hermano derecho creado con la mitad de las claves.
 * @param <T> tipo de dato almacenado, debe implementar Comparable
 */
public class ResultadoSplit <T extends Comparable<T>> {
    T datoQueSube;           // Dato central del nodo dividido que se promueve al padre
    NodoB<T> hermanoDerecho; // Nuevo nodo creado con la mitad derecha de las claves

    /**
     * Crea un nuevo ResultadoSplit con el dato promovido y el nodo hermano.
     * @param dato    dato central que sube al nodo padre
     * @param hermano nodo hermano derecho resultante del split
     */
    public ResultadoSplit(T dato, NodoB<T> hermano) {
        this.datoQueSube = dato;       // Guardamos el dato que subirá al padre
        this.hermanoDerecho = hermano; // Guardamos el nuevo hermano derecho
    }

    /**
     * Devuelve el dato que debe subir al padre tras el split.
     * @return dato promovido al padre
     */
    public T getDatoQueSube() {
        return datoQueSube;
    }

    /**
     * Establece el dato que debe subir al padre.
     * @param datoQueSube nuevo dato a promover
     */
    public void setDatoQueSube(T datoQueSube) {
        this.datoQueSube = datoQueSube;
    }

    /**
     * Devuelve el nodo hermano derecho creado tras el split.
     * @return nodo hermano derecho
     */
    public NodoB<T> getHermanoDerecho() {
        return hermanoDerecho;
    }

    /**
     * Establece el nodo hermano derecho.
     * @param hermanoDerecho nuevo nodo hermano derecho
     */
    public void setHermanoDerecho(NodoB<T> hermanoDerecho) {
        this.hermanoDerecho = hermanoDerecho;
    }
}