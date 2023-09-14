package com.example.mensa.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity

@Table(name = "utenti")
public class Utente {

    @Id
    private String username; 
    private String password;
    @Column(unique = true)
    private String email;
    private String nome;
    private String cognome;
    private boolean confermato ; 
    @OneToMany(mappedBy="utente" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Alunno> alunni ;



    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public List<Alunno> getAlunni() {
		return alunni;
	}

	public void setAlunni(List<Alunno> alunni) {
		this.alunni = alunni;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public boolean isConfermato() {
		return confermato;
	}

	public void setConfermato(boolean confermato) {
		this.confermato = confermato;
	}
}
