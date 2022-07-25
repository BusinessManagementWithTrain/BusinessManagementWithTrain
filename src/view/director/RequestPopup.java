package view.director;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import controller.classes.ManagerImpl;
import exceptions.AnotherAcceptedRequestException;
import model.interfaces.Request;

import java.awt.Font;
import java.util.Iterator;

import javax.swing.border.BevelBorder;
import java.awt.Cursor;
import javax.swing.border.EtchedBorder;

public class RequestPopup {

	private JDialog requestPopup;
	private JFrame directorFrame;
	private final JPanel internalScrollPanePanel = new JPanel();
	private String directorName;
	
	/**
	 * Create the application.
	 */
	public RequestPopup(String directorName, JFrame directorFrame) {
		this.directorName = directorName;
		this.directorFrame = directorFrame;
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RequestPopup window = initialize();
					window.requestPopup.setVisible(true);
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
	private RequestPopup initialize() {
		requestPopup = new JDialog();
		requestPopup.setTitle("Current Requests");
		requestPopup.setModal(true);
		requestPopup.setBounds(100, 100, 450, 300);
		requestPopup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		requestPopup.setResizable(false);
		requestPopup.setLocationRelativeTo(null);
		requestPopup.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel requestPopupGeneralPanel = new JPanel();
		requestPopup.getContentPane().add(requestPopupGeneralPanel, BorderLayout.CENTER);
		requestPopupGeneralPanel.setLayout(new BoxLayout(requestPopupGeneralPanel, BoxLayout.Y_AXIS));
		
		JPanel topPanel = new JPanel();
		requestPopupGeneralPanel.add(topPanel);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		
		JButton backButton = new JButton("Back");
		backButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.addActionListener(e -> requestPopup.dispose());
		topPanel.add(backButton);
		
		Component rigidArea_12 = Box.createRigidArea(new Dimension(100, 0));
		topPanel.add(rigidArea_12);
		
		JPanel acceptedRequestPanel = new JPanel();
		acceptedRequestPanel.setBorder(new TitledBorder(null, "Accepted Request", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		topPanel.add(acceptedRequestPanel);
		acceptedRequestPanel.setLayout(new BoxLayout(acceptedRequestPanel, BoxLayout.Y_AXIS));
		
		Component rigidArea_6 = Box.createRigidArea(new Dimension(150, 8));
		acceptedRequestPanel.add(rigidArea_6);
		
		JLabel acceptedRequestsProductQuantityLabel = new JLabel(ManagerImpl.getManager().showDirectorInfo(directorName).getAcceptedRequest() == null? "There is no accepted":
																 "\"" + ManagerImpl.getManager().showDirectorInfo(directorName).getAcceptedRequest().getReceiverFactory().getName() + "\" needs");
		acceptedRequestsProductQuantityLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		acceptedRequestsProductQuantityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		acceptedRequestPanel.add(acceptedRequestsProductQuantityLabel);
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(0, 8));
		acceptedRequestPanel.add(rigidArea_5);
		
		JLabel acceptedRequestFactoryNameLabel = new JLabel(ManagerImpl.getManager().showDirectorInfo(directorName).getAcceptedRequest() == null? " request!":
															ManagerImpl.getManager().showDirectorInfo(directorName).getAcceptedRequest().getSentQuantity() + " kg of " + ManagerImpl.getManager().showDirectorInfo(directorName).getFactory().getMaterial().getProcessedMaterial());
		acceptedRequestFactoryNameLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		acceptedRequestFactoryNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		acceptedRequestPanel.add(acceptedRequestFactoryNameLabel);
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(0, 16));
		acceptedRequestPanel.add(rigidArea_4);
		
		Component rigidArea_13 = Box.createRigidArea(new Dimension(70, 0));
		topPanel.add(rigidArea_13);
		
		JScrollPane availableRequestScrollPane = new JScrollPane();
		availableRequestScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		availableRequestScrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		availableRequestScrollPane.setBorder(new TitledBorder(null, "Available Requests", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		requestPopupGeneralPanel.add(availableRequestScrollPane);
		
		internalScrollPanePanel.setLayout(new BoxLayout(internalScrollPanePanel, BoxLayout.Y_AXIS));
		
		for(Iterator<Request> iterator = ManagerImpl.getManager().showDirectorInfo(directorName).getRequestsToSatisfy().iterator();
			iterator.hasNext();) {
			internalScrollPanePanel.add(createNewRequestPanel(iterator.next()));
		}
		
		availableRequestScrollPane.setViewportView(internalScrollPanePanel);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		requestPopup.getContentPane().add(rigidArea, BorderLayout.WEST);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		requestPopup.getContentPane().add(rigidArea_1, BorderLayout.EAST);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		requestPopup.getContentPane().add(rigidArea_2, BorderLayout.NORTH);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		requestPopup.getContentPane().add(rigidArea_3, BorderLayout.SOUTH);
		
		return this;
	}
	
	private JPanel createNewRequestPanel(Request availableRequest) {
		
		JPanel newPanel = new JPanel();
		
		newPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.X_AXIS));
		
		JPanel internalPanel = new JPanel();
		newPanel.add(internalPanel);
		internalPanel.setLayout(new BoxLayout(internalPanel, BoxLayout.Y_AXIS));
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(150, 8));
		internalPanel.add(rigidArea_1);
		
		JLabel currentRequestsProductQuantityLabel = new JLabel("\"" + availableRequest.getReceiverFactory().getName() + "\" needs");
		currentRequestsProductQuantityLabel.setFont(new Font("Serif", Font.ITALIC, 18));
		currentRequestsProductQuantityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		internalPanel.add(currentRequestsProductQuantityLabel);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(0, 8));
		internalPanel.add(rigidArea_2);
		
		JLabel currentRequestFactoryNameLabel = new JLabel(availableRequest.getSentQuantity() + " kg of " + ManagerImpl.getManager().showDirectorInfo(directorName).getFactory().getMaterial().getProcessedMaterial());
		currentRequestFactoryNameLabel.setFont(new Font("Serif", Font.ITALIC, 18));
		currentRequestFactoryNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		internalPanel.add(currentRequestFactoryNameLabel);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(0, 16));
		internalPanel.add(rigidArea_3);
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(60, 0));
		newPanel.add(rigidArea_4);
		
		JButton acceptButton = new JButton("Accept");
		acceptButton.setMinimumSize(new Dimension(80, 40));
		acceptButton.setMaximumSize(new Dimension(80, 40));
		acceptButton.setPreferredSize(new Dimension(80, 40));
		acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		acceptButton.addActionListener(e -> {
			try {
				ManagerImpl.getManager().satisfiesRequestDirector(availableRequest, directorName);
				requestPopup.dispose();
				directorFrame.dispose();
				new DirectorFrame(directorName);
			} catch (AnotherAcceptedRequestException e1) {
				JOptionPane.showMessageDialog(requestPopup, "Another request is already accepted!");
			}
		});
		newPanel.add(acceptButton);
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(30, 0));
		newPanel.add(rigidArea_5);
		
		return newPanel;
	}
}
