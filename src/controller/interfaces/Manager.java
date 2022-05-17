package controller.interfaces;

import model.interfaces.Director;
import model.interfaces.Factory;
import model.interfaces.Request;
import model.interfaces.Train;

/**
 * Breve descrizione del dirigente
 * 
 * @author Battistelli Stefano
 */

public interface Manager {

	/**
	 * Viene passato un riferimento all'oggetto direttore da aggiungere al set dei direttori
	 * 
	 * @param direttore assunto
	 */
	void hireDirector(Director director);

	/**
	 * Viene passato il nome di un direttore da rimuovere dal set dei direttori
	 * 
	 * @param nome del direttore licenziato
	 */
	void fireDirector(String directorName);

	/**
	 * Viene passata una richiesta che verrà successivamente inviata al treno 
	 * come carico merce e verrà rimossa da tutti i direttori che hanno tale richiesta
	 * 
	 * @param richiesta soddisfatta
	 */
	void satisfiesRequest(Request requestApproved, String directorName);

	/**
	 * Prossima destinazione da raggiungere con il treno  
	 */
	void nextDestination();
	
	/**
	 * Metodo che servirà per creare una nuova richiesta grazie al direttore specificato
	 * 
	 * @param quantità di metariale richiesta
	 * @param nome del direttore
	 */
	void createNewRequest(int quantity, String directorName);
	
	/**
	 * Metodo che visualizza le informazioni riguardanti un direttore
	 * 
	 * @param nome del direttore
	 * @return direttore associato al nome
	 */
	Director showDirectorInfo(String directorName);
	
	/**
	 * Viene passato come parametro il nome di un direttore,
	 * verrà restituita l'azienda del direttore
	 * 
	 * @param nome del direttore da cui prendere l'azienda
	 * @return l'azienda associata al direttore
	 */
	Factory showFactoryInfo(String directorName);
	
	/**
	 * Metodo che visualizza le informazioni riguardanti il treno 
	 * 
	 * @return il treno
	 */
	Train showTrainInfo();
	
	/**
	 * Metodo che visualizza le informazioni di una richiesta
	 * 
	 * @param ID della richiesta
	 * @return richiesta associata all'id
	 */
	Request showRequestInfo(int id);
}