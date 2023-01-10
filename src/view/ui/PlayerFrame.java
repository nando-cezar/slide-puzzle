package view.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import controller.MatchController;
import controller.PieceController;
import controller.PlayerController;
import controller.PlayerMatchController;
import controller.PuzzleController;
import interfaces.UserInformationListener;
import model.Player;
import model.RecordPlayerMatch;
import util.Format;
import util.ImageManager;
import view.components.CustomButton;
import view.components.CustomField;
import view.components.JPhotoRound;

public class PlayerFrame extends JPanel {

	private static final long serialVersionUID = 1L;
	private Player player;
	private UserInformationListener userListener;
	private JPhotoRound photoPersona;
	private RecordPlayerMatch recordPlayerMatch;
	private CustomField fieldUsername;
	private CustomField fieldEmail;
	private CustomField currentPassword;
	private CustomField newPassword;
	private JRadioButton changePassword;

	public PlayerFrame(Player player, UserInformationListener userListener) {
		super();
		this.player = player;
		this.userListener = userListener;
		this.recordPlayerMatch = PlayerMatchController.getInstance().recordPlayerMatchByPlayer(player.getPlayerId());
		initialize();
	}

	public void initialize() {
		this.setBounds(0, 0, 1175, 670);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);

		fieldUsername = new CustomField("Nome do usuário:", 300, 500, 50, false);
		fieldEmail = new CustomField("E-mail:", 300, 500, 150, false);
		currentPassword = new CustomField("Senha atual:", 300, 500, 300, true);
		newPassword = new CustomField("Nova senha:", 300, 500, 400, true);

		currentPassword.setVisible(false);
		newPassword.setVisible(false);

		fieldUsername.setText(player.getPlayerUsername());
		fieldEmail.setText(player.getPlayerEmail());

		CustomButton btnRegister = new CustomButton("Atualizar", "img\\icons\\icon-update.png",
				new Color(255, 255, 255), new Color(0, 0, 128), 500, 590, 250, 50);
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateInformation();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnRegister.setBackground(new Color(249, 13, 72));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnRegister.setBackground(new Color(0, 0, 128));
			}
		});

		CustomButton btnReset = new CustomButton("Limpar histórico", "img\\icons\\icon-remove.png",
				new Color(255, 255, 255), new Color(0, 0, 128), 800, 590, 250, 50);
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String message = "Tem certeza que deseja fazer isso?\nIsso irá limpar todo histórico do jogo.";
				Object[] options = { "Sim", "Não" };
				int response = JOptionPane.showOptionDialog(null, message, "Pergunta", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

				if (response == JOptionPane.YES_OPTION) {
					PieceController.getInstance().removeAll();
					PlayerMatchController.getInstance().removeAll();
					MatchController.getInstance().removeAll();
					PuzzleController.getInstance().removeAll();
				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnReset.setBackground(new Color(249, 13, 72));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnReset.setBackground(new Color(0, 0, 128));
			}
		});

		JPanel panelInformationPersona = new JPanel();
		panelInformationPersona.setBackground(new Color(205, 205, 205));
		panelInformationPersona.setBounds(10, 11, 457, 630);
		panelInformationPersona.setLayout(null);

		photoPersona = new JPhotoRound(player.getPlayerUrlImage(), 350);
		photoPersona.setCursor(new Cursor(Cursor.HAND_CURSOR));
		photoPersona.setBounds(51, 21, 350, 350);

		JSeparator separatorPhotoPersona = new JSeparator();
		separatorPhotoPersona.setBounds(10, 382, 437, 7);
		separatorPhotoPersona.setForeground(new Color(249, 13, 72));
		separatorPhotoPersona.setBackground(new Color(249, 13, 72));

		JLabel lbTitleInformationPersona = new JLabel("Informações gerais");
		lbTitleInformationPersona.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbTitleInformationPersona.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitleInformationPersona.setBounds(10, 400, 437, 20);

		JLabel titleTotalMatch = new JLabel("Total de partidas: ");
		titleTotalMatch.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleTotalMatch.setBounds(10, 431, 136, 25);

		JLabel titleMatchComplete = new JLabel("Partidas Completas: ");
		titleMatchComplete.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleMatchComplete.setBounds(10, 456, 136, 25);

		JLabel titleMatchNotComplete = new JLabel("Partidas nao completadas:");
		titleMatchNotComplete.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleMatchNotComplete.setBounds(213, 456, 165, 25);

		JLabel titleTotalPoints = new JLabel("Total de pontos: ");
		titleTotalPoints.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleTotalPoints.setBounds(10, 570, 113, 25);

		JLabel titleMaxPoints = new JLabel("Maior pontuação:  ");
		titleMaxPoints.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleMaxPoints.setBounds(223, 594, 116, 25);

		JLabel titleSmallerPoints = new JLabel("Menor pontuação: ");
		titleSmallerPoints.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleSmallerPoints.setBounds(10, 594, 124, 25);

		JLabel titleTotalDuration = new JLabel("Total de horas jogadas: ");
		titleTotalDuration.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleTotalDuration.setBounds(10, 517, 156, 25);

		JLabel titleMaxDuration = new JLabel("Maior duração : ");
		titleMaxDuration.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleMaxDuration.setBounds(10, 545, 113, 25);

		JLabel titleMinDuration = new JLabel("Menor duração :  ");
		titleMinDuration.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleMinDuration.setBounds(223, 545, 113, 25);

		JLabel titleAvgPoints = new JLabel("Média de pontos: ");
		titleAvgPoints.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleAvgPoints.setBounds(223, 570, 124, 25);

		JLabel textTotalMatch = new JLabel(String.valueOf(recordPlayerMatch.getTotalMatch()));
		textTotalMatch.setHorizontalAlignment(SwingConstants.RIGHT);
		textTotalMatch.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textTotalMatch.setBounds(147, 431, 50, 25);

		JLabel textMatchComplete = new JLabel(String.valueOf(recordPlayerMatch.getTotalMatchCompleted()));
		textMatchComplete.setHorizontalAlignment(SwingConstants.RIGHT);
		textMatchComplete.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textMatchComplete.setBounds(147, 456, 50, 25);

		JLabel textMatchNotComplete = new JLabel(String.valueOf(recordPlayerMatch.getTotalMatchNotCompleted()));
		textMatchNotComplete.setHorizontalAlignment(SwingConstants.RIGHT);
		textMatchNotComplete.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textMatchNotComplete.setBounds(383, 456, 50, 25);

		JLabel textTotalDuration = new JLabel(Format.hours(recordPlayerMatch.getTotalDuration()));
		textTotalDuration.setHorizontalAlignment(SwingConstants.CENTER);
		textTotalDuration.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textTotalDuration.setBounds(167, 517, 280, 25);

		JLabel textMaxDuration = new JLabel(Format.hours(recordPlayerMatch.getMaxDuration()));
		textMaxDuration.setHorizontalAlignment(SwingConstants.RIGHT);
		textMaxDuration.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textMaxDuration.setBounds(121, 545, 93, 25);

		JLabel textMinDuration = new JLabel(Format.hours(recordPlayerMatch.getMinDuration()));
		textMinDuration.setHorizontalAlignment(SwingConstants.RIGHT);
		textMinDuration.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textMinDuration.setBounds(335, 546, 112, 25);

		JLabel textTotalPoints = new JLabel(Format.punctuation(recordPlayerMatch.getTotalPoints()));
		textTotalPoints.setHorizontalAlignment(SwingConstants.RIGHT);
		textTotalPoints.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textTotalPoints.setBounds(131, 570, 82, 25);

		JLabel textMaxPoints = new JLabel(Format.punctuation(recordPlayerMatch.getMaxPoints()));
		textMaxPoints.setHorizontalAlignment(SwingConstants.RIGHT);
		textMaxPoints.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textMaxPoints.setBounds(344, 594, 103, 25);

		JLabel textSmallerPoints = new JLabel(Format.punctuation(recordPlayerMatch.getMinPoints()));
		textSmallerPoints.setHorizontalAlignment(SwingConstants.RIGHT);
		textSmallerPoints.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textSmallerPoints.setBounds(141, 594, 72, 25);

		JLabel textAvgPoints = new JLabel(Format.punctuation(recordPlayerMatch.getAvgPoints()));
		textAvgPoints.setHorizontalAlignment(SwingConstants.RIGHT);
		textAvgPoints.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textAvgPoints.setBounds(346, 570, 99, 25);

		JSeparator separatorPhotoPersonaSecondary = new JSeparator();
		separatorPhotoPersonaSecondary.setForeground(new Color(74, 74, 74));
		separatorPhotoPersonaSecondary.setBackground(new Color(74, 74, 74));
		separatorPhotoPersonaSecondary.setBounds(10, 499, 437, 7);
		panelInformationPersona.add(separatorPhotoPersonaSecondary);

		changePassword = new JRadioButton("Alterar senha");
		changePassword.setBackground(Color.WHITE);
		changePassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		changePassword.setBounds(680, 251, 120, 23);
		changePassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (changePassword.isSelected()) {
					currentPassword.setVisible(true);
					newPassword.setVisible(true);
				}

				if (!changePassword.isSelected()) {
					currentPassword.setVisible(false);
					newPassword.setVisible(false);
				}
			}
		});

		Arrays.asList(fieldUsername, fieldEmail, currentPassword, newPassword,
				btnRegister, btnReset, panelInformationPersona, changePassword).forEach(this::add);

		Arrays.asList(photoPersona, separatorPhotoPersona, lbTitleInformationPersona, titleTotalMatch,
				titleMatchComplete, titleMatchNotComplete, titleTotalPoints, titleMaxPoints,
				titleSmallerPoints, titleTotalDuration, titleMaxDuration, titleMinDuration,
				titleAvgPoints, textTotalMatch, textMatchComplete, textMatchNotComplete,
				textTotalDuration, textMaxDuration, textMinDuration, textTotalPoints,
				textMaxPoints, textSmallerPoints, textAvgPoints).forEach(panelInformationPersona::add);

		photoPersona.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Object message = "O que deseja fazer com a foto do seu perfil ?";
				Object[] options = { "Editar", "Remover" };
				int response = JOptionPane.showOptionDialog(null, message, "Pergunta", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

				if (response == JOptionPane.YES_OPTION) {
					updatePhoto();
				} else {
					removePhoto();
				}
			}
		});
	}

	private void updateInformation() {

		String currentPasswordInt = Arrays.toString(currentPassword.getPassword());
		String newPasswordInt = Arrays.toString(newPassword.getPassword());
		String username = fieldUsername.getText();
		String email = fieldEmail.getText();
		String image = photoPersona.getPath();
		boolean isValid = false;

		if (changePassword.isSelected()) {

			if (currentPassword.getPassword().length == 0 ||
					newPassword.getPassword().length == 0 ||
					newPasswordInt.isBlank() ||
					currentPasswordInt.isBlank() ||
					username.isBlank() ||
					email.isBlank()) {
				JOptionPane.showMessageDialog(null, "Preencha todas as informações necessaria.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			} else if (!currentPasswordInt.equals(newPasswordInt)) {
				JOptionPane.showMessageDialog(null, "Senha não coincidem.", "Erro", JOptionPane.ERROR_MESSAGE);
			} else {
				player.setPlayerUrlImage(image);
				player.setPlayerUsername(username);
				player.setPlayerEmail(email);
				player.setPlayerPassword(newPasswordInt);
				isValid = true;
			}
		} else {

			if (username.isBlank() || email.isBlank()) {
				JOptionPane.showMessageDialog(null, "Preencha todas as informações necessaria.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			} else {
				player.setPlayerUrlImage(image);
				player.setPlayerUsername(username);
				player.setPlayerEmail(email);
				isValid = true;
			}
		}

		if (isValid) {
			if (PlayerController.getInstance().update(player)) {
				String message = "As informações foram atualizadas. Agora, o sistema será reinicializado com as novas informações!";
				JOptionPane.showMessageDialog(null, message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				userListener.onClick(player);
			}
		}
	}

	private void updatePhoto() {
		ImageManager imageManager = new ImageManager("img\\players\\");
		photoPersona.setPath(imageManager.getAbsolutePath());
	}

	private void removePhoto() {
		String path = "img\\icons\\icon-persona.png";
		photoPersona.setPath(path);
	}
}
