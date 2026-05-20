package juego.model.habitacion;
import juego.model.EstructurasUtilizadas.Grafos.Arista;
import juego.model.EstructurasUtilizadas.Grafos.EntradaAdyacencia;
import juego.model.EstructurasUtilizadas.Grafos.Grafo;
import juego.model.EstructurasUtilizadas.Grafos.Nodo;
import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;

/**
 * Grafo de habitaciones del juego.
 * Envuelve un Grafo genérico y mantiene una correspondencia entre los identificadores
 * numéricos de cada Habitacion y los nombres de nodo usados internamente (por ejemplo "HAB1").
 * Permite añadir habitaciones, conectarlas, buscar rutas y calcular distancias entre ellas.
 */
public class GrafoHabitacion{

    private Grafo grafo;                                               // Grafo dirigido subyacente que almacena las conexiones entre habitaciones
    private ListaSimplementeEnlazada<ParNombreHabitacion> habitaciones; // Mapeo de nombre de nodo a objeto Habitacion
    private ListaSimplementeEnlazada<ParIdNombre> idnombre;             // Mapeo de id numérico a nombre de nodo

    /**
     * Crea un grafo de habitaciones vacío.
     */
    public GrafoHabitacion() {
        this.grafo = new Grafo(0);                    // Grafo dirigido con contador de ids iniciado en 0
        this.habitaciones=new ListaSimplementeEnlazada(); // Lista de pares nombre-habitación
        this.idnombre=new ListaSimplementeEnlazada();     // Lista de pares id-nombre
    }

    /**
     * Añade una habitación al grafo asignándole un nombre de nodo único ("HAB" + id).
     * Si la habitación es null no hace nada.
     * @param habitacion habitación a añadir al grafo
     */
    public void agregarHabitacion (Habitacion habitacion) {
        if(habitacion!=null){
            String nombre = "HAB" + habitacion.getId(); // Generamos el nombre de nodo a partir del id
            idnombre.add(new ParIdNombre(habitacion.getId(),nombre));    // Registramos el par id-nombre
            habitaciones.add(new ParNombreHabitacion(nombre,habitacion)); // Registramos el par nombre-habitación
            grafo.agregarNodo(nombre); // Añadimos el nodo al grafo subyacente
        }
    }

    /**
     * Conecta dos habitaciones mediante una arista dirigida con etiqueta "conexión".
     * Las habitaciones deben estar previamente añadidas al grafo.
     * @param habitacion1 habitación de partida de la conexión
     * @param habitacion2 habitación de llegada de la conexión
     * @throws IllegalArgumentException si alguna de las habitaciones es null o no existe en el grafo
     */
    public void conectarHabitaciones(Habitacion habitacion1, Habitacion habitacion2) {
        if (habitacion1==null||habitacion2==null){
            throw new IllegalArgumentException("La habitacion no puede ser nulas");
        }
        String nombre1 = buscarNombrePorId(habitacion1.getId()); // Obtenemos el nombre del nodo de la habitación 1
        String nombre2 = buscarNombrePorId(habitacion2.getId()); // Obtenemos el nombre del nodo de la habitación 2
        if (nombre1 == null || nombre2 == null) {
            throw new IllegalArgumentException("Una o ambas habitaciones no existen en el grafo"); // Alguna habitación no fue añadida antes
        }
        grafo.agregarArista(nombre1, nombre2, "conexión"); // Añadimos la arista dirigida entre los dos nodos
        grafo.agregarArista(nombre2, nombre1, "conexión"); // Añadimos la arista inversa para hacerla bidireccional
    }

    /**
     * Busca y devuelve la habitación asociada al nombre de nodo indicado.
     * @param nombre nombre del nodo en el grafo
     * @return habitación correspondiente, o null si no existe
     */
    private Habitacion buscarHabitacionPorNombre(String nombre) {
        if(nombre == null){
            return null;
        }
        for (int j = 0; j < habitaciones.getSize(); j++) {
            ParNombreHabitacion par = habitaciones.getAt(j);
            if (par.getNombreNodo().equals(nombre)) {
                return par.getHabitacion();
            }
        }
        return null;
    }

    /**
     * Devuelve la habitación con el identificador numérico indicado.
     * @param id identificador de la habitación a buscar
     * @return habitación con ese id, o null si no existe en el grafo
     */
    public Habitacion obtenerHabitacion(int id) {
        String nombre = buscarNombrePorId(id);          // Obtenemos el nombre del nodo a partir del id
        if (nombre == null) return null;                // El id no está registrado en el grafo
        return buscarHabitacionPorNombre(nombre);       // Buscamos la habitación por nombre de nodo
    }

    /**
     * Devuelve la lista de habitaciones directamente conectadas a la habitación indicada.
     * @param habitacion habitación de la que obtener las conexiones
     * @return lista de habitaciones vecinas, vacía si no tiene conexiones o es null
     * @throws IllegalArgumentException si la habitación es null
     */
    public ListaSimplementeEnlazada<Habitacion> obtenerHabitaciones(Habitacion habitacion) {
        if (habitacion == null) {
            throw new IllegalArgumentException("La habitación no puede ser nula");
        }
        ListaSimplementeEnlazada<Habitacion> respuesta = new ListaSimplementeEnlazada();
        EntradaAdyacencia entrada = grafo.buscarEntrada("HAB" + habitacion.getId()); // Buscamos la entrada del nodo en el grafo
        if (entrada == null) {
            return respuesta; // La habitación no tiene conexiones registradas: devolvemos lista vacía
        }
        ListaSimplementeEnlazada<Arista> aristas = entrada.getAristas(); // Aristas que salen de la habitación
        for (int i = 0; i < aristas.getSize(); i++) {
            Arista arista = aristas.getAt(i);
            String nombreDestino = arista.getDestino().getNombre();              // Nombre del nodo destino
            Habitacion habitacionDestino = buscarHabitacionPorNombre(nombreDestino); // Habitación correspondiente
            if (habitacionDestino != null) {
                respuesta.add(habitacionDestino); // Añadimos la habitación vecina al resultado
            }
        }
        return respuesta;
    }

    /**
     * Busca y devuelve la ruta más corta (en número de conexiones) entre dos habitaciones.
     * Utiliza el algoritmo de BFS del grafo subyacente.
     * @param habitacioninicio habitación de partida
     * @param habitacionfinal  habitación de destino
     * @return lista de habitaciones en orden desde inicio hasta destino, o null si no hay ruta
     */
    public ListaSimplementeEnlazada<Habitacion> buscarRuta(Habitacion habitacioninicio, Habitacion habitacionfinal) {
        if (habitacioninicio == null || habitacionfinal == null) {
            throw new IllegalArgumentException("Las habitaciones no pueden ser nulas");
        }
        if (habitacioninicio.getId() == habitacionfinal.getId()) {
            ListaSimplementeEnlazada<Habitacion> respuesta = new ListaSimplementeEnlazada();
            respuesta.add(habitacioninicio);
            return respuesta;
        }
        String nombre1= "HAB"+ habitacioninicio.getId(); // Nombre del nodo de inicio
        String nombre2= "HAB"+ habitacionfinal.getId();  // Nombre del nodo de destino
        ListaSimplementeEnlazada<Nodo>nodos=grafo.caminoMínimo(nombre1,nombre2); // BFS en el grafo subyacente
        if(nodos==null){
            return null; // No existe ruta entre las dos habitaciones
        }
        ListaSimplementeEnlazada<Habitacion> respuesta = new ListaSimplementeEnlazada();
        for (int i=0; i<nodos.getSize();i++){
            String nombreNodo= nodos.getAt(i).getNombre();
            for(int j=0; j<habitaciones.getSize();j++){
                if(habitaciones.getAt(j).getNombreNodo().equals(nombreNodo)){
                    respuesta.add(habitaciones.getAt(j).getHabitacion()); // Añadimos la habitación correspondiente al nodo
                }
            }
        }
        return respuesta;
    }

    /**
     * Busca el nombre de nodo asociado al identificador de habitación indicado.
     * Para en cuanto lo encuentra sin recorrer el resto de la lista.
     * @param idBuscado identificador numérico de la habitación
     * @return nombre del nodo en el grafo, o null si el id no está registrado
     */
    private String buscarNombrePorId(int idBuscado) {
        for (int i = 0; i < idnombre.getSize(); i++) {
            ParIdNombre par = idnombre.getAt(i);
            if (par.getIdHabitacion() == idBuscado) {
                return par.getNombreNodo(); // Id encontrado: devolvemos el nombre del nodo
            }
        }
        return null; // El id no está registrado en el grafo
    }

    /**
     * Calcula el número de conexiones (aristas) en la ruta más corta entre dos habitaciones.
     * @param habitacion1 habitación de partida
     * @param habitacion2 habitación de destino
     * @return número de aristas de la ruta mínima, o -1 si no existe ruta
     */
    public int calcularDistancia(Habitacion habitacion1, Habitacion habitacion2) {
        ListaSimplementeEnlazada<Habitacion> ruta = buscarRuta(habitacion1, habitacion2);
        if (ruta == null) {
            return -1; // No hay ruta entre las dos habitaciones
        }
        return ruta.getSize() - 1; // El número de aristas es el número de nodos menos 1
    }

    /**
     * Comprueba si todas las habitaciones del grafo están conectadas entre sí.
     * @return true si el grafo es conexo, false si hay habitaciones aisladas
     */
    public boolean esConexo(){
        return !grafo.esDisjunto(); // El grafo es conexo si no es disjunto
    }

    /**
     * Devuelve una representación en texto de todas las habitaciones del grafo.
     * @return cadena con la representación de cada habitación separada por saltos de línea
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<habitaciones.getSize();i++){
            ParNombreHabitacion par=habitaciones.getAt(i);
            sb.append(par.getHabitacion().toString()+ "\n" ); // Añadimos la representación de cada habitación
        }
        return sb.toString();
    }
}
