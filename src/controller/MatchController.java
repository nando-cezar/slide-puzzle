package controller;

import dao.MatchDAO;
import model.Match;

public class MatchController {

	private static MatchController instance;

	private MatchController() {
	}

	public static MatchController getInstance() {
		if (instance == null) {
			instance = new MatchController();
		}
		return instance;
	}

	public boolean save(Match match) {
		return MatchDAO.getInstance().save(match);
	}

	public Match findById(Long id) {
		return MatchDAO.getInstance().findById(id);
	}

	public void removeAll(){
		MatchDAO.getInstance().removeAll();
	}

	public Match findByPuzzleId(Long id) {
		return MatchDAO.getInstance().findByPuzzleId(id);
	}

}
