package test;
 
import static org.junit.Assert.*;
import java.util.*;

import controller.classes.ManagerImpl;
import controller.interfaces.Manager;
import model.classes.*;
import model.interfaces.*;

/**
 * Breve descrizione dei test
 * 
 * @author Grasso Emanuele
 */

public class Test {
	
	@org.junit.Test
	public void managerTest() {
		Manager manager = new ManagerImpl();
		
		//Elenco dei metodi che saranno da testare
		manager.hireDirector(null);;
		manager.createRequest(null);
		manager.getDirectorByName(null);
		manager.getRequestById(0);
		manager.getTrain();
		manager.fireDirector(null);
		manager.satisfiesRequest(null);
		manager.showAgencyInfo(null);
	}

	@org.junit.Test
	public void agencyTest() {
		Factory firstAgency = new FactoryImpl();
		Factory secondAgency = new FactoryImpl();
		Factory thirdAgency = new FactoryImpl();
		
		
		//Elenco dei metodi che saranno da testare
		firstAgency.getLoadingWarehouse();
		firstAgency.getMaterial();
		firstAgency.getName();
		firstAgency.getUnloadingWarehouse();
	
		secondAgency.getLoadingWarehouse();
		secondAgency.getMaterial();
		secondAgency.getName();
		secondAgency.getUnloadingWarehouse();
		
		thirdAgency.getLoadingWarehouse();
		thirdAgency.getMaterial();
		thirdAgency.getName();
		thirdAgency.getUnloadingWarehouse();
	}
	
	@org.junit.Test
	public void directorTest() {
		Director aldoDirector = new DirectorImpl(null, null);
		Director pietroDirector = new DirectorImpl(null, null);
		Director giovanniDirector = new DirectorImpl(null, null);
		
		//Elenco dei metodi che saranno da testare
		aldoDirector.addRequestToSatisfy(null);
		aldoDirector.emptyWarehouse();
		aldoDirector.satisfyRequest(null);
		aldoDirector.getFactory();
		aldoDirector.getRequestsToSatisfy();
		aldoDirector.removeRequestToSatisfy(null);
		aldoDirector.showsFactoryInfo();
	
		pietroDirector.addRequestToSatisfy(null);
		pietroDirector.emptyWarehouse();
		pietroDirector.satisfyRequest(null);
		pietroDirector.getFactory();
		pietroDirector.getRequestsToSatisfy();
		pietroDirector.removeRequestToSatisfy(null);
		pietroDirector.showsFactoryInfo();
		
		giovanniDirector.addRequestToSatisfy(null);
		giovanniDirector.emptyWarehouse();
		giovanniDirector.satisfyRequest(null);
		giovanniDirector.getFactory();
		giovanniDirector.getRequestsToSatisfy();
		giovanniDirector.removeRequestToSatisfy(null);
		giovanniDirector.showsFactoryInfo();
	}
	
	@org.junit.Test
	public void materialTest() {
		Material stone = new MaterialImpl(null);
		Material sand = new MaterialImpl(null);
		Material glass = new MaterialImpl(null);

		//Elenco dei metodi che saranno da testare
		stone.getName();
		
		sand.getName();
		
		glass.getName();
	}
	
	@org.junit.Test
	public void requestTest() {
		Request firstRequest = new RequestImpl(null, null, 0);
		Request secondRequest = new RequestImpl(null, null, 0);
		Request thirdRequest = new RequestImpl(null, null, 0);
		
		//Elenco dei metodi che saranno da testare
		firstRequest.getSentMaterial();
		firstRequest.getSentQuantity();
		firstRequest.getReceiverFactory();
		firstRequest.getRequestId();
		firstRequest.getSendingFactory();
		firstRequest.setSendingFactory(null);
	
		secondRequest.getSentMaterial();
		secondRequest.getSentQuantity();
		secondRequest.getReceiverFactory();
		secondRequest.getRequestId();
		secondRequest.getSendingFactory();
		secondRequest.setSendingFactory(null);
		
		thirdRequest.getSentMaterial();
		thirdRequest.getSentQuantity();
		thirdRequest.getReceiverFactory();
		thirdRequest.getRequestId();
		thirdRequest.getSendingFactory();
		thirdRequest.setSendingFactory(null);
	}

	@org.junit.Test
	public void staffTest() {
		Staff firstStaff = new StaffImpl();
		Staff secondStaff = new StaffImpl();
		Staff thirdStaff = new StaffImpl();
		
		//Elenco dei metodi che saranno da testare
		firstStaff.getNumber();
		firstStaff.startWorking();
		firstStaff.stopWorking();
		
		secondStaff.getNumber();
		secondStaff.startWorking();
		secondStaff.stopWorking();
		
		thirdStaff.getNumber();
		thirdStaff.startWorking();
		thirdStaff.stopWorking();
	}
	
	@org.junit.Test
	public void storeTest() {
		Store store = new StoreImpl();
		
		
	}
	
	@org.junit.Test
	public void trainTest() {
		Train train = new TrainImpl();
		
		//Elenco dei metodi che saranno da testare
		train.addRequest(null);
		train.cargoManagment();
		train.getCargoQuantityByMaterial(null);
		train.getCargoTotalQuantity();
		train.getCurrentDestination();
		train.nextDestination();
		train.showCargoInfo();
	}
	
	@org.junit.Test
	public void warehouseTest() {
		Warehouse firstWarehouse = new WarehouseImpl();
		Warehouse secondWarehouse = new WarehouseImpl();
		Warehouse thirdWarehouse = new WarehouseImpl();
	
		//Elenco dei metodi che saranno da testare
		firstWarehouse.addMaterial(0);
		firstWarehouse.getCurrentCapacity();
		firstWarehouse.getTotalCapacity();
		firstWarehouse.removeMaterial(0);
		
		secondWarehouse.addMaterial(0);
		secondWarehouse.getCurrentCapacity();
		secondWarehouse.getTotalCapacity();
		secondWarehouse.removeMaterial(0);
		
		thirdWarehouse.addMaterial(0);
		thirdWarehouse.getCurrentCapacity();
		thirdWarehouse.getTotalCapacity();
		thirdWarehouse.removeMaterial(0);
	}
}
