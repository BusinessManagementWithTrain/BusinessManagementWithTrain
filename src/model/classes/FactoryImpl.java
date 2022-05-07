package model.classes;

import java.util.Objects;

import model.interfaces.Factory;
import model.interfaces.Material;
import model.interfaces.Staff;
import model.interfaces.Warehouse;

/**
 * Breve descrizione dell'azienda
 * 
 * @author Rinaldi Simone
 */

public class FactoryImpl implements Factory {
	
	/*
	 * Come specificato nella documentazione, ogni azienda avrà rispettivamente, il nome, il materiale
	 * prodotto, il magazzino di carico merci, il magazzino di scarico merci e il personale assunto
	 */
	private final String name;
	private final Material material;
	private final Warehouse loadingWarehouse;
	private final Warehouse unloadingWarehouse;
	private final Staff staff;
	
	/**
	 * Il costruttore servirà principalmente ad associare i vari valori di riferimento passati
	 * dal controller, ed inoltre creerà i magazzini e i dipendenti ad essi collegati
	 * 
	 * @param il nome dell'azienda
	 * @param il materiale prodotto dall'azienda
	 * @param il numero dei dipendenti
	 * @param la capienza del magazzino di carico
	 * @param la capienza del magazzino di scarico
	 */
	public FactoryImpl(final String name, final Material material, final int staffSize, final int loadingSize, final int unloadingSize) {
		this.name 					= name;
		this.material 				= material;
		this.loadingWarehouse 		= new WarehouseImpl(loadingSize);
		this.unloadingWarehouse		= new WarehouseImpl(unloadingSize);
		this.staff 					= new StaffImpl(staffSize, this.loadingWarehouse, this.unloadingWarehouse);
	}

	/*
	 * Metodo che consente di avere il riferimento al nome dell'azienda
	 *
	 * @return il nome dell'azienda
	 */
	@Override
	public String getName() {
		return this.name;
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
	 * Metodo che consente di avere il riferimento al magazzino di scarico
	 * 
	 * @return magazzino di scarico
	 */
	@Override
	public Warehouse getUnloadingWarehouse() {
		return this.unloadingWarehouse;
	}

	/*
	 * Metodo che consente di avere il riferimento al materiale prodotto
	 * 
	 * @return il materiale prodotto dall'azienda
	 */
	@Override
	public Material getMaterial() {
		return this.material;
	}

	/*
	 * Metodo che consente di avere il riferimento allo staff dell'azienda
	 * 
	 * @return lo staff dell'azienda
	 */
	@Override
	public Staff getStuffMembers() {
		return this.staff;
	}
	
	/*
	 * Come specificato nella documentazione, due aziende sono considerate uguali
	 * se hanno lo stesso nome
	 * 
	 * @param oggetto da controllare
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FactoryImpl other = (FactoryImpl) obj;
		return Objects.equals(name, other.name);
	}
}
