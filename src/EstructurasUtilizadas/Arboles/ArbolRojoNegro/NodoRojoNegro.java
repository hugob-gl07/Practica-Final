package EstructurasUtilizadas.Arboles.ArbolRojoNegro;

/**
 * Representa un nodo de un Árbol Rojo-Negro.
 * Cada nodo almacena un valor entero, un color (rojo o negro) y referencias
 * a sus hijos izquierdo y derecho y a su nodo padre.
 * Todo nodo recién creado es rojo por defecto, conforme a las reglas de inserción del árbol.
 */
public class NodoRojoNegro  {
    private int valor;               // Valor entero almacenado en el nodo
    private boolean esRojo;          // Color del nodo: true = rojo, false = negro
    private NodoRojoNegro izquierdo; // Hijo izquierdo (valores menores)
    private NodoRojoNegro derecho;   // Hijo derecho (valores mayores)
    private NodoRojoNegro padre;     // Nodo padre, null si es la raíz

    /**
     * Crea un nuevo nodo con el valor indicado.
     * El nodo se inicializa en color rojo y sin hijos ni padre.
     * @param valor valor entero a almacenar en el nodo
     */
    public NodoRojoNegro (int valor) {
        this.valor = valor;
        this.esRojo = true;      // Todo nodo nuevo es rojo por defecto
        this.izquierdo = null;   // Sin hijo izquierdo al crearse
        this.derecho = null;     // Sin hijo derecho al crearse
        this.padre = null;       // Sin padre al crearse (se asigna al insertarlo en el árbol)
    }

    /**
     * Devuelve el valor almacenado en el nodo.
     * @return valor entero del nodo
     */
    public int getValor() {
        return valor;
    }

    /**
     * Establece el valor almacenado en el nodo.
     * @param valor nuevo valor a almacenar
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * Indica si el nodo es de color rojo.
     * @return true si el nodo es rojo, false si es negro
     */
    public boolean getEsRojo() {
        return esRojo;
    }

    /**
     * Establece el color del nodo.
     * @param esRojo true para pintar el nodo de rojo, false para negro
     */
    public void setEsRojo(boolean esRojo) {
        this.esRojo = esRojo;
    }

    /**
     * Devuelve el hijo izquierdo del nodo.
     * @return hijo izquierdo, o null si no existe
     */
    public NodoRojoNegro getIzquierdo() {
        return izquierdo;
    }

    /**
     * Establece el hijo izquierdo del nodo.
     * @param izquierdo nodo que será el hijo izquierdo
     */
    public void setIzquierdo(NodoRojoNegro izquierdo) {
        this.izquierdo = izquierdo;
    }

    /**
     * Devuelve el hijo derecho del nodo.
     * @return hijo derecho, o null si no existe
     */
    public NodoRojoNegro getDerecho() {
        return derecho;
    }

    /**
     * Establece el hijo derecho del nodo.
     * @param derecho nodo que será el hijo derecho
     */
    public void setDerecho(NodoRojoNegro derecho) {
        this.derecho = derecho;
    }

    /**
     * Devuelve el nodo padre de este nodo.
     * @return nodo padre, o null si este nodo es la raíz
     */
    public NodoRojoNegro getPadre() {
        return padre;
    }

    /**
     * Establece el nodo padre de este nodo.
     * @param padre nodo que será el padre
     */
    public void setPadre(NodoRojoNegro padre) {
        this.padre = padre;
    }
}
