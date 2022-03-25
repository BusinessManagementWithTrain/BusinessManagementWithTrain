package test;

import static org.junit.Assert.*;
import java.util.*;
import java.util.concurrent.ForkJoinPool.ManagedBlocker;

import controller.classes.ManagerImpl;
import controller.interfaces.Manager;
import model.classes.AgencyImpl;
import model.classes.DirectorImpl;
import model.classes.MaterialImpl;
import model.classes.RequestImpl;
import model.classes.StaffImpl;
import model.classes.StoreImpl;
import model.classes.TrainImpl;
import model.classes.WarehouseImpl;
import model.interfaces.Agency;
import model.interfaces.Director;
import model.interfaces.Material;
import model.interfaces.Request;
import model.interfaces.Staff;
import model.interfaces.Store;
import model.interfaces.Train;
import model.interfaces.Warehouse;

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
		manager.addAgency(new AgencyImpl());
		manager.createRequest(null);
		manager.getAgency(null);
		manager.getRequest(0);
		manager.getTrain();
		manager.removeAgency(null);
		manager.satisfiesRequest(null);
		manager.viewAgencyInfo(null);
	}

	@org.junit.Test
	public void agencyTest() {
		Agency firstAgency = new AgencyImpl();
		Agency secondAgency = new AgencyImpl();
		Agency thirdAgency = new AgencyImpl();
		
		
		//Elenco dei metodi che saranno da testare
		firstAgency.getDirector();
		firstAgency.getLoadingWarehouse();
		firstAgency.getMaterial();
		firstAgency.getName();
		firstAgency.getUnloadingWarehouse();
	
		secondAgency.getDirector();
		secondAgency.getLoadingWarehouse();
		secondAgency.getMaterial();
		secondAgency.getName();
		secondAgency.getUnloadingWarehouse();
		
		thirdAgency.getDirector();
		thirdAgency.getLoadingWarehouse();
		thirdAgency.getMaterial();
		thirdAgency.getName();
		thirdAgency.getUnloadingWarehouse();
	}
	
	@org.junit.Test
	public void directorTest() {
		Director aldoDirector = new DirectorImpl();
		Director pietroDirector = new DirectorImpl();
		Director giovanniDirector = new DirectorImpl();
		
		//Elenco dei metodi che saranno da testare
		aldoDirector.addRequestToSatisfy(null);
		aldoDirector.emptyWarehouse();
		aldoDirector.fullfilsRequest(null);
		aldoDirector.getAgency();
		aldoDirector.getRequestById(0);
		aldoDirector.removeRequestToSatisfy(null);
		aldoDirector.showsAgencyInfo();
	
		pietroDirector.addRequestToSatisfy(null);
		pietroDirector.emptyWarehouse();
		pietroDirector.fullfilsRequest(null);
		pietroDirector.getAgency();
		pietroDirector.getRequestById(0);
		pietroDirector.removeRequestToSatisfy(null);
		pietroDirector.showsAgencyInfo();
		
		giovanniDirector.addRequestToSatisfy(null);
		giovanniDirector.emptyWarehouse();
		giovanniDirector.fullfilsRequest(null);
		giovanniDirector.getAgency();
		giovanniDirector.getRequestById(0);
		giovanniDirector.removeRequestToSatisfy(null);
		giovanniDirector.showsAgencyInfo();
	}
	
	@org.junit.Test
	public void materialTest() {
		Material stone = new MaterialImpl();
		Material sand = new MaterialImpl();
		Material glass = new MaterialImpl();

		//Elenco dei metodi che saranno da testare
		stone.getName();
		
		sand.getName();
		
		glass.getName();
	}
	
	@org.junit.Test
	public void requestTest() {
		Request firstRequest = new RequestImpl();
		Request secondRequest = new RequestImpl();
		Request thirdRequest = new RequestImpl();
		
		//Elenco dei metodi che saranno da testare
		firstRequest.getMaterialSended();
		firstRequest.getQuantitySended();
		firstRequest.getReceiverAgency();
		firstRequest.getRequestId();
		firstRequest.getSendingAgency();
		firstRequest.setSendingAgency(null);
	
		secondRequest.getMaterialSended();
		secondRequest.getQuantitySended();
		secondRequest.getReceiverAgency();
		secondRequest.getRequestId();
		secondRequest.getSendingAgency();
		secondRequest.setSendingAgency(null);
		
		thirdRequest.getMaterialSended();
		thirdRequest.getQuantitySended();
		thirdRequest.getReceiverAgency();
		thirdRequest.getRequestId();
		thirdRequest.getSendingAgency();
		thirdRequest.setSendingAgency(null);
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
		train.showsCargoInfo();
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
