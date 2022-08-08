package exceptions;

public class EqualMaterialException extends Exception {
	private static final long serialVersionUID = 1L;

	public EqualMaterialException() {
		this("");
	}
	
	public EqualMaterialException(String error) {
		super(error);
	}
}
