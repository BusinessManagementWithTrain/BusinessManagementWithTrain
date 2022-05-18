package model.classes;

import exceptions.EmptyWarehouseException;
import exceptions.FullWarehouseException;
import exceptions.StaffIsAlreadyNotWorkingException;
import exceptions.StaffIsAlreadyWorkingException;
import model.interfaces.Staff;
import model.interfaces.Warehouse;

/**
 * Breve descrizione dei dipendenti
 * 
 * @author Scaramuzzino Elia
 */

public class StaffImpl implements Staff {
	/*
	 * Come specificato nella documentazione, ogni gruppo di operatori avrà il riferimento
	 * al numero totale dei dipendenti, al magazzino di carico in cui lavorare, al magazzino di
	 * scarico dove appoggiare il materiale lavorato ed un booleano che controllerà se i dipendenti
	 * stiano effettivamente lavorando
	 */
	private final int staffNumber;
	private final Warehouse receiverWarehouse;
	private final Warehouse sendingWarehouse;
	private boolean isStaffWorking;
	
	/**
	 * Il costruttore servirà principalmente ad assegnare i valori presi in input alle variabili interne e
	 * ad inizializzare la variabile booleana a false in quanto, all'atto della creazione, i 
	 * dipendenti non stiano ancora lavorando
	 */
	public StaffImpl(final int staffNumber, final Warehouse receiverWarehouse, final Warehouse sendingWarehouse) {
		this.staffNumber 			= staffNumber;
		this.receiverWarehouse		= receiverWarehouse;
		this.sendingWarehouse 		= sendingWarehouse;
		this.isStaffWorking 		= false;
	}

	/*
	 * Consente di iniziare a lavorare il materiale dal magazzino di carico
	 */
	@Override
	public void startWorking() throws EmptyWarehouseException, StaffIsAlreadyWorkingException  {
		if(this.isStaffWorking){
			throw new StaffIsAlreadyWorkingException("Staff is already working!");
		}

		this.receiverWarehouse.removeMaterial(this.staffNumber);
		this.isStaffWorking = true;
	}

	/*
	 * Consente di finire di lavorare il materiale e di posizionarlo nel magazzino di scarico
	 */
	@Override
	public void stopWorking() throws FullWarehouseException, StaffIsAlreadyNotWorkingException {
		if(!this.isStaffWorking){
			throw new StaffIsAlreadyNotWorkingException("Staff is not already working!");

		}
		
		this.sendingWarehouse.addMaterial(this.staffNumber);
		this.isStaffWorking = false;
	}

	/*
	 * Consente di avere riferimento al numero degli operai all'interno dell'azienda
	 * 
	 * @return il numero degli operai
	 */
	@Override
	public int getNumber() {
		return this.staffNumber;
	}
}
