package juego.model.entidades;

/**
 * Enumerado que representa la rareza de un objeto del juego.
 * Cada nivel de rareza lleva asociado un multiplicador que amplifica las estadísticas del objeto.
 * Los niveles van de COMUN (x1.0) a LEGENDARIO (x5.0).
 */
public enum Rareza {
    COMUN("Multiplicador de 1.0"),           // El objeto base, sin bonificación
    POCO_COMUN("Multiplicador de 1.5"),      // Ligera mejora sobre el objeto base
    RARO("Multiplicador de 2.0"),            // El doble de estadísticas base
    EPICO("Multiplicador de 3.0"),           // El triple de estadísticas base
    LEGENDARIO("Multiplicador de 5.0");      // Objetos únicos con cinco veces las estadísticas base

    private final String descripcion; // Texto descriptivo con el multiplicador que aplica esta rareza

    /**
     * Crea un nivel de rareza con su descripción de multiplicador.
     * @param descripcion texto que describe el multiplicador aplicado
     */
    Rareza(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve la descripción del multiplicador de esta rareza.
     * @return texto con el multiplicador, por ejemplo "Multiplicador de 2.0"
     */
    public String getDescripcion() {
        return descripcion;
    }
}
