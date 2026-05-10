package juego.model.Exceptions;

/**
 * Excepción lanzada cuando se intenta operar sobre una lista circular vacía.
 * Extiende EstructuraVaciasException para unificar el manejo de estructuras vacías.
 */
public class ListaCircularExceptions extends EstructuraVaciasException {
    /** Crea la excepción con el mensaje por defecto "La lista circular esta vacía".*/
    public ListaCircularExceptions(){
        super("La lista circular esta vacía");
    }
    /**
     * Crea la excepción con un mensaje personalizado.
     * @param message mensaje descriptivo del error
     */
    public ListaCircularExceptions(String message) {
        super(message);
    }
}
