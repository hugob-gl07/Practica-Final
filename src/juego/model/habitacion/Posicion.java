package juego.model.habitacion;

/**
 * Registro auxiliar usado durante el recorrido BFS de la matriz de habitación.
 * Almacena la fila y columna de una celda junto con la distancia acumulada desde el origen,
 * necesaria para respetar el rango de movimiento del personaje.
 */
public class Posicion {
    protected int fila;      // Fila de la celda en la matriz
    protected int columna;   // Columna de la celda en la matriz
    protected int distancia; // Número de pasos desde la celda de inicio hasta esta celda

    /**
     * Crea una posición con la fila, columna y distancia indicadas.
     * @param fila      fila de la celda en la matriz
     * @param columna   columna de la celda en la matriz
     * @param distancia distancia acumulada desde la celda de origen
     */
    public Posicion(int fila, int columna, int distancia){
        this.fila=fila;
        this.columna=columna;
        this.distancia=distancia;
    }

    /**
     * Devuelve la fila de la celda.
     * @return fila en la matriz
     */
    public int getFila() {
        return fila;
    }

    /**
     * Devuelve la columna de la celda.
     * @return columna en la matriz
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Devuelve la distancia acumulada desde la celda de origen.
     * @return número de pasos desde el inicio
     */
    public int getDistancia() {
        return distancia;
    }

    /**
     * Establece la fila de la celda.
     * @param fila nueva fila en la matriz
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    /**
     * Establece la columna de la celda.
     * @param columna nueva columna en la matriz
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

    /**
     * Establece la distancia acumulada desde la celda de origen.
     * @param distancia nueva distancia acumulada
     */
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
}
