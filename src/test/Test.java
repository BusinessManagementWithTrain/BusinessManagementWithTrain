package test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import controller.classes.ManagerImpl;
import controller.interfaces.Manager;
import exceptions.EqualMaterialException;
import exceptions.LowTrainCapacityException;
import model.classes.DirectorImpl;
import model.classes.FactoryImpl;
import model.classes.MaterialImpl;
import model.classes.RequestImpl;
import model.interfaces.Director;
import model.interfaces.Factory;
import model.interfaces.Material;
import model.interfaces.Request;
import model.interfaces.Warehouse;

/**
 * Breve descrizione dei test
 * 
 * @author Grasso Emanuele
 */

public class Test {
	
	@org.junit.Test
	public void startTest() {
		try {
			ManagerImpl.getManager(1000);
		} catch (LowTrainCapacityException e) {
			e.printStackTrace();
		}
	}

	
	@org.junit.Test
	public void directorTest() {
		
	}
	
	@org.junit.Test
	public void factoryTest() {
		
	}
	
	@org.junit.Test
	public void materialTest() {
		
	}
	
	@org.junit.Test
	public void requestTest() {
		
	}
	
	@org.junit.Test
	public void staffTest() {
		
	}
	
	@org.junit.Test
	public void storeTest() {
		
	}
	
	@org.junit.Test
	public void trainTest() {
		
	}
	
	@org.junit.Test
	public void warehouseTest() {
		
	}
	
	@org.junit.Test
	public void exceptionTests() {
		
	}
	
	@org.junit.Test
	public void anotherAcceptedRequestExceptionTest() {
		
	}
	
	@org.junit.Test
	public void emptyDestinationSetExceptionTest() {
		
	}
	
	@org.junit.Test
	public void emptyFieldExceptionTest() {
		
	}
	
	@org.junit.Test
	public void equalMaterialExceptionTest() {
		
	}
	
	@org.junit.Test
	public void emptyWarehouseExceptionTest() {
		
	}
	
	@org.junit.Test
	public void fullTrainExceptionTest() {
		
	}
	
	@org.junit.Test
	public void fullWarehouseExceptionTest() {
		
	}
	
	@org.junit.Test
	public void lowTrainCapacityExceptionTest() {
		
	}
	
	@org.junit.Test
	public void maximumCharactersExceptionTest() {
		
	}
	
	@org.junit.Test
	public void staffIsAlreadyNotWorkingExceptionTest() {
		
	}

	@org.junit.Test
	public void staffIsAlreadyWorkingExceptionTest() {
		
	}
	
	@org.junit.Test
	public void wrongNeededQuantityExceptionTest() {
		
	}
}
