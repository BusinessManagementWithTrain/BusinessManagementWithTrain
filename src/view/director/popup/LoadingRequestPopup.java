package view.director.popup;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.classes.ManagerImpl;
import model.interfaces.Factory;

public class LoadingRequestPopup extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private static final Dimension SIZE 		= new Dimension(230, 300);
	private static final int H_GAP_COMPONENT 	= 5;
	private static final int H_GAP_BUTTON 		= 30;
	
	private final JLabel 		factoryName;
	private final JLabel 		requiredMaterial;
	private final JTextField 	requiredAmount;
	private final JPanel		buttonPanel;

	public LoadingRequestPopup(String directorName) {
		Factory factory = ManagerImpl.getManager(0).showFactoryInfo(directorName);
		
		this.setModal(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Create Loading Request");
		
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		this.factoryName 		= new LoadingRequestLabel("Factory's Name: ", factory.getName());
		this.requiredMaterial 	= new LoadingRequestLabel("Required Material: ", "" + factory.getMaterial());
		this.requiredAmount 	= new LoadingRequestTextField("Required Amount: ");
		this.buttonPanel		= new PopupButtonPanel(this, directorName, this.requiredAmount);
		
		
		this.add(this.factoryName);
		this.add(Box.createRigidArea(new Dimension(0, H_GAP_COMPONENT)));
		this.add(this.requiredMaterial);
		this.add(Box.createRigidArea(new Dimension(0, H_GAP_COMPONENT)));
		this.add(this.requiredAmount);
		this.add(Box.createRigidArea(new Dimension(0, H_GAP_BUTTON)));
		this.add(this.buttonPanel);
		
		this.setSize(SIZE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}