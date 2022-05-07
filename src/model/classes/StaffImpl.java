package model.classes;

import model.interfaces.Staff;
import model.interfaces.Warehouse;

public class StaffImpl implements Staff {
	private final int staffNumber;				// Numero di operai
	private final Warehouse receiverWarehouse;	// Magazzino di carico
	private final Warehouse sendingWarehouse;	// Magazzino di scarico
	private boolean isStaffWorking;				// Variabile per controllare se sto gia lavorando
	
	/**
	 * Il costruttore assegna valori presi in input alle variabili interne, il booleano "isStaffWorking" è inizializzato
	 * a false per garantire un corretto funzionamento dei metodi sottostanti
	 */
	public StaffImpl(final int staffNumber, final Warehouse receiverWarehouse, final Warehouse sendingWarehouse) {
		this.staffNumber = staffNumber;
		this.receiverWarehouse = receiverWarehouse;
		this.sendingWarehouse = sendingWarehouse;
		this.isStaffWorking = false;
	}

	//Consente di iniziare a lavorare il materiale dal magazzino di carico
	@Override
	public void startWorking() {
		if(isStaffWorking){
			//genero eccezione
		}

		this.receiverWarehouse.removeMaterial(staffNumber);
		this.isStaffWorking = true;
	}

	//Finisce di lavorare il materiale e lo mette nel magazzino di scarico
	@Override
	public void stopWorking() {
		if(!isStaffWorking){
			//genero eccezione
		}
		
		this.sendingWarehouse.addMaterial(staffNumber);
		this.isStaffWorking = false;
	}

	//Consente di avere riferimento al numero di operai all'interno dell'azienda
	@Override
	public int getNumber() {
		return this.staffNumber;
	}
}
