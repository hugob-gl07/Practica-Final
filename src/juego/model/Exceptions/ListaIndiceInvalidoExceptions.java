package juego.model.Exceptions;

public class ListaIndiceInvalidoExceptions extends RuntimeException {
  public ListaIndiceInvalidoExceptions(){
    super("Estas intentando acceder a un rango invalido");
  }
  public ListaIndiceInvalidoExceptions(String message) {
        super(message);
    }
}
