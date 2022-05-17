package view.director.popup.loadingRequest;

import javax.swing.JTextField;

import controller.classes.ManagerImpl;
import view.director.popup.PopupButtonPanel;
import view.director.popup.RequestPopup;

public class LoadingRequestPopup extends RequestPopup {
	private static final long serialVersionUID = 1L;

	private final static JTextField REQUIRED_AMOUNT = new LoadingRequestTextField("Required Amount: ");

	public LoadingRequestPopup(String directorName) {
		super(directorName,
			  new LoadingRequestLabel("Required Material: ", "" + ManagerImpl.getManager(0).showFactoryInfo(directorName).getMaterial()),
			  REQUIRED_AMOUNT,
			  new PopupButtonPanel(directorName, REQUIRED_AMOUNT));
	}
}