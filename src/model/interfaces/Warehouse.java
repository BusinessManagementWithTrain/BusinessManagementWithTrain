package model.interfaces;

/**
 * Il magazzino consentira di tener traccia dei prodotti da lavorare e/o lavorati
 * 
 * @author Scaramuzzino Elia
 */

public interface Warehouse {

	/**
	 * Incrementa il numero di materiale all'interno del magazzino
	 * 
	 * @param quantità da inserire nel magazzino
	 */
	void addMaterial(int quantity);
	
	/**
	 * Decrementa il numero di materiale all'interno del magazzino
	 * 
	 * @param la quantità da rimuovere dal magazzino
	 */
	void removeMaterial(int quantity);
	
	/**
	 * Consente di avere il riferimento alla capienza totale del magazzino
	 * 
	 * @return la capienza totale del magazzino
	 */
	int getTotalCapacity();
	
	/**
	 * Consente di avere il riferimento alla capienza corrente del magazzino
	 * 
	 * @return la capienza corrente del magazzino
	 */
	int getCurrentCapacity();
}
