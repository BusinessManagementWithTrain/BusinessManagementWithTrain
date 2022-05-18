package model.interfaces;

import exceptions.EmptyWarehouseException;
import exceptions.FullWarehouseException;
import exceptions.StaffIsAlreadyNotWorkingException;
import exceptions.StaffIsAlreadyWorkingException;

/**
 * Il personale permetterà di trasformare il materiale da grezzo a lavorato secondo le esigenze dell'utente
 * 
 * @author Scaramuzzino Elia
 */

public interface Staff {

	/**
	 * Consente di iniziare a lavorare il materiale dal magazzino di carico
	 */
	void startWorking() throws EmptyWarehouseException, StaffIsAlreadyWorkingException;
	
	/**
	 * Finisce di lavorare il materiale e lo mette nel magazzino di scarico
	 */
	void stopWorking() throws FullWarehouseException, StaffIsAlreadyNotWorkingException;
	
	/**
	 * Consente di avere riferimento al numero di operai all'interno dell'azienda
	 *
	 * @return il numero degli operai
	 */
	int getNumber();
}
