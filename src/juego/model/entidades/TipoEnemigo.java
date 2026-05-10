package juego.model.entidades;
public enum TipoEnemigo {
    GOBLIN(30, 5, 2),      // vida, ataque, defensa
    ORCO(60, 10, 4),
    ESQUELETO(40, 8, 3),
    BANDOLERO(50, 12, 3),
    DRAGON(150, 25, 8);

    private final int vidaBase;
    private final int ataqueBase;
    private final int defensaBase;

    private TipoEnemigo(int vida, int ataque, int defensa) {
        this.vidaBase = vida;
        this.ataqueBase = ataque;
        this.defensaBase = defensa;
    }

    public int getVidaBase() { return vidaBase; }
    public int getAtaqueBase() { return ataqueBase; }
    public int getDefensaBase() { return defensaBase; }
}