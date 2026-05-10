package juego.model.Exceptions;

public class ListaVaciaExceptions extends EstructuraVaciasException {
  public ListaVaciaExceptions(){
    super("La Lista Simplemente Enlazada está vacía");
  }
  public ListaVaciaExceptions(String message) {
        super(message);
    }
}
