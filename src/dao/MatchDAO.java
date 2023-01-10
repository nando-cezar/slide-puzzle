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
import model.Match;
import model.Puzzle;

public class MatchDAO implements DAOListener<Match> {

	private static MatchDAO instance;

	private MatchDAO() {
	}

	public static MatchDAO getInstance() {
		if (instance == null) {
			instance = new MatchDAO();
		}
		return instance;
	}

	@Override
	public boolean save(Match match) {
		ResultSet id;
		try {
			Connection connection = ConnectionFactory.getConnection();
			String sql = "INSERT INTO MATCH(PUZZLE_ID) VALUES (?)";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, match.getPuzzle().getId());
			ps.execute();
			id = ps.getGeneratedKeys();
			if (id.next()) {
				match.setId(id.getLong(1));
			}
			ps.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
		}

		return false;
	}

	@Override
	public boolean update(Match match) {
		try {
			Connection connection = ConnectionFactory.getConnection();
			String sql = "UPDATE MATCH SET PUZZLE_ID = ? WHERE MATCH_ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, match.getPuzzle().getId());
			ps.setLong(2, match.getId());
			ps.execute();
			ps.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return false;
	}

	@Override
	public Match findById(Long id) {
		try {
			Connection connection = ConnectionFactory.getConnection();
			String sql = "SELECT * FROM MATCH WHERE MATCH_ID = " + id;
			Statement stmt = (Statement) connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				Long indexPuzzle = rs.getLong("PUZZLE_ID");
				Puzzle puzzle = PuzzleDAO.getInstance().findById(indexPuzzle);
				puzzle.setId(indexPuzzle);
				return new Match(rs.getLong("MATCH_ID"), puzzle);
			}

		} catch (SQLException e) {
			System.err.println(e);
		}

		return null;
	}

	@Override
	public List<Match> findAll() {
		ArrayList<Match> listMatches = new ArrayList<Match>();

		try {
			Connection connection = ConnectionFactory.getConnection();
			String sql = "SELECT * FROM MATCH";
			Statement stmt = (Statement) connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Puzzle puzzle = PuzzleDAO.getInstance().findById(rs.getLong("PUZZLE_ID"));
				listMatches.add(new Match(rs.getLong("MATCH_ID"), puzzle));
			}
			rs.close();
			connection.close();

		} catch (SQLException e) {
			System.err.println(e);
		}
		return listMatches;
	}

	@Override
	public void removeAll() {

		try {
			Connection connection = ConnectionFactory.getConnection();
			Statement stmt = connection.createStatement();

			String query = "DELETE FROM MATCH";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	public Match findByPuzzleId(Long id) {
		try {
			Connection connection = ConnectionFactory.getConnection();
			String sql = "SELECT * FROM MATCH WHERE PUZZLE_ID = " + id;
			Statement stmt = (Statement) connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				Puzzle puzzle = PuzzleDAO.getInstance().findById(id);
				return new Match(rs.getLong("MATCH_ID"), puzzle);
			}

		} catch (SQLException e) {
			System.err.println(e);
		}

		return null;
	}

}
