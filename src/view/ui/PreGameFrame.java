package view.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.PlayerMatchController;
import interfaces.HoverEffect;
import interfaces.PuzzleFrameListener;
import model.Match;
import model.Player;
import model.PlayerMatch;
import model.Puzzle;
import util.ImageManager;
import util.enums.TypeGame;
import util.enums.TypeShuffle;
import view.components.CustomButton;
import view.components.CustomComboBox;
import view.components.CustomLabel;
import view.components.JPhotoRound;
import view.components.RankingSpecificFrame;

public class PreGameFrame extends JPanel {

	private static final long serialVersionUID = 1L;
	private CustomButton buttonChooseImage;
	private CustomComboBox<Object> cbShuffle;
	private CustomComboBox<Object> cbSize;
	private CustomComboBox<Object> cbType;
	private JPanel containerImage;
	private MouseListener hoverChooseImage;
	private JPhotoRound lbImage;
	private Map<String, TypeShuffle> optionsShuffle;
	private Map<String, Integer> optionsSize;
	private Map<String, TypeGame> optionsType;
	private Player player;
	private List<PlayerMatch> playerMatch;
	private PuzzleFrameListener puzzleFrameListener;
	private RankingSpecificFrame ranking;

	private TypeGame typeGame;

	public PreGameFrame(PuzzleFrameListener puzzleFrameListener, Player player) {
		super();
		this.typeGame = TypeGame.newGame;
		this.playerMatch = PlayerMatchController.getInstance().findAll();
		this.player = player;
		this.puzzleFrameListener = puzzleFrameListener;
		optionsSize = new HashMap<String, Integer>() {
			private static final long serialVersionUID = 1L;
			{
				put("2x2", 2);
				put("3x3", 3);
				put("4x4", 4);
				put("5x5", 5);
			}
		};

		optionsShuffle = new HashMap<String, TypeShuffle>() {
			private static final long serialVersionUID = 1L;
			{
				put("Par", TypeShuffle.pairs);
				put("Ímpar", TypeShuffle.odd);
			}
		};

		optionsType = new HashMap<String, TypeGame>() {
			private static final long serialVersionUID = 1L;
			{
				put("Nova partida", TypeGame.newGame);
				put("Partida pausada", TypeGame.pausedGame);
				put("Partida multijogador", TypeGame.multiplayerGame);
			}
		};
		initialize();

	}

	private void configureGameOptions(JPanel panelRight, ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			typeGame = optionsType.get(e.getItem().toString());
			if (typeGame == TypeGame.newGame) {
				panelRight.setVisible(false);
				Arrays.asList(cbShuffle, cbSize, buttonChooseImage).forEach(cb -> cb.setVisible(true));
				containerImage.addMouseListener(hoverChooseImage);
				Arrays.asList(containerImage, lbImage).forEach(c -> c.addMouseListener(hoverChooseImage));

			} else if (typeGame == TypeGame.pausedGame) {
				panelRight.setVisible(true);
				List<PlayerMatch> newList = playerMatch.stream().filter(pm -> {
					return !pm.isCompleted() && pm.getPlayer().getPlayerId() == player.getPlayerId();
				}).sorted(Comparator.comparingLong(PlayerMatch::getMilliSecondsDuration))
				.collect(Collectors.toList());
				ranking.setPlayerMatch(newList);
				Arrays.asList(cbShuffle, cbSize, buttonChooseImage).forEach(cb -> cb.setVisible(false));
				Arrays.asList(containerImage, lbImage).forEach(c -> c.removeMouseListener(hoverChooseImage));
				containerImage.removeMouseListener(hoverChooseImage);

			} else {
				panelRight.setVisible(true);
				List<PlayerMatch> newList = playerMatch.stream().filter(pm -> pm.isCompleted())
				.sorted(Comparator.comparingLong(PlayerMatch::getMilliSecondsDuration))
				.collect(Collectors.toList());
				ranking.setPlayerMatch(newList);
				Arrays.asList(cbShuffle, cbSize, buttonChooseImage).forEach(cb -> cb.setVisible(false));
				Arrays.asList(containerImage, lbImage).forEach(c -> c.removeMouseListener(hoverChooseImage));
				containerImage.removeMouseListener(hoverChooseImage);
			}
		} else {
			ranking.setSelectedMatch(null);
		}
	}

	private void initGame() {
		int selectedSize;
		TypeShuffle selectedShuffle;
		long currentTime = 0;
		Puzzle puzzle;
		Match match;

		if (isExistingGame()) {
			if (ranking.getSelectedMatch() == null) {
				JOptionPane.showInternalMessageDialog(this, "Selecione uma partida para disputar!", "Atenção",
						JOptionPane.NO_OPTION);
				return;
			}
			puzzle = ranking.getSelectedPuzzle();
			match = ranking.getSelectedMatch();
			PlayerMatch playerMatch = ranking.getSelectedPlayerMatch();
			currentTime = typeGame == TypeGame.pausedGame ? playerMatch.getMilliSecondsDuration() : 0;

		} else {
			selectedSize = optionsSize.get(cbSize.getSelectedItem());
			selectedShuffle = optionsShuffle.get(cbShuffle.getSelectedItem());
			puzzle = new Puzzle(selectedSize, selectedSize, lbImage.getPath(), selectedShuffle);
			match = new Match(puzzle);
		}
		puzzleFrameListener.onClick(puzzle, match, currentTime, typeGame);
	}

	private void initialize() {
		this.setBounds(0, 0, 1175, 670);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);

		JPanel panelLeft = new JPanel();
		panelLeft.setLayout(null);
		panelLeft.setBackground(Color.WHITE);
		panelLeft.setBounds(10, 0, 270, this.getHeight());

		JPanel panelRight = new JPanel();
		panelRight.setLayout(null);
		panelRight.setBackground(new Color(0, 0, 0, 0));
		panelRight.setBounds(300, 0, 775, this.getHeight() - 35);
		panelRight.setVisible(false);

		JLabel labelBackground = new JLabel();
		BufferedImage resized;
		try {
			resized = ImageIO.read(new File("img\\bgs\\bg-main.jpg"));
			Image image = resized.getScaledInstance(panelRight.getWidth(), panelRight.getHeight(), 1);
			labelBackground.setIcon(new ImageIcon(image));
			labelBackground.setHorizontalAlignment(SwingConstants.CENTER);
			labelBackground.setBounds(panelRight.getX(), panelRight.getY(), panelRight.getWidth(),
					panelRight.getHeight());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		MouseListener hoverEffect = new HoverEffect(new Color(249, 13, 72), new Color(0, 0, 128));

		JLabel labelTitle = new CustomLabel("PRÉ-CONFIGURAÇÃO", 0, 0, panelLeft.getWidth(), 30);

		ItemListener listener = new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				configureGameOptions(panelRight, e);
			}
		};

		cbType = new CustomComboBox<>("Selecione o tipo de partida:", optionsType.keySet().stream().sorted().toArray(),
				0, nextBottom(labelTitle), panelLeft.getWidth(), listener);

		cbSize = new CustomComboBox<>("Selecione o tamanho do tabuleiro:",
				optionsSize.keySet().stream().sorted().toArray(), 0, nextBottom(cbType), panelLeft.getWidth());

		cbShuffle = new CustomComboBox<>("Selecione o tipo de permutação:",
				optionsShuffle.keySet().stream().sorted().toArray(), 0, nextBottom(cbSize), panelLeft.getWidth());

		buttonChooseImage = new CustomButton("Escolha uma imagem...", null, 0, nextBottom(cbShuffle),
				panelLeft.getWidth(), 40);
		buttonChooseImage.addActionListener(e -> selectImage());
		buttonChooseImage.addMouseListener(hoverEffect);

		containerImage = new JPanel();
		containerImage.setLayout(null);
		containerImage.setBorder(null);
		containerImage.setBounds(0, nextBottom(buttonChooseImage), panelLeft.getWidth(), panelLeft.getWidth());
		containerImage.setBackground(new Color(220, 220, 220));

		lbImage = new JPhotoRound("img\\puzzle\\default.jpg", (int) (containerImage.getHeight() * 0.9));
		lbImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lbImage.setBounds((int) (containerImage.getWidth() * 0.05), (int) (containerImage.getHeight() * 0.05),
				(int) (containerImage.getWidth() * 0.9), (int) (containerImage.getHeight() * 0.9));

		hoverChooseImage = new HoverEffect(new Color(249, 13, 72), new Color(220, 220, 220)) {

			@Override
			public void mouseClicked(MouseEvent e) {
				selectImage();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				containerImage.setBackground(getBackgroundEntered());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				containerImage.setBackground(getBackgroundExited());
			};
		};
		containerImage.addMouseListener(hoverChooseImage);
		lbImage.addMouseListener(hoverChooseImage);

		CustomButton buttonInit = new CustomButton("JOGAR", "img\\icons\\icon-control.png", 0,
				nextBottom(containerImage), panelLeft.getWidth(), 50);
		buttonInit.addMouseListener(hoverEffect);
		buttonInit.addActionListener(e -> initGame());

		ranking = new RankingSpecificFrame(playerMatch, 0, 0, panelRight.getWidth(), panelRight.getHeight() / 4 * 3,
				url -> {
					ImageManager imageManager = new ImageManager(url);
					lbImage.setPath(imageManager.getAbsolutePath());
				});

		Arrays.asList(lbImage).forEach(containerImage::add);
		Arrays.asList(labelTitle, cbType, cbSize, cbShuffle, buttonChooseImage, containerImage, buttonInit)
				.forEach(panelLeft::add);
		Arrays.asList(panelLeft, panelRight, labelBackground).forEach(this::add);
		Arrays.asList(ranking).forEach(panelRight::add);

	}

	private boolean isExistingGame() {
		return !optionsType.get(cbType.getSelectedItem()).equals(TypeGame.newGame);
	}

	private int nextBottom(Component c) {
		return c.getY() + c.getHeight() + 10;
	}

	protected void selectImage() {
		ImageManager imageManager = new ImageManager("img\\puzzle\\");
		lbImage.setPath(imageManager.getAbsolutePath());
	}
}
