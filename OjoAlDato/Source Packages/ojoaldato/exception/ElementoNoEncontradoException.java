package ojoaldato.exception;

/**
 * Excepcion personalizada que se lanza cuando un elemento no se encuentra en
 * las estructuras de datos del modelo.
 */
public class ElementoNoEncontradoException extends RuntimeException {
    /**
     * Crea una nueva excepción que indica que un elemento no ha sido encontrado.
     *
     * @param message Descripción del error o información adicional.
     */
    public ElementoNoEncontradoException(String message) {
        super(message);
    }
}


/**
 * Excepción personalizada que se lanza cuando se intenta añadir un elemento
 * que ya existe en una colección.
 */
public class ElementoDuplicadoException extends RuntimeException {
    /**
     * Crea una nueva excepción que indica que un elemento ya está registrado.
     *
     * @param message Descripción del error o información adicional.
     */
    public ElementoDuplicadoException(String message) {
        super(message);
    }
}