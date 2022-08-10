package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import controller.classes.ManagerImpl;
import exceptions.EmptyFieldException;
import exceptions.EqualMaterialException;
import exceptions.MaximumCharactersException;
import exceptions.WrongStaffValueException;
import exceptions.WrongWarehouseCapacityException;
import model.classes.FactoryImpl;
import model.classes.MaterialImpl;
import model.interfaces.Factory;
import model.interfaces.Material;

public class FactoryTest {
	
	/*
	 * I test sulle aziende si focalizzano principalmente nel verificare
	 * le corrette allocazioni e soprattutto verificare l'utilizzo corretto
	 * delle eccezioni.
	 */
	
	@org.junit.Test
	public void factoryTests() {
		try {
			/*
			 * In entrambe le creazioni del manager notiamo che la capienza del treno viene
			 * asssociata sempre a 1000, questo per evitare di incorrere nell'eccezione
			 */
			
			//Creiamo un nuovo manager ed invochiamo la prima batteria di test
			ManagerImpl.getManager(1000);
			generalFactoryTest();
			
			//Resettiamo le variabili e lanciamo una seconda batteria di test
			ManagerImpl.getManager(1000);
			exceptionFactoryTest();
		} catch (Exception e) {
			//Se arriviamo qui qualcosa è andato storto
			e.printStackTrace();
		}
	}

	private void generalFactoryTest() throws EqualMaterialException, EmptyFieldException, MaximumCharactersException, WrongStaffValueException, WrongWarehouseCapacityException {

		/*
		 * Nella prima batteria andremo a testare le associazioni corrette da parte
		 * dell'applicativo e, successivamente, andremo a verificare il criterio
		 * d'uguaglianza, ossia il nome uguale
		 */
		Material primoMateriale 	= new MaterialImpl("Primogrezzo", "Primolavorato");
		Factory primaAzienda		= new FactoryImpl("Nomeazienda", primoMateriale, 20, 400, 300);
		
		assertEquals(primaAzienda.getMaterial().getRawMaterial(), "Primogrezzo");
		assertEquals(primaAzienda.getMaterial().getProcessedMaterial(), "Primolavorato");
		assertEquals(primaAzienda.getName(), "Nomeazienda");
		assertEquals(primaAzienda.getLoadingWarehouse().getTotalCapacity(), 400);
		assertEquals(primaAzienda.getUnloadingWarehouse().getTotalCapacity(), 300);
		assertEquals(primaAzienda.getStuffMembers().getNumber(), 20);
			
		Material secondoMateriale 	= new MaterialImpl("Secgrezzo", "Seclavorato");
		Factory secondaAzienda	= new FactoryImpl("Nomeazienda", secondoMateriale, 30, 300, 400);
		assertEquals(primaAzienda, secondaAzienda);		
	}
	
	private void exceptionFactoryTest() {
		/*
		 * Nella seconda batteria di test andremo a verificare le corrette invocazioni
		 * delle eccezioni create ad hoc
		 */
		Material materiale;
		Factory azienda = null;
		
		try {
			//Nel primo caso il nome dell'azienda è vuoto
			materiale 	= new MaterialImpl("Grezzo", "Lavorato");
			azienda		= new FactoryImpl("", materiale, 20, 400, 300);
		} catch(EmptyFieldException | EqualMaterialException | MaximumCharactersException | WrongStaffValueException | WrongWarehouseCapacityException e) {
			assertEquals(e.getClass(), EmptyFieldException.class);
			assertNull(azienda);
		}
		
		try {
			//Nel secondo caso il nome dell'azienda supera i 12 caratteri massimi consentiti
			materiale 	= new MaterialImpl("Grezzo", "Lavorato");
			azienda		= new FactoryImpl("Nomemagg12car", materiale, 20, 400, 300);
		} catch(MaximumCharactersException | EqualMaterialException | EmptyFieldException | WrongStaffValueException | WrongWarehouseCapacityException e) {
			assertEquals(e.getClass(), MaximumCharactersException.class);
			assertNull(azienda);
		}
		
		try {
			//Nel terzo caso il numero dei membri staff supera la capienza dei magazzini
			materiale 	= new MaterialImpl("Grezzo", "Lavorato");
			azienda		= new FactoryImpl("Nomeazienda", materiale, 500, 400, 300);
		} catch(WrongStaffValueException | EqualMaterialException | EmptyFieldException | MaximumCharactersException | WrongWarehouseCapacityException e) {
			assertEquals(e.getClass(), WrongStaffValueException.class);
			assertNull(azienda);
		}
		
		try {
			//Nel quarto caso il numero dei membri staff supera la capienza del magazzino di scarico
			materiale 	= new MaterialImpl("Grezzo", "Lavorato");
			azienda		= new FactoryImpl("Nomeazienda", materiale, 400, 500, 300);
		} catch(WrongStaffValueException | EqualMaterialException | EmptyFieldException | MaximumCharactersException | WrongWarehouseCapacityException e) {
			assertEquals(e.getClass(), WrongStaffValueException.class);
			assertNull(azienda);
		}
		
		try {
			//Nel quinto caso il numero dei membri staff supera la capienza del magazzino di carico
			materiale 	= new MaterialImpl("Grezzo", "Lavorato");
			azienda		= new FactoryImpl("Nomeazienda", materiale, 400, 300, 500);
		} catch(WrongStaffValueException | EqualMaterialException | EmptyFieldException | MaximumCharactersException | WrongWarehouseCapacityException e) {
			assertEquals(e.getClass(), WrongStaffValueException.class);
			assertNull(azienda);
		}

		try {
			//Nel sesto caso la capienza dei magazzini supera la capienza del treno
			materiale 	= new MaterialImpl("Grezzo", "Lavorato");
			azienda		= new FactoryImpl("Nomeazienda", materiale, 20, 2000, 2000);
		} catch(WrongWarehouseCapacityException | EqualMaterialException | EmptyFieldException | MaximumCharactersException | WrongStaffValueException e) {
			assertEquals(e.getClass(), WrongWarehouseCapacityException.class);
			assertNull(azienda);
		}
		
		try {
			//Nel settimo caso la capienza del magazzino di scarico supera la capienza del treno
			materiale 	= new MaterialImpl("Grezzo", "Lavorato");
			azienda		= new FactoryImpl("Nomeazienda", materiale, 20, 200, 2000);
		} catch(WrongWarehouseCapacityException | EqualMaterialException | EmptyFieldException | MaximumCharactersException | WrongStaffValueException e) {
			assertEquals(e.getClass(), WrongWarehouseCapacityException.class);
			assertNull(azienda);
		}
		
		try {
			//Nell'ottavo caso la capienza del magazzino di carico supera la capienza del treno
			materiale 	= new MaterialImpl("Grezzo", "Lavorato");
			azienda		= new FactoryImpl("Nomeazienda", materiale, 20, 2000, 200);
		} catch(WrongWarehouseCapacityException | EqualMaterialException | EmptyFieldException | MaximumCharactersException | WrongStaffValueException e) {
			assertEquals(e.getClass(), WrongWarehouseCapacityException.class);
			assertNull(azienda);
		}
		
		//In ogni caso, in presenza di eccezioni, l'azienda non viene creata
	}
}
