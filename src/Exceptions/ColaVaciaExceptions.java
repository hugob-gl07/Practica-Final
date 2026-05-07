package Exceptions;

public class ColaVaciaExceptions extends EstructuraVaciasException {
    public ColaVaciaExceptions(){
        super("La cola esta vacia");
    }
    public ColaVaciaExceptions(String message) {
        super(message);
    }
}
