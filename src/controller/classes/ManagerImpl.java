package controller.classes;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import controller.interfaces.Manager;
import model.interfaces.*;
import model.classes.*;

public class ManagerImpl implements Manager {
	
	private static ManagerImpl manager = null;
	
	private Set<Director> linkDirectors;				// set di tutti i direttori
	private Set<Request> linkRequestsManager;			// set di richieste che può soddisfare il manager
	private Set<Request> linkGlobalRequests;			// set di tutte le richieste
	private Store store;								// 
	private Train train;
	
	
	private ManagerImpl(int trainCapacity) {
		this.linkDirectors			= new LinkedHashSet<Director>();
		this.linkRequestsManager	= new LinkedHashSet<Request>();
		this.linkGlobalRequests 	= new LinkedHashSet<Request>();
		this.store 					= new StoreImpl();
		this.train 					= new TrainImpl(trainCapacity,this.store);
	}
	
	
	
	// utilizzo del Singleton Design Pattern
	
	
	
	/* invocando questo metodo per la prima volta, invoco la classe SigletonHelper 
	 * quando lo invoco le volte succesive, mi ritorna la costante MANAGER*/
	public static ManagerImpl getManager(int trainCapacity) {
		if(manager == null) {
			manager = new ManagerImpl(trainCapacity);
		}
		return manager;
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

	/* questo metodo consente di ritornare un'azienda attraverso il nome del direttore
	 * 
	 * @param nome del direttore
	 *  */
	
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

		
	/*
	 * Viene aggiunta la richiesta ai vari direttori che possono soddisfarla
	 * 
	 * @param quantità di materiale richiesto dall'utente 
	 * 		  nome del direttore
	 */
	
	
	private void sendRequest(Request request) {
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
	

	/*
	 * Prossima destinazione da raggiungere con il treno
	 * 
	 *  		  
	 */
	public void nextDestination() {
		this.train.nextDestination();		
	}
	
	/*
	 * Metodo che servirà per creare una nuova richiesta grazie al direttore specificato
	 * 
	 * @param quantità di metariale richiesta e nome del direttore
	 */
	public void createNewRequest(int quantity, String directorName) {
		sendRequest(getDirectorByName(directorName).newRequest(quantity));
	}
	
	/*
	 * Metodo che visualizza le informazioni riguardanti un direttore
	 * 
	 * 
	 * @param nome del direttore
	 */
	public Director showDirectorInfo(String directorName) {
		return getDirectorByName(directorName);
		
	}
	
	/*
	 * Metodo che visualizza le informazioni riguardanti il treno 
	 * 
	 * 
	 * @param 
	 */
	public Train showTrainInfo() {
		return this.train;
	}
	
	/*
	 * Metodo che visualizza le informazioni di una richiesta
	 * 
	 * 
	 * @param valore dell'id
	 */
	public Request showRequestInfo(int id){
		return this.linkGlobalRequests.stream()
				.filter(r ->r.getRequestId() == (id))
				.findFirst().
				get();
	}
	
	
}
