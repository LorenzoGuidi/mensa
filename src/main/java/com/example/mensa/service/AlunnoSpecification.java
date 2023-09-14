package com.example.mensa.service;

import org.springframework.data.jpa.domain.Specification;

import com.example.mensa.model.Alunno;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class AlunnoSpecification {
    public static Specification<Alunno> getCodiceFiscaleAndSezioneAndAnnoAccademico(String codiceFiscale, String annoAccademico) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicateCodiceFiscale = criteriaBuilder.like(root.get("codiceFiscale"), "%" + codiceFiscale.toLowerCase() + "%");
            
            Predicate predicateSezioneScuola = criteriaBuilder.like(root.get("sezioneScuola"), "%" + codiceFiscale.toLowerCase() + "%");
            Predicate predicateAnnoAccademico = criteriaBuilder.equal(root.get("annoAccademico"), annoAccademico);
            Predicate predicateCognomeBambino = criteriaBuilder.like(root.get("cognome") , "%" + codiceFiscale.toLowerCase() + "%");
            Predicate predicateCognomeRichiedente = criteriaBuilder.like(root.get("cognomeRichidente") , "%" + codiceFiscale.toLowerCase() + "%");

            
            Predicate predicateOR = criteriaBuilder.or(predicateCodiceFiscale, predicateSezioneScuola , predicateCognomeBambino, predicateCognomeRichiedente);
            
            return criteriaBuilder.and(predicateOR, predicateAnnoAccademico);
        };
    }
}