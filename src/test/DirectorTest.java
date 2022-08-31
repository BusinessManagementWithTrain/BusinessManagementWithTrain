package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.classes.ManagerImpl;
import exceptions.AnotherAcceptedRequestException;
import exceptions.AnotherEmptyRequestIsPresentException;
import exceptions.DirectorIsAlreadyPresentException;
import exceptions.EmptyFieldException;
import exceptions.EqualsMaterialsException;
import exceptions.FullWarehouseException;
import exceptions.MaximumCharactersException;
import exceptions.WrongNeededQuantityException;
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

public class DirectorTest {
	
	/*
	 * I test sui direttori si focalizzano principalmente nel verificare
	 * le corrette allocazioni, il corretto funzionamento dei metodi (specialmente
	 * quello relativo all'uguaglianza) e soprattutto verificare l'utilizzo corretto
	 * delle eccezioni.
	 */
	
	public void directorTests() {
		try {
			/*
			 * Durante le creazioni del manager notiamo che la capienza del treno viene
			 * associata sempre a 1000, questo per evitare di incorrere nell'eccezione
			 */
			
			//Creiamo un nuovo manager ed invochiamo la prima batteria di test
			ManagerImpl.getManager(1000);
			generalDirectorTest();
			
			//Resettiamo le variabili e lanciamo una seconda batteria di test
			ManagerImpl.getManager(1000);
			equalsDirectorTest();
			
			//Resettiamo le variabili e lanciamo una terza batteria di test
			ManagerImpl.getManager(1000);
			exceptionDirectorTest();
		} catch (Exception e) {
			//Se arriviamo qui qualcosa è andato storto
			e.printStackTrace();
			fail("Unexpected exception!");
		}
	}

	private void generalDirectorTest() throws MaximumCharactersException, EmptyFieldException, WrongStaffValueException, WrongWarehouseCapacityException, EqualsMaterialsException, WrongNeededQuantityException, FullWarehouseException, AnotherAcceptedRequestException, AnotherEmptyRequestIsPresentException, DirectorIsAlreadyPresentException {

		/*
		 * Nella prima batteria andremo a testare le associazioni corrette da parte
		 * dell'applicativo e il corretto funzionamento dei metodi
		 */
		
		//Iniziamo creando ed assumendo un direttore
		Material primoMateriale			= new MaterialImpl("Grezzo", "Lavorato");
		Factory primaAzienda			= new FactoryImpl("PrimaAz", primoMateriale, 20, 400, 400);
		Director primoDirettore		 	= new DirectorImpl("PrimoDir", primaAzienda);
		ManagerImpl.getManager().hireDirector(primoDirettore);
		
		//Verifichiamo l'allocazione corretta delle componenti
		assertEquals(primaAzienda, primoDirettore.getFactory());
		assertEquals("PrimoDir", primoDirettore.getName());
		
		//Simuliamo la creazione di una richiesta attraverso il metodo del direttore
		Request richiesta = primoDirettore.newRequest(100);
		
		//Verifichiamo che la richiesta venga creata correttamente
		assertEquals(primaAzienda, richiesta.getReceiverFactory());
		assertEquals("Grezzo", richiesta.getSentMaterial());
		assertEquals(100, richiesta.getSentQuantity());
		
		/*
		 * A questo punto creiamo ed assumiamo un secondo direttore che produrrà il
		 * proprio materiale dal materiale prodotto dal primo direttore
		 */
		Material secondoMateriale		= new MaterialImpl("Lavorato", "PostLavorato");
		Factory secondaAzienda			= new FactoryImpl("SecondaAz", secondoMateriale, 20, 500, 500);
		Director secondoDirettore 		= new DirectorImpl("SecondoDir", secondaAzienda);
		ManagerImpl.getManager().hireDirector(secondoDirettore);
		
		/*
		 * Simuliamo la creazione di una nuova richiesta per il secondo direttore attraverso il manager
		 * (metodo già testato in precedenza)
		 */
		ManagerImpl.getManager().createNewRequest(50, "SecondoDir");
		
		/*
		 * Simuliamo di soddisfare la richiesta attraverso il primo direttore
		 * e verifichiamo che tutte le variabili del caso vengano aggiornate, ossia:
		 * 	- La richiesta viene rimossa dalla lista delle richieste soddisfabili
		 * 	- La richiesta diventa "Richiesta Accettata" per il direttore stesso
		 */
		primoDirettore.satisfyRequest(primoDirettore.getRequestsToSatisfy().stream().findFirst().get());		
		assertTrue(primoDirettore.getRequestsToSatisfy().isEmpty());
		assertEquals(50, primoDirettore.getAcceptedRequest().getSentQuantity());
		assertEquals(secondaAzienda, primoDirettore.getAcceptedRequest().getReceiverFactory());
		assertEquals(primaAzienda, primoDirettore.getAcceptedRequest().getSendingFactory());
		assertEquals("Lavorato", primoDirettore.getAcceptedRequest().getSentMaterial());
		
		//A questo punto testiamo i metodi per modificare le richieste
		//Iniziamo aggiungendo una richiesta
		richiesta 						= new RequestImpl(secondaAzienda, "Lavorato", 100);
		primoDirettore.addRequestToSatisfy(richiesta);
		
		//Verifichiamo che il metodo per rimuoverla funzioni correttamente
		primoDirettore.removeRequestToSatisfy(richiesta);
		assertTrue(primoDirettore.getRequestsToSatisfy().isEmpty());
		
		//Verifichiamo che il metodo per rimuovere una richiesta accettata funzioni correttamente
		primoDirettore.setAcceptedRequestToNull();
		assertNull(primoDirettore.getAcceptedRequest());
		
		//A questo punto passiamo a testare i metodi agenti sui magazzini
		/*
		 * Iniziamo aggiungendo a mano del materiale e verifichiamo che il metodo opzionale
		 * per svuotare i magazzini di scarico funzioni come previsto dalla documentazione, ossia:
		 * 	- Venga creata una nuova richiesta con destinazione il negozio;
		 */
		primoDirettore.getFactory().getUnloadingWarehouse().addMaterial(200);
		richiesta = primoDirettore.emptyWarehouse();
		
		assertEquals(primaAzienda, richiesta.getSendingFactory());
		assertEquals(StoreImpl.getStoreInstance(), richiesta.getReceiverFactory());
		assertEquals("Lavorato", richiesta.getSentMaterial());
		assertEquals(200, richiesta.getSentQuantity());
	}
	
	private void equalsDirectorTest() throws MaximumCharactersException, EmptyFieldException, EqualsMaterialsException, WrongStaffValueException, WrongWarehouseCapacityException {
		/*
		 * Nella seconda batteria di test andremo a verificare il criterio
		 * d'uguaglianza, ossia il nome uguale o l'azienda uguale
		 */
		
		//Creiamo due direttori con lo stesso nome
		Material primoMateriale			= new MaterialImpl("Grezzo", "Lavorato");
		Factory primaAzienda			= new FactoryImpl("PrimaAz", primoMateriale, 20, 400, 400);
		Director primoDirettore 		= new DirectorImpl("Direttore", primaAzienda);
		
		Material secondoMateriale		= new MaterialImpl("Lavorato", "PostLavorato");
		Factory secondaAzienda			= new FactoryImpl("SecondaAz", secondoMateriale, 20, 400, 400);
		Director secondoDirettore 		= new DirectorImpl("Direttore", secondaAzienda);
		
		//Verifichiamo l'uguaglianza accertandoci di non confrontare lo stesso direttore
		assertEquals(primoDirettore, secondoDirettore);
		assertEquals(primoDirettore.hashCode(), secondoDirettore.hashCode());
		assertNotSame(primoDirettore, secondoDirettore);
		
		//A questo punto cambiamo il nome di uno dei due impostando però la stessa azienda dell'altro
		secondoDirettore 				= new DirectorImpl("SecDir", primaAzienda);
		
		//Verifichiamo nuovamente l'uguaglianza
		assertEquals(primoDirettore, secondoDirettore);
		assertEquals(primoDirettore.getFactory().hashCode(), secondoDirettore.getFactory().hashCode());
		assertNotSame(primoDirettore, secondoDirettore);
	}
	
	private void exceptionDirectorTest() throws EqualsMaterialsException, EmptyFieldException, MaximumCharactersException, WrongStaffValueException, WrongWarehouseCapacityException, FullWarehouseException, WrongNeededQuantityException, DirectorIsAlreadyPresentException {
		/*
		 * Nella terza batteria di test andremo a verificare le corrette invocazioni
		 * delle eccezioni create ad hoc
		 */
		
		//Iniziamo creando la base per poi creare un direttore
		Material primoMateriale			= new MaterialImpl("Grezzo", "Lavorato");
		Factory primaAzienda			= new FactoryImpl("PrimaAz", primoMateriale, 20, 400, 500);
		Director primoDirettore 		= null;
		
		try {
			//La prima eccezione testata è quella relativa ad un nome "vuoto"
			primoDirettore 				= new DirectorImpl("", primaAzienda);
			fail("No exception here!");
		} catch(EmptyFieldException | MaximumCharactersException e) {
			assertEquals(EmptyFieldException.class, e.getClass());
			assertNull(primoDirettore);
		}
		
		try {
			//La seconda eccezione testata è quella relativa ad un nome più lungo di 12 caratteri
			primoDirettore 				= new DirectorImpl("Piùdi12caratt", primaAzienda);
			fail("No exception here!");
		} catch(EmptyFieldException | MaximumCharactersException e) {
			assertEquals(MaximumCharactersException.class, e.getClass());
			assertNull(primoDirettore);
		}
		
		//A questo punto creiamo ed assumiamo il direttore per passare ad altri test
		primoDirettore 					= new DirectorImpl("Direttore", primaAzienda);
		ManagerImpl.getManager().hireDirector(primoDirettore);
		
		try {
			/*
			 * La terza eccezione testata è quella relativa alla creazione di una richiesta
			 * con quantità inferiore ad 1
			 */
			ManagerImpl.getManager().createNewRequest(0, "Direttore");
			fail("No exception here!");
		} catch(WrongNeededQuantityException e) {
			assertTrue(ManagerImpl.getManager().getlinkRequestsManager().isEmpty());
		}
		
		try {
			/*
			 * La quarta eccezione testata è quella relativa alla creazione di una richiesta
			 * con quantità superiore alla capienza dei magazzini
			 */
			ManagerImpl.getManager().createNewRequest(450, "Direttore");
			fail("No exception here!");
		} catch(WrongNeededQuantityException e) {
			assertTrue(ManagerImpl.getManager().getlinkRequestsManager().isEmpty());
		}
		
		try {
			/*
			 * La quinta eccezione testata è quella relativa alla creazione di una richiesta
			 * di scarico con il magazzino di scarico vuoto
			 */
			ManagerImpl.getManager().emptyWarehouse("Direttore");
			fail("No exception here!");
		} catch(WrongNeededQuantityException | AnotherEmptyRequestIsPresentException e) {
			assertEquals(WrongNeededQuantityException.class, e.getClass());
			assertTrue(ManagerImpl.getManager().showTrainInfo().getLoadingRequests().isEmpty());
		}
		
		//A questo punto aggiungiamo manualmente del materiale per testare un'altra eccezione
		primoDirettore.getFactory().getUnloadingWarehouse().addMaterial(100);
		
		try {
			/*
			 * La sesta eccezione testata è quella relativa alla creazione di più di una richiesta
			 * di scarico alla volta, come da documentazione: il magazzino può essere svuotato
			 * solo una volta prima del passaggio del treno, per poi tornare ad essere nuovamente
			 * svuotabile
			 */
			ManagerImpl.getManager().emptyWarehouse("Direttore");
			ManagerImpl.getManager().emptyWarehouse("Direttore");
			fail("No exception here!");
		} catch(WrongNeededQuantityException | AnotherEmptyRequestIsPresentException e) {
			assertEquals(AnotherEmptyRequestIsPresentException.class, e.getClass());
			assertEquals(1, ManagerImpl.getManager().showTrainInfo().getLoadingRequests().size());
		}
		
		//Per testare l'ultima eccezione necessitiamo di un altro direttore
		Material secondoMateriale		= new MaterialImpl("Lavorato", "PostLavorato");
		Factory secondaAzienda			= new FactoryImpl("SecondaAz", secondoMateriale, 20, 400, 400);
		Director secondoDirettore 		= new DirectorImpl("SecondoDir", secondaAzienda);
		ManagerImpl.getManager().hireDirector(secondoDirettore);
		
		//Il suo compito sarà quello di creare due richieste simili
		ManagerImpl.getManager().createNewRequest(50, "SecondoDir");
		ManagerImpl.getManager().createNewRequest(50, "SecondoDir");
		assertEquals(2, primoDirettore.getRequestsToSatisfy().size());
		
		//A questo punto salviamo l'id di una delle due richieste
		int requestId = primoDirettore.getRequestsToSatisfy().stream().findFirst().get().getRequestId();
		
		try {
			//E facciamo soddisfare al direttore la stessa richiesta collegata all'id salvato
			primoDirettore.satisfyRequest(primoDirettore.getRequestsToSatisfy().stream()
																			   .filter(r -> r.getRequestId() == (requestId))
																			   .findFirst()
																			   .get());
			//Accertandosi che sia stato fatto
			assertEquals(requestId, primoDirettore.getAcceptedRequest().getRequestId());
			assertEquals(1, primoDirettore.getRequestsToSatisfy().size());
			
			/*
			 * La settima eccezione testata è quella relativa all'accettazione di due richieste
			 * in contemporanea, senza aver terminato di soddisfare la prima accettata
			 */
			primoDirettore.satisfyRequest(primoDirettore.getRequestsToSatisfy().stream().findFirst().get());
			fail("No exception here!");
		} catch(AnotherAcceptedRequestException e) {
			assertEquals(1, primoDirettore.getRequestsToSatisfy().size());
			assertEquals(requestId, primoDirettore.getAcceptedRequest().getRequestId());
		}
	}
}
