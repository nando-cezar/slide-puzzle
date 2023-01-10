package controller;

import dao.PlayerDAO;
import model.Player;

public class PlayerController {

	private static PlayerController instance;

	private PlayerController() {
	}

	public static PlayerController getInstance() {
		if (instance == null) {
			instance = new PlayerController();
		}
		return instance;
	}

	public Player authenticate(Player player) {
		return PlayerDAO.getInstance().authenticate(player);
	}

	public boolean save(Player player) {
		return PlayerDAO.getInstance().save(player);
	}

	public boolean update(Player player) {
		return PlayerDAO.getInstance().update(player);
	}

	public Player findById(Long id) {
		return PlayerDAO.getInstance().findById(id);
	}

}
