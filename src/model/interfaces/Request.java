package model.interfaces;

import exceptions.WrongSendingFactoryException;

/**
 * Interfaccia della richiesta, oggetto fondamentale sul quale tutto il programma grava,
 * questa consente ai vari direttori e al dirigente di poter richiedere e/o spedire del materiale.
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
	 * @throws WrongSendingFactoryException 
	 */
	void setSendingFactory(Factory sendingFactory) throws WrongSendingFactoryException;

	/**
	 * Consente di impostare il parametro dell'azienda ricevente nel negozio, così da
	 * poter indirizzare la richiesta allo stesso
	 */
	void setReceiverFactoryToStore();

	
}
