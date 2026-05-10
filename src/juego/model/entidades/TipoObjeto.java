package juego.model.entidades;

/**
 * Enumerado que representa la categoría funcional de un objeto del juego.
 * El tipo determina cómo se usa el objeto y qué efecto produce sobre el jugador.
 */
public enum TipoObjeto {
    ARMA("Aumenta el daño de ataque"),                                                        // Incrementa el ataque del jugador
    ARMADURA("Reduce el daño recibido"),                                                      // Incrementa la defensa del jugador
    POCIMA_VIDA("Curación instantanea"),                                                      // Restaura puntos de vida al usarse
    POCIMA_MANA("Otorga efectos mágicos"),                                                    // Restaura o amplía el maná al usarse
    CONSUMIBLE("Objeto de un solo uso donde recibes alguna ventaja"),                         // Se consume al usarse una sola vez
    LLAVE("Te permite abrir puetas para acceder a diferentes zonas"),                         // Desbloquea puertas o zonas restringidas
    TESORO("Aumenta la puntuación del juego o te otorga un coleccionable");                   // Aumenta puntuación o es coleccionable

    private final String descripcion; // Texto que explica el efecto de este tipo de objeto

    /**
     * Crea un tipo de objeto con su descripción de efecto.
     * @param descripcion texto que describe el efecto del objeto
     */
    private TipoObjeto(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve la descripción del efecto de este tipo de objeto.
     * @return texto descriptivo del efecto
     */
    public String getDescripcion() {
        return descripcion;
    }
}
