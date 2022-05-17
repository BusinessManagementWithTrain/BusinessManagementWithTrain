package exceptions;

public class FullTrainException extends Exception {
	private static final long serialVersionUID = 1L;

	public FullTrainException() {
		this("");
	}
	
	public FullTrainException(String error) {
		super(error);
	}
}
