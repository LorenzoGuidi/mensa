package com.example.mensa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mensa.model.Alunno;
import com.example.mensa.repository.AlunnoRepository;

@Service

public class AlunnoServiceImpl implements AlunnoService {
	
	@Autowired
	AlunnoRepository ar;

	@Override
	public Alunno findByCF(String codicefiscale) {
		
		Alunno a = ar.getOne(codicefiscale);
		
		return a;
	}

	@Override
	public Alunno findById(Integer id) {
		
		
		Alunno a = ar.getOne(id.toString());
		
		return a;
	}
	
	@Override
	public List<Alunno> getAllAlunni() {
		return ar.findAll();
		
	}


	@Override
	public void saveAlunno(Alunno alunno) {
		ar.save(alunno);
		
	}

}
