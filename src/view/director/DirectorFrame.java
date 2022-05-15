package view.director;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.classes.ManagerImpl;
import model.classes.DirectorImpl;
import model.classes.FactoryImpl;
import model.classes.MaterialImpl;
import view.director.base.BaseDirectorPanel;

/**
 * 
 * 
 * @author Grasso Emanuele
 *  
 */

public class DirectorFrame extends JFrame {
	
	private static final int FRAME_HEIGTH = 400;
	private static final int FRAME_WIDTH = 500;
	
	public static void main(String[] args) {
		ManagerImpl.getManager(100).hireDirector(new DirectorImpl("Gigio", new FactoryImpl("Azienda 1", new MaterialImpl("Ferro", "Acciaio"), 6, 600, 500)));
		new DirectorFrame("Gigio");
	}
	
	private static final long serialVersionUID = 1;
	
	private JPanel baseDirectorPanel;
	
	ImageIcon img = new ImageIcon("C:\\Users\\grass\\OneDrive\\Desktop\\BusinessManagementWithTrain\\Project\\images\\Logo_Direttore.jpg");
	
	public DirectorFrame(String directorName) {
		super("Hi Mr. " + ManagerImpl.getManager(0).showDirectorInfo(directorName).getName() + ", welcome back!");
		
		this.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGTH));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		ManagerImpl.getManager(100).hireDirector(new DirectorImpl(directorName, new FactoryImpl("Azienda 1", null, 5, 500, 1000)));
		
		this.baseDirectorPanel = new BaseDirectorPanel(directorName);
		
		this.add(baseDirectorPanel);
		this.setVisible(true);
	}

	
}
