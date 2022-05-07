package model.classes;

import model.interfaces.Warehouse;

public class WarehouseImpl implements Warehouse {

	private final int totalCapacity; // dimensione del magazzino
	private int currentCapacity; // capacità corrente del magazzino

	public WarehouseImpl(final int totalCapacity) {
		this.totalCapacity = totalCapacity;
		this.currentCapacity = 0;	
	}
	
	//Incrementa il numero di materiale nel magazzino (+quantity)
	@Override
	public void addMaterial(int quantity) {
		if(!(this.currentCapacity + quantity <= this.totalCapacity)){
			//genero eccezione
		}
		this.currentCapacity += quantity;

	}

	//Decrementa il numero di materliale nel magazzino (-quantity)
	@Override
	public void removeMaterial(int quantity) {
		if(!(this.currentCapacity - quantity >= 0)){
			//genero l'eccezione
		}
		this.currentCapacity -= quantity;

	}

	//Ritorna la capacità totale del magazzino
	@Override
	public int getTotalCapacity() {
		
		return this.totalCapacity;
	}
	
	//POTREMMO RIMUOVERE QUESTO METODO
	//Ritorna la capacità corrente del magazzino
	@Override
	public int getCurrentCapacity() {
		
		return this.currentCapacity;

	}

}
