package model.interfaces;

/**
 * Breve descrizione della richiesta.
 * 
 * @author Grasso Emanuele
 */

public interface Request {
	
	/**
	 * Consente di avere il riferimento all'azienda che spedisce il materiale
	 * 
	 * @return azienda mittente
	 */
	Agency getSendingAgency();
	
	/**
	 * Consente di avere il riferimento all'azienda che riceve il materiale
	 * 
	 * @return azienda destinatario
	 */
	Agency getReceiverAgency();
	
	/**
	 * Consente di avere il riferimento al tipo di materiale spedito
	 * 
	 * @return il tipo di materiale spedito/da spedire
	 */
	Material getMaterialSended();
	
	/**
	 * Consente di avere il riferimento alla quantit� di materiale spedito 
	 * 
	 * @return quantit� di materiale spedito/da spedire
	 */
	int getQuantitySended();
	
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
	void setSendingAgency(Agency sendingAgency);
}
