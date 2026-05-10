package juego.model.Exceptions;

/**
 * Excepción lanzada cuando se intenta operar sobre una lista simplemente enlazada vacía.
 * Extiende EstructuraVaciasException para unificar el manejo de estructuras vacías.
 */
public class ListaVaciaExceptions extends EstructuraVaciasException {
  /** Crea la excepción con el mensaje por defecto "La Lista Simplemente Enlazada está vacía".*/
  public ListaVaciaExceptions(){
    super("La Lista Simplemente Enlazada está vacía");
  }
  /**
   * Crea la excepción con un mensaje personalizado.
   * @param message mensaje descriptivo del error
   */
  public ListaVaciaExceptions(String message) {
        super(message);
    }
}
