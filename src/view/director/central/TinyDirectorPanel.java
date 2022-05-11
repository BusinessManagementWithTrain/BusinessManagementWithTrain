package view.director.central;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.interfaces.Warehouse;

public class TinyDirectorPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final static int QUANTITY_LABEL_TEXT_SIZE = 15;
	private final static int PRODUCT_LABEL_TEXT_SIZE = 15;
	
	private JLabel quantityLabel;
	private JLabel productNameLabel;
	
	public TinyDirectorPanel(Warehouse warehouse, String warehouseName) {	
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		this.quantityLabel = new CentralDirectorLabel(warehouse.getCurrentCapacity() + "/" + warehouse.getTotalCapacity(), QUANTITY_LABEL_TEXT_SIZE);
		this.productNameLabel = new CentralDirectorLabel(/*warehouse.getMaterial()*/ "A", PRODUCT_LABEL_TEXT_SIZE);
		
		this.add(this.quantityLabel);
		this.add(this.productNameLabel);
		
	    this.setBorder(BorderFactory.createTitledBorder(warehouseName));
		this.setAlignmentX(SwingConstants.CENTER);
	}

}
