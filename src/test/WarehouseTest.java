package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import controller.classes.ManagerImpl;
import exceptions.EmptyWarehouseException;
import exceptions.EqualsMaterialsException;
import exceptions.FullWarehouseException;
import exceptions.WrongWarehouseCapacityException;
import model.classes.WarehouseImpl;
import model.interfaces.Warehouse;

public class WarehouseTest {
	/*
	 * I test sui magazzini si focalizzano principalmente nel verificare
	 * le corrette allocazioni e soprattutto verificare l'utilizzo corretto
	 * delle eccezioni.
	 */
	
	public void warehouseTests() {
		try {
			/*
			 * In entrambe le creazioni del manager notiamo che la capienza del treno viene
			 * associata sempre a 1000, questo per evitare di incorrere nell'eccezione
			 */
			
			//Creiamo un nuovo manager ed invochiamo la prima batteria di test
			ManagerImpl.getManager(1000);
			generalWarehouseTest();
			
			//Resettiamo le variabili e lanciamo una seconda batteria di test
			ManagerImpl.getManager(1000);
			exceptionWarehouseTest();
			
		} catch (Exception e) {
			//Se arriviamo qui qualcosa è andato storto
			e.printStackTrace();
			fail("Unexpected exception!");
		}
	}

	private void generalWarehouseTest() throws WrongWarehouseCapacityException, FullWarehouseException, EmptyWarehouseException {

		/*
		 * Nella prima batteria andremo a testare le associazioni
		 * corrette da parte dell'applicativo e l'invocazione dei
		 * metodi fondamentali
		 */
		Warehouse primoMagazzino = new WarehouseImpl("Materiale", 400);
		
		assertEquals(400, primoMagazzino.getTotalCapacity());
		assertEquals(0, primoMagazzino.getCurrentCapacity());
		assertEquals("Materiale", primoMagazzino.getMaterial());
		
		//Il primo metodo testato è quello relativo all'aggiunta di materiale
		primoMagazzino.addMaterial(100);
		assertEquals(100, primoMagazzino.getCurrentCapacity());
		
		//Il secondo metodo testato è quello relativo alla rimozione di materiale
		primoMagazzino.removeMaterial(50);
		assertEquals(50, primoMagazzino.getCurrentCapacity());
		
	}
	
	private void exceptionWarehouseTest() throws EqualsMaterialsException, WrongWarehouseCapacityException {
		/*
		 * Nella seconda batteria di test andremo a verificare le corrette invocazioni
		 * delle eccezioni create ad hoc
		 */
		Warehouse magazzino = null;
		
		try {
			/*
			 * La prima eccezione testata è quella relativa alla creazione di
			 * un magazzino con la capienza superiore alla capienza del treno
			 */
			magazzino = new WarehouseImpl("Materiale", 2000);
			fail("No exception here!");
		} catch(WrongWarehouseCapacityException e) {
			assertNull(magazzino);
		}
		
		//Creo correttamente il magazzino per proseguire con i test
		magazzino = new WarehouseImpl("Materiale", 500);
		
		try {
			/*
			 * La seconda eccezione testata è quella relativa ad un errato importo
			 * nella fase di carico del magazzino
			 */
			magazzino.addMaterial(600);
			fail("No exception here!");
		} catch(FullWarehouseException e) {
			assertEquals(0, magazzino.getCurrentCapacity());
		}
		
		try {
			/*
			 * La terza eccezione testata è quella relativa ad un errato importo
			 * nella fase di scarico del magazzino
			 */
			magazzino.removeMaterial(100);
			fail("No exception here!");
		} catch(EmptyWarehouseException e) {
			assertEquals(0, magazzino.getCurrentCapacity());
		}
	}
}
