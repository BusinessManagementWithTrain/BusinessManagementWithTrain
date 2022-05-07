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
	 * Sarà privato per consentire l'utilizzo del SingleTon Pattern
	 */
	private StoreImpl() {
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
	 * Classe dedicata all'utilizzo del SingleTon Pattern, questa classe contiene solo una costante,
	 * la scelta di questo stile risiede nell'ideologia di puntare ad una soluzione comoda non solo
	 * nell'implementazione, ma anche a livello hardware;
	 * il riferimento alla classe e l'allocazione della stessa all'interno dello stack avverrà solo dopo
	 * la prima invocazione del metodo getStore()
	 */
	private static class SingletonHelper {
		private static final StoreImpl STORE = new StoreImpl();
	}
	
	/*
	 * Consente di avere il riferimento a sempre la stessa istanza della classe Store
	 * 
	 * @return l'istanza della classe StoreImpl
	 */
	public static StoreImpl getStore() {
		return SingletonHelper.STORE;
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
