package juego.model.Exceptions;

/**
 * Excepción lanzada cuando se intenta operar sobre una pila vacía.
 * Extiende EstructuraVaciasException para unificar el manejo de estructuras vacías.
 */
public class PilaVaciaExceptions extends EstructuraVaciasException{
    /** Crea la excepción con el mensaje por defecto "La pila esta vacia".*/
    public PilaVaciaExceptions(){
        super("La pila esta vacia");
    }
    /**
     * Crea la excepción con un mensaje personalizado.
     * @param mensaje mensaje descriptivo del error
     */
    public PilaVaciaExceptions(String mensaje){
        super(mensaje);
    }
}
