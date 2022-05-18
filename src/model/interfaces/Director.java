package model.interfaces;

import java.util.Set;

import exceptions.IsNotPresentException;
import exceptions.IsPresentException;

/**
 * Interfaccia del direttore, ovvero l'oggetto che gestirà
 * un'azienda e comunicherà con gli altri direttori all'atto dell'utilizzo del programma.
 * 
 * @author Grasso Emanuele
 */

public interface Director {
	
	/**
	 * Consente al direttore di creare una richiesta per ricevere il materiale
	 * da lavorare
	 * 
	 * @param la quantità specificata dall'utente
	 * @return la richiesta per ricevere il materiale
	 */
	Request newRequest(int neededQuantity);

	/**
	 * Crea la richiesta per svuotare il magazzino di prodotti lavorati
	 * 
	 * @return la richiesta per svuotare
	 */
	Request emptyWarehouse(); 
	
	/**
	 * Consente al dirigente di aggiungere una richiesta al set delle
	 * richieste soddisfabili dal direttore
	 * 
	 * @param nuova richiesta da soddisfare
	 */
	void addRequestToSatisfy(Request requestToSatisfy) throws IsPresentException;
	
	/**
	 * Consente al dirigente di rimuovere una richiesta dal set delle
	 * richieste soddisfabili poichè già soddisfatta da un altro direttore
	 * 
	 * @param richiesta soddisfatta da rimuovere
	 */
	void removeRequestToSatisfy(Request requestToBeRemoved) throws IsNotPresentException;
	
	/**
	 * Consente al direttore di soddisfare una richiesta
	 * precedentemente inviata da un altro direttore 
	 * 
	 * @param richiesta soddisfatta
	 */
	void satisfyRequest(Request requestFulfilled);
	
	/**
	 * Consente di avere il riferimento al set delle richieste da soddisfare
	 * 
	 * @return lista delle richieste
	 */
	Set<Request> getRequestsToSatisfy();
	
	/**
	 * Consente di avere il riferimento all'azienda gestita dal direttore
	 * 
	 * @return azienda gestita dal direttore
	 */
	Factory getFactory();
	
	/**
	 * Consente di avere il riferimento al nome del direttore
	 * 
	 * @return nome del direttore
	 */
	String getName();
	
}
