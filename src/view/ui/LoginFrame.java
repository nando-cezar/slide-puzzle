package view.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;

import controller.PlayerController;

import java.awt.Color;

import model.Player;
import view.components.CustomButton;
import view.components.CustomField;

import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginFrame extends AbstractWindow {

	private JFrame frame;
	private CustomField fieldLoginUsername;
	private CustomField fieldLoginPassword;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame window = new LoginFrame();
					window.frame.setVisible(true);
					window.frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginFrame() {
		super();
		initialize();
	}

	protected void initialize() {
		MouseAdapter hoverEffect = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				e.getComponent().setBackground(new Color(249, 13, 72));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				e.getComponent().setBackground(new Color(0, 0, 128));
			}
		};

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(1000, 400, 850, 606);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(504, 22, 320, 545);
		tabbedPane.setOpaque(false);
		frame.getContentPane().add(tabbedPane);

		JPanel containerLogin = new JPanel();
		containerLogin.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Entrar", null, containerLogin, null);
		containerLogin.setLayout(null);

		fieldLoginUsername = new CustomField("Nome do usuário:", 270, 30, 50, false);
		fieldLoginPassword = new CustomField("Senha:", 270, 30, 150, true);
		CustomButton btnLogin = new CustomButton("Entrar", "img\\icons\\icon-login.png", new Color(255, 255, 255),
				new Color(0, 0, 128), 20, 279, 270, 50);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Player player = new Player(fieldLoginUsername.getText(),
						Arrays.toString(fieldLoginPassword.getPassword()));

				Player playerSelected = PlayerController.getInstance().authenticate(player);

				if (playerSelected.getPlayerId() != null) {
					KernelFrame.main(null, playerSelected);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(null,
							"Dados inválidos, por favor insira as credenciais novamente!", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnLogin.addMouseListener(hoverEffect);

		JLabel lbRecover = new JLabel("Esqueceu sua senha?");
		lbRecover.setBounds(20, 418, 270, 23);
		containerLogin.add(lbRecover);
		lbRecover.setHorizontalAlignment(SwingConstants.CENTER);
		lbRecover.setForeground(new Color(249, 13, 72));
		lbRecover.setFont(new Font("Tahoma", Font.BOLD, 15));

		JPanel containerRegister = new JPanel();
		containerRegister.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Cadastrar-se", null, containerRegister, null);
		containerRegister.setLayout(null);

		CustomField fieldRegisterUsername = new CustomField("Nome do usuário:", 270, 30, 50, false);
		CustomField fieldRegisterEmail = new CustomField("E-mail:", 270, 30, 150, false);
		CustomField fieldRegisterPassword = new CustomField("Senha:", 270, 30, 250, true);

		CustomButton btnRegister = new CustomButton("Cadastrar", "img\\icons\\icon-adduser.png",
				new Color(255, 255, 255), new Color(0, 0, 128), 21, 374, 270, 50);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Player player = new Player(fieldRegisterUsername.getText(), fieldRegisterEmail.getText(),
						Arrays.toString(fieldRegisterPassword.getPassword()));

				if (PlayerController.getInstance().save(player)) {
					JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!", "Sucesso",
							JOptionPane.INFORMATION_MESSAGE);
					tabbedPane.setSelectedIndex(0);
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário!", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnRegister.addMouseListener(hoverEffect);

		Arrays.asList(fieldLoginUsername, fieldLoginPassword, btnLogin).forEach(containerLogin::add);
		Arrays.asList(fieldRegisterUsername, fieldRegisterEmail, fieldRegisterPassword, btnRegister)
				.forEach(containerRegister::add);

		JLabel lbBG = new JLabel("");
		BufferedImage resized;
		try {
			resized = ImageIO.read(new File("img\\bgs\\bg-login.jpg"));
			Image image = resized.getScaledInstance(494, 567, 1);
			lbBG.setIcon(new ImageIcon(image));
			lbBG.setHorizontalAlignment(SwingConstants.CENTER);
			lbBG.setBounds(0, 0, 494, 567);
			frame.getContentPane().add(lbBG);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
