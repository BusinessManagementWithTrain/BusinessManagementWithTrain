package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import controller.classes.ManagerImpl;
import exceptions.EmptyFieldException;
import exceptions.EqualsMaterialsException;
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
	 * le corrette allocazioni, il corretto funzionamento dei metodi (specialmente
	 * quello relativo all'uguaglianza) e soprattutto verificare l'utilizzo corretto
	 * delle eccezioni.
	 */
	
	public void factoryTests() {
		try {
			/*
			 * In entrambe le creazioni del manager notiamo che la capienza del treno viene 
			 * associata sempre a 1000, questo per evitare di incorrere nell'eccezione
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
			fail("Unexpected exception!");
		}
	}

	private void generalFactoryTest() throws EqualsMaterialsException, EmptyFieldException, MaximumCharactersException, WrongStaffValueException, WrongWarehouseCapacityException {

		/*
		 * Nella prima batteria andremo a testare le associazioni corrette da parte
		 * dell'applicativo e, successivamente, andremo a verificare il criterio
		 * d'uguaglianza, ossia il nome uguale
		 */
		
		//Iniziamo creando un'azienda e verificando l'allocazione corretta delle componenti
		Material primoMateriale 	= new MaterialImpl("PrimGrezzo", "PrimLavorato");
		Factory primaAzienda		= new FactoryImpl("azienda", primoMateriale, 20, 400, 300);
		
		assertEquals("PrimGrezzo", primaAzienda.getMaterial().getRawMaterial());
		assertEquals("PrimLavorato", primaAzienda.getMaterial().getProcessedMaterial());
		assertEquals("azienda", primaAzienda.getName());
		assertEquals(400, primaAzienda.getLoadingWarehouse().getTotalCapacity());
		assertEquals(300, primaAzienda.getUnloadingWarehouse().getTotalCapacity());
		assertEquals(20, primaAzienda.getStuffMembers().getNumber());
		
		/*
		 * A questo punto andiamo a testare il criterio d'uguaglianza associando
		 * alla nuova azienda un nuovo materiale ma il nome uguale alla prima azienda
		 */
		Material secondoMateriale 	= new MaterialImpl("SecGrezzo", "SecLavorato");
		Factory secondaAzienda		= new FactoryImpl("azienda", secondoMateriale, 30, 300, 400);
		
		//Verifichiamo l'uguaglianza accertandoci di avere a che fare con due entità distinte
		assertNotSame(primaAzienda, secondaAzienda);
		assertEquals(primaAzienda, secondaAzienda);
	}
	
	private void exceptionFactoryTest() throws EqualsMaterialsException {
		/*
		 * Nella seconda batteria di test andremo a verificare le corrette invocazioni
		 * delle eccezioni create ad hoc
		 */
		
		//Iniziamo creando tutto il necessario per permetterci di inizializzare un'azienda
		Material materiale 			= new MaterialImpl("Grezzo", "Lavorato");
		Factory azienda 			= null;
		
		try {
			//La prima eccezione testata è quella relativa ad un nome "vuoto"
			azienda					= new FactoryImpl("", materiale, 20, 400, 300);
			fail("No exception here!");
		} catch(EmptyFieldException | MaximumCharactersException | WrongStaffValueException | WrongWarehouseCapacityException e) {
			assertEquals(EmptyFieldException.class, e.getClass());
			assertNull(azienda);
		}
		
		try {
			//La seconda eccezione testata è quella relativa ad un nome più lungo di 12 caratteri
			azienda					= new FactoryImpl("Nomemagg12car", materiale, 20, 400, 300);
			fail("No exception here!");
		} catch(MaximumCharactersException | EmptyFieldException | WrongStaffValueException | WrongWarehouseCapacityException e) {
			assertEquals(MaximumCharactersException.class, e.getClass());
			assertNull(azienda);
		}
		
		try {
			/*
			 * La terza eccezione testata è quella relativa al numero dei membri dello staff
			 * superiore al numero della capienza di entrambi i magazzini
			 */
			azienda					= new FactoryImpl("Nomeazienda", materiale, 500, 400, 300);
			fail("No exception here!");
		} catch(WrongStaffValueException | EmptyFieldException | MaximumCharactersException | WrongWarehouseCapacityException e) {
			assertEquals(WrongStaffValueException.class, e.getClass());
			assertNull(azienda);
		}
		
		try {
			/*
			 * La quarta eccezione testata è quella relativa al numero dei membri dello staff
			 * superiore al numero della capienza del magazzino di scarico
			 */
			azienda					= new FactoryImpl("Nomeazienda", materiale, 400, 500, 300);
			fail("No exception here!");
		} catch(WrongStaffValueException | EmptyFieldException | MaximumCharactersException | WrongWarehouseCapacityException e) {
			assertEquals(WrongStaffValueException.class, e.getClass());
			assertNull(azienda);
		}
		
		try {
			/*
			 * La quinta eccezione testata è quella relativa al numero dei membri dello staff
			 * superiore al numero della capienza del magazzino di carico
			 */
			azienda					= new FactoryImpl("Nomeazienda", materiale, 400, 300, 500);
			fail("No exception here!");
		} catch(WrongStaffValueException | EmptyFieldException | MaximumCharactersException | WrongWarehouseCapacityException e) {
			assertEquals(WrongStaffValueException.class, e.getClass());
			assertNull(azienda);
		}

		try {
			/*
			 * La sesta eccezione testata è quella relativa al numero della capienza 
			 * di entrambi i magazzini superiore alla capienza del treno
			 */
			azienda					= new FactoryImpl("Nomeazienda", materiale, 20, 2000, 2000);
			fail("No exception here!");
		} catch(WrongWarehouseCapacityException | EmptyFieldException | MaximumCharactersException | WrongStaffValueException e) {
			assertEquals(WrongWarehouseCapacityException.class, e.getClass());
			assertNull(azienda);
		}
		
		try {
			/*
			 * La settima eccezione testata è quella relativa al numero della capienza 
			 * del magazzino di scarico superiore alla capienza del treno
			 */
			azienda					= new FactoryImpl("Nomeazienda", materiale, 20, 200, 2000);
			fail("No exception here!");
		} catch(WrongWarehouseCapacityException | EmptyFieldException | MaximumCharactersException | WrongStaffValueException e) {
			assertEquals(WrongWarehouseCapacityException.class, e.getClass());
			assertNull(azienda);
		}
		
		try {
			/*
			 * L'ottava eccezione testata è quella relativa al numero della capienza 
			 * del magazzino di carico superiore alla capienza del treno
			 */
			azienda					= new FactoryImpl("Nomeazienda", materiale, 20, 2000, 200);
			fail("No exception here!");
		} catch(WrongWarehouseCapacityException | EmptyFieldException | MaximumCharactersException | WrongStaffValueException e) {
			assertEquals(WrongWarehouseCapacityException.class, e.getClass());
			assertNull(azienda);
		}
		
		//In ogni caso, in presenza di eccezioni, l'azienda non viene creata
	}
}
