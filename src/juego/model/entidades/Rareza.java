package juego.model.entidades;

public enum Rareza {
    COMUN("Multiplicador de 1.0"),
    POCO_COMUN("Multiplicador de 1.5"),
    RARO("Multiplicador de 2.0"),
    EPICO("Multiplicador de 3.0"),
    LEGENDARIO("Multiplicador de 5.0"),;

    private final String descripcion;
    Rareza(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
}
