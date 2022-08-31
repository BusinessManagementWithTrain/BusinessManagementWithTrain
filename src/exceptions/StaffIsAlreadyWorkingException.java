package exceptions;

/**
 * Eccezione invocata qualora si provasse a far lavorare lo staff senza
 * farlo smettere prima
 */

public class StaffIsAlreadyWorkingException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public StaffIsAlreadyWorkingException() {
		this("");
	}

	public StaffIsAlreadyWorkingException(String error) {
		super(error);
	}
}
