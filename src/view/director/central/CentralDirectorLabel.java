package view.director.central;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CentralDirectorLabel extends JLabel{
	private static final long serialVersionUID = 1L;
	
	public CentralDirectorLabel(final String text, final int size) {
		super(text);
		this.setFont(new Font(Font.DIALOG, Font.ITALIC, size));
	}
}
