package EstructurasUtilizadas.Celda;
import java.util.Objects;

/**
 * Representa una celda individual en la matriz de la habitación
 * Cada celda tiene cordenadas x,y y un tipo
 */
public class Celda {
    private int x, y;
    private TipoCelda tipo;

    public Celda(int x, int y, TipoCelda tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TipoCelda getTipo() {
        return tipo;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setTipo(TipoCelda tipo) {
        this.tipo = tipo;
    }

    /**
     * Comrueba si la celda es transitable por el jugador
     *
     * @return true si el jugador puede moverse a esta celda, false en caso contrario
     */
    public boolean esTransitable() {
        return tipo!=TipoCelda.PARED;
    }

    /**
     * Comprueba si la celda tiene un enemigo
     *
     * @ return true si hay un enemigo en esta celda, false en caso contrario
     */
    public boolean tieneEnemigo() {
        if (tipo == TipoCelda.ENEMIGO) {
            return true;
        }
        return false;
    }

    /**
     * Comprueba si la celda contiene un objeto recogible
     *
     * @return true si la celda tiene un objeto, false en caso contrario
     */
    public boolean tieneObjeto() {
        if (tipo == TipoCelda.OBJETO) {
            return true;
        }
        return false;
    }

    /**
     * Compara esta celda con otra para verificar si ocupan la misma posición.
     *
     * @param otra la celda a comparar
     * @return true si ambas celdas tienen las mismas coordenadas, false en caso contrario
     */
    public boolean mismaPosicion(Celda otra) {
        if (this.x == otra.x && this.y == otra.y) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Celda celda = (Celda) o;
        return x == celda.x && y == celda.y && tipo == celda.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, tipo);
    }

    /**
     * Devuelve un carácter que representa visualmente el tipo de celda.
     * Útil para dibujar el mapa en consola o para debugging
     *
     * @return carácter ASCII que representa la celda
     */
    public char obtenerRepresentacion() {
        if (tipo == TipoCelda.VACIO) {
            return '.';
        } else if (tipo == TipoCelda.PARED) {
            return '#';
        } else if (tipo == TipoCelda.ENEMIGO) {
            return 'E';
        } else if (tipo == TipoCelda.OBJETO) {
            return 'O';
        } else if (tipo == TipoCelda.ENTRADA) {
            return 'P';
        }
        else if  (tipo == TipoCelda.JUGADOR) {
            return 'J';
        }
        else if  (tipo == TipoCelda.SALIDA) {
            return 'S';
        }
        return '?'; // Para cualquier tipo desconocido
    }

    @Override
    public String toString() {
        return "Celda(" + x + "," + y + ") [" + tipo + "]";
    }

    /**
     * Comprueba si las coordenadas son válidas dentro de los límites de una habitación.
     *
     * @param maxFilas    número máximo de filas de la habitación
     * @param maxColumnas número máximo de columnas de la habitación
     * @return true si las coordenadas están dentro de los límites, false en caso contrario
     */
    public boolean coordenadasValidas(int maxFilas, int maxColumnas) {
        if(x>=0 && y>=0 && x< maxFilas && y< maxColumnas) {
            return true;
        }
        return false;
    }

    /**
     * Intenta cambiar el tipo de la celda (por ejemplo, al recoger un objeto).
     *
     * @param nuevoTipo el nuevo tipo de celda
     * @return true si el cambio fue exitoso, false si no es permitido
     */
    public boolean cambiarTipo(TipoCelda nuevoTipo) {
        if (nuevoTipo == null) {
            return false;
        }
        else if (tipo == TipoCelda.PARED) {
                return false;
            }
        setTipo(nuevoTipo);
        return true;
    }
}
