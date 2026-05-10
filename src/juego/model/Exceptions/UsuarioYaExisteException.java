package juego.model.Exceptions;

/**
 * Excepción lanzada cuando se intenta registrar un usuario con un email que ya existe en el sistema.
 * Extiende RuntimeException directamente porque es un error de lógica del llamador.
 */
public class UsuarioYaExisteException extends RuntimeException {
    /**
     * Crea la excepción indicando el email duplicado.
     * @param email email del usuario que ya está registrado en el sistema
     */
    public UsuarioYaExisteException(String email) {
        super("Ya existe un usuario registrado con el email: " + email);
    }
}
