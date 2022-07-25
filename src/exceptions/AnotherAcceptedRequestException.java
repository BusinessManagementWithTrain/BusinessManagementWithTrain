package exceptions;

public class AnotherAcceptedRequestException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public AnotherAcceptedRequestException() {
		this("");
	}
	
	public AnotherAcceptedRequestException(String error) {
		super(error);
	}
}
