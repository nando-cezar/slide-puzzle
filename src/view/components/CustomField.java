package view.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CustomField extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField fieldCommon;
	private JPasswordField fieldPassword;
	private String title;
	private int width;
	private int x;
	private int y;
	private boolean isFieldPassword;
	private JSeparator separator;

	public CustomField(String title, int width, int x, int y, boolean isFieldPassword) {
		this.title = title;
		this.width = width;
		this.x = x;
		this.y = y;
		this.isFieldPassword = isFieldPassword;
		this.initialize();
	}

	public void initialize() {

		MouseAdapter hoverEffect = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				separator.setForeground(new Color(249, 13, 72));
				separator.setBackground(new Color(249, 13, 72));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				separator.setForeground(new Color(0, 0, 128));
				separator.setBackground(new Color(0, 0, 128));
			}
		};


		this.setLayout(null);
		this.setBorder(null);
		this.setBounds(x, y, width, 75);
		this.setBackground(new Color(255, 255, 255));

		JLabel label = new CustomLabel(title, 0, 0, width, 30);

		if (!isFieldPassword) {
			fieldCommon = new JTextField();
			fieldCommon.setBounds(0, 30, width, 40);
			fieldCommon.setHorizontalAlignment(SwingConstants.CENTER);
			fieldCommon.setForeground(new Color(0, 0, 0));
			fieldCommon.setFont(new Font("Tahoma", Font.PLAIN, 15));
			fieldCommon.setColumns(10);
			fieldCommon.setBorder(null);
			fieldCommon.setBackground(new Color(255, 255, 255));
			fieldCommon.addMouseListener(hoverEffect);
			this.add(fieldCommon);
		} else {
			fieldPassword = new JPasswordField();
			fieldPassword.setBounds(0, 30, width, 40);
			fieldPassword.setHorizontalAlignment(SwingConstants.CENTER);
			fieldPassword.setForeground(new Color(0, 0, 0));
			fieldPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
			fieldPassword.setBorder(null);
			fieldPassword.setBackground(new Color(255, 255, 255));
			fieldPassword.addMouseListener(hoverEffect);
			this.add(fieldPassword);
		}

		separator = new JSeparator();
		separator.setBounds(0, 70, width, 2);
		separator.setForeground(new Color(0, 0, 128));
		separator.setBackground(new Color(0, 0, 128));
		separator.addMouseListener(hoverEffect);

		Arrays.asList(label, separator).forEach(this::add);
	}

	public String getText() {
		return fieldCommon.getText();
	}

	public void setText(String text) {
		fieldCommon.setText(text);
	}

	public char[] getPassword() {
		return fieldPassword.getPassword();
	}

}
