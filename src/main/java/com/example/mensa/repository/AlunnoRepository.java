package com.example.mensa.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.mensa.model.Alunno;



public interface AlunnoRepository extends JpaRepository<Alunno,String> , JpaSpecificationExecutor<Alunno>{
	
	@Query("SELECT a FROM Alunno a WHERE a.dataIscrizione BETWEEN :dataInizio AND :dataFine")
    List<Alunno> findByDataBetween(Date dataInizio, Date dataFine);

}
