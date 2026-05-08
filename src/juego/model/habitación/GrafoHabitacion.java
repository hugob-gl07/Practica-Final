package juego.model.habitación;
import juego.model.EstructurasUtilizadas.Grafos.Arista;
import juego.model.EstructurasUtilizadas.Grafos.EntradaAdyacencia;
import juego.model.EstructurasUtilizadas.Grafos.Grafo;
import juego.model.EstructurasUtilizadas.Grafos.Nodo;
import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;


public class GrafoHabitacion{

    private Grafo grafo;
    private ListaSimplementeEnlazada<ParNombreHabitacion> habitaciones;
    private ListaSimplementeEnlazada<ParIdNombre> idnombre;

    public GrafoHabitacion() {
        this.grafo = new Grafo(0);
        this.habitaciones=new ListaSimplementeEnlazada();
        this.idnombre=new ListaSimplementeEnlazada();
    }
    public void agregarHabitacion (Habitacion habitacion) {
        if(habitacion!=null){
            String nombre = "HAB" + habitacion.getId();
            idnombre.add(new ParIdNombre(habitacion.getId(),nombre));
            habitaciones.add(new ParNombreHabitacion(nombre,habitacion));
            grafo.agregarNodo(nombre);
        }
    }
    public void conectarHabitaciones(Habitacion habitacion1, Habitacion habitacion2) {
        if (habitacion1==null||habitacion2==null){
            throw new IllegalArgumentException("La habitacion no puede ser nulas");
        }

        // CAMBIO: Usar buscarNombrePorId en lugar de bucle manual
        String nombre1 = buscarNombrePorId(habitacion1.getId());
        String nombre2 = buscarNombrePorId(habitacion2.getId());

        // CAMBIO: Validar que ambas habitaciones existan
        if (nombre1 == null || nombre2 == null) {
            throw new IllegalArgumentException("Una o ambas habitaciones no existen en el grafo");
        }

        grafo.agregarArista(nombre1, nombre2, "conexión");
    }

    private Habitacion buscarHabitacionPorNombre(String nombre) {
        for (int j = 0; j < habitaciones.getSize(); j++) {
            ParNombreHabitacion par = habitaciones.getAt(j);
            if (par.getNombreNodo().equals(nombre)) {
                return par.getHabitacion();
            }
        }
        return null;
    }

    // DESPUÉS: Simplificar obtenerHabitacion
    public Habitacion obtenerHabitacion(int id) {
        String nombre = buscarNombrePorId(id);  // CAMBIO: Usar método auxiliar
        if (nombre == null) return null;        // CAMBIO: Validar
        return buscarHabitacionPorNombre(nombre); // CAMBIO: Usar método auxiliar
    }
    public ListaSimplementeEnlazada<Habitacion> obtenerHabitaciones(Habitacion habitacion) {
        if (habitacion == null) {
            throw new IllegalArgumentException("La habitación no puede ser nula");
        }

        ListaSimplementeEnlazada<Habitacion> respuesta = new ListaSimplementeEnlazada();
        EntradaAdyacencia entrada = grafo.buscarEntrada("HAB" + habitacion.getId());

        // CAMBIO: Validar que la entrada existe
        if (entrada == null) {
            return respuesta; // Devolver lista vacía si no hay conexiones
        }

        ListaSimplementeEnlazada<Arista> aristas = entrada.getAristas();
        for (int i = 0; i < aristas.getSize(); i++) {
            Arista arista = aristas.getAt(i);
            String nombreDestino = arista.getDestino().getNombre();

            // CAMBIO: Usar método auxiliar en lugar de bucle manual
            Habitacion habitacionDestino = buscarHabitacionPorNombre(nombreDestino);
            if (habitacionDestino != null) {
                respuesta.add(habitacionDestino);
            }
        }
        return respuesta;
    }
    public ListaSimplementeEnlazada<Habitacion> buscarRuta(Habitacion habitacioninicio, Habitacion habitacionfinal) {
        String nombre1= "HAB"+ habitacioninicio.getId();
        String nombre2= "HAB"+ habitacionfinal.getId();
        ListaSimplementeEnlazada<Nodo>nodos=grafo.caminoMínimo(nombre1,nombre2);
        if(nodos==null){
            return null;
        }
        ListaSimplementeEnlazada<Habitacion> respuesta = new ListaSimplementeEnlazada();
        for (int i=0; i<nodos.getSize();i++){
            String nombreNodo= nodos.getAt(i).getNombre();
            for(int j=0; j<habitaciones.getSize();j++){
                if(habitaciones.getAt(j).getNombreNodo().equals(nombreNodo)){
                    respuesta.add(habitaciones.getAt(j).getHabitacion());
                }
            }
        }
        return respuesta;
    }
    private String buscarNombrePorId(int idBuscado) {
        // Recorrer toda la lista buscando el ID
        for (int i = 0; i < idnombre.getSize(); i++) {
            ParIdNombre par = idnombre.getAt(i);
            if (par.getIdHabitacion() == idBuscado) {  // ¡Encontrado!
                return par.getNombreNodo();            // Devolver nombre
            }
        }
        return null;  // No encontrado
    }

    public int calcularDistancia(Habitacion habitacion1, Habitacion habitacion2) {
        ListaSimplementeEnlazada<Habitacion> ruta = buscarRuta(habitacion1, habitacion2);
        if (ruta == null) {
            return -1; // No hay ruta
        }
        return ruta.getSize() - 1; // Número de aristas en la ruta
    }
    public boolean esConexo(){
        return !grafo.esDisjunto();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<habitaciones.getSize();i++){
            ParNombreHabitacion par=habitaciones.getAt(i);
            sb.append(par.getHabitacion().toString()+ "\n" );
        }
        return sb.toString();
    }
}
