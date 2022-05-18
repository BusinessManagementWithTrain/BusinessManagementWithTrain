package view.director.popup;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PopupButton extends JButton {
	private static final long serialVersionUID = 1L;
	
	private static final Dimension SIZE = new Dimension(100, 40);

	public PopupButton(final String name, final ActionListener event) {
		super(name);
		
		this.setMaximumSize(SIZE);
		this.setActionCommand(name);
		this.addActionListener(event);
	}
}
