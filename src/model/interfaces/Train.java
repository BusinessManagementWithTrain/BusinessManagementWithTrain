package model.interfaces;


/**
 * Breve descrizione del treno
 * 
 * @author Rinaldi Simone
 */

public interface Train {

	/*
	 * metood per la gestione del carico/scarico della merce
	 */
	void cargoManagment();
	
	/**
	 * metodo per aggiungere richieste alla lista di richieste di carico
	 * @param newRequest
	 */
	void addRequest(Request newRequest);
	
	/*
	 * metodo per vedere la prossima destinazione 
	 */
	void nextDestination();
	
	
	
}
