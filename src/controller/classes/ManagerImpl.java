package controller.classes;

import java.util.HashSet;
import java.util.List;
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
	 * Come specificato nella documentazione, la classe manager conterrà, rispettivamente, in primis il 
	 * riferimento alla classe stessa per poter sfruttare il SingleTon Design Pattern, dopodichè conterrà
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
	 * Sfruttando il SingleTon Design Pattern, necessiteremo di un metodo statico
	 * per l'allocazione della classe del manager e, dalla seconda invocazione, il metodo
	 * statico ci permetterà di avere il riferimento all'unica istanza del manager.
	 * Ad eccezione delle invocazioni effettuate passando un nuovo valore intero che questo
	 * andrà a creare un nuovo manager resettando le variabili
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
	 * Viene passato un nome del direttore e ritorna il riferimento al direttore 
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
	 * Viene aggiunta la richiesta ai vari direttori che possono soddisfarla
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
	
	
	public boolean factoryIsPresent(Factory factory) {
		return this.linkDirectors.stream()
				   				 .map(Director::getFactory)
				   				 .collect(Collectors.toSet()).contains(factory);
	}
	
	/*
	 * Viene passato un riferimento all'oggetto direttore da aggiungere al set dei direttori
	 * e gli vengono aggiunte le richieste accettabili
	 * 
	 * @param direttore assunto
	 */
	@Override
	public void hireDirector(Director hiredDirector) throws DirectorIsAlreadyPresentException {	
		if(this.linkDirectors.contains(hiredDirector) ||
		   factoryIsPresent(hiredDirector.getFactory())) {
			throw new DirectorIsAlreadyPresentException();
		}
		
		this.linkDirectors.add(hiredDirector);
		
		Set<Request> requestsToAdd = new HashSet<>();
		requestsToAdd.addAll(fromLinkRequestToSpecificList(this.linkGlobalRequests, hiredDirector));
		requestsToAdd.addAll(fromLinkRequestToSpecificList(this.linkRequestsManager, hiredDirector));
		
		for (Request request : requestsToAdd) {
			try {
				hiredDirector.addRequestToSatisfy(request);
			} catch(WrongNeededQuantityException e) {}
		}

		this.linkRequestsManager.stream()
	    					    .filter(r -> r.getSentMaterial().equals(hiredDirector.getFactory().getMaterial().getProcessedMaterial()))
	    					    .forEach(r -> linkRequestsManager.remove(r));
		
	}
	
	private List<Request> fromLinkRequestToSpecificList(Set<Request> list, Director hiredDirector) {
		return list.stream()
				   .filter(r -> r.getSentMaterial().equals(hiredDirector.getFactory()
						   												.getMaterial()
						   												.getProcessedMaterial()))
				   .toList();
	}
	
	/*
	 * Viene passato il nome di un direttore da rimuovere dal set dei direttori,
	 * Inoltre verranno eliminate le richieste che hanno come destinazione l'azienda del direttore da licenziare
	 * 
	 * @param nome del direttore licenziato
	 */
	@Override
	public void fireDirector (String directorName) {
		Director firedDirector = getDirectorByName(directorName);
		
		this.linkDirectors.remove(getDirectorByName(directorName));
		
		// elimino le richieste del direttore da linkRequestsManager
		removeRequestBySet(linkRequestsManager, firedDirector);
		
		// elimino le richieste del direttore da linkGlobalManager
		removeRequestBySet(linkGlobalRequests, firedDirector);
		
		/* 
		 * prendiamo i direttori
		 * cerchiamo i direttori che hanno almeno una richiesta da inviare all'azienda del directorName
		 * rimuoviamo tutti le richieste dai direttori */
		
		
		/*
		 * Prendo i direttori
		 * cerco i direttori che hanno almeno una richiesta con destinazione la tappa
		 * rimuovo le richieste con la tappa
		 */
		this.linkDirectors.stream()
						  .forEach(d -> d.getRequestsToSatisfy().stream()
																.filter(r -> r.getReceiverFactory().equals(firedDirector.getFactory()))
																.collect(Collectors.toSet()).stream()
																							.forEach(r -> d.removeRequestToSatisfy(r)));
		
		// per ogni direttore che ha richieste di scarico nell'azienda del direttore da eliminare, 
		// invoco il metodo che mi permette di resettare la richiesta del direttore
		this.linkDirectors.stream()
		  				  .filter(d -> d.getAcceptedRequest() != null && 
		  				  		  d.getAcceptedRequest().getReceiverFactory().equals(firedDirector.getFactory()))
		  				  .forEach(Director::setAcceptedRequestToNull);
		
		// imposto come destinazione lo store a quelle richieste, contenute nel treno, che hanno come destinazione l'azienda del direttore da eliminare
		this.train.getUnloadingRequests().stream()
									     .filter(r -> r.getReceiverFactory().equals(firedDirector.getFactory()))
									     .forEach(r -> r.setReceiverFactoryToStore());
		
		
		firedDirector.getRequestsToSatisfy().stream().forEach(r -> sendRequest(r));
		
		if(firedDirector.getAcceptedRequest() != null) {
			sendRequest(firedDirector.getAcceptedRequest());
		}
												
	}
	
	private void removeRequestBySet(Set<Request> set, Director firedDirector) {
		set.stream()
		   .filter(r -> r.getReceiverFactory().equals(firedDirector.getFactory()))
		   .collect(Collectors.toSet())
		   .stream()
		   .forEach(r -> set.remove(r));
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
