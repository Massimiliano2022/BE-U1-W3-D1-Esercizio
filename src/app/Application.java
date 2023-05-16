package app;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import app.entities.Student;

public class Application {

	static Connection conn = null;

	public static void main(String[] args) {
		String url = "jdbc:postgresql://localhost:5432/School?useSSL=false";
		String username = "postgres";
		String password = "1234";

		conn = connectingDb(url, username, password);

		if (conn != null) {

			List<Student> listaStudenti = getListaStudenti();

			creaStudente();

			Student studenteCercato = cercaStudente(listaStudenti.size() + 1);

			updateStudent(5.0, studenteCercato.getId());

			getBestStudent();

			deleteStudent(studenteCercato.getId());

			disconnettiDb(conn);
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

	private static List<Student> getListaStudenti() {
		List<Student> listaStudenti = new ArrayList<>();
		String sqlSelect = "SELECT * FROM students";
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sqlSelect);
			while (result.next()) {

				int id = result.getInt("id");
				String nome = result.getString("nome");
				String cognome = result.getString("cognome");
				String sesso = result.getString("sesso");
				Date dataNascita = result.getDate("data_nascita");
				double media = result.getDouble("media");
				double votoMinimo = result.getDouble("voto_minimo");
				double votoMassimo = result.getDouble("voto_massimo");

				Student s = new Student(id, nome, cognome, sesso, dataNascita, media, votoMinimo, votoMassimo);
				listaStudenti.add(s);

				System.out.println("Studente : " + s.getId() + " " + s.getNome() + " " + s.getCognome());

			}
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}

		return listaStudenti;
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

	private static Student cercaStudente(int idSearch) {
		Student s = null;
		String sqlSelect = "SELECT * FROM students WHERE id='" + idSearch + "'";
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sqlSelect);
			while (result.next()) {

				int id = result.getInt("id");
				String nome = result.getString("nome");
				String cognome = result.getString("cognome");
				String sesso = result.getString("sesso");
				Date dataNascita = result.getDate("data_nascita");
				double media = result.getDouble("media");
				double votoMinimo = result.getDouble("voto_minimo");
				double votoMassimo = result.getDouble("voto_massimo");

				s = new Student(id, nome, cognome, sesso, dataNascita, media, votoMinimo, votoMassimo);
				System.out.println("Studente cercato : " + s.getId() + " " + s.getNome() + " " + s.getCognome());

			}
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}

		return s;
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

	private static void getBestStudent() {

		Student bestStudent = null;

		List<Student> listaStudenti = getListaStudenti();

		Iterator<Student> i = listaStudenti.iterator();

		while (i.hasNext()) {

			Student s = i.next();

			if (s.getMediaVoti() > bestStudent.getMediaVoti()) {

				bestStudent = i.next();

			}

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

	private static void disconnettiDb(Connection conn2) {
		try {
			conn.close();
			System.out.println("Disconnesso");
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}
	}

}
