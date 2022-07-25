package view.train;

import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.Popup;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DropMode;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.*;

import controller.classes.ManagerImpl;
import controller.interfaces.Manager;
import exceptions.EmptyDestinationsSetException;
import exceptions.FullTrainException;
import exceptions.FullWarehouseException;
import exceptions.LowTrainCapacityException;
import exceptions.NullManagerException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.classes.TrainImpl;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TransformAttribute;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.JComboBox;
import javax.swing.Box;
import javax.swing.JSpinner;
import javax.swing.JFormattedTextField;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;



public class TrainFrame {

	private JFrame frame;
	private JTextField currentDestianationTxt;
	private JTextField txtTrainCurrentCapacity;
	
	

	/**
	 * Create the application.
	 * @throws NullManagerException 
	 * @throws LowTrainCapacityException 
	 */
	public TrainFrame() throws NullManagerException{
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainFrame window = initialize();
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
	 * @throws NullManagerException 
	 * @throws LowTrainCapacityException 
	 */
	private TrainFrame initialize() throws NullManagerException{
		
		//Frame dell'interfaccia
		frame = new JFrame();
		frame.setBounds(100, 100, 703, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Casella di testo della capacità corrente/totale
		txtTrainCurrentCapacity = new JTextField();
		txtTrainCurrentCapacity.setEditable(false);
		txtTrainCurrentCapacity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtTrainCurrentCapacity.setBounds(449, 48, 229, 47);
		txtTrainCurrentCapacity.setText(Integer.toString(ManagerImpl.getManager().showTrainInfo().getCurrentCapacity()) 
										+ "/" + 
										Integer.toString(ManagerImpl.getManager().showTrainInfo().getMaxCapacity())
										+ " [Kg]");
		
		frame.getContentPane().add(txtTrainCurrentCapacity);
		txtTrainCurrentCapacity.setColumns(10);
		try {
			txtTrainCurrentCapacity.setText(Integer.toString(ManagerImpl.getManager().showTrainInfo().getCurrentCapacity()) 
											+ "/" + 
											Integer.toString(ManagerImpl.getManager().showTrainInfo().getMaxCapacity())
											+ " [Kg]");
		} catch (NullManagerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//casella di testo destinazione corrente
		currentDestianationTxt = new JTextField();
		currentDestianationTxt.setEditable(false);
		try {
			currentDestianationTxt.setText(ManagerImpl.getManager().showTrainInfo().getCurrentDestination().getName());
		} catch (NullPointerException e) {
			currentDestianationTxt.setText(null);
		}
		
		currentDestianationTxt.setBounds(459, 166, 213, 69);
		frame.getContentPane().add(currentDestianationTxt);
		currentDestianationTxt.setColumns(10);
		
		
		//Bottone della prossima destinazione
		JButton nextDestinationBtn = new JButton("Next Destination");
		nextDestinationBtn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nextDestinationBtn.setBounds(459, 245, 213, 41);
		frame.getContentPane().add(nextDestinationBtn);
		
		//EventListener del bottone di nextDestination
		nextDestinationBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				try {
					ManagerImpl.getManager().nextDestination();
					JOptionPane.showMessageDialog(frame, ManagerImpl.getManager().showTrainInfo().getQuantitytoUnLoad() + " Kg have been unloaded \n" + 
												  		 ManagerImpl.getManager().showTrainInfo().getQuantitytoLoad() + " Kg have been loaded");
					frame.dispose();
					new TrainFrame();
					
					
				} catch (FullWarehouseException e1) {
					JOptionPane.showMessageDialog(frame, "the warehouse is full...");
				} catch (FullTrainException e1) {
					JOptionPane.showMessageDialog(frame, "the train is full...");
				} catch (NullManagerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (EmptyDestinationsSetException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frame, "there isn't a next destination...");
				}
			}
			
			
		});
		
		
		
		
		//back button
		JButton backButton = new JButton("Back");
		backButton.setBounds(501, 372, 177, 41);
		backButton.addActionListener(e-> frame.dispose());
		frame.getContentPane().add(backButton);
		 
		//label capacità corrente treno
		JLabel currentCapacityLabel = new JLabel("Current Capacity/Total Capacity");
		currentCapacityLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		currentCapacityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		currentCapacityLabel.setBounds(449, 10, 229, 28);
		currentCapacityLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		frame.getContentPane().add(currentCapacityLabel);
		
		
		//label destinazione corrente
		JLabel lblNewLabel = new JLabel("Current Destination\r\n\r\n");
		lblNewLabel.setBounds(459, 138, 159, 28);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		frame.getContentPane().add(lblNewLabel);
		
		
		
		
		
		
		
		
		
		
		//label mappa del treno
		JLabel lblNewLabel_1 = new JLabel("Stuff Table");
		lblNewLabel_1.setBounds(10, 28, 129, 20);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.getContentPane().add(lblNewLabel_1);
		
		
		
		
		//Inizio costruzione Tabella risorse del treno
		int i = 0;
		String columnNames[] = {"Material", "Quantity[Kg]"};
		Object[][] data = new Object[ManagerImpl.getManager().showTrainInfo().getStuffMap().size()][2];
		for(Map.Entry<String, Integer> entry : ManagerImpl.getManager().showTrainInfo().getStuffMap().entrySet()) {

		  data[i][0] = entry.getKey();
		  data[i][1] = entry.getValue();
		  i++;
		}
		
		JTable table = new JTable(data, columnNames);
		table.setFillsViewportHeight(true);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.setDefaultRenderer(String.class, centerRenderer);
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {

		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		table.setModel(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 58, 255, 228);
		frame.getContentPane().add(scrollPane);
		//Fine costruzione Tabella del treno
	    
	    
		return this;
		
	}
}
