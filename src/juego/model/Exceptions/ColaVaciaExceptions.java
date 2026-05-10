package juego.model.Exceptions;

/**
 * Excepción lanzada cuando se intenta operar sobre una cola vacía.
 * Extiende EstructuraVaciasException para unificar el manejo de estructuras vacías.
 */
public class ColaVaciaExceptions extends EstructuraVaciasException {
    /** Crea la excepción con el mensaje por defecto "La cola esta vacia".*/
    public ColaVaciaExceptions(){
        super("La cola esta vacia");
    }
    /**
     * Crea la excepción con un mensaje personalizado.
     * @param message mensaje descriptivo del error
     */
    public ColaVaciaExceptions(String message) {
        super(message);
    }
}
