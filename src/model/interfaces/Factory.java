package model.interfaces;

/**
 * Breve descrizione dell'azienda
 * 
 * @author Rinaldi Simone
 */

public interface Factory {

	/**
	 * 
	 * @return
	 */
	String getName();
	
	/**
	 * 
	 * @return
	 */
	Warehouse getLoadingWarehouse();
	
	/**
	 * 
	 * @return
	 */
	Warehouse getUnloadingWarehouse();
	
	/**
	 * 
	 * @return
	 */
	Material getMaterial();
}