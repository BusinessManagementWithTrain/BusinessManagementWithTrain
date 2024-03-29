package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import controller.classes.ManagerImpl;
import exceptions.EmptyDestinationsQueueException;
import exceptions.EmptyWarehouseException;
import exceptions.FullTrainException;
import exceptions.FullWarehouseException;
import exceptions.LowTrainCapacityException;
import model.classes.DirectorImpl;
import model.classes.FactoryImpl;
import model.classes.MaterialImpl;
import model.classes.StoreImpl;
import model.interfaces.Director;
import model.interfaces.Factory;
import model.interfaces.Material;
import model.interfaces.Request;

public class TrainTest {

	/*
	 * I test sul treno si focalizzano principalmente nel verificare
	 * il corretto funzionamento dei metodi e soprattutto 
	 * nel controllare l'utilizzo corretto delle eccezioni.
	 */
	
	public void trainTests() {
		try {
			/*
			 * In quasi tutte le creazioni del manager notiamo che la capienza del treno viene 
			 * associata sempre a 1000, questo per evitare di incorrere nell'eccezione
			 */
			
			//Creiamo un nuovo manager ed invochiamo la prima batteria di test
			ManagerImpl.getManager(1000);
			generalTrainTest();
			
			/*
			 * Lanciamo una seconda batteria di test senza resettare le variabili,
			 * lo faremo in seguito
			 */
			firstExceptionTrainTest();
			
			//Resettiamo le variabili e lanciamo una terza batteria di test
			ManagerImpl.getManager(1000);
			fullWarehouseExceptionTrainTest();
			
			//Resettiamo le variabili e lanciamo una quarta batteria di test
			ManagerImpl.getManager(1000);
			emptyWarehouseExceptionTrainTest();
			

		} catch (Exception e) {
			//Se arriviamo qui qualcosa è andato storto
			e.printStackTrace();
			fail("Unexpected exception!");
		}
	}

	private void generalTrainTest() throws Exception {

		/*
		 * Nella prima batteria andremo a testare le associazioni corrette da parte
		 * dell'applicativo e, successivamente, andremo a verificare il corretto
		 * funzionamento dei metodi principali
		 */
		
		//Iniziamo verificando le associazioni
		assertEquals(1000, ManagerImpl.getManager().showTrainInfo().getMaxCapacity());
		assertEquals(0, ManagerImpl.getManager().showTrainInfo().getCurrentCapacity());
		assertNull(ManagerImpl.getManager().showTrainInfo().getCurrentDestination());
		
		//Creiamo due direttori che ci permetteranno di creare delle richieste
		Material primoMateriale			= new MaterialImpl("Grezzo", "Lavorato");
		Factory aziendaDestinatario		= new FactoryImpl("PrimaAz", primoMateriale, 20, 400, 400);
		Director direttoreDestinatario	= new DirectorImpl("PrimoDir", aziendaDestinatario);
		ManagerImpl.getManager().hireDirector(direttoreDestinatario);
		
		Material secondoMateriale		= new MaterialImpl("Lavorato", "PostLavorato");
		Factory aziendaMittente			= new FactoryImpl("SecondaAz", secondoMateriale, 20, 500, 500);
		Director direttoreMittente 		= new DirectorImpl("SecondoDir", aziendaMittente);
		ManagerImpl.getManager().hireDirector(direttoreMittente);
		
		//Creiamo e soddisfiamo la richiesta, riempiendo il magazzino del mittente
		Request richiesta = direttoreMittente.newRequest(50);
		direttoreDestinatario.addRequestToSatisfy(richiesta);
		ManagerImpl.getManager().satisfiesRequestDirector(richiesta, "PrimoDir");
		direttoreDestinatario.getFactory().getUnloadingWarehouse().addMaterial(50);
		
		//Verifichiamo che la richiesta sia effettivamente arrivata al treno
		assertTrue(ManagerImpl.getManager().showTrainInfo().getLoadingRequests().contains(richiesta));
		
		/*
		 * Invochiamo il metodo nextDestination aspettandoci che:
		 * 	- La tappa attuale diventi l'azienda destinatario;
		 * 	- La richiesta diventi una richiesta di scarico;
		 * 	- La richiesta venga rimossa dalle richieste di carico;
		 * 	- 
		 */
		ManagerImpl.getManager().nextDestination();
		assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination().equals(aziendaDestinatario));
		assertTrue(ManagerImpl.getManager().showTrainInfo().getUnloadingRequests().contains(richiesta));
		assertFalse(ManagerImpl.getManager().showTrainInfo().getLoadingRequests().contains(richiesta));
		assertNull(direttoreDestinatario.getAcceptedRequest());
		assertEquals(50, ManagerImpl.getManager().showTrainInfo().getCurrentCapacity());
		assertEquals(50, ManagerImpl.getManager().showTrainInfo().getStuffMap().get("Lavorato"));
		assertEquals(50, ManagerImpl.getManager().showTrainInfo().getQuantitytoLoad());
		assertEquals(0, aziendaDestinatario.getUnloadingWarehouse().getCurrentCapacity());
		
		ManagerImpl.getManager().nextDestination();
		assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination().equals(aziendaMittente));
		assertFalse(ManagerImpl.getManager().showTrainInfo().getUnloadingRequests().contains(richiesta));
		assertEquals(0, ManagerImpl.getManager().showTrainInfo().getCurrentCapacity());
		assertEquals(0, ManagerImpl.getManager().showTrainInfo().getStuffMap().get("Lavorato"));
		assertEquals(50, ManagerImpl.getManager().showTrainInfo().getQuantitytoUnload());
		assertEquals(50, aziendaMittente.getLoadingWarehouse().getCurrentCapacity());
		
		ManagerImpl.getManager().showTrainInfo().addRequest(richiesta);
		assertTrue(ManagerImpl.getManager().showTrainInfo().getLoadingRequests().contains(richiesta));
		assertTrue(ManagerImpl.getManager().showTrainInfo().getLoadingRequests().stream().findFirst().get().equals(richiesta));
	}
	
	private void firstExceptionTrainTest() throws Exception {
		/*
		 * Nella seconda batteria di test andremo a verificare le corrette invocazioni
		 * delle prime eccezioni create ad hoc
		 */
		try {
			/*
			 * La prima eccezione testata è quella relativa alla creazione di un treno
			 * con capienza inferiore alle 100 unità (valore decretato da noi)
			 */
			ManagerImpl.getManager(0);
		} catch(LowTrainCapacityException e) {
			assertNull(ManagerImpl.getManager());
		}
		
		ManagerImpl.getManager(1000);
		
		/*
		 * Proseguiamo creando tre aziende la cui dimensione dei magazzini di carico ci permetterà
		 * di testare efficacemente le prime eccezioni
		 */
		Material primoMateriale			= new MaterialImpl("Grezzo", "Lavorato");
		Factory primaAzienda			= new FactoryImpl("PrimaAz", primoMateriale, 20, 400, 1000);
		Director primoDirettore		 	= new DirectorImpl("PrimoDir", primaAzienda);
		ManagerImpl.getManager().hireDirector(primoDirettore);
		
		Material secondoMateriale		= new MaterialImpl("Lavorato", "PostLavorato");
		Factory secondaAzienda			= new FactoryImpl("SecondaAz", secondoMateriale, 20, 1000, 500);
		Director secondoDirettore 		= new DirectorImpl("SecondoDir", secondaAzienda);
		ManagerImpl.getManager().hireDirector(secondoDirettore);
		
		Material terzoMateriale			= new MaterialImpl("Grezzo2", "Lavorato");
		Factory terzaAzienda			= new FactoryImpl("TerzaAz", terzoMateriale, 20, 400, 400);
		Director terzoDirettore 		= new DirectorImpl("TerzoDir", terzaAzienda);
		ManagerImpl.getManager().hireDirector(terzoDirettore);
		
		try {
			/*
			 * La seconda eccezione testata è quella relativa al tentativo di far muovere
			 * il treno senza aver prima inserito una tappa
			 */
			ManagerImpl.getManager().nextDestination();
			fail("No exception here!");
		} catch(EmptyDestinationsQueueException e) {
			assertNull(ManagerImpl.getManager().showTrainInfo().getCurrentDestination());
		}
		
		try {
			/*
			 * La terza eccezione testata è quella relativa al tentato sovraccarico
			 * del treno
			 */
			Request richiesta 			= secondoDirettore.newRequest(1000);
			primoDirettore.addRequestToSatisfy(richiesta);
			ManagerImpl.getManager().satisfiesRequestDirector(richiesta, "PrimoDir");
			primoDirettore.getFactory().getUnloadingWarehouse().addMaterial(1000);
		
			richiesta 					= secondoDirettore.newRequest(100);
			terzoDirettore.addRequestToSatisfy(richiesta);
			ManagerImpl.getManager().satisfiesRequestDirector(richiesta, "TerzoDir");
			terzoDirettore.getFactory().getUnloadingWarehouse().addMaterial(100);
			
			ManagerImpl.getManager().nextDestination();
			ManagerImpl.getManager().nextDestination();
			
			fail("No exception here!");
			
		} catch(FullTrainException e) {
			/*
			 * Riscontrata l'eccezione però dobbiamo testare che il programma si comporti
			 * come specificato nella documentazione:
			 * 	- Caricare più materiale possibile nel treno;
			 * 	- Riaggiungere la tappa attuale in fondo alla lista;
			 * 	- Riprovare a soddisfare le richieste rimanenti in un secondo momento
			 */
			assertEquals(100, terzoDirettore.getFactory().getUnloadingWarehouse().getCurrentCapacity());
			assertEquals(1000, ManagerImpl.getManager().showTrainInfo().getCurrentCapacity());
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination().equals(terzaAzienda));
			
			ManagerImpl.getManager().nextDestination();
			ManagerImpl.getManager().nextDestination();
			
			assertEquals(0, terzoDirettore.getFactory().getUnloadingWarehouse().getCurrentCapacity());
			assertEquals(100, ManagerImpl.getManager().showTrainInfo().getCurrentCapacity());
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination().equals(terzaAzienda));
		}
	}
	
	private void fullWarehouseExceptionTrainTest() throws Exception {
		
		/*
		 * Nella terza batteria di test andremo a verificare la corretta invocazione
		 * dell'eccezione "FullWarehouseException"
		 */
		
		/*
		 * Iniziamo creando tre aziende di cui due con i magazzini uguali
		 * ed una con i magazzini leggermente più grandi delle altre due
		 */
		Material primoMateriale			= new MaterialImpl("Grezzo", "Lavorato");
		Factory primaAzienda			= new FactoryImpl("PrimaAz", primoMateriale, 20, 400, 400);
		Director primoDirettore		 	= new DirectorImpl("PrimoDir", primaAzienda);
		ManagerImpl.getManager().hireDirector(primoDirettore);
		
		Material secondoMateriale		= new MaterialImpl("Lavorato", "PostLavorato");
		Factory secondaAzienda			= new FactoryImpl("SecondaAz", secondoMateriale, 20, 500, 500);
		Director secondoDirettore 		= new DirectorImpl("SecondoDir", secondaAzienda);
		ManagerImpl.getManager().hireDirector(secondoDirettore);
		
		Material terzoMateriale			= new MaterialImpl("Grezzo2", "Lavorato");
		Factory terzaAzienda			= new FactoryImpl("TerzaAz", terzoMateriale, 20, 400, 400);
		Director terzoDirettore 		= new DirectorImpl("TerzoDir", terzaAzienda);
		ManagerImpl.getManager().hireDirector(terzoDirettore);
		
		try {
			/*
			 * A questo punto andiamo a creare due richieste la cui somma supera la capienza
			 * del magazzino di carico dell'azienda richiedente
			 */
			Request richiesta	 		= secondoDirettore.newRequest(400);
			primoDirettore.addRequestToSatisfy(richiesta);
			ManagerImpl.getManager().satisfiesRequestDirector(richiesta, "PrimoDir");
			primoDirettore.getFactory().getUnloadingWarehouse().addMaterial(400);
			
			richiesta = secondoDirettore.newRequest(200);
			terzoDirettore.addRequestToSatisfy(richiesta);
			ManagerImpl.getManager().satisfiesRequestDirector(richiesta, "TerzoDir");
			terzoDirettore.getFactory().getUnloadingWarehouse().addMaterial(200);
			
			assertEquals(2, ManagerImpl.getManager().showTrainInfo().getLoadingRequests().size());
			
			//Proseguiamo facendo spostare il treno fino alla tappa desiderata
			ManagerImpl.getManager().nextDestination();
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination().equals(primaAzienda));
			assertEquals(400, ManagerImpl.getManager().showTrainInfo().getCurrentCapacity());
			assertEquals(0, primaAzienda.getUnloadingWarehouse().getCurrentCapacity());
			assertEquals(1, ManagerImpl.getManager().showTrainInfo().getUnloadingRequests().size());
			
			ManagerImpl.getManager().nextDestination();
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination().equals(terzaAzienda));
			assertEquals(600, ManagerImpl.getManager().showTrainInfo().getCurrentCapacity());
			assertEquals(0, terzaAzienda.getUnloadingWarehouse().getCurrentCapacity());
			assertEquals(2, ManagerImpl.getManager().showTrainInfo().getUnloadingRequests().size());
			
			ManagerImpl.getManager().nextDestination();
			
			fail("No exception here!");
		} catch(FullWarehouseException e) {
			/*
			 * Riscontrata l'eccezione però dobbiamo testare che il programma si comporti
			 * come specificato nella documentazione:
			 * 	- Scaricare più materiale possibile nel magazzino di scarico;
			 * 	- Aggiungere il negozio alle tappe da raggiungere;
			 */
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination().equals(secondaAzienda));
			
			ManagerImpl.getManager().nextDestination();
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination().equals(StoreImpl.getStoreInstance()));
			assertEquals(0, ManagerImpl.getManager().showTrainInfo().getCurrentCapacity());
		}
	}
	
	private void emptyWarehouseExceptionTrainTest() throws Exception {
		
		/*
		 * Nella terza batteria di test andremo a verificare la corretta invocazione
		 * dell'eccezione "EmptyWarehouseException"
		 */
		
		//Andiamo a creare le aziende di cui necessiteremo per il test
		Material primoMateriale			= new MaterialImpl("Grezzo", "Lavorato");
		Factory primaAzienda			= new FactoryImpl("PrimaAz", primoMateriale, 20, 400, 400);
		Director primoDirettore		 	= new DirectorImpl("PrimoDir", primaAzienda);
		ManagerImpl.getManager().hireDirector(primoDirettore);
		
		Material secondoMateriale		= new MaterialImpl("Lavorato", "PostLavorato");
		Factory secondaAzienda			= new FactoryImpl("SecondaAz", secondoMateriale, 20, 500, 500);
		Director secondoDirettore 		= new DirectorImpl("SecondoDir", secondaAzienda);
		ManagerImpl.getManager().hireDirector(secondoDirettore);
		
		Material terzoMateriale			= new MaterialImpl("Grezzo2", "Lavorato");
		Factory terzaAzienda			= new FactoryImpl("TerzaAz", terzoMateriale, 20, 400, 400);
		Director terzoDirettore 		= new DirectorImpl("TerzoDir", terzaAzienda);
		ManagerImpl.getManager().hireDirector(terzoDirettore);
		
		try {
			/*
			 * A questo punto andiamo a creare due richieste ma facciamo in modo che
			 * solamente ad un azienda possa soddisfarne una
			 */
			Request richiesta = secondoDirettore.newRequest(200);	
			primoDirettore.addRequestToSatisfy(richiesta);
			ManagerImpl.getManager().satisfiesRequestDirector(richiesta, "PrimoDir");
			primoDirettore.getFactory().getUnloadingWarehouse().addMaterial(200);
			
			richiesta = secondoDirettore.newRequest(200);
			terzoDirettore.addRequestToSatisfy(richiesta);
			ManagerImpl.getManager().satisfiesRequestDirector(richiesta, "TerzoDir");
		
			//Proseguiamo facendo spostare il treno fino alla tappa desiderata
			ManagerImpl.getManager().nextDestination();
			ManagerImpl.getManager().nextDestination();
			
			fail("No exception here!");
		} catch(EmptyWarehouseException e) {
			/*
			 * Riscontrata l'eccezione però dobbiamo testare che il programma si comporti
			 * come specificato nella documentazione:
			 * 	- Caricare più materiale possibile nel treno;
			 * 	- Riaggiungere la tappa attuale in fondo alla lista;
			 * 	- Riprovare a soddisfare le richieste rimanenti in un secondo momento
			 */
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination().equals(terzaAzienda));
			
			ManagerImpl.getManager().nextDestination();
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination().equals(secondaAzienda));
			terzoDirettore.getFactory().getUnloadingWarehouse().addMaterial(200);
			
			ManagerImpl.getManager().nextDestination();
			assertTrue(ManagerImpl.getManager().showTrainInfo().getCurrentDestination().equals(terzaAzienda));
		}
	}
}
