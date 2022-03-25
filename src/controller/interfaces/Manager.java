package controller.interfaces;

import model.interfaces.Agency;
import model.interfaces.Request;
import model.interfaces.Train;

/**
 * Breve descrizione del dirigente
 * 
 * @author Battistelli Stefano
 */

public interface Manager {

	/**
	 * Viene passato un riferimento all'oggeto agenzia da aggiungere, 
	 * che andrà aggiunta alla lista delle agenzie
	 * 
	 * @param agenzia da aggiungere
	 */
	void addAgency(Agency agencyToAdd);

	/**
	 * Viene passato un riferimento all'oggetto agenzia da rimuovere, 
	 * che andrà rimosso dalla lista delle agenzie
	 * 
	 * @param agenzia da rimuovere
	 */
	void removeAgency(Agency agencyToRemove);

	/**
	 * Viene passato un rimerimento all'oggetto agenzia,
	 * si stamperanno tutte le informazioni di tale agenzia
	 * 
	 * @param agenzia da vedere
	 */
	void viewAgencyInfo(Agency agency);

	/**
	 * Viene passata una richiesta, la si invia al treno 
	 * come carico merce e si rimuove da tutti i direttori che hanno tale richiesta
	 * 
	 * @param richiesta soddisfatta
	 */
	void satisfiesRequest(Request requestApproved);

	/**
	 * Viene passato un nome dell'azienda e ritorna il riferimento all'azienda
	 *
	 * @param nome dell'agenzia
	 * @return
	 */
	Agency getAgency(String name);
	
	/**
	 * Ritorna il treno
	 * 
	 * @return
	 */
	Train getTrain();

	/**
	 * Tramite id ritorna il riferimento alla richiesta
	 * 
	 * @param id univoco della richiesta
	 * @return
	 */
	Request getRequest(int id);

	/**
	 * Viene aggiunta la richiesta alle varie aziende che possono soddisfarla
	 * 
	 * @param richiesta da condividere
	 */
	void createRequest(Request requestToCreate);
}
