package controller.interfaces;

import exceptions.FullTrainException;
import exceptions.FullWarehouseException;
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
	 * Viene passata una richiesta che verr� successivamente inviata al treno 
	 * come carico merce e verr� rimossa da tutti i direttori che hanno tale richiesta
	 * 
	 * @param richiesta soddisfatta
	 */
	void satisfiesRequestDirector(Request requestApproved, String directorName);

	/**
	 * Questo metodo permette al Manager di soddisfare le richieste che non possono soddisfare i direttori
	 * @throws FullWarehouseException 
	 * 
	 * 
	 */
	void satisfiesRequestManager(Request requestApproved) throws FullWarehouseException;

	
	/**
	 * Prossima destinazione da raggiungere con il treno  
	 */
	void nextDestination() throws FullWarehouseException, FullTrainException;
	
	/**
	 * Metodo che servir� per creare una nuova richiesta grazie al direttore specificato
	 * 
	 * @param quantit� di metariale richiesta
	 * @param nome del direttore
	 */
	void createNewRequest(int quantity, String directorName);
	
	/**
	 * Metodo che permette di svuotare il magazzino con il materiale lavorato tramite il nome di un direttore
	 * 
	 * @param nome del direttore
	 */
	public void emptyWarehouse(String directorName);
	
	/**
	 * Metodo che visualizza le informazioni riguardanti un direttore
	 * 
	 * @param nome del direttore
	 * @return direttore associato al nome
	 */
	Director showDirectorInfo(String directorName);
	
	/**
	 * Viene passato come parametro il nome di un direttore,
	 * verr� restituita l'azienda del direttore
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