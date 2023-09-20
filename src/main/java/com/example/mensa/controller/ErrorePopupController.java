package com.example.mensa.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mensa.model.Alunno;
import com.example.mensa.model.ErrorePopup;
import com.example.mensa.model.Utente;
import com.example.mensa.repository.UtenteRepository;
import com.example.mensa.service.AlunnoService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ErrorePopupController {

	@Autowired
	UtenteRepository ur;
	
	@Autowired
	AlunnoService as;
	
	@GetMapping(value = "check-ava", produces = "application/json")
	@ResponseBody
	ErrorePopup error(@RequestParam String username , String password , String email) {
	
		ErrorePopup ep = new ErrorePopup();
		
		List<Utente> utenti = ur.findAll();
		for(Utente u : utenti) {
			if(u.getUsername().equals(username) || u.getEmail().equals(email)) {
				ep.setError(true);
				
			}
				
		}
		
		
		
		return ep;
	}
	
	@GetMapping(value = "check-iscrizione", produces = "application/json")
	@ResponseBody
	ErrorePopup error2(@RequestParam String codiceFiscale) {
		ErrorePopup ep = new ErrorePopup();
		 List<Alunno> alunni = as.getAllAlunni();
		    
		    //caso upload admin
		    for (Alunno al : alunni) {
		    
		     if (!al.getCodiceFiscale().equals(codiceFiscale) || al.getDataIscrizione() != null) {
		        	ep.setError(true);
		        
		        }		   
		    }		    
		    
		    return ep;		 
//		    for (Alunno al : alunni) {
//		    	System.out.println("iterazione numero: " + al.getId());
//		        if (al.getCodiceFiscale().equals(codiceFiscale) && al.getDataIscrizione() == null) 
//		         ep.setError(false); 
//		         else {
//		        	 ep.setError(true);
//			        return ep;
//		         } 
//		    }
//			return ep;
	}
}
