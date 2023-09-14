package com.example.mensa.service;

import java.util.List;

import com.example.mensa.model.Alunno;

public interface AlunnoService {
	
	
	public Alunno findByCF(String codicefiscale);
	
	public Alunno findById(Integer id);
	
	
	
	public List<Alunno> getAllAlunni();
	
	public void saveAlunno(Alunno alunno);
	

}
