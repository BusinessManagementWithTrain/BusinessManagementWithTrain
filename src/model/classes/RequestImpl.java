package model.classes;

import java.util.Objects;

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
	 * Come indicato dalla documentazione, ogni richiesta conterrà le informazioni relative
	 * alle aziende mittenti e destinatarie, al tipo e alla quantità di materiale richiesto
	 * e all'id univoco di ogni richiesta che verrà sfruttato per controllare l'uguaglianza
	 * tra due richieste
	 */
	private Factory sendingFactory;
	private final Factory receiverFactory;
	private final String sentMaterial;
	private final int sentQuantity;
	private final int requestId;

	private static int newRequestId = 0;

	
	/**
	 * Il costruttore servir� per prendere in input tutti i dati al fine di poter
	 * creare una richiesta vera e propria.
	 * Inotre servir� ad associare l'id univoco della richiesta che verr� gestito
	 * ed aggiornato ad ogni nuova richiesta
	 * 
	 * @param azienda mittente
	 * @param azienda destinatario
	 * @param materiale richiesto
	 * @param quantit� richiesta
	 */
	public RequestImpl(final Factory receiverFactory, final String sentMaterial, final int sentQuantity) {
		this(null, receiverFactory, sentMaterial, sentQuantity);
	}
	
	public RequestImpl(final Factory sendingFactory, final Factory receiverFactory, final String sentMaterial, final int sentQuantity) {
		this.sendingFactory		= sendingFactory;
		this.receiverFactory 	= receiverFactory;
		this.sentMaterial 		= sentMaterial;
		this.sentQuantity 		= sentQuantity;
		this.requestId = RequestImpl.newRequestId++;
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
	 * Consente di impostare il parametro dell'azienda mittente, solo dopo che questa
	 * avr� accettato la richiesta
	 * 
	 * @param aziendaMittente
	 */
	@Override
	public void setSendingFactory(Factory sendingFactory) {
		this.sendingFactory = sendingFactory;
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
	 * Come precedentemente specificato, l'uguaglianza tra due richieste sarà effettiva
	 * quando le loro aziende mittenti saranno uguali e lo saranno anche le aziende destinatarie
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
		return Objects.equals(this.requestId, other.requestId) ||
			   Objects.equals(this.receiverFactory, other.receiverFactory)
			   && Objects.equals(this.sendingFactory, StoreImpl.getStoreInstance());
	}
	
	
}