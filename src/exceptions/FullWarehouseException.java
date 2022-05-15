package exceptions;

public class FullWarehouseException extends Exception {
	private static final long serialVersionUID = 1L;

	public FullWarehouseException() {
		this("");
	}
	
	public FullWarehouseException(String error) {
		super(error);
	}
}
