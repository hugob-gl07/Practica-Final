package EstructurasUtilizadas.Grafos;
import EstructurasUtilizadas.Colas.Cola.Cola;
import EstructurasUtilizadas.Colas.ColaPrioridad.ColaPrioridadMin;
import EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;
import EstructurasUtilizadas.Pila.Pila;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Implementación de un grafo dirigido y etiquetado mediante lista de adyacencia.
 * Cada nodo se identifica por nombre y puede conectarse con otros nodos a través de aristas
 * que llevan una etiqueta (relación). Permite cargar el grafo desde un fichero JSON,
 * buscar caminos mínimos (BFS) y consultar relaciones semánticas entre nodos.
 */
public class Grafo {
    private ListaSimplementeEnlazada<EntradaAdyacencia> entradas; // Lista de adyacencia: un registro por cada nodo del grafo
    private int contador; // Contador para asignar un id único a cada nuevo nodo

    /**
     * Crea un grafo vacío con el contador de ids iniciado en el valor indicado.
     * @param contador valor inicial del contador de ids de nodo
     */
    public Grafo(int contador) {
        this.entradas = new ListaSimplementeEnlazada<>(); // La lista de nodos empieza vacía
        this.contador = contador;
    }

    /**
     * Busca en la lista de adyacencia la entrada del nodo con el nombre indicado.
     * Para en cuanto la encuentra sin recorrer el resto de la lista.
     * @param nombre nombre del nodo a buscar
     * @return entrada del nodo si existe, null si no se encuentra
     */
    public EntradaAdyacencia buscarEntrada(String nombre) {
        EntradaAdyacencia resultado = null;
        int i = 0;
        while (i < entradas.getSize() && resultado == null) {
            if (entradas.getAt(i).getNodo().getNombre().equals(nombre)) {
                resultado = entradas.getAt(i); // Nodo encontrado: guardamos la entrada y el while termina
            }
            i++;
        }
        return resultado; // null si no existe ningún nodo con ese nombre
    }

    /**
     * Añade un nuevo nodo con el nombre indicado al grafo.
     * Si ya existe un nodo con ese nombre no hace nada para evitar duplicados.
     * @param nombre nombre del nuevo nodo
     * @throws IllegalArgumentException si el nombre es nulo
     */
    public void agregarNodo(String nombre) {
        if(nombre==null){
            throw new IllegalArgumentException("El nombre no puede ser nulo.");
        }
        else if (buscarEntrada(nombre) == null) {
            Nodo nuevoNodo = new Nodo(contador, nombre);                       // Creamos el nodo con el id actual del contador
            EntradaAdyacencia nuevaEntrada = new EntradaAdyacencia(nuevoNodo); // Creamos su entrada en la lista de adyacencia
            entradas.add(nuevaEntrada); // Añadimos la entrada al grafo
            contador++;                 // Incrementamos el contador para que el siguiente nodo tenga un id diferente
        }
    }

    /**
     * Añade una arista dirigida desde el nodo origen hasta el nodo destino con la etiqueta indicada.
     * Si alguno de los dos nodos no existe en el grafo, lo crea antes de añadir la arista.
     * Al ser un grafo dirigido, la arista solo aparece en la lista del nodo origen.
     * @param origen   nombre del nodo de partida
     * @param destino  nombre del nodo de llegada
     * @param etiqueta etiqueta o relación que describe la arista
     * @throws IllegalArgumentException si alguno de los parámetros es nulo
     */
    public void agregarArista(String origen, String destino, String etiqueta) {
        if(origen == null || destino == null || etiqueta == null) {
            throw new IllegalArgumentException("Error: No se permiten valores nulos para origen, destino o etiqueta"); // Validamos que no se pasen valores nulos
        }
        agregarNodo(origen);  // Creamos el nodo origen si no existe todavía
        agregarNodo(destino); // Creamos el nodo destino si no existe todavía
        EntradaAdyacencia entradaOrigen  = buscarEntrada(origen);  // Buscamos la entrada del nodo origen
        EntradaAdyacencia entradaDestino = buscarEntrada(destino); // Buscamos la entrada del nodo destino
        Arista arista = new Arista(entradaOrigen.getNodo(), etiqueta, entradaDestino.getNodo()); // Creamos la arista entre los dos nodos
        entradaOrigen.getAristas().add(arista); // Añadimos la arista a la lista del nodo origen
    }

    /**
     * Calcula el camino mínimo (en número de aristas) entre dos nodos usando BFS.
     * Para en cuanto localiza el destino sin continuar explorando nodos innecesarios.
     * Si alguno de los nodos no existe o no hay camino, devuelve null.
     * @param origen  nombre del nodo de partida
     * @param destino nombre del nodo de llegada
     * @return lista de nodos del camino mínimo en orden origen → destino, o null si no existe
     */
    public ListaSimplementeEnlazada<Nodo> caminoMínimo(String origen, String destino) {
        ListaSimplementeEnlazada<Nodo> resultado = null;
        if (buscarEntrada(origen) != null && buscarEntrada(destino) != null) {
            Cola<String> cola = new Cola<>();                                   // Cola BFS para gestionar el orden de visita
            ListaSimplementeEnlazada<String> visitados = new ListaSimplementeEnlazada<>(); // Nodos ya visitados para no procesarlos dos veces
            ListaSimplementeEnlazada<Arista> padres = new ListaSimplementeEnlazada<>();    // Aristas del árbol BFS para reconstruir el camino
            cola.enqueue(origen);  // Empezamos el BFS desde el nodo origen
            visitados.add(origen); // Marcamos el nodo origen como visitado
            while (!cola.isEmpty() && resultado == null) {
                // resultado == null en el while garantiza que paramos al encontrar el destino
                String nodoactual = cola.dequeue(); // Sacamos el siguiente nodo a procesar
                EntradaAdyacencia entradaActual = buscarEntrada(nodoactual);
                ListaSimplementeEnlazada<Arista> aristas = entradaActual.getAristas(); // Aristas que salen del nodo actual
                if (nodoactual.equals(destino)) {
                    resultado = reconstruirCamino(padres, origen, destino); // Destino alcanzado: reconstruimos el camino
                } else {
                    // Solo exploramos vecinos si aún no hemos llegado al destino
                    for (int i = 0; i < aristas.getSize(); i++) {
                        Arista aristaActual = aristas.getAt(i);
                        String nombreVecino = aristaActual.getDestino().getNombre(); // Nombre del vecino al que lleva esta arista
                        if (visitados.get(nombreVecino) == null) {
                            visitados.add(nombreVecino);  // Marcamos el vecino como visitado
                            cola.enqueue(nombreVecino);   // Lo encolamos para procesarlo después
                            padres.add(aristaActual);     // Guardamos la arista que llevó al vecino para reconstruir el camino
                        }
                    }
                }
            }
        }
        return resultado;
    }

    /**
     * Reconstruye el camino desde el origen hasta el destino a partir del árbol BFS almacenado en padres.
     * Recorre las aristas de destino a origen usando una pila para invertir el orden y devolver el camino correcto.
     * Si no existe camino entre los nodos devuelve null.
     * @param padres  lista de aristas del árbol BFS
     * @param origen  nombre del nodo de partida
     * @param destino nombre del nodo de llegada
     * @return lista de nodos del camino en orden origen → destino, o null si no hay camino
     */
    private ListaSimplementeEnlazada<Nodo> reconstruirCamino(ListaSimplementeEnlazada<Arista> padres, String origen, String destino) {
        Pila<Nodo> pila = new Pila<Nodo>(); // EstructurasUtilizadas.Pila para invertir el camino (lo recorremos de destino a origen)
        String actual = destino;
        boolean encontrado = true;
        while (!actual.equals(origen) && encontrado) {
            encontrado = false;
            for (int i = 0; i < padres.getSize() && !encontrado; i++) {
                Arista a = padres.getAt(i);
                if (a.getDestino().getNombre().equals(actual)) {
                    pila.push(a.getDestino());          // Añadimos el nodo actual al camino
                    actual = a.getOrigen().getNombre(); // Retrocedemos al nodo padre
                    encontrado = true;
                }
            }
        }
        pila.push(buscarEntrada(origen).getNodo()); // Añadimos el nodo origen al inicio del camino
        ListaSimplementeEnlazada<Nodo> lista = new ListaSimplementeEnlazada<>();
        while (!pila.isEmpty()) {
            lista.add(pila.pop()); // Vaciamos la pila para obtener el camino en orden correcto (origen → destino)
        }
        if (!actual.equals(origen)) {
            lista = null; // Si no llegamos al origen no existe camino válido
        }
        return lista;
    }

    /**
     * Comprueba si el grafo es disjunto (no conexo).
     * Realiza un BFS no dirigido desde el primer nodo y comprueba si todos los nodos son alcanzables.
     * @return true si el grafo es disjunto (hay nodos inalcanzables), false si es conexo
     */
    public boolean esDisjunto() {
        boolean resultado = false;
        if (!entradas.isEmpty()) {
            Cola<String> cola = new Cola<>();                                   // Cola BFS para gestionar el orden de visita
            ListaSimplementeEnlazada<String> visitados = new ListaSimplementeEnlazada<>(); // Nodos ya visitados
            cola.enqueue(entradas.getAt(0).getNodo().getNombre()); // Empezamos por el primer nodo de la lista de adyacencia
            visitados.add(entradas.getAt(0).getNodo().getNombre());
            while (!cola.isEmpty()) {
                String nodoactual = cola.dequeue(); // Sacamos el siguiente nodo a procesar
                EntradaAdyacencia entradaActual = buscarEntrada(nodoactual);
                ListaSimplementeEnlazada<Arista> aristas = entradaActual.getAristas(); // Aristas que salen del nodo actual
                // Exploramos vecinos a los que salen aristas del nodo actual (aristas salientes)
                for (int i = 0; i < aristas.getSize(); i++) {
                    Arista aristaActual = aristas.getAt(i);
                    String nombreVecino = aristaActual.getDestino().getNombre(); // Nombre del vecino al que llega la arista
                    if (visitados.get(nombreVecino) == null) {
                        visitados.add(nombreVecino);
                        cola.enqueue(nombreVecino);
                    }
                }
                // También exploramos aristas entrantes (tratamos el grafo como no dirigido para la conectividad)
                for (int i = 0; i < entradas.getSize(); i++) {
                    ListaSimplementeEnlazada<Arista> aristasotro = entradas.getAt(i).getAristas();
                    for (int j = 0; j < aristasotro.getSize(); j++) {
                        if (aristasotro.getAt(j).getDestino().getNombre().equals(nodoactual)) {
                            String nombrevecino = entradas.getAt(i).getNodo().getNombre(); // Nodo desde el que llega la arista entrante
                            if (visitados.get(nombrevecino) == null) {
                                visitados.add(nombrevecino);
                                cola.enqueue(nombrevecino);
                            }
                        }
                    }
                }
            }
            if (visitados.getSize() != entradas.getSize()) {
                resultado = true; // No se alcanzaron todos los nodos: el grafo es disjunto
            }
        }
        return resultado;
    }

    /**
     * Dado un nodo sujeto y una relación (etiqueta de arista), devuelve el nombre del nodo
     * al que apunta la primera arista con esa relación que sale del sujeto.
     * Para en cuanto la encuentra sin recorrer el resto de las aristas.
     * @param sujeto   nombre del nodo de partida
     * @param relacion etiqueta de la arista a buscar
     * @return nombre del nodo destino si se encuentra la relación, null en caso contrario
     */
    public String getValor(String sujeto, String relacion) {
        String resultado = null;
        EntradaAdyacencia entradaActual = buscarEntrada(sujeto);
        if (entradaActual != null) {
            ListaSimplementeEnlazada<Arista> aristas = entradaActual.getAristas();
            int i = 0;
            while (i < aristas.getSize() && resultado == null) {
                // resultado == null en el while garantiza que paramos al encontrar la relación
                if (aristas.getAt(i).getEtiqueta().equals(relacion)) {
                    resultado = aristas.getAt(i).getDestino().getNombre(); // Relación encontrada: guardamos el destino y el while termina
                }
                i++;
            }
        }
        return resultado;
    }

    /**
     * Devuelve los nombres de todos los nodos que tienen una arista con la relación y el valor indicados.
     * Es decir, busca todos los sujetos que cumplen: sujeto --relacion--> valor.
     * @param relacion etiqueta de arista a buscar
     * @param valor    nombre del nodo destino buscado
     * @return lista de nombres de nodos origen que cumplen la condición
     */
    public ListaSimplementeEnlazada<String> buscarPorRelacionYValor(String relacion, String valor) {
        ListaSimplementeEnlazada<String> lista = new ListaSimplementeEnlazada<>();
        for (int i = 0; i < entradas.getSize(); i++) {
            EntradaAdyacencia entrada = entradas.getAt(i);
            ListaSimplementeEnlazada<Arista> aristas = entrada.getAristas();
            for (int j = 0; j < aristas.getSize(); j++) {
                // Comprobamos si la arista tiene la relación buscada y apunta al valor buscado
                if (aristas.getAt(j).getEtiqueta().equals(relacion) && aristas.getAt(j).getDestino().getNombre().equals(valor)) {
                    lista.add(aristas.getAt(j).getOrigen().getNombre()); // Añadimos el nodo origen a los resultados
                }
            }
        }
        return lista;
    }

    /**
     * Devuelve los nombres de todos los nodos destino alcanzables mediante
     * una arista con la relación indicada, desde cualquier nodo del grafo.
     * @param relacion etiqueta de arista a buscar
     * @return lista de nombres de nodos destino de todas las aristas con esa relación
     */
    public ListaSimplementeEnlazada<String> listarValoresPorRelación(String relacion) {
        ListaSimplementeEnlazada<String> lista = new ListaSimplementeEnlazada<>();
        for (int i = 0; i < entradas.getSize(); i++) {
            EntradaAdyacencia entrada = entradas.getAt(i);
            ListaSimplementeEnlazada<Arista> aristas = entrada.getAristas();
            for (int j = 0; j < aristas.getSize(); j++) {
                if (aristas.getAt(j).getEtiqueta().equals(relacion)) {
                    lista.add(aristas.getAt(j).getDestino().getNombre()); // Añadimos el destino si la etiqueta coincide
                }
            }
        }
        return lista;
    }

    /**
     * Devuelve la lista de tipos de nodo distintos presentes en el grafo.
     * El tipo se obtiene tomando el prefijo antes del primer carácter ':' del nombre del nodo.
     * Por ejemplo, el nodo "Persona:Juan" tiene tipo "Persona".
     * @return lista de tipos de nodo sin duplicados
     */
    public ListaSimplementeEnlazada<String> getTiposdeNodos() {
        ListaSimplementeEnlazada<String> lista = new ListaSimplementeEnlazada<>();
        for (int i = 0; i < entradas.getSize(); i++) {
            String nodo = entradas.getAt(i).getNodo().getNombre();
            String prefijo = nodo.split(":")[0]; // Extraemos el tipo como la parte antes de ':'
            if (lista.get(prefijo) == null) {
                lista.add(prefijo); // Solo añadimos el tipo si no está ya en la lista
            }
        }
        return lista;
    }

    /**
     * Extrae el valor asociado a una clave en una línea de texto con formato JSON.
     * Busca el patrón {@code "clave": "valor"} y devuelve el valor entre comillas.
     * @param linea línea de texto donde buscar
     * @param clave nombre del campo JSON a extraer
     * @return valor del campo si se encuentra, null en caso contrario
     */
    private String extraerValor(String linea, String clave) {
        String resultado = null;
        int posicionclave = linea.indexOf("\"" + clave + "\""); // Buscamos la clave entre comillas
        if (posicionclave != -1) {
            int posDospuntos = linea.indexOf(":", posicionclave);   // Localizamos los dos puntos después de la clave
            int posApertura  = linea.indexOf("\"", posDospuntos);   // Primera comilla del valor
            int posCierre    = linea.indexOf("\"", posApertura + 1); // Segunda comilla del valor
            resultado = linea.substring(posApertura + 1, posCierre); // Extraemos el texto entre las dos comillas
        }
        return resultado;
    }

    /**
     * Carga el grafo desde un fichero JSON con tripletas de la forma sujeto-predicado-objeto.
     * Cada línea que contenga el campo "sujeto" se interpreta como una arista:
     * {@code sujeto --predicado--> objeto}.
     * Usa try-with-resources para garantizar que el fichero se cierra siempre,
     * incluso si ocurre una excepción durante la lectura.
     * @param ruta ruta al fichero JSON a cargar
     * @throws RuntimeException si el fichero no existe o hay un error de lectura
     */
    public void cargarDesdeJson(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            // try-with-resources: br.close() se llama automáticamente al salir del bloque
            String linea = br.readLine();
            while (linea != null) {
                if (linea.contains("sujeto")) {
                    // Extraemos los tres campos de la tripleta
                    String sujeto    = extraerValor(linea, "sujeto");
                    String predicado = extraerValor(linea, "predicado");
                    String objeto    = extraerValor(linea, "objeto");
                    if (sujeto != null && predicado != null && objeto != null) {
                        agregarArista(sujeto, objeto, predicado); // Añadimos la arista al grafo
                    }
                }
                linea = br.readLine(); // Pasamos a la siguiente línea
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e); // El fichero no existe
        } catch (IOException e) {
            throw new RuntimeException(e); // Error de lectura o cierre
        }
    }

    /**
     * Calcula el camino de menor coste total (suma de pesos de aristas) entre dos nodos
     * usando el algoritmo de Dijkstra con cola de prioridad mínima.
     * Inicializa la distancia del origen a 0 y el resto a infinito,
     * y va confirmando nodos en orden de distancia creciente.
     * @param origen  nombre del nodo de partida
     * @param destino nombre del nodo de llegada
     * @return lista de nodos del camino de menor coste en orden origen → destino, o null si no existe
     */
    public ListaSimplementeEnlazada<Nodo> dijkstra(String origen, String destino) {
        ListaSimplementeEnlazada<Nodo> resultado = null;
        if (buscarEntrada(origen) != null && buscarEntrada(destino) != null) {
            ListaSimplementeEnlazada<EntradaDistancia> distancias = new ListaSimplementeEnlazada<>();
            for (int i = 0; i < entradas.getSize(); i++) {
                String nombrenodo = entradas.getAt(i).getNodo().getNombre();
                if (nombrenodo.equals(origen)) {
                    distancias.add(new EntradaDistancia(nombrenodo, 0)); // La distancia al nodo origen es 0
                } else {
                    distancias.add(new EntradaDistancia(nombrenodo, Integer.MAX_VALUE)); // La distancia a los demás nodos es inicialmente infinita
                }
            }
            ColaPrioridadMin<EntradaDistancia> min = new ColaPrioridadMin<>();
            min.enqueue(buscarDistancia(distancias, origen)); // Empezamos por el nodo origen
            ListaSimplementeEnlazada<Arista> padres = new ListaSimplementeEnlazada<>();
            ListaSimplementeEnlazada<String> confirmados = new ListaSimplementeEnlazada<>();
            while (!min.isEmpty()) {
                EntradaDistancia actual = min.dequeue(); // Sacamos el nodo con la menor distancia acumulada
                if (confirmados.get(actual.getNodo()) == null) {
                    confirmados.add(actual.getNodo()); // Marcamos el nodo actual como confirmado (su distancia ya es definitiva)
                    if (actual.getNodo().equals(destino)) {
                        // Destino alcanzado: recogemos los padres y reconstruimos el camino
                        for (int i = 0; i < distancias.getSize(); i++) {
                            Arista padre = distancias.getAt(i).getPadre();
                            if (padre != null) {
                                padres.add(padre); // Añadimos las aristas padre para reconstruir el camino
                            }
                        }
                        resultado=reconstruirCamino(padres, origen, destino);
                    }
                    EntradaAdyacencia entrada = buscarEntrada(actual.getNodo());
                    ListaSimplementeEnlazada<Arista> aristas = entrada.getAristas();
                    for (int i = 0; i < aristas.getSize(); i++) {
                        Arista arista = aristas.getAt(i);
                        String nodovecino = arista.getDestino().getNombre();
                        EntradaDistancia entradavecino = buscarDistancia(distancias, nodovecino);
                        // Comprobamos que la distancia actual sea finita para evitar desbordamiento al sumar
                        if (entradavecino != null && actual.getDistancia() != Integer.MAX_VALUE) {
                            int nuevadistancia = actual.getDistancia() + arista.getPeso(); // Seguro: distancia actual es finita
                            if (nuevadistancia < entradavecino.getDistancia()) {
                                entradavecino.setDistancia(nuevadistancia); // Actualizamos con el camino más corto encontrado
                                entradavecino.setPadre(arista);             // Guardamos la arista que produjo esta mejora
                                min.enqueue(entradavecino);                 // Reinsertamos el vecino con la nueva distancia
                            }
                        }
                    }
                }
            }
        }
        return resultado; // No existe camino entre origen y destino
    }

    /**
     * Busca en la tabla de distancias la entrada correspondiente al nodo con el nombre indicado.
     * Para en cuanto la encuentra sin recorrer el resto de la lista.
     * @param distancias tabla de distancias de Dijkstra
     * @param nombre     nombre del nodo a buscar
     * @return entrada de distancia del nodo si existe, null en caso contrario
     */
    private EntradaDistancia buscarDistancia(ListaSimplementeEnlazada<EntradaDistancia> distancias, String nombre) {
        EntradaDistancia resultado = null;
        int i = 0;
        while (i < distancias.getSize() && resultado == null) {
            if (distancias.getAt(i).getNodo().equals(nombre)) {
                resultado = distancias.getAt(i); // Entrada encontrada: el while termina
            }
            i++;
        }
        return resultado;
    }
}
