package model.classes;

import java.util.Collections;
import java.util.LinkedHashMap;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import model.interfaces.Factory;
import model.interfaces.Material;
import model.interfaces.Request;
import model.interfaces.Store;
import model.interfaces.Train;

/**
 * Breve descrizione della classe del treno
 * 
 * @author Rinaldi Simone
 *
 */

public class TrainImpl implements Train {
	/*
	 * Come specificato dalla documentazione, il treno avr‡ rispettivamente un set di richieste 
	 * per caricare il materiale sul treno, un set di richieste per scaricare il materiale dal treno,
	 * una mappa per tenere traccia della quantit‡ relativa ad ogni materiale caricato sul treno,
	 * un set per tenere traccia delle varie destinazioni che il treno dovr‡ raggiungere,
	 * un riferimento al negozio unico e la capienza massima dello stesso
	 */
	private final Set<Request> loadingRequests;
	private final Set<Request> unloadingRequests;
	private final Map<Material,Integer> stuffMap;
	private final Set<Factory> destinationsSet;
	private final Store store;
	private final int maxCapacity;
	
	/**
	 * Il costruttore servir‡ principalmente per associare il negozio e la capienza massima del treno,
	 * ma anche per inizializzare tutte le strutture dati
	 * 
	 * @param capacit√† massima di spazio del treno
	 * @param istanza del negozio
	 */
	public TrainImpl(final int maxCapacity, final Store store) {
		this.loadingRequests 		= new LinkedHashSet<>();
		this.unloadingRequests		= new LinkedHashSet<>();
		this.stuffMap 				= new LinkedHashMap<>();
		this.destinationsSet		= new LinkedHashSet<>();
		
		this.store 					= store;
		this.maxCapacity			= maxCapacity;
	}		
	
	/*
	 * Metodo utilizzato per evitare ridondanza di codice, preso un set in input, preso un set in input
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
	 * Metodo che somma e restutisce la quantit‡ totale di materiale da caricare o scaricare
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
	 * Metodo che controlla se il treno raggiunge la capacit‡ massima aggiungendo una
	 * determinata quantit‡
	 * 
	 * @param la quantit‡ da aggiungere al treno
	 * @return true o false in base al controllo
	 */
	private boolean isFull(int newLoad){
		return(getCurrentCapacity() + newLoad > this.maxCapacity);
	}
	
	/*
	 * Metodo per ottenere la capacit‡ attuale del treno
	 * 
	 * @return capacit‡†corrente del treno
	 */
	private int getCurrentCapacity() {
		return this.maxCapacity - this.stuffMap.values()
										  	   .stream()
										  	   .mapToInt(Integer::intValue)
										  	   .sum();
	}
	
	/*
	 * Metodo utilizzato per evitare ridondanza di codice, controlla
	 * se c'Ë o meno una tappa utile
	 */
	private void checkDestinationSet() {
		if(this.destinationsSet.isEmpty()) {
			//eccezione
		}
	}
		
	/*
	 * Metodo che viene invocato ad ogni nuova stazione, il suo scopo Ë quello di scaricare la merce
	 * dal treno caricandola nei magazzini e viceversa, data la complessit‡ del metodo si Ë ritenuto
	 * necessario commentarne il funzionamento dall'interno
	 */
	private void cargoManagment() {
		// Il primo controllo viene effettuato sulla lista di scarico, si verifica che abbia almeno un elemento
		if(!getUnloadingRequest().isEmpty()) {
			try {
				// Se arriva qui vuol dire che c'Ë almeno una richiesta di scarico diretta a questa azienda
				// Salviamo la quantit‡ di materiale che bisogna scaricare all'interno dell'azienda
				int quantityToManage = getQuantityToManage(this.unloadingRequests, 
														   r -> r.getReceiverFactory().equals(getCurrentDestination()));
				
				// Riempiamo il magazzino di carico dell'azienda
				getCurrentDestination().getLoadingWarehouse()
									   .addMaterial(quantityToManage);
				
				// Scarichiamo dal treno la quantit‡ di materiale richiesta
				this.stuffMap.merge(getCurrentDestination().getMaterial(),
									- quantityToManage,
									Integer::sum);
				
				// Rimuovo tutte le richieste soddisfatte
				this.unloadingRequests.removeAll(getUnloadingRequest());
				
			} catch (Exception e/*magazzino pieno*/) {
				
				/* Se nel magazzino non c'Ë spazio per scaricare tutta la merce, allora convoglio 
				 * tutte le richieste verso il negozio
				 */
				getUnloadingRequest().stream()
									 .forEach(r -> r.setSendingFactory(this.store));
				
				// GENERA UN'ECCEZIONE PER IL POPUP DELLA VIEW
			}
		}
		
		// Il secondo controllo viene effettuato sulla lista di carico, si verifica che abbia almeno un elemento
		if(!getLoadingRequest().isEmpty()) {
			// Se arriva qui vuol dire che c'Ë almeno una richiesta di carico in partenza da questa azienda
			// Salviamo la quantit‡ di materiale che bisogna caricare all'interno del treno
			int quantityToManage = getQuantityToManage(this.loadingRequests,
													   r -> r.getSendingFactory().equals(getCurrentDestination()));
			
			// Controlliamo se c'Ë spazio nel treno
			if(isFull(quantityToManage)) {
				
				/*
				 *  Se lo spazio non dovesse bastare, verr‡ spostata la tappa attuale
				 *  in fondo alla lista delle tappe cosÏ da poter riprovare a caricare
				 *  dopo aver svuotato un po' il treno
				 */
				Factory factoryTemp = getCurrentDestination();
				this.destinationsSet.remove(factoryTemp);
				this.destinationsSet.add(factoryTemp);
				
				// GENERA UN'ECCEZIONE PER IL POPUP DELLA VIEW
			}
			else {
				// Se arriva qui vuol dire che ci sono richieste e c'Ë lo spazio nel treno
				// Come prima cosa viene scaricato il materiale dal magazzino dell'azienda
				getCurrentDestination().getUnloadingWarehouse().addMaterial(- quantityToManage);
					
				// Successivamente viene caricato il materiale nel treno aggiornando la mappa dei materiali
				this.stuffMap.merge(getCurrentDestination().getMaterial(), quantityToManage, Integer::sum);
				
				// Aggiorno il set delle tappe aggiungendo tutte le aziende di destinazione non presenti
				getLoadingRequest().stream()
								   .forEach(r -> this.destinationsSet.add(r.getReceiverFactory()));
					
				// Infine sposto tutte le richieste nel set delle richieste di scarico
				getLoadingRequest().stream()
								   .forEach(r -> this.unloadingRequests.add(r));
			}
		}
	}
	
	/*
	 * Metodo che invia il treno alla prossima tappa utile
	 */
	@Override
	public void nextDestination() {
		this.destinationsSet.remove(List.copyOf(this.destinationsSet).get(0));
		checkDestinationSet();
		cargoManagment();
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
	public Map<Material, Integer> getStuffMap() {
		return Collections.unmodifiableMap(this.stuffMap);
	}
	
	/*
	 * Metodo che consente di avere il riferimento alla tappa attuale del treno
	 * 
	 * @return la tappa attuale del treno
	 */
	@Override
	public Factory getCurrentDestination() {
		checkDestinationSet();
		return List.copyOf(this.destinationsSet).get(0);
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
}
