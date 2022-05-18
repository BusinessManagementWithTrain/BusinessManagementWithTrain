package view.director.central;

import java.awt.Font;

import javax.swing.JLabel;

public class TinyDirectorLabel extends JLabel{
	private static final long serialVersionUID = 1L;
	
	public TinyDirectorLabel(final String text, final int size) {
		super(text);
		
		this.setAlignmentX(CENTER_ALIGNMENT);
		this.setFont(new Font(Font.DIALOG, Font.ITALIC, size));
	}
}
