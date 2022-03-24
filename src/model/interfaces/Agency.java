package model.interfaces;

/**
 * Breve descrizione dell'azienda
 * 
 * @author Rinaldi Simone
 */

public interface Agency {

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
	Director getDirector();
	
	/**
	 * 
	 * @return
	 */
	Material getMaterial();
}