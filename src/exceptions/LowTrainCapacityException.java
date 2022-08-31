package exceptions;

/**
 * Eccezione invocata qualora venisse creato un treno con una capienza
 * inferiore ad un minimo
 */

public class LowTrainCapacityException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public LowTrainCapacityException() {
		this("");
	}
	
	public LowTrainCapacityException(String errorMessage) {
		super(errorMessage);
	}

}
