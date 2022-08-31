package exceptions;

/**
 * Eccezione invocata qualora si provasse a fermare lo staff senza
 * farlo lavorare prima
 */

public class StaffIsAlreadyNotWorkingException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public StaffIsAlreadyNotWorkingException() {
		this("");
	}

	public StaffIsAlreadyNotWorkingException(String error) {
		super(error);
	}
}
