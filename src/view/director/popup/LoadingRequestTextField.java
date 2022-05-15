package view.director.popup;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class LoadingRequestTextField extends JTextField {
	private static final long serialVersionUID = 1L;
	
	private static final Dimension SIZE = new Dimension(200, 50);
	private static final int FONT_SIZE = 20;

	public LoadingRequestTextField(String componentName) {
		
		this.setFont(new Font(Font.SERIF, 0, FONT_SIZE));
		this.setToolTipText("Insert amount needed here!");
		this.setBorder(BorderFactory.createTitledBorder(componentName));
		this.setMaximumSize(SIZE);
	}
}
