package juego.model.Exceptions;

/**
 * Excepción lanzada cuando se intenta acceder a una posición fuera del rango válido de una lista.
 * Extiende RuntimeException directamente porque no está asociada a una estructura vacía,
 * sino a un índice incorrecto proporcionado por el llamador.
 */
public class ListaIndiceInvalidoExceptions extends RuntimeException {
  /** Crea la excepción con el mensaje por defecto indicando que el rango es inválido.*/
  public ListaIndiceInvalidoExceptions(){
    super("Estas intentando acceder a un rango invalido");
  }
  /**
   * Crea la excepción con un mensaje personalizado.
   * @param message mensaje descriptivo del error
   */
  public ListaIndiceInvalidoExceptions(String message) {
        super(message);
    }
}
