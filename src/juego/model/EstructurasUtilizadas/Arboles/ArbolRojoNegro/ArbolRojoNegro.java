package juego.model.EstructurasUtilizadas.Arboles.ArbolRojoNegro;
/**
 * Implementación de un Árbol Rojo-Negro.
 * Es un árbol binario de búsqueda auto-balanceado que garantiza las siguientes propiedades:
 * 1. Todo nodo es rojo o negro.
 * 2. La raíz siempre es negra.
 * 3. Todo nodo rojo tiene hijos negros (no hay dos rojos consecutivos).
 * 4. Todo camino desde un nodo hasta sus hojas nulas tiene el mismo número de nodos negros.
 * Estas propiedades garantizan que la altura del árbol sea O(log n).
 */
public class ArbolRojoNegro {
    private NodoRojoNegro raiz; // Nodo raíz del árbol, null si el árbol está vacío

    /**
     * Crea un Árbol Rojo-Negro vacío.
     */
    public ArbolRojoNegro() {
        this.raiz = null; // El árbol comienza sin ningún nodo
    }

    /**
     * Devuelve el nodo hermano del nodo dado (el otro hijo del padre).
     * Se usa durante el reequilibrio para decidir qué rotaciones o repintados aplicar.
     * @param nodo nodo del que obtener el hermano
     * @return nodo hermano, o null si el nodo o su padre son null
     */
    private NodoRojoNegro hermano(NodoRojoNegro nodo){
        NodoRojoNegro resultado = null;
        if(nodo != null && nodo.getPadre() != null){
            if (nodo.getPadre().getIzquierdo() == nodo){
                resultado = nodo.getPadre().getDerecho();  // El hermano es el hijo derecho del padre
            }
            else{
                resultado = nodo.getPadre().getIzquierdo(); // El hermano es el hijo izquierdo del padre
            }
        }
        return resultado;
    }

    /**
     * Pinta el nodo de color negro si no es null.
     * Se usa durante el reequilibrio para mantener las propiedades del árbol.
     * @param nodo nodo a pintar de negro
     */
    private void pintarNegro(NodoRojoNegro nodo){
        if(nodo != null){
            nodo.setEsRojo(false); // Asignamos el color negro al nodo
        }
    }

    /**
     * Pinta el nodo de color rojo si no es null.
     * Se usa durante el reequilibrio para mantener las propiedades del árbol.
     * @param nodo nodo a pintar de rojo
     */
    private void pintarRojo(NodoRojoNegro nodo){
        if(nodo != null){
            nodo.setEsRojo(true); // Asignamos el color rojo al nodo
        }
    }

    /**
     * Aplica una rotación simple hacia la derecha sobre el nodo dado.
     * El hijo izquierdo sube a la posición del nodo, que baja a hijo derecho.
     * Actualiza los punteros de padre para mantener la consistencia del árbol.
     * @param nodo nodo sobre el que se aplica la rotación
     */
    private void rotarDerecha(NodoRojoNegro nodo) {
        NodoRojoNegro nuevaraiz = nodo.getIzquierdo();       // El hijo izquierdo sube y se convierte en la nueva raíz del subárbol
        NodoRojoNegro padreOriginal = nodo.getPadre();        // Guardamos el padre original para reconectar tras la rotación
        nodo.setIzquierdo(nuevaraiz.getDerecho());            // El hijo derecho de la nueva raíz pasa a ser el hijo izquierdo del nodo bajado
        if(nuevaraiz.getDerecho() != null){
            nuevaraiz.getDerecho().setPadre(nodo);            // Actualizamos el padre del nodo adoptado
        }
        nuevaraiz.setPadre(padreOriginal);                    // La nueva raíz toma el padre del nodo original
        if(padreOriginal == null){
            raiz = nuevaraiz;                                 // Si era la raíz del árbol, actualizamos la raíz global
        }
        else if (padreOriginal.getIzquierdo() == nodo){
            padreOriginal.setIzquierdo(nuevaraiz);            // La nueva raíz ocupa el lugar del hijo izquierdo del abuelo
        }
        else{
            padreOriginal.setDerecho(nuevaraiz);              // La nueva raíz ocupa el lugar del hijo derecho del abuelo
        }
        nuevaraiz.setDerecho(nodo);                           // El nodo original baja y se convierte en hijo derecho de la nueva raíz
        nodo.setPadre(nuevaraiz);                             // Actualizamos el padre del nodo que bajó
    }

    /**
     * Aplica una rotación simple hacia la izquierda sobre el nodo dado.
     * El hijo derecho sube a la posición del nodo, que baja a hijo izquierdo.
     * Actualiza los punteros de padre para mantener la consistencia del árbol.
     * @param nodo nodo sobre el que se aplica la rotación
     */
    private void rotarIzquierda(NodoRojoNegro nodo) {
        NodoRojoNegro nuevaraiz = nodo.getDerecho();          // El hijo derecho sube y se convierte en la nueva raíz del subárbol
        NodoRojoNegro padreOriginal = nodo.getPadre();        // Guardamos el padre original para reconectar tras la rotación
        nodo.setDerecho(nuevaraiz.getIzquierdo());            // El hijo izquierdo de la nueva raíz pasa a ser el hijo derecho del nodo bajado
        if(nuevaraiz.getIzquierdo() != null){
            nuevaraiz.getIzquierdo().setPadre(nodo);          // Actualizamos el padre del nodo adoptado
        }
        nuevaraiz.setPadre(padreOriginal);                    // La nueva raíz toma el padre del nodo original
        if(padreOriginal == null){
            raiz = nuevaraiz;                                 // Si era la raíz del árbol, actualizamos la raíz global
        }
        else if (padreOriginal.getDerecho() == nodo){
            padreOriginal.setDerecho(nuevaraiz);              // La nueva raíz ocupa el lugar del hijo derecho del abuelo
        }
        else{
            padreOriginal.setIzquierdo(nuevaraiz);            // La nueva raíz ocupa el lugar del hijo izquierdo del abuelo
        }
        nuevaraiz.setIzquierdo(nodo);                         // El nodo original baja y se convierte en hijo izquierdo de la nueva raíz
        nodo.setPadre(nuevaraiz);                             // Actualizamos el padre del nodo que bajó
    }

    /**
     * Inserta un valor en el subárbol cuya raíz es el nodo dado siguiendo las reglas BST.
     * Asigna el padre correcto al nuevo nodo para que el árbol mantenga los punteros ascendentes.
     * @param raiz  raíz del subárbol donde insertar
     * @param valor valor a insertar
     * @param padre padre del nodo raíz actual (para asignarlo al nuevo nodo)
     * @return nodo raíz del subárbol tras la inserción
     */
    private NodoRojoNegro insertarBST(NodoRojoNegro raiz, int valor, NodoRojoNegro padre){
        NodoRojoNegro resultado = null;
        if(raiz == null){
            NodoRojoNegro nodo = new NodoRojoNegro(valor); // Posición encontrada: creamos el nuevo nodo rojo
            nodo.setPadre(padre);                           // Asignamos el padre para mantener el puntero ascendente
            resultado=nodo;
        }
        if (valor < raiz.getValor()) {
            raiz.setIzquierdo(insertarBST(raiz.getIzquierdo(), valor, raiz)); // El valor es menor: bajamos por la izquierda
            resultado=raiz;
        }
        else {
            raiz.setDerecho(insertarBST(raiz.getDerecho(), valor, raiz)); // El valor es mayor o igual: bajamos por la derecha
            resultado=raiz;
        }
        return resultado;
    }

    /**
     * Busca recursivamente el nodo que contiene el valor indicado en el subárbol dado.
     * Aprovecha la propiedad BST para descartar ramas enteras en cada paso.
     * @param nodo  raíz del subárbol donde buscar
     * @param valor valor a buscar
     * @return nodo que contiene el valor, o null si no existe
     */
    private NodoRojoNegro buscarNodo(NodoRojoNegro nodo, int valor){
        NodoRojoNegro resultado = null;
        if(nodo != null){
            if(valor == nodo.getValor()){
                resultado = nodo;                                        // Valor encontrado: devolvemos este nodo
            }
            else if (valor < nodo.getValor()) {
                resultado = buscarNodo(nodo.getIzquierdo(), valor);      // El valor es menor: buscamos por la izquierda
            }
            else {
                resultado = buscarNodo(nodo.getDerecho(), valor);        // El valor es mayor: buscamos por la derecha
            }
        }
        return resultado; // null si no se encontró el valor
    }

    /**
     * Restaura las propiedades del Árbol Rojo-Negro tras una inserción.
     * Sube por el árbol desde el nodo insertado corrigiendo violaciones de color mediante
     * repintados y rotaciones hasta que no queden dos nodos rojos consecutivos.
     * Al terminar garantiza que la raíz sea negra.
     * @param nodo nodo recién insertado desde el que comenzar la corrección
     */
    private void fixInsert(NodoRojoNegro nodo){
        // Mientras el padre exista y sea rojo hay una violación que corregir
        while(nodo.getPadre() != null && nodo.getPadre().getEsRojo()){
            NodoRojoNegro padre  = nodo.getPadre();          // Padre del nodo actual
            NodoRojoNegro abuelo = padre.getPadre();         // Abuelo del nodo actual (siempre negro si el padre es rojo)
            NodoRojoNegro tio    = hermano(padre);           // Tío del nodo (hermano del padre)
            if(padre == abuelo.getIzquierdo()){
                // El padre es hijo izquierdo del abuelo
                if(tio != null && tio.getEsRojo()){
                    // Caso 1: el tío es rojo → repintamos padre y tío de negro, abuelo de rojo y subimos
                    pintarNegro(padre);
                    pintarNegro(tio);
                    pintarRojo(abuelo);
                    nodo = abuelo; // Subimos al abuelo para seguir comprobando hacia arriba
                }
                else{
                    // El tío es negro o inexistente → necesitamos rotaciones
                    if(nodo == padre.getDerecho()){
                        // Caso 2 (LL → LR): el nodo es hijo derecho → rotamos el padre a la izquierda para convertirlo en caso 3
                        nodo = padre;
                        rotarIzquierda(padre);
                        padre = nodo.getPadre(); // Actualizamos padre tras la rotación
                    }
                    // Caso 3: el nodo es hijo izquierdo → repintamos y rotamos el abuelo a la derecha
                    pintarNegro(padre);
                    pintarRojo(abuelo);
                    rotarDerecha(abuelo);
                }
            }
            else{
                // El padre es hijo derecho del abuelo (casos simétricos)
                if(tio != null && tio.getEsRojo()){
                    // Caso 1 simétrico: el tío es rojo → repintamos y subimos
                    pintarNegro(padre);
                    pintarNegro(tio);
                    pintarRojo(abuelo);
                    nodo = abuelo; // Subimos al abuelo para seguir comprobando hacia arriba
                }
                else{
                    // El tío es negro o inexistente → necesitamos rotaciones
                    if(nodo == padre.getIzquierdo()){
                        // Caso 2 simétrico (RR → RL): rotamos el padre a la derecha para convertirlo en caso 3
                        nodo = padre;
                        rotarDerecha(padre);
                        padre = nodo.getPadre(); // Actualizamos padre tras la rotación
                    }
                    // Caso 3 simétrico: repintamos y rotamos el abuelo a la izquierda
                    pintarNegro(padre);
                    pintarRojo(abuelo);
                    rotarIzquierda(abuelo);
                }
            }
        }
        pintarNegro(raiz); // La raíz siempre debe ser negra (propiedad 2 del árbol Rojo-Negro)
    }

    /**
     * Añade un nuevo valor al árbol Rojo-Negro manteniendo el orden BST y las propiedades de color.
     * Primero inserta el nodo como en un BST estándar y luego corrige las violaciones de color.
     * @param valor valor entero a insertar
     */
    public void insertar(int valor){
        raiz = insertarBST(raiz, valor, null); // Insertamos siguiendo las reglas BST desde la raíz
        NodoRojoNegro nuevo = buscarNodo(raiz, valor); // Localizamos el nodo recién insertado
        fixInsert(nuevo); // Restauramos las propiedades Rojo-Negro desde el nuevo nodo
    }

    private void inorden(NodoRojoNegro nodo) {
        if (nodo == null) return;
        inorden(nodo.getIzquierdo());
        System.out.println(nodo.getValor() + (nodo.getEsRojo() ? "R" : "N"));        inorden(nodo.getDerecho());
    }
    public void inordenRojoNegro(){
        inorden(raiz);
    }
}
