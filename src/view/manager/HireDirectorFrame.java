package view.manager;



/*
		JButton btnNewButton_1 = new JButton("Confirm");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ManagerImpl.getManager().hireDirector(new DirectorImpl(directorNameTextField.getText(),
									   						  			   new FactoryImpl(factoryNameTextField.getText(),
									   						  					   		   new MaterialImpl(rawMaterialTextField.getText(),
									   						  					   				   			processedMaterialTextField.getText()),
									   						  					   		   Integer.valueOf(numberOperatorsTextField.getText()),
									   						  					   		   Integer.valueOf(loadingWarehouseSizeTextField.getText()),
									   						  					   		   Integer.valueOf(unloadingWarehouseSizeTextField.getText()))));
					frmHireDirector.dispose();
					new ManagerFrame();
					
				} catch (WrongNeededQuantityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (EqualMaterialException e1) {
					JOptionPane.showMessageDialog(frmHireDirector,"The materials are equals");
				} catch (EmptyFieldException e1) {
					JOptionPane.showMessageDialog(frmHireDirector,"One of the fields has not been filled in");
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MaximumCharactersException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
 */



import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import controller.classes.ManagerImpl;
import exceptions.EmptyFieldException;
import exceptions.EqualMaterialException;
import exceptions.MaximumCharactersException;
import exceptions.WrongNeededQuantityException;
import model.classes.DirectorImpl;
import model.classes.FactoryImpl;
import model.classes.MaterialImpl;
import view.manager.ManagerFrame;

import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Cursor;
import javax.swing.SwingConstants;

public class HireDirectorFrame {

	private JFrame frmHireDirector;
	private JTextField rawMaterialTextField;
	private JTextField processedMaterialTextField;
	private JTextField directorNameTextField;
	private JTextField loadingWarehouseSizeTextField;
	private JTextField unloadingWarehouseSizeTextField;
	private JTextField numberOperatorsTextField;
	private JTextField factoryNameTextField;

	/**
	 * Create the application.
	 */
	public HireDirectorFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HireDirectorFrame window = initialize();
					window.frmHireDirector.setVisible(true);
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
	private HireDirectorFrame initialize() {
		frmHireDirector = new JFrame();
		frmHireDirector.setTitle("Hire director");
		frmHireDirector.setBounds(100, 100, 450, 320);
		frmHireDirector.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHireDirector.setLocationRelativeTo(null);
	 	frmHireDirector.setResizable(false);
		
		JPanel labelPanel = new JPanel();
		labelPanel.setPreferredSize(new Dimension(220, 10));
		labelPanel.setMinimumSize(new Dimension(200, 10));
		labelPanel.setMaximumSize(new Dimension(200, 32767));
		frmHireDirector.getContentPane().add(labelPanel, BorderLayout.WEST);
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		labelPanel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JLabel rawMaterialLabel = new JLabel("Type of raw material");
		rawMaterialLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rawMaterialLabel.setMinimumSize(new Dimension(150, 25));
		rawMaterialLabel.setToolTipText("\r\n");
		rawMaterialLabel.setMaximumSize(new Dimension(150, 20));
		rawMaterialLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelPanel.add(rawMaterialLabel);
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 10));
		labelPanel.add(rigidArea_4);
		
		JLabel finiteMaterialLabel = new JLabel("Type of processed material");
		finiteMaterialLabel.setHorizontalAlignment(SwingConstants.CENTER);
		finiteMaterialLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		finiteMaterialLabel.setMinimumSize(new Dimension(170, 25));
		finiteMaterialLabel.setMaximumSize(new Dimension(170, 20));
		labelPanel.add(finiteMaterialLabel);
		
		Component rigidArea_6 = Box.createRigidArea(new Dimension(20, 10));
		labelPanel.add(rigidArea_6);
		
		JLabel directorNameLabel = new JLabel("Director's name");
		directorNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		directorNameLabel.setMaximumSize(new Dimension(150, 20));
		directorNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelPanel.add(directorNameLabel);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 10));
		labelPanel.add(rigidArea_2);
		
		JLabel loadingWarehouseSizeLabel = new JLabel("Loading warehouse size");
		loadingWarehouseSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loadingWarehouseSizeLabel.setMaximumSize(new Dimension(150, 20));
		loadingWarehouseSizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelPanel.add(loadingWarehouseSizeLabel);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 10));
		labelPanel.add(rigidArea_3);
		
		JLabel unloadingWarehouseSizeLabel = new JLabel("Unloading warehouse size");
		unloadingWarehouseSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		unloadingWarehouseSizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		unloadingWarehouseSizeLabel.setMaximumSize(new Dimension(150, 20));
		unloadingWarehouseSizeLabel.setPreferredSize(new Dimension(150, 20));
		labelPanel.add(unloadingWarehouseSizeLabel);
		
		Component rigidArea_9 = Box.createRigidArea(new Dimension(20, 10));
		labelPanel.add(rigidArea_9);
		
		JLabel numberOperatorsLabel = new JLabel("Staff size");
		numberOperatorsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numberOperatorsLabel.setMaximumSize(new Dimension(150, 20));
		numberOperatorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelPanel.add(numberOperatorsLabel);
		
		Component rigidArea_10 = Box.createRigidArea(new Dimension(20, 10));
		labelPanel.add(rigidArea_10);
		
		JLabel factoryNameLabel = new JLabel("Factory's name");
		factoryNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		factoryNameLabel.setMaximumSize(new Dimension(150, 20));
		factoryNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelPanel.add(factoryNameLabel);
		
		JPanel textBoxPanel = new JPanel();
		textBoxPanel.setMinimumSize(new Dimension(200, 10));
		textBoxPanel.setMaximumSize(new Dimension(200, 32767));
		frmHireDirector.getContentPane().add(textBoxPanel, BorderLayout.CENTER);
		textBoxPanel.setLayout(new BoxLayout(textBoxPanel, BoxLayout.Y_AXIS));
		
		rawMaterialTextField = new JTextField();
		rawMaterialTextField.setMinimumSize(new Dimension(7, 25));
		rawMaterialTextField.setMaximumSize(new Dimension(150, 20));
		textBoxPanel.add(rawMaterialTextField);
		rawMaterialTextField.setColumns(10);
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 10));
		textBoxPanel.add(rigidArea_5);
		
		processedMaterialTextField = new JTextField();
		processedMaterialTextField.setMaximumSize(new Dimension(150, 20));
		textBoxPanel.add(processedMaterialTextField);
		processedMaterialTextField.setColumns(10);
		
		Component rigidArea_7 = Box.createRigidArea(new Dimension(20, 10));
		textBoxPanel.add(rigidArea_7);
		
		directorNameTextField = new JTextField();
		directorNameTextField.setMinimumSize(new Dimension(7, 25));
		directorNameTextField.setMaximumSize(new Dimension(150, 20));
		textBoxPanel.add(directorNameTextField);
		directorNameTextField.setColumns(10);
		
		Component rigidArea_8 = Box.createRigidArea(new Dimension(20, 10));
		textBoxPanel.add(rigidArea_8);
		
		loadingWarehouseSizeTextField = new JTextField();
		loadingWarehouseSizeTextField.setMaximumSize(new Dimension(150, 20));
		textBoxPanel.add(loadingWarehouseSizeTextField);
		loadingWarehouseSizeTextField.setColumns(10);
		
		Component rigidArea_11 = Box.createRigidArea(new Dimension(20, 10));
		textBoxPanel.add(rigidArea_11);
		
		unloadingWarehouseSizeTextField = new JTextField();
		unloadingWarehouseSizeTextField.setMaximumSize(new Dimension(150, 20));
		textBoxPanel.add(unloadingWarehouseSizeTextField);
		unloadingWarehouseSizeTextField.setColumns(10);
		
		Component rigidArea_12 = Box.createRigidArea(new Dimension(20, 10));
		textBoxPanel.add(rigidArea_12);
		
		numberOperatorsTextField = new JTextField();
		numberOperatorsTextField.setMaximumSize(new Dimension(150, 20));
		textBoxPanel.add(numberOperatorsTextField);
		numberOperatorsTextField.setColumns(10);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 10));
		frmHireDirector.getContentPane().add(rigidArea, BorderLayout.NORTH);
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(10, 30));
		frmHireDirector.getContentPane().add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 10));
		panel_3.add(rigidArea_1);
		
		Component rigidArea_13 = Box.createRigidArea(new Dimension(20, 10));
		textBoxPanel.add(rigidArea_13);
		

		factoryNameTextField = new JTextField();
		factoryNameTextField.setMaximumSize(new Dimension(150, 20));
		textBoxPanel.add(factoryNameTextField);
		factoryNameTextField.setColumns(10);
		
		Component rigidArea_14 = Box.createRigidArea(new Dimension(20, 10));
		textBoxPanel.add(rigidArea_14);
		
		JButton btnNewButton_1 = new JButton("Confirm");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ManagerImpl.getManager().hireDirector(new DirectorImpl(directorNameTextField.getText(),
									   						  			   new FactoryImpl(factoryNameTextField.getText(),
									   						  					   		   new MaterialImpl(rawMaterialTextField.getText(),
									   						  					   				   			processedMaterialTextField.getText()),
									   						  					   		   Integer.valueOf(numberOperatorsTextField.getText()),
									   						  					   		   Integer.valueOf(loadingWarehouseSizeTextField.getText()),
									   						  					   		   Integer.valueOf(unloadingWarehouseSizeTextField.getText()))));
				frmHireDirector.dispose();
				new ManagerFrame();
			} catch (EqualMaterialException e1) {
				JOptionPane.showMessageDialog(frmHireDirector,"The materials are equal, change it.");
			} catch (WrongNeededQuantityException e1) {
				JOptionPane.showMessageDialog(frmHireDirector,"");
			} catch (EmptyFieldException e1) {
				JOptionPane.showMessageDialog(frmHireDirector,"One of the fields has not been filled in");
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(frmHireDirector,"Enter numeric values ​​in full format");
			} catch (MaximumCharactersException e1) {
				JOptionPane.showMessageDialog(frmHireDirector,"The director name entered is too long, max 12 characters.");
			}
		}});
		
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		textBoxPanel.add(btnNewButton_1);
		
		JButton backButton = new JButton("Back");
		backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backButton.setMaximumSize(new Dimension(85, 21));
		backButton.setMinimumSize(new Dimension(85, 21));
		backButton.setPreferredSize(new Dimension(70, 30));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmHireDirector.dispose();
				new ManagerFrame();
			}
		});
		panel_3.add(backButton);
		return this;
				
	}

}
