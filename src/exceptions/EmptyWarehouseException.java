package exceptions;

public class EmptyWarehouseException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmptyWarehouseException() {
		this("");
	}
	
	public EmptyWarehouseException(String error) {
		super(error);
	}
}
