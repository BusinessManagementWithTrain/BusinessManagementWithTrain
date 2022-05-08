package model.classes;

import model.interfaces.Material;
import model.interfaces.Staff;
import model.interfaces.Store;
import model.interfaces.Warehouse;

/**
 * Il negozio permetterà di scaricare materiale inutilizzato senza limiti
 * 
 * @author Scaramuzzino Elia
 */

public class StoreImpl implements Store {

	/*
	 * Come specificato nella documentazione, il negozio sarà una particolare azienda
	 * con l'unico dettaglio di fungere da enorme "discarica", infatti l'unico riferimento
	 * di cui avrà bisogno sarà quello relativo al magazzino di carico della merce che
	 * verrà appositamente trattato ed utilizzato ad hoc
	 */
	private Warehouse loadingWarehouse;	
	
	/**
	 * Il costruttore servirà unicamente per riscrivere il funzionamento del magazzino di carico
	 * dal quale verranno tolti tutti i controlli e non verrà utilizzato neanche un metodo, questo
	 * ci consentirà di utilizzare il negozio come un'azienda ma che all'effettivo non farà nulla.
	 */
	public StoreImpl() {
		this.loadingWarehouse = new Warehouse(){
			@Override
			public void addMaterial(int quantity) {}

			@Override
			public void removeMaterial(int quantity) {}
	
			@Override
			public int getTotalCapacity() {
				return 0;
			}	

			@Override
			public int getCurrentCapacity() {
				return 0;
			}	
		};	
	}
	
	/*
	 * Metodo che consente di avere il riferimento al nome dell'azienda
	 *
	 * @return il nome dell'azienda
	 */
	@Override
	public String getName() {
		return "Store";
	}

	/*
	 * Metodo che consente di avere il riferimento al magazzino di carico
	 *
	 * @return il magazzino di carico
	 */
	@Override
	public Warehouse getLoadingWarehouse() {
		return this.loadingWarehouse;
	}


	/*
	 * Metodo che consente di avere il riferimento al magazzino di scarico [INUTILIZZATO]
	 * 
	 * @return null
	 */
	@Override
	public Warehouse getUnloadingWarehouse() {
		return null;
	}

	/*
	 * Metodo che consente di avere il riferimento al materiale prodotto [INUTILIZZATO]
	 * 
	 * @return null
	 */
	@Override
	public Material getMaterial() {
		return null;
	}

	/*
	 * Metodo che consente di avere il riferimento allo staff dell'azienda [INUTILIZZATO]
	 * 
	 * @return null
	 */
	@Override
	public Staff getStuffMembers() {
		return null;
	}
}
