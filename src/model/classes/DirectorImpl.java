package model.classes;

import model.interfaces.Factory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import controller.classes.ManagerImpl;
import exceptions.AnotherAcceptedRequestException;
import exceptions.AnotherEmptyRequestIsPresentException;
import exceptions.EmptyFieldException;
import exceptions.MaximumCharactersException;
import exceptions.WrongNeededQuantityException;
import exceptions.WrongSendingFactoryException;
import model.interfaces.Director;
import model.interfaces.Request;

/**
* Classe destinata all'implementazione dell'interfaccia del direttore, ovvero l'oggetto
* che gestir� un'azienda e comunicher� con gli altri direttori all'atto dell'utilizzo del programma.
* 
* @author Grasso Emanuele
*
*/

public class DirectorImpl implements Director {
	
	private final static int MIN_NAME_CHAR = 1;
	private final static int MAX_NAME_CHAR = 12;
	
	/* 
	 * Come specificato dalla documentazione, ogni direttore avrà un nome,
	 * un'unica azienda da gestire, un set di richieste da poter soddisfare
	 * e la richiesta da completare attualmente accettata
	 */
	private final String name;
	private final Factory factory;
	private final Set<Request> requestsToSatisfy;
	private Request acceptedRequest;
	
	/**
	 * Il costruttore servirà principalmente ad associare il nome e
	 * l'azienda al corrispettivo direttore
	 * 
	 * @param azienda
	 * @throws MaximumCharactersException 
	 * @throws EmptyFieldException 
	 */
	public DirectorImpl(final String name, final Factory factory) throws MaximumCharactersException, EmptyFieldException {
		if(name.length() < MIN_NAME_CHAR) {
			throw new EmptyFieldException();
		} else if(name.length() > MAX_NAME_CHAR) {
			throw new MaximumCharactersException();
		}
		
		this.name 				= name;
		this.factory 			= factory;
		this.requestsToSatisfy	= new HashSet<>();
	}

	/*
	 * Questo metodo permette di fare un controllo sulla capacità del magazzino
	 * di carico, così da garantire in ingresso solo le richieste soddisfabili
	 *
	 * @param la quantità della richiesta da verificare
	 * @param la capienza del magazzino da paragonare
	 * @throws WrongNeededQuantityException
	 */
	private void checkWarehouseCapacity(int neededQuantity, int warehouseCapacity) throws WrongNeededQuantityException {
		if(neededQuantity < 1 || neededQuantity > warehouseCapacity) {
			throw new WrongNeededQuantityException();
		}
	}
	
	/*
	 * Consente al direttore di creare una richiesta per ricevere il materiale
	 * da lavorare
	 * 
	 * @param la quantità specificata dall'utente
	 * @return la richiesta per ricevere il materiale 
	 */
	@Override
	public Request newRequest(int neededQuantity) throws WrongNeededQuantityException {
		
		checkWarehouseCapacity(neededQuantity,
							   this.factory.getLoadingWarehouse().getTotalCapacity());
		
		return new RequestImpl(this.factory,
							   this.factory.getLoadingWarehouse().getMaterial(),
							   neededQuantity);
	}
	
	/*
	 * Crea la richiesta per svuotare il magazzino di prodotti lavorati
	 *  
	 * @return la richiesta per svuotare
	 */
	@Override
	public Request emptyWarehouse() throws WrongNeededQuantityException, AnotherEmptyRequestIsPresentException {
		if(this.factory.getUnloadingWarehouse().getCurrentCapacity() == 0) {
			throw new WrongNeededQuantityException();
		} else if(ManagerImpl.getManager()
						     .showTrainInfo()
							 .getLoadingRequests().stream()
												  .anyMatch(r -> r.getReceiverFactory().equals(StoreImpl.getStoreInstance()) &&
														  	     r.getSendingFactory().equals(this.factory))) {
			throw new AnotherEmptyRequestIsPresentException();
		}
		
		return new RequestImpl(this.factory,
							   StoreImpl.getStoreInstance(),
							   this.factory.getUnloadingWarehouse().getMaterial(),
							   this.factory.getUnloadingWarehouse().getCurrentCapacity());
	}																				
	
	/*
	 * Consente al dirigente di aggiungere una richiesta al set delle
	 * richieste soddisfabili dal direttore
	 * @param richiestaDaSoddisfare
	 */
	@Override
	public void addRequestToSatisfy(Request requestToSatisfy) throws WrongNeededQuantityException {
		checkWarehouseCapacity(requestToSatisfy.getSentQuantity(),
							   this.factory.getUnloadingWarehouse().getTotalCapacity());
		
		this.requestsToSatisfy.add(requestToSatisfy);
	}
	
	/*
	 * Consente al dirigente di rimuovere una richiesta dalla lista delle
	 * richieste soddisfabili poich� gi� soddisfatta da un altro direttore
	 * 
	 * @param richiestaSoddisfatta
	 */
	@Override
	public void removeRequestToSatisfy(Request requestToBeRemoved) {
		this.requestsToSatisfy.remove(requestToBeRemoved);
	}
	
	/*
	 * Consente al direttore di soddisfare una richiesta
	 * precedentemente inviata da un altro direttore 
	 * 
	 * @param richiestaDaSoddisfare
	 */
	@Override
	public void satisfyRequest(Request requestFulfilled) throws AnotherAcceptedRequestException {
		if(this.acceptedRequest != null) {
			throw new AnotherAcceptedRequestException();
		}
		
		try {
			requestFulfilled.setSendingFactory(this.factory);
		} catch (WrongSendingFactoryException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		this.acceptedRequest = requestFulfilled;
		this.requestsToSatisfy.remove(requestFulfilled);
	}
	
	/*
	 * Consente al treno di resettare la richiesta del direttore in quanto soddisfatta
	 */
	@Override
	public void setAcceptedRequestToNull() {
		this.acceptedRequest = null;
	}

	/*
	 * Consente di avere il riferimento al set delle richieste da soddisfare
	 * 
	 * @return set delle richieste
	 */
	@Override
	public Set<Request> getRequestsToSatisfy() {
		return Collections.unmodifiableSet(this.requestsToSatisfy);
	}
	
	/*
	 * Consente di avere il riferimento alla richiesta attualmente accettata o null in
	 * caso questa non sia presente
	 * 
	 * @return richiesta accettata
	 */
	@Override
	public Request getAcceptedRequest() {
		return this.acceptedRequest;
	}

	/*
	 * Consente di avere il riferimento al nome del direttore
	 * 
	 * @return il nome del direttore
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/*
	 * Consente di avere il riferimento all'azienda gestita dal direttore
	 * 
	 * @return azienda gestita dal direttore
	 */
	@Override
	public Factory getFactory() {
		return this.factory;
	}

	
	@Override
	public int hashCode() {
		int value = 0;
		
		for (int i = 0; i < this.name.length(); i++) {
			value += this.name.toCharArray()[i] + i;
		}
		
		return value * this.name.length();
	}
	
	/*
	 * Come specificato nella documentazione, due direttori sono considerati
	 * uguali se gestiscono la stessa azienda o hanno lo stesso nome
	 *
	 * @param oggetto da controllare
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirectorImpl other = (DirectorImpl) obj;
		return this.name.equals(other.name) ||
			   this.factory.equals(other.factory);
	}
	
	
}