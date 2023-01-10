package view.components;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.Piece;

public class PieceButton extends JButton {
	
	private static final long serialVersionUID = 1L;
	private Piece piece;
	private int width;
	private int height;
	
	public PieceButton(Piece piece, int width, int height) {	
		this.piece = piece;
		this.width = width;
		this.height = height;
		this.configImg();
	}
	
	public void configImg() {
		ImageIcon img = piece.getImg();
		img.setImage(img.getImage().getScaledInstance(width, height, 100));
		setIcon(img);
	}

	public Piece getPiece() {
		return piece;
	}

}
