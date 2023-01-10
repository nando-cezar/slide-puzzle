package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;

public class Piece {

	private int LINE;
	private int COLUMN;
	
	private int index;
	private int currentPosition;
	private ImageIcon img;
	private boolean isEmpty;
	private List<Piece> neighbors;
	
	public Piece(int index, int line, int column, boolean isEmpty, int currentPosition) {
		this.index = index;
		this.currentPosition = currentPosition;
		this.LINE = line;
		this.COLUMN = column;
		this.isEmpty = isEmpty;
		this.neighbors = new ArrayList<Piece>();
	}
	
	public Piece() {
		this.neighbors = new ArrayList<Piece>();
	}

	public List<Piece> getNeighbors() {
		return neighbors;
	}
	
	public void setNeighbors(List<Piece> neighbors) {
		this.neighbors = neighbors;
	}

	public void setLINE(int lINE) {
		LINE = lINE;
	}

	public void setCOLUMN(int cOLUMN) {
		COLUMN = cOLUMN;
	}

	public void addNeighbor(Piece neighbor) {
		boolean different =  this.getLINE() != neighbor.getLINE() && this.getCOLUMN() != neighbor.getCOLUMN();
		
		int deltaLine = Math.abs(this.getLINE() - neighbor.getLINE());
		int deltaColumn = Math.abs(this.getCOLUMN() - neighbor.getCOLUMN());
		int generalDelta = deltaLine + deltaColumn;
		
		if(generalDelta == 1 && !different) {
			neighbors.add(neighbor);
		}
	}
	
	public void movement() {
		Optional<Piece> piece = neighbors.stream().filter(e -> e.isEmpty()).findFirst();

		if(piece.isPresent()) {
			exchange(piece.get());
		}
	}
	
	public boolean verifyMovement() {
		Optional<Piece> piece = neighbors.stream().filter(e -> e.isEmpty()).findFirst();
		return piece.isPresent() ? true : false;
	}
	
	
	public void exchange(Piece destiny) {
		int index = destiny.getIndex();
		ImageIcon img = destiny.getImg();
		boolean isEmpty = destiny.isEmpty();
		destiny.setAttributes(this.getIndex(), this.getImg(), this.isEmpty());
		this.setAttributes(index, img, isEmpty);
	}
	
	private void setAttributes(int index, ImageIcon img, boolean isEmpty) {
		this.setIndex(index);
		this.setImg(img);
		this.setEmpty(isEmpty);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getLINE() {
		return LINE;
	}

	public int getCOLUMN() {
		return COLUMN;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		if(isEmpty) {
			this.setImg(new ImageIcon("img\\empty.png"));
		}
		this.isEmpty = isEmpty;
	}

	public ImageIcon getImg() {
		return img;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}
}
