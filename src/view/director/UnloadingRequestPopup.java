package view.director;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import controller.classes.ManagerImpl;
import exceptions.WrongNeededQuantityException;
import model.interfaces.Factory;

public class UnloadingRequestPopup {

	private JDialog unloadingRequestDialogPopup;
	private String directorName;

	/**
	 * Create the application.
	 */
	public UnloadingRequestPopup(String directorName) {
		this.directorName = directorName;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UnloadingRequestPopup window = initialize();
					window.unloadingRequestDialogPopup.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 * @return 
	 */
	private UnloadingRequestPopup initialize() {
		unloadingRequestDialogPopup = new JDialog();
		unloadingRequestDialogPopup.setModal(true);
		unloadingRequestDialogPopup.setTitle("Create Unloading Request");
		unloadingRequestDialogPopup.setBounds(100, 100, 230, 300);
		unloadingRequestDialogPopup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		unloadingRequestDialogPopup.setResizable(false);
		unloadingRequestDialogPopup.setLocationRelativeTo(null);
		unloadingRequestDialogPopup.getContentPane().setLayout(new BoxLayout(unloadingRequestDialogPopup.getContentPane(), BoxLayout.Y_AXIS));
		
		JLabel factoryNameLabel = new JLabel(Factory().getName());
		factoryNameLabel.setFont(new Font("Serif", Font.ITALIC, 20));
		factoryNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		factoryNameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		factoryNameLabel.setMaximumSize(new Dimension(200, 50));
		factoryNameLabel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Factory's Name:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		factoryNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		unloadingRequestDialogPopup.getContentPane().add(factoryNameLabel);
		
		Component rigidArea = Box.createRigidArea(new Dimension(0, 5));
		unloadingRequestDialogPopup.getContentPane().add(rigidArea);
		
		JLabel materialToBeRemoved = new JLabel(Factory().getMaterial().getProcessedMaterial());
		materialToBeRemoved.setMaximumSize(new Dimension(200, 50));
		materialToBeRemoved.setHorizontalAlignment(SwingConstants.CENTER);
		materialToBeRemoved.setHorizontalTextPosition(SwingConstants.CENTER);
		materialToBeRemoved.setFont(new Font("Serif", Font.ITALIC, 20));
		materialToBeRemoved.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Material To Be Removed:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		materialToBeRemoved.setAlignmentX(Component.CENTER_ALIGNMENT);
		unloadingRequestDialogPopup.getContentPane().add(materialToBeRemoved);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(0, 5));
		unloadingRequestDialogPopup.getContentPane().add(rigidArea_1);
		
		JLabel amountToBeRemoved = new JLabel(Factory().getUnloadingWarehouse().getCurrentCapacity() + " kg");
		amountToBeRemoved.setHorizontalTextPosition(SwingConstants.CENTER);
		amountToBeRemoved.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Amount To Be Removed:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		amountToBeRemoved.setHorizontalAlignment(SwingConstants.CENTER);
		amountToBeRemoved.setFont(new Font("Serif", Font.ITALIC, 20));
		amountToBeRemoved.setMaximumSize(new Dimension(200, 50));
		amountToBeRemoved.setAlignmentX(Component.CENTER_ALIGNMENT);
		unloadingRequestDialogPopup.getContentPane().add(amountToBeRemoved);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(0, 40));
		unloadingRequestDialogPopup.getContentPane().add(rigidArea_2);
		
		JPanel bottomPanel = new JPanel();
		unloadingRequestDialogPopup.getContentPane().add(bottomPanel);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
		bottomPanel.add(rigidArea_4);
		
		JPanel buttonsPanel = new JPanel();
		bottomPanel.add(buttonsPanel);
		buttonsPanel.setLayout(new BorderLayout(0, 0));
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> {
			unloadingRequestDialogPopup.dispose();
		});
		buttonsPanel.add(cancelButton, BorderLayout.WEST);
		
		JButton createButton = new JButton("Create");
		createButton.addActionListener(e -> {
			
			try {
				ManagerImpl.getManager().emptyWarehouse(directorName);
				JOptionPane.showMessageDialog(loadingRequestDialogPopup, "Unloading Request Create Succesfully!");
				unloadingRequestDialogPopup.dispose();
			} catch(WrongNeededQuantityException exc) {
				JOptionPane.showMessageDialog(unloadingRequestDialogPopup, "The unloading warehouse is already empty!");
			}
			
		});
		buttonsPanel.add(createButton, BorderLayout.EAST);
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 20));
		bottomPanel.add(rigidArea_5);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(0, 20));
		unloadingRequestDialogPopup.getContentPane().add(rigidArea_3);
		
		return this;
	}

	private Factory Factory() {
		return ManagerImpl.getManager().showFactoryInfo(directorName);
	}
}
