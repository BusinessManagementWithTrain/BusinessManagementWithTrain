package model.classes;

//import dalle varie librerie di sistema
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import controller.classes.ManagerImpl;
import exceptions.EmptyDestinationsSetException;
import exceptions.EmptyWarehouseException;
import exceptions.FullTrainException;
import exceptions.FullWarehouseException;
import model.interfaces.Factory;
import model.interfaces.Request;
import model.interfaces.Train;

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
	private final Queue<Factory> destinationsSet;
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
		this.destinationsSet		= new UniqueQueue<>();
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
	private void isFull(int newLoad) throws FullTrainException{
		if(getCurrentCapacity() + newLoad > this.maxCapacity) {
			throw new FullTrainException();
		}
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
	private void cargoManagement() throws Exception{
		this.quantitytoLoad=0;
		this.quantitytoUnLoad=0;
		List<Request> filteredList;
		Exception myException = null;
		// Il primo controllo viene effettuato sulla lista di scarico, si verifica che abbia almeno un elemento
		if(!getUnloadingRequest().isEmpty()) {
			
			//lista filtrata con le sole aziende che hanno come azienda ricevente la destinazione corrente
			filteredList = this.unloadingRequests.stream().filter(i->i.getReceiverFactory().equals(currentDestination)).toList();
			
			
				//ciclo su ogni richiesta della lista filtrata
				for (Request r : filteredList) {
					
					try {
							this.currentDestination.getLoadingWarehouse().addMaterial(r.getSentQuantity());
							this.quantitytoUnLoad =	mapManage(quantitytoUnLoad, r.getSentMaterial(), - r.getSentQuantity());	
							this.unloadingRequests.remove(r);
							
					} catch (FullWarehouseException e) {
						r.setReceiverFactoryToStore();
						this.destinationsSet.add(StoreImpl.getStoreInstance());
						this.destinationsSet.remove(currentDestination);
						myException=e;
					}
				
				}
			
		}
		
		// Il secondo controllo viene effettuato sulla lista di carico, si verifica che abbia almeno un elemento
		if(!getLoadingRequest().isEmpty()) {
			filteredList = this.loadingRequests.stream().filter(i->i.getSendingFactory().equals(currentDestination)).toList();
			
			for (Request r : filteredList) {
						try {
							isFull(r.getSentQuantity());
							currentDestination.getUnloadingWarehouse().removeMaterial(r.getSentQuantity()); 

							this.quantitytoLoad = mapManage(quantitytoLoad, r.getSentMaterial(), r.getSentQuantity());
	
						 	this.loadingRequests.remove(r);
						 	this.unloadingRequests.add(r);
						 	this.destinationsSet.add(r.getReceiverFactory()); 
						 	
						 	//controllo per rimuovere la richiesta accettata
						 	if(r.getReceiverFactory() != StoreImpl.getStoreInstance()) {
						 		ManagerImpl.getManager().getDirectorByFactory(currentDestination).setAcceptedRequestToNull();
						 	}
						} catch (EmptyWarehouseException | FullTrainException e) {
							this.destinationsSet.remove(currentDestination);
							this.destinationsSet.add(currentDestination);
						}
			}
		}
		
		if(myException != null){
			
			throw myException;
			
		}
	}
	

	//metodo che gestisce carico e scarico del treno
	private int mapManage(int quantity,String material,int sentQuanity) {

		//aggiungo sul treno
		this.stuffMap.merge(material,sentQuanity, Integer::sum); 
		
		//metto in valore assoluto così che risulti sempre positiva
		return Math.abs(sentQuanity);
	}

	/*
	 * Metodo che invia il treno alla prossima tappa utile
	 */
	@Override
	public void nextDestination() throws Exception{
		
		checkDestinationSet();
		currentDestination = this.destinationsSet.peek();
		cargoManagement();
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

	@Override
	public String toString() {
		return "TrainImpl [loadingRequests=" + loadingRequests + ", unloadingRequests=" + unloadingRequests
				+ ", destinationsSet=" + destinationsSet + ", currentDestination=" + currentDestination + "]";
	}
	
	
}

