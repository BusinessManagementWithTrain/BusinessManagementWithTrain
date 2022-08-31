package exceptions;

/**
 * Eccezione invocata qualora si provasse a caricare
 * il treno superando la capienza massima
 */

public class FullTrainException extends Exception {
	private static final long serialVersionUID = 1L;

	public FullTrainException() {
		this("");
	}
	
	public FullTrainException(String error) {
		super(error);
	}
}
