package controller.interfaces;

import java.util.Set;

import exceptions.AnotherAcceptedRequestException;
import exceptions.AnotherEmptyRequestIsPresentException;
import exceptions.DirectorIsAlreadyPresentException;
import exceptions.EmptyDestinationsQueueException;
import exceptions.EmptyWarehouseException;
import exceptions.FullTrainException;
import exceptions.FullWarehouseException;
import exceptions.WrongNeededQuantityException;
import model.interfaces.Director;
import model.interfaces.Factory;
import model.interfaces.Request;
import model.interfaces.Train;

/**
 * Interfaccia del Manager, oggetto che ha la possibilità di gestire un numero non arbitrario
 * di direttori e di manipolare le operazioni tra di essi. 
 * Ha inoltre la possibilità di soddisfare particolari richieste.
 * 
 * @author Battistelli Stefano
 */

public interface Manager {

	/**
	 * Metodo utilizzato per aggiungere un nuovo direttore al set dei direttori
	 * 
	 * @param direttore assunto
	 * @throws WrongNeededQuantityException 
	 * @throws DirectorIsAlreadyPresentException 
	 */
	void hireDirector(Director director) throws WrongNeededQuantityException, DirectorIsAlreadyPresentException;

	/**
	 * Metodo utilizzato per rimuovere un direttore dal set dei direttori
	 * 
	 * @param nome del direttore licenziato
	 */
	void fireDirector(String directorName);
	
	/**
	 * Metodo che aggiunge la richiesta ai vari direttori che possono soddisfarla
	 * 
	 * @param quantità di materiale richiesto dall'utente
	 * @throws WrongNeededQuantityException 
	 */
	void sendRequest(Request request) throws WrongNeededQuantityException;
	
	/**
	 * Metodo che restituisce un valore booleano in base alla presenza
	 * dell'azienda tra i direttori assunti
	 * 
	 * @param azienda da cercare
	 * @return
	 */
	boolean factoryIsPresent(Factory factory);

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
	 * 
	 * @throws FullWarehouseException 
	 */
	void satisfiesRequestManager(Request requestApproved) throws FullWarehouseException;

	
	/**
	 * Prossima destinazione da raggiungere con il treno 
	 * 
	 * @throws EmptyDestinationsQueueException 
	 * @throws EmptyWarehouseException 
	 * @throws Exception 
	 */
	void nextDestination() throws FullWarehouseException, FullTrainException, EmptyDestinationsQueueException, EmptyDestinationsQueueException, EmptyWarehouseException, Exception;
	
	/**
	 * Metodo che servirà per creare una nuova richiesta grazie al direttore specificato
	 * 
	 * @param quantità di metariale richiesta
	 * @param nome del direttore
	 * @throws WrongNeededQuantityException 
	 */
	void createNewRequest(int quantity, String directorName) throws WrongNeededQuantityException;
	
	/**
	 * Metodo che permette di svuotare il magazzino con il materiale lavorato del direttore specificato
	 * 
	 * @param nome del direttore
	 * @throws WrongNeededQuantityException 
	 * @throws AnotherEmptyRequestIsPresentException 
	 */
	void emptyWarehouse(String directorName) throws WrongNeededQuantityException, AnotherEmptyRequestIsPresentException;
	
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