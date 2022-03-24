package model.interfaces;

/**
 * Breve descrizione del magazzino
 * 
 * @author Scaramuzzino Elia
 */

public interface Warehouse {

	/**
	 * 
	 * @param quantity
	 */
	void addMaterial(int quantity);
	
	/**
	 * 
	 * @param quantity
	 */
	void removeMaterial(int quantity);
	
	/**
	 * 
	 * @return
	 */
	int getTotalCapacity();
	
	/**
	 * 
	 * @return
	 */
	int getCurrentCapacity();
}
