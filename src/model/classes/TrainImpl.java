package model.classes;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import controller.classes.ManagerImpl;
import exceptions.EmptyDestinationsQueueException;
import exceptions.EmptyWarehouseException;
import exceptions.FullTrainException;
import exceptions.FullWarehouseException;
import exceptions.LowTrainCapacityException;
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
	 * una coda per tenere traccia delle varie destinazioni che il treno dovrà raggiungere, 
	 * un riferimento alla tappa attuale alla quale si trova il treno ed un'indicazione
	 * alla capienza massima dello stesso.
	 * Inoltre, per aiutare l'interfaccia grafica, il treno supporterà due ulteriori campi per far
	 * facilmente riferimento alla quantità di materiale caricato e scaricato.
	 */
	private static final int MIN_QUANTITY = 100;
	
	private final Set<Request> loadingRequests;
	private final Set<Request> unloadingRequests;
	private final Map<String,Integer> stuffMap;
	private final Queue<Factory> destinationsQueue;
	private Factory currentDestination;
	private final int maxCapacity;
	private int quantityToLoad;
	private int quantityToUnload;
	
	/**
	 * Il costruttore servirà principalmente per associare la capienza massima del treno 
	 * e per inizializzare tutte le strutture dati e gli indicatori
	 * 
	 * @param capacitÃ  massima di spazio del treno
	 * @throws LowTrainCapacityException 
	 */
	public TrainImpl(final int maxCapacity) throws LowTrainCapacityException {
		if(maxCapacity < MIN_QUANTITY) {
			throw new LowTrainCapacityException();
		}
		
		this.loadingRequests 		= new HashSet<>();
		this.unloadingRequests		= new HashSet<>();
		this.stuffMap 				= new LinkedHashMap<>();
		this.destinationsQueue		= new UniqueQueue<>();
		this.maxCapacity			= maxCapacity;
		this.currentDestination		= null;
		this.quantityToLoad			= 0;
		this.quantityToUnload		= 0; 
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
	 * Metodo che controlla se il treno raggiunge la capacità massima aggiungendo una
	 * determinata quantità
	 * 
	 * @param la quantità da aggiungere al treno
	 * @return true o false in base al controllo
	 */
	private void isFull(int newLoad) throws FullTrainException {
		if(getCurrentCapacity() + newLoad > this.maxCapacity) {
			throw new FullTrainException();
		}
	}
	
	/*
	 * Metodo utilizzato per evitare ridondanza di codice, controlla
	 * se c'è o meno una tappa utile
	 * 
	 * @throws EmptyDestinationsSetException
	 */
	private void checkDestinationSet() throws EmptyDestinationsQueueException {
		if(this.destinationsQueue.isEmpty()) {
			throw new EmptyDestinationsQueueException("Destinations set empty, no destinations");
		}
	}
	
	/*
	 * Metodo utilizzato per modificare i valori della mappa all'interno
	 * del treno
	 * 
	 * @param il materiale da modificare
	 * @param il valore da aggiungere/rimuovere dalla mappa
	 * @return il valore da aggiungere/rimuovere dalla mappa in valore assoluto
	 * 
	 */
	private int mapManage(String material, int sentQuanity) {
		this.stuffMap.merge(material, sentQuanity, Integer::sum); 
		
		return Math.abs(sentQuanity);
	}
		
	/*
	 * Metodo che viene invocato ad ogni nuova stazione, il suo scopo è quello di scaricare la merce
	 * dal treno caricandola nei magazzini e viceversa, data la complessità del metodo si è ritenuto
	 * necessario commentarne il funzionamento dall'interno
	 * 
	 * @throws Exception
	 */
	private void cargoManagement() throws Exception {
		/*
		 * Iniziamo inizializzando le variaili utilizzate per la grafica ed una variabile
		 * che conterrà eventuali eccezioni
		 */
		this.quantityToLoad = 0;
		this.quantityToUnload = 0;
		Exception myException = null;
		
		//Procediamo prima con le richieste di scarico così da liberare spazio prima di riempire
		if(!getUnloadingRequest().isEmpty()) {
		
			for (Request request : this.getUnloadingRequest()) {
				
				//Posizioniamo un try-catch all'interno di un ciclo for-each così da gestire eventuali eccezioni 
				try {
					/*
					 * A questo punto proviamo a scaricare singolarmente tutte le richieste
					 * provando prima a riempire il magazzino di carico che potrebbe generare
					 * un'eccezione, nel caso sia troppo pieno.
					 * Procedendo poi a svuotare il treno rimuovendo la richiesta ormai completata
					 */
					this.currentDestination.getLoadingWarehouse().addMaterial(request.getSentQuantity());
					
					this.quantityToUnload =	mapManage(request.getSentMaterial(),
													  - request.getSentQuantity());	
					
					this.unloadingRequests.remove(request);
					
				} catch (FullWarehouseException e) {
					/*
					 * Se arriviamo qui l'unico motivo può essere il magazzino troppo carico, in tal
					 * caso procediamo come da specifica, ovvero indirizzando la singola
					 * richiesta verso lo store, aggiungendolo alle destinazioni, e salvandoci 
					 * l'eccezione in modo da non bloccare l'esecuzione
					 */
					request.setReceiverFactoryToStore();
					this.destinationsQueue.add(StoreImpl.getStoreInstance());
					myException = e;
				} finally {
					//Infine rimuoviamo la destinazione attuale
					this.destinationsQueue.remove(this.currentDestination);
				}
			}	
		}
		
		//A questo punto procediamo con le richieste di carico (se presenti)
		if(!getLoadingRequest().isEmpty()) {
		
			//Inizialmente rimuoviamo la tappa corrente che, in caso di problemi, verrà riaggiunta
			this.destinationsQueue.remove(this.currentDestination);
			
			for (Request request : this.getLoadingRequest()) {
				
				//Posizioniamo un try-catch all'interno di un ciclo for-each così da gestire eventuali eccezioni
				try {
					/*
					 * A questo punto, per ogni richiesta, controlliamo prima la capienza del treno
					 * per poi procedere con la rimozione dal magazzino di scarico per caricare sul
					 * treno aggiornando la mappa dei materiali e la coda delle tappe.
					 */
					isFull(request.getSentQuantity());
					this.currentDestination.getUnloadingWarehouse().removeMaterial(request.getSentQuantity()); 

					this.quantityToLoad = mapManage(request.getSentMaterial(),
													request.getSentQuantity());
	
				 	this.loadingRequests.remove(request);
				 	this.unloadingRequests.add(request);
				 	this.destinationsQueue.add(request.getReceiverFactory());
					
				 	if(!request.getReceiverFactory().equals(StoreImpl.getStoreInstance())) {
				 		ManagerImpl.getManager().getDirectorByFactory(this.currentDestination).setAcceptedRequestToNull();
				 	}
				} catch (EmptyWarehouseException | FullTrainException e) {
					this.destinationsQueue.add(this.currentDestination);
					myException = e;
				}
			}
		}
		
		if(myException != null){
			throw myException;
		}
	}

	/*
	 * Metodo che invia il treno alla prossima tappa utile
	 * 
	 * @throws Exception
	 */
	@Override
	public void nextDestination() throws Exception {
		
		checkDestinationSet();
		this.currentDestination = this.destinationsQueue.peek();
		cargoManagement();
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
		this.destinationsQueue.add(newRequest.getSendingFactory());
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
		return this.currentDestination;
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
	/*
	 * Metodo che consente di avere il riferimento della quantità da caricare nel treno
	 *
	 * @return la quantità da caricare nel treno
	 */
	@Override
	public int getQuantitytoLoad() {
		return this.quantityToLoad;
	}
	
	/*
	 * Metodo che consente di avere il riferimento della quantità da scaricare dal treno
	 *
	 * @return la quantità da scaricare dal treno
	 */
	@Override
	public int getQuantitytoUnload() {
		return this.quantityToUnload;
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
	 * Metodo per ottenere il riferimento alle richieste di scarico
	 * 
	 * @return le richieste di scarico
	 */
	@Override
	public Set<Request> getUnloadingRequests() {
		return Collections.unmodifiableSet(this.unloadingRequests);
		
	}
	
	/*
	 * Metodo per ottenere il riferimento alle richieste di carico
	 * 
	 * @return le richieste di carico
	 */
	@Override
	public Set<Request> getLoadingRequests() {
		return Collections.unmodifiableSet(this.loadingRequests);
	}
}

