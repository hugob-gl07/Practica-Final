package juego.model.entidades;

/**
 * Enumerado que representa los tipos de enemigos del juego.
 * Cada tipo define las estadísticas base (vida, ataque, defensa) con las que se creará el enemigo.
 * Los valores se usan en la fábrica de enemigos para instanciarlos correctamente.
 */
public enum TipoEnemigo {
    GOBLIN(30, 5, 2),      // Enemigo débil, fácil de derrotar
    ORCO(60, 10, 4),       // Enemigo de dificultad media con más resistencia
    ESQUELETO(40, 8, 3),   // Equilibrio entre vida y ataque
    BANDOLERO(50, 12, 3),  // Alto ataque, dificultad media
    DRAGON(150, 25, 8);    // Jefe con las estadísticas más altas del juego

    private final int vidaBase;    // Puntos de vida con los que empieza el enemigo
    private final int ataqueBase;  // Daño base que inflige al jugador en cada ataque
    private final int defensaBase; // Reducción de daño que aplica cuando recibe golpes

    /**
     * Crea un tipo de enemigo con sus estadísticas base.
     * @param vida    puntos de vida iniciales del enemigo
     * @param ataque  daño base por ataque
     * @param defensa reducción de daño base
     */
    private TipoEnemigo(int vida, int ataque, int defensa) {
        this.vidaBase = vida;
        this.ataqueBase = ataque;
        this.defensaBase = defensa;
    }

    /**
     * Devuelve los puntos de vida base de este tipo de enemigo.
     * @return vida máxima inicial
     */
    public int getVidaBase() { return vidaBase; }

    /**
     * Devuelve el daño base por ataque de este tipo de enemigo.
     * @return daño base por ataque
     */
    public int getAtaqueBase() { return ataqueBase; }

    /**
     * Devuelve la reducción de daño base de este tipo de enemigo.
     * @return defensa base
     */
    public int getDefensaBase() { return defensaBase; }
}
