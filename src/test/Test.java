package test;


/**
 * Breve descrizione dei test
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

