package view.staff;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * 
 * 
 * @author Scaramuzzino Elia
 *
 */

public class StaffFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		new StaffFrame();
	}
	
	private JPanel staffPanel;
	private JTextField textField;
	private JButton button1, button2;
	
	
	
	public StaffFrame() {
		
		super("Interfaccia Staff");
		
		staffPanel = new JPanel();
		textField = new JTextField("");
		button1 = new JButton("Inizia a lavorare");
		button2 = new JButton("Finisci di lavorare");
		
		
		
		add(staffPanel);
		
		setSize(800,500);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		setVisible(true);
		System.out.println("");
	}
	

}
