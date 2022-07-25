package exceptions;

public class MaximumCharactersException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public MaximumCharactersException() {
		this("");
	}
	
	public MaximumCharactersException(String error) {
		super(error);
	}
	

}
