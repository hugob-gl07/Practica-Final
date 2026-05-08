package juego.model.Exceptions;

public class ColaPrioridadVaciaExceptions extends EstructuraVaciasException {
    public ColaPrioridadVaciaExceptions(){
        super("La cola con prioridad está vacía");
    }
    public ColaPrioridadVaciaExceptions(String message) {
        super(message);
    }
}
