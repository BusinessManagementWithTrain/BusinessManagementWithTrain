package test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import controller.classes.ManagerImpl;
import controller.interfaces.Manager;
import exceptions.EqualMaterialException;
import exceptions.LowTrainCapacityException;
import model.classes.DirectorImpl;
import model.classes.FactoryImpl;
import model.classes.MaterialImpl;
import model.classes.RequestImpl;
import model.interfaces.Director;
import model.interfaces.Factory;
import model.interfaces.Material;
import model.interfaces.Request;
import model.interfaces.Warehouse;

/**
 * Breve descrizione dei test
 * 
 * @author Grasso Emanuele
 */

public class Test {
	/*
	@org.junit.Test
	public void startTest() {
		try {
			ManagerImpl.getManager(1000);
		} catch (LowTrainCapacityException e) {
			e.printStackTrace();
		}
	}

	
	@org.junit.Test
	public void directorTest() {
		
	}
	
	@org.junit.Test
	public void factoryTest() {
		
	}
	
	@org.junit.Test
	public void materialTest() {
		
	}
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
	}*/
	
	/*
	@org.junit.Test 
	public void CaricoTest() {
		try {
			//creo il manager col treno di capienza 1000kg
			ManagerImpl.getManager(1000);
			
			//creo un materiale
			MaterialImpl m1 = new MaterialImpl("p1", "p2");
			//creo la prima azienda
			FactoryImpl f1 = new FactoryImpl("A", m1, 20, 200, 200);
			//creo un direttore
			DirectorImpl d1 = new DirectorImpl("pippo", f1);
			//assumo il direttore
			ManagerImpl.getManager().hireDirector(d1);
			
			//uguale a sopra
			MaterialImpl m2 = new MaterialImpl("p2", "p3");
			FactoryImpl f2 = new FactoryImpl("B", m2, 20, 300, 300);
			DirectorImpl d2 = new DirectorImpl("pluto", f2);
			ManagerImpl.getManager().hireDirector(d2);
			
			
			//creo una richiesta
			Request r1 =	new RequestImpl(f1, m1.getRawMaterial(), 200);
			//Request r2 = 	new RequestImpl(f1, StoreImpl.getStoreInstance(), m1.getProcessedMaterial(), 80);
			//creo un seconda richiesta
			Request r3 = 	new RequestImpl(f2, m2.getRawMaterial(), 100);
			
			ManagerImpl.getManager().sendRequest(r1);
			ManagerImpl.getManager().satisfiesRequestManager(r1);
			
			ManagerImpl.getManager().showDirectorInfo("pippo").getFactory().getLoadingWarehouse().removeMaterial(80);
			ManagerImpl.getManager().showDirectorInfo("pippo").getFactory().getUnloadingWarehouse().addMaterial(80);
			ManagerImpl.getManager().emptyWarehouse("pippo");
			
			ManagerImpl.getManager().sendRequest(r3);
			ManagerImpl.getManager().satisfiesRequestDirector(r3, "pippo");
			
			
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		emptyWarehouseTest();
		
		
	}*/
	
	/*
	@org.junit.Test
	public void requestTest() {
		
	}
	
	@org.junit.Test
	public void staffTest() {
		
	}
	
	@org.junit.Test
	public void storeTest() {
		
	}
	
	@org.junit.Test
	public void trainTest() {
		
	}
	
	@org.junit.Test
	public void warehouseTest() {
		
	}
	
	@org.junit.Test
	public void exceptionTests() {
		
	}
	
	@org.junit.Test
	public void anotherAcceptedRequestExceptionTest() {
		
	}
	
	@org.junit.Test
	public void emptyDestinationSetExceptionTest() {
  
	public void emptyWarehouseTest() {
			try {
				ManagerImpl.getManager().nextDestination();
				ManagerImpl.getManager().nextDestination();
				ManagerImpl.getManager().nextDestination();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	}*/
	
	/*
	@org.junit.Test
	public void TestManu1() {
		try {
			
			//creazione manager
			ManagerImpl.getManager(1000);
			
			//creazione materiali
			MaterialImpl m1 = new MaterialImpl("a", "b");
			MaterialImpl m2 = new MaterialImpl("b","c");
			MaterialImpl m3 = new MaterialImpl("d", "b");
			
			//creazione aziende
			FactoryImpl f1 = new FactoryImpl("A", m1, 20, 300, 300);
			FactoryImpl f2 = new FactoryImpl("B", m2, 20, 200, 200);
			FactoryImpl f3 = new FactoryImpl("C", m3, 20, 300, 300);
			
			//creazione direttori
			DirectorImpl d1 = new DirectorImpl("pippo", f1);
			DirectorImpl d2 = new DirectorImpl("pluto", f2);
			DirectorImpl d3 = new DirectorImpl("paperino", f3);
			
			//assunzione direttori
			ManagerImpl.getManager().hireDirector(d1);
			ManagerImpl.getManager().hireDirector(d2);
			ManagerImpl.getManager().hireDirector(d3);
			
			//creazione richieste dell'azienda A e C
			RequestImpl r1 = new RequestImpl(f1, m1.getRawMaterial(), 300);
			RequestImpl r3 = new RequestImpl(f3, m3.getRawMaterial(), 300);
			
			//creazione richieste dell'azienda B 
			RequestImpl r2 = new RequestImpl(f2,m2.getRawMaterial(),100);
			RequestImpl r4 = new RequestImpl(f2,m2.getRawMaterial(),150);
			
			//soddisfo richiesta di A
			ManagerImpl.getManager().sendRequest(r1);
			ManagerImpl.getManager().satisfiesRequestManager(r1);
			
			//soddisfo richiesta di C
			ManagerImpl.getManager().sendRequest(r3);
			ManagerImpl.getManager().satisfiesRequestManager(r3);
			
			//invio le richieste di carico di B da 100 e 150 [Kg]
			ManagerImpl.getManager().sendRequest(r2);
			ManagerImpl.getManager().sendRequest(r4);
			
			//il direttore dell'azienda A soddisfa la prima richiesta dell'azienda B
			ManagerImpl.getManager().satisfiesRequestDirector(r2, d1.getName());
			
			//il direttore dell'azienda C soddisfa la seconda richiesta dell'azienda B
			ManagerImpl.getManager().satisfiesRequestDirector(r4, d3.getName());
			
			//faccio lavorare l'azienda A
			ManagerImpl.getManager().showDirectorInfo(d1.getName()).getFactory().getLoadingWarehouse().removeMaterial(r2.getSentQuantity());
			ManagerImpl.getManager().showDirectorInfo(d1.getName()).getFactory().getUnloadingWarehouse().addMaterial(r2.getSentQuantity());
			
			//faccio lavorare l'azienda C
			ManagerImpl.getManager().showDirectorInfo(d3.getName()).getFactory().getLoadingWarehouse().removeMaterial(r3.getSentQuantity());
			ManagerImpl.getManager().showDirectorInfo(d3.getName()).getFactory().getUnloadingWarehouse().addMaterial(r3.getSentQuantity());
			
			
			//muovo il treno
			ManagerImpl.getManager().nextDestination();
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination()==f1);
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentCapacity()==100);
			
			ManagerImpl.getManager().nextDestination();
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination()==f3);
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentCapacity()==250);
			
			ManagerImpl.getManager().nextDestination();
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination()==f2);
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentCapacity()==150);
			
			ManagerImpl.getManager().nextDestination();
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination()==StoreImpl.getStoreInstance());
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentCapacity()==0);
			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
	
	@org.junit.Test
	public void TestManu2() throws Exception {
		
		//[Fase 1]
		
		//creazione manager
		ManagerImpl.getManager(1000);
		
		//creazione materiale di B
		MaterialImpl m2 = new MaterialImpl("b", "c");
		
		//Creazione Azienda B
		FactoryImpl f2 = new FactoryImpl("B", m2, 20, 200, 200);
		DirectorImpl d2 = new DirectorImpl("pluto", f2);
		ManagerImpl.getManager().hireDirector(d2);
		
		//Richiesta che fa B
		RequestImpl r2 = new RequestImpl(f2, m2.getRawMaterial(), 20);
		
		//Il manager soddisfa la richiesta di B
		ManagerImpl.getManager().sendRequest(r2);
		ManagerImpl.getManager().satisfiesRequestManager(r2);
		
		
		//[Fase 2]
		
		//creo azienda A
		MaterialImpl m1 = new MaterialImpl("a", "b");
		FactoryImpl f1 = new FactoryImpl("A", m1, 20, 300, 300);
		DirectorImpl d1 = new DirectorImpl("pippo", f1);
		ManagerImpl.getManager().hireDirector(d1);
		
		
		//creo Azienda C
		MaterialImpl m3 = new MaterialImpl("d", "b");
		FactoryImpl f3 = new FactoryImpl("C", m3, 20, 300, 300);
		DirectorImpl d3 = new DirectorImpl("paperino", f3);
		ManagerImpl.getManager().hireDirector(d3);
		
		// creo 2 richieste di carico di B da 200 e 100 [kg]
		RequestImpl rb1 = new RequestImpl(f2, m2.getRawMaterial(), 100);
		RequestImpl rb2 = new RequestImpl(f2, m2.getRawMaterial(), 200);
		
		ManagerImpl.getManager().sendRequest(rb1);
		ManagerImpl.getManager().sendRequest(rb2);
		
		
		//faccio fare ad A e C delle richieste di carico da 300 kg
		RequestImpl r1 = new RequestImpl(f1, m1.getRawMaterial(), 300);
		RequestImpl r3 = new RequestImpl(f3, m3.getRawMaterial(), 300);
		
		//accetto le richieste di A e C da master
		ManagerImpl.getManager().sendRequest(r1);
		ManagerImpl.getManager().satisfiesRequestManager(r1);
		ManagerImpl.getManager().sendRequest(r3);
		ManagerImpl.getManager().satisfiesRequestManager(r3);
		
		
		
		
		//il direttore dell'azienda A soddisfa la richiesta da 200 dell'azienda B
		ManagerImpl.getManager().satisfiesRequestDirector(rb2, d1.getName());
		ManagerImpl.getManager().showDirectorInfo(d1.getName()).getFactory().getLoadingWarehouse().removeMaterial(rb2.getSentQuantity());
		ManagerImpl.getManager().showDirectorInfo(d1.getName()).getFactory().getUnloadingWarehouse().addMaterial(rb2.getSentQuantity());
		
		//il direttore dell'azienda C soddisfa la seconda richiesta dell'azienda B
		ManagerImpl.getManager().satisfiesRequestDirector(rb1, d3.getName());
		ManagerImpl.getManager().showDirectorInfo(d3.getName()).getFactory().getLoadingWarehouse().removeMaterial(rb1.getSentQuantity());
		ManagerImpl.getManager().showDirectorInfo(d3.getName()).getFactory().getUnloadingWarehouse().addMaterial(rb1.getSentQuantity());
		
		
		System.out.println(d1);
		System.out.println(ManagerImpl.getManager().showTrainInfo());
		
		
		ManagerImpl.getManager().nextDestination();
		assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination()==f1);
		assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentCapacity()==200);
		
		ManagerImpl.getManager().nextDestination();
		assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination()==f3);
		assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentCapacity()==300);
		
		try {
			ManagerImpl.getManager().nextDestination();	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination()==f2);
		System.out.println(ManagerImpl.getManager().showTrainInfo().getCurrentCapacity());
		assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentCapacity()==200);
		
		ManagerImpl.getManager().nextDestination();
		assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination()==StoreImpl.getStoreInstance());
		assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentCapacity()==0);
		
	}
	
	@org.junit.Test
	public void emptyFieldExceptionTest() {
		
	}
	
	@org.junit.Test
	public void equalMaterialExceptionTest() {
		
	}
	
	@org.junit.Test
	public void emptyWarehouseExceptionTest() {
		
	}
	
	@org.junit.Test
	public void fullTrainExceptionTest() {
		
	}
	
	@org.junit.Test
	public void fullWarehouseExceptionTest() {
		
	}
	
	@org.junit.Test
	public void lowTrainCapacityExceptionTest() {
		
	}
	
	@org.junit.Test
	public void maximumCharactersExceptionTest() {
		
	}
	
	@org.junit.Test
	public void staffIsAlreadyNotWorkingExceptionTest() {
		
	}

	@org.junit.Test
	public void staffIsAlreadyWorkingExceptionTest() {
		
	}
	
	@org.junit.Test
	public void wrongNeededQuantityExceptionTest() {
		
	}
}
