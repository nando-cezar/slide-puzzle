package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import connection.ConnectionFactory;
import model.Piece;

public class PieceDAO {

	private static PieceDAO instance;

	private PieceDAO() {
	}

	public static PieceDAO getInstance() {
		if (instance == null) {
			instance = new PieceDAO();
		}
		return instance;
	}

	public boolean save(Long playerMatch, List<Piece> pieces) {

		Connection connection = ConnectionFactory.getConnection();
		String sql = "INSERT INTO PIECE(PLAYER_MATCH_ID, PIECE_INDEX, PIECE_CURRENT_POSITION, PIECE_COLUMN, PIECE_LINE, PIECE_EMPTY) VALUES (?,?,?,?,?,?)";
		PreparedStatement ps;

		try {
			ps = connection.prepareStatement(sql);
			
			for (int i = 0; i < pieces.size(); i++) {

				ps.setLong(1, playerMatch);
				ps.setInt(2, pieces.get(i).getIndex());
				ps.setInt(3, i);
				ps.setInt(4,pieces.get(i).getCOLUMN());
				ps.setInt(5, pieces.get(i).getLINE());
				ps.setBoolean(6, pieces.get(i).isEmpty());
				ps.execute();
				
			}

			ps.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return false;
	}

	public boolean update(Long playerMatch, List<Piece> pieces) {
		try {
			Connection connection = ConnectionFactory.getConnection();
			String sql = "UPDATE PIECE SET PIECE_CURRENT_POSITION = ?, PIECE_EMPTY = ? WHERE PLAYER_MATCH_ID = ? AND PIECE_INDEX = ?";
			PreparedStatement ps = connection.prepareStatement(sql);

			for (int i = 0; i < pieces.size(); i++) {

				ps.setLong(1, i);
				ps.setBoolean(2, pieces.isEmpty());
				ps.setLong(3, playerMatch);
				ps.setInt(4,pieces.get(i).getIndex());

				ps.execute();
				
			}
			
			ps.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return false;
	}

	public void configPiece(Long playerMatch, List<Piece> pieces) {

		Connection connection = ConnectionFactory.getConnection();
		String sql = "SELECT * FROM PIECE WHERE PLAYER_MATCH_ID = " + playerMatch;
		Statement stmt;
		try {
			stmt = (Statement) connection.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Piece currentPosition = pieces.get(rs.getInt("PIECE_CURRENT_POSITION"));
				Piece pieceIndex = pieceIndice(pieces, rs.getInt("PIECE_INDEX"));
				pieceIndex.exchange(currentPosition);
			}

			rs.close();
			connection.close();
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	private Piece pieceIndice(List<Piece> pieces, int index) {
		return pieces.stream().filter(e -> e.getIndex() == index).findFirst().get();
	}

	public void removeAll() {
		try {
			Connection connection = ConnectionFactory.getConnection();
			Statement stmt = connection.createStatement();

			String query = "DELETE FROM PIECE";

			stmt.executeUpdate(query);

		} catch (SQLException e) {
			System.err.println(e);
		}
	}

}
