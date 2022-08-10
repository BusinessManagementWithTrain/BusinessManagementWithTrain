package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import controller.classes.ManagerImpl;
import exceptions.EmptyFieldException;
import exceptions.EqualMaterialException;
import exceptions.MaximumCharactersException;
import exceptions.WrongNeededQuantityException;
import exceptions.WrongStaffValueException;
import exceptions.WrongWarehouseCapacityException;
import model.classes.FactoryImpl;
import model.classes.MaterialImpl;
import model.classes.RequestImpl;
import model.classes.StoreImpl;
import model.interfaces.Factory;
import model.interfaces.Material;
import model.interfaces.Request;

public class RequestTest {
	/*
	 * I test sulle richieste si focalizzano principalmente nel verificare
	 * le corrette allocazioni e la corretta applicazione del criterio d'uguaglianza
	 */
	
	@org.junit.Test
	public void requestTests() {
		try {
			/*
			 * Notiamo che la capienza del treno viene asssociata a 1000,
			 * questo per evitare di incorrere nell'eccezione
			 */
			
			//Creiamo un nuovo manager ed invochiamo la prima batteria di test
			ManagerImpl.getManager(1000);
			generalRequestTest();
			
		} catch (Exception e) {
			//Se arriviamo qui qualcosa è andato storto
			e.printStackTrace();
		}
	}

	private void generalRequestTest() throws WrongNeededQuantityException, EqualMaterialException, EmptyFieldException, MaximumCharactersException, WrongStaffValueException, WrongWarehouseCapacityException {
		/*
		 * Nella prima batteria andremo a testare le associazioni corrette da parte
		 * dell'applicativo e, successivamente, andremo a verificare il criterio
		 * d'uguaglianza, ossia il codice identificativo uguale
		 */
		Material primoMateriale = new MaterialImpl("lavorato", "postLavorato");
		Factory aziendaDestinatario = new FactoryImpl("Destinatario", primoMateriale, 20, 300, 300);
		Request primaRichiesta = new RequestImpl(aziendaDestinatario, "lavorato", 100);
		
		assertEquals(primaRichiesta.getRequestId(), 0);
		assertEquals(primaRichiesta.getReceiverFactory(), aziendaDestinatario);
		assertEquals(primaRichiesta.getSentMaterial(), "lavorato");
		assertEquals(primaRichiesta.getSentQuantity(), 100);
		
		Material secondoMateriale = new MaterialImpl("grezzo", "lavorato");
		Factory aziendaMittente = new FactoryImpl("Mittente", secondoMateriale, 20, 300, 300);
		
		primaRichiesta.setSendingFactory(aziendaMittente);
		assertEquals(primaRichiesta.getSendingFactory(), aziendaMittente);
		
		primaRichiesta.setReceiverFactoryToStore();
		assertEquals(primaRichiesta.getReceiverFactory(), StoreImpl.getStoreInstance());
	
		/*
		 * La creazione di una seconda richiesta esattamente uguale alla prima, ma con il
		 * codice identificativo diverso (cosa che l'applicativo gestisce in automatico),
		 * ci permette di verificare esattamente la disuguaglianza tra le due
		 */
		Request secondaRichiesta = new RequestImpl(aziendaDestinatario, "lavorato", 100);
		
		assertEquals(secondaRichiesta.getRequestId(), 1);
		assertNotEquals(primaRichiesta, secondaRichiesta);
		
		/*
		 * L'ultimo blocco testa le associazioni corrette in caso di utilizzo
		 * del secondo costruttore
		 */
		Request terzaRichiesta = new RequestImpl(aziendaMittente, aziendaDestinatario, "lavorato", 40);
		
		assertEquals(terzaRichiesta.getRequestId(), 2);
		assertEquals(terzaRichiesta.getReceiverFactory(), aziendaDestinatario);
		assertEquals(terzaRichiesta.getSendingFactory(), aziendaMittente);
		assertEquals(terzaRichiesta.getSentMaterial(), "lavorato");
		assertEquals(terzaRichiesta.getSentQuantity(), 40);
		
	}
}
