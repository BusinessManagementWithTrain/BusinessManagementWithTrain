package view.director.popup;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;

import controller.classes.ManagerImpl;
import view.director.popup.loadingRequest.LoadingRequestLabel;

public class RequestPopup extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private static final Dimension SIZE 		= new Dimension(230, 300);
	private static final int H_GAP_COMPONENT 	= 5;
	private static final int H_GAP_BUTTON 		= 30;
	
	private final JLabel factoryName;
	
	public RequestPopup(final String directorName, final Component secondComponent, final Component thirdComponent, final Component fourthComponent) {
		this.setModal(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Create Unloading Request");
		
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		this.factoryName = new LoadingRequestLabel("Factory's Name: ", ManagerImpl.getManager(0).showFactoryInfo(directorName).getName());
		
		this.add(this.factoryName);
		this.add(Box.createRigidArea(new Dimension(0, H_GAP_COMPONENT)));
		this.add(secondComponent);
		this.add(Box.createRigidArea(new Dimension(0, H_GAP_COMPONENT)));
		this.add(thirdComponent);
		this.add(Box.createRigidArea(new Dimension(0, H_GAP_BUTTON)));
		this.add(fourthComponent);
		
		this.setSize(SIZE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public JDialog getRequestPopup() {
		return this;
	}
}
