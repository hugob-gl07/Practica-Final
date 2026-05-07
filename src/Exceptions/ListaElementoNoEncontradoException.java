package Exceptions;

public class ListaElementoNoEncontradoException extends RuntimeException {
    public ListaElementoNoEncontradoException() {
        super("El elemento buscado no ha sido encontrado");
    }
    public ListaElementoNoEncontradoException(String message) {
        super(message);
    }
}
