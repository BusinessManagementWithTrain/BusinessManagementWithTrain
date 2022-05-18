package exceptions;

public class StaffIsAlreadyNotWorkingException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public StaffIsAlreadyNotWorkingException() {
		this("");
	}

	public StaffIsAlreadyNotWorkingException(String error) {
		super(error);
	}
}
