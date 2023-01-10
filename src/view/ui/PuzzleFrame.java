package view.ui;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.MatchController;
import controller.PieceController;
import controller.PlayerMatchController;
import controller.PuzzleController;
import interfaces.PuzzleBoardListener;
import interfaces.StopwatchListener;
import model.Match;
import model.Player;
import model.PlayerMatch;
import model.Puzzle;
import util.enums.TypeGame;
import util.enums.TypeShuffle;
import view.components.PuzzleBoard;
import view.components.Stopwatch;

public class PuzzleFrame extends JPanel {

	private static final long serialVersionUID = 1L;
	private Player player;
	private boolean wasExecuted;
	private Stopwatch stopWatch;
	private PuzzleBoard puzzleBoard;
	private long currentTime;
	private TypeGame typeGame;
	private Match match;

	public PuzzleFrame(Player player, int size, String urlImage, TypeShuffle typeShuffle, long currentTime,
			TypeGame typeGame) {
		super();
		this.player = player;
		this.currentTime = currentTime;
		this.typeGame = typeGame;
		this.wasExecuted = false;
		this.stopWatch = new Stopwatch(puzzleBoardListener(), currentTime);
		this.puzzleBoard = new PuzzleBoard(size, urlImage, typeShuffle, puzzleBoardListener(), stopwatchListener(),
				typeGame);
		this.initialize();
	}

	public PuzzleFrame(Player player, Puzzle puzzle, Match match, long currentTime, TypeGame typeGame) {
		super();
		this.player = player;
		this.match = match;
		this.currentTime = currentTime;
		this.typeGame = typeGame;
		this.wasExecuted = false;
		this.stopWatch = new Stopwatch(puzzleBoardListener(), currentTime);
		this.puzzleBoard = new PuzzleBoard(puzzle, puzzleBoardListener(), stopwatchListener(), typeGame);
		this.initialize();
	}

	public void initialize() {
		this.setBounds(0, 0, 1175, 670);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);

		JLabel lbBG = new JLabel("");
		BufferedImage resized;
		try {
			resized = ImageIO.read(new File("img\\bgs\\bg-main.jpg"));
			Image image = resized.getScaledInstance(730, 640, 1);
			lbBG.setIcon(new ImageIcon(image));
			lbBG.setHorizontalAlignment(SwingConstants.CENTER);
			lbBG.setBounds(645, 0, 430, 640);
			this.add(lbBG);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Arrays.asList(puzzleBoard, stopWatch).forEach(this::add);
	}

	public PuzzleBoardListener puzzleBoardListener() {
		return new PuzzleBoardListener() {

			@Override
			public void persist() {
				persistenceData(puzzleBoard.getPuzzle(), true);
				stopWatch.stop();
			}

			@Override
			public void keep() {
				persistenceData(puzzleBoard.getPuzzle(), false);
				stopWatch.pause();
			}

		};
	}

	public StopwatchListener stopwatchListener() {

		return new StopwatchListener() {

			@Override
			public void pause() {
				stopWatch.pause();
			}

			@Override
			public void stop() {
				stopWatch.stop();
			}

			@Override
			public boolean isRunning() {
				return stopWatch.isRunning();
			}

			@Override
			public Long getDuration() {
				return stopWatch.getSeconds();
			}

			@Override
			public long setMilliSeconds() {
				return currentTime;
			}

		};
	}

	private void persistenceData(Puzzle puzzle, boolean isCompleted) {
		if(!wasExecuted && typeGame == TypeGame.newGame){
			PuzzleController.getInstance().save(puzzle);

			match = new Match(puzzle);

			MatchController.getInstance().save(match);
			
			PlayerMatch playerMatch = new PlayerMatch(
					player,
					match,
					stopWatch.getMilliSeconds(),
					isCompleted
					);
			PlayerMatchController.getInstance().save(playerMatch);
			
			PieceController.getInstance().save(playerMatch.getId(), puzzle.getPieces());

			wasExecuted = !wasExecuted;

		}else if(!wasExecuted && typeGame == TypeGame.multiplayerGame) {
			PuzzleController.getInstance().save(puzzle);
			
			PlayerMatch playerMatch = new PlayerMatch(
					player,
					this.match,
					stopWatch.getMilliSeconds(),
					isCompleted
					);
			
			PlayerMatchController.getInstance().save(playerMatch);
			
			PieceController.getInstance().save(playerMatch.getId(), puzzle.getPieces());

			wasExecuted = !wasExecuted;

		}else{
			PuzzleController.getInstance().update(puzzle);
			
			this.match.setPuzzle(puzzle);
			PlayerMatch playerMatch = PlayerMatchController.getInstance().findById(player.getPlayerId(),match.getId());
			playerMatch.setMilliSecondsDuration(stopWatch.getMilliSeconds());
			playerMatch.setCompleted(isCompleted);

			PlayerMatchController.getInstance().update(playerMatch);
			
			PieceController.getInstance().update(playerMatch.getId(), puzzle.getPieces());

		}	
	}

}
