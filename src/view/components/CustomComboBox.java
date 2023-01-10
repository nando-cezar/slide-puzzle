package view.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class CustomComboBox<T> extends JPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox<T> comboBox;
	private String title;
	private T[] model;
	private int x;
	private int y;
	private int width;
	private JSeparator separator;

	public CustomComboBox(String title, T[] model, int x, int y, int width) {
		super();
		this.title = title;
		this.model = model;
		this.x = x;
		this.y = y;
		this.width = width;
		this.initialize();
	}

	public CustomComboBox(String title, T[] model, int x, int y, int width, ItemListener listener) {
		this(title, model, x, y, width);
		comboBox.addItemListener(listener);
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
		this.setBounds(x, y, width, 62);
		this.setBackground(new Color(255, 255, 255));

		JLabel label = new CustomLabel(title, 0, 0, width, 15);

		comboBox = new JComboBox<T>();
		comboBox.setModel(new DefaultComboBoxModel<T>(model));
		comboBox.setBounds(0, 15, width, 40);
		comboBox.addMouseListener(hoverEffect);
		comboBox.setUI(new BasicComboBoxUI() {
			@Override
			protected void installDefaults() {
				comboBox.setBackground(new Color(255, 255, 255));
				comboBox.setForeground(new Color(0, 0, 0));
				comboBox.setFont(new Font("Tahoma", Font.BOLD, 15));
				comboBox.setFocusable(false);
			}

			@Override
			protected JButton createArrowButton() {
				final JButton button = new JButton(new ImageIcon("img\\icons\\icon-arrowdown.png"));
				button.addMouseListener(hoverEffect);
				button.setFocusPainted(false);
				button.setFocusable(false);
				button.setRequestFocusEnabled(false);
				button.setContentAreaFilled(false);
				return button;
			}

		});

		separator = new JSeparator();
		separator.setBounds(0, 55, width, 7);
		separator.setForeground(new Color(0, 0, 128));
		separator.setBackground(new Color(0, 0, 128));
		separator.addMouseListener(hoverEffect);

		Arrays.asList(label, comboBox, separator).forEach(this::add);
	}

	public Object getSelectedItem() {
		return comboBox.getSelectedItem();
	}
}
