package view.director.central;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class TinyDirectorButton extends JButton {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 180;
	private static final int HEIGHT = 35;
	
	public TinyDirectorButton(final String name, final ActionListener event) {
		super(name);
		
		this.setAlignmentX(CENTER_ALIGNMENT);
		this.addActionListener(event);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(getPreferredSize());
	}
}
