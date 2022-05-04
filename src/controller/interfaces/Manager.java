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
	void fireDirector(String directorName);

	/**
	 * Viene passato ucome parametro il nome di un direttore,
	 * si stamperanno tutte le informazioni dell'azienda del direttore
	 * 
	 * @param nome del direttore da cui prendere l'azienda
	 */
	Factory showFactoryInfo(String directorName);

	/**
	 * Viene passata una richiesta, la si invia al treno 
	 * come carico merce e si rimuove da tutti i direttori che hanno tale richiesta
	 * 
	 * @param richiesta soddisfatta
	 */
	void satisfiesRequest(Request requestApproved, String directorName);



	/**
	 * Prossima destinazione da raggiungere con il treno
	 * 
	 *  		  
	 */
	void nextDestination();
	
	/**
	 * Metodo che servirà per creare una nuova richiesta grazie al direttore specificato
	 * 
	 * @param quantità di metariale richiesta e nome del direttore
	 */
	void createNewRequest(int quantity, String directorName);
	
	/**
	 * Metodo che visualizza le informazioni riguardanti un direttore
	 * 
	 * 
	 * @param nome del direttore
	 */
	Director showDirectorInfo(String directorName);
	
	/**
	 * Metodo che visualizza le informazioni riguardanti il treno 
	 * 
	 * 
	 * @param 
	 */
	void showTrainInfo();
	
	/**
	 * Metodo che visualizza le informazioni di una richiesta
	 * 
	 * 
	 * @param ID della richiesta
	 */
	void showRequestInfo(int id);
	
	
	
}