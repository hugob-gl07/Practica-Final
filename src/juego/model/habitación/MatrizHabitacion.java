package juego.model.habitación;

import juego.model.EstructurasUtilizadas.Colas.Cola.Cola;
import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;

/**
 * Representa la matriz de celdas que conforman la habitación.
 * Cada celda en la matriz tiene un tipo que determina su comportamiento y propiedades.
 */
public class MatrizHabitacion {
    private Celda[][] matriz;
    private int filas,columnas;
    public MatrizHabitacion(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        matriz = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = new Celda(i, j, TipoCelda.VACIO);
            }
        }
    }
    public int getFilas() {
        return filas;
    }
    public int getColumnas() {
        return columnas;
    }
    public Celda getCelda(int fila, int columna) {
        if((fila< 0 ||fila>= filas) || (columna<0 || columna>= columnas)) {
            throw new IllegalArgumentException();
        }
        return matriz[fila][columna];
    }
    public void setCelda(int fila, int columna, Celda celda) {
        if((fila< 0 ||fila>= filas) || (columna<0 || columna>= columnas)) {
            throw new IllegalArgumentException();
        }
        matriz[fila][columna] = celda;
    }
    public boolean esTransitable(int fila, int columna) {
        if((fila< 0 ||fila>= filas) || (columna<0 || columna>= columnas)) {
            return false;
        }
        return matriz[fila][columna].esTransitable();
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                sb.append(matriz[i][j].obtenerRepresentacion());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public ListaSimplementeEnlazada<Celda> obtenerCasillasAlcanzable(int filaInicio, int columnaInicio, int rango){
        Cola<Posicion> cola=new Cola<>();
        ListaSimplementeEnlazada<Celda>resultado= new ListaSimplementeEnlazada<Celda>();
        boolean[][] visitados=new boolean[filas][columnas];
        visitados[filaInicio][columnaInicio]=true;
        cola.enqueue(new Posicion(filaInicio,columnaInicio,0));
        while(!cola.isEmpty()){
            Posicion actual= cola.dequeue();
            if(actual.distancia<=rango) {
                resultado.add(getCelda(actual.fila, actual.columna));
                int [] dfila= {-1,1,0,0};
                int [] dfcolumna= {0,0,-1,1,};
                for (int d= 0; d< 4; d++){
                    int nuevaFila=actual.fila+dfila[d];
                    int nuevaColumna=actual.columna+dfcolumna[d];
                    if((nuevaFila>=0 && nuevaFila<filas && nuevaColumna>=0 && nuevaColumna<columnas)&& (!visitados[nuevaFila][nuevaColumna])&&(esTransitable(nuevaFila,nuevaColumna))){
                        visitados[nuevaFila][nuevaColumna]=true;
                        cola.enqueue (new Posicion(nuevaFila,nuevaColumna,actual.distancia+1));
                    }
                }
            }

        }
        return resultado;
    }
}
