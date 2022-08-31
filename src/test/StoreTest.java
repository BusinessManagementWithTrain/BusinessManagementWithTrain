package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import controller.classes.ManagerImpl;
import exceptions.EmptyFieldException;
import exceptions.EqualsMaterialsException;
import exceptions.FullWarehouseException;
import exceptions.MaximumCharactersException;
import exceptions.WrongStaffValueException;
import exceptions.WrongWarehouseCapacityException;
import model.classes.StoreImpl;
import model.interfaces.Store;

public class StoreTest {
	/*
	 * I test sul negozio si focalizzano principalmente nel verificare
	 * le corrette allocazioni e soprattutto verificare l'unicità dello stesso
	 */
	
	public void storeTests() {
		try {
			/*
			 * Notiamo che la capienza del treno viene associata sempre a 1000,
			 * questo per evitare di incorrere nell'eccezione
			 */
			
			//Creiamo un nuovo manager ed invochiamo la prima batteria di test
			ManagerImpl.getManager(1000);
			generalStoreTest();
			
		} catch (Exception e) {
			//Se arriviamo qui qualcosa è andato storto
			e.printStackTrace();
			fail("Unexpected exception!");
		}
	}

	private void generalStoreTest() throws EqualsMaterialsException, EmptyFieldException, MaximumCharactersException, WrongStaffValueException, WrongWarehouseCapacityException, FullWarehouseException {

		/*
		 * Nella prima batteria andremo a testare le associazioni corrette da parte
		 * dell'applicativo andando a verificare l'unicità
		 */
		Store negozio = StoreImpl.getStoreInstance();
		
		assertEquals(StoreImpl.getStoreInstance(), negozio);
		assertSame(StoreImpl.getStoreInstance(), negozio);
		assertNull(negozio.getMaterial());
		assertNull(negozio.getUnloadingWarehouse());
		assertEquals("Store", negozio.getName());
		assertNull(negozio.getStuffMembers());
		
		/*
		 * Nel prossimo test andremo a controllare la capienza concettualmente illimitata
		 * in quanto, come dichiarato nella relazione, non aggiungendo elementi ad un magazzino
		 * vero e proprio, il magazzino del negozio continuerà ad avere capienza pari a 0
		 */
		assertEquals(0, negozio.getLoadingWarehouse().getCurrentCapacity());
		
		for(int i = 0; i < 1000; i++) {
			negozio.getLoadingWarehouse().addMaterial(1000);
			assertEquals(0, negozio.getLoadingWarehouse().getCurrentCapacity());
		}
	}
}
