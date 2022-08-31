package model.classes;

import controller.classes.ManagerImpl;
import exceptions.WrongSendingFactoryException;
import model.interfaces.Factory;
import model.interfaces.Request;

/**
* Classe destinata all'implementazione dell'interfaccia della richiesta, oggetto fondamentale
* sul quale tutto il programma grava, questa consentir� ai vari direttori e al dirigente di poter 
* richiedere e/o spedire del materiale.
* 
* @author Grasso Emanuele
*/

public class RequestImpl implements Request {

	/*
	 * Come indicato dalla documentazione, ogni richiesta conterr� le informazioni relative
	 * alle aziende mittenti e destinatarie, al tipo e alla quantit� di materiale richiesto
	 * e all'id univoco di ogni richiesta che verr� sfruttato per controllare l'uguaglianza
	 * tra due richieste
	 */
	private Factory sendingFactory;
	private Factory receiverFactory;
	private final String sentMaterial;
	private final int sentQuantity;
	private final int requestId;
	
	private static int newRequestId = 0;

	
	/**
	 * Il costruttore  prende in input tutti i dati al fine di poter
	 * creare una richiesta vera e propria.
	 * Inotre associa l'id univoco della richiesta che viene gestito
	 * ed aggiornato ad ogni nuova richiesta
	 * 
	 * @param receiverFactory : azienda mittente
	 * @param sentMaterial : nome del materiale richiesto
	 * @param sentQuantity : quantità richiesta
	 */
	public RequestImpl(final Factory receiverFactory, final String sentMaterial, final int sentQuantity) {
		this(null, receiverFactory, sentMaterial, sentQuantity);
	}
	
	public RequestImpl(final Factory sendingFactory, final Factory receiverFactory, final String sentMaterial, final int sentQuantity) {
		this.sendingFactory		= sendingFactory;
		this.receiverFactory 	= receiverFactory;
		this.sentMaterial 		= sentMaterial;
		this.sentQuantity 		= sentQuantity;
		this.requestId 			= RequestImpl.newRequestId++;
	}

	/*
	 * Consente di avere il riferimento all'azienda che spedisce il materiale
	 * 
	 * @return azienda mittente
	 */
	@Override
	public Factory getSendingFactory() {
		return this.sendingFactory;
	}

	/*
	 * Consente di avere il riferimento all'azienda che riceve il materiale
	 * 
	 * @return azienda destinatario
	 */
	@Override
	public Factory getReceiverFactory() {
		return this.receiverFactory;
	}

	/*
	 * Consente di avere il riferimento al tipo di materiale spedito
	 * 
	 * @return il tipo di materiale
	 */
	@Override
	public String getSentMaterial() {
		return this.sentMaterial;
	}

	/*
	 * Consente di avere il riferimento alla quantit� di materiale spedito 
	 * 
	 * @return quantit� di materiale
	 */
	@Override
	public int getSentQuantity() {
		return this.sentQuantity;
	}

	/*
	 * Consente di avere il riferimento all'id univoco della richiesta
	 * 
	 * @return id univoco della richiesta
	 */
	@Override
	public int getRequestId() {
		return this.requestId;
	}

	/*
	 * Consente di impostare il parametro dell'azienda mittente, solo dopo che questa
	 * avr� accettato la richiesta
	 * 
	 * @param aziendaMittente
	 * @throws WrongSendingFactoryException
	 */
	@Override
	public void setSendingFactory(Factory sendingFactory) throws WrongSendingFactoryException {
		if(!sendingFactory.getMaterial().getProcessedMaterial().equals(this.sentMaterial) ||
		   !ManagerImpl.getManager().factoryIsPresent(sendingFactory)) {
			throw new WrongSendingFactoryException();
		}
			
		this.sendingFactory = sendingFactory;
	}
	
	/*
	 * Consente di impostare il parametro dell'azienda ricevente nel negozio, così da
	 * poter indirizzare la richiesta allo stesso
	 */
	@Override
	public void setReceiverFactoryToStore() {
		this.receiverFactory = StoreImpl.getStoreInstance();
		this.sendingFactory = null;
	}
	
	
	@Override
	public int hashCode() {
		return this.requestId;
	}
	
	/*
	 * Come precedentemente specificato, l'uguaglianza tra due richieste sareffettiva
	 * quando il loro codice univoco sar� uguale
	 * 
	 * @param obj
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestImpl other = (RequestImpl) obj;
		return this.requestId == other.requestId;
	}
}