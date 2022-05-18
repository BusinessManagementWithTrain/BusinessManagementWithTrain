package view.director.base;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class BaseDirectorLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	private static final int TITLE_SIZE = 25;
	
	public BaseDirectorLabel(String name) {
		super(name);
		this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, TITLE_SIZE));
		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setHorizontalAlignment(SwingConstants.CENTER);
	}
}
