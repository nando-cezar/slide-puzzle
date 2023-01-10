package view.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CustomButton extends JButton {

	private static final long serialVersionUID = 1L;
	private String iconPath;
	private Color colorForeground;
	private Color colorBackground;
	private int x;
	private int y;
	private int width;
	private int height;

	public CustomButton(String title, String iconPath, Color colorForeground, Color colorBackground, int x, int y,
			int width, int height) {
		super(title);
		this.iconPath = iconPath;
		this.colorForeground = colorForeground;
		this.colorBackground = colorBackground;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.initialize();
	}

	public CustomButton(String title, String iconPath, int x, int y, int width, int height) {
		this(title, iconPath, new Color(255, 255, 255), new Color(0, 0, 128), x, y, width, height);
	}

	private void initialize() {
		this.setFocusable(false);
		this.setIconTextGap(iconPath != null ? 70 : 0);
		this.setForeground(colorForeground);
		this.setBackground(colorBackground);
		this.setBorder(null);
		this.setIcon(new ImageIcon(iconPath));
		this.setContentAreaFilled(false);
		this.setOpaque(true);
		this.setFont(new Font("Tahoma", Font.BOLD, 15));
		this.setBounds(x, y, width, height);
		this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	}

}
