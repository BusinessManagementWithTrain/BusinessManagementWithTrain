package model.classes;

import controller.classes.ManagerImpl;
import exceptions.EmptyFieldException;
import exceptions.MaximumCharactersException;
import exceptions.WrongStaffValueException;
import exceptions.WrongWarehouseCapacityException;
import model.interfaces.Factory;
import model.interfaces.Material;
import model.interfaces.Staff;
import model.interfaces.Warehouse;

/**
 * 
 * Classe destinata all'implementazione dell'interfaccia della generica azienda
 * 
 * @author Rinaldi Simone
 */

public class FactoryImpl implements Factory {
	
	private final static int MIN_NAME_CHAR = 1;
	private final static int MAX_NAME_CHAR = 12;
	
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
	 * @throws EmptyFieldException
	 * @throws MaximumCharactersException
	 * @throws WrongStaffValueException
	 * @throws WrongWarehouseCapacityException
	 */
	public FactoryImpl(final String name, final Material material, final int staffSize, final int loadingSize, final int unloadingSize) throws EmptyFieldException, MaximumCharactersException, WrongStaffValueException, WrongWarehouseCapacityException {
		if(name.length() < MIN_NAME_CHAR) {
			throw new EmptyFieldException();
		} else if(name.length() > MAX_NAME_CHAR) {
			throw new MaximumCharactersException();
		}
		
		else {
			this.name 					= name;
			this.material 				= material;
			this.loadingWarehouse 		= new WarehouseImpl(material.getRawMaterial(), loadingSize);
			this.unloadingWarehouse		= new WarehouseImpl(material.getProcessedMaterial(), unloadingSize);
			this.staff 					= new StaffImpl(staffSize, this.loadingWarehouse, this.unloadingWarehouse);
		}
		
		
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
	
	@Override
	public int hashCode() {
		int value = 0;
		
		for (int i = 0; i < this.name.length(); i++) {
			value += this.name.toCharArray()[i] + i;
		}
		
		return value * this.name.length();
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
		return this.name.equals(other.name);
	}
}
