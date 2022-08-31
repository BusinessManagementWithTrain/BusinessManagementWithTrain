package exceptions;

public class WrongSendingFactoryException extends Exception {
	private static final long serialVersionUID = 1L;

	public WrongSendingFactoryException() {
		this("");
	}
	
	public WrongSendingFactoryException(String errorMessage) {
		super(errorMessage);
	}
}
