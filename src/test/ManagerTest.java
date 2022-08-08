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
			primoMateriale = new MaterialImpl("PrimGrezzo", "PrimLavorato");
			Factory primaAzienda = new FactoryImpl("PrimaAz", primoMateriale, 20, 200, 200);
			Director primoDirettore = new DirectorImpl("PrimoDir", primaAzienda);
				
			ManagerImpl.getManager().hireDirector(primoDirettore);
			assertEquals(ManagerImpl.getManager().getLinkDirectors().size(), 1);
			assertEquals(ManagerImpl.getManager().showDirectorInfo("PrimoDir"), primoDirettore);
			
		} catch (EqualMaterialException | EmptyFieldException | MaximumCharactersException e) {
			e.printStackTrace();
		}
		

	}
	
	public void sendRequestGetLinkRequestAndSatisfiesByManagerTest() {
		try {
			//Test richiesta per manager
			Request primaRichiesta = new RequestImpl(ManagerImpl.getManager().showFactoryInfo("PrimoDir"), "Grezzo", 10);	
			
			ManagerImpl.getManager().sendRequest(primaRichiesta);
			assertTrue(ManagerImpl.getManager().getlinkRequestsManager().contains(primaRichiesta));
			assertEquals(ManagerImpl.getManager().getlinkRequestsManager().size(), 1);
			
			ManagerImpl.getManager().satisfiesRequestManager(primaRichiesta);
			assertTrue(ManagerImpl.getManager().getlinkRequestsManager().isEmpty());
			assertEquals(ManagerImpl.getManager().showFactoryInfo("PrimoDir").getLoadingWarehouse().getCurrentCapacity(), 10);
		
		} catch (FullWarehouseException | WrongNeededQuantityException e) {
			e.printStackTrace();
		}	
	}

	public void fireDirectorTest1() {
		//Test licenziamento singolo direttore
		ManagerImpl.getManager().fireDirector("PrimoDir");
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
			Material primoMateriale = new MaterialImpl("PrimGrezzo", "PrimLavorato");
			Factory primaAzienda = new FactoryImpl("PrimaAz", primoMateriale, 20, 200, 200);
			Director primoDirettore = new DirectorImpl("PrimoDir", primaAzienda);
			
			Material secondoMateriale = new MaterialImpl("PrimLavorato", "SecLavorato");
			Factory secondaAzienda = new FactoryImpl("SecondaAz", secondoMateriale, 20, 200, 200);
			Director secondoDirettore = new DirectorImpl("SecondoDir", secondaAzienda);
			
			ManagerImpl.getManager().hireDirector(primoDirettore);
			ManagerImpl.getManager().hireDirector(secondoDirettore);
			
			assertTrue(ManagerImpl.getManager().getLinkDirectors().size() == 2);
			assertEquals(ManagerImpl.getManager().showDirectorInfo("PrimoDir"), primoDirettore);
			assertEquals(ManagerImpl.getManager().showDirectorInfo("SecondoDir"), secondoDirettore);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendRequestGetLinkRequestAndSatisfiesRequestTest() {
		try {
			//Test richiesta per direttori
			Request terzaRichiesta = new RequestImpl(ManagerImpl.getManager().showFactoryInfo("SecondoDir"), "PrimLavorato", 10);	
			
			ManagerImpl.getManager().sendRequest(terzaRichiesta);
			assertTrue(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().contains(terzaRichiesta));
			assertEquals(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().size(), 1);
			assertTrue(ManagerImpl.getManager().getlinkRequestsManager().isEmpty());
			
			Request quartaRichiesta = new RequestImpl(ManagerImpl.getManager().showFactoryInfo("SecondoDir"), "PrimLavorato", 20);
			
			ManagerImpl.getManager().sendRequest(quartaRichiesta);
			assertTrue(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().contains(quartaRichiesta));
			assertEquals(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().size(), 2);
			assertTrue(ManagerImpl.getManager().getlinkRequestsManager().isEmpty());
			
			ManagerImpl.getManager().satisfiesRequestDirector(quartaRichiesta, "PrimoDir");
			assertEquals(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().size(), 1);
			assertEquals(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getAcceptedRequest(), quartaRichiesta);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void fireDirectorTest2() {
		ManagerImpl.getManager().fireDirector("SecondoDir");
		assertEquals(ManagerImpl.getManager().getLinkDirectors().size(), 1);
		assertTrue(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().isEmpty());
		assertEquals(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getAcceptedRequest(), null);
		
	}
	
	
}
