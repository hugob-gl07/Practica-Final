package juego.model.entidades;

public enum TipoObjeto {
    ARMA("Aumenta el daño de ataque"),
    ARMADURA("Reduce el daño recibido"),
    POCIMA_VIDA("Curación instantanea"),
    POCIMA_MANA("Otorga efectos mágicos"),
    CONSUMIBLE("Objeto de un solo uso donde recibes alguna ventaja"),
    LLAVE("Te permite abrir puetas para acceder a diferentes zonas"),
    TESORO("Aumenta la puntuación del juego o te otorga un coleccionable");

    private final String descripcion;

    private TipoObjeto(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
}
