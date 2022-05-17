package view.director.central;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import controller.classes.ManagerImpl;
import model.interfaces.Factory;
import model.interfaces.Warehouse;
import view.director.popup.loadingRequest.LoadingRequestPopup;
import view.director.popup.unloadingRequest.UnloadingRequestPopup;

public class CentralDirectorPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JPanel leftTinyDirectorPanel;
	private JPanel rightTinyDirectorPanel;
	
	public CentralDirectorPanel(String directorName) {
		Warehouse loadingWarehouse = getFactory(directorName).getLoadingWarehouse();
		Warehouse unloadingWarehouse = getFactory(directorName).getUnloadingWarehouse();
		
		this.setLayout(new BorderLayout());
		
		this.leftTinyDirectorPanel = new TinyDirectorPanel(loadingWarehouse.getCurrentCapacity() + "/" + loadingWarehouse.getTotalCapacity(),
														   "A"/*loadingWarehouse.getMaterial()*/,
														   "Loading Warehouse",
														   "Create Loading Request",
														   event -> {
															   new LoadingRequestPopup(directorName);
														   });
		this.rightTinyDirectorPanel = new TinyDirectorPanel(unloadingWarehouse.getCurrentCapacity() + "/" + unloadingWarehouse.getTotalCapacity(),
															"A"/*unloadingWarehouse.getMaterial()*/,
															"Unloading Warehouse",
															"Create Unloading Request",
															event -> {
																new UnloadingRequestPopup(directorName);
															});
		
		this.add(this.leftTinyDirectorPanel, BorderLayout.LINE_START);
		this.add(this.rightTinyDirectorPanel, BorderLayout.LINE_END);
	}
	
	private Factory getFactory(String directorName) {
		return ManagerImpl.getManager(0).showFactoryInfo(directorName);
	}

}
