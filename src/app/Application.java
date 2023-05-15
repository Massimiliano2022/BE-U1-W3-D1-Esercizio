package app;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.entities.Student;

public class Application {

	static Connection conn = null;

	public static void main(String[] args) {
		String url = "jdbc:postgresql://localhost:5432/School?useSSL=false";
		String username = "postgres";
		String password = "1234";

		conn = connectingDb(url, username, password);

		if (conn != null) {

			creaStudente();

			/*
			 * int idSelectedStudent = selectIdStudent(s);
			 * 
			 * if (idSelectedStudent != 0) { updateStudent(4.5, idSelectedStudent);
			 * deleteStudent(idSelectedStudent); }
			 */

		}
	}

	private static Connection connectingDb(String url, String username, String password) {
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		return conn;
	}

	private static void creaStudente() {
		Student s = new Student("Francesco", "Ferrari", "M", Date.valueOf("1994-06-15"), 6.0, 5.0, 7.0);
		if (s != null) {
			insertStudent(s);
		}
	}

	public static void insertStudent(Student s) {
		String sqlInsert = "INSERT INTO students (nome,cognome,sesso,data_nascita,media,voto_minimo,voto_massimo) VALUES(?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlInsert);
			stmt.setString(1, s.getNome());
			stmt.setString(2, s.getCognome());
			stmt.setString(3, s.getSesso());
			stmt.setDate(4, s.getDataNascita());
			stmt.setDouble(5, s.getMediaVoti());
			stmt.setDouble(6, s.getVotoMinimo());
			stmt.setDouble(7, s.getVotoMassimo());
			stmt.execute();
			System.out.println("Studente inserito!");
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

	private static int selectIdStudent(Student s) {
		int id = 0;

		String sqlSelect = "SELECT * FROM students WHERE nome='" + s.getNome() + "' AND cognome='" + s.getCognome()
				+ "'";
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sqlSelect);
			while (result.next()) {

				id = result.getInt("id");

			}
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}

		return id;
	}

	private static void updateStudent(double d, int i) {
		String sqlUpdate = "UPDATE students SET voto_minimo=? WHERE id=?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlUpdate);
			stmt.setDouble(1, d);
			stmt.setInt(2, i);

			/*
			 * String query = stmt.toString(); System.out.println("Query SQL generata: " +
			 * query);
			 */

			stmt.execute();
			System.out.println("Studente modificato!");
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

	private static void deleteStudent(int idSelectedStudent) {
		String sqlDeleteOne = "DELETE FROM students WHERE id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlDeleteOne);
			stmt.setInt(1, idSelectedStudent);
			stmt.execute();
			System.out.println("Studente con id " + idSelectedStudent + " eliminato!");
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

}
