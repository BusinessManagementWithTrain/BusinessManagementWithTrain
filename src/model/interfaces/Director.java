package model.interfaces;

/**
 * Breve descrizione del direttore
 * 
 * @author Grasso Emanuele
 */

public interface Director {

	/**
	 * Svuota il magazzino di prodotti lavorati
	 */
	void emptyWarehouse(); 
	
	/**
	 * Consente al dirigente di aggiungere una richiesta alla lista delle
	 * richieste soddisfabili dal direttore
	 * 
	 * @param nuova richiesta da soddisfare
	 */
	void addRequestToSatisfy(Request requestToSatisfy);
	
	/**
	 * Consente al dirigente di rimuovere una richiesta dalla lista delle
	 * richieste soddisfabili poichè già soddisfatta da un altro direttore
	 * 
	 * @param richiesta soddisfatta da rimuovere
	 */
	void removeRequestToSatisfy(Request requestToBeRemoved);
	
	/**
	 * Consente al direttore di soddisfare una richiesta
	 * precedentemente inviata da un altro direttore 
	 * 
	 * @param richiesta soddisfatta
	 */
	void fullfilsRequest(Request requestFulfilled);
	
	/**
	 * Consente di avere il riferimento ad una specifica richiesta da soddisfare
	 * 
	 * @param id della richiesta
	 * @return richiesta corrispondente all'id
	 */
	Request getRequestById(int requestId);
	
	/**
	 * Consente di visualizzare a schermo i dati relativi all'azienda del direttore
	 */
	void showsAgencyInfo();
	
	/**
	 * Consente di avere il riferimento all'azienda gestita dal direttore
	 * 
	 * @return azienda gestita dal direttore
	 */
	Agency getAgency();
	
}
