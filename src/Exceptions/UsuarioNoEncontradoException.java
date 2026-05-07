package Exceptions;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(String email) {
        super("No se encontró ningún usuario con el email: " + email);
    }
}
