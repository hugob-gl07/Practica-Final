package EstructurasUtilizadas.Arboles.ArbolAVL;
import EstructurasUtilizadas.Arboles.ArbolBusquedaBinaria.ArbolBusquedaBinaria;
/**
 * Implementación de un Árbol AVL (Árbol Binario de Búsqueda Equilibrado).
 * Extiende ArbolBusquedaBinaria garantizando que tras cada inserción el árbol
 * se reequilibra automáticamente mediante rotaciones.
 * La diferencia de altura entre subárboles de cualquier nodo nunca supera 1.
 * @param <T> tipo de dato almacenado, debe implementar Comparable
 */
public class ArbolAVL<T extends Comparable<T>> extends ArbolBusquedaBinaria<T> {
    /**
     * Calcula el factor de equilibrio de un nodo.
     * El factor es la diferencia entre la altura del subárbol izquierdo y el derecho.
     * Un factor de 2 o -2 indica que el árbol necesita reequilibrarse.
     * @param nodo nodo del que calcular el factor
     * @return diferencia de alturas (izquierda - derecha), 0 si el nodo es null
     */
    private int getFactorEquilibrio(NodoAVL<T> nodo) {
        int respuesta = 0; // Iniciamos la variable respuesta
        if (nodo == null) {
            respuesta = 0; // Un nodo inexistente tiene factor 0
        } else {
            NodoAVL<T> hijoizq = (NodoAVL<T>) nodo.getIzquierdo(); // Obtenemos el hijo izquierdo
            NodoAVL<T> hijoder = (NodoAVL<T>) nodo.getDerecho();   // Obtenemos el hijo derecho
            int alturaizq = 0; // Iniciamos la variable altura izquierda
            if (hijoizq == null) {
                alturaizq = 0; // Sin hijo izquierdo su altura es 0
            } else {
                alturaizq = hijoizq.getAltura(); // Leemos la altura almacenada en el nodo
            }
            int alturader = 0; // Iniciamos la variable altura derecha
            if (hijoder == null) {
                alturader = 0; // Sin hijo derecho su altura es 0
            } else {
                alturader = hijoder.getAltura(); // Leemos la altura almacenada en el nodo
            }
            respuesta = alturaizq - alturader; // Factor positivo: pesa más la izquierda. Negativo: pesa más la derecha
        }
        return respuesta; // Devolvemos la variable respuesta
    }
    /**
     * Recalcula y actualiza la altura almacenada en el nodo en función de las alturas de sus hijos.
     * @param nodo nodo cuya altura se debe actualizar
     */
    private void actualizarAltura(NodoAVL<T> nodo) {
        NodoAVL<T> hijoizq = (NodoAVL<T>) nodo.getIzquierdo(); // Obtenemos el hijo izquierdo
        NodoAVL<T> hijoder = (NodoAVL<T>) nodo.getDerecho();   // Obtenemos el hijo derecho
        int alturaizq = 0;
        if (hijoizq == null) {
            alturaizq = 0; // Sin hijo izquierdo su altura es 0
        } else {
            alturaizq = hijoizq.getAltura(); // Leemos la altura almacenada en el nodo
        }
        int alturader = 0;
        if (hijoder == null) {
            alturader = 0; // Sin hijo derecho su altura es 0
        } else {
            alturader = hijoder.getAltura(); // Leemos la altura almacenada en el nodo
        }
        int nuevaaltura = 1 + Math.max(alturaizq, alturader); // La altura es 1 más el camino más largo
        nodo.setAltura(nuevaaltura); // Guardamos la nueva altura en el nodo
    }

    /**
     * Aplica una rotación simple hacia la derecha sobre el nodo dado.
     * Se usa cuando el desbalance está en el lado izquierdo-izquierdo (factor = 2,
     * hijo izquierdo con factor >= 0).
     * @param nodo nodo desbalanceado
     * @return nueva raíz del subárbol tras la rotación
     */
    private NodoAVL<T> rotarDerecha(NodoAVL<T> nodo) {
        NodoAVL<T> nuevaraiz = (NodoAVL<T>) nodo.getIzquierdo(); // El hijo izquierdo sube y se convierte en la nueva raíz
        nodo.setIzquierdo(nuevaraiz.getDerecho()); // El hijo derecho de la nueva raíz pasa a ser el hijo izquierdo del nodo bajado
        nuevaraiz.setDerecho(nodo); // El nodo original baja y se convierte en hijo derecho de la nueva raíz
        actualizarAltura(nodo);     // Actualizamos primero el nodo que bajó
        actualizarAltura(nuevaraiz); // Luego actualizamos la nueva raíz
        return nuevaraiz; // Devolvemos la nueva raíz para reconectar el subárbol
    }
    /**
     * Aplica una rotación simple hacia la izquierda sobre el nodo dado.
     * Se usa cuando el desbalance está en el lado derecho-derecho (factor = -2,
     * hijo derecho con factor <= 0).
     * @param nodo nodo desbalanceado
     * @return nueva raíz del subárbol tras la rotación
     */
    private NodoAVL<T> rotarIzquierda(NodoAVL<T> nodo) {
        NodoAVL<T> nuevaraiz = (NodoAVL<T>) nodo.getDerecho(); // El hijo derecho sube y se convierte en la nueva raíz
        nodo.setDerecho(nuevaraiz.getIzquierdo()); // El hijo izquierdo de la nueva raíz pasa a ser el hijo derecho del nodo bajado
        nuevaraiz.setIzquierdo(nodo); // El nodo original baja y se convierte en hijo izquierdo de la nueva raíz
        actualizarAltura(nodo);       // Actualizamos primero el nodo que bajó
        actualizarAltura(nuevaraiz);  // Luego actualizamos la nueva raíz
        return nuevaraiz; // Devolvemos la nueva raíz para reconectar el subárbol
    }
    /**
     * Aplica una rotación doble izquierda-derecha sobre el nodo dado.
     * Se usa cuando el desbalance está en el lado izquierdo-derecho (factor = 2,
     * hijo izquierdo con factor < 0).
     * Primero rota el hijo izquierdo a la izquierda y luego el nodo a la derecha.
     * @param nodo nodo desbalanceado
     * @return nueva raíz del subárbol tras la doble rotación
     */
    private NodoAVL<T> rotarIzquierdaDerecha(NodoAVL<T> nodo) {
        NodoAVL<T> hijoizq = (NodoAVL<T>) nodo.getIzquierdo(); // Obtenemos el hijo izquierdo desbalanceado
        nodo.setIzquierdo(rotarIzquierda(hijoizq)); // Paso 1: rotamos el hijo izquierdo hacia la izquierda
        NodoAVL<T> nuevaraiz = rotarDerecha(nodo);  // Paso 2: rotamos el nodo actual hacia la derecha
        return nuevaraiz; // Devolvemos la nueva raíz del subárbol
    }
    /**
     * Aplica una rotación doble derecha-izquierda sobre el nodo dado.
     * Se usa cuando el desbalance está en el lado derecho-izquierdo (factor = -2,
     * hijo derecho con factor > 0).
     * Primero rota el hijo derecho a la derecha y luego el nodo a la izquierda.
     * @param nodo nodo desbalanceado
     * @return nueva raíz del subárbol tras la doble rotación
     */
    private NodoAVL<T> rotarDerechaIzquierda(NodoAVL<T> nodo) {
        NodoAVL<T> hijoder = (NodoAVL<T>) nodo.getDerecho(); // Obtenemos el hijo derecho desbalanceado
        nodo.setDerecho(rotarDerecha(hijoder)); // Paso 1: rotamos el hijo derecho hacia la derecha
        NodoAVL<T> nuevaraiz = rotarIzquierda(nodo); // Paso 2: rotamos el nodo actual hacia la izquierda
        return nuevaraiz; // Devolvemos la nueva raíz del subárbol
    }
    /**
     * Comprueba el factor de equilibrio del nodo y aplica la rotación correspondiente
     * si es necesario para restaurar el equilibrio AVL.
     * @param nodo nodo a reequilibrar
     * @return nodo raíz del subárbol tras el reequilibrio (puede ser diferente al original)
     */
    private NodoAVL<T> reequilibrar(NodoAVL<T> nodo) {
        NodoAVL<T> respuesta = nodo; // Por defecto devolvemos el mismo nodo si no hay desbalance
        int factor = getFactorEquilibrio(nodo); // Calculamos el factor de equilibrio del nodo actual
        NodoAVL<T> hijoizq = (NodoAVL<T>) nodo.getIzquierdo(); // Creamos la variable hijo izquierdo
        NodoAVL<T> hijoder = (NodoAVL<T>) nodo.getDerecho(); // Creamos la variable hijo derecho
        int factorHijoizq = getFactorEquilibrio(hijoizq); // Factor del hijo izquierdo para detectar el tipo de rotación
        int factorHijoder = getFactorEquilibrio(hijoder);  // Factor del hijo derecho para detectar el tipo de rotación
        if (factor == 2) {
            // Desbalance hacia la izquierda
            if (factorHijoizq >= 0) {
                respuesta = rotarDerecha(nodo); // Caso LL: rotación simple derecha
            } else {
                respuesta = rotarIzquierdaDerecha(nodo); // Caso LR: rotación doble izquierda-derecha
            }
        } else if (factor == -2) {
            // Desbalance hacia la derecha
            if (factorHijoder <= 0) {
                respuesta = rotarIzquierda(nodo); // Caso RR: rotación simple izquierda
            } else {
                respuesta = rotarDerechaIzquierda(nodo); // Caso RL: rotación doble derecha-izquierda
            }
        }
        return respuesta; // Devolvemos la nueva raíz tras el reequilibrio
    }
    /**
     * Inserta un dato en el subárbol cuya raíz es el nodo dado, actualiza la altura y reequilibra el árbol si es necesario.
     * @param nodo  raíz del subárbol donde insertar
     * @param dato  valor a insertar
     * @return nueva raíz del subárbol tras la inserción y el reequilibrio
     */
    private NodoAVL<T> insertar(NodoAVL<T> nodo, T dato) {
        if (nodo == null) {
            nodo = new NodoAVL<>(dato); // Posición encontrada: creamos el nuevo nodo AVL
        } else if (dato.compareTo(nodo.getDato()) > 0) {
            nodo.setDerecho(insertar((NodoAVL<T>) nodo.getDerecho(), dato)); // El dato es mayor: bajamos por la derecha
        } else {
            nodo.setIzquierdo(insertar((NodoAVL<T>) nodo.getIzquierdo(), dato)); // El dato es menor o igual: bajamos por la izquierda
        }
        actualizarAltura(nodo);    // Actualizamos la altura tras la inserción
        return reequilibrar(nodo); // Reequilibramos si es necesario y devolvemos la nueva raíz
    }
    /**
     * Añade un nuevo dato al árbol AVL manteniendo el orden BST y el equilibrio AVL.
     * @param dato valor a insertar
     * @throws IllegalArgumentException si el dato es nulo
     */
    public void add(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("El dato no puede ser nulo.");
        }
        raiz = insertar((NodoAVL<T>) raiz, dato); // Insertamos desde la raíz y actualizamos la referencia
    }
}
