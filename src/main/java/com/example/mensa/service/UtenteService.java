package com.example.mensa.service;

import com.example.mensa.model.Alunno;
import com.example.mensa.model.Utente;

import java.util.List;

import org.springframework.stereotype.Service;

public interface UtenteService {

    public Utente findByUsername(String username);
    public Utente findByEmail(String email);
    public void saveUser(Utente u);
    public String getEmailUtenteCorrente(Utente u);
   

}
