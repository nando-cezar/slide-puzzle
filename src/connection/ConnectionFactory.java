package connection;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ConnectionFactory {

	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost:5432/puzzle";
	private static final String USER = "postgres";
	private static final String PASS = "root";
	private static boolean tableCreated = false;

	public static Connection getConnection() {

		try {
			Class.forName(DRIVER);
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			createTables(con);
			return con;

		} catch (ClassNotFoundException | SQLException ex) {
			throw new RuntimeException("Erro na conexão!!!", ex);
		}
	}

	public static void closeConnection(Connection conn) {

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				System.err.println("Erro na conexão!!!" + ex);
			}
		}
	}

	public static void closeConnection(Connection conn, PreparedStatement stmt) {

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				System.err.println("Erro na conexão!!!" + ex);
			}
		}
		closeConnection(conn);
	}

	public static void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				System.err.println("Erro na conexão!!!" + ex);
			}
		}
		closeConnection(conn, stmt);
	}

	public static void createTables(Connection con) {
		if (tableCreated)
			return;

		StringBuilder str = new StringBuilder();

		try {
			Scanner f = new Scanner(Paths.get("sql//puzzle.sql"));
			while (f.hasNextLine()) {
				str.append(f.nextLine());
			}
			con.createStatement().execute(str.toString());
			tableCreated = true;
		} catch (SQLException e) {
			System.out.println("Erro na criação das tabelas!");
			tableCreated = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}