package app.entities;

import java.sql.Date;

public class Student {

	private int id;
	private String nome;
	private String cognome;
	private String sesso;
	private Date dataNascita;
	private double mediaVoti;
	private double votoMinimo;
	private double votoMassimo;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getSesso() {
		return sesso;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setMediaVoti(double mediaVoti) {
		this.mediaVoti = mediaVoti;
	}

	public double getMediaVoti() {
		return mediaVoti;
	}

	public void setVotoMinimo(double votoMinimo) {
		this.votoMinimo = votoMinimo;
	}

	public double getVotoMinimo() {
		return votoMinimo;
	}

	public void setVotoMassimo(double votoMassimo) {
		this.votoMassimo = votoMassimo;
	}

	public double getVotoMassimo() {
		return votoMassimo;
	}

	public Student(String nome, String cognome, String sesso, Date dataNascita, double mediaVoti, double votoMinimo,
			double votoMassimo) {
		setNome(nome);
		setCognome(cognome);
		setSesso(sesso);
		setDataNascita(dataNascita);
		setMediaVoti(mediaVoti);
		setVotoMinimo(votoMinimo);
		setVotoMassimo(votoMassimo);
	}
}
