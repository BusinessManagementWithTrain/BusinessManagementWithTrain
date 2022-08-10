package view.director;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import controller.classes.ManagerImpl;
import model.interfaces.Factory;
import view.manager.ManagerFrame;
import view.staff.StaffFrame;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

public class DirectorFrame {

	private JFrame directorFrame;
	private final String directorName;

	/**
	 * Create the application.
	 */
	public DirectorFrame(String directorName) {
		this.directorName = directorName;
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final DirectorFrame window = initialize();
					window.directorFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private DirectorFrame initialize() {
		directorFrame = new JFrame();
		directorFrame.setTitle("Hi Mr. " + this.directorName + ". Welcome Back!");
		directorFrame.setBounds(100, 100, 600, 420);
		directorFrame.setResizable(false);
		directorFrame.setLocationRelativeTo(null);
		directorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel baseDirectorPanel = new JPanel();
		directorFrame.getContentPane().add(baseDirectorPanel, BorderLayout.CENTER);
		baseDirectorPanel.setLayout(new BorderLayout(0, 0));
		
		Component rigidArea = Box.createRigidArea(new Dimension(0, 20));
		baseDirectorPanel.add(rigidArea, BorderLayout.NORTH);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(50, 0));
		rigidArea_1.setPreferredSize(new Dimension(30, 298));
		baseDirectorPanel.add(rigidArea_1, BorderLayout.EAST);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(50, 0));
		rigidArea_2.setPreferredSize(new Dimension(30, 298));
		baseDirectorPanel.add(rigidArea_2, BorderLayout.WEST);
		
		JPanel centralDirectorPanel = new JPanel();
		baseDirectorPanel.add(centralDirectorPanel, BorderLayout.CENTER);
		centralDirectorPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel loadingWarehousePanel = new JPanel();
		loadingWarehousePanel.setPreferredSize(new Dimension(205, 10));
		loadingWarehousePanel.setBorder(new TitledBorder(null, "Loading Warehouse", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		loadingWarehousePanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		centralDirectorPanel.add(loadingWarehousePanel, BorderLayout.WEST);
		loadingWarehousePanel.setLayout(new BoxLayout(loadingWarehousePanel, BoxLayout.Y_AXIS));
		
		Component rigidArea_7 = Box.createRigidArea(new Dimension(0, 8));
		loadingWarehousePanel.add(rigidArea_7);
		
		JLabel loadingProductQuantityLabel = new JLabel(Factory().getLoadingWarehouse().getCurrentCapacity() + "/" +
														Factory().getLoadingWarehouse().getTotalCapacity() + " kg");
		loadingProductQuantityLabel.setFont(new Font("Dialog", Font.ITALIC, 15));
		loadingProductQuantityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadingWarehousePanel.add(loadingProductQuantityLabel);
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(0, 8));
		loadingWarehousePanel.add(rigidArea_4);
		
		JLabel loadingProductNameLabel = new JLabel(Factory().getLoadingWarehouse().getMaterial());
		loadingProductNameLabel.setFont(new Font("Dialog", Font.ITALIC, 15));
		loadingProductNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadingWarehousePanel.add(loadingProductNameLabel);
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(0, 16));
		loadingWarehousePanel.add(rigidArea_5);
		
		JButton createLoadingRequestButton = new JButton(" Create Loading Request ");
		createLoadingRequestButton.setPreferredSize(new Dimension(185, 35));
		createLoadingRequestButton.setMaximumSize(new Dimension(185, 35));
		createLoadingRequestButton.setMargin(new Insets(8, 14, 8, 14));
		createLoadingRequestButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		createLoadingRequestButton.addActionListener(e -> new LoadingRequestPopup(directorName));
		loadingWarehousePanel.add(createLoadingRequestButton);
		
		JPanel unloadingWarehousePanel = new JPanel();
		unloadingWarehousePanel.setPreferredSize(new Dimension(205, 10));
		unloadingWarehousePanel.setBorder(new TitledBorder(null, "Unloading Warehouse", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		unloadingWarehousePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		centralDirectorPanel.add(unloadingWarehousePanel, BorderLayout.EAST);
		unloadingWarehousePanel.setLayout(new BoxLayout(unloadingWarehousePanel, BoxLayout.Y_AXIS));
		
		Component rigidArea_8 = Box.createRigidArea(new Dimension(0, 8));
		unloadingWarehousePanel.add(rigidArea_8);
		
		JLabel unloadingProductQuantityLabel = new JLabel(Factory().getUnloadingWarehouse().getCurrentCapacity() + "/" +
														  Factory().getUnloadingWarehouse().getTotalCapacity() + " kg");
		unloadingProductQuantityLabel.setFont(new Font("Dialog", Font.ITALIC, 15));
		unloadingProductQuantityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		unloadingWarehousePanel.add(unloadingProductQuantityLabel);
		
		Component rigidArea_6 = Box.createRigidArea(new Dimension(0, 8));
		unloadingWarehousePanel.add(rigidArea_6);
		
		JLabel unloadingProductNameLabel = new JLabel(Factory().getUnloadingWarehouse().getMaterial());
		unloadingProductNameLabel.setFont(new Font("Dialog", Font.ITALIC, 15));
		unloadingProductNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		unloadingWarehousePanel.add(unloadingProductNameLabel);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(0, 16));
		unloadingWarehousePanel.add(rigidArea_3);
		
		JButton createUnloadingRequestButton = new JButton("Create Unloading Request");
		createUnloadingRequestButton.setMaximumSize(new Dimension(185, 35));
		createUnloadingRequestButton.setPreferredSize(new Dimension(185, 35));
		createUnloadingRequestButton.setMargin(new Insets(8, 14, 8, 14));
		createUnloadingRequestButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		createUnloadingRequestButton.addActionListener(e -> new UnloadingRequestPopup(directorName));
		unloadingWarehousePanel.add(createUnloadingRequestButton);
		
		Component rigidArea_16 = Box.createRigidArea(new Dimension(584, 22));
		baseDirectorPanel.add(rigidArea_16, BorderLayout.SOUTH);
		
		JPanel baseDirectorLabel = new JPanel();
		directorFrame.getContentPane().add(baseDirectorLabel, BorderLayout.NORTH);
		baseDirectorLabel.setLayout(new BoxLayout(baseDirectorLabel, BoxLayout.Y_AXIS));
		
		Component rigidArea_9 = Box.createRigidArea(new Dimension(0, 15));
		baseDirectorLabel.add(rigidArea_9);
		
		JLabel factoryNameLabel = new JLabel(Factory().getName());
		factoryNameLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		factoryNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		factoryNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		baseDirectorLabel.add(factoryNameLabel);
		
		JPanel baseBottomDirectorPanel = new JPanel();
		directorFrame.getContentPane().add(baseBottomDirectorPanel, BorderLayout.SOUTH);
		baseBottomDirectorPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel bottomDirectorPanel = new JPanel();
		baseBottomDirectorPanel.add(bottomDirectorPanel, BorderLayout.CENTER);
		bottomDirectorPanel.setLayout(new BoxLayout(bottomDirectorPanel, BoxLayout.X_AXIS));
		
		JButton backButton = new JButton("Back\r\n");
		backButton.setAlignmentY(Component.TOP_ALIGNMENT);
		backButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		backButton.addActionListener(e -> {
			directorFrame.dispose();
			new ManagerFrame();
		});
		bottomDirectorPanel.add(backButton);
		
		Component rigidArea_10 = Box.createRigidArea(new Dimension(70, 0));
		bottomDirectorPanel.add(rigidArea_10);
		
		JPanel currentRequestsPanel = new JPanel();
		currentRequestsPanel.setMaximumSize(new Dimension(250, 140));
		currentRequestsPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Current Requests", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		bottomDirectorPanel.add(currentRequestsPanel);
		currentRequestsPanel.setLayout(new BoxLayout(currentRequestsPanel, BoxLayout.Y_AXIS));
		
		Component rigidArea_12 = Box.createRigidArea(new Dimension(0, 8));
		currentRequestsPanel.add(rigidArea_12);
		
		JLabel acceptedRequestsProductQuantityLabel = new JLabel(ManagerImpl.getManager().showDirectorInfo(directorName).getAcceptedRequest() == null? "There is no accepted request!":
																 "\"" + ManagerImpl.getManager().showDirectorInfo(directorName).getAcceptedRequest().getReceiverFactory().getName() + "\" needs");
		acceptedRequestsProductQuantityLabel.setFont(new Font("Dialog", Font.ITALIC, 15));
		acceptedRequestsProductQuantityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		currentRequestsPanel.add(acceptedRequestsProductQuantityLabel);
		
		Component rigidArea_13 = Box.createRigidArea(new Dimension(0, 8));
		currentRequestsPanel.add(rigidArea_13);
		
		JLabel acceptedRequestFactoryNameLabel = new JLabel(ManagerImpl.getManager().showDirectorInfo(directorName).getAcceptedRequest() == null? " ":
														    ManagerImpl.getManager().showDirectorInfo(directorName).getAcceptedRequest().getSentQuantity() + " kg of " + ManagerImpl.getManager().showDirectorInfo(directorName).getFactory().getMaterial().getProcessedMaterial());
		acceptedRequestFactoryNameLabel.setFont(new Font("Dialog", Font.ITALIC, 15));
		acceptedRequestFactoryNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		currentRequestsPanel.add(acceptedRequestFactoryNameLabel);
		
		Component rigidArea_15 = Box.createRigidArea(new Dimension(0, 16));
		currentRequestsPanel.add(rigidArea_15);
		
		JButton allRequestsButton = new JButton("All Requests");
		allRequestsButton.setMaximumSize(new Dimension(230, 35));
		allRequestsButton.setPreferredSize(new Dimension(230, 35));
		allRequestsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		allRequestsButton.addActionListener(e -> new RequestPopup(directorName, directorFrame));
		currentRequestsPanel.add(allRequestsButton);
		
		Component rigidArea_17 = Box.createRigidArea(new Dimension(20, 20));
		bottomDirectorPanel.add(rigidArea_17);
		
		JPanel operatorsPanel = new JPanel();
		operatorsPanel.setMaximumSize(new Dimension(150, 80));
		operatorsPanel.setBorder(new TitledBorder(null, "Operators Panel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		operatorsPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		bottomDirectorPanel.add(operatorsPanel);
		operatorsPanel.setLayout(new BoxLayout(operatorsPanel, BoxLayout.Y_AXIS));
		
		JLabel operatorsNumberLabel = new JLabel("Operators Number: " + Factory().getStuffMembers().getNumber());
		operatorsNumberLabel.setFont(new Font("Book Antiqua", Font.PLAIN, 12));
		operatorsNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		operatorsNumberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		operatorsPanel.add(operatorsNumberLabel);
		
		Component rigidArea_18 = Box.createRigidArea(new Dimension(0, 5));
		operatorsPanel.add(rigidArea_18);
		
		JButton openOperatorsButton = new JButton("Go To Work");
		openOperatorsButton.setHorizontalTextPosition(SwingConstants.CENTER);
		openOperatorsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		openOperatorsButton.addActionListener(e -> {
			directorFrame.dispose();
			new StaffFrame(directorName);
		});
		operatorsPanel.add(openOperatorsButton);
		
		Component rigidArea_11 = Box.createRigidArea(new Dimension(24, 0));
		baseBottomDirectorPanel.add(rigidArea_11, BorderLayout.WEST);
		
		Component rigidArea_14 = Box.createRigidArea(new Dimension(584, 21));
		baseBottomDirectorPanel.add(rigidArea_14, BorderLayout.SOUTH);
		
		return this;
	}
	
	private Factory Factory() {
		return ManagerImpl.getManager().showFactoryInfo(directorName);
	}
	
	public void newFrame() {
		directorFrame.dispose();
		new DirectorFrame(directorName);
	}

}
