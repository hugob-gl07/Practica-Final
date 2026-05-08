package juego.model.habitación;

/**
 * Enumerado que define los diferentes tipos de celdas en una habitación.
 * Cada tipo determina el comportamiento y las propiedades de una celda.
 */
public enum TipoCelda {
    VACIO,
    PARED,
    ENTRADA,
    SALIDA,
    OBJETO,
    ENEMIGO,
    JUGADOR;
    /**
     * Determina si una celda de este tipo es transitable por el jugador.
     * @return true si el jugador puede moverse a esta celda, false en caso contrario
     */
    public boolean esTransitable() {
        switch(this) {
            case VACIO:
            case ENTRADA:
            case SALIDA:
            case OBJETO:
                return true;
            case PARED:
            case ENEMIGO:
            case JUGADOR:
                return false;
            default:
                return false;
        }
    }

    /**
     * Determina si una celda de este tipo puede contener objetos.
     * @return true si puede contener objetos, false en caso contrario
     */
    public boolean puedeContenerObjetos() {
        switch(this) {
            case VACIO:
            case ENTRADA:
            case SALIDA:
                return true;
            case PARED:
            case OBJETO:
            case ENEMIGO:
            case JUGADOR:
                return false;
            default:
                return false;
        }
    }

    /**
     * Determina si una celda de este tipo representa un obstáculo.
     * @return true si es un obstáculo, false en caso contrario
     */
    public boolean esObstaculo() {
        return this == PARED;
    }
    /**
     * Devuelve un carácter que representa visualmente este tipo de celda.
     * Útil para dibujar el mapa en consola o para debugging.
     * @return carácter ASCII que representa el tipo de celda
     */
    public char obtenerCaracterRepresentativo() {
        switch(this) {
            case VACIO: return '.';
            case PARED: return '#';
            case ENTRADA: return 'E';
            case SALIDA: return 'S';
            case OBJETO: return '*';
            case ENEMIGO: return 'X';
            case JUGADOR: return '@';
            default: return '?';
        }
    }

    /**
     * Devuelve un color representativo para este tipo de celda (si se usa interfaz gráfica).
     * @return cadena que representa un color
     */
    public String obtenerColorRepresentativo() {
        switch(this) {
            case VACIO: return "BLANCO";
            case PARED: return "GRIS";
            case ENTRADA: return "VERDE";
            case SALIDA: return "ROJO";
            case OBJETO: return "AMARILLO";
            case ENEMIGO: return "NEGRO";
            case JUGADOR: return "AZUL";
            default: return "MAGENTA";
        }
    }
    /**
     * Determina si una entidad puede colocarse en una celda de este tipo.
     * @return true si puede colocarse una entidad aquí, false en caso contrario
     */
    public boolean puedeContenerEntidad() {
        switch(this) {
            case VACIO:
            case ENTRADA:
            case SALIDA:
                return true;
            case PARED:
            case OBJETO:
            case ENEMIGO:
            case JUGADOR:
                return false;
            default:
                return false;
        }
    }

    /**
     * Determina si requiere interacción especial cuando el jugador entra.
     * @return true si requiere interacción especial, false en caso contrario
     */
    public boolean requiereInteraccion() {
        switch(this) {
            case OBJETO:
            case ENEMIGO:
            case SALIDA:
                return true;
            case VACIO:
            case PARED:
            case ENTRADA:
            case JUGADOR:
                return false;
            default:
                return false;
        }
    }
    /**
     * Obtiene una representación en texto de todos los tipos de celda.
     * @return array con los nombres de todos los tipos
     */
    public static String[] obtenerTodosLosTipos() {
        TipoCelda[] tipos = values();
        String[] nombres = new String[tipos.length];
        for (int i = 0; i < tipos.length; i++) {
            nombres[i] = tipos[i].name();
        }
        return nombres;
    }

    /**
     * Obtiene el tipo de celda transitable
     * @return array con los tipos transitables
     */
    public static TipoCelda[] obtenerTiposTransitables() {
        return new TipoCelda[]{VACIO, ENTRADA, SALIDA, OBJETO};
    }
}