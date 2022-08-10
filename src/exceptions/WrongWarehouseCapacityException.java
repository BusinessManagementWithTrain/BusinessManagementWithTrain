package exceptions;

public class WrongWarehouseCapacityException extends Exception {
	private static final long serialVersionUID = 1L;

	public WrongWarehouseCapacityException() {
		this("");
	}

	public WrongWarehouseCapacityException(String errorMessage) {
		super(errorMessage);
	}
}
