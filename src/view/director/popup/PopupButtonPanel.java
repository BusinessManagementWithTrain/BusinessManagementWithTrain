package view.director.popup;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.classes.ManagerImpl;

public class PopupButtonPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JButton confirmButton;
	private final JButton cancelButton;
	
	public PopupButtonPanel(final LoadingRequestPopup requestPopup, final String directorName, final JTextField requiredAmount) {
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		this.confirmButton = new PopupButton("Create", event -> {
			try {
				ManagerImpl.getManager(0).createNewRequest(Integer.valueOf(requiredAmount.getText()),
														   directorName);
				System.out.println(ManagerImpl.getManager(0).showRequestInfo(0));
				requestPopup.dispose();
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(requestPopup, "The entered value is not an integer!");
			}
		});
		
		this.cancelButton = new PopupButton("Cancel", event -> {
			requestPopup.dispose();
		});
		
		
		this.add(cancelButton);
		this.add(confirmButton);
	}
}
