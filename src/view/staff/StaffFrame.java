package view.staff;

import java.awt.EventQueue;

import javax.swing.JFrame;

import controller.classes.ManagerImpl;
import exceptions.EmptyWarehouseException;
import exceptions.FullWarehouseException;
import exceptions.StaffIsAlreadyNotWorkingException;
import exceptions.StaffIsAlreadyWorkingException;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import view.director.DirectorFrame;
public class StaffFrame {

	private JFrame frame;
	private String internalDirectorName;
	

	/**
	 * Create the application.
	 */
	public StaffFrame(String directorName) {
		this.internalDirectorName = directorName;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StaffFrame window = initialize();
					window.frame.setVisible(true);
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
	private StaffFrame initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		JButton startWorkingBtn = new JButton("Start Working");
		startWorkingBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		startWorkingBtn.addActionListener(e -> {
			try {
				ManagerImpl.getManager().showFactoryInfo(internalDirectorName).getStuffMembers().startWorking();
				frame.dispose();
				new StaffFrame(internalDirectorName);
				
			} catch (EmptyWarehouseException e1) {
				JOptionPane.showMessageDialog(frame, "The loading Warehouse has not enough materials!");
			} catch (StaffIsAlreadyWorkingException e1) {
				JOptionPane.showMessageDialog(frame, "The staff is already working!");
			}
		});
		startWorkingBtn.setBounds(135, 30, 165, 50);
		frame.getContentPane().add(startWorkingBtn);
		
		JButton stopWorkingBtn = new JButton("Stop Working");
		stopWorkingBtn.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		stopWorkingBtn.addActionListener(e -> {
			try {
				ManagerImpl.getManager().showFactoryInfo(internalDirectorName).getStuffMembers().stopWorking();
				frame.dispose();
				new StaffFrame(internalDirectorName);
				
			} catch (FullWarehouseException e1) {
				JOptionPane.showMessageDialog(frame, "The unloading Warehouse has not enough space!");
			} catch (StaffIsAlreadyNotWorkingException e1) {
				JOptionPane.showMessageDialog(frame, "The staff is already not working!");
			}
			
		});
		stopWorkingBtn.setBounds(135, 183, 165, 50);
		frame.getContentPane().add(stopWorkingBtn);
		
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(e -> frame.dispose());
		backBtn.setBounds(341, 232, 85, 21);
		backBtn.addActionListener(e -> {
			frame.dispose();
			new DirectorFrame(ManagerImpl.getManager().getDirectorByFactory(ManagerImpl.getManager().showFactoryInfo(internalDirectorName)).getName());
		});
		frame.getContentPane().add(backBtn);
		
		JLabel lblNewLabel = new JLabel("Operators number:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(154, 116, 136, 21);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Loading Warehouse");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(21, 118, 123, 17);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Unloading Warehouse");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(300, 120, 126, 13);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel(ManagerImpl.getManager().showFactoryInfo(internalDirectorName).getLoadingWarehouse().getCurrentCapacity() + "/" +
									      ManagerImpl.getManager().showFactoryInfo(internalDirectorName).getLoadingWarehouse().getTotalCapacity());
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(62, 134, 45, 13);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel(ManagerImpl.getManager().showFactoryInfo(internalDirectorName).getStuffMembers().getNumber() + "");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(199, 134, 45, 13);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel(ManagerImpl.getManager().showFactoryInfo(internalDirectorName).getUnloadingWarehouse().getCurrentCapacity() + "/" +
			      						  ManagerImpl.getManager().showFactoryInfo(internalDirectorName).getUnloadingWarehouse().getTotalCapacity());
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(343, 134, 45, 13);
		frame.getContentPane().add(lblNewLabel_5);
		return this;
	}
}
