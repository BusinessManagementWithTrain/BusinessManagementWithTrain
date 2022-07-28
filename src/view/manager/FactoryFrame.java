package view.manager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import javax.swing.JScrollPane;

import controller.classes.ManagerImpl;
import model.interfaces.Director;
import view.director.DirectorFrame;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;


public class FactoryFrame {

	private JFrame frmFactory;
	private JPanel panel = new JPanel();


	/**
	 * Create the application.
	 */
	public FactoryFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					FactoryFrame window = initialize();
					window.frmFactory.setVisible(true);
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
	private FactoryFrame initialize() {
		frmFactory = new JFrame();
		frmFactory.setTitle("Factory");
		frmFactory.setBounds(100, 100, 490, 350);
		frmFactory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFactory.setLocationRelativeTo(null);
		frmFactory.setResizable(false);
		
		JButton btnNewButton_2 = new JButton("Back");
		btnNewButton_2.setBounds(10, 282, 85, 21);
		btnNewButton_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmFactory.dispose();
				new ManagerFrame();
			}
		});
		frmFactory.getContentPane().setLayout(null);
		frmFactory.getContentPane().add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 450, 250);
		frmFactory.getContentPane().add(scrollPane);
		
		panel.setMaximumSize(new Dimension(430, 250));
		panel.setBounds(10, 10, 415, 215);
		panel.setPreferredSize(new Dimension(430, 250));
		panel.setMinimumSize(new Dimension(430, 250));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		
		for(Iterator<Director> iterator = ManagerImpl.getManager().getLinkDirectors().iterator();iterator.hasNext();) {
			panel.add(createLayout(iterator.next()));
		}
		
		scrollPane.setViewportView(panel);
		
		return this;
	}
	
	private JPanel createLayout(Director director) {
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		Component rigidArea = Box.createRigidArea(new Dimension(50, 50));
		panel_1.add(rigidArea);
		
		JLabel lblNewLabel = new JLabel(director.getFactory().getName());
		panel_1.add(lblNewLabel);
		
		Component rigidArea1 = Box.createRigidArea(new Dimension(50, 20));
		panel_1.add(rigidArea1);
		
		JButton view = new JButton("View");
		view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmFactory.dispose();
				new DirectorFrame(director.getName());
			}
		});
		view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel_1.add(view);
		
		
		Component rigidArea2 = Box.createRigidArea(new Dimension(50, 20));
		panel_1.add(rigidArea2);
		
		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagerImpl.getManager().fireDirector(director.getName());
				frmFactory.dispose();
				new FactoryFrame();
			}
		});
		remove.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel_1.add(remove);
		
		return panel_1;
		
	}
}
