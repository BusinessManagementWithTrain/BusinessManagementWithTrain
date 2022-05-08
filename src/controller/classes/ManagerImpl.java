package controller.classes;

import java.util.LinkedHashSet;

import controller.interfaces.Manager;
import model.classes.DirectorImpl;
import model.classes.RequestImpl;
import model.classes.TrainImpl;
import model.interfaces.Director;
import model.interfaces.Factory;
import model.interfaces.Request;
import model.interfaces.Train;

public class ManagerImpl implements Manager {
	
	private LinkedHashSet <Director> linkDirectors;				// set di tutti i direttori
	private LinkedHashSet <Request> linkRequestsManager;		// set di richieste che può soddisfare il manager
	private LinkedHashSet <Request> linkGlobalRequests;			// set di tutte le richieste
	private Train train;
	
	private ManagerImpl() {
		this.linkDirectors			= new LinkedHashSet<Director>();
		this.train 					= new TrainImpl(0);
		this.linkRequestsManager	= new LinkedHashSet<Request>();
		this.linkGlobalRequests 	= new LinkedHashSet<Request>();
	}
	
	
	
	// utilizzo del Singleton Design Pattern
	
	/* questo metodo viene invocato all'interno getManager, e crea un'istanza di ManagerImpl */
	private static class SingletonHelper {
		private static final ManagerImpl MANAGER = new ManagerImpl();
	}
	
	/* invocando questo metodo per la prima volta, invoco la classe SigletonHelper 
	 * quando lo invoco le volte succesive, mi ritorna la costante MANAGER*/
	public static ManagerImpl getManager() {
		return SingletonHelper.MANAGER;
	}
	
	/* questo metodo aggiunge un Director a linkDirectors
	 * AAAAAAAAAAAAAA: aggiungere controllo di non creare due direttori uguali */

	@Override
	public void hireDirector(Director hiredDirector) {
		this.linkDirectors.add(hiredDirector);
		
	}
	
	/* questo metodo rimuove un oggetto di tipo direttore, ricevendo in ingresso una stringa */

	@Override
	public void fireDirector (String directorName) {
		this.linkDirectors.remove(getDirectorByName(directorName));		
	}

	/* */
	
	@Override
	public Factory showFactoryInfo(String directorName) {
		return getDirectorByName(directorName).getFactory();
	
	}

	/* Viene passata una richiesta soddisfatta e si completa settando il nome del mittente
	 * Poi la si invia al treno come carico merce e si rimuove da tutti i direttori che hanno tale richiesta */
	
	@Override
	public void satisfiesRequest(Request requestApproved, String directorName) {
		requestApproved.setSendingFactory(getDirectorByName(directorName).getFactory());
		
		this.train.addRequest(requestApproved);
		// riscrivibile con stream
		for (Director d : linkDirectors) {
			if(d.getRequestsToSatisfy().contains(requestApproved))
				d.removeRequestToSatisfy(requestApproved);
		}
		
	}

	/* Viene passato un nome del direttore e ritorna il riferimento al direttore */
	
	
	private Director getDirectorByName(String directorName) {
		return this.linkDirectors.stream()
				.filter(d -> d.getName().equals(directorName))
				.findFirst()
				.get();
	}

	/* questo metodo ritorna un oggetto di tipo treno */
	
	
	/**
	 * Viene aggiunta la richiesta ai vari direttori che possono soddisfarla
	 * 
	 * @param quantità di materiale richiesto dall'utente 
	 * 		  nome del direttore
	 */
	
	
	private void sendRequest(Request request) {
		// riscrivibile con stream
		boolean satisfy = false;
		for (Director d : linkDirectors) {
			if(request.getSentMaterial().equals(d.getFactory().getMaterial())) {
				d.addRequestToSatisfy(request);
				satisfy = true;
			}
		}
		
		if(satisfy)
			this.linkGlobalRequests.add(request);
		else
			this.linkRequestsManager.add(request);
	}
	

	/**
	 * Prossima destinazione da raggiungere con il treno
	 * 
	 *  		  
	 */
	public void nextDestination() {
		this.train.nextDestination();		
	}
	
	/**
	 * Metodo che servirà per creare una nuova richiesta grazie al direttore specificato
	 * 
	 * @param quantità di metariale richiesta e nome del direttore
	 */
	public void createNewRequest(int quantity, String directorName) {
		sendRequest(getDirectorByName(directorName).newRequest(quantity));
	}
	
	/**
	 * Metodo che visualizza le informazioni riguardanti un direttore
	 * 
	 * 
	 * @param nome del direttore
	 */
	public Director showDirectorInfo(String directorName) {
		return getDirectorByName(directorName);
		
	}
	
	/**
	 * Metodo che visualizza le informazioni riguardanti il treno 
	 * 
	 * 
	 * @param 
	 */
	public void showTrainInfo() {
		this.train.TrainInfo();	
	}
	
	/**
	 * Metodo che visualizza le informazioni di una richiesta
	 * 
	 * 
	 * @param nome del direttore da cui prendere l'azienda
	 */
	public void showRequestInfo(int id){
		
	}
	
	
}
