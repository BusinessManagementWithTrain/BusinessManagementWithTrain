package model.interfaces;

/**
 * Breve descrizione del treno
 * 
 * @author Rinaldi Simone
 */

public interface Train {

	/**
	 * 
	 */
	void cargoManagment();
	
	/**
	 * 
	 * @param newRequest
	 */
	void addRequest(Request newRequest);
	
	/**
	 * 
	 */
	void nextDestination();
	
	/**
	 * 
	 */
	void showsCargoInfo();
	
	/**
	 * 
	 * @return
	 */
	int getCargoTotalQuantity();
	
	/**
	 * 
	 * @param material
	 * @return
	 */
	int getCargoQuantityByMaterial(Material material);
	
	/**
	 * 
	 * @return
	 */
	Agency getCurrentDestination();
}
