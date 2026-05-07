package EstructurasUtilizadas.Arboles.ArbolBusquedaBinaria;
import EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;
import EstructurasUtilizadas.Colas.Cola.Cola;
import static java.lang.Math.max;
/**
 * Implementación de un Árbol Binario de Búsqueda (BST) genérico.
 * Mantiene los elementos ordenados: los menores a la izquierda y los mayores a la derecha.
 * Permite insertar elementos y realizar distintos tipos de recorridos y consultas.
 * @param <T> tipo de dato almacenado, debe implementar Comparable
 */
public class ArbolBusquedaBinaria<T extends Comparable<T>> {
    /** Nodo raíz del árbol. */
    protected Nodo<T> raiz; // Crea un árbol vacío
    public ArbolBusquedaBinaria() {
        raiz = null;
    }
    /**
     * Constructor privado que crea un árbol cuya raíz es el nodo indicado.
     * Se usa internamente para construir subárboles.
     * @param nodo nodo que actuará como raíz del nuevo árbol
     */
    private ArbolBusquedaBinaria(Nodo<T> nodo) {
        raiz = nodo;
    }
    /**
     * Inserta un dato en el subárbol cuya raíz es el nodo indicado.
     * Si el dato es mayor va a la derecha, si es menor o igual va a la izquierda.
     * @param nodo  raíz del subárbol donde insertar
     * @param dato  valor a insertar
     * @return el nodo raíz del subárbol tras la inserción
     */
    private Nodo<T> insertar(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            // Posición encontrada: creamos el nuevo nodo
            nodo = new Nodo<>(dato);
        } else if (dato.compareTo(nodo.getDato()) > 0) {
            // El dato es mayor: bajamos por la derecha
            nodo.setDerecho(insertar(nodo.getDerecho(), dato));
        } else {
            // El dato es menor o igual: bajamos por la izquierda
            nodo.setIzquierdo(insertar(nodo.getIzquierdo(), dato));
        }
        return nodo; // Devolvemos el nodo
    }
    /**
     * Añade un nuevo dato al árbol manteniendo el orden BST.
     * @param dato valor a insertar
     * @throws IllegalArgumentException si el dato es nulo
     */
    public void add(T dato) {
        if(dato==null) {
            throw new IllegalArgumentException("El dato no puede ser nulo.");
        }
        raiz = insertar(raiz, dato); // LLamamos al método insertar
    }
    /**
     * Recorre el subárbol en orden central (izquierdo → raíz → derecho)
     * y añade cada dato a la lista.
     * @param nodo  raíz del subárbol a recorrer
     * @param lista lista donde se almacenan los datos en orden
     */
    private void inorden(Nodo<T> nodo, ListaSimplementeEnlazada<T> lista) {
        if (nodo == null) {
            return; // Caso base: subárbol vacío, no hay nada que recorrer
        }
        inorden(nodo.getIzquierdo(), lista); // Primero recorremos el subárbol izquierdo
        lista.add(nodo.getDato());           // Luego añadimos el dato del nodo actual
        inorden(nodo.getDerecho(), lista);   // Por último recorremos el subárbol derecho
    }
    /**
     * Devuelve una lista con los datos del árbol en orden central (inorden).
     * El resultado es una lista ordenada de menor a mayor.
     * @return lista con los datos en orden central
     */
    public ListaSimplementeEnlazada<T> getListaOrdenadaCentral() {
        ListaSimplementeEnlazada<T> lista = new ListaSimplementeEnlazada<>(); // Creamos una Lista Simplemente Enlazada para almacenar los datos del recorrido
        inorden(raiz, lista); // LLamamos al método inorden
        return lista; // Devolvemos la lista creada
    }
    /**
     * Recorre el subárbol en preorden (raíz → izquierdo → derecho)
     * y añade cada dato a la lista.
     * @param nodo  raíz del subárbol a recorrer
     * @param lista lista donde se almacenan los datos
     */
    private void preorden(Nodo<T> nodo, ListaSimplementeEnlazada<T> lista) {
        if (nodo == null) {
            return; // Caso base: subárbol vacío, no hay nada que recorrer
        }
        lista.add(nodo.getDato());            // Primero añadimos el dato del nodo actual
        preorden(nodo.getIzquierdo(), lista); // Luego recorremos el subárbol izquierdo
        preorden(nodo.getDerecho(), lista);   // Por último recorremos el subárbol derecho
    }
    /**
     * Devuelve una lista con los datos del árbol en preorden.
     * @return lista con los datos en preorden
     */
    public ListaSimplementeEnlazada<T> getListaPreOrden() {
        ListaSimplementeEnlazada<T> lista = new ListaSimplementeEnlazada<>(); // Creamos una Lista Simplemente Enlazada para almacenar los datos del recorrido
        preorden(raiz, lista); // LLamamos el método preorden
        return lista; // Devolvemos la lista creada
    }
    /**
     * Recorre el subárbol en postorden (izquierdo → derecho → raíz)
     * y añade cada dato a la lista.
     * @param nodo  raíz del subárbol a recorrer
     * @param lista lista donde se almacenan los datos
     */
    private void postorden(Nodo<T> nodo, ListaSimplementeEnlazada<T> lista) {
        if (nodo == null) {
            return; // Caso base: subárbol vacío, no hay nada que recorrer
        }
        postorden(nodo.getIzquierdo(), lista); // Primero recorremos el subárbol izquierdo
        postorden(nodo.getDerecho(), lista);   // Luego recorremos el subárbol derecho
        lista.add(nodo.getDato());             // Por último añadimos el dato del nodo actual
    }
    /**
     * Devuelve una lista con los datos del árbol en postorden.
     * @return lista con los datos en postorden
     */
    public ListaSimplementeEnlazada<T> getListaPostOrden() {
        ListaSimplementeEnlazada<T> lista = new ListaSimplementeEnlazada<>(); // Creamos una Lista Simplemente Enlazada para almacenar los datos del recorrido
        postorden(raiz, lista); // LLamamos al método postorden
        return lista; // Devolvemos la lista creada
    }
    /**
     * Calcula recursivamente la altura del subárbol cuya raíz es el nodo dado.
     * La altura es el número de niveles desde ese nodo hasta la hoja más lejana.
     * @param nodo raíz del subárbol
     * @return altura del subárbol, 0 si el nodo es null
     */
    private int altura(Nodo<T> nodo) {
        int respuesta = 0;
        if (nodo != null) {
            int alturaIzquierda = altura(nodo.getIzquierdo()); // Altura del subárbol izquierdo
            int alturaDerecha = altura(nodo.getDerecho());      // Altura del subárbol derecho
            int altura = 1 + Math.max(alturaIzquierda, alturaDerecha); // El nodo actual suma 1 al camino más largo
            respuesta= altura; // Devolvemos la altura calculada
        }
        return respuesta;
    }
    /**
     * Devuelve la altura total del árbol.
     * @return altura del árbol, 0 si está vacío
     */
    public int getAltura() {
        return altura(raiz); // LLamamos al método altura con la raíz
    }
    /**
     * Recorre el subárbol y añade a la lista los datos de los nodos
     * que se encuentran exactamente en el nivel indicado.
     * @param nodo  raíz del subárbol a recorrer
     * @param nivel nivel objetivo (0 es la raíz)
     * @param lista lista donde se almacenan los datos del nivel
     */
    private void listaDatosNivel(Nodo<T> nodo, int nivel, ListaSimplementeEnlazada<T> lista) {
        if (nodo == null) {
            return; // Caso base: rama vacía, no hay nodo en este nivel
        } else if (nivel == 0) {
            lista.add(nodo.getDato()); // Hemos llegado al nivel buscado: añadimos el dato
        } else if (nivel > 0) {
            // Todavía no hemos llegado al nivel: bajamos un nivel en cada subárbol
            listaDatosNivel(nodo.getIzquierdo(), nivel - 1, lista);
            listaDatosNivel(nodo.getDerecho(), nivel - 1, lista);
        }
    }

    /**
     * Devuelve una lista con los datos de todos los nodos que están en el nivel indicado.
     * El nivel 0 corresponde a la raíz.
     * @param nivel nivel del árbol a consultar
     * @return lista con los datos del nivel indicado
     * @throws IllegalArgumentException si el nivel es negativo
     */
    public ListaSimplementeEnlazada<T> getListaDatosNivel(int nivel) {
        if(nivel < 0) {
            throw new IllegalArgumentException("El nivel no puede ser negativo.");
        }
        ListaSimplementeEnlazada<T> lista = new ListaSimplementeEnlazada<>(); // Creamos una Lista Simplemente Enlazada para almacenar la información
        listaDatosNivel(raiz, nivel, lista); // Llamamos al método listaDatosNivel
        return lista; // Devolvemos la lista
    }

    /**
     * Recorre el árbol aprovechando la propiedad BST para encontrar el camino
     * desde la raíz hasta el nodo que contiene el dato buscado.
     * @param dato  valor a buscar
     * @param nodo  nodo actual del recorrido
     * @param lista lista donde se acumula el camino recorrido
     */
    private void getcamino(T dato, Nodo<T> nodo, ListaSimplementeEnlazada<T> lista) {
        if (nodo == null) {
            return; // El dato no existe en el árbol, dejamos la lista vacía
        } else if (dato.compareTo(nodo.getDato()) == 0) {
            lista.add(nodo.getDato()); // Dato encontrado: añadimos el último nodo del camino
        } else if (dato.compareTo(nodo.getDato()) < 0) {
            lista.add(nodo.getDato());              // Añadimos el nodo actual al camino
            getcamino(dato, nodo.getIzquierdo(), lista); // El dato es menor: continuamos por la izquierda
        } else if (dato.compareTo(nodo.getDato()) > 0) {
            lista.add(nodo.getDato());             // Añadimos el nodo actual al camino
            getcamino(dato, nodo.getDerecho(), lista); // El dato es mayor: continuamos por la derecha
        }
    }

    /**
     * Devuelve una lista con el camino desde la raíz hasta el nodo que contiene el dato.
     * Si el dato no existe devuelve una lista vacía.
     * @param dato valor a buscar
     * @return lista con los nodos del camino, desde la raíz hasta el dato
     * @throws IllegalArgumentException si el dato es nulo
     */
    public ListaSimplementeEnlazada<T> getListaCamino(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("El dato no puede ser nulo.");
        }
        ListaSimplementeEnlazada<T> lista = new ListaSimplementeEnlazada<>(); // Creamos una Lista Simplemente Enlazada para almacenar los datos
        getcamino(dato, raiz, lista); // Llamamos al método getcamino
        return lista; // Devolvemos la lista creada
    }

    /**
     * Devuelve el subárbol izquierdo de la raíz como un nuevo árbol independiente.
     * @return subárbol izquierdo, o null si no existe
     */
    public ArbolBusquedaBinaria<T> getSubArbolIzquierdo() {
        ArbolBusquedaBinaria<T> respuesta = null;
        if (raiz == null) {
            respuesta = null; // El árbol está vacío, no hay subárbol
        } else if (raiz.getIzquierdo() == null) {
            respuesta = null; // La raíz no tiene hijo izquierdo
        } else {
            // Construimos un nuevo árbol cuya raíz es el hijo izquierdo
            respuesta = new ArbolBusquedaBinaria<T>(raiz.getIzquierdo());
        }
        return respuesta;
    }

    /**
     * Devuelve el subárbol derecho de la raíz como un nuevo árbol independiente.
     * @return subárbol derecho, o null si no existe
     */
    public ArbolBusquedaBinaria<T> getSubArbolDerecho() {
        ArbolBusquedaBinaria<T> respuesta = null;
        if (raiz == null) {
            respuesta = null; // El árbol está vacío, no hay subárbol
        } else if (raiz.getDerecho() == null) {
            respuesta = null; // La raíz no tiene hijo derecho
        } else {
            // Construimos un nuevo árbol cuya raíz es el hijo derecho
            respuesta = new ArbolBusquedaBinaria<T>(raiz.getDerecho());
        }
        return respuesta; //Devolvemos si el arbol existe o no
    }

    /**
     * Comprueba recursivamente si el subárbol cuya raíz es el nodo dado es homogéneo.
     * Un árbol es homogéneo si todos sus nodos internos tienen exactamente 2 hijos.
     * @param nodo raíz del subárbol a comprobar
     * @return true si el subárbol es homogéneo, false en caso contrario
     */
    private boolean isHomogeneo(Nodo<T> nodo) {
        boolean respuesta = false;
        if (nodo == null) {
            respuesta = true; // Un árbol vacío se considera homogéneo
        } else if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
            respuesta = true; // Un nodo hoja (sin hijos) es válido en un árbol homogéneo
        } else if ((nodo.getIzquierdo() != null && nodo.getDerecho() == null) || (nodo.getIzquierdo() == null && nodo.getDerecho() != null)) {
            respuesta = false; // Un nodo con solo 1 hijo rompe la homogeneidad
        } else if (nodo.getIzquierdo() != null && nodo.getDerecho() != null) {
            // El nodo tiene 2 hijos: comprobamos recursivamente ambos subárboles
            respuesta = isHomogeneo(nodo.getIzquierdo()) && isHomogeneo(nodo.getDerecho());
        }
        return respuesta;
    }

    /**
     * Indica si el árbol es homogéneo.
     * Un árbol es homogéneo si todos sus nodos internos tienen exactamente 2 hijos.
     * @return true si el árbol es homogéneo, false en caso contrario
     */
    public boolean isArbolHomogeneo() {
        return isHomogeneo(raiz); // LLamamos al método isHomogeneo con la raíz del árbol
    }

    /**
     * Comprueba recursivamente si el subárbol es completo.
     * Un árbol es completo si todas sus hojas están al mismo nivel.
     * @param nodo        raíz del subárbol a comprobar
     * @param nivelActual profundidad del nodo actual desde la raíz
     * @param altura      nivel en el que deben estar todas las hojas
     * @return true si el subárbol es completo, false en caso contrario
     */
    private boolean isCompleto(Nodo<T> nodo, int nivelActual, int altura) {
        boolean respuesta = false;
        if (nodo == null) {
            respuesta=true;
        } else if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
            // Es una hoja: comprobamos que está exactamente en el nivel esperado
            respuesta= (nivelActual == altura);
        } else {
            // No es hoja: bajamos un nivel en ambos subárboles y comprobamos recursivamente
            respuesta= isCompleto(nodo.getIzquierdo(), nivelActual + 1, altura) && isCompleto(nodo.getDerecho(), nivelActual + 1, altura);
        }
        return respuesta;
    }

    /**
     * Indica si el árbol es completo.
     * Un árbol es completo si todas sus hojas están al mismo nivel.
     * @return true si el árbol es completo, false en caso contrario
     */
    public boolean isArbolCompleto() {
        int altura = getAltura() - 1; // Las hojas deben estar en el último nivel (altura - 1)
        return isCompleto(raiz, 0, altura);
    }

    /**
     * Comprueba si el árbol es casi completo usando un recorrido por niveles (BFS).
     * Un árbol es casi completo si todos los niveles están llenos excepto el último,
     * y en el último las hojas están agrupadas a la izquierda.
     * @return true si el árbol es casi completo, false en caso contrario
     */
    private boolean isSemiCompleto() {
        boolean respuesta = true;
        if (raiz == null) {
            respuesta = true; // Un árbol vacío se considera casi completo
        } else {
            Cola<Nodo<T>> cola = new Cola<>();
            cola.enqueue(raiz); // Iniciamos el recorrido BFS desde la raíz
            boolean encontrado = false; // Se activa cuando encontramos un nodo sin algún hijo
            while (!cola.isEmpty()) {
                Nodo<T> actual = cola.dequeue(); // Sacamos el siguiente nodo a procesar
                Nodo<T> izquierdo = actual.getIzquierdo();
                Nodo<T> derecha = actual.getDerecho();
                // Si ya encontramos un hueco y este nodo tiene hijos asimétricos, no es casi completo
                if (encontrado && ((izquierdo != null && derecha == null) || (izquierdo == null && derecha != null))) {
                    respuesta = false;
                } else if (izquierdo != null) {
                    cola.enqueue(izquierdo); // Hijo izquierdo existe: lo añadimos a la cola
                } else {
                    encontrado = true; // No hay hijo izquierdo: activamos la bandera de hueco
                }
                if (derecha == null) {
                    encontrado = true; // Hay hueco: cualquier nodo posterior debe ser hoja
                }
                // Comprobamos el hijo derecho independientemente del izquierdo
                if (derecha != null && encontrado) {
                    respuesta = false; // Hay hijo derecho, pero ya habíamos encontrado un hueco
                } else if (derecha != null) {
                    cola.enqueue(derecha); // Hijo derecho existe: lo añadimos a la cola
                }
            }
        }
        return respuesta;
    }

    /**
     * Indica si el árbol es casi completo.
     * Un árbol es casi completo si todos los niveles están llenos excepto el último,
     * y en el último las hojas están agrupadas a la izquierda.
     * @return true si el árbol es casi completo, false en caso contrario
     */
    public boolean isArbolSemiCompleto() {
        return isSemiCompleto(); // LLamamos al método isSemiCompleto para comprobar la condición
    }

    /**
     * Calcula recursivamente el grado máximo del subárbol cuya raíz es el nodo dado.
     * El grado de un nodo es el número de hijos que tiene (0, 1 o 2).
     * @param nodo raíz del subárbol
     * @return grado máximo encontrado en el subárbol
     */
    private int grado(Nodo<T> nodo) {
        int respuesta = 0; // Iniciamos la variable respuesta
        int hijosactual = 0; // Iniciamos la variable hijosactual para contar los hijos del nodo actual
        if (nodo == null) {
            respuesta = 0; // Un nodo inexistente tiene grado 0
        } else {
            // Contamos cuántos hijos tiene el nodo actual
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                hijosactual = 0; // Nodo hoja: ningún hijo
            } else if ((nodo.getIzquierdo() != null && nodo.getDerecho() == null) || (nodo.getIzquierdo() == null && nodo.getDerecho() != null)) {
                hijosactual = 1; // Solo tiene un hijo
            } else {
                hijosactual = 2; // Tiene los dos hijos
            }
            int gradoizq = grado(nodo.getIzquierdo()); // Grado máximo del subárbol izquierdo
            int gradoder = grado(nodo.getDerecho());    // Grado máximo del subárbol derecho
            // El grado del árbol es el máximo entre el nodo actual y sus subárboles
            respuesta = Math.max(hijosactual, Math.max(gradoizq, gradoder));
        }
        return respuesta; // Devolvemos la variable respuesta
    }

    /**
     * Devuelve el grado del árbol, es decir, el número máximo de hijos
     * que tiene cualquier nodo del árbol.
     * @return grado del árbol (0, 1 o 2 en un árbol binario)
     */
    public int getGrado() {
        return grado(raiz); // Llamamos al método grado con la raíz del árbol para obtener el resultado
    }
}

