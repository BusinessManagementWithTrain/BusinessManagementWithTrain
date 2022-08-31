package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import controller.classes.ManagerImpl;
import exceptions.EqualsMaterialsException;
import model.classes.MaterialImpl;
import model.interfaces.Material;

public class MaterialTest {

	/*
	 * I test sul materiale si focalizzano principalmente nel verificare
	 * le corrette allocazioni e soprattutto verificare l'utilizzo corretto
	 * delle eccezioni.
	 */

	public void materialTests() {
		try {
			/*
			 * In entrambe le creazioni del manager notiamo che la capienza del treno viene
			 * associata sempre a 1000, questo per evitare di incorrere nell'eccezione
			 */
			
			//Creiamo un nuovo manager ed invochiamo la prima batteria di test
			ManagerImpl.getManager(1000);
			generalMaterialTest();
			
			//Resettiamo le variabili e lanciamo una seconda batteria di test
			ManagerImpl.getManager(1000);
			exceptionStaffTest();
			
		} catch (Exception e) {
			//Se arriviamo qui qualcosa è andato storto
			e.printStackTrace();
			fail("Unexpected exception!");
		}
	}

	private void generalMaterialTest() throws EqualsMaterialsException {

		/*
		 * Nella prima batteria andremo a testare le associazioni corrette da parte
		 * dell'applicativo
		 */
		Material primoMateriale = new MaterialImpl("Grezzo", "Lavorato");
		
		assertEquals("Lavorato", primoMateriale.getProcessedMaterial());
		assertEquals("Grezzo", primoMateriale.getRawMaterial());
	}
	
	private void exceptionStaffTest() {
		/*
		 * Nella seconda batteria di test andremo a verificare le corrette invocazioni
		 * delle eccezioni create ad hoc
		 */
		Material materiale = null;
		
		try {
			/*
			 * La prima eccezione testata è quella relativa ad un materiale con
			 * lo stesso nome sia da grezzo che da lavorato
			 */
			materiale = new MaterialImpl("Uguale", "Uguale");
			fail("No exception here!");
		} catch(EqualsMaterialsException e) {
			assertNull(materiale);
		}
	}
}
