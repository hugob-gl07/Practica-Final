package juego.model.Exceptions;

/**
 * Excepción lanzada cuando se intenta operar sobre una cola de prioridad vacía.
 * Extiende EstructuraVaciasException para unificar el manejo de estructuras vacías.
 */
public class ColaPrioridadVaciaExceptions extends EstructuraVaciasException {
    /** Crea la excepción con el mensaje por defecto "La cola con prioridad está vacía".*/
    public ColaPrioridadVaciaExceptions(){
        super("La cola con prioridad está vacía");
    }
    /**
     * Crea la excepción con un mensaje personalizado.
     * @param message mensaje descriptivo del error
     */
    public ColaPrioridadVaciaExceptions(String message) {
        super(message);
    }
}
