package juego.model.habitacion;

import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;

/**
 * Representa una habitación individual del juego.
 * Envuelve una MatrizHabitacion y añade un identificador y nombre propios.
 * Las habitaciones se ordenan por identificador numérico al implementar Comparable.
 */
public class Habitacion implements Comparable<Habitacion> {
    private int id;                  // Identificador único de la habitación en el grafo de habitaciones
    private String nombre;           // Nombre descriptivo de la habitación
    private MatrizHabitacion matriz; // Cuadrícula de celdas que forma el interior de la habitación

    /**
     * Crea una habitación con el identificador, nombre y dimensiones indicados.
     * Inicializa la matriz interna con todas las celdas en estado VACIO.
     * @param id      identificador único de la habitación
     * @param nombre  nombre descriptivo de la habitación
     * @param columnas número de columnas de la cuadrícula interna
     * @param filas   número de filas de la cuadrícula interna
     */
    public Habitacion(int id, String nombre, int columnas, int filas) {
        this.id = id;
        this.nombre = nombre;
        this.matriz = new MatrizHabitacion(columnas, filas); // Creamos la cuadrícula con las dimensiones dadas
    }

    /**
     * Devuelve el identificador único de la habitación.
     * @return id de la habitación
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la habitación.
     * @param id nuevo id de la habitación
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre descriptivo de la habitación.
     * @return nombre de la habitación
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre descriptivo de la habitación.
     * @param nombre nuevo nombre de la habitación
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la celda en la posición indicada de la matriz interna.
     * @param fila    fila de la celda
     * @param columna columna de la celda
     * @return celda en esa posición
     */
    public Celda getCelda(int fila, int columna) {
        return matriz.getCelda(fila, columna); // Delegamos en la matriz
    }

    /**
     * Reemplaza la celda en la posición indicada de la matriz interna.
     * @param fila    fila de la celda a sustituir
     * @param columna columna de la celda a sustituir
     * @param celda   nueva celda a colocar en esa posición
     */
    public void setCelda(int fila, int columna, Celda celda) {
        matriz.setCelda(fila, columna, celda); // Delegamos en la matriz
    }

    /**
     * Comprueba si la celda en la posición indicada es transitable.
     * @param fila    fila a comprobar
     * @param columna columna a comprobar
     * @return true si la celda es transitable, false en caso contrario
     */
    public boolean esTransitable(int fila, int columna) {
        return matriz.esTransitable(fila, columna); // Delegamos en la matriz
    }

    /**
     * Comprueba si la habitación contiene al menos una celda del tipo indicado.
     * @param tipoCelda tipo de celda a buscar
     * @return true si existe al menos una celda de ese tipo, false en caso contrario
     */
    public boolean tieneCelda(TipoCelda tipoCelda) {
        for (int i = 0; i < matriz.getFilas(); i++) {
            for (int j = 0; j < matriz.getColumnas(); j++) {
                if (matriz.getCelda(i, j).getTipo() == tipoCelda) {
                    return true; // Encontramos una celda del tipo buscado
                }
            }
        }
        return false; // Ninguna celda tiene ese tipo
    }

    /**
     * Busca y devuelve la primera celda del tipo indicado en la habitación.
     * @param tipoCelda tipo de celda a buscar
     * @return primera celda encontrada del tipo indicado
     * @throws IllegalArgumentException si no hay ninguna celda del tipo indicado
     */
    public Celda buscarcelda(TipoCelda tipoCelda) {
        for (int i = 0; i < matriz.getFilas(); i++) {
            for (int j = 0; j < matriz.getColumnas(); j++) {
                if (matriz.getCelda(i, j).getTipo() == tipoCelda) {
                    return matriz.getCelda(i,j); // Devolvemos la primera celda del tipo buscado
                }
            }
        }
        throw new IllegalArgumentException("No se encontró una celda del tipo especificado en la habitación."); // No hay ninguna celda del tipo indicado
    }

    /**
     * Devuelve el número de filas de la cuadrícula interna.
     * @return número de filas
     */
    public int getFila(){
        return matriz.getFilas(); // Delegamos en la matriz
    }

    /**
     * Devuelve el número de columnas de la cuadrícula interna.
     * @return número de columnas
     */
    public int getColumna(){
        return matriz.getColumnas(); // Delegamos en la matriz
    }

    /**
     * Calcula las casillas alcanzables desde la posición indicada dentro del rango dado.
     * Delega en la matriz interna usando BFS.
     * @param fila    fila de la posición de inicio
     * @param columna columna de la posición de inicio
     * @param rango   número máximo de pasos desde la posición de inicio
     * @return lista de celdas alcanzables dentro del rango
     */
    public ListaSimplementeEnlazada<Celda> obtenerCasillasAlcanzables(int fila, int columna, int rango) {
        return matriz.obtenerCasillasAlcanzable(fila, columna, rango); // Delegamos en la matriz
    }

    /**
     * Devuelve una representación en texto de la habitación con su id, nombre y cuadrícula.
     * @return cadena con los datos de la habitación
     */
    public String toString() {
        return "Habitacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", matriz=\n" + matriz.toString() +
                '}';
    }

    /**
     * Compara esta habitación con otra por su identificador numérico.
     * @param o habitación con la que comparar
     * @return diferencia de ids: negativo si esta va antes, 0 si son iguales, positivo si va después
     */
    public int compareTo(Habitacion o) {
        return this.id - o.id; // Ordenamos por id ascendente
    }
}
