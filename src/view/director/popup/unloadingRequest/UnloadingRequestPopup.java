package view.director.popup.unloadingRequest;

import controller.classes.ManagerImpl;
import view.director.popup.PopupButtonPanel;
import view.director.popup.RequestPopup;

public class UnloadingRequestPopup extends RequestPopup {
	private static final long serialVersionUID = 1L;
	
	public UnloadingRequestPopup(String directorName) {
		super(directorName,
			  new UnloadingRequestLabel("Sent Material: ", "" + ManagerImpl.getManager(0).showFactoryInfo(directorName).getMaterial()),
			  new UnloadingRequestLabel("Sent Amount: ",   "" + ManagerImpl.getManager(0).showFactoryInfo(directorName).getUnloadingWarehouse().getCurrentCapacity()),
			  new PopupButtonPanel(directorName));
	}
}
