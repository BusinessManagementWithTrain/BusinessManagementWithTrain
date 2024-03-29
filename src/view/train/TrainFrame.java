package view.train;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JButton;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.*;

import controller.classes.ManagerImpl;
import exceptions.EmptyDestinationsQueueException;
import exceptions.EmptyWarehouseException;
import exceptions.FullTrainException;
import exceptions.FullWarehouseException;
import exceptions.LowTrainCapacityException;
import view.manager.ManagerFrame;

import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import java.awt.Font;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class TrainFrame {

	private JFrame frame;
	private JTextField currentDestinationtxt;
	private JTextField TrainCurrentCapacitytxt;
	
	/**
	 * Create the application. 
	 * @throws LowTrainCapacityException 
	 */
	public TrainFrame() {
		
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
	 * @throws LowTrainCapacityException 
	 */
	private TrainFrame initialize() {
		
		//Frame dell'interfaccia
		frame = new JFrame();
		frame.setBounds(100, 100, 703, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		//Casella di testo della capacità corrente/totale
		TrainCurrentCapacitytxt = new JTextField();
		TrainCurrentCapacitytxt.setHorizontalAlignment(JTextField.CENTER);
		TrainCurrentCapacitytxt.setEditable(false);
		TrainCurrentCapacitytxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		TrainCurrentCapacitytxt.setBounds(449, 48, 229, 47);
		TrainCurrentCapacitytxt.setText(Integer.toString(ManagerImpl.getManager().showTrainInfo().getCurrentCapacity()) 
										+ "/" + 
										Integer.toString(ManagerImpl.getManager().showTrainInfo().getMaxCapacity())
										+ " [Kg]");
		
		frame.getContentPane().add(TrainCurrentCapacitytxt);
		TrainCurrentCapacitytxt.setColumns(10);
		TrainCurrentCapacitytxt.setText(Integer.toString(ManagerImpl.getManager().showTrainInfo().getCurrentCapacity()) 
										+ "/" + 
										Integer.toString(ManagerImpl.getManager().showTrainInfo().getMaxCapacity())
										+ " [Kg]");
		
		//casella di testo destinazione corrente
		currentDestinationtxt = new JTextField();
		currentDestinationtxt.setHorizontalAlignment(JTextField.CENTER);
		currentDestinationtxt.setFont(new Font("Serif", Font.ITALIC, 25));
		currentDestinationtxt.setEditable(false);
		try {
			currentDestinationtxt.setText(ManagerImpl.getManager().showTrainInfo().getCurrentDestination().getName());
		} catch (NullPointerException e) {
			currentDestinationtxt.setText(null);
		}
		
		currentDestinationtxt.setBounds(459, 166, 213, 69);
		frame.getContentPane().add(currentDestinationtxt);
		currentDestinationtxt.setColumns(10);
		
		
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
					JOptionPane.showMessageDialog(frame, ManagerImpl.getManager().showTrainInfo().getQuantitytoUnload() + " Kg have been unloaded \n" + 
												  		 ManagerImpl.getManager().showTrainInfo().getQuantitytoLoad() + " Kg have been loaded");
				} catch(FullWarehouseException e1) {
					JOptionPane.showMessageDialog(frame,
		  					  					  "The loading warehouse hasn't enough free space."
		  					  					  + "\nThe stuff will be send to the store!",
		  					  					  "ERROR!",
		  					  					  JOptionPane.ERROR_MESSAGE);

				} catch(EmptyDestinationsQueueException e1) {
					JOptionPane.showMessageDialog(frame,
		  					  					  "There isn't a next destination!",
		  					  					  "ERROR!",
		  					  					  JOptionPane.ERROR_MESSAGE);

				} catch(FullTrainException e1) {
					JOptionPane.showMessageDialog(frame,
		  					  					  "The train can't load entirely your stuff, we loaded the possible amount,\n"
		  					  					  + "check the Stuff Table.\nWe will try to load the remaining stuff later!",
		  					  					  "Warning!",
		  					  					  JOptionPane.WARNING_MESSAGE);

				} catch(EmptyWarehouseException e1) {
					JOptionPane.showMessageDialog(frame,
		  					  					  "The unloading warehouse hasn't enough material.\nWe loaded the possible amount,"
		  					  					  + "check the Stuff Table.\nWe will try to load the remaining stuff later!",
		  					  					  "Warning!",
		  					  					  JOptionPane.WARNING_MESSAGE);
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(0);
				} finally {
					frame.dispose();
					new TrainFrame();
				}
			}
		});
		
		
		//back button
		JButton backButton = new JButton("Back");
		backButton.setBounds(501, 372, 177, 41);
		backButton.addActionListener(e-> {
			frame.dispose();
			new ManagerFrame();
		});
		frame.getContentPane().add(backButton);
		 
		//label capacità corrente treno
		JLabel currentCapacityLabel = new JLabel("Current Capacity/Total Capacity");
		currentCapacityLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		currentCapacityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		currentCapacityLabel.setBounds(449, 10, 229, 28);
		currentCapacityLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		frame.getContentPane().add(currentCapacityLabel);
		
		
		//label destinazione corrente
		JLabel CurrDestLabel = new JLabel("Current Destination\r\n\r\n");
		CurrDestLabel.setBounds(459, 138, 159, 28);
		CurrDestLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		frame.getContentPane().add(CurrDestLabel);
		
		
		
		//label mappa del treno
		JLabel tableLabel = new JLabel("Stuff Table");
		tableLabel.setBounds(10, 28, 129, 20);
		tableLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.getContentPane().add(tableLabel);
		
		
		
		
		//Inizio costruzione Tabella risorse del treno
		int i = 0;
		String columnNames[] = {"Material", "Quantity[Kg]"};
		Object[][] data = new Object[ManagerImpl.getManager().showTrainInfo().getStuffMap().size()][2];
		for(Map.Entry<String, Integer> entry : ManagerImpl.getManager().showTrainInfo().getStuffMap().entrySet()) {

		  data[i][0] = entry.getKey();
		  data[i][1] = entry.getValue();
		  i++;
		}
		
		JTable trainMaterialsTable = new JTable(data, columnNames);
		trainMaterialsTable.setFillsViewportHeight(true);
		trainMaterialsTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		trainMaterialsTable.setDefaultRenderer(String.class, centerRenderer);
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
		
		trainMaterialsTable.setModel(tableModel);
		JScrollPane tableScrollPane = new JScrollPane(trainMaterialsTable);
		tableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tableScrollPane.setBounds(10, 58, 255, 228);
		frame.getContentPane().add(tableScrollPane);
		//Fine costruzione Tabella del treno
		return this;
		
	}
}
