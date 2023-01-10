package controller;

import java.util.List;

import dao.PieceDAO;
import model.Piece;

public class PieceController {

	private static PieceController instance;

	private PieceController() {
	}

	public static PieceController getInstance() {
		if (instance == null) {
			instance = new PieceController();
		}
		return instance;
	}

	public boolean save(Long playerMatch, List<Piece> pieces) {
		return PieceDAO.getInstance().save(playerMatch, pieces);
	}

	public boolean update(Long playerMatch, List<Piece> pieces) {
		return PieceDAO.getInstance().update(playerMatch, pieces);
	}

	public void configPiece(Long playerMatch, List<Piece> pieces) {
		PieceDAO.getInstance().configPiece(playerMatch, pieces);
	}

	public void removeAll() {
		PieceDAO.getInstance().removeAll();
	}

}
