package exceptions;

/**
 * Eccezione invocata qualora si provi a raggiungere
 * la prossima tappa della coda, ma questa risultasse vuota
 */

public class EmptyDestinationsQueueException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmptyDestinationsQueueException() {
		this("");
	}
	
	public EmptyDestinationsQueueException(String error) {
		super(error);
	}
}
