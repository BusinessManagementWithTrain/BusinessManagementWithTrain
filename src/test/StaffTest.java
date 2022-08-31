package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import controller.classes.ManagerImpl;
import exceptions.EmptyWarehouseException;
import exceptions.FullWarehouseException;
import exceptions.StaffIsAlreadyNotWorkingException;
import exceptions.StaffIsAlreadyWorkingException;
import exceptions.WrongStaffValueException;
import exceptions.WrongWarehouseCapacityException;
import model.classes.StaffImpl;
import model.classes.WarehouseImpl;
import model.interfaces.Staff;
import model.interfaces.Warehouse;

public class StaffTest {
	
	/*
	 * I test sullo staff si focalizzano principalmente nel verificare
	 * le corrette allocazioni e soprattutto verificare l'utilizzo corretto
	 * delle eccezioni.
	 */

	public void staffTests() {
		try {
			/*
			 * In entrambe le creazioni del manager notiamo che la capienza del treno viene
			 * associata sempre a 1000, questo per evitare di incorrere nell'eccezione
			 */
			
			//Creiamo un nuovo manager ed invochiamo la prima batteria di test
			ManagerImpl.getManager(1000);
			generalStaffTest();
			
			//Resettiamo le variabili e lanciamo una seconda batteria di test
			ManagerImpl.getManager(1000);
			exceptionStaffTest();
			
		} catch (Exception e) {
			//Se arriviamo qui qualcosa è andato storto
			e.printStackTrace();
			fail("Unexpected exception!");
		}
	}

	private void generalStaffTest() throws WrongWarehouseCapacityException, WrongStaffValueException, FullWarehouseException, EmptyWarehouseException, StaffIsAlreadyWorkingException, StaffIsAlreadyNotWorkingException {

		/*
		 * Nella prima batteria andremo a testare le associazioni corrette da parte
		 * dell'applicativo e a testare i metodi fondamentali
		 */
		
		//Iniziamo creando lo staf e verificando l'allocazione corretta delle componenti
		Warehouse carico = new WarehouseImpl("Grezzo", 500);
		Warehouse scarico = new WarehouseImpl("Lavorato", 700);
		Staff staff = new StaffImpl(20, carico, scarico);

		assertEquals(20, staff.getNumber());
		
		//A questo punto carichiamo manualmente il materiale (metodo già testato) 
		carico.addMaterial(300);
		
		/*
		 * E testiamo i metodi dello staff che, come specificato nella documentazione, funzioneranno a cicli:
		 * 	- Durante la fase di inizio lavoro, lo staff preleva del materiale dal magazzino di carico
		 * 	  simulando la lavorazione dello stesso;
		 * 	- Durante la fase di fine lavoro, lo staff posizionerà il materiale lavorato nel magazzino
		 *    di scarico e si metterà in pausa fin tanto che l'utente non inizierà un nuovo lavoro;
		 */
		staff.startWorking();
		assertEquals(280, carico.getCurrentCapacity());
		assertEquals(0, scarico.getCurrentCapacity());
		
		staff.stopWorking();
		assertEquals(280, carico.getCurrentCapacity());
		assertEquals(20, scarico.getCurrentCapacity());
	}
	
	private void exceptionStaffTest() throws WrongWarehouseCapacityException, WrongStaffValueException, FullWarehouseException {
		/*
		 * Nella seconda batteria di test andremo a verificare le corrette invocazioni
		 * delle eccezioni create ad hoc
		 */
		
		//Iniziamo creando tutto il necessario per poi andare a inizializzare lo staff
		Warehouse carico = new WarehouseImpl("Grezzo", 500);
		Warehouse scarico = new WarehouseImpl("Lavorato", 700);
		Staff staff = null;
		
		try {
			//La prima eccezione testata è quella relativa ad un numero di operatori inferiore a 1
			staff = new StaffImpl(0, carico, scarico);
			fail("No exception here!");
		} catch(WrongStaffValueException e) {
			assertNull(staff);
		}
		
		try {
			/*
			 * La seconda eccezione testata è quella relativa ad un numero degli operatori 
			 * superiore alla capienza massima del magazzino di carico
			 */
			staff = new StaffImpl(600, carico, scarico);
			fail("No exception here!");
		} catch(WrongStaffValueException e) {
			assertNull(staff);
		}
		
		//Riassegniamo i valori dei magazzini per testare nuovamente il numero degli operatori
		carico = new WarehouseImpl("Grezzo", 700);
		scarico = new WarehouseImpl("Lavorato", 500);
		
		try {
			/*
			 * La terza eccezione testata è quella relativa ad un numero degli operatori 
			 * superiore alla capienza massima del magazzino di scarico
			 */
			staff = new StaffImpl(600, carico, scarico);
			fail("No exception here!");
		} catch(WrongStaffValueException e) {
			assertNull(staff);
		}
		
		//Creo correttamente lo staff per proseguire con i test
		staff = new StaffImpl(20, carico, scarico);
		
		try {
			/*
			 * La quarta eccezione testata è quella relativa all'invocazione del metodo
			 * utilizzato per iniziare i lavori senza aver materiale nel magazzino di carico
			 */
			staff.startWorking();
		} catch(EmptyWarehouseException | StaffIsAlreadyWorkingException e) {
			assertEquals(EmptyWarehouseException.class, e.getClass());
			assertEquals(0, carico.getCurrentCapacity());
			assertEquals(0 ,scarico.getCurrentCapacity());
		}
		
		//Carico manualmente il magazzino di carico per proseguire con i test
		carico.addMaterial(50);
		
		try {
			/*
			 * La quinta eccezione testata è quella relativa all'invocazione del metodo
			 * utilizzato per iniziare i lavori senza aver terminato i lavori precedentemente
			 */
			staff.startWorking();
			staff.startWorking();
			fail("No exception here!");
		} catch (EmptyWarehouseException | StaffIsAlreadyWorkingException e) {
			assertEquals(StaffIsAlreadyWorkingException.class, e.getClass());
			assertEquals(30, carico.getCurrentCapacity());
			assertEquals(0 ,scarico.getCurrentCapacity());
		}
		
		try {
			/*
			 * La sesta eccezione testata è quella relativa all'invocazione del metodo
			 * utilizzato per terminare i lavori senza aver iniziato i lavori precedentemente
			 */
			staff.stopWorking();
			staff.stopWorking();
			fail("No exception here!");
		} catch (FullWarehouseException | StaffIsAlreadyNotWorkingException e) {
			assertEquals(StaffIsAlreadyNotWorkingException.class, e.getClass());
			assertEquals(30, carico.getCurrentCapacity());
			assertEquals(20, scarico.getCurrentCapacity());
		}
	}
}
