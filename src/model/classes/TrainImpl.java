package model.classes;

import java.util.Collections;

import java.util.LinkedHashMap;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import controller.classes.ManagerImpl;
import exceptions.EmptyDestinationsSetException;
import exceptions.FullTrainException;
import exceptions.FullWarehouseException;
import model.interfaces.Factory;
import model.interfaces.Material;
import model.interfaces.Request;
import model.interfaces.Staff;
import model.interfaces.Store;
import model.interfaces.Train;
import model.interfaces.Warehouse;

/**
 * Classe destinata all'implementazione dell'interfaccia del treno, ovvero l'oggetto
 * che avrà come compito quello di caricare e scaricare merce dai magazzini di carico e di
 * scarico delle aziende
 * 
 * @author Rinaldi Simone
 */

public class TrainImpl implements Train {
	/*
	 * Come specificato dalla documentazione, il treno avrà rispettivamente un set di richieste 
	 * per caricare il materiale sul treno, un set di richieste per scaricare il materiale dal treno,
	 * una mappa per tenere traccia della quantità relativa ad ogni materiale caricato sul treno,
	 * un set per tenere traccia delle varie destinazioni che il treno dovrà raggiungere,
	 * un riferimento al negozio unico e la capienza massima dello stesso
	 */
	private final Set<Request> loadingRequests;
	private final Set<Request> unloadingRequests;
	private final Map<String,Integer> stuffMap;
	private final Set<Factory> destinationsSet;
	private Factory currentDestination;
	private final int maxCapacity;
	private int quantitytoLoad;
	private int quantitytoUnLoad;
	/**
	 * Il costruttore servirà principalmente per associare il negozio e la capienza massima del treno,
	 * ma anche per inizializzare tutte le strutture dati
	 * 
	 * @param capacitÃ  massima di spazio del treno
	 * @param istanza del negozio
	 */
	public TrainImpl(final int maxCapacity) {
		this.loadingRequests 		= new LinkedHashSet<>();
		this.unloadingRequests		= new LinkedHashSet<>();
		this.stuffMap 				= new LinkedHashMap<>();
		this.destinationsSet		= new LinkedHashSet<>();
		this.maxCapacity			= maxCapacity;
		this.quantitytoLoad			= 0;
		this.quantitytoUnLoad		= 0; 
	}		
	
	/*
	 * Metodo utilizzato per evitare ridondanza di codice, preso un set in input
	 * questo viene filtrato attraverso il predicato e restituito sotto forma di set
	 * 
	 * @param un set da filtrare
	 * @param un predicato che indica come filtrarlo
	 * @return il set filtrato
	 */
	private Set<Request> getFilteredReqestsSet(Set<Request> set, Predicate<? super Request> predicate){
		return set.stream()
				  .filter(predicate)
				  .collect(Collectors.toSet());	
	}
	
	/*
	 * Metodo utilizzato per evitare ridondanza di codice, restituisce un set contenente
	 * tutte le richieste che devono scaricare materiale nell'azienda attuale
	 * 
	 * @return il set delle richieste
	 */
	private Set<Request> getUnloadingRequest() {
		return getFilteredReqestsSet(this.unloadingRequests,
									 r -> r.getReceiverFactory().equals(getCurrentDestination()));
	}
	
	/*
	 * Metodo utilizzato per evitare ridondanza di codice, restituisce un set contenente
	 * tutte le richieste che devono caricare materiale dall'azienda attuale
	 * 
	 * @return il set delle richieste
	 */
	private Set<Request> getLoadingRequest() {
		return getFilteredReqestsSet(this.loadingRequests,
									 r -> r.getSendingFactory().equals(getCurrentDestination()));
	}
	
	/*
	 * Metodo che somma e restutisce la quantità totale di materiale da caricare o scaricare
	 * nell'azienda raggiunta
	 * 
	 *  @param un set nel quale cercare ed effettuare la somma
	 *  @param un predicato che indica se cercare le aziende in cui scaricare o quelle in cui caricare
	 *  @return la somma di tutti i materiali
	 */
	private int getQuantityToManage(Set<Request> set, Predicate<? super Request> predicate) {
		return getFilteredReqestsSet(set, predicate).stream()
							   						.mapToInt(Request::getSentQuantity)
							   						.sum();
	}	
	
	/*
	 * Metodo che controlla se il treno raggiunge la capacità massima aggiungendo una
	 * determinata quantità
	 * 
	 * @param la quantità da aggiungere al treno
	 * @return true o false in base al controllo
	 */
	private boolean isFull(int newLoad){
		return(getCurrentCapacity() + newLoad > this.maxCapacity);
	}
	
	/*
	 * Metodo per ottenere la capacità attuale del treno
	 * 
	 * @return capacità corrente del treno
	 */
	
	@Override
	public int getCurrentCapacity() {
		return this.stuffMap.values()
							.stream()
							.mapToInt(Integer::intValue)
							.sum();
	}
	
	/*
	 * Metodo utilizzato per evitare ridondanza di codice, controlla
	 * se c'è o meno una tappa utile
	 */
	private void checkDestinationSet() throws EmptyDestinationsSetException {
		if(this.destinationsSet.isEmpty()) {
			throw new EmptyDestinationsSetException("Destinations set empty, no destinations");
		}
	}
		
	/*
	 * Metodo che viene invocato ad ogni nuova stazione, il suo scopo è quello di scaricare la merce
	 * dal treno caricandola nei magazzini e viceversa, data la complessità del metodo si è ritenuto
	 * necessario commentarne il funzionamento dall'interno
	 */
	private void cargoManagment() throws FullWarehouseException,FullTrainException{
		
		
		// Il primo controllo viene effettuato sulla lista di scarico, si verifica che abbia almeno un elemento
		if(!getUnloadingRequest().isEmpty()) {
			
		
			try {
				
				// Se arriva qui vuol dire che c'è almeno una richiesta di scarico diretta a questa azienda
				// Salviamo la quantità di materiale che bisogna scaricare all'interno dell'azienda
				this.quantitytoUnLoad = getQuantityToManage(this.unloadingRequests, 
														   r -> r.getReceiverFactory().equals(getCurrentDestination()));
				
				
				//se la destinazione attuale è il negozio,scarico nel negozio
				if(getCurrentDestination().equals(StoreImpl.getStoreInstance())) {
					for (Request r : this.unloadingRequests) {
						
						if(r.getReceiverFactory().equals(getCurrentDestination())) {
							this.stuffMap.merge(r.getSentMaterial(),-quantitytoUnLoad, Integer::sum);	
						}
						
					}
					
				}
				
				else {
					// Riempiamo il magazzino di carico dell'azienda
					getCurrentDestination().getLoadingWarehouse()
										   .addMaterial(quantitytoUnLoad);
					
					
					// Scarichiamo dal treno la quantità di materiale richiesta
					//ricorda che non prendi il materiale direttamente dall'azienda ma dai magazzini della medesima
					this.stuffMap.merge(getCurrentDestination().getLoadingWarehouse().getMaterial(),
										- quantitytoUnLoad,
										Integer::sum);
					
					// Rimuovo tutte le richieste soddisfatte
					this.unloadingRequests.removeAll(getUnloadingRequest());
				}
			} catch (FullWarehouseException e) {
				
				/* Se nel magazzino non c'è spazio per scaricare tutta la merce, allora convoglio 
				 * tutte le richieste verso il negozio
				 */
				getUnloadingRequest().stream()
									 .forEach(r -> r.setSendingFactory(StoreImpl.getStoreInstance()));
				
				throw e;
				// GENERA UN'ECCEZIONE PER IL POPUP DELLA VIEW
			}
		}
		
		// Il secondo controllo viene effettuato sulla lista di carico, si verifica che abbia almeno un elemento
		if(!getLoadingRequest().isEmpty()) {
			// Se arriva qui vuol dire che c'è almeno una richiesta di carico in partenza da questa azienda
			// Salviamo la quantità di materiale che bisogna caricare all'interno del treno
			this.quantitytoLoad = getQuantityToManage(this.loadingRequests,
													   r -> r.getSendingFactory().equals(getCurrentDestination()));
			
			// Controlliamo se c'è spazio nel treno
			if(isFull(this.quantitytoLoad)) {
				
				/*
				 *  Se lo spazio non dovesse bastare, verrà spostata la tappa attuale
				 *  in fondo alla lista delle tappe così da poter riprovare a caricare
				 *  dopo aver svuotato un po' il treno
				 */
				Factory factoryTemp = getCurrentDestination();
				this.destinationsSet.remove(factoryTemp);
				this.destinationsSet.add(factoryTemp);
				new FullTrainException("WARNING: the train is full, you can't load stuff");
				
				// GENERA UN'ECCEZIONE PER IL POPUP DELLA VIEW
			}
			else {
				
				// Se arriva qui vuol dire che ci sono richieste e c'è lo spazio nel treno
				// Come prima cosa viene scaricato il materiale dal magazzino dell'azienda
				getCurrentDestination().getUnloadingWarehouse().addMaterial(- this.quantitytoLoad);
					
				// Successivamente viene caricato il materiale nel treno aggiornando la mappa dei materiali
				this.stuffMap.merge(getCurrentDestination().getUnloadingWarehouse().getMaterial(), this.quantitytoLoad, Integer::sum);
				
				// Aggiorno il set delle tappe aggiungendo tutte le aziende di destinazione non presenti
				getLoadingRequest().stream()
								   .forEach(r ->{
									   this.destinationsSet.add(r.getReceiverFactory());
				 					   this.unloadingRequests.add(r);
				 					   ManagerImpl.getManager().getDirectorByFactory(r.getSendingFactory()).setAcceptedRequestToNull();						   
				}); 
			}
		}
	}
	
	/*
	 * Metodo che invia il treno alla prossima tappa utile
	 */
	@Override
	public void nextDestination() throws FullWarehouseException,FullTrainException, EmptyDestinationsSetException{
		
		checkDestinationSet();
		currentDestination = List.copyOf(this.destinationsSet).get(0);
		cargoManagment();
		this.destinationsSet.remove(currentDestination);
	}
	
	/*
	 * Metodo che aggiunge una richiesta alla lista delle richieste di carico ed aggiunge l'azienda
	 * di carico alla lista delle tappe (solo se non presente)
	 * 
	 * @param la richiesta da aggiungere
	 */
	@Override
	public void addRequest(Request newRequest) {
		this.loadingRequests.add(newRequest);
		this.destinationsSet.add(newRequest.getSendingFactory());
	}
	
	/*
	 * Metodo che consente di avere il riferimento alla mappa della merce sul treno
	 * 
	 * @return la mappa della merce
	 */
	@Override
	public Map<String, Integer> getStuffMap() {
		return Collections.unmodifiableMap(this.stuffMap);
	}
	
	/*
	 * Metodo che consente di avere il riferimento alla tappa attuale del treno
	 * 
	 * @return la tappa attuale del treno
	 */
	@Override
	public Factory getCurrentDestination(){
		return currentDestination;
	}
	
	/*
	 * Metodo che consente di avere il riferimento alla capienza massima del treno 
	 *
	 * @return la capienza massima del treno
	 */
	@Override
	public int getMaxCapacity() {
		return this.maxCapacity;
		
	}
	/**
	 * Metodo che consente di avere il riferimento della quantità da caricare nel treno
	 *
	 * @return la quantità da caricare nel treno
	 */
	@Override
	public int getQuantitytoLoad() {
		return quantitytoLoad;
	}
	
	/**
	 * Metodo che consente di avere il riferimento della quantità da scaricare dal treno
	 *
	 * @return la quantità da scaricare dal treno
	 */
	@Override
	public int getQuantitytoUnLoad() {
		return quantitytoUnLoad;
	}
	
	
}
