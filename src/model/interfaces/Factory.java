package model.interfaces;

/**
 * Interfaccia dell'azienda, ovvero l'oggetto dotato di due magazzini che interagisce con il treno 
 * per il carico e lo scarico del materiale, il quale verrà lavorato
 * dai membri dello staff interni a quest'oggetto.   
 * @author Rinaldi Simone
 */

public interface Factory {

	/**
	 * Metodo che consente di avere il riferimento al nome dell'azienda
	 *
	 * @return il nome dell'azienda
	 */
	String getName();
	
	/**
	 * Metodo che consente di avere il riferimento al magazzino di carico
	 *
	 * @return il magazzino di carico
	 */
	Warehouse getLoadingWarehouse();
	
	/**
	 * Metodo che consente di avere il riferimento al magazzino di scarico
	 * 
	 * @return magazzino di scarico
	 */
	Warehouse getUnloadingWarehouse();
	
	/**
	 * Metodo che consente di avere il riferimento al materiale prodotto
	 * 
	 * @return il materiale prodotto dall'azienda
	 */
	Material getMaterial();

	/**
	 * Metodo che consente di avere il riferimento allo staff dell'azienda
	 * 
	 * @return lo staff dell'azienda
	 */
	Staff getStuffMembers();
}