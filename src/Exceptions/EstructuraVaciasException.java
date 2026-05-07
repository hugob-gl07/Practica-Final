package Exceptions;

public class EstructuraVaciasException extends RuntimeException {
    public EstructuraVaciasException(){
        super("La estructura de datos está vacia");
    }
    public EstructuraVaciasException(String message) {
        super(message);
    }
}
