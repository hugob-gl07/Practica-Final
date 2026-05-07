package Exceptions;

public class PilaVaciaExceptions extends EstructuraVaciasException{
    public PilaVaciaExceptions(){
        super("La pila esta vacia");
    }
    public PilaVaciaExceptions(String mensaje){
        super(mensaje);
    }
}
