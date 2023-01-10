package controller;

import java.util.List;

import dao.PlayerMatchDAO;
import model.PlayerMatch;
import model.RecordPlayerMatch;

public class PlayerMatchController {

	private static PlayerMatchController instance;

	private PlayerMatchController() {
	}

	public static PlayerMatchController getInstance() {
		if (instance == null) {
			instance = new PlayerMatchController();
		}
		return instance;
	}

	public boolean save(PlayerMatch playerMatch) {
		return PlayerMatchDAO.getInstance().save(playerMatch);
	}

	public boolean update(PlayerMatch playerMatch) {
		return PlayerMatchDAO.getInstance().update(playerMatch);
	}

	public List<PlayerMatch> findAll() {
		return PlayerMatchDAO.getInstance().findAll();
	}

	public void removeAll() {
		PlayerMatchDAO.getInstance().removeAll();
	}

	public PlayerMatch findById(Long playerId, Long matchId) {
		return PlayerMatchDAO.getInstance().findById(playerId, matchId);
	}

	public int totalPages(int limit, boolean isComplete) {
		return (int) Math.ceil( PlayerMatchDAO.getInstance().totalElement( isComplete)/limit);
	}
	
	public List<PlayerMatch> findAll(int page,int limit , boolean isComplete) {
		return PlayerMatchDAO.getInstance().findAll(page, limit, isComplete);
	}
	
	public RecordPlayerMatch recordPlayerMatchByPlayer(Long idPlayer) {
		return PlayerMatchDAO.getInstance().recordPlayerMatchByPlayer(idPlayer);
	}

}
