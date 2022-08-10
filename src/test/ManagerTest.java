package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import controller.classes.ManagerImpl;
import exceptions.EmptyFieldException;
import exceptions.EqualMaterialException;
import exceptions.FullWarehouseException;
import exceptions.LowTrainCapacityException;
import exceptions.MaximumCharactersException;
import exceptions.WrongNeededQuantityException;
import exceptions.WrongStaffValueException;
import exceptions.WrongWarehouseCapacityException;
import model.classes.DirectorImpl;
import model.classes.FactoryImpl;
import model.classes.MaterialImpl;
import model.classes.RequestImpl;
import model.interfaces.Director;
import model.interfaces.Factory;
import model.interfaces.Material;
import model.interfaces.Request;

public class ManagerTest {
	
	@org.junit.Test
	public void managerTest1() {
		try {
			ManagerImpl.getManager(1000);
			hireDirectorTest1();
			sendRequestGetLinkRequestAndSatisfiesByManagerTest();
			fireDirectorTest1();
			
		} catch (LowTrainCapacityException e) {
			e.printStackTrace();
		}
	}
	
	public void hireDirectorTest1() {
		//Test assunzione unico direttore
		Material primoMateriale;
		
		try {
			primoMateriale = new MaterialImpl("Primgrezzo", "Primlavorato");
			Factory primaAzienda = new FactoryImpl("Primaaz", primoMateriale, 20, 200, 200);
			Director primoDirettore = new DirectorImpl("Primodir", primaAzienda);
				
			ManagerImpl.getManager().hireDirector(primoDirettore);
			assertEquals(ManagerImpl.getManager().getLinkDirectors().size(), 1);
			assertEquals(ManagerImpl.getManager().showDirectorInfo("Primodir"), primoDirettore);
			
		} catch (EqualMaterialException | EmptyFieldException | MaximumCharactersException | WrongStaffValueException | WrongWarehouseCapacityException e) {
			e.printStackTrace();
		}
		

	}
	
	public void sendRequestGetLinkRequestAndSatisfiesByManagerTest() {
		try {
			//Test richiesta per manager
			Request primaRichiesta = new RequestImpl(ManagerImpl.getManager().showFactoryInfo("Primodir"), "Grezzo", 10);	
			
			ManagerImpl.getManager().sendRequest(primaRichiesta);
			assertTrue(ManagerImpl.getManager().getlinkRequestsManager().contains(primaRichiesta));
			assertEquals(ManagerImpl.getManager().getlinkRequestsManager().size(), 1);
			
			ManagerImpl.getManager().satisfiesRequestManager(primaRichiesta);
			assertTrue(ManagerImpl.getManager().getlinkRequestsManager().isEmpty());
			assertEquals(ManagerImpl.getManager().showFactoryInfo("Primodir").getLoadingWarehouse().getCurrentCapacity(), 10);
		
		} catch (FullWarehouseException | WrongNeededQuantityException e) {
			e.printStackTrace();
		}	
	}

	public void fireDirectorTest1() {
		//Test licenziamento singolo direttore
		ManagerImpl.getManager().fireDirector("Primodir");
		assertTrue(ManagerImpl.getManager().getLinkDirectors().isEmpty());
	}

	
	
	@org.junit.Test
	public void managerTest2() {
		try {
			ManagerImpl.getManager(1000);
			hireDirectorTest2();
			sendRequestGetLinkRequestAndSatisfiesRequestTest();
			fireDirectorTest2();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void hireDirectorTest2() {
		try {
			//Test assunzione due direttori
			Material primoMateriale = new MaterialImpl("Primgrezzo", "Primlavorato");
			Factory primaAzienda = new FactoryImpl("Primaaz", primoMateriale, 20, 200, 200);
			Director primoDirettore = new DirectorImpl("Primodir", primaAzienda);
			
			Material secondoMateriale = new MaterialImpl("Primlavorato", "Seclavorato");
			Factory secondaAzienda = new FactoryImpl("Secondaaz", secondoMateriale, 20, 200, 200);
			Director secondoDirettore = new DirectorImpl("Secondodir", secondaAzienda);
			
			ManagerImpl.getManager().hireDirector(primoDirettore);
			ManagerImpl.getManager().hireDirector(secondoDirettore);
			
			assertTrue(ManagerImpl.getManager().getLinkDirectors().size() == 2);
			assertEquals(ManagerImpl.getManager().showDirectorInfo("Primodir"), primoDirettore);
			assertEquals(ManagerImpl.getManager().showDirectorInfo("Secondodir"), secondoDirettore);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendRequestGetLinkRequestAndSatisfiesRequestTest() {
		try {
			//Test richiesta per direttori
			Request terzaRichiesta = new RequestImpl(ManagerImpl.getManager().showFactoryInfo("Secondodir"), "Primlavorato", 10);	
			
			ManagerImpl.getManager().sendRequest(terzaRichiesta);
			assertTrue(ManagerImpl.getManager().showDirectorInfo("Primodir").getRequestsToSatisfy().contains(terzaRichiesta));
			assertEquals(ManagerImpl.getManager().showDirectorInfo("Primodir").getRequestsToSatisfy().size(), 1);
			assertTrue(ManagerImpl.getManager().getlinkRequestsManager().isEmpty());
			
			Request quartaRichiesta = new RequestImpl(ManagerImpl.getManager().showFactoryInfo("Secondodir"), "Primlavorato", 20);
			
			ManagerImpl.getManager().sendRequest(quartaRichiesta);
			assertTrue(ManagerImpl.getManager().showDirectorInfo("Primodir").getRequestsToSatisfy().contains(quartaRichiesta));
			assertEquals(ManagerImpl.getManager().showDirectorInfo("Primodir").getRequestsToSatisfy().size(), 2);
			assertTrue(ManagerImpl.getManager().getlinkRequestsManager().isEmpty());
			
			ManagerImpl.getManager().satisfiesRequestDirector(quartaRichiesta, "Primodir");
			assertEquals(ManagerImpl.getManager().showDirectorInfo("Primodir").getRequestsToSatisfy().size(), 1);
			assertEquals(ManagerImpl.getManager().showDirectorInfo("Primodir").getAcceptedRequest(), quartaRichiesta);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void fireDirectorTest2() {
		ManagerImpl.getManager().fireDirector("Secondodir");
		assertEquals(ManagerImpl.getManager().getLinkDirectors().size(), 1);
		assertTrue(ManagerImpl.getManager().showDirectorInfo("Primodir").getRequestsToSatisfy().isEmpty());
		assertEquals(ManagerImpl.getManager().showDirectorInfo("Primodir").getAcceptedRequest(), null);
		
	}
	
	
}
