package view.components;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import interfaces.PuzzleBoardListener;
import interfaces.ShuffleListener;
import interfaces.StopwatchListener;
import model.Piece;
import model.Puzzle;
import util.enums.TypeGame;
import util.enums.TypeShuffle;

public class PuzzleBoard extends JPanel {

	private static final long serialVersionUID = 1L;
	private PuzzleBoardListener puzzleBoardListener;
	private StopwatchListener stopwatchListener;
	private List<PieceButton> buttons;
	private Puzzle puzzle;
	private TypeGame typeGame;

	public PuzzleBoard(int size, String urlImage, TypeShuffle typeShuffle, PuzzleBoardListener puzzleBoardListener,
			StopwatchListener stopwatchListener, TypeGame typeGame) {
		this.puzzleBoardListener = puzzleBoardListener;
		this.stopwatchListener = stopwatchListener;
		this.typeGame = typeGame;
		this.buttons = new ArrayList<PieceButton>();
		this.puzzle = new Puzzle(size, size, urlImage, typeShuffle);
		this.initialize();
	}

	public PuzzleBoard(Puzzle puzzle, PuzzleBoardListener puzzleBoardListener, StopwatchListener stopwatchListener, TypeGame typeGame) {
		this.puzzleBoardListener = puzzleBoardListener;
		this.stopwatchListener = stopwatchListener;
		this.typeGame = typeGame;
		this.buttons = new ArrayList<PieceButton>();
		this.puzzle = puzzle;
		this.initialize();
	}

	private void initialize() {
		this.setBorder(new LineBorder(new Color(0, 0, 128)));
		this.setBounds(10, 80, 630, 560);
		this.setLayout(new GridLayout(puzzle.getLINES(), puzzle.getCOLUMNS()));

		int width = this.getWidth() / puzzle.getLINES();
		int height = this.getHeight() / puzzle.getCOLUMNS();
		List<Piece> piecesSorted = puzzle.getPieces().stream()
				.sorted((p1, p2) -> Integer.compare(p1.getCurrentPosition(), p2.getCurrentPosition())).toList();
		
		piecesSorted.forEach(e -> buttons.add(new PieceButton(e, width, height)));
		
		//puzzle.getPieces().forEach(p -> p.setIndex(p.getCurrentPosition()));
		
		ShuffleListener shuffleListener = new ShuffleListener() {
			@Override
			public void updateButtons() {
				buttons.forEach(button -> button.configImg());
			}
		};

		if (typeGame != TypeGame.pausedGame) {
			Thread shuffle = new Thread(() -> {
				puzzle.shuffleTable(shuffleListener);
			});
			
			shuffle.start();			
		}

		buttons.forEach(button -> {
			this.add(button);
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if (stopwatchListener.isRunning()) {
						makeMovement(button);
						button.setBorder(null);
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					if (button.getPiece().verifyMovement() && stopwatchListener.isRunning()) {
						button.setBorder(BorderFactory.createLineBorder(Color.green, 3));
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if (stopwatchListener.isRunning()) {
						button.setBorder(null);
					}
				}
			});
		});
	}

	private void makeMovement(PieceButton button) {
		PieceButton piece = buttons.stream().filter(h -> h.getPiece().isEmpty()).findFirst().get();
		button.getPiece().movement();
		button.configImg();
		piece.configImg();
		if (puzzle.completedPuzzle()) {
			puzzleBoardListener.persist();
			JOptionPane.showMessageDialog(null, "Parabéns, você ganhou!!!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public Puzzle getPuzzle() {
		return puzzle;
	}
}
