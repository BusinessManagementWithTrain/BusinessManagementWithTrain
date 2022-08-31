package view.director;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.border.TitledBorder;

import controller.classes.ManagerImpl;
import exceptions.WrongNeededQuantityException;

import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class LoadingRequestPopup {

	private JDialog loadingRequestDialogPopup;
	private JTextField requiredAmountTextField;
	private String directorName;

	/**
	 * Create the application.
	 */
	public LoadingRequestPopup(String directorName) {
		this.directorName = directorName;
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoadingRequestPopup window = initialize();
					window.loadingRequestDialogPopup.setVisible(true);
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
	private LoadingRequestPopup initialize() {
		loadingRequestDialogPopup = new JDialog();
		loadingRequestDialogPopup.setModal(true);
		loadingRequestDialogPopup.setTitle("Create Loading Request");
		loadingRequestDialogPopup.setBounds(100, 100, 230, 300);
		loadingRequestDialogPopup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		loadingRequestDialogPopup.setResizable(false);
		loadingRequestDialogPopup.setLocationRelativeTo(null);
		loadingRequestDialogPopup.getContentPane().setLayout(new BoxLayout(loadingRequestDialogPopup.getContentPane(), BoxLayout.Y_AXIS));
		
		JLabel factoryNameLabel = new JLabel(ManagerImpl.getManager().showFactoryInfo(directorName).getName());
		factoryNameLabel.setFont(new Font("Serif", Font.ITALIC, 20));
		factoryNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		factoryNameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		factoryNameLabel.setMaximumSize(new Dimension(200, 50));
		factoryNameLabel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Factory's Name:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		factoryNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadingRequestDialogPopup.getContentPane().add(factoryNameLabel);
		
		Component rigidArea = Box.createRigidArea(new Dimension(0, 5));
		loadingRequestDialogPopup.getContentPane().add(rigidArea);
		
		JLabel requiredMaterialLabel = new JLabel(ManagerImpl.getManager().showFactoryInfo(directorName).getMaterial().getRawMaterial());
		requiredMaterialLabel.setMaximumSize(new Dimension(200, 50));
		requiredMaterialLabel.setHorizontalAlignment(SwingConstants.CENTER);
		requiredMaterialLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		requiredMaterialLabel.setFont(new Font("Serif", Font.ITALIC, 20));
		requiredMaterialLabel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Required Material:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		requiredMaterialLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadingRequestDialogPopup.getContentPane().add(requiredMaterialLabel);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(0, 5));
		loadingRequestDialogPopup.getContentPane().add(rigidArea_1);
		
		requiredAmountTextField = new JTextField();
		requiredAmountTextField.setFont(new Font("Serif", Font.PLAIN, 20));
		requiredAmountTextField.setMaximumSize(new Dimension(200, 50));
		requiredAmountTextField.setToolTipText("Insert amount needed here!");
		requiredAmountTextField.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Required Amount:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		loadingRequestDialogPopup.getContentPane().add(requiredAmountTextField);
		requiredAmountTextField.setColumns(10);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(0, 40));
		loadingRequestDialogPopup.getContentPane().add(rigidArea_2);
		
		JPanel bottomPanel = new JPanel();
		loadingRequestDialogPopup.getContentPane().add(bottomPanel);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 0));
		bottomPanel.add(rigidArea_4);
		
		JPanel buttonsPanel = new JPanel();
		bottomPanel.add(buttonsPanel);
		buttonsPanel.setLayout(new BorderLayout(0, 0));
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> {
			loadingRequestDialogPopup.dispose();
		});
		buttonsPanel.add(cancelButton, BorderLayout.WEST);
		
		JButton createButton = new JButton("Create");
		
		createButton.addActionListener(e -> {
			try {
				ManagerImpl.getManager().createNewRequest(Integer.valueOf(requiredAmountTextField.getText()), directorName);
				JOptionPane.showMessageDialog(loadingRequestDialogPopup, "Loading Request Create Succesfully!");
				loadingRequestDialogPopup.dispose();
			} catch(NumberFormatException exc) {
				JOptionPane.showMessageDialog(loadingRequestDialogPopup,
  						 					  "The entered value is not an integer!",
  						 					  "ERROR!",
  						 					  JOptionPane.ERROR_MESSAGE);
			} catch(WrongNeededQuantityException exc) {
				JOptionPane.showMessageDialog(loadingRequestDialogPopup,
											  "The entered value is too big, negative or null!",
											  "ERROR!",
											  JOptionPane.ERROR_MESSAGE);
			}
		});
		buttonsPanel.add(createButton, BorderLayout.EAST);
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 0));
		bottomPanel.add(rigidArea_5);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(0, 20));
		loadingRequestDialogPopup.getContentPane().add(rigidArea_3);
		
		return this;
	}

}
