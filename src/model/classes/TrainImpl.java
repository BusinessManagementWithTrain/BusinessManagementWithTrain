package model.classes;

import java.util.LinkedHashMap;

import java.util.LinkedHashSet;
import java.util.LinkedList;
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
 *  Classe destinata all'implementazione dell'interfaccia del treno, ovvero l'oggetto
 *  che avrà come compito quello di caricare e scaricare merce dai magazzini di carico e di
 *  scarico delle aziende
 */
	


public class TrainImpl implements Train {
	
	
	//set di richieste di carico
	private Set<Request>loadingRequests;
	
	//set di richieste di scarico
	private Set<Request>unloadingRequests;
	
	//mappa che tiene traccia del materiale e della quantità
	private Map<Material,Integer>stuffMap;
	
	//set che tiene conto delle tappe del treno
	private Set<Factory>destinationsList;
	
	//negozio
	private Store store;
	
	//capacità massima del treno
	private final int maxCapacity;
	
	
	//costruttore del treno
	/**
	 * 
	 * @param capacità massima di spazio del treno
	 * @param istanza del negozio
	 */
	public TrainImpl(int max,Store s) {
		this.store=s;
		this.maxCapacity = max;
		this.loadingRequests = new LinkedHashSet<>();
		this.unloadingRequests= new LinkedHashSet<>();
		this.stuffMap = new LinkedHashMap<>();
		this.destinationsList= new LinkedHashSet<>();
	}		
	
	/*
	 * metodo per ottenere la capacità attuale del treno
	 * @return capacità corrente del treno
	 */
	private int getCurrentCapacity() {
		return this.maxCapacity - stuffMap.values().stream().mapToInt(Integer::intValue).sum();
	}
	
	/*
	 * metodo che controlla se il treno raggiunge la capacità massima col carico corrente
	 */
	private boolean isFull(int newLoad){
		return(getCurrentCapacity() + newLoad > this.maxCapacity);
	}
	
	/*somma della quantità materiale totale sul treno*/
	private int getQuantityToManage(Set<Request>set) {
		return  set.stream()
                .mapToInt(Request::getSentQuantity)
                .sum();
	}	
	
	//metodo per filtrare le richieste
	private Set<Request> getFilteredReqestsList(Set<Request> list,Predicate<?super Request> p){
		return list.stream().filter(p).collect(Collectors.toSet());	
	}
	
	//metodo per ottenere un set di richieste di carico con la stessa azienda mittente
	private Set<Request> getLoadingRequest(){
		return getFilteredReqestsList(this.loadingRequests, i->i.getSendingFactory()==getCurrentDestination());
	}
	
	//metodo per otterne un set di richieste di scarico con la stessa azienda ricevente
	private Set<Request> getUnloadingRequest() {
		return getFilteredReqestsList(this.unloadingRequests, i->i.getReceiverFactory()==getCurrentDestination());
	}
	
	//metodo per conoscere la tappa corrente del treno
	private Factory getCurrentDestination() {
			
		if(this.destinationsList.isEmpty()) {
			/*eccezione*/
		}
		return (Factory) this.destinationsList.toArray()[0];
	}
	
	
	//metodo per selezionare la prossima tappa
	@Override
	public void nextDestination() {
		this.destinationsList.remove(0);
		cargoManagment();
	}
		
	//metodo per la gestione della merce
	@Override
	public void cargoManagment() {
		
		if(destinationsList.isEmpty()) {
			/*eccezione*/
		}
		
		//controllo che vi siano richieste di scarico
		if(!(getUnloadingRequest().isEmpty())) {
			
			try {
		
				/*riempio il magazzino*/
				getCurrentDestination().getLoadingWarehouse()
									   .addMaterial(getQuantityToManage(unloadingRequests));
				
				/*scarico del materiale dal treno*/
				stuffMap.merge(getCurrentDestination().getMaterial(), - getQuantityToManage(unloadingRequests), Integer::sum);
				
				/*rimuovo dalle richieste di scarico che avevano come destinazione comune la tappa attuale*/
				getUnloadingRequest()
				.removeAll(getUnloadingRequest().stream()
													.filter(i->i.getReceiverFactory()==getCurrentDestination())
													.collect(Collectors.toSet()));
				
			} catch (Exception e/*magazzino pieno*/) {
				
				/*se nel magazzino non c'è spazio per scaricare la merce convoglio tutte le richieste verso il negozio*/
				getUnloadingRequest().stream()
									 .filter(i->i.getReceiverFactory()==getCurrentDestination())
									 .forEach(k->k.setSendingFactory(store));
			}
		}
		
		
		//controllo se la lista di richieste di carico contiene richieste da soddisfare
		if(!(getLoadingRequest().isEmpty())) {
			
			//controllo se il treno può soddisfare la richiesta
			if(isFull(getQuantityToManage(loadingRequests))) {
				
				/*spostare la tappa in fondo alla lista delle tappe*/
				
				Factory factoryTemp =getCurrentDestination();
				destinationsList.remove(factoryTemp);
				destinationsList.add(factoryTemp);
				
				
			}
			else {					
				//prelevo materiale dal magazzino
				getCurrentDestination().getUnloadingWarehouse().addMaterial(- getQuantityToManage(loadingRequests));
					
				//aggiorno la mappa dei materiali del treno
				stuffMap.merge(getCurrentDestination().getMaterial(),  getQuantityToManage(loadingRequests), Integer::sum);
					
				//aggiungo le tappe destinatarie(Receiver) della richiesta di carico corrente nella lista delle tappe
				getLoadingRequest().stream().forEach(i->destinationsList.add(i.getReceiverFactory()));
					
				//sposto la richiesta di carico corrente nelle richieste di scarico
				getLoadingRequest().stream().forEach(k->unloadingRequests.add(k));
			}
		}
	}
	
	//metodo per aggiungere una richiesta alla lista delle richieste
	@Override
	public void addRequest(Request newRequest) {
		this.loadingRequests.add(newRequest);
		this.destinationsList.add(newRequest.getSendingFactory());
	}
	
	
	
	
	
	
	/*Metodo per ottenere le richieste di carico*/
	public Set<Request> getLoadingRequests() {
		return this.loadingRequests;
	}

	/*Metodo per ottenere le richieste di scarico*/
	public Set<Request> getUnloadingRequests() {
		return this.unloadingRequests;
	}

	/*Metodo per ottenere mappa della merce sul treno*/
	public Map<Material, Integer> getStuffMap() {
		return this.stuffMap;
	}

	/*Metodo per ottenere set di destinazioni del treno */
	public Set<Factory> getDestinationsSet() {
		return this.destinationsList;
	}
	
	/*metodo per ottenere l'oggetto negozio*/
	public Store getStore() {
		return this.store;
	}

	/*metodo per ottenere la capacità totale del treno*/
	public int getMaxCapacity() {
		return this.maxCapacity;
	}

	
	
	
	
	
}
