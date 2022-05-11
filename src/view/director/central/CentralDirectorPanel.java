package view.director.central;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.classes.ManagerImpl;

public class CentralDirectorPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JPanel leftTinyDirectorPanel;
	private JPanel rightTinyDirectorPanel;
	
	public CentralDirectorPanel(String directorName) {
		this.setLayout(new BorderLayout());
		
		this.leftTinyDirectorPanel = new TinyDirectorPanel(ManagerImpl.getManager(0).showFactoryInfo(directorName).getLoadingWarehouse(), "Loading Warehouse");
		this.rightTinyDirectorPanel = new TinyDirectorPanel(ManagerImpl.getManager(0).showFactoryInfo(directorName).getUnloadingWarehouse(), "Unloading Warehouse");
		
		this.add(this.leftTinyDirectorPanel);
		this.add(this.rightTinyDirectorPanel);
	}

}
