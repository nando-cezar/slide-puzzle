package controller;

import dao.PuzzleDAO;
import model.Puzzle;

public class PuzzleController {

	private static PuzzleController instance;

	private PuzzleController() {
	}

	public static PuzzleController getInstance() {
		if (instance == null) {
			instance = new PuzzleController();
		}
		return instance;
	}

	public boolean save(Puzzle puzzle) {
		return PuzzleDAO.getInstance().save(puzzle);
	}
	
	public boolean update(Puzzle puzzle) {
		return PuzzleDAO.getInstance().update(puzzle);
	}

	public Puzzle findById(Long id) {
		return PuzzleDAO.getInstance().findById(id);
	}

	public void removeAll(){
		PuzzleDAO.getInstance().removeAll();
	}

}
