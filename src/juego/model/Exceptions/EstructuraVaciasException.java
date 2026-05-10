package juego.model.Exceptions;

/**
 * Excepción base para todas las operaciones inválidas sobre estructuras de datos vacías.
 * Extiende RuntimeException para que no sea obligatorio capturarla explícitamente.
 * Las clases concretas de cada estructura deben extender esta clase.
 */
public class EstructuraVaciasException extends RuntimeException {
    /** Crea la excepción con el mensaje por defecto "La estructura de datos está vacia".*/
    public EstructuraVaciasException(){
        super("La estructura de datos está vacia");
    }
    /**
     * Crea la excepción con un mensaje personalizado.
     * @param message mensaje descriptivo del error
     */
    public EstructuraVaciasException(String message) {
        super(message);
    }
}
