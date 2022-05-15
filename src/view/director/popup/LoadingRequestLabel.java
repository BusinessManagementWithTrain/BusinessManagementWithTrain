package view.director.popup;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LoadingRequestLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	
	private static final Dimension SIZE = new Dimension(200, 50);
	private static final int FONT_SIZE = 20;
	
	public LoadingRequestLabel(String componentName, String labelBody) {
		super(labelBody, SwingConstants.CENTER);
		
		this.setAlignmentX(CENTER_ALIGNMENT);
		this.setFont(new Font(Font.SERIF, Font.ITALIC, FONT_SIZE));
		this.setBorder(BorderFactory.createTitledBorder(componentName));
		this.setMaximumSize(SIZE);
	}
}
