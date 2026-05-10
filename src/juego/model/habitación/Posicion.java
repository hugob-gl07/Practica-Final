package juego.model.habitación;

public class Posicion {
    protected int fila;
    protected int columna;
    protected  int distancia;

    public Posicion(int fila, int columna,int distancia){
        this.fila=fila;
        this.columna=columna;
        this.distancia=distancia;
    }
    public int getFila() {
        return fila;
    }
    public int getColumna() {
        return columna;
    }
    public int getDistancia() {
        return distancia;
    }
    public void setFila(int fila) {
        this.fila = fila;

    }
    public void setColumna(int columna) {
        this.columna = columna;
    }
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }


}

