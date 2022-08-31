package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;


import controller.classes.ManagerImpl;
import exceptions.AnotherAcceptedRequestException;
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
import model.interfaces.Director;
import model.interfaces.Factory;
import model.interfaces.Material;
import model.interfaces.Request;

public class ManagerTest {
	
	/*
	 * I test sul manager si focalizzano principalmente sul controllare
	 * la corretta gestione dell'applicativo, principalmente i movimenti
	 * delle richieste tra i vari direttori.
	 */
	
	public void managerTests() {
		try {
			/*
			 * In tutte le creazioni del manager notiamo che la capienza del treno viene 
			 * associata sempre a 1000, questo per evitare di incorrere nell'eccezione
			 */
			
			//Creiamo un nuovo manager ed invochiamo la prima batteria di test
			ManagerImpl.getManager(1000);
			generalManagerOneDirectorTest();
			
			//Resettiamo le variabili e lanciamo una seconda batteria di test
			ManagerImpl.getManager(1000);
			generalManagerTwoDirectorsTest();
			
			//Resettiamo le variabili e lanciamo una terza batteria di test
			ManagerImpl.getManager(1000);
			generalManagerMoreThanTwoDirectorsTest();
		} catch (Exception e) {
			//Se arriviamo qui qualcosa è andato storto
			e.printStackTrace();
			fail("Unexpected exception!");
		}
	}
	
	private void generalManagerOneDirectorTest() throws EqualsMaterialsException, EmptyFieldException, MaximumCharactersException, WrongStaffValueException, WrongWarehouseCapacityException, FullWarehouseException, WrongNeededQuantityException, DirectorIsAlreadyPresentException {
		
		/*
		 * Nella prima batteria di test andremo a verificare la corretta gestione
		 * relativa ad un unico direttore
		 */
		
		//Iniziamo creando ed assumendo il direttore
		Material primoMateriale			= new MaterialImpl("Grezzo", "Lavorato");
		Factory primaAzienda 			= new FactoryImpl("PrimaAz", primoMateriale, 20, 200, 200);
		Director primoDirettore 		= new DirectorImpl("PrimoDir", primaAzienda);	
		ManagerImpl.getManager().hireDirector(primoDirettore);
		
		//Verifichiamo la corretta assunzione dello stesso
		assertEquals(1, ManagerImpl.getManager().getLinkDirectors().size());
		assertTrue(ManagerImpl.getManager().getLinkDirectors().contains(primoDirettore));
		assertEquals(primoDirettore, ManagerImpl.getManager().showDirectorInfo("PrimoDir"));
		assertSame(primoDirettore, ManagerImpl.getManager().showDirectorInfo("PrimoDir"));
		
		//Simuliamo la creazione di una richiesta da parte del direttore
		Request richiesta 				= ManagerImpl.getManager().showDirectorInfo("PrimoDir").newRequest(10);
		
		/*
		 * Controlliamo che, essendoci un unico direttore, la possibilità di
		 * soddisfare tale richiesta non può che ricadere sul manager
		 */
		ManagerImpl.getManager().sendRequest(richiesta);
		assertTrue(ManagerImpl.getManager().getlinkRequestsManager().contains(richiesta));
		assertEquals(1, ManagerImpl.getManager().getlinkRequestsManager().size());
			
		/*
		 * Verichiamo che soddisfando la richiesta vengano effettivamente svolti
		 * tutti i passaggi necessari
		 */
		ManagerImpl.getManager().satisfiesRequestManager(richiesta);
		assertTrue(ManagerImpl.getManager().getlinkRequestsManager().isEmpty());
		assertEquals(10, ManagerImpl.getManager().showFactoryInfo("PrimoDir").getLoadingWarehouse().getCurrentCapacity());
	
		//Rimettiamo in circolo la richiesta per effettuare il test finale
		ManagerImpl.getManager().sendRequest(richiesta);
		assertTrue(ManagerImpl.getManager().getlinkRequestsManager().contains(richiesta));
		assertEquals(1, ManagerImpl.getManager().getlinkRequestsManager().size());
		
		/*
		 * Andiamo a licenziare il direttore controllando che tutti i parametri vengano aggiornati, ossia:
		 * 	- Il direttore viene rimosso dalla lista dei direttori del manager;
		 * 	- Le richieste create vengono rimosse dall'applicativo;
		 */
		ManagerImpl.getManager().fireDirector("PrimoDir");
		assertTrue(ManagerImpl.getManager().getLinkDirectors().isEmpty());
		assertFalse(ManagerImpl.getManager().getlinkRequestsManager().contains(richiesta));
		assertTrue(ManagerImpl.getManager().getlinkRequestsManager().isEmpty());
	}
	
	private void generalManagerTwoDirectorsTest() throws EqualsMaterialsException, EmptyFieldException, MaximumCharactersException, WrongStaffValueException, WrongWarehouseCapacityException, AnotherAcceptedRequestException, DirectorIsAlreadyPresentException {
		
		/*
		 * Nella seconda batteria di test andremo a verificare la corretta gestione
		 * relativa a due direttori distinti, collegati dal materiale
		 */

		/*
		 * Iniziamo creando ed assumendo i direttori notando che:
		 * 	- Il materiale di cui necessita il secondo direttore è lo stesso prodotto dal primo direttore, ossia "PrimLavorato";
		 * 	- I magazzini del secondo direttore sono 100 unità più grandi dei magazzini del primo direttore;
		 */
		Material primoMateriale 		= new MaterialImpl("PrimGrezzo", "PrimLavorato");
		Factory primaAzienda 			= new FactoryImpl("PrimaAz", primoMateriale, 20, 200, 200);
		Director primoDirettore 		= new DirectorImpl("PrimoDir", primaAzienda);
			
		Material secondoMateriale 		= new MaterialImpl("PrimLavorato", "SecLavorato");
		Factory secondaAzienda 			= new FactoryImpl("SecondaAz", secondoMateriale, 20, 300, 300);
		Director secondoDirettore 		= new DirectorImpl("SecondoDir", secondaAzienda);
			
		ManagerImpl.getManager().hireDirector(primoDirettore);
		ManagerImpl.getManager().hireDirector(secondoDirettore);
		
		//Verifichiamo la corretta assunzione dei direttori
		assertEquals(2, ManagerImpl.getManager().getLinkDirectors().size());
		assertEquals(primoDirettore, ManagerImpl.getManager().showDirectorInfo("PrimoDir"));
		assertSame(primoDirettore, ManagerImpl.getManager().showDirectorInfo("PrimoDir"));
		assertEquals(secondoDirettore, ManagerImpl.getManager().showDirectorInfo("SecondoDir"));
		assertSame(secondoDirettore, ManagerImpl.getManager().showDirectorInfo("SecondoDir"));
		
		//Simuliamo la creazione di una richiesta da parte del secondo direttore
		Request primaRichiesta 			= new RequestImpl(ManagerImpl.getManager().showFactoryInfo("SecondoDir"), "PrimLavorato", 10);
		
		/*
		 * Controlliamo che la richiesta venga smistata come previsto dalla documentazione
		 * ossia: la richiesta verrà mandata a tutti i direttori in grado di soddisfarla, qualora
		 * e solo nel caso in cui non sia presente neanche un direttore in grado di soddisfarla,
		 * questa verrà presa in carico dal manager
		 */
		ManagerImpl.getManager().sendRequest(primaRichiesta);
		assertTrue(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().contains(primaRichiesta));
		assertEquals(1, ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().size());
		assertTrue(ManagerImpl.getManager().getlinkRequestsManager().isEmpty());
		
		
		/*
		 * Simuliamo la creazione di una seconda richiesta da parte del secondo direttore
		 * in cui la quantità da spedire supera la capienza del magazzino del primo direttore
		 */
		Request secondaRichiesta 		= new RequestImpl(ManagerImpl.getManager().showFactoryInfo("SecondoDir"), "PrimLavorato", 250);
		
		//Controlliamo che la richiesta venga smistata come previsto dalla documentazione
		ManagerImpl.getManager().sendRequest(secondaRichiesta);
		assertFalse(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().contains(secondaRichiesta));
		assertEquals(1, ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().size());
		assertTrue(ManagerImpl.getManager().getlinkRequestsManager().contains(secondaRichiesta));
		assertEquals(1, ManagerImpl.getManager().getlinkRequestsManager().size());
		
		/*
		 * Verifichiamo che soddisfando la richiesta attraverso un direttore
		 * vengano svolti tutti i passaggi necessari
		 */
		ManagerImpl.getManager().satisfiesRequestDirector(primaRichiesta, "PrimoDir");
		assertTrue(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().isEmpty());
		assertEquals(primaRichiesta, ManagerImpl.getManager().showDirectorInfo("PrimoDir").getAcceptedRequest());
	
		//Rimettiamo in circolo la prima richiesta per poi effettuare il test finale
		ManagerImpl.getManager().sendRequest(primaRichiesta);
		assertTrue(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().contains(primaRichiesta));
		assertEquals(1, ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().size());
		assertEquals(1, ManagerImpl.getManager().getlinkRequestsManager().size());
		
		/*
		 * Andiamo a licenziare il secondo direttore controllando che tutti i parametri vengano aggiornati, ossia:
		 * 	- Il direttore viene rimosso dalla lista dei direttori del manager;
		 * 	- Le richieste create vengono rimosse dall'applicativo;
		 */
		ManagerImpl.getManager().fireDirector("SecondoDir");
		assertEquals(1, ManagerImpl.getManager().getLinkDirectors().size());
		assertTrue(ManagerImpl.getManager().getlinkRequestsManager().isEmpty());
		assertTrue(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().isEmpty());
		assertNull(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getAcceptedRequest());
		assertFalse(ManagerImpl.getManager().getlinkRequestsManager().contains(secondaRichiesta));
		assertTrue(ManagerImpl.getManager().getlinkRequestsManager().isEmpty());
	}
	
	private void generalManagerMoreThanTwoDirectorsTest() throws EqualsMaterialsException, EmptyFieldException, MaximumCharactersException, WrongStaffValueException, WrongWarehouseCapacityException, DirectorIsAlreadyPresentException {
		
		/*
		 * Nella terza batteria di test andremo a verificare la corretta gestione
		 * relativa a più direttori distinti, collegati dal materiale
		 */
		
		/*
		 * Iniziamo creando ed assumendo i direttori notando che:
		 * 	- Il materiale di cui necessita il terzo direttore è lo stesso prodotto dal primo e dal secondo direttore, ossia "PrimLavorato";
		 *  - Il materiale di cui necessita il primo direttore è lo stesso prodotto dal terzo direttore, ovvero "PrimGrezzo";
		 * 	- I magazzini del secondo direttore sono 100 unità più grandi dei magazzini del primo direttore;
		 * 	- I magazzini del terzo direttore sono 100 unità più grandi dei magazzini del secondo direttore;
		 */
		Material primoMateriale 		= new MaterialImpl("PrimGrezzo", "PrimLavorato");
		Factory primaAzienda 			= new FactoryImpl("PrimaAz", primoMateriale, 20, 200, 200);
		Director primoDirettore 		= new DirectorImpl("PrimoDir", primaAzienda);
			
		Material secondoMateriale 		= new MaterialImpl("SecGrezzo", "PrimLavorato");
		Factory secondaAzienda 			= new FactoryImpl("SecondaAz", secondoMateriale, 20, 300, 300);
		Director secondoDirettore 		= new DirectorImpl("SecondoDir", secondaAzienda);
		
		Material terzoMateriale 		= new MaterialImpl("PrimLavorato", "PrimGrezzo");
		Factory terzaAzienda 			= new FactoryImpl("TerzaAz", terzoMateriale, 20, 400, 400);
		Director terzoDirettore 		= new DirectorImpl("TerzoDir", terzaAzienda);
		
		ManagerImpl.getManager().hireDirector(primoDirettore);
		ManagerImpl.getManager().hireDirector(secondoDirettore);
		ManagerImpl.getManager().hireDirector(terzoDirettore);
		
		//Verifichiamo la corretta assunzione dei direttori
		assertEquals(3, ManagerImpl.getManager().getLinkDirectors().size());
		assertEquals(primoDirettore, ManagerImpl.getManager().showDirectorInfo("PrimoDir"));
		assertSame(primoDirettore, ManagerImpl.getManager().showDirectorInfo("PrimoDir"));
		assertEquals(secondoDirettore, ManagerImpl.getManager().showDirectorInfo("SecondoDir"));
		assertSame(secondoDirettore, ManagerImpl.getManager().showDirectorInfo("SecondoDir"));
		assertEquals(terzoDirettore, ManagerImpl.getManager().showDirectorInfo("TerzoDir"));
		assertSame(terzoDirettore, ManagerImpl.getManager().showDirectorInfo("TerzoDir"));
		
		/*
		 * Simuliamo la creazione di una richiesta da parte del terzo direttore soddisfabile
		 * sia dal primo che dal secondo direttore
		 */
		Request primaRichiesta 				= new RequestImpl(ManagerImpl.getManager().showFactoryInfo("TerzoDir"), "PrimLavorato", 10);	
		
		//Controlliamo che la richiesta venga smistata come previsto dalla documentazione
		ManagerImpl.getManager().sendRequest(primaRichiesta);
		assertTrue(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().contains(primaRichiesta));
		assertTrue(ManagerImpl.getManager().showDirectorInfo("SecondoDir").getRequestsToSatisfy().contains(primaRichiesta));
		assertEquals(1, ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().size());
		assertEquals(1, ManagerImpl.getManager().showDirectorInfo("SecondoDir").getRequestsToSatisfy().size());
		assertTrue(ManagerImpl.getManager().getlinkRequestsManager().isEmpty());
		
		/*
		 * Simuliamo la creazione di una richiesta da parte del terzo direttore soddisfabile
		 * solo dal secondo direttore
		 */
		Request secondaRichiesta 			= new RequestImpl(ManagerImpl.getManager().showFactoryInfo("TerzoDir"), "PrimLavorato", 250);
		
		//Controlliamo che la richiesta venga smistata come previsto dalla documentazione
		ManagerImpl.getManager().sendRequest(secondaRichiesta);
		assertFalse(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().contains(secondaRichiesta));
		assertTrue(ManagerImpl.getManager().showDirectorInfo("SecondoDir").getRequestsToSatisfy().contains(secondaRichiesta));
		assertEquals(1, ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().size());
		assertEquals(2, ManagerImpl.getManager().showDirectorInfo("SecondoDir").getRequestsToSatisfy().size());
		assertTrue(ManagerImpl.getManager().getlinkRequestsManager().isEmpty());
	
		/*
		 * Simuliamo la creazione di una richiesta da parte del terzo direttore non soddisfabile
		 * da nessun'altro direttore
		 */
		Request terzaRichiesta 						= new RequestImpl(ManagerImpl.getManager().showFactoryInfo("TerzoDir"), "PrimLavorato", 350);
		
		//Controlliamo che la richiesta venga smistata come previsto dalla documentazione
		ManagerImpl.getManager().sendRequest(terzaRichiesta);
		assertFalse(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().contains(terzaRichiesta));
		assertFalse(ManagerImpl.getManager().showDirectorInfo("SecondoDir").getRequestsToSatisfy().contains(terzaRichiesta));
		assertEquals(1, ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().size());
		assertEquals(2, ManagerImpl.getManager().showDirectorInfo("SecondoDir").getRequestsToSatisfy().size());
		assertTrue(ManagerImpl.getManager().getlinkRequestsManager().contains(terzaRichiesta));
	
		/*
		 * Simuliamo la creazione di una richiesta da parte del primo direttore soddisfabile
		 * solo dal terzo direttore
		 */
		Request quartaRichiesta 			= new RequestImpl(ManagerImpl.getManager().showFactoryInfo("PrimoDir"), "PrimGrezzo", 10);
		
		//Controlliamo che la richiesta venga smistata come previsto dalla documentazione
		ManagerImpl.getManager().sendRequest(quartaRichiesta);
		assertTrue(ManagerImpl.getManager().showDirectorInfo("TerzoDir").getRequestsToSatisfy().contains(quartaRichiesta));
		assertEquals(1, ManagerImpl.getManager().showDirectorInfo("TerzoDir").getRequestsToSatisfy().size());
		assertEquals(1, ManagerImpl.getManager().getlinkRequestsManager().size());
		assertFalse(ManagerImpl.getManager().getlinkRequestsManager().contains(quartaRichiesta));
		
		/*
		 * Andiamo a licenziare il terzo direttore controllando che tutti i parametri vengano aggiornati, ossia:
		 * 	- Il direttore viene rimosso dalla lista dei direttori del manager;
		 * 	- Le richieste create dallo stesso vengono rimosse dall'applicativo;
		 * 	- Le richieste soddisfabili dallo stesso vengono rimesse in circolo nell'applicativo;
		 */
		ManagerImpl.getManager().fireDirector("TerzoDir");
		assertEquals(2, ManagerImpl.getManager().getLinkDirectors().size());
		assertFalse(ManagerImpl.getManager().getLinkDirectors().contains(terzoDirettore));
		assertTrue(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getRequestsToSatisfy().isEmpty());
		assertNull(ManagerImpl.getManager().showDirectorInfo("PrimoDir").getAcceptedRequest());
		assertTrue(ManagerImpl.getManager().showDirectorInfo("SecondoDir").getRequestsToSatisfy().isEmpty());
		assertNull(ManagerImpl.getManager().showDirectorInfo("SecondoDir").getAcceptedRequest());
		assertTrue(ManagerImpl.getManager().getlinkRequestsManager().contains(quartaRichiesta));
		assertEquals(1, ManagerImpl.getManager().getlinkRequestsManager().size());
		
	}
}