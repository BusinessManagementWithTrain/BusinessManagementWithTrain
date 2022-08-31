package controller.classes;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


import controller.interfaces.Manager;
import exceptions.LowTrainCapacityException;
import exceptions.WrongNeededQuantityException;
import exceptions.AnotherAcceptedRequestException;
import exceptions.AnotherEmptyRequestIsPresentException;
import exceptions.DirectorIsAlreadyPresentException;
import exceptions.FullWarehouseException;
import model.interfaces.*;
import model.classes.*;

public class ManagerImpl implements Manager {
	
	/*
	 * Come specificato nella documentazione, la classe manager contiene, rispettivamente, il 
	 * riferimento alla classe stessa per poter sfruttare il SingleTon Design Pattern,
	 * un set di tutti i direttori assunti, un set di tutte le richieste sottisfabili unicamente dal manager,
	 * un set di tutte le richieste attive attualmente, il riferimento al negozio ed il riferimento al treno
	 */
	private static ManagerImpl manager    = null;
	
	private Set<Director> linkDirectors;
	private Set<Request> linkRequestsManager;
	private Set<Request> linkGlobalRequests;
	private Train train;
	
	/**
	 * Il costruttore serve per creare il treno con la capienza segnalata dall'utente
	 * ed inoltre inizializza tutti i vari campi.
	 * Il costruttore è privato per consentire l'utilizzo del SingleTon Design Pattern
	 * 
	 * @param trainCapacity la capacità del treno
	 * @throws LowTrainCapacityException 
	 */
	private ManagerImpl(int trainCapacity) throws LowTrainCapacityException {
		this.linkDirectors			= new HashSet<Director>();
		this.linkRequestsManager	= new HashSet<Request>();
		this.linkGlobalRequests 	= new HashSet<Request>();
		this.train 					= new TrainImpl(trainCapacity);
	}

	/*
	 * Sfruttando il SingleTon Design Pattern, necessitiamo di un metodo statico
	 * per l'allocazione della classe del manager e, dalla seconda invocazione, il metodo
	 * statico ci permetterà di avere il riferimento all'unica istanza del manager.
	 * Ad eccezione delle invocazioni effettuate passando un nuovo valore intero poichè in questo
	 * modo si andrà a creare un nuovo manager resettando le variabili
	 *
	 * @param la capacità del treno
	 */
	public static ManagerImpl getManager(int trainCapacity) throws LowTrainCapacityException{
		try {
			manager = new ManagerImpl(trainCapacity);
		} catch(LowTrainCapacityException e) {
			manager = null;
			throw e;
		}
		return manager;
	}
	
	public static ManagerImpl getManager() {
		return manager;
	}
	
	/*
	 * Metodo che restituisce un direttore assunto partendo dal nome 
	 * 
	 * @param nome del direttore
	 * @return il direttore associato al nome
	 */
	private Director getDirectorByName(String directorName) {
		return this.linkDirectors.stream()
								 .filter(d -> d.getName().equals(directorName))
								 .findFirst()
								 .get();
	}
	
	/*
	 * Metodo utilizzato per aggiungere un nuovo direttore al set dei direttori
	 * 
	 * @param direttore assunto
	 */
	@Override
	public void hireDirector(Director hiredDirector) throws DirectorIsAlreadyPresentException {	
		//Solo se il direttore non è presente e neanche l'azienda
		if(this.linkDirectors.contains(hiredDirector) ||
		   factoryIsPresent(hiredDirector.getFactory())) {
			throw new DirectorIsAlreadyPresentException();
		}
		
		this.linkDirectors.add(hiredDirector);
		
		/*
		 * Da questo momento vengono rivisionate tutte le ricerche già in circolo
		 * per selezionare quelle soddisfabili dal nuovo direttore
		 */
		Set<Request> requestsToAdd = new HashSet<>();
		requestsToAdd.addAll(fromLinkRequestToSpecificList(this.linkGlobalRequests, hiredDirector));
		requestsToAdd.addAll(fromLinkRequestToSpecificList(this.linkRequestsManager, hiredDirector));
		
		for (Request request : requestsToAdd) {
			try {
				hiredDirector.addRequestToSatisfy(request);
			} catch(WrongNeededQuantityException e) {}
		}

		/*
		 * Qui eliminiamo le richieste che prima erano soddisfabili solo dal manager
		 * ma che ora vengono soddisfatte dal nuovo direttore;
		 */
		this.linkRequestsManager.stream()
	    					    .filter(r -> r.getSentMaterial().equals(hiredDirector.getFactory().getMaterial().getProcessedMaterial()))
	    					    .forEach(r -> linkRequestsManager.remove(r));
		
	}
	
	/*
	 * Metodo privato utilizzato per filtrare un set restituendone
	 * un altro composto unicamente da richieste soddisfabili dal direttore richiesto
	 * 
	 * @param il set da filtrare
	 * @param il direttore assunto
	 */
	private Set<Request> fromLinkRequestToSpecificList(Set<Request> set, Director hiredDirector) {
		return set.stream()
				   .filter(r -> r.getSentMaterial().equals(hiredDirector.getFactory()
						   												.getMaterial()
						   												.getProcessedMaterial()))
				   .collect(Collectors.toSet());
	}
	
	/*
	 * Metodo utilizzato per rimuovere un direttore dal set dei direttori
	 * 
	 * @param nome del direttore licenziato
	 */
	@Override
	public void fireDirector (String directorName) {
		//Salvataggio del direttore in una variabile d'appoggio
		Director firedDirector = getDirectorByName(directorName);
		
		this.linkDirectors.remove(getDirectorByName(directorName));
		
		// elimino le richieste del direttore da linkRequestsManager
		removeRequestBySet(linkRequestsManager, firedDirector);
		
		// elimino le richieste del direttore da linkGlobalManager
		removeRequestBySet(linkGlobalRequests, firedDirector);
		
		/*
		 * A questo punto elimino tutte le richieste precedentemente
		 * create dal direttore licenziato
		 */
		this.linkDirectors.stream()
						  .forEach(d -> d.getRequestsToSatisfy().stream()
																.filter(r -> r.getReceiverFactory().equals(firedDirector.getFactory()))
																.collect(Collectors.toSet()).stream()
																							.forEach(r -> d.removeRequestToSatisfy(r)));
		
		/*
		 * Resetto le richieste precedentemente create dal direttore
		 * licenziato e successivamente accettate da un altro direttore
		 */
		this.linkDirectors.stream()
		  				  .filter(d -> d.getAcceptedRequest() != null && 
		  				  		  d.getAcceptedRequest().getReceiverFactory().equals(firedDirector.getFactory()))
		  				  .forEach(Director::setAcceptedRequestToNull);
		
		/*
		 * Prendo le richieste precedentemente create dal direttore
		 * licenziato, successivamente accettate da un altro direttore
		 * ed infine caricate sul treno e gli setto come destinazione il negozio
		 */
		this.train.getUnloadingRequests().stream()
									     .filter(r -> r.getReceiverFactory().equals(firedDirector.getFactory()))
									     .forEach(r -> r.setReceiverFactoryToStore());
		
		//Rimetto in circolo tutte le richieste che poteva soddisfare il direttore licenziato
		firedDirector.getRequestsToSatisfy().stream().forEach(r -> sendRequest(r));
		
		if(firedDirector.getAcceptedRequest() != null) {
			sendRequest(firedDirector.getAcceptedRequest());
		}
												
	}
	
	/*
	 * Metodo privato che rimuove da un set specificato tutte le richieste
	 * in cui compare l'azienda del direttore licenziato
	 * 
	 * @param il set da filtrare
	 * @param il direttore licenziato
	 */
	private void removeRequestBySet(Set<Request> set, Director firedDirector) {
		set.stream()
		   .filter(r -> r.getReceiverFactory().equals(firedDirector.getFactory()))
		   .collect(Collectors.toSet())
		   .stream()
		   .forEach(r -> set.remove(r));
	}
		
	/*
	 * Metodo che aggiunge la richiesta ai vari direttori che possono soddisfarla
	 * 
	 * @param quantità di materiale richiesto dall'utente
	 */
	@Override
	public void sendRequest(Request request) {
		boolean satisfy = false;
		for (Director d : this.linkDirectors) {
			if(request.getSentMaterial().equals(d.getFactory().getMaterial().getProcessedMaterial())) {
				try {
					d.addRequestToSatisfy(request);
					satisfy = true;
				} catch(WrongNeededQuantityException e) {}
			}
		}
		
		if(satisfy)
			this.linkGlobalRequests.add(request);
		else
			this.linkRequestsManager.add(request);
	}
	
	/*
	 * Metodo che restituisce un valore booleano in base alla presenza
	 * dell'azienda tra i direttori assunti
	 * 
	 * @param azienda da cercare
	 * @return
	 */
	@Override
	public boolean factoryIsPresent(Factory factory) {
		return this.linkDirectors.stream()
				   				 .map(Director::getFactory)
				   				 .collect(Collectors.toSet()).contains(factory);
	}

	/*
	 * Viene passata una richiesta che verrà successivamente inviata al treno 
	 * come carico merce e verrà rimossa da tutti i direttori che hanno tale richiesta
	 * 
	 * @param richiesta soddisfatta
	 */
	@Override
	public void satisfiesRequestDirector(Request requestApproved, String directorName) throws AnotherAcceptedRequestException {
		getDirectorByName(directorName).satisfyRequest(requestApproved);
		
		this.train.addRequest(requestApproved);
		
		this.linkDirectors.stream()
						  .filter(d -> d.getRequestsToSatisfy().contains(requestApproved))
						  .forEach(d -> d.removeRequestToSatisfy(requestApproved));
	}
	
	/*
	 * Viene passata una richiesta al Manager che verrà subito soddisfatta senza passare per il treno
	 * Successivamente verrà eliminata dalle richieste del Manager
	 * 
	 * @param richiesta soddisfatta
	 */
	public void satisfiesRequestManager(Request requestApproved) throws FullWarehouseException {
		requestApproved.getReceiverFactory().getLoadingWarehouse().addMaterial(requestApproved.getSentQuantity());
		linkRequestsManager.remove(requestApproved);
	}
	
		
	/*
	 * Prossima destinazione da raggiungere con il treno  
	 */
	@Override
	public void nextDestination() throws Exception {
		this.train.nextDestination();		
	}

	/*
	 * Metodo che servirà per creare una nuova richiesta grazie al direttore specificato
	 * 
	 * @param quantità di metariale richiesta
	 * @param nome del direttore
	 */
	@Override
	public void createNewRequest(int quantity, String directorName) throws WrongNeededQuantityException {
		sendRequest(getDirectorByName(directorName).newRequest(quantity));
	}

	
	/*
	 * Metodo che permette di svuotare il magazzino con il materiale lavorato tramite il nome di un direttore
	 * 
	 * @param nome del direttore
	 */
	@Override
	public void emptyWarehouse(String directorName) throws WrongNeededQuantityException, AnotherEmptyRequestIsPresentException{
		this.train.addRequest(this.getDirectorByName(directorName).emptyWarehouse());
	}

	/*
	 * Metodo che visualizza le informazioni riguardanti un direttore
	 * 
	 * @param nome del direttore
	 * @return direttore associato al nome
	 */
	@Override
	public Director showDirectorInfo(String directorName) {
		return getDirectorByName(directorName);	
	}
	
	/*
	 * Viene passato come parametro il nome di un direttore,
	 * verrà restituita l'azienda del direttore
	 * 
	 * @param nome del direttore da cui prendere l'azienda
	 * @return l'azienda associata al direttore
	 */
	@Override
	public Factory showFactoryInfo(String directorName) {
		return getDirectorByName(directorName).getFactory();
	}
	
	/*
	 * Metodo che visualizza le informazioni riguardanti il treno 
	 * 
	 * @return il treno
	 */
	
	@Override

	public Train showTrainInfo() {
		return this.train;
	}

	/*
	 *  Metodo che ritorna la lista dei direttori assunti dal Manager
	 * 
	 *  @return la lista di Direttori
	 */
	public Set<Director> getLinkDirectors() {
		return linkDirectors;
	}
	
	/*
	 *  Metodo che ritorna la lista delle richieste accettabili esclusivamente manager
	 * 
	 *  @return la lista di richieste del Manager
	 */
	public Set<Request> getlinkRequestsManager() {
		return linkRequestsManager;
	}

	/*
	 *  Metodo che ritorna il direttore data una specifica azienda
	 * 
	 *  @param Factory
	 *  @return il Direttore associato
	 */
	public Director getDirectorByFactory(Factory factory) {
		return this.linkDirectors.stream()
				 .filter(d -> d.getFactory().equals(factory))
				 .findFirst()
				 .get();
	}
}
