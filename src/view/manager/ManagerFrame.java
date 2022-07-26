package view.manager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SwingConstants;

import view.train.TrainFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;

public class ManagerFrame {

	private JFrame frmManager;

	/**
	 * Create the application.
	 */
	public ManagerFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerFrame window = initialize();
					window.frmManager.setVisible(true);
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
	private ManagerFrame initialize() {
		frmManager = new JFrame();
		frmManager.setTitle("Manager");
		frmManager.setBounds(100, 100, 450, 300);
		frmManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmManager.setLocationRelativeTo(null);
		frmManager.setResizable(false);
		
		JPanel panel = new JPanel();
		frmManager.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JButton btnNewButton = new JButton("Factory");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Factory();
				frmManager.dispose();
			}
		});
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton.setPreferredSize(new Dimension(250, 21));
		btnNewButton.setMinimumSize(new Dimension(250, 21));
		btnNewButton.setMaximumSize(new Dimension(250, 35));
		btnNewButton.setHorizontalTextPosition(SwingConstants.CENTER);
		panel.add(btnNewButton);
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_4.setMaximumSize(new Dimension(20, 30));
		panel.add(rigidArea_4);
		
		JButton btnNewButton_1 = new JButton("Hire director");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new HireDirector();
				frmManager.dispose();
				
			}
		});
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setMinimumSize(new Dimension(250, 21));
		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton_1.setMaximumSize(new Dimension(250, 35));
		panel.add(btnNewButton_1);
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_5.setMaximumSize(new Dimension(20, 30));
		panel.add(rigidArea_5);
		
		JButton btnNewButton_2 = new JButton("Train");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TrainFrame();
				frmManager.dispose();
			}
		});
		btnNewButton_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_2.setMaximumSize(new Dimension(250, 35));
		btnNewButton_2.setMinimumSize(new Dimension(250, 21));
		btnNewButton_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnNewButton_2);
		
		Component rigidArea_6 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_6.setMaximumSize(new Dimension(20, 30));
		panel.add(rigidArea_6);
		
		JButton btnNewButton_3 = new JButton("View requests");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ViewRequests();
				frmManager.dispose();
			}
		});
		btnNewButton_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_3.setMaximumSize(new Dimension(250, 35));
		btnNewButton_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnNewButton_3);
		
		Component rigidArea = Box.createRigidArea(new Dimension(123, 223));
		frmManager.getContentPane().add(rigidArea, BorderLayout.WEST);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		frmManager.getContentPane().add(rigidArea_1, BorderLayout.NORTH);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(123, 223));
		frmManager.getContentPane().add(rigidArea_3, BorderLayout.EAST);
		return this;
	}

}
