package controller.interfaces;

import java.util.Set;

import exceptions.AnotherAcceptedRequestException;
import exceptions.EmptyDestinationsSetException;
import exceptions.EmptyWarehouseException;
import exceptions.FullTrainException;
import exceptions.FullWarehouseException;
import exceptions.WrongNeededQuantityException;
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
	 * @throws WrongNeededQuantityException 
	 */
	void hireDirector(Director director) throws WrongNeededQuantityException;

	/**
	 * Viene passato il nome di un direttore da rimuovere dal set dei direttori
	 * 
	 * @param nome del direttore licenziato
	 */
	void fireDirector(String directorName);
	
	/**
	 * Viene aggiunta la richiesta ai vari direttori che possono soddisfarla
	 * 
	 * @param quantità di materiale richiesto dall'utente
	 * @throws WrongNeededQuantityException 
	 */
	void sendRequest(Request request) throws WrongNeededQuantityException;

	/**
	 * Viene passata una richiesta che verrà successivamente inviata al treno 
	 * come carico merce e verrà rimossa da tutti i direttori che hanno tale richiesta
	 * 
	 * @param richiesta soddisfatta
	 * @throws AnotherAcceptedRequestException 
	 */
	void satisfiesRequestDirector(Request requestApproved, String directorName) throws AnotherAcceptedRequestException;

	/**
	 * Questo metodo permette al Manager di soddisfare le richieste che non possono soddisfare i direttori
	 * @throws FullWarehouseException 
	 * 
	 * 
	 */
	void satisfiesRequestManager(Request requestApproved) throws FullWarehouseException;

	
	/**
	 * Prossima destinazione da raggiungere con il treno  
	 * @throws EmptyDestinationsSetException 
	 * @throws EmptyWarehouseException 
	 * @throws Exception 
	 */
	void nextDestination() throws FullWarehouseException, FullTrainException, EmptyDestinationsSetException, EmptyDestinationsSetException, EmptyWarehouseException, Exception;
	
	/**
	 * Metodo che servirà per creare una nuova richiesta grazie al direttore specificato
	 * 
	 * @param quantità di metariale richiesta
	 * @param nome del direttore
	 * @throws WrongNeededQuantityException 
	 */
	void createNewRequest(int quantity, String directorName) throws WrongNeededQuantityException;
	
	/**
	 * Metodo che permette di svuotare il magazzino con il materiale lavorato tramite il nome di un direttore
	 * 
	 * @param nome del direttore
	 * @throws WrongNeededQuantityException 
	 */
	void emptyWarehouse(String directorName) throws WrongNeededQuantityException;
	
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
	
	/**
	 *  Metodo che ritorna la lista dei direttori assunti dal Manager
	 * 
	 * @return la lista di Direttori
	 */
	Set<Director> getLinkDirectors();
	
	/**
	 *  Metodo che ritorna la lista delle richieste accettabili esclusivamente manager
	 * 
	 *  @return la lista di richieste del Manager
	 */
	Set<Request> getlinkRequestsManager();
	
	/**
	 *  Metodo che ritorna il direttore data una specifica azienda
	 * 
	 *  @param Factory
	 *  @return il Direttore associato
	 */
	Director getDirectorByFactory(Factory factory);
}