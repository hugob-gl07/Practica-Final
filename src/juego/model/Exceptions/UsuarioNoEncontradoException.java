package juego.model.Exceptions;

/**
 * Excepción lanzada cuando se intenta buscar un usuario con un email que no existe en el sistema.
 * Extiende RuntimeException directamente porque es un error de lógica del llamador.
 */
public class UsuarioNoEncontradoException extends RuntimeException {
    /**
     * Crea la excepción indicando el email que no se ha encontrado.
     * @param email email del usuario que no existe en el sistema
     */
    public UsuarioNoEncontradoException(String email) {
        super("No se encontró ningún usuario con el email: " + email);
    }
}
