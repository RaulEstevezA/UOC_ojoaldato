package ojoaldato.excepcion;

/**
 * Excepción personalizada que se lanza cuando se intenta añadir un elemento
 * que ya existe en una colección.
 */
public class ElementoDuplicadoException extends BaseException {
    /**
     * Crea una nueva excepción que indica que un elemento ya está registrado.
     *
     * @param message Descripción del error o información adicional.
     */
    public ElementoDuplicadoException(String message) {
        super(message);
    }
}
