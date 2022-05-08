package model.interfaces;


/**
 * Interfaccia del treno, ovvero l'oggetto che gestirà
 * il carico e lo scarico merci tra i magazzini delle aziende
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
