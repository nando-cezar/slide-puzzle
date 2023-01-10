package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import interfaces.DAOListener;
import model.Player;

public class PlayerDAO implements DAOListener<Player> {

	private static PlayerDAO instance;

	private PlayerDAO() {
	}

	public static PlayerDAO getInstance() {
		if (instance == null) {
			instance = new PlayerDAO();
		}
		return instance;
	}

	public Player authenticate(Player player) {

		Player playerIntern = new Player();

		try {
			Connection connection = ConnectionFactory.getConnection();
			String sql = "SELECT * FROM PLAYER AS P WHERE P.PLAYER_USERNAME = '" + player.getPlayerUsername()
					+ "' AND P.PLAYER_PASSWORD = '" + player.getPlayerPassword() + "'";
			Statement stmt = (Statement) connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				playerIntern.setPlayerId((rs.getLong("PLAYER_ID")));
				playerIntern.setPlayerUsername(rs.getString("PLAYER_USERNAME"));
				playerIntern.setPlayerEmail(rs.getString("PLAYER_EMAIL"));
				playerIntern.setPlayerPassword(rs.getString("PLAYER_PASSWORD"));
				playerIntern.setPlayerUrlImage(rs.getString("PLAYER_URL_IMAGE"));
			}

			rs.close();
			connection.close();
			return playerIntern;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public boolean save(Player player) {
		try {
			Connection connection = ConnectionFactory.getConnection();
			String sql = "INSERT INTO PLAYER (PLAYER_USERNAME, PLAYER_EMAIL, PLAYER_PASSWORD, PLAYER_URL_IMAGE) VALUES (?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, player.getPlayerUsername());
			ps.setString(2, player.getPlayerEmail());
			ps.setString(3, player.getPlayerPassword());
			ps.setString(4, "img\\icons\\icon-persona.png");
			ps.execute();
			ps.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
	}

	@Override
	public boolean update(Player player) {

		try {
			Connection connection = ConnectionFactory.getConnection();
			String sql = "UPDATE PLAYER SET PLAYER_USERNAME = ?, PLAYER_EMAIL = ?, PLAYER_PASSWORD = ?, PLAYER_URL_IMAGE = ? WHERE PLAYER_ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, player.getPlayerUsername());
			ps.setString(2, player.getPlayerEmail());
			ps.setString(3, player.getPlayerPassword());
			ps.setString(4, player.getPlayerUrlImage());
			ps.setLong(5, player.getPlayerId());
			ps.execute();
			ps.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}

	}

	@Override
	public List<Player> findAll() {

		ArrayList<Player> listPlayers = new ArrayList<Player>();

		try {
			Connection connection = ConnectionFactory.getConnection();
			String sql = "SELECT * FROM PLAYER";
			Statement stmt = (Statement) connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Player player = new Player();
				player.setPlayerId((rs.getLong("PLAYER_ID")));
				player.setPlayerUsername(rs.getString("PLAYER_USERNAME"));
				player.setPlayerEmail(rs.getString("PLAYER_EMAIL"));
				player.setPlayerPassword(rs.getString("PLAYER_PASSWORD"));
				listPlayers.add(player);
			}
			rs.close();
			connection.close();

		} catch (SQLException e) {
			System.err.println(e);
		}
		return listPlayers;
	}

	@Override
	public Player findById(Long integer) {

		Player player = new Player();

		try {
			Connection connection = ConnectionFactory.getConnection();
			String sql = "SELECT * FROM PLAYER WHERE PLAYER_ID = " + integer;
			Statement stmt = (Statement) connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				player.setPlayerId((rs.getLong("PLAYER_ID")));
				player.setPlayerUsername(rs.getString("PLAYER_USERNAME"));
				player.setPlayerEmail(rs.getString("PLAYER_EMAIL"));
				player.setPlayerPassword(rs.getString("PLAYER_PASSWORD"));
				player.setPlayerUrlImage(rs.getString("PLAYER_URL_IMAGE"));
				rs.close();
				connection.close();
				return player;
			}

		} catch (SQLException e) {
			System.err.println(e);
		}
		return null;
	}

	@Override
	public void removeAll() {
		try {
			Connection connection = ConnectionFactory.getConnection();
			Statement stmt = connection.createStatement();

			String query = "DELETE FROM PLAYER";

			stmt.executeUpdate(query);

		} catch (SQLException e) {
			System.err.println(e);
		}
	}
}
