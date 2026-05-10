package juego.model.habitación;

import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;

/**
 * Representa una habitación individual del juego con sus propiedades y matriz interna.
 * Cada habitación tiene un identificador único y conexiones con otras habitaciones.
 */
public class Habitacion implements Comparable<Habitacion> {
    private int id;
    private String nombre;
    private MatrizHabitacion matriz;

    public Habitacion(int id, String nombre, int columnas, int filas) {
        this.id = id;
        this.nombre = nombre;
        this.matriz = new MatrizHabitacion(columnas, filas);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Celda getCelda(int fila, int columna) {
        return matriz.getCelda(fila, columna);
    }
    public void setCelda(int fila, int columna, Celda celda) {
        matriz.setCelda(fila, columna, celda);
    }
    public boolean esTransitable(int fila, int columna) {
        return matriz.esTransitable(fila, columna);
    }
    public boolean tieneCelda(TipoCelda tipoCelda) {
        for (int i = 0; i < matriz.getFilas(); i++) {
            for (int j = 0; j < matriz.getColumnas(); j++) {
                if (matriz.getCelda(i, j).getTipo() == tipoCelda) {
                    return true ;
                }
            }
        }
        return false ;
    }
    public Celda buscarcelda(TipoCelda tipoCelda) {
        for (int i = 0; i < matriz.getFilas(); i++) {
            for (int j = 0; j < matriz.getColumnas(); j++) {
                if (matriz.getCelda(i, j).getTipo() == tipoCelda) {
                    return matriz.getCelda(i,j);
                }
            }
        }
         throw new IllegalArgumentException("No se encontró una celda del tipo especificado en la habitación.");
    }
    public int getFila(){
        return matriz.getFilas();
    }
    public int getColumna(){
        return matriz.getColumnas();
    }

    public ListaSimplementeEnlazada<Celda> obtenerCasillasAlcanzables(int fila, int columna, int rango) {
        return matriz.obtenerCasillasAlcanzable(fila, columna, rango);
    }

    public String toString() {
        return "Habitacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", matriz=\n" + matriz.toString() +
                '}';
    }
    public int compareTo(Habitacion o) {
        return this.id - o.id;
    }
}