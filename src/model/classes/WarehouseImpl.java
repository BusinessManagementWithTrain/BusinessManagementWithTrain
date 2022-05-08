package model.classes;

import model.interfaces.Warehouse;

/**
 * Il magazzino consentira di tener traccia dei prodotti da lavorare e/o lavorati
 * 
 * @author Scaramuzzino Elia
 */

public class WarehouseImpl implements Warehouse {

	/*
	 * Come specificato nella documentazione, ogni magazzino conterrà, rispettivamente,
	 * la capienza totale dello stesso e la capienza corrente dello stesso
	 */
	private final int totalCapacity;
	private int currentCapacity;

	/**
	 * Il costruttore servirà unicamente ad associare la capienza totale al magazzino in questione
	 * 
	 * @param la capienza totale del magazzino
	 */
	public WarehouseImpl(final int totalCapacity) {
		this.totalCapacity = totalCapacity;
		this.currentCapacity = 0;	
	}
	
	/*
	 * Incrementa il numero di materiale all'interno del magazzino
	 * 
	 * @param quantità da inserire nel magazzino
	 */
	@Override
	public void addMaterial(int quantity) {
		if(!(this.currentCapacity + quantity <= this.totalCapacity)){
			//genero eccezione
		}
		this.currentCapacity += quantity;
	}

	/*
	 * Decrementa il numero di materiale all'interno del magazzino
	 * 
	 * @param la quantità da rimuovere dal magazzino
	 */
	@Override
	public void removeMaterial(int quantity) {
		if(!(this.currentCapacity - quantity >= 0)){
			//genero l'eccezione
		}
		this.currentCapacity -= quantity;
	}

	/*
	 * Consente di avere il riferimento alla capienza totale del magazzino
	 * 
	 * @return la capienza totale del magazzino
	 */
	@Override
	public int getTotalCapacity() {
		
		return this.totalCapacity;
	}
	
	/*
	 * Consente di avere il riferimento alla capienza corrente del magazzino
	 * 
	 * @return la capienza corrente del magazzino
	 */
	@Override
	public int getCurrentCapacity() {
		
		return this.currentCapacity;

	}
}
