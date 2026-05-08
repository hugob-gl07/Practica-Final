package juego.model.Exceptions;

public class ListaCircularExceptions extends EstructuraVaciasException {
    public ListaCircularExceptions(){
        super("La lista circular esta vacía");
    }
    public ListaCircularExceptions(String message) {
        super(message);
    }
}
