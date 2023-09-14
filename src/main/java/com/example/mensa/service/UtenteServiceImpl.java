package com.example.mensa.service;

import com.example.mensa.model.Alunno;
import com.example.mensa.model.Utente;
import com.example.mensa.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteServiceImpl implements UtenteService{

    @Autowired
    UtenteRepository ur;


    @Override
    public Utente findByUsername(String username) {

        Utente user = ur.getOne(username);
        return user;
    }
    

    @Override
    public Utente findByEmail(String email) {

        Utente user = ur.getOne(email);
        return user;
    }
    
    @Override
    public String getEmailUtenteCorrente(Utente u) {
     
        String emailUtente = u.getEmail();
        
        return emailUtente;
    }

    @Override
    public void saveUser(Utente u) {

         ur.save(u);

    }


	
}

