package model.classes;

import exceptions.EmptyWarehouseException;
import exceptions.FullWarehouseException;
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
	private final String material;
	private final int totalCapacity;
	private int currentCapacity;

	/**
	 * Il costruttore servirà unicamente ad associare il materiale contenuto nel magazzino e
	 * la capienza totale al magazzino in questione
	 * 
	 * @param il nome del materiale
	 * @param la capienza totale del magazzino
	 */
	public WarehouseImpl(final String material, final int totalCapacity) {
		this.material = material;
		this.totalCapacity = totalCapacity;
		this.currentCapacity = 0;
		
	}
	
	/*
	 * Incrementa il numero di materiale all'interno del magazzino
	 * 
	 * @param quantità da inserire nel magazzino
	 */
	@Override
	public void addMaterial(int quantity) throws FullWarehouseException {
		if(!(this.currentCapacity + quantity <= this.totalCapacity)){
			
			throw new FullWarehouseException("The warehouse is full!");
		}
		this.currentCapacity += quantity;
	}

	/*
	 * Decrementa il numero di materiale all'interno del magazzino
	 * 
	 * @param la quantità da rimuovere dal magazzino
	 */
	@Override
	public void removeMaterial(int quantity) throws EmptyWarehouseException {
		if(!(this.currentCapacity - quantity >= 0)){
			
			throw new EmptyWarehouseException("The warehouse is empty!");
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
	
	/* 
	 * Consente di avere il riferimento al materiale contenuto nel magazzino
	 * 
	 * @return il materiale contenuto nel magazzino
	 */
	@Override
	public String getMaterial() {
		
		return this.material;
		
	}
}
