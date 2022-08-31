package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import controller.classes.ManagerImpl;
import exceptions.DirectorIsAlreadyPresentException;
import exceptions.EmptyFieldException;
import exceptions.EqualsMaterialsException;
import exceptions.MaximumCharactersException;
import exceptions.WrongSendingFactoryException;
import exceptions.WrongStaffValueException;
import exceptions.WrongWarehouseCapacityException;
import model.classes.DirectorImpl;
import model.classes.FactoryImpl;
import model.classes.MaterialImpl;
import model.classes.RequestImpl;
import model.classes.StoreImpl;
import model.interfaces.Director;
import model.interfaces.Factory;
import model.interfaces.Material;
import model.interfaces.Request;

public class RequestTest {
	/*
	 * I test sulle richieste si focalizzano principalmente nel verificare
	 * le corrette allocazioni e la corretta applicazione del criterio d'uguaglianza
	 */
	
	public void requestTests() {
		try {
			/*
			 * Notiamo che la capienza del treno viene associata a 1000,
			 * questo per evitare di incorrere nell'eccezione
			 */
			
			//Creiamo un nuovo manager ed invochiamo la prima batteria di test
			ManagerImpl.getManager(1000);
			generalRequestTest();
			
			//Resettiamo le variabili e lanciamo una seconda batteria di test
			ManagerImpl.getManager(1000);
			exceptionRequestTest();
			
		} catch (Exception e) {
			//Se arriviamo qui qualcosa è andato storto
			e.printStackTrace();
			fail("Unexpected exception!");
		}
	}

	private void generalRequestTest() throws EqualsMaterialsException, EmptyFieldException, MaximumCharactersException, WrongStaffValueException, WrongWarehouseCapacityException, WrongSendingFactoryException, DirectorIsAlreadyPresentException {
		/*
		 * Nella prima batteria andremo a testare le associazioni corrette da parte
		 * dell'applicativo e, successivamente, andremo a verificare il criterio
		 * d'uguaglianza, ossia il codice identificativo uguale
		 */
		
		//Iniziamo creando una richiesta e verificando l'allocazione corretta delle componenti
		Material primoMateriale 		= new MaterialImpl("lavorato", "postLavorato");
		Factory aziendaDestinatario		= new FactoryImpl("Destinatario", primoMateriale, 20, 300, 300);
		Request primaRichiesta 			= new RequestImpl(aziendaDestinatario, "lavorato", 100);
		
		assertEquals(14, primaRichiesta.getRequestId());
		assertEquals(aziendaDestinatario, primaRichiesta.getReceiverFactory());
		assertEquals("lavorato", primaRichiesta.getSentMaterial());
		assertEquals(100, primaRichiesta.getSentQuantity());
		
		//A questo punto creiamo ed assumiamo un direttore (metodo già testato)
		Material secondoMateriale 		= new MaterialImpl("grezzo", "lavorato");
		Factory aziendaMittente 		= new FactoryImpl("Mittente", secondoMateriale, 20, 300, 300);
		Director direttoreMittente		= new DirectorImpl("Direttore", aziendaMittente);
		ManagerImpl.getManager().hireDirector(direttoreMittente);
		
		//Testiamo i metodi per settare le aziende della richiesta
		//Nel primo caso settiamo l'aziendaMittente come azienda mittente
		primaRichiesta.setSendingFactory(aziendaMittente);
		assertEquals(aziendaMittente, primaRichiesta.getSendingFactory());
		
		//Nel secondo caso settiamo lo store come azienda destinatario
		primaRichiesta.setReceiverFactoryToStore();
		assertEquals(StoreImpl.getStoreInstance(), primaRichiesta.getReceiverFactory());
	
		/*
		 * La creazione di una seconda richiesta esattamente uguale alla prima, ma con il
		 * codice identificativo diverso (cosa che l'applicativo gestisce in automatico),
		 * ci permette di verificare esattamente la disuguaglianza tra le due
		 */
		Request secondaRichiesta 		= new RequestImpl(aziendaDestinatario, "lavorato", 100);
		Request terzaRichiesta			= new RequestImpl(aziendaDestinatario, "lavorato", 100);
		
		/*
		 * Possiamo notare che la seconda richiesta e la terza siano uguali 
		 * in ogni componente ad eccezione del loro id e questo le rende diverse
		 * agli occhi dell'applicativo
		 */
		assertEquals(secondaRichiesta.getReceiverFactory(), terzaRichiesta.getReceiverFactory());
		assertEquals(secondaRichiesta.getSendingFactory(), terzaRichiesta.getSendingFactory());
		assertEquals(secondaRichiesta.getSentMaterial(), terzaRichiesta.getSentMaterial());
		assertEquals(secondaRichiesta.getSentQuantity(), terzaRichiesta.getSentQuantity());
		assertNotEquals(secondaRichiesta.getRequestId(), terzaRichiesta.getRequestId());
		assertNotEquals(secondaRichiesta, terzaRichiesta);
		
		/*
		 * L'ultimo blocco testa le associazioni corrette in caso di utilizzo
		 * del secondo costruttore
		 */
		Request quartaRichiesta 		= new RequestImpl(aziendaMittente, aziendaDestinatario, "lavorato", 40);
		
		assertEquals(17, quartaRichiesta.getRequestId());
		assertEquals(aziendaDestinatario, quartaRichiesta.getReceiverFactory());
		assertEquals(aziendaMittente, quartaRichiesta.getSendingFactory());
		assertEquals("lavorato", quartaRichiesta.getSentMaterial());
		assertEquals(40, quartaRichiesta.getSentQuantity());
		
	}
	
	private void exceptionRequestTest() throws EqualsMaterialsException, EmptyFieldException, MaximumCharactersException, WrongStaffValueException, WrongWarehouseCapacityException, DirectorIsAlreadyPresentException {
		/*
		 * Nella seconda batteria di test andremo a verificare le corrette invocazioni
		 * delle eccezioni create ad hoc
		 */
		
		//Iniziamo creando una richiesta ed un direttore che andremo ad assumere (metodo già testato)
		Material primoMateriale 		= new MaterialImpl("Lavorato", "PostLavorato");
		Factory aziendaDestinatario		= new FactoryImpl("Destinatario", primoMateriale, 20, 300, 300);
		Request primaRichiesta 			= new RequestImpl(aziendaDestinatario, "Lavorato", 100);
		
		Material secondoMateriale		= new MaterialImpl("Grezzo", "Altro");
		Factory aziendaMittente			= new FactoryImpl("Mittente", secondoMateriale, 20, 300, 300);
		Director direttoreMittente		= new DirectorImpl("Mittente", aziendaMittente);
		ManagerImpl.getManager().hireDirector(direttoreMittente);
		
		try {
			/*
			 * La prima eccezione testata è quella relativa all'assegnazione di un'azienda
			 * che produce un materiale non coerente al materiale richiesto:
			 * 	- Altro != Lavorato
			 */
			primaRichiesta.setSendingFactory(aziendaMittente);
			fail("No exception here!");
		} catch(WrongSendingFactoryException e) {
			assertNull(primaRichiesta.getSendingFactory());
		}
		
		/*
		 * A questo punto creiamo una nuova azienda con il materiale prodotto
		 * uguale al materiale richiesto dall'aziendaDestinatario
		 */
		Material terzoMateriale 		= new MaterialImpl("Grezzo", "Lavorato");
		Factory secAziendaMittente		= new FactoryImpl("SecMittente", terzoMateriale, 20, 300, 300);
		try {
			/*
			 * La seconda eccezione testata è quella relativa all'assegnazione di 
			 * un'azienda che non compare tra le aziende dei direttori assunti precedentemente
			 */
			primaRichiesta.setSendingFactory(secAziendaMittente);
			fail("No exception here!");
		} catch(WrongSendingFactoryException e) {
			assertNull(primaRichiesta.getSendingFactory());
		}
	}
}
