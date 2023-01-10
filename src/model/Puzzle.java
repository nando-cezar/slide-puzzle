package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import controller.PieceController;
import interfaces.ShuffleListener;
import util.enums.TypeShuffle;

public class Puzzle {

	private Long id;
	private int LINES;
	private int COLUMNS;
	private List<Piece> pieces = new ArrayList<>();
	private TypeShuffle typeShuffle;
	private String urlImage;
	private int size;
	private boolean isFromBd = false;
	
	public Puzzle() {
	}

	public Puzzle(int lines, int columns, String urlImage, TypeShuffle typeShuffle) {
		this(0L, lines, columns, urlImage, typeShuffle);
	}
	
	public Puzzle(int lines, int columns, String urlImage, TypeShuffle typeShuffle, List<Piece> pieces) {
		this.size = lines;
		this.LINES = lines;
		this.COLUMNS = columns;
		this.typeShuffle = typeShuffle;
		this.pieces = pieces;
		this.urlImage = urlImage;
		this.initialize();
	}

	public Puzzle(Long id, int lines, int columns, String urlImage, TypeShuffle typeShuffle) {
		this.id = id;
		this.size = lines;
		this.LINES = lines;
		this.COLUMNS = columns;
		this.typeShuffle = typeShuffle;
		this.pieces = new ArrayList<>();
		this.urlImage = urlImage;
		this.initialize();
	}

	public void initialize() {
		generatepieces();
		associateNeighbors();
		addEmpty();
	}
	
	public void initializeFromBd(Long idPlayerMatch) {
		generatepieces();
		associateNeighbors();
		PieceController.getInstance().configPiece(idPlayerMatch, pieces);
		addEmpty();
	}


	private void generatepieces() {

		try {
			BufferedImage imagem = ImageIO.read(new File(urlImage));
			int w = imagem.getWidth() / this.getCOLUMNS();
			int h = imagem.getHeight() / this.getLINES();
			int index = 1;

			for (int l = 0; l < this.getLINES(); l++) {
				for (int c = 0; c < this.getCOLUMNS(); c++) {
					Piece piece = new Piece(index, l, c, false, index);
					piece.setImg(new ImageIcon(imagem.getSubimage(c * w, l * h, w, h)));
					pieces.add(piece);
					index++;
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void associateNeighbors() {
		pieces.forEach(m -> {
			pieces.forEach(s -> s.addNeighbor(m));
		});
	}

	private void addEmpty() {
		pieces.stream().filter(e -> e.getIndex() == this.getTotalSize()).findFirst().get().setEmpty(true);
	}

	public void shuffleTable(ShuffleListener shuffleListener) {
		try {
			Thread.sleep(1000);
			int animationTime = 2000;
			Random random = new Random();
			int numberExchange = 0;

			while (!this.isValidNumber(numberExchange)) {
				numberExchange = random.nextInt(0, this.getTotalSize());
			}
			animationTime /= numberExchange;
			Set<Swap> swaps = new HashSet<Swap>();

			while (swaps.size() < numberExchange) {
				Piece pieceOrigin = pieces.get(random.nextInt(this.getTotalSize() - 1));
				Piece pieceDestiny = pieces.get(random.nextInt(this.getTotalSize() - 1));

				if (!pieceOrigin.equals(pieceDestiny)) {
					Swap current = new Swap(pieceOrigin.getIndex(), pieceDestiny.getIndex());

					if (!swaps.contains(current)) {
						swaps.add(current);
						pieceOrigin.exchange(pieceDestiny);
						shuffleListener.updateButtons();
						Thread.sleep(animationTime);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exchange(int origin, int destiny) {
		if ((origin < getTotalSize() && origin >= 0) && (destiny < getTotalSize() && destiny >= 0)) {
			Piece pieceOrigin = pieces.get(origin);
			Piece pieceDestiny = pieces.get(destiny);
			pieceOrigin.exchange(pieceDestiny);
		}
	}

	private boolean isPair(int number) {
		return number % 2 == 0;
	}

	private boolean isValidNumber(int number) {
		return number != 0 && typeShuffle.equals(TypeShuffle.pairs) ? this.isPair(number) : !this.isPair(number);
	}

	public boolean completedPuzzle() {
		int value = 1;
		for (Piece piece : pieces) {
			if (piece.getIndex() != value++) {
				return false;
			}
		}
		return true;
	}

	public int getTotalSize() {
		return getCOLUMNS() * getLINES();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getLINES() {
		return LINES;
	}

	public void setLINES(int lINES) {
		LINES = lINES;
	}

	public int getCOLUMNS() {
		return COLUMNS;
	}

	public void setCOLUMNS(int cOLUMNS) {
		COLUMNS = cOLUMNS;
	}

	public List<Piece> getPieces() {
		return pieces;
	}

	public void setPieces(List<Piece> pieces) {
		this.pieces = pieces;
	}

	public TypeShuffle getTypeShuffle() {
		return typeShuffle;
	}

	public void setTypeShuffle(TypeShuffle typeShuffle) {
		this.typeShuffle = typeShuffle;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public boolean isFromBd() {
		return isFromBd;
	}

	public void setFromBd(boolean isFromBd) {
		this.isFromBd = isFromBd;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}