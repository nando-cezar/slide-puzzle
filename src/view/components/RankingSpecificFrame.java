package view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.plaf.DimensionUIResource;

import controller.MatchController;
import controller.PuzzleController;
import interfaces.HoverEffect;
import interfaces.RankingListener;
import model.Match;
import model.PlayerMatch;
import model.Puzzle;
import util.Format;

public class RankingSpecificFrame extends JPanel {

	private static final long serialVersionUID = -4017090534925567889L;
	private int width = 1000;
	private int height = 600;
	private List<PlayerMatch> playerMatch;
	private int x;
	private int y;
	private JPanel panel;
	private PlayerMatch selectedPlayerMatch;
	private String selectedUrlImage;
	private RankingListener listener;
	private Puzzle selectedPuzzle;
	private Match selectedMatch;
	private Color backgroundRed;
	private Color backgroundColor;

	public RankingSpecificFrame(List<PlayerMatch> playerMatch, int x, int y, int width, int height,
			RankingListener listener) {
		super();
		this.playerMatch = playerMatch;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.listener = listener;
		this.selectedPlayerMatch = null;
		this.panel = new JPanel();
		this.initialize();
	}

	public void initialize() {
		backgroundColor = new Color(220, 220, 220);
		backgroundRed = new Color(255, 67, 102);
		MouseListener hoverEffect = new HoverEffect(Color.WHITE, backgroundColor) {

			@Override
			public void mouseEntered(MouseEvent e) {
				if (selectedPlayerMatch == null)
					super.mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (selectedPlayerMatch == null)
					super.mouseExited(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseClicked(e);
				configureItemClick(backgroundColor, e);
			}
		};

		this.setBackground(backgroundColor);
		this.setBounds(x, y, width, height);
		this.setLayout(null);

		int Width = (int) (width * 0.91);
		int headHeight = (int) (height * 0.07);
		int bodyHeught = (int) (height * 0.12);

		JPanel head = new JPanel();
		head.setBounds(10, 11, Width, headHeight);
		head.setLayout(null);
		add(head);

		JLabel headClassification = new JLabel("Classificação");
		headClassification.setFont(new Font("Tahoma", Font.BOLD, 11));
		headClassification.setHorizontalAlignment(SwingConstants.CENTER);
		headClassification.setBounds(0, 0, Width / 6, headHeight);
		head.add(headClassification);

		JLabel headUsers = new JLabel("Usuário");
		headUsers.setFont(new Font("Tahoma", Font.BOLD, 11));
		headUsers.setHorizontalAlignment(SwingConstants.CENTER);
		headUsers.setBounds(Width / 6, 0, Width / 6 * 2, headHeight);
		head.add(headUsers);

		JLabel headMatches = new JLabel("Nº Partida");
		headMatches.setFont(new Font("Tahoma", Font.BOLD, 11));
		headMatches.setHorizontalAlignment(SwingConstants.CENTER);
		headMatches.setBounds(Width / 6 * 3, 0, Width / 6, headHeight);
		head.add(headMatches);

		JLabel headPunctuation = new JLabel("Pontuação");
		headPunctuation.setFont(new Font("Tahoma", Font.BOLD, 11));
		headPunctuation.setHorizontalAlignment(SwingConstants.CENTER);
		headPunctuation.setBounds(Width / 6 * 4, 0, Width / 6, headHeight);
		head.add(headPunctuation);

		JLabel headDuration = new JLabel("Duração");
		headDuration.setFont(new Font("Tahoma", Font.BOLD, 11));
		headDuration.setHorizontalAlignment(SwingConstants.CENTER);
		headDuration.setBounds(Width / 6 * 5, 0, Width / 6, headHeight);
		head.add(headDuration);

		Arrays.asList(head, headClassification, headUsers, headPunctuation, headDuration, headMatches)
				.forEach(e -> e.setBackground(backgroundColor));

		panel.setLayout(null);
		panel.setBounds(head.getX(), headHeight + head.getY(), Width, (int) (bodyHeught * playerMatch.size()));
		panel.setPreferredSize(new DimensionUIResource(Width, (int) (bodyHeught * playerMatch.size())));
		panel.setBackground(backgroundColor);

		JScrollPane scrollBar = new JScrollPane(panel);
		scrollBar.setBounds(head.getX(), headHeight + head.getY(), Width + 20, (int) (height * 0.89));
		scrollBar.setViewportView(panel);
		add(scrollBar);

		int classification = 1;
		int vertexY = 0;

		for (int i = 0; i < playerMatch.size(); i++) {
			PlayerMatch current = playerMatch.get(i);
			long qtd = playerMatch.stream().filter(pm -> pm.getMatch().getId() == current.getMatch().getId()).count();

			JPanel body = new JPanel();
			body.setBounds(0, vertexY, (int) (width * 0.91), bodyHeught);
			body.setLayout(null);
			body.setBackground(qtd >= 3 ? backgroundRed : backgroundColor);
			panel.add(body);
			vertexY += bodyHeught;

			JLabel bodyClassification = new JLabel();
			bodyClassification.setHorizontalAlignment(SwingConstants.CENTER);
			bodyClassification.setBounds(0, 0, Width / 6, bodyHeught);
			Format.classification(bodyClassification, classification);
			body.add(bodyClassification);

			JLabel bodyUsers = new JLabel(current.getPlayer().getPlayerUsername());
			bodyUsers.setHorizontalAlignment(SwingConstants.CENTER);
			bodyUsers.setBounds(Width / 6, 0, Width / 6 * 2, bodyHeught);
			body.add(bodyUsers);

			JLabel bodyMatches = new JLabel(current.getMatch().getId().toString());
			bodyMatches.setHorizontalAlignment(SwingConstants.CENTER);
			bodyMatches.setBounds(Width / 6 * 3, 0, Width / 6, bodyHeught);
			body.add(bodyMatches);

			JLabel bodyPunctuation = new JLabel(Format.punctuation(current.getPlayerPoints()));
			bodyPunctuation.setHorizontalAlignment(SwingConstants.CENTER);
			bodyPunctuation.setBounds(Width / 6 * 4, 0, Width / 6, bodyHeught);
			body.add(bodyPunctuation);

			JLabel bodyDuration = new JLabel(Format.hours(current.getMilliSecondsDuration()));
			bodyDuration.setHorizontalAlignment(SwingConstants.CENTER);
			bodyDuration.setBounds(Width / 6 * 5, 0, Width / 6, bodyHeught);
			body.add(bodyDuration);

			JSeparator separator = new JSeparator();
			separator.setBounds(headHeight * 50 / 455, 0, Width, 2);
			body.add(separator);
			if (qtd < 3)
				body.addMouseListener(hoverEffect);

			classification++;
		}
	}

	public List<PlayerMatch> getPlayerMatch() {
		return playerMatch;
	}

	public PlayerMatch getSelectedPlayerMatch() {
		return this.selectedPlayerMatch;
	}

	public void setPlayerMatch(List<PlayerMatch> playerMatch) {
		this.playerMatch = playerMatch;
		panel.removeAll();
		removeAll();
		initialize();
	}

	private void configureItemClick(Color backgroundColor, MouseEvent e) {

		List<Component> components = Arrays.asList(e.getComponent().getParent().getComponents());
		int index = components.indexOf(e.getComponent());
		selectedPlayerMatch = playerMatch.get(index);
		long quantity = playerMatch.stream().filter(pm -> pm.getMatch().getId() == selectedPlayerMatch.getId()).count();

		components.stream()
				.filter(c -> !c.equals(e.getComponent()) && c.getBackground() != backgroundRed)
				.forEach(c -> c.setBackground(backgroundColor));
		e.getComponent().setBackground(Color.WHITE);
		if (quantity >= 3) {
			String message = "A partida selecionada atingiu o limite de 3 jogadores! Selecione outra";
			JOptionPane.showMessageDialog(this, message, "Atenção", JOptionPane.ERROR_MESSAGE);
			return;
		}

		selectedMatch = MatchController.getInstance().findById(selectedPlayerMatch.getMatch().getId());
		selectedPuzzle = PuzzleController.getInstance().findById(selectedMatch.getPuzzle().getId());
		selectedPuzzle.initializeFromBd(selectedPlayerMatch.getId());
		selectedUrlImage = selectedPuzzle.getUrlImage();
		listener.changeImage(selectedUrlImage);

	}

	public Puzzle getSelectedPuzzle() {
		return selectedPuzzle;
	}

	public void setSelectedPuzzle(Puzzle selectedPuzzle) {
		this.selectedPuzzle = selectedPuzzle;
	}

	public Match getSelectedMatch() {
		return selectedMatch;
	}

	public void setSelectedMatch(Match selectedMatch) {
		this.selectedMatch = selectedMatch;
	}

	public String getSelectedUrlImage() {
		return selectedUrlImage;
	}

	public void setSelectedUrlImage(String selectedUrlImage) {
		this.selectedUrlImage = selectedUrlImage;
	}
}
