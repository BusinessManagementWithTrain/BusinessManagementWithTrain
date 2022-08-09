package exceptions;

public class WrongStaffValueException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public WrongStaffValueException() {
		this("");
	}

	public WrongStaffValueException(String errorMessage) {
		super(errorMessage);
	}
}
