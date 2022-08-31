package exceptions;

/**
 * Eccezione invocata qualora si provasse a creare uno Staff la cui
 * dimensione supera quella della capienza dei magazzini
 */

public class WrongStaffValueException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public WrongStaffValueException() {
		this("");
	}

	public WrongStaffValueException(String errorMessage) {
		super(errorMessage);
	}
}
