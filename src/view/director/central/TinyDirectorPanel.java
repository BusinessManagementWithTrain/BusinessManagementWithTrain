package view.director.central;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TinyDirectorPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final static int QUANTITY_LABEL_TEXT_SIZE 	= 15;
	private final static int PRODUCT_LABEL_TEXT_SIZE 	= 15;
	private final static int WIDTH 						= 200;
	private final static int RIGID_AREA_H_GAP 			= 7;
	
	private JLabel quantityLabel;
	private JLabel productNameLabel;
	private JButton tinyDirectorButton;
	
	public TinyDirectorPanel(final String quantityLabel, final String productNameLabel, final String warehouseName, final String actionButton, final ActionListener event) {	
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		this.quantityLabel = new TinyDirectorLabel(quantityLabel, QUANTITY_LABEL_TEXT_SIZE);
		this.productNameLabel = new TinyDirectorLabel(productNameLabel, PRODUCT_LABEL_TEXT_SIZE);
		this.tinyDirectorButton = new TinyDirectorButton(actionButton, event);
		
		this.add(Box.createRigidArea(new Dimension(0, RIGID_AREA_H_GAP)));
		this.add(this.quantityLabel);
		this.add(Box.createRigidArea(new Dimension(0, RIGID_AREA_H_GAP)));
		this.add(this.productNameLabel);
		this.add(Box.createRigidArea(new Dimension(0, RIGID_AREA_H_GAP * 2)));
		this.add(this.tinyDirectorButton);
		
		this.setPreferredSize(new Dimension(WIDTH, 0));
		this.setBorder(BorderFactory.createTitledBorder(warehouseName));
	}

}
