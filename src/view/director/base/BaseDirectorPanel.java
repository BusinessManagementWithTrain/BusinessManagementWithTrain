package view.director.base;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.classes.ManagerImpl;
import view.director.central.CentralDirectorPanel;

public class BaseDirectorPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private final static int V_GAP = 10;
	
	private JLabel baseDirectorLabel;
	private JPanel centralDirectorPanel;
	
	public BaseDirectorPanel(String directorName) {
		this.setLayout(new BorderLayout());
		
		this.baseDirectorLabel = new BaseDirectorLabel(ManagerImpl.getManager(0).showFactoryInfo(directorName).getName());
		this.centralDirectorPanel = new CentralDirectorPanel(directorName);
		
		this.setBorder(BorderFactory.createEmptyBorder(V_GAP, V_GAP, 0, 0));
		
		this.add(this.baseDirectorLabel, BorderLayout.PAGE_START);
		this.add(this.centralDirectorPanel, BorderLayout.CENTER);
		
		//centro
		//basso
	}
}
