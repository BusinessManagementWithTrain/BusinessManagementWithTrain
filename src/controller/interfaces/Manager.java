package controller.interfaces;

import model.interfaces.Director;
import model.interfaces.Request;
import model.interfaces.Train;

/**
 * Breve descrizione del dirigente
 * 
 * @author Battistelli Stefano
 */

public interface Manager {

	/**
	 * Viene passato un riferimento all'oggeto direttore da aggiungere, 
	 * che andrà aggiunta alla lista dei direttori
	 * 
	 * @param direttore assunto
	 */
	void hireDirector(Director director);

	/**
	 * Viene passato un riferimento all'oggetto direttore da rimuovere, 
	 * che andrà rimosso dalla lista dei direttori
	 * 
	 * @param direttore licenziato
	 */
	void fireDirector(Director director);

	/**
	 * Viene passato un rimerimento all'oggetto direttore,
	 * si stamperanno tutte le informazioni dell'azienda del direttore
	 * 
	 * @param direttore da cui prendere l'azienda
	 */
	void showAgencyInfo(Director director);

	/**
	 * Viene passata una richiesta, la si invia al treno 
	 * come carico merce e si rimuove da tutti i direttori che hanno tale richiesta
	 * 
	 * @param richiesta soddisfatta
	 */
	void satisfiesRequest(Request requestApproved);

	/**
	 * Viene passato un nome del direttore e ritorna il riferimento al direttore
	 *
	 * @param nome del direttore
	 * @return
	 */
	Director getDirectorByName(String directorName);
	
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
	Request getRequestById(int id);

	/**
	 * Viene aggiunta la richiesta ai vari direttori che possono soddisfarla
	 * 
	 * @param richiesta da condividere
	 */
	void createRequest(Request requestToCreate);
}