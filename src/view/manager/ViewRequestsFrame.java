package view.manager;

import java.awt.EventQueue;

import javax.swing.JFrame;

import controller.classes.ManagerImpl;
import exceptions.FullWarehouseException;
import model.interfaces.Request;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Cursor;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

public class ViewRequestsFrame {

	private JFrame frmViewRequests;
	private JPanel panel = new JPanel();

	/**
	 * Create the application.
	 */
	public ViewRequestsFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewRequestsFrame window = initialize();
					window.frmViewRequests.setVisible(true);
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
	private ViewRequestsFrame initialize() {
		frmViewRequests = new JFrame();
		frmViewRequests.setTitle("View Requests");
		frmViewRequests.setBounds(100, 100, 490, 350);
		frmViewRequests.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmViewRequests.setLocationRelativeTo(null);
		frmViewRequests.setResizable(false);
		
		JButton btnNewButton_2 = new JButton("Back");
		btnNewButton_2.setBounds(10, 282, 85, 21);
		btnNewButton_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmViewRequests.dispose();
				new ManagerFrame();
			}
		});
		frmViewRequests.getContentPane().setLayout(null);
		frmViewRequests.getContentPane().add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 450, 250);
		frmViewRequests.getContentPane().add(scrollPane);
		
		panel.setMaximumSize(new Dimension(430, 250));
		panel.setBounds(10, 10, 415, 215);
		panel.setPreferredSize(new Dimension(430, 250));
		panel.setMinimumSize(new Dimension(430, 250));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 50));
		panel_1.add(rigidArea);
		
		JLabel lblNewLabel = new JLabel("Name factory");
		panel_1.add(lblNewLabel);
		
		Component rigidArea1 = Box.createRigidArea(new Dimension(30, 20));
		panel_1.add(rigidArea1);

		JLabel lblNewLabel2 = new JLabel("Type material");
		panel_1.add(lblNewLabel2);
		
		Component rigidArea2 = Box.createRigidArea(new Dimension(30, 20));
		panel_1.add(rigidArea2);

		JLabel lblNewLabel3 = new JLabel("Quantity");
		panel_1.add(lblNewLabel3);
		
		Component rigidArea3 = Box.createRigidArea(new Dimension(120, 20));
		panel_1.add(rigidArea3);
		
		for(Iterator<Request> iterator = ManagerImpl.getManager().getlinkRequestsManager().iterator();iterator.hasNext();) {
			panel.add(createLayout(iterator.next()));
		}
		
		scrollPane.setViewportView(panel);
		
		return this;
		
	}
	private JPanel createLayout(Request request) {
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		Component rigidArea = Box.createRigidArea(new Dimension(0, 50));
		panel_1.add(rigidArea);
		
		JLabel lblNewLabel = new JLabel(request.getReceiverFactory().getName());
		panel_1.add(lblNewLabel);
		
		Component rigidArea1 = Box.createRigidArea(new Dimension(60, 20));
		panel_1.add(rigidArea1);

		JLabel lblNewLabel2 = new JLabel(request.getSentMaterial());
		panel_1.add(lblNewLabel2);
		
		Component rigidArea2 = Box.createRigidArea(new Dimension(60, 20));
		panel_1.add(rigidArea2);

		JLabel lblNewLabel3 = new JLabel(String.valueOf(request.getSentQuantity()));
		panel_1.add(lblNewLabel3);
		
		Component rigidArea3 = Box.createRigidArea(new Dimension(40, 20));
		panel_1.add(rigidArea3);
		
		JButton accept = new JButton("Accept");
		accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ManagerImpl.getManager().satisfiesRequestManager(request);
				} catch (FullWarehouseException e1) {
					JOptionPane.showMessageDialog(panel_1,"The loading Warehouse of \""+request.getReceiverFactory().getName()+"\" is full");
				}
				frmViewRequests.dispose();
				new ViewRequestsFrame();
			}
		});
		accept.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel_1.add(accept);
		
		return panel_1;
		
	}
}
