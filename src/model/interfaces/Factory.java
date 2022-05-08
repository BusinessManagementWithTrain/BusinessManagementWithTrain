package model.interfaces;

/**
 * Interfaccia dell'azienda, ovvero l'oggetto per il quale saranno usati i magazzini 
 * per interagire con il treno.
 * @author Rinaldi Simone
 */

public interface Factory {

	/**
	 * metodo per ottenere il nome dell'azienda
	 * @return nome dell'azienda
	 */
	String getName();
	
	/**
	 * metodo per ottenere il magazzino di carico
	 * @return magazzino di carico
	 */
	Warehouse getLoadingWarehouse();
	
	/**
	 * metodo per ottenere il magazzino di scarico
	 * @return magazzino di scarico
	 */
	Warehouse getUnloadingWarehouse();
	
	/**
	 * metodo per ottenere il materiale dell'azienda
	 * @return materiale dell'azienda
	 */
	Material getMaterial();

	/**
	 * metodo per ottenere il numero di dipendenti dell'azienda
	 * @return numero dipendenti dell'azienda
	 */
	Staff getStuffMembers();
}