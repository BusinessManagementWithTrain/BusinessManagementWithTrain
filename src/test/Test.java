package test;


/**
 * Test effettuati sfruttando il framework JUnit.
 * Come si può notare dalla composizione di questa classe, viene invocata
 * una classe separata per ogni componente da testare, con relativo messaggio
 * di conferma in caso di test superato.
 * 
 * Ogni classe di test conterrà al suo interno le batterie di test
 * ritenute necessarie per ottenere un corretto feedback da parte
 * dell'applicativo.
 * 
 * @author Grasso Emanuele
 */
public class Test {
	
	@org.junit.Test
	public void testStart() {
		new ManagerTest().managerTests();
		System.out.println("Manager tests passed succesfully!\n");
		
		new DirectorTest().directorTests();
		System.out.println("Director tests passed succesfully!\n");
		
		new FactoryTest().factoryTests();
		System.out.println("Factory tests passed succesfully!\n");
		
		new MaterialTest().materialTests();
		System.out.println("Material tests passed succesfully!\n");
		
		new RequestTest().requestTests();
		System.out.println("Request tests passed succesfully!\n");
		
		new StaffTest().staffTests();
		System.out.println("Staff tests passed succesfully!\n");
		
		new StoreTest().storeTests();
		System.out.println("Store tests passed succesfully!\n");
		
		new TrainTest().trainTests();
		System.out.println("Train tests passed succesfully!\n");
		
		new UniqueQueueTest().uniqueQueueTests();
		System.out.println("Unique Queue tests passed succesfully!\n");
		
		new WarehouseTest().warehouseTests();
		System.out.println("Warehouse tests passed succesfully!\n");
	}
}

