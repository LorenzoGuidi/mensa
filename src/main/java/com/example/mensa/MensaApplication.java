package com.example.mensa;

import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.example.mensa.model.Alunno;
import com.example.mensa.model.Utente;
import com.example.mensa.repository.UtenteRepository;
import com.example.mensa.twilio.TwilioAllert;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@SpringBootApplication
public class MensaApplication extends TimerTask{
	

	
	public static void main(String[] args) {
		MyComponent mComponent = SpringApplication.run(MensaApplication.class, args).getBean(MyComponent.class);
		
		
		Timer timer = new Timer();


	
        // Imposta l'orario desiderato di esecuzione
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);  // Mese (0 = Gennaio, 1 = Febbraio, ecc.)
        cal.set(Calendar.DAY_OF_MONTH, 10);  // Giorno
        cal.set(Calendar.HOUR_OF_DAY, 9);  // Ora
        cal.set(Calendar.MINUTE, 0);  // Minuto
        cal.set(Calendar.SECOND, 0);  // Secondo
        
       Date data1 =  cal.getTime();
            
        
        
        cal.set(Calendar.MONTH, Calendar.JANUARY);  // Mese (0 = Gennaio, 1 = Febbraio, ecc.)
        cal.set(Calendar.DAY_OF_MONTH, 10);  // Giorno
        cal.set(Calendar.HOUR_OF_DAY, 9);  // Ora
        cal.set(Calendar.MINUTE, 0);  // Minuto
        cal.set(Calendar.SECOND, 0);  // Secondo
        
        Date data2 = cal.getTime();
        
        
        cal.set(Calendar.MONTH, Calendar.MARCH);  // Mese (0 = Gennaio, 1 = Febbraio, ecc.)
        cal.set(Calendar.DAY_OF_MONTH, 10);  // Giorno
        cal.set(Calendar.HOUR_OF_DAY, 9);  // Ora
        cal.set(Calendar.MINUTE, 0);  // Minuto
        cal.set(Calendar.SECOND, 0);  // Secondo
        
        Date data3 = cal.getTime();
        
        
        cal.set(Calendar.MONTH, Calendar.MAY);  // Mese (0 = Gennaio, 1 = Febbraio, ecc.)
        cal.set(Calendar.DAY_OF_MONTH, 20);  // Giorno
        cal.set(Calendar.HOUR_OF_DAY, 9);  // Ora
        cal.set(Calendar.MINUTE, 0);  // Minuto
        cal.set(Calendar.SECOND, 0);  // Secondo
        
        Date data4 = cal.getTime();
      
      
      

        // Crea un TimerTask per eseguire il metodo desiderato
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            	
            	List<Alunno> alunni = mComponent.retrieveAllAlunni();
            	
            	for(Alunno a : alunni) {
            		System.out.println(a.getCognome());
            		if(a.getResiduoUtente() < -50) {
            			String numero = a.getTelefonoCell();
            			TwilioAllert tAllert = new TwilioAllert();
                    	tAllert.segnala(numero);//a twilio attivato ricordarsi di modificare il metodo segnala!
            		}
            	}
            	List<Utente> utenti = mComponent.retrieveAllUtenti();
            	for(Utente u : utenti) {
            		if(!u.isConfermato()) {
            			mComponent.getUtenteRepository().delete(u);
            		}
            	}
           
            	
             
            }
        };
        
        
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
            	
            	List<Alunno> alunni = mComponent.retrieveAllAlunni();
            	
            	for(Alunno a : alunni) {
            		System.out.println(a.getCognome());
            		if(a.getResiduoUtente() < -50) {
            			String numero = a.getTelefonoCell();
            			TwilioAllert tAllert = new TwilioAllert();
                    	tAllert.segnala(numero);//a twilio attivato ricordarsi di modificare il metodo segnala!
            		}
            	}
            	List<Utente> utenti = mComponent.retrieveAllUtenti();
            	for(Utente u : utenti) {
            		if(!u.isConfermato()) {
            			mComponent.getUtenteRepository().delete(u);
            		}
            	}
           
            	
             
            }
        };
        
        TimerTask task3 = new TimerTask() {
            @Override
            public void run() {
            	
            	List<Alunno> alunni = mComponent.retrieveAllAlunni();
            	
            	for(Alunno a : alunni) {
            		System.out.println(a.getCognome());
            		if(a.getResiduoUtente() < -50) {
            			String numero = a.getTelefonoCell();
            			TwilioAllert tAllert = new TwilioAllert();
                    	tAllert.segnala(numero);//a twilio attivato ricordarsi di modificare il metodo segnala!
            		}
            	}
            	List<Utente> utenti = mComponent.retrieveAllUtenti();
            	for(Utente u : utenti) {
            		if(!u.isConfermato()) {
            			mComponent.getUtenteRepository().delete(u);
            		}
            	}
           
            	
             
            }
        };
        
        
        TimerTask task4 = new TimerTask() {
            @Override
            public void run() {
            	
            	List<Alunno> alunni = mComponent.retrieveAllAlunni();
            	
            	for(Alunno a : alunni) {
            		System.out.println(a.getCognome());
            		if(a.getResiduoUtente() < -50) {
            			String numero = a.getTelefonoCell();
            			TwilioAllert tAllert = new TwilioAllert();
                    	tAllert.segnala(numero);//a twilio attivato ricordarsi di modificare il metodo segnala!
            		}
            	}
            	List<Utente> utenti = mComponent.retrieveAllUtenti();
            	for(Utente u : utenti) {
            		if(!u.isConfermato()) {
            			mComponent.getUtenteRepository().delete(u);
            		}
            	}
           
            	
             
            }
        };
        
       



        // Schedula l'esecuzione del TimerTask una volta al giorno all'orario specificato
       // timer.schedule(task, orarioEsecuzione, 24 * 60 * 60 * 1000); // Ripeti ogni 24 ore
        
        timer.schedule(task, data1, 365 * 24 * 60 * 60 * 1000L);
        timer.schedule(task2, data2, 365 * 24 * 60 * 60 * 1000L);
        timer.schedule(task3, data3, 365 * 24 * 60 * 60 * 1000L);
        timer.schedule(task4, data4, 365 * 24 * 60 * 60 * 1000L);
    

        // Puoi aggiungere qui eventuali altre operazioni che devono continuare ad eseguirsi

	}
	
	
	
	
	
	   @Component
	    public static class MyComponent {
	        private final EntityManagerFactory entityManagerFactory;
	        private final UtenteRepository utenteRepository; // Aggiungi l'attributo per la repository
	
	

	        @Autowired
	        public MyComponent(EntityManagerFactory entityManagerFactory, UtenteRepository utenteRepository) {
	            this.entityManagerFactory = entityManagerFactory;
	        
				this.utenteRepository = utenteRepository;
	        }

	        public UtenteRepository getUtenteRepository() {
	            return utenteRepository;
	        }
	         
	        
	        @Transactional
	    	public List<Alunno> retrieveAllAlunni() {
	    	    EntityManager entityManager = entityManagerFactory.createEntityManager();
	    	    
	    	    try {
	    	        String jpql = "SELECT a FROM Alunno a";
	    	        TypedQuery<Alunno> query = entityManager.createQuery(jpql, Alunno.class);
	    	        return query.getResultList();
	    	    } finally {
	    	        entityManager.close();
	    	    }
	    	}
	        
	        
	        @Transactional
	    	public List<Utente> retrieveAllUtenti() {
	    	    EntityManager entityManager = entityManagerFactory.createEntityManager();
	    	    
	    	    try {
	    	        String jpql = "SELECT u FROM Utente u";
	    	        TypedQuery<Utente> query = entityManager.createQuery(jpql, Utente.class);
	    	        return query.getResultList();
	    	    } finally {
	    	        entityManager.close();
	    	    }
	    	}
	
	
	
	
	   }





	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}





	




	
}
