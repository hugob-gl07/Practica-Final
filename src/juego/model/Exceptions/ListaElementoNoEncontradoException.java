package juego.model.Exceptions;

/**
 * Excepción lanzada cuando se busca o elimina un elemento que no existe en la lista.
 * Extiende RuntimeException directamente porque el elemento no encontrado es un error
 * de lógica del llamador, no un estado de la estructura.
 */
public class ListaElementoNoEncontradoException extends RuntimeException {
    /** Crea la excepción con el mensaje por defecto "El elemento buscado no ha sido encontrado".*/
    public ListaElementoNoEncontradoException() {
        super("El elemento buscado no ha sido encontrado");
    }
    /**
     * Crea la excepción con un mensaje personalizado.
     * @param message mensaje descriptivo del error
     */
    public ListaElementoNoEncontradoException(String message) {
        super(message);
    }
}
