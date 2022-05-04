package model.classes;

import model.interfaces.Material;
import model.interfaces.Staff;
import model.interfaces.Store;
import model.interfaces.Warehouse;

public class StoreImpl implements Store {

	private Warehouse loadingWarehouse;	//ci permette di avere riferimento al magazzino di scarico che verrà appositamente riscritto ad hoc

	public StoreImpl() {
		this.loadingWarehouse = new Warehouse(){
			/*l'unica cosa che servirà è la possibilità di scaricare materiale senza limiti.
			 *i metodi sono stati lasciati vuoti in quanto non necessari.
			 */
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

	@Override
	public String getName() {
		return "Store";
	}

	@Override
	public Warehouse getLoadingWarehouse() {
		return this.loadingWarehouse;
	}

	@Override
	public Warehouse getUnloadingWarehouse() {
		return null;
	}

	@Override
	public Material getMaterial() {
		return null;
	}

	@Override
	public Staff getStuffMembers() {
		return null;
	}
}
