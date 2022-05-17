package exceptions;

public class LowTrainCapacityException extends Exception {

	private static final long serialVersionUID = 1L;

	public LowTrainCapacityException() {
		this("");
	}
	
	public LowTrainCapacityException(String error) {
		super(error);
	}
}
