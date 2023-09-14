package com.example.mensa.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity

@Table(name = "alunni")

public class Alunno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private char sesso;
	private String nome;
	private String cognome;
	

	private String codiceFiscale;
	private String dataNascita;
	private String luogoNascita;
	private String gradoScuola;
	private String sedeScolastica;
	private String sezioneScuola;
	private boolean iscritto;
	private boolean privacyOk;
	
	private double rientri;
	  @ElementCollection(targetClass = GiorniSettimana.class)
	  @CollectionTable(name = "alunno_giorni_settimana", joinColumns = @JoinColumn(name = "alunno_id"))
	  @Column(name = "giorno_settimana")
	  @Enumerated(EnumType.STRING)
	private Set<GiorniSettimana> giorni;
	  
	private boolean fatturaOgniVolta;
	private double ImpPagato1;
	private double PastoPagato1;
	private String dataPagamento1;
	private double ImpPagato2;
	private double PastoPagato2;
	private String dataPagamento2;
	private double ImpPagato3;
	private double PastoPagato3;
	private String dataPagamento3;
	private double ImpPagato4;
	private double PastoPagato4;
	private String dataPagamento4;
	private double ImpPagato5;
	private double PastoPagato5;
	private String dataPagamento5;
	private int presenzaOttobre;
	private double quotaUtenteOttobre;
	private double dovutoUtenteOttobre;
	private double quotaComuneOttobre;
	private double dovutoComuneOttobre;
	private int presenzaNovembre;
	private double quotaUtenteNovembre;
	private double dovutoUtenteNovembre;
	private double quotaComuneNovembre;
	private double dovutoComuneNovembre;
	private int presenzaDicembre;
	private double quotaUtenteDicembre;
	private double dovutoUtenteDicembre;
	private double quotaComuneDicembre;
	private double dovutoComuneDicembre;
	private int presenzaGennaio;
	private double quotaUtenteGennaio;
	private double dovutoUtenteGennaio;
	private double quotaComuneGennaio;
	private double dovutoComuneGennaio;
	private int presenzaFebbraio;
	private double quotaUtenteFebbraio;
	private double dovutoUtenteFebbraio;
	private double quotaComuneFebbraio;
	private double dovutoComuneFebbraio;
	private int presenzaMarzo;
	private double quotaUtenteMarzo;
	private double dovutoUtenteMarzo;
	private double quotaComuneMarzo;
	private double dovutoComuneMarzo;
	private int presenzaAprile;
	private double quotaUtenteAprile;
	private double dovutoUtenteAprile;
	private double quotaComuneAprile;
	private double dovutoComuneAprile;
	private int presenzaMaggio;
	private double quotaUtenteMaggio;
	private double dovutoUtenteMaggio;
	private double quotaComuneMaggio;
	private double dovutoComuneMaggio;
	private int presenzaGiugno;
	private double quotaUtenteGiugno;
	private double dovutoUtenteGiugno;
	private double quotaComuneGiugno;
	private double dovutoComuneGiugno;
	
	private double presenzeRegistrate;
	private double pastiPagati;
	private double differenzaPasti;
	private double quotaUtente;
	private double dovutoUtente;
	private double pagatoUtente;
	private double residuoUtente;
	private String nomeRichiedente;
	private String cognomeRichidente;
	private String luogoNascitaRichidente;
	private Date dataNascitaRichidente;
	private String indirizzoRichidente;
	private String civicoRichidente;
	private String comune; 
	private String cap;
	private String provincia;
	private String telefono;	
	private String telefonoCell;
	private String email;
	
	private Date dataIscrizione;
	
	
	@ManyToOne
	private Utente utente;
	
	private String annoAccademico;
	
	
	
	
	
	public String getAnnoAccademico() {
		return annoAccademico;
	}
	public void setAnnoAccademico(String annoAccademico) {
		this.annoAccademico = annoAccademico;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public Date getDataIscrizione() {
		return dataIscrizione;
	}
	public void setDataIscrizione(Date dataIscrizione) {
		this.dataIscrizione = dataIscrizione;
	}
	public String getLuogoNascita() {
		return luogoNascita;
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	public String getGradoScuola() {
		return gradoScuola;
	}
	public void setGradoScuola(String gradoScuola) {
		this.gradoScuola = gradoScuola;
	}
	public String getSedeScolastica() {
		return sedeScolastica;
	}
	public void setSedeScolastica(String sedeScolastica) {
		this.sedeScolastica = sedeScolastica;
	}
	public double getRientri() {
		return rientri;
	}
	public void setRientri(double rientri) {
		this.rientri = rientri;
	}
	public Set<GiorniSettimana> getGiorni() {
		return giorni;
	}
	public void setGiorni(Set<GiorniSettimana> giorni) {
		this.giorni = giorni;
	}
	public String getCognomeRichidente() {
		return cognomeRichidente;
	}
	public void setCognomeRichidente(String cognomeRichidente) {
		this.cognomeRichidente = cognomeRichidente;
	}
	public String getLuogoNascitaRichidente() {
		return luogoNascitaRichidente;
	}
	public void setLuogoNascitaRichidente(String luogoNascitaRichidente) {
		this.luogoNascitaRichidente = luogoNascitaRichidente;
	}
	public Date getDataNascitaRichidente() {
		return dataNascitaRichidente;
	}
	public void setDataNascitaRichidente(Date dataNascitaRichidente) {
		this.dataNascitaRichidente = dataNascitaRichidente;
	}
	public String getIndirizzoRichidente() {
		return indirizzoRichidente;
	}
	public void setIndirizzoRichidente(String indirizzoRichidente) {
		this.indirizzoRichidente = indirizzoRichidente;
	}
	public String getCivicoRichidente() {
		return civicoRichidente;
	}
	public void setCivicoRichidente(String civicoRichidente) {
		this.civicoRichidente = civicoRichidente;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getTelefonoCell() {
		return telefonoCell;
	}
	public void setTelefonoCell(String telefonoCell) {
		this.telefonoCell = telefonoCell;
	}
	public char getSesso() {
		return sesso;
	}
	public void setSesso(char sesso) {
		this.sesso = sesso;
	}
	public String getNomeRichiedente() {
		return nomeRichiedente;
	}
	public void setNomeRichiedente(String nomeRichiedente) {
		this.nomeRichiedente = nomeRichiedente;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getSezioneScuola() {
		return sezioneScuola;
	}
	public void setSezioneScuola(String sezioneScuola) {
		this.sezioneScuola = sezioneScuola;
	}
	public boolean isIscritto() {
		return iscritto;
	}
	public void setIscritto(boolean iscritto) {
		this.iscritto = iscritto;
	}
	public boolean getFatturaOgniVolta() {
		return fatturaOgniVolta;
	}
	public void setFatturaOgniVolta(boolean fatturaOgniVolta) {
		this.fatturaOgniVolta = fatturaOgniVolta;
	}
	public double getImpPagato1() {
		return ImpPagato1;
	}
	public void setImpPagato1(double impPagato1) {
		ImpPagato1 = impPagato1;
	}
	public double getPastoPagato1() {
		return PastoPagato1;
	}
	public void setPastoPagato1(double pastoPagato1) {
		PastoPagato1 = pastoPagato1;
	}
	public String getDataPagamento1() {
		return dataPagamento1;
	}
	public void setDataPagamento1(String dataPagamento1) {
		this.dataPagamento1 = dataPagamento1;
	}
	public double getImpPagato2() {
		return ImpPagato2;
	}
	public void setImpPagato2(double impPagato2) {
		ImpPagato2 = impPagato2;
	}
	public double getPastoPagato2() {
		return PastoPagato2;
	}
	public void setPastoPagato2(double pastoPagato2) {
		PastoPagato2 = pastoPagato2;
	}
	public String getDataPagamento2() {
		return dataPagamento2;
	}
	public void setDataPagamento2(String dataPagamento2) {
		this.dataPagamento2 = dataPagamento2;
	}
	public double getImpPagato3() {
		return ImpPagato3;
	}
	public void setImpPagato3(double impPagato3) {
		ImpPagato3 = impPagato3;
	}
	public int getPresenzaOttobre() {
		return presenzaOttobre;
	}
	public void setPresenzaOttobre(int presenzaOttobre) {
		this.presenzaOttobre = presenzaOttobre;
	}
	public double getQuotaUtenteOttobre() {
		return quotaUtenteOttobre;
	}
	public void setQuotaUtenteOttobre(double quotaUtenteOttobre) {
		this.quotaUtenteOttobre = quotaUtenteOttobre;
	}
	public double getDovutoUtenteOttobre() {
		return dovutoUtenteOttobre;
	}
	public void setDovutoUtenteOttobre(double dovutoUtenteOttobre) {
		this.dovutoUtenteOttobre = dovutoUtenteOttobre;
	}
	public double getQuotaComuneOttobre() {
		return quotaComuneOttobre;
	}
	public void setQuotaComuneOttobre(double quotaComuneOttobre) {
		this.quotaComuneOttobre = quotaComuneOttobre;
	}
	public double getDovutoComuneOttobre() {
		return dovutoComuneOttobre;
	}
	public void setDovutoComuneOttobre(double dovutoComuneOttobre) {
		this.dovutoComuneOttobre = dovutoComuneOttobre;
	}
	public int getPresenzaNovembre() {
		return presenzaNovembre;
	}
	public void setPresenzaNovembre(int presenzaNovembre) {
		this.presenzaNovembre = presenzaNovembre;
	}
	public double getQuotaUtenteNovembre() {
		return quotaUtenteNovembre;
	}
	public void setQuotaUtenteNovembre(double quotaUtenteNovembre) {
		this.quotaUtenteNovembre = quotaUtenteNovembre;
	}
	public double getDovutoUtenteNovembre() {
		return dovutoUtenteNovembre;
	}
	public void setDovutoUtenteNovembre(double dovutoUtenteNovembre) {
		this.dovutoUtenteNovembre = dovutoUtenteNovembre;
	}
	public double getQuotaComuneNovembre() {
		return quotaComuneNovembre;
	}
	public void setQuotaComuneNovembre(double quotaComuneNovembre) {
		this.quotaComuneNovembre = quotaComuneNovembre;
	}
	public double getDovutoComuneNovembre() {
		return dovutoComuneNovembre;
	}
	public void setDovutoComuneNovembre(double dovutoComuneNovembre) {
		this.dovutoComuneNovembre = dovutoComuneNovembre;
	}
	public int getPresenzaDicembre() {
		return presenzaDicembre;
	}
	public void setPresenzaDicembre(int presenzaDicembre) {
		this.presenzaDicembre = presenzaDicembre;
	}
	public double getQuotaUtenteDicembre() {
		return quotaUtenteDicembre;
	}
	public void setQuotaUtenteDicembre(double quotaUtenteDicembre) {
		this.quotaUtenteDicembre = quotaUtenteDicembre;
	}
	public double getDovutoUtenteDicembre() {
		return dovutoUtenteDicembre;
	}
	public void setDovutoUtenteDicembre(double dovutoUtenteDicembre) {
		this.dovutoUtenteDicembre = dovutoUtenteDicembre;
	}
	public double getQuotaComuneDicembre() {
		return quotaComuneDicembre;
	}
	public void setQuotaComuneDicembre(double quotaComuneDicembre) {
		this.quotaComuneDicembre = quotaComuneDicembre;
	}
	public double getDovutoComuneDicembre() {
		return dovutoComuneDicembre;
	}
	public void setDovutoComuneDicembre(double dovutoComuneDicembre) {
		this.dovutoComuneDicembre = dovutoComuneDicembre;
	}
	public int getPresenzaGennaio() {
		return presenzaGennaio;
	}
	public void setPresenzaGennaio(int presenzaGennaio) {
		this.presenzaGennaio = presenzaGennaio;
	}
	public double getQuotaUtenteGennaio() {
		return quotaUtenteGennaio;
	}
	public void setQuotaUtenteGennaio(double quotaUtenteGennaio) {
		this.quotaUtenteGennaio = quotaUtenteGennaio;
	}
	public double getDovutoUtenteGennaio() {
		return dovutoUtenteGennaio;
	}
	public void setDovutoUtenteGennaio(double dovutoUtenteGennaio) {
		this.dovutoUtenteGennaio = dovutoUtenteGennaio;
	}
	public double getQuotaComuneGennaio() {
		return quotaComuneGennaio;
	}
	public void setQuotaComuneGennaio(double quotaComuneGennaio) {
		this.quotaComuneGennaio = quotaComuneGennaio;
	}
	public double getDovutoComuneGennaio() {
		return dovutoComuneGennaio;
	}
	public void setDovutoComuneGennaio(double dovutoComuneGennaio) {
		this.dovutoComuneGennaio = dovutoComuneGennaio;
	}
	public int getPresenzaFebbraio() {
		return presenzaFebbraio;
	}
	public void setPresenzaFebbraio(int presenzaFebbraio) {
		this.presenzaFebbraio = presenzaFebbraio;
	}
	public double getQuotaUtenteFebbraio() {
		return quotaUtenteFebbraio;
	}
	public void setQuotaUtenteFebbraio(double quotaUtenteFebbraio) {
		this.quotaUtenteFebbraio = quotaUtenteFebbraio;
	}
	public double getDovutoUtenteFebbraio() {
		return dovutoUtenteFebbraio;
	}
	public void setDovutoUtenteFebbraio(double dovutoUtenteFebbraio) {
		this.dovutoUtenteFebbraio = dovutoUtenteFebbraio;
	}
	public double getQuotaComuneFebbraio() {
		return quotaComuneFebbraio;
	}
	public void setQuotaComuneFebbraio(double quotaComuneFebbraio) {
		this.quotaComuneFebbraio = quotaComuneFebbraio;
	}
	public double getDovutoComuneFebbraio() {
		return dovutoComuneFebbraio;
	}
	public void setDovutoComuneFebbraio(double dovutoComuneFebbraio) {
		this.dovutoComuneFebbraio = dovutoComuneFebbraio;
	}
	public int getPresenzaMarzo() {
		return presenzaMarzo;
	}
	public void setPresenzaMarzo(int presenzaMarzo) {
		this.presenzaMarzo = presenzaMarzo;
	}
	public double getQuotaUtenteMarzo() {
		return quotaUtenteMarzo;
	}
	public void setQuotaUtenteMarzo(double quotaUtenteMarzo) {
		this.quotaUtenteMarzo = quotaUtenteMarzo;
	}
	public double getDovutoUtenteMarzo() {
		return dovutoUtenteMarzo;
	}
	public void setDovutoUtenteMarzo(double dovutoUtenteMarzo) {
		this.dovutoUtenteMarzo = dovutoUtenteMarzo;
	}
	public double getQuotaComuneMarzo() {
		return quotaComuneMarzo;
	}
	public void setQuotaComuneMarzo(double quotaComuneMarzo) {
		this.quotaComuneMarzo = quotaComuneMarzo;
	}
	public double getDovutoComuneMarzo() {
		return dovutoComuneMarzo;
	}
	public void setDovutoComuneMarzo(double dovutoComuneMarzo) {
		this.dovutoComuneMarzo = dovutoComuneMarzo;
	}
	public int getPresenzaAprile() {
		return presenzaAprile;
	}
	public void setPresenzaAprile(int presenzaAprile) {
		this.presenzaAprile = presenzaAprile;
	}
	public double getQuotaUtenteAprile() {
		return quotaUtenteAprile;
	}
	public void setQuotaUtenteAprile(double quotaUtenteAprile) {
		this.quotaUtenteAprile = quotaUtenteAprile;
	}
	public double getDovutoUtenteAprile() {
		return dovutoUtenteAprile;
	}
	public void setDovutoUtenteAprile(double dovutoUtenteAprile) {
		this.dovutoUtenteAprile = dovutoUtenteAprile;
	}
	public double getQuotaComuneAprile() {
		return quotaComuneAprile;
	}
	public void setQuotaComuneAprile(double quotaComuneAprile) {
		this.quotaComuneAprile = quotaComuneAprile;
	}
	public double getDovutoComuneAprile() {
		return dovutoComuneAprile;
	}
	public void setDovutoComuneAprile(double dovutoComuneAprile) {
		this.dovutoComuneAprile = dovutoComuneAprile;
	}
	public int getPresenzaMaggio() {
		return presenzaMaggio;
	}
	public void setPresenzaMaggio(int presenzaMaggio) {
		this.presenzaMaggio = presenzaMaggio;
	}
	public double getQuotaUtenteMaggio() {
		return quotaUtenteMaggio;
	}
	public void setQuotaUtenteMaggio(double quotaUtenteMaggio) {
		this.quotaUtenteMaggio = quotaUtenteMaggio;
	}
	public double getDovutoUtenteMaggio() {
		return dovutoUtenteMaggio;
	}
	public void setDovutoUtenteMaggio(double dovutoUtenteMaggio) {
		this.dovutoUtenteMaggio = dovutoUtenteMaggio;
	}
	public double getQuotaComuneMaggio() {
		return quotaComuneMaggio;
	}
	public void setQuotaComuneMaggio(double quotaComuneMaggio) {
		this.quotaComuneMaggio = quotaComuneMaggio;
	}
	public double getDovutoComuneMaggio() {
		return dovutoComuneMaggio;
	}
	public void setDovutoComuneMaggio(double dovutoComuneMaggio) {
		this.dovutoComuneMaggio = dovutoComuneMaggio;
	}
	public int getPresenzaGiugno() {
		return presenzaGiugno;
	}
	public void setPresenzaGiugno(int presenzaGiugno) {
		this.presenzaGiugno = presenzaGiugno;
	}
	public double getQuotaUtenteGiugno() {
		return quotaUtenteGiugno;
	}
	public void setQuotaUtenteGiugno(double quotaUtenteGiugno) {
		this.quotaUtenteGiugno = quotaUtenteGiugno;
	}
	public double getDovutoUtenteGiugno() {
		return dovutoUtenteGiugno;
	}
	public void setDovutoUtenteGiugno(double dovutoUtenteGiugno) {
		this.dovutoUtenteGiugno = dovutoUtenteGiugno;
	}
	public double getQuotaComuneGiugno() {
		return quotaComuneGiugno;
	}
	public void setQuotaComuneGiugno(double quotaComuneGiugno) {
		this.quotaComuneGiugno = quotaComuneGiugno;
	}
	public double getDovutoComuneGiugno() {
		return dovutoComuneGiugno;
	}
	public void setDovutoComuneGiugno(double dovutoComuneGiugno) {
		this.dovutoComuneGiugno = dovutoComuneGiugno;
	}

	public double getPastoPagato3() {
		return PastoPagato3;
	}
	public void setPastoPagato3(double pastoPagato3) {
		PastoPagato3 = pastoPagato3;
	}
	public String getDataPagamento3() {
		return dataPagamento3;
	}
	public void setDataPagamento3(String dataPagamento3) {
		this.dataPagamento3 = dataPagamento3;
	}
	public double getImpPagato4() {
		return ImpPagato4;
	}
	public void setImpPagato4(double impPagato4) {
		ImpPagato4 = impPagato4;
	}
	public double getPastoPagato4() {
		return PastoPagato4;
	}
	public void setPastoPagato4(double pastoPagato4) {
		PastoPagato4 = pastoPagato4;
	}
	public String getDataPagamento4() {
		return dataPagamento4;
	}
	public void setDataPagamento4(String dataPagamento4) {
		this.dataPagamento4 = dataPagamento4;
	}
	public double getImpPagato5() {
		return ImpPagato5;
	}
	public void setImpPagato5(double impPagato5) {
		ImpPagato5 = impPagato5;
	}
	public double getPastoPagato5() {
		return PastoPagato5;
	}
	public void setPastoPagato5(double pastoPagato5) {
		PastoPagato5 = pastoPagato5;
	}
	public String getDataPagamento5() {
		return dataPagamento5;
	}
	public void setDataPagamento5(String dataPagamento5) {
		this.dataPagamento5 = dataPagamento5;
	}
	public double getPresenzeRegistrate() {
		return presenzeRegistrate;
	}
	public void setPresenzeRegistrate(double presenzeRegistrate) {
		this.presenzeRegistrate = presenzeRegistrate;
	}
	public double getPastiPagati() {
		return pastiPagati;
	}
	public void setPastiPagati(double pastiPagati) {
		this.pastiPagati = pastiPagati;
	}
	public double getDifferenzaPasti() {
		return differenzaPasti;
	}
	public void setDifferenzaPasti(double differenzaPasti) {
		this.differenzaPasti = differenzaPasti;
	}
	public double getQuotaUtente() {
		return quotaUtente;
	}
	public void setQuotaUtente(double quotaUtente) {
		this.quotaUtente = quotaUtente;
	}
	public double getDovutoUtente() {
		return dovutoUtente;
	}
	public void setDovutoUtente(double dovutoUtente) {
		this.dovutoUtente = dovutoUtente;
	}
	public double getPagatoUtente() {
		return pagatoUtente;
	}
	public void setPagatoUtente(double pagatoUtente) {
		this.pagatoUtente = pagatoUtente;
	}
	public double getResiduoUtente() {
		return residuoUtente;
	}
	public void setResiduoUtente(double residuoUtente) {
		this.residuoUtente = residuoUtente;
	}

	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	public boolean isPrivacyOk() {
		return privacyOk;
	}
	public void setPrivacyOk(boolean privacyOk) {
		this.privacyOk = privacyOk;
	}


}
