package model.classes;

import model.interfaces.Factory;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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
	
	/* 
	 * Come specificato dalla documentazione, ogni direttore avr� un nome,
	 * un'unica azienda da gestire e un set di richieste da poter soddisfare
	 */
	private final String name;
	private final Factory factory;
	private final Set<Request> requestsToSatisfy;
	
	/**
	 * Il costruttore servir� principalmente ad associare il nome e
	 * l'azienda al corrispettivo direttore
	 * 
	 * @param azienda
	 */
	public DirectorImpl(final String name, final Factory factory) {
		this.name 				= name;
		this.factory 			= factory;
		this.requestsToSatisfy	= new LinkedHashSet<>();
	}

	/*
	 * Consente al direttore di creare una richiesta per ricevere il materiale
	 * da lavorare
	 * 
	 * @param la quantit� specificata dall'utente
	 * @return la richiesta per ricevere il materiale 
	 */
	@Override
	public Request newRequest(int neededQuantity) {
		return new RequestImpl(this.factory,
							   this.factory.getLoadingWarehouse().getMaterial(),
							   neededQuantity);
	}
	//DA GESTIRE L'ECCEZIONE IN CASO DI QUANTITA' TROPPO GRANDE O NEGATIVA
	
	/*
	 * Crea la richiesta per svuotare il magazzino di prodotti lavorati
	 * 
	 * @return la richiesta per svuotare
	 */
	@Override
	public Request emptyWarehouse() {
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
	public void addRequestToSatisfy(Request requestToSatisfy){
		this.requestsToSatisfy.add(requestToSatisfy);
	}
	
	/*
	 * Consente al dirigente di rimuovere una richiesta dalla lista delle
	 * richieste soddisfabili poich� gi� soddisfatta da un altro direttore
	 * 
	 * @param richiestaSoddisfatta
	 */
	@Override
	public void removeRequestToSatisfy(Request requestToBeRemoved){
		this.requestsToSatisfy.remove(requestToBeRemoved);
	}
	
	/*
	 * Consente al direttore di soddisfare una richiesta
	 * precedentemente inviata da un altro direttore 
	 * 
	 * @param richiestaDaSoddisfare
	 */
	@Override
	public void satisfyRequest(Request requestFulfilled) {
		requestFulfilled.setSendingFactory(factory);
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
		return Objects.equals(this.factory, other.factory) ||
			   Objects.equals(this.name, other.name);
	}
}