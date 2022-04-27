package model.interfaces;

/**
 * Il personale permetterà di trasformare il materiale da grezzo a lavorato secondo le esigenze dell'utente
 * 
 * @author Scaramuzzino Elia
 */

public interface Staff {

	/**
	 * Consente di iniziare a lavorare il materiale dal magazzino di carico
	 */
	void startWorking();
	
	/**
	 * Finisce di lavorare il materiale e lo mette nel magazzino di scarico
	 */
	void stopWorking();
	
	/**
	 * Consente di avere riferimento al numero di operai all'interno dell'azienda
	 * @return Numero di operai
	 */
	int getNumber();
}
