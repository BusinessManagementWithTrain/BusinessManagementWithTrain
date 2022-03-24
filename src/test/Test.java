package test;

import static org.junit.Assert.*;
import java.util.*;
import java.util.concurrent.ForkJoinPool.ManagedBlocker;

import controller.interfaces.Manager;
import model.interfaces.Director;
import model.interfaces.Material;
import model.interfaces.Request;

/**
 * Breve descrizione dei test
 * 
 * @author Grasso Emanuele
 */

public class Test {
	
	private Manager manager;
	
	private Manager createManager() {
		//Inizializzazione manager
		//Inizializzazione di tutto ciò che serve per la creazione del progetto vuoto
		
		return null;
	}
	
	@org.junit.Test
	private void testStart() {
		this.manager = createManager();
		
	}
	
	@org.junit.Test
	private void managerTest() {
		
	}

	@org.junit.Test
	private void agencyTest() {
		
	}
	
	@org.junit.Test
	private void directorTest() {
		Director aldoDirector;
		Director pietroDirector;
		Director giovanniDirector;
	}
	
	@org.junit.Test
	private void materialTest() {
		Material stone;
		Material sand;
		Material glass;
	}
	
	@org.junit.Test
	private void requestTest() {
		Request firstRequest;
		Request secondRequest;
	}

	@org.junit.Test
	private void staffTest() {
		
	}
	
	@org.junit.Test
	private void storeTest() {
		
	}
	
	@org.junit.Test
	private void trainTest() {
		
	}
	
	@org.junit.Test
	private void warehouseTest() {
		
	}
}
