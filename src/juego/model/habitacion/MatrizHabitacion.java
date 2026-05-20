package juego.model.habitacion;

import juego.model.EstructurasUtilizadas.Colas.Cola.Cola;
import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;

/**
 * Representa la cuadrícula de celdas que compone el interior de una habitación.
 * Gestiona el acceso individual a cada celda y calcula las casillas alcanzables
 * desde una posición dada dentro de un rango de movimiento usando BFS.
 */
public class MatrizHabitacion {
    private Celda[][] matriz; // Array bidimensional de celdas que forma la habitación
    private int filas;        // Número de filas de la matriz
    private int columnas;     // Número de columnas de la matriz

    /**
     * Crea una matriz de habitación de las dimensiones indicadas.
     * Inicializa todas las celdas como VACIO.
     * @param filas    número de filas de la cuadrícula
     * @param columnas número de columnas de la cuadrícula
     */
    public MatrizHabitacion(int filas, int columnas) {
        if(filas <= 0 || columnas <= 0){
            throw new IllegalArgumentException("Las dimensiones de la matriz deben ser mayores que 0");
        }
        this.filas = filas;
        this.columnas = columnas;
        matriz = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = new Celda(i, j, TipoCelda.VACIO); // Inicializamos cada celda como vacía
            }
        }
    }

    /**
     * Devuelve el número de filas de la matriz.
     * @return número de filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * Devuelve el número de columnas de la matriz.
     * @return número de columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * Devuelve la celda en la posición indicada.
     * @param fila    fila de la celda (0 = primera fila)
     * @param columna columna de la celda (0 = primera columna)
     * @return celda en esa posición
     * @throws IllegalArgumentException si las coordenadas están fuera del rango de la matriz
     */
    public Celda getCelda(int fila, int columna) {
        if((fila< 0 ||fila>= filas) || (columna<0 || columna>= columnas)) {
            throw new IllegalArgumentException("Coordenadas (" + fila + "," + columna + ") fuera del rango de la matriz");
        }
        return matriz[fila][columna];
    }

    /**
     * Reemplaza la celda en la posición indicada por la celda dada.
     * @param fila    fila de destino (0 = primera fila)
     * @param columna columna de destino (0 = primera columna)
     * @param celda   nueva celda a colocar en esa posición
     * @throws IllegalArgumentException si las coordenadas están fuera del rango de la matriz
     */
    public void setCelda(int fila, int columna, Celda celda) {
        if((fila< 0 ||fila>= filas) || (columna<0 || columna>= columnas)) {
            throw new IllegalArgumentException("Coordenadas (" + fila + "," + columna + ") fuera del rango de la matriz");
        }
        matriz[fila][columna] = celda; // Sustituimos la celda en esa posición
    }

    /**
     * Comprueba si la celda en la posición indicada es transitable.
     * Devuelve false directamente si las coordenadas están fuera de la matriz.
     * @param fila    fila a comprobar
     * @param columna columna a comprobar
     * @return true si la celda es transitable, false si no lo es o está fuera de la matriz
     */
    public boolean esTransitable(int fila, int columna) {
        if((fila< 0 ||fila>= filas) || (columna<0 || columna>= columnas)) {
            return false; // Fuera de los límites: no transitable
        }
        return matriz[fila][columna].esTransitable(); // Delegamos en la celda
    }

    /**
     * Devuelve una representación en texto de la matriz dibujando cada celda con su carácter.
     * @return cadena con la cuadrícula de caracteres de la habitación
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                sb.append(matriz[i][j].obtenerRepresentacion()); // Carácter representativo de cada celda
            }
            sb.append("\n"); // Salto de línea al final de cada fila
        }
        return sb.toString();
    }

    /**
     * Calcula todas las celdas transitables alcanzables desde la posición de inicio
     * dentro del rango de movimiento indicado usando un recorrido BFS.
     * Solo incluye celdas dentro de los límites y transitables.
     * @param filaInicio    fila de la celda desde la que se calcula el alcance
     * @param columnaInicio columna de la celda desde la que se calcula el alcance
     * @param rango         número máximo de pasos desde la posición de inicio
     * @return lista de celdas alcanzables dentro del rango indicado
     */
    public ListaSimplementeEnlazada<Celda> obtenerCasillasAlcanzable(int filaInicio, int columnaInicio, int rango){
        Cola<Posicion> cola=new Cola<>();                                         // Cola BFS para gestionar el orden de visita
        ListaSimplementeEnlazada<Celda> resultado= new ListaSimplementeEnlazada<Celda>(); // Lista de celdas alcanzables
        boolean[][] visitados=new boolean[filas][columnas];                      // Array para marcar celdas ya procesadas
        visitados[filaInicio][columnaInicio]=true;                               // Marcamos la celda de inicio como visitada
        cola.enqueue(new Posicion(filaInicio,columnaInicio,0));                  // Empezamos el BFS desde la posición de inicio con distancia 0
        while(!cola.isEmpty()){
            Posicion actual= cola.dequeue(); // Sacamos la siguiente posición a procesar
            if(actual.distancia<=rango) {
                resultado.add(getCelda(actual.fila, actual.columna)); // La celda está dentro del rango: la añadimos al resultado
                int [] dfila= {-1,1,0,0};      // Desplazamientos de fila: arriba, abajo, sin cambio, sin cambio
                int [] dfcolumna= {0,0,-1,1};  // Desplazamientos de columna: sin cambio, sin cambio, izquierda, derecha
                for (int d= 0; d< 4; d++){
                    int nuevaFila=actual.fila+dfila[d];
                    int nuevaColumna=actual.columna+dfcolumna[d];
                    // Solo añadimos la vecina si está dentro de los límites, no visitada y transitable
                    if((nuevaFila>=0 && nuevaFila<filas && nuevaColumna>=0 && nuevaColumna<columnas)
                            && (!visitados[nuevaFila][nuevaColumna])
                            &&(esTransitable(nuevaFila,nuevaColumna))){
                        visitados[nuevaFila][nuevaColumna]=true;
                        cola.enqueue(new Posicion(nuevaFila,nuevaColumna,actual.distancia+1)); // Encolamos con la distancia incrementada
                    }
                }
            }
        }
        return resultado;
    }
}
