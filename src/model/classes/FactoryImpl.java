package model.classes;

import java.util.Objects;

import model.interfaces.Factory;
import model.interfaces.Material;
import model.interfaces.Staff;
import model.interfaces.Warehouse;

public class FactoryImpl implements Factory {
	
	//staff azienda
	private Staff staff;
	
	//nome azienda
	private String name;
	
	//materiale dell'azienda
	private Material material;
	
	//magazzino di carico
	private Warehouse loadingWarehouse;
	
	//magazzino di scarico
	private Warehouse unloadingWarehouse;
	
	//costruttore azienda
	public FactoryImpl(String name,Material material,int stuffSize) {
		this.name=name;
		this.material=material;
		this.loadingWarehouse= new WarehouseImpl();
		this.unloadingWarehouse= new WarehouseImpl();
		//da vedere con elia
		//this.staff=new StaffImpl(stuffSize,loadingWarehouse,unloadingWarehouse);
	}

	/*
	 * metodo per ottenre il nome dell'azienda
	 * @return nome dell'azienda
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	/*
	 * metodo per ottenere l'oggetto delle informazioni di carico
	 * @return magazzino di carico
	 */
	@Override
	public Warehouse getLoadingWarehouse() {
		// TODO Auto-generated method stub
		return this.loadingWarehouse;
	}


	/*
	 * metodo per ottenere l'oggetto del magazzino di scarico
	 * @return magazzinod di scarico
	 */
	@Override
	public Warehouse getUnloadingWarehouse() {
		// TODO Auto-generated method stub
		return this.unloadingWarehouse;
	}

	/**
	 * metodo per ottenere il materiale dell'azienda
	 * @return il materiale dell'azienda
	 */
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return this.material;
	}

	@Override
	public Staff getStuffMembers() {
		return staff;
	}
	
	/**
	 * metodo per controllare che non vengano generate aziende con lo stesso nome
	 * @return se l'operazione di confronto ha generato conflitti con il nome dell'azienda o meno
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
