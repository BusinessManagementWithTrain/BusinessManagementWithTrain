package model.interfaces;

/**
 * Interfaccia della richiesta, oggetto fondamentale sul quale tutto il programma grava,
 * questa consentir� ai vari direttori e al dirigente di poter richiedere e/o spedire del materiale.
 * 
 * @author Grasso Emanuele
 */

public interface Request {
	
	/**
	 * Consente di avere il riferimento all'azienda che spedisce il materiale
	 * 
	 * @return azienda mittente
	 */
	Factory getSendingFactory();
	
	/**
	 * Consente di avere il riferimento all'azienda che riceve il materiale
	 * 
	 * @return azienda destinatario
	 */
	Factory getReceiverFactory();
	
	/**
	 * Consente di avere il riferimento al tipo di materiale spedito
	 * 
	 * @return il tipo di materiale spedito/da spedire
	 */
	String getSentMaterial();
	
	/**
	 * Consente di avere il riferimento alla quantit� di materiale spedito 
	 * 
	 * @return quantit� di materiale spedito/da spedire
	 */
	int getSentQuantity();
	
	/**
	 * Consente di avere il riferimento all'id univoco della richiesta
	 * 
	 * @return id univoco della richiesta
	 */
	int getRequestId();
	
	/**
	 * Consente di impostare il parametro dell'azienda mittente, solo dopo che questa
	 * avr� accettato la richiesta
	 * 
	 * @param azienda mittente
	 */
	void setSendingFactory(Factory sendingFactory);

	void setReceiverFactoryToStore();

	
}
