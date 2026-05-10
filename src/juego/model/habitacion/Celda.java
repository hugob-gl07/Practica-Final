package juego.model.habitacion;
import java.util.Objects;

/**
 * Representa una celda individual en la matriz de la habitación.
 * Cada celda tiene coordenadas (x, y) y un tipo que determina su comportamiento
 * y cómo puede interactuar con los personajes del juego.
 * Las celdas se ordenan por fila y luego por columna al implementar Comparable.
 */
public class Celda implements Comparable<Celda> {
    private int x, y;       // Coordenadas de la celda en la matriz (fila, columna)
    private TipoCelda tipo; // Tipo que determina el comportamiento de la celda

    /**
     * Crea una celda en las coordenadas y con el tipo indicados.
     * @param x    fila de la celda en la matriz
     * @param y    columna de la celda en la matriz
     * @param tipo tipo de la celda
     */
    public Celda(int x, int y, TipoCelda tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
    }

    /**
     * Devuelve la fila de la celda en la matriz.
     * @return coordenada x (fila)
     */
    public int getX() {
        return x;
    }

    /**
     * Devuelve la columna de la celda en la matriz.
     * @return coordenada y (columna)
     */
    public int getY() {
        return y;
    }

    /**
     * Devuelve el tipo de la celda.
     * @return tipo de la celda
     */
    public TipoCelda getTipo() {
        return tipo;
    }

    /**
     * Establece la fila de la celda.
     * @param x nueva fila en la matriz
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Establece la columna de la celda.
     * @param y nueva columna en la matriz
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Establece el tipo de la celda.
     * @param tipo nuevo tipo de celda
     */
    public void setTipo(TipoCelda tipo) {
        this.tipo = tipo;
    }

    /**
     * Comprueba si el jugador puede moverse a esta celda.
     * Todas las celdas son transitables excepto las paredes.
     * @return true si el jugador puede moverse a esta celda, false en caso contrario
     */
    public boolean esTransitable() {
        return tipo!=TipoCelda.PARED; // Solo las paredes bloquean el movimiento
    }

    /**
     * Comprueba si la celda contiene un enemigo.
     * @return true si hay un enemigo en esta celda, false en caso contrario
     */
    public boolean tieneEnemigo() {
        if (tipo == TipoCelda.ENEMIGO) {
            return true; // La celda es de tipo ENEMIGO
        }
        return false;
    }

    /**
     * Comprueba si la celda contiene un objeto recogible.
     * @return true si la celda tiene un objeto, false en caso contrario
     */
    public boolean tieneObjeto() {
        if (tipo == TipoCelda.OBJETO) {
            return true; // La celda es de tipo OBJETO
        }
        return false;
    }

    /**
     * Compara esta celda con otra para verificar si ocupan la misma posición.
     * @param otra la celda a comparar
     * @return true si ambas celdas tienen las mismas coordenadas, false en caso contrario
     */
    public boolean mismaPosicion(Celda otra) {
        if (this.x == otra.x && this.y == otra.y) {
            return true; // Mismas coordenadas: misma posición en la matriz
        }
        return false;
    }

    /**
     * Comprueba si este objeto es igual a otro por tipo, x e y.
     * @param o objeto a comparar
     * @return true si son la misma celda, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Celda celda = (Celda) o;
        return x == celda.x && y == celda.y && tipo == celda.tipo;
    }

    /** Devuelve el hash de la celda basado en sus coordenadas y tipo.*/
    @Override
    public int hashCode() {
        return Objects.hash(x, y, tipo);
    }

    /**
     * Devuelve un carácter que representa visualmente el tipo de celda.
     * Útil para dibujar el mapa en consola o para debugging.
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

    /**
     * Devuelve una representación en texto de la celda con sus coordenadas y tipo.
     * @return cadena en formato "Celda(x,y) [tipo]"
     */
    @Override
    public String toString() {
        return "Celda(" + x + "," + y + ") [" + tipo + "]";
    }

    /**
     * Comprueba si las coordenadas de esta celda son válidas dentro de los límites de la habitación.
     * @param maxFilas    número máximo de filas de la habitación
     * @param maxColumnas número máximo de columnas de la habitación
     * @return true si las coordenadas están dentro de los límites, false en caso contrario
     */
    public boolean coordenadasValidas(int maxFilas, int maxColumnas) {
        if(x>=0 && y>=0 && x< maxFilas && y< maxColumnas) {
            return true; // Ambas coordenadas están dentro del rango
        }
        return false;
    }

    /**
     * Intenta cambiar el tipo de la celda, por ejemplo al recoger un objeto o derrotar un enemigo.
     * No permite cambiar el tipo de una celda de tipo PARED ni aceptar un tipo nulo.
     * @param nuevoTipo el nuevo tipo de celda
     * @return true si el cambio fue exitoso, false si no está permitido
     */
    public boolean cambiarTipo(TipoCelda nuevoTipo) {
        if (nuevoTipo == null) {
            return false; // No aceptamos tipos nulos
        }
        else if (tipo == TipoCelda.PARED) {
                return false; // Las paredes no se pueden cambiar
            }
        setTipo(nuevoTipo); // Aplicamos el cambio de tipo
        return true;
    }

    /**
     * Compara esta celda con otra primero por fila y luego por columna.
     * @param o celda con la que comparar
     * @return valor negativo si esta va antes, 0 si son iguales, positivo si va después
     */
    @Override
    public int compareTo(Celda o) {
        if(this.x != o.x) {
            return Integer.compare(this.x, o.x); // Primero comparamos por fila
        }
        return Integer.compare(this.y, o.y); // Si la fila es igual comparamos por columna
    }
}
