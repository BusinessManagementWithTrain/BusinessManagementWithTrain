package model.interfaces;

import java.util.Map;

import exceptions.EmptyDestinationsSetException;
import exceptions.EmptyWarehouseException;
import exceptions.FullTrainException;
import exceptions.FullWarehouseException;

/**
 * Interfaccia del treno, ovvero l'oggetto che gestirà
 * il carico e lo scarico merci tra i magazzini delle aziende
 * 
 * @author Rinaldi Simone
 */

public interface Train {
	
	/**
	 * Metodo che invia il treno alla prossima tappa utile
	 * @throws EmptyDestinationsSetException 
	 * @throws EmptyWarehouseException 
	 */
	void nextDestination() throws FullWarehouseException,FullTrainException, EmptyDestinationsSetException, EmptyWarehouseException;
	
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
	Map<String, Integer> getStuffMap();

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

	/**
	 * Metodo che consente di avere il riferimento della quantità da scaricare dal treno
	 *
	 * @return la quantità da scaricare dal treno
	 */
	int getQuantitytoUnLoad();

	/**
	 * Metodo che consente di avere il riferimento alla capienza massima del treno 
	 *
	 * @return la quantità da caricare nel treno
	 */
	int getQuantitytoLoad();

	/**
	 * Metodo che consente di avere il riferimento alla capienza corrente del treno 
	 *
	 * @return la capienza corrente del treno
	 */
	int getCurrentCapacity();
}