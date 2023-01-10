package view.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.PlayerController;
import interfaces.PuzzleFrameListener;
import interfaces.UserInformationListener;
import model.Player;
import view.components.CustomButton;
import view.components.JPhotoRound;

public class KernelFrame extends AbstractWindow {

	private JFrame frame;
	private JDesktopPane desktopPane;
	private PuzzleFrame puzzleFrame;

	public static void main(String[] args, Player player) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KernelFrame window = new KernelFrame(player);
					window.frame.setVisible(true);
					window.frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public KernelFrame(Player player) {
		super();
		initialize(player);
	}

	private void initialize(Player player) {

		PuzzleFrameListener puzzleListener = (puzzle, match, currentTime, typeGame) -> {
			puzzleFrame = new PuzzleFrame(player, puzzle, match, currentTime, typeGame);
			showFrame(puzzleFrame);
		};

		UserInformationListener userListener = (playerUpdated) -> {
			updateUserInformation(playerUpdated);
		};

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 1300, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		JPanel panelPersona = new JPanel();
		panelPersona.setBackground(new Color(45, 45, 45));
		panelPersona.setBounds(10, 63, 180, 217);
		panelPersona.setLayout(null);
		panelPersona.setVisible(false);

		JLabel lbPhotoPersona = new JPhotoRound(player.getPlayerUrlImage(), 110);
		lbPhotoPersona.setBounds(35, 10, 110, 110);

		JLabel lbUsername = new JLabel(player.getPlayerUsername());
		lbUsername.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbUsername.setForeground(new Color(255, 255, 255));
		lbUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lbUsername.setBounds(10, 132, 160, 30);

		JLabel lbEmail = new JLabel(player.getPlayerEmail());
		lbEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbEmail.setForeground(new Color(255, 255, 255));
		lbEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lbEmail.setBounds(10, 173, 160, 30);

		panelPersona.add(lbPhotoPersona);
		panelPersona.add(lbUsername);
		panelPersona.add(lbEmail);

		JButton btnPlay = new CustomButton("", "img\\icons\\icon-control.png", new Color(255, 255, 255),
				new Color(0, 0, 128), 0, 63, 200, 48);
		JButton btnConfig = new CustomButton("", "img\\icons\\icon-config.png", new Color(255, 255, 255),
				new Color(0, 0, 128), 0, 122, 200, 48);
		JButton btnLogout = new CustomButton("", "img\\icons\\icon-logout.png", new Color(255, 255, 255),
				new Color(0, 0, 128), 0, 200, 200, 48);

		JPanel panelLeftMenu = new JPanel();
		panelLeftMenu.setBounds(0, 0, 70, 711);
		panelLeftMenu.setBackground(new Color(60, 60, 60));
		panelLeftMenu.setLayout(null);
		panelLeftMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				panelLeftMenu.setBounds(0, 0, 200, 711);
				panelPersona.setVisible(true);
				btnPlay.setLocation(0, 300);
				btnConfig.setLocation(0, 360);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				panelLeftMenu.setBounds(0, 0, 70, 711);
				panelPersona.setVisible(false);
				btnPlay.setLocation(0, 63);
				btnConfig.setLocation(0, 122);
			}
		});

		MouseAdapter hoverEffect = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				e.getComponent().setBackground(new Color(249, 13, 72));
				panelLeftMenu.setBounds(0, 0, 200, 711);
				panelPersona.setVisible(true);
				btnPlay.setLocation(0, 300);
				btnConfig.setLocation(0, 360);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				e.getComponent().setBackground(new Color(0, 0, 128));
				panelLeftMenu.setBounds(0, 0, 70, 711);
				panelPersona.setVisible(false);
				btnPlay.setLocation(0, 63);
				btnConfig.setLocation(0, 122);
			}
		};

		desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(255, 255, 255));
		desktopPane.setBounds(200, 60, 1084, 651);

		btnPlay.setIcon(new ImageIcon("img\\icons\\icon-control.png"));
		btnPlay.setText("JOGAR ");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (puzzleFrame != null && puzzleFrame.stopwatchListener().getDuration() > 0) {
					if(puzzleFrame.stopwatchListener().isRunning()) puzzleFrame.stopwatchListener().pause();
					String message = "Deseja realmente sair?";
					Object[] options = { "Sim", "Não" };
					int response = JOptionPane.showOptionDialog(null, message, "Pergunta", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
					if (response == JOptionPane.YES_OPTION) {
						showFrame(new PreGameFrame(puzzleListener, player));
					}else{
						return;
					}
				}
				showFrame(new PreGameFrame(puzzleListener, player));
			}
		});
		btnPlay.addMouseListener(hoverEffect);

		btnConfig.setIcon(new ImageIcon("img\\icons\\icon-config.png"));
		btnConfig.setText("AJUSTE");
		btnConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (puzzleFrame != null && puzzleFrame.stopwatchListener().getDuration() > 0) {
					if(puzzleFrame.stopwatchListener().isRunning()) puzzleFrame.stopwatchListener().pause();
					String message = "Deseja realmente sair?";
					Object[] options = { "Sim", "Não" };
					int response = JOptionPane.showOptionDialog(null, message, "Pergunta", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
					if (response == JOptionPane.YES_OPTION) {
						showFrame(new PlayerFrame(player, userListener));
					}else{
						return;
					}
				}
				showFrame(new PlayerFrame(player, userListener));
			}
		});
		btnConfig.addMouseListener(hoverEffect);

		btnLogout.setLocation(0, 650);
		btnLogout.setIcon(new ImageIcon("img\\icons\\icon-logout.png"));
		btnLogout.setText("SAIR    ");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnLogout.addMouseListener(hoverEffect);

		JLabel lbIconMenu = new JLabel("");
		lbIconMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lbIconMenu.setIcon(new ImageIcon("img\\icons\\icon-menu.png"));
		lbIconMenu.setBounds(10, 11, 50, 40);

		JPanel panelHeader = new JPanel();
		panelHeader.setBounds(69, 0, 1215, 55);
		panelHeader.setBackground(new Color(45, 45, 45));
		panelHeader.setLayout(null);

		JLabel lbTitle = new JLabel("█▓▒░⡷⠂ S̳L̳I̳D̳E̳R̳ ̳P̳U̳Z̳Z̳L̳E̳ ⠐⢾░▒▓█");
		lbTitle.setForeground(new Color(240, 240, 240));
		lbTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbTitle.setBackground(new Color(240, 240, 240));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(10, 11, 1195, 33);
		panelHeader.add(lbTitle);

		Arrays.asList(lbIconMenu, panelPersona, btnPlay, btnConfig, btnLogout).forEach(panelLeftMenu::add);
		Arrays.asList(panelLeftMenu, desktopPane, panelHeader).forEach(frame.getContentPane()::add);
	}

	private void showFrame(Component frame) {
		desktopPane.removeAll();
		desktopPane.add(frame);
		frame.setVisible(true);
	}

	private void updateUserInformation(Player player) {
		Player playerSelected = PlayerController.getInstance().findById(player.getPlayerId());
		KernelFrame.main(null, playerSelected);
		frame.dispose();
	}

}
