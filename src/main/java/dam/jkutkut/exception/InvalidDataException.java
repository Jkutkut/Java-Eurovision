package dam.jkutkut.exception;

/**
 * Clase personalizada para poder lanzar excepciones personalizadas.
 */
public class InvalidDataException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidDataException(String s) {
		super(s);
	}
}
