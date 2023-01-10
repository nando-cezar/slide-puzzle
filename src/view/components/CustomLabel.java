package view.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class CustomLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;

	public CustomLabel(String text, int x, int y, int width, int height) {
		super(text);
		this.setBounds(x, y, width, height);
		this.setForeground(new Color(69, 69, 69));
		this.setFont(new Font("Tahoma", Font.BOLD, 15));
	}
}
