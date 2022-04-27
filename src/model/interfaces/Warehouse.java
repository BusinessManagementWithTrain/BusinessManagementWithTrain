package model.interfaces;

/**
 * Il magazzino consentira di tener traccia dei prodotti da lavorare e/o lavorati
 * 
 * @author Scaramuzzino Elia
 */

public interface Warehouse {

	/**
	 * Incrementa il numero di materiale nel magazzino (+quantity)
	 * @param quantity
	 */
	void addMaterial(int quantity);
	
	/**
	 * Decrementa il numero di materliale nel magazzino (-quantity)
	 * @param quantity
	 */
	void removeMaterial(int quantity);
	
	/**
	 * Ritorna la capacità totale del magazzino
	 * @return
	 */
	int getTotalCapacity();
	
	/**
	 * Ritorna la capacità corrente del magazzino
	 * @return
	 */
	int getCurrentCapacity();
}
