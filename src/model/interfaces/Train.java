package model.interfaces;

import java.util.Map;

/**
 * Interfaccia del treno, ovvero l'oggetto che gestirà
 * il carico e lo scarico merci tra i magazzini delle aziende
 * 
 * @author Rinaldi Simone
 */

public interface Train {
	
	/**
	 * Metodo che invia il treno alla prossima tappa utile
	 */
	void nextDestination();
	
	/**
	 * metodo per aggiungere richieste alla lista di richieste di carico
	 * @param newRequest
	 */
	void addRequest(Request newRequest);
	
	/**
	 * Metodo che consente di avere il riferimento alla mappa della merce sul treno
	 * 
	 * @return la mappa della merce
	 */
	Map<Material, Integer> getStuffMap();

	/**
	 * Metodo che consente di avere il riferimento alla tappa attuale del treno
	 * 
	 * @return la tappa attuale del treno
	 */
	Factory getCurrentDestination();

	/**
	 * Metodo che consente di avere il riferimento alla capienza massima del treno 
	 *
	 * @return la capienza massima del treno
	 */
	int getMaxCapacity();
}
