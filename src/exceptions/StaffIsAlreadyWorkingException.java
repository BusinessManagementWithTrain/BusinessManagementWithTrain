package exceptions;

public class StaffIsAlreadyWorkingException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public StaffIsAlreadyWorkingException() {
		this("");
	}

	public StaffIsAlreadyWorkingException(String error) {
		super(error);
	}
}
