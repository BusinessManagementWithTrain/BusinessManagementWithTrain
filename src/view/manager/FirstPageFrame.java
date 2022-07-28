package view.manager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dialog.ModalExclusionType;
import javax.swing.SwingConstants;

import controller.classes.ManagerImpl;
import exceptions.LowTrainCapacityException;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Font;

public class FirstPageFrame {

	private JFrame initialPage;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstPageFrame window = new FirstPageFrame();
					window.initialPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FirstPageFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initialPage = new JFrame();
		initialPage.setTitle("Welcome");
		initialPage.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		initialPage.setBounds(100, 100, 450, 300);
		initialPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialPage.getContentPane().setLayout(null);
		initialPage.setLocationRelativeTo(null);
		initialPage.setResizable(false);
		
		JLabel lblNewLabel = new JLabel("Please, enter the maximum train capacity (Kg >= 100)");
		lblNewLabel.setToolTipText("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(66, 40, 320, 31);
		initialPage.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 30));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(145, 97, 130, 63);
		initialPage.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton Confirm = new JButton("Confirm");
		Confirm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ManagerImpl.getManager(Integer.valueOf(textField.getText()));
					new ManagerFrame();
					initialPage.dispose();
				} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(initialPage,"The value is not an integer.");
				} catch (LowTrainCapacityException e1) {
				JOptionPane.showMessageDialog(initialPage,"The value is too low, please increase it.");
				}
			}
		});
		Confirm.setBounds(163, 200, 96, 37);
		initialPage.getContentPane().add(Confirm);
	}
}
