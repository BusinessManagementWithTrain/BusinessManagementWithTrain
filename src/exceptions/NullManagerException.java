package exceptions;

public class NullManagerException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public  NullManagerException() {
		this("");
	}
	
	public NullManagerException(String error) {
		super(error);
	}
}
