package exceptions;

public class EmptyDestinationsSetException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public EmptyDestinationsSetException() {
		this("");
	}
	
	public EmptyDestinationsSetException(String error) {
		super(error);
	}
}
