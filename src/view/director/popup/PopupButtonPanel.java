package view.director.popup;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.classes.ManagerImpl;

public class PopupButtonPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JButton confirmButton;
	private JButton cancelButton;
	
	public PopupButtonPanel(final String directorName, final JTextField requiredAmount) {
	/*	//JDialog requestPopup = (JDialog)this.get;
		
		this.createPopupButtonPanel(requestPopup, event -> {
			try {
				ManagerImpl.getManager(0).createNewRequest(Integer.valueOf(requiredAmount.getText()),
														   directorName);
				requestPopup.dispose();
				
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(requestPopup, "The entered value is not an integer!");
			}
		});
	*/	
	}
	
	public PopupButtonPanel(final String directorName) {
		JDialog requestPopup = (JDialog)this.getParent();
		
		this.createPopupButtonPanel(requestPopup, event -> {
			ManagerImpl.getManager(0).showDirectorInfo(directorName).emptyWarehouse();
			requestPopup.dispose();
		});
	}
	
	private void createPopupButtonPanel(final JDialog requestPopup, final ActionListener createEvent) {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		this.confirmButton = new PopupButton("Create", createEvent);
		
		this.cancelButton = new PopupButton("Cancel", event -> {
			requestPopup.dispose();
		});
		
		
		this.add(cancelButton);
		this.add(confirmButton);
	}
}
