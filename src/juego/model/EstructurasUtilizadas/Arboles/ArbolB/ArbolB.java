package juego.model.EstructurasUtilizadas.Arboles.ArbolB;

/**
 * Implementación de un Árbol B genérico de orden configurable.
 * Un Árbol B es un árbol de búsqueda autobalanceado donde cada nodo puede almacenar
 * múltiples claves y tener múltiples hijos. Garantiza que todas las hojas están al mismo nivel.
 * Con un árbol de orden {@code n}, cada nodo puede tener hasta {@code n - 1} claves
 * y hasta {@code n} hijos.
 * @param <T> tipo de dato almacenado, debe implementar Comparable
 */
public class ArbolB<T extends Comparable<T>> {
    private NodoB<T> raiz; // Nodo raíz del árbol
    private int orden;     // Orden del árbol: número máximo de hijos que puede tener un nodo

    /**
     * Crea un Árbol B vacío con el orden indicado.
     * @param orden orden del árbol (número máximo de hijos por nodo)
     * @throws IllegalArgumentException si el orden es menor a 2, ya que un árbol de orden 1 no es válido
     */
    public ArbolB(int orden) {
        if(orden<2){
            throw new IllegalArgumentException("El orden del árbol debe ser al menos 2."); // Un árbol de orden menor a 2 no es válido
        }
        this.raiz = null;   // El árbol comienza vacío
        this.orden = orden; // Guardamos el orden del árbol
    }

    /**
     * Indica si el árbol está vacío.
     * @return true si el árbol no tiene elementos, false en caso contrario
     */
    public boolean isEmpty() {
        return raiz == null;
    }

    /**
     * Comprueba si un dato existe en el árbol.
     * @param dato valor a buscar
     * @return true si el dato está en el árbol, false en caso contrario
     * @throws IllegalArgumentException si el dato es nulo
     */
    public boolean buscar(T dato) {
        if(dato == null) {
            throw new IllegalArgumentException("El dato no puede ser nulo.");
        }
        return buscar(raiz, dato); // Iniciamos la búsqueda desde la raíz
    }

    /**
     * Busca recursivamente un dato en el subárbol cuya raíz es el nodo dado.
     * Recorre las claves del nodo para determinar en qué hijo continuar la búsqueda.
     * @param nodo nodo actual del recorrido
     * @param dato valor a buscar
     * @return true si el dato se encuentra, false en caso contrario
     */
    private boolean buscar(NodoB<T> nodo, T dato) {
        boolean respuesta = false;
        if (nodo != null) {
            int i = 0;
            // Avanzamos por las claves del nodo mientras el dato sea mayor que la clave actual
            while (i < nodo.getDatos().getSize() && dato.compareTo(nodo.getDatos().getAt(i)) > 0) {
                i++;
            }
            // Comprobamos si el dato coincide con la clave en la posición i
            if (i < nodo.getDatos().getSize() && dato.compareTo(nodo.getDatos().getAt(i)) == 0) {
                respuesta = true; // Dato encontrado en este nodo
            } else if (nodo.getHijos().isEmpty()) {
                respuesta = false; // Nodo hoja sin coincidencia: el dato no existe
            } else {
                respuesta = buscar(nodo.getHijos().getAt(i), dato); // Bajamos al hijo correspondiente y continuamos la búsqueda
            }
        }
        return respuesta;
    }

    /**
     * Añade un nuevo dato al árbol manteniendo las propiedades del Árbol B.
     * Si la inserción provoca que la raíz se desborde, se crea una nueva raíz.
     * @param dato valor a insertar
     * @throws IllegalArgumentException si el dato es nulo
     */
    public void add(T dato) {
        if(dato==null) {
            throw new IllegalArgumentException("El dato no puede ser nulo.");
        }
        else if (raiz == null) {
            // El árbol estaba vacío: creamos la raíz con el primer dato
            this.raiz = new NodoB<T>();
            raiz.getDatos().add(dato);
        } else {
            ResultadoSplit<T> bandeja = add(raiz, dato); // Insertamos recursivamente desde la raíz
            if (bandeja != null) {
                // La raíz se desbordó: creamos una nueva raíz con el dato promovido
                NodoB<T> nuevaRaiz = new NodoB<T>();
                nuevaRaiz.getDatos().add(bandeja.getDatoQueSube());    // El dato promovido es la única clave de la nueva raíz
                nuevaRaiz.getHijos().add(this.raiz);                   // La raíz anterior pasa a ser el hijo izquierdo
                nuevaRaiz.getHijos().add(bandeja.getHermanoDerecho()); // El nuevo hermano pasa a ser el hijo derecho
                this.raiz = nuevaRaiz; // Actualizamos la referencia a la raíz
            }
        }
    }

    /**
     * Inserta recursivamente un dato en el subárbol cuya raíz es el nodo dado.
     * Si el nodo alcanza el número máximo de claves tras la inserción, se realiza un split.
     * @param nodo nodo actual donde insertar
     * @param dato valor a insertar
     * @return un ResultadoSplit con el dato promovido y el nuevo hermano si hubo split, null si no
     */
    private ResultadoSplit<T> add(NodoB<T> nodo, T dato) {
        ResultadoSplit<T> respuesta = null;
        int i = 0;
        // Buscamos la posición correcta dentro del nodo para mantener el orden
        while (i < nodo.getDatos().getSize() && dato.compareTo(nodo.getDatos().getAt(i)) > 0) {
            i++;
        }
        if (nodo.getHijos().getSize() == 0) {
            // Nodo hoja: insertamos el dato directamente en la posición encontrada
            nodo.getDatos().insertAt(i, dato);
        } else {
            // Nodo interno: bajamos al hijo correspondiente y recogemos el posible resultado del split
            ResultadoSplit<T> bandeja = add(nodo.getHijos().getAt(i), dato);
            if (bandeja != null) {
                // Un hijo se desbordó: incorporamos el dato promovido y el nuevo hermano en este nodo
                T datopromovido = bandeja.getDatoQueSube();
                nodo.getDatos().insertAt(i, datopromovido);       // Insertamos el dato promovido en este nodo
                NodoB<T> nodohermano = bandeja.getHermanoDerecho();
                nodo.getHijos().insertAt(i + 1, nodohermano);     // Añadimos el nuevo nodo hijo a la derecha de la posición insertada
            }
        }
        if (nodo.getDatos().getSize() >= this.orden) {
            respuesta= split(nodo); // El nodo está lleno: lo dividimos y propagamos el resultado hacia arriba
        }
        return respuesta; // No hubo desbordamiento, no hay nada que propagar
    }

    /**
     * Divide un nodo lleno en dos nodos y devuelve el dato central que debe subir al padre.
     * La mitad izquierda de las claves queda en el nodo original,
     * La mitad derecha pasa a un nuevo nodo hermano.
     * Si el nodo tiene hijos, también se distribuyen entre los dos nodos.
     * @param nodo nodo a dividir
     * @return ResultadoSplit con el dato que sube al padre y el nuevo nodo hermano derecho
     */
    private ResultadoSplit<T> split(NodoB<T> nodo) {
        int indicemedio = nodo.getDatos().getSize() / 2; // Índice del dato central que subirá al padre
        T datomedio = nodo.getDatos().getAt(indicemedio); // Dato central que se promoverá al padre
        NodoB<T> nuevohermano = new NodoB<>();             // Nuevo nodo que contendrá la mitad derecha
        // Copiamos las claves de la mitad derecha al nuevo hermano
        for (int i = indicemedio + 1; i < nodo.getDatos().getSize(); i++) {
            nuevohermano.getDatos().add(nodo.getDatos().getAt(i));
        }
        // Eliminamos del nodo original las claves del centro hacia la derecha (inclusive el dato central)
        for (int i = nodo.getDatos().getSize() - 1; i >= indicemedio; i--) {
            nodo.getDatos().removeAt(i);
        }
        if (!nodo.getHijos().isEmpty()) {
            // Si el nodo tiene hijos, también distribuimos los hijos de la mitad derecha al nuevo hermano
            for (int i = indicemedio + 1; i < nodo.getHijos().getSize(); i++) {
                nuevohermano.getHijos().add(nodo.getHijos().getAt(i));
            }
            // Eliminamos del nodo original los hijos que pasaron al hermano
            for (int i = nodo.getHijos().getSize() - 1; i >= indicemedio + 1; i--) {
                nodo.getHijos().removeAt(i);
            }
        }
        ResultadoSplit<T> caja = new ResultadoSplit<>(datomedio, nuevohermano); // Empaquetamos el resultado del split
        return caja;
    }

    /**
     * Elimina recursivamente un dato del subárbol cuya raíz es el nodo dado.
     * Si el dato está en un nodo interno, se sustituye por su sucesor en orden (el menor del subárbol derecho).
     * Tras la eliminación, reequilibra el árbol mediante rotaciones o fusiones si algún hijo
     * queda con menos del mínimo de claves permitido.
     * @param nodo nodo actual del recorrido
     * @param dato valor a eliminar
     */
    private void remove(NodoB<T> nodo, T dato) {
        boolean necesitaRequilibrar = false; // Indica si algún hijo fue modificado y puede necesitar reequilibrio
        int i = 0;
        // Buscamos la posición del dato dentro de las claves del nodo actual
        while (i < nodo.getDatos().getSize() && dato.compareTo(nodo.getDatos().getAt(i)) > 0) {
            i++;
        }

        int ramaAReequilibrar = i; // Índice del hijo que podría quedar deficitario tras la eliminación

        if (i < nodo.getDatos().getSize() && dato.compareTo(nodo.getDatos().getAt(i)) == 0) {
            // El dato se encontró en este nodo
            if (nodo.getHijos().getSize() == 0) {
                // Nodo hoja: eliminamos el dato directamente, no hay hijos que reequilibrar
                nodo.getDatos().removeAt(i);
            } else {
                // Nodo interno: buscamos el sucesor en orden (el menor valor del subárbol derecho)
                NodoB<T> actual = nodo.getHijos().getAt(i + 1);
                while (!actual.getHijos().isEmpty()) {
                    actual = actual.getHijos().getAt(0); // Bajamos hasta la hoja más a la izquierda del subárbol derecho
                }
                T sucesor = actual.getDatos().getAt(0); // El sucesor es el primer dato de esa hoja

                // Sustituimos el dato eliminado por el sucesor en este nodo
                nodo.getDatos().removeAt(i);
                nodo.getDatos().insertAt(i, sucesor);

                // Eliminamos el sucesor de donde estaba (en el subárbol derecho)
                remove(nodo.getHijos().getAt(i + 1), sucesor);
                ramaAReequilibrar = i + 1; // El hijo que puede quedar deficitario es el derecho
                necesitaRequilibrar = true; // Modificamos un hijo interno: puede necesitar reequilibrio
            }
        } else {
            // El dato no está en este nodo: bajamos al hijo que corresponde por orden
            if (!nodo.getHijos().isEmpty()) {
                remove(nodo.getHijos().getAt(i), dato); // Continuamos la eliminación en el hijo
                ramaAReequilibrar = i;
                necesitaRequilibrar = true; // Modificamos un hijo: puede necesitar reequilibrio
            }
            // Si es hoja sin coincidencia: el dato no existe, necesitaRequilibrar queda false
        }

        // Solo reequilibramos si modificamos un hijo y éste quedó con menos del mínimo de claves
        int idx = ramaAReequilibrar;
        if (necesitaRequilibrar && !nodo.getHijos().isEmpty() && nodo.getHijos().getAt(idx).getDatos().getSize() < (this.orden - 1) / 2) {

            if (idx > 0 && nodo.getHijos().getAt(idx - 1).getDatos().getSize() > (this.orden - 1) / 2) {
                // El hermano izquierdo tiene claves de sobra: rotamos una clave hacia la derecha (préstamo izquierdo)
                NodoB<T> hijoSub = nodo.getHijos().getAt(idx);
                NodoB<T> hermanoIzq = nodo.getHijos().getAt(idx - 1);
                T datoPadre = nodo.getDatos().getAt(idx - 1);
                T ultimoDatoHermano = hermanoIzq.getDatos().removeAt(hermanoIzq.getDatos().getSize() - 1); // Quitamos la última clave del hermano izquierdo

                hijoSub.getDatos().insertAt(0, datoPadre);             // La clave del padre baja al inicio del hijo deficitario
                nodo.getDatos().removeAt(idx - 1);
                nodo.getDatos().insertAt(idx - 1, ultimoDatoHermano);  // La última clave del hermano sube al padre

                if (!hermanoIzq.getHijos().isEmpty()) {
                    // También movemos el último hijo del hermano izquierdo al inicio del hijo deficitario
                    NodoB<T> hijoPasado = hermanoIzq.getHijos().removeAt(hermanoIzq.getHijos().getSize() - 1);
                    hijoSub.getHijos().insertAt(0, hijoPasado);
                }
            } else if (idx < nodo.getHijos().getSize() - 1 && nodo.getHijos().getAt(idx + 1).getDatos().getSize() > (this.orden - 1) / 2) {
                // El hermano derecho tiene claves de sobra: rotamos una clave hacia la izquierda (préstamo derecho)
                NodoB<T> hijoSub = nodo.getHijos().getAt(idx);
                NodoB<T> hermanoDer = nodo.getHijos().getAt(idx + 1);
                T datoPadre = nodo.getDatos().getAt(idx);
                T primerDatoHermano = hermanoDer.getDatos().removeAt(0); // Quitamos la primera clave del hermano derecho

                hijoSub.getDatos().insertAt(hijoSub.getDatos().getSize(), datoPadre); // La clave del padre baja al final del hijo deficitario
                nodo.getDatos().removeAt(idx);
                nodo.getDatos().insertAt(idx, primerDatoHermano);                     // La primera clave del hermano sube al padre

                if (!hermanoDer.getHijos().isEmpty()) {
                    // También movemos el primer hijo del hermano derecho al final del hijo deficitario
                    NodoB<T> hijoPasado = hermanoDer.getHijos().removeAt(0);
                    hijoSub.getHijos().insertAt(hijoSub.getHijos().getSize(), hijoPasado);
                }
            } else {
                // Ningún hermano puede prestar claves: debemos fusionar (merge)
                if (idx > 0) {
                    // Fusionamos el hijo deficitario con su hermano izquierdo
                    NodoB<T> hijoSub = nodo.getHijos().getAt(idx);
                    NodoB<T> hermanoIzq = nodo.getHijos().getAt(idx - 1);
                    T datoDelPadre = nodo.getDatos().removeAt(idx - 1); // La clave separadora del padre baja al nodo fusionado

                    hermanoIzq.getDatos().insertAt(hermanoIzq.getDatos().getSize(), datoDelPadre); // Añadimos la clave del padre al final del hermano izquierdo
                    for (int j = 0; j < hijoSub.getDatos().getSize(); j++) {
                        // Copiamos todas las claves del hijo deficitario al hermano izquierdo
                        hermanoIzq.getDatos().insertAt(hermanoIzq.getDatos().getSize(), hijoSub.getDatos().getAt(j));
                    }
                    for (int k = 0; k < hijoSub.getHijos().getSize(); k++) {
                        // Copiamos todos los hijos del hijo deficitario al hermano izquierdo
                        hermanoIzq.getHijos().insertAt(hermanoIzq.getHijos().getSize(), hijoSub.getHijos().getAt(k));
                    }
                    nodo.getHijos().removeAt(idx); // Eliminamos el hijo deficitario (ya absorbido por el hermano)
                } else {
                    // Fusionamos el hijo deficitario con su hermano derecho
                    NodoB<T> hijoSub = nodo.getHijos().getAt(idx);
                    NodoB<T> hermanoDer = nodo.getHijos().getAt(idx + 1);
                    T datoDelPadre = nodo.getDatos().removeAt(idx); // La clave separadora del padre baja al nodo fusionado

                    hijoSub.getDatos().insertAt(hijoSub.getDatos().getSize(), datoDelPadre); // Añadimos la clave del padre al final del hijo deficitario
                    for (int j = 0; j < hermanoDer.getDatos().getSize(); j++) {
                        // Copiamos todas las claves del hermano derecho al hijo deficitario
                        hijoSub.getDatos().insertAt(hijoSub.getDatos().getSize(), hermanoDer.getDatos().getAt(j));
                    }
                    for (int k = 0; k < hermanoDer.getHijos().getSize(); k++) {
                        // Copiamos todos los hijos del hermano derecho al hijo deficitario
                        hijoSub.getHijos().insertAt(hijoSub.getHijos().getSize(), hermanoDer.getHijos().getAt(k));
                    }
                    nodo.getHijos().removeAt(idx + 1); // Eliminamos el hermano derecho (ya absorbido por el hijo)
                }
            }
        }
    }

    /**
     * Elimina un dato del árbol y reajusta la raíz si quedó vacía tras la eliminación.
     * @param dato valor a eliminar
     * @throws IllegalArgumentException si el dato es nulo
     */
    public void remove(T dato) {
        if(dato == null) {
            throw new IllegalArgumentException("El dato no puede ser nulo.");
        }
        else if (this.raiz == null) return; // El árbol está vacío, no hay nada que eliminar

        remove(this.raiz, dato); // Eliminamos el dato de forma recursiva desde la raíz

        // Si la raíz quedó sin claves pero todavía tiene hijos, el único hijo sube como nueva raíz
        if (this.raiz.getDatos().isEmpty() && !this.raiz.getHijos().isEmpty()) {
            this.raiz = this.raiz.getHijos().getAt(0);
        }
        // Si la raíz quedó sin claves y sin hijos, el árbol ha quedado completamente vacío
        else if (this.raiz.getDatos().isEmpty()) {
            this.raiz = null;
        }
    }
}
