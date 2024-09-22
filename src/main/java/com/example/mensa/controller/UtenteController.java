package com.example.mensa.controller;

import java.util.Date;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.mensa.model.Alunno;
import com.example.mensa.model.Utente;
import com.example.mensa.repository.AlunnoRepository;
import com.example.mensa.repository.UtenteRepository;
import com.example.mensa.service.AlunnoService;
import com.example.mensa.service.AlunnoSpecification;
import com.example.mensa.service.UtenteService;
import com.example.mensa.service.UtilsService;
import com.example.mensa.twilio.TwilioAllert;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Controller
public class UtenteController {
	
	@Autowired
	TwilioAllert twilioAllert;
	@Autowired
	UtilsService uj;
	
	@Autowired
	UtenteService us;
	@Autowired
	UtenteRepository ur;
	
	@Autowired
	AlunnoService as;
	
	@Autowired
	AlunnoRepository ar;
	
	@Autowired
	Environment env;
	
	

	@Autowired
	private JavaMailSender mailSender;
	
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	

	

    @RequestMapping(value = "/", method = RequestMethod.GET)
    ModelAndView home(){

    	
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");

      return mav;
    }
    
    
    @RequestMapping(value= "/login", method = RequestMethod.GET)
    public ModelAndView loginPage() {
    	 
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("login");
    	return mav;
    	
    }
    @RequestMapping(value= "/contatti", method = RequestMethod.GET)
    public ModelAndView contact() {
    	 
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("contatti");
    	return mav;
    	
    }

	@RequestMapping(value= "/register", method = RequestMethod.GET)
	public ModelAndView registerPage() {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("register");
		return mav;

	}
    
    @RequestMapping(value= "/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest rec, Model mod) {
    	 
    	ModelAndView mav = new ModelAndView();
    	
    	HttpSession session = rec.getSession();
    	String username = rec.getParameter("username");
    	String inputPassword = rec.getParameter("password");


    	
    	List<Utente> utenti = ur.findAll();
    	
    	for(Utente u : utenti) {
    		if((u.getUsername().equals(username) || u.getEmail().equals(username)) && BCrypt.checkpw(inputPassword, u.getPassword()) && u.isConfermato()) {
    			String uname = u.getUsername();
    			session.setAttribute("username" , uname);
    			if(u.getUsername().equals("admin")) {
        			
        			return new ModelAndView ("redirect:/admin_page");
        		}else 
        			return new ModelAndView ("redirect:/user_page");
        		
        		
        	
        	}
    	}
    	
    	mod.addAttribute("errorMessage","Le Credenziali inserite sono sbagliate, riprova");
    	mav.setViewName("errore");
    	return mav;

    }
    
    @RequestMapping(value= "/register", method = RequestMethod.POST)
    public ModelAndView register(HttpServletRequest rec, Model mod) {
    	
    	ModelAndView mav = new ModelAndView();
    	String username = rec.getParameter("username");
    	String password = rec.getParameter("password");
    	String email = rec.getParameter("email");
    	String nome = rec.getParameter("nome");
    	String cognome = rec.getParameter("cognome");
    	
    	
		 HttpSession session = rec.getSession();
		//mail check system
//		Random randidtoken = new Random();
//		Integer idtoken = randidtoken.nextInt(100000000-9000000) - 900000;//il range del id token, x non sforare le 10 cifre
//		String SIDTOKEN = idtoken.toString();
//		session.setAttribute("token", SIDTOKEN);
		
		session.setAttribute("username", username );
		List<Utente> utenti = ur.findAll();
		for(Utente u : utenti) {
			
			if(u.getUsername().equals(username) || u.getEmail().equals(email)) {
				
				mod.addAttribute("errorMessage", "nome Utente o Email già registrati nel DataBase");
				
				mav.setViewName("errore");
				
				return mav;
			}
			//blocco spam
			if(username.length() > 15 || nome.length() > 15 || cognome.length() > 15) {
				
				mod.addAttribute("errorMessage", "campi per la registrazione troppo lunghi , riprova");
				
				mav.setViewName("errore");
				
				return mav;
			}
			////////////////////////////////////
		}
		
		//mail Sender
		
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom(env.getProperty("spring.mail.username"));
	        message.setTo(rec.getParameterValues("email"));
	        message.setSubject("Activation Mail System"); 
	        String url = "https://mensapoggiomirteto.com/conferma?&username="+username;
	        message.setText("Questo è il suo username : " + username + "\n" + "questa è la sua password :" + password + "\n" + "Clicca qui per confermare la registrazione:\n" + url);

	        
	        mailSender.send(message);
	     //--------------   
    	
    	Utente u = new Utente();
    	u.setUsername(username);
    	u.setNome(nome);
    	u.setCognome(cognome);
    	
    	String hashedPassword = uj.hashPassword(password);
    	u.setPassword(hashedPassword);
    	u.setEmail(email);
    	u.setConfermato(false);
    	
    	
    
    	us.saveUser(u);
    	
    	mav.setViewName("login");
    	return mav;
    	
    	
    }
    
    @RequestMapping(value= "/user_page", method = RequestMethod.GET)
    public ModelAndView userPage(HttpServletRequest rec,Model mod) {
   	 
    	ModelAndView mav = new ModelAndView();
    	
    	HttpSession session = rec.getSession();
    	
    	String utenteUsername = session.getAttribute("username").toString(); 
    	
    	Utente u = us.findByUsername(utenteUsername);
    	List<Alunno> alunni = u.getAlunni();
    	
    	mav.addObject("alunni", alunni);
    	
    	
    	System.out.println("sessioneee" + " " + session + "\n");
    	System.out.println("sessioneeeUsername" + " " + session.getAttribute("username") + "\n");
   

    	
    	try {
			if(session.getAttribute("username").equals(null)) {
				
			}
		} catch (Exception e) {
			mod.addAttribute("errorMessage" , "non hai permessi per entrare in questa pagina, prova prima ad accedere con un account registrato");

			mav.setViewName("errore");
			return mav;
		}
    	
    	
    	mav.setViewName("user_page");
		return mav;
    }
    

    
    @RequestMapping(value= "/errore", method = RequestMethod.GET)
    public ModelAndView errorPage() {
    	 
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("errore");
    	return mav;
    	
    }
    
    @RequestMapping(value= "/conferma", method = RequestMethod.GET)
    public ModelAndView confermaEmail(HttpServletRequest rec,Model mod) {
    	
//    	String token = rec.getParameter("token");
    	String username = rec.getParameter("username");
    	
    	HttpSession session = rec.getSession();
//    	 String tokenPreso = session.getAttribute("token").toString();
//    	 if(tokenPreso.equals(token)) {
//  
    		 
    	     Utente u = us.findByUsername(username);
    	     u.setConfermato(true);
    	     
    	     us.saveUser(u);
    	     ModelAndView mav = new ModelAndView();
    	    	mav.setViewName("login");
    	    	return mav;

    	    	
//    	 }
    	 
// 		mod.addAttribute("errorMessage" , "qualcosa è andato storto durante la registrazione, riprova :) ");

    	
//    	return new ModelAndView("redirect:/errore");
    	
    }
      @RequestMapping(value= "/form_page", method = RequestMethod.GET)
    public ModelAndView formPage(HttpServletRequest rec , Model mod) {
    	  ModelAndView mav = new ModelAndView();
    	 HttpSession session = rec.getSession();
    	  String utenteUsername = session.getAttribute("username").toString();
    	Utente u = us.findByUsername(utenteUsername);
	  
      	List<Alunno> alunniutente = u.getAlunni();
    	  
    		for(Alunno al : alunniutente) {
    			if(al.getResiduoUtente() < -10) {
    			
    			   mod.addAttribute("errorMessage" , "Non è possibile fare una nuova iscrizione per mancati pagamenti degli anni precedenti");
    				
    			    mav.setViewName("errore");
    			    return mav;
    			
    			}
    		
    		}
    	
    	  Alunno a = new Alunno();
    	
    	mav.addObject("alunno", a);
    	mav.setViewName("form_page");
    	return mav;
    	
    }
    
    @RequestMapping(value= "/iscrizione", method = RequestMethod.POST)
    public ModelAndView iscrivi(Alunno a,@RequestParam boolean privacyOk,HttpServletRequest rec , Model mod) throws ParseException, IllegalArgumentException, IllegalAccessException {
    	ModelAndView mav = new ModelAndView();
    	HttpSession session = rec.getSession();
    	String utenteUsername = session.getAttribute("username").toString();  	
    	Utente u = us.findByUsername(utenteUsername);
    	List<Alunno> alunniutente = u.getAlunni();
    	
    	
    	
    	
    	
    	
 
//    	String nome = rec.getParameter("nome");
//    	String cognome = rec.getParameter("cognome");
//    	String luogoNascita = rec.getParameter("luogoNascita");
    	String codiceFiscale = rec.getParameter("codiceFiscale");
    	String luogoNascitaRichidente = rec.getParameter("luogoNascitaRichidente");
    	String indirizzoRichidente = rec.getParameter("indirizzoRichidente");
    	String civicoRichidente = rec.getParameter("civicoRichidente");
    	String codiceFiscaleRichiedente = rec.getParameter("codiceFiscaleRichiedente");
    	String quotaUtente = rec.getParameter("quotaUtente");
    	double quotaUtenteInt = Double.parseDouble(quotaUtente);
    
//    	String dataNascita = rec.getParameter("dataNascita"); // Supponiamo che sia nel formato "AAAA-MM-GG"
//    	// Parsa la stringa nel formato "AAAA-MM-GG" in un oggetto LocalDate
//    	LocalDate dataNascitaParsed = LocalDate.parse(dataNascita);
//    	// Formatta la data nel formato desiderato "dd-MM-yyyy"
//    	String dataNascitaFormatted = dataNascitaParsed.format(formatter);
//    	LocalDate dataNascitaFinal = LocalDate.parse(dataNascitaFormatted, formatter);
    	
    	SimpleDateFormat dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);

	    Date dataiscrizione = new Date();
	    
	    
	    LocalDate date = LocalDate.now();
	   
	    int meseCorrente = date.getMonthValue();

        // Calcola gli anni accademici
        int anno1, anno2;
        if (meseCorrente >= 1 && meseCorrente <= 6) {
            // Se ci si registra tra gennaio e giugno, l'anno accademico inizia l'anno corrente
            anno1 = date.getYear() - 1 ;
        } else {
            // Se ci si registra da luglio in poi, l'anno accademico inizia l'anno successivo
            anno1 = date.getYear() ;
        }
        anno2 = anno1 + 1;

        // Costruisci l'anno accademico
        String annoAccademico = anno1 + "/" + anno2;
//    	
//    	
//    	
    	
//    	
       	
	    List<Alunno> alunni = as.getAllAlunni();
	    
	    //caso upload admin
	    for (Alunno al : alunni) {
	    	System.out.println("iterazione numero: " + al.getId());
	    	if(al.getCodiceFiscale().equals(codiceFiscale) && al.getResiduoUtente() < -10){
	    		
	    		mod.addAttribute("errorMessage" , "Questo/a alunno/a presenta delle morosità da recuperare, perciò non è possibile iscriverlo al momento");
	    		   
	    	    mav.setViewName("errore");
	    	    
	    	    
	    	    return mav;
	    		
	    		
	    		
	    	}
	    	else if (al.getCodiceFiscale().equals(codiceFiscale) && al.getDataIscrizione() == null) {
	            Field[] fields = Alunno.class.getDeclaredFields();
	            for (Field field : fields) {
	                field.setAccessible(true);
	                String fieldName = field.getName();
	                if (!fieldName.equals("id")) { // Escludi il campo ID durante la copia dei valori
	                    Object value = field.get(a);
	                    field.set(al, value);
	                }
	            }
	            
	            
	            
	            // Stessa quota per tutti i mesi
	            al.setQuotaUtenteOttobre(quotaUtenteInt);
	            al.setQuotaUtenteNovembre(quotaUtenteInt);
	            al.setQuotaUtenteDicembre(quotaUtenteInt);
	           al.setQuotaUtenteGennaio(quotaUtenteInt);
	            al.setQuotaUtenteFebbraio(quotaUtenteInt);
	            al.setQuotaUtenteMarzo(quotaUtenteInt);
	            al.setQuotaUtenteAprile(quotaUtenteInt);
	           al.setQuotaUtenteMaggio(quotaUtenteInt);
	            al.setQuotaUtenteGiugno(quotaUtenteInt);
	            
	            
	            
	          
	           
	            
	            al.setCodiceFiscaleRichiedente(codiceFiscaleRichiedente);
	            al.setLuogoNascitaRichidente(luogoNascitaRichidente);
	            al.setIndirizzoRichidente(indirizzoRichidente);
	            al.setCivicoRichidente(civicoRichidente);
	            al.setDataIscrizione(dataiscrizione);
	            al.setAnnoAccademico(annoAccademico);
	            al.setNomeRichiedente(u.getNome());
	            al.setCognomeRichidente(u.getCognome());
	            al.setEmail(u.getEmail());
	            al.setUtente(u);
	            al.setIscritto(true);
	            
	            as.saveAlunno(al);
	            
	            
	            

	    		if(u.getUsername().equals("admin")) {
	    			
	    			return new ModelAndView ("redirect:/admin_page");
	    		}
	    		
	            return new ModelAndView("redirect:/user_page");
	        }
	        
         
	        
	    }
	   
	   mod.addAttribute("errorMessage" , "Upload ancora non eseguito o Alunno gia iscritto per quest'anno accademico, contattare l'admin");
	   
	    mav.setViewName("errore");
	    
	    
	    return mav;
	    
	    

//	    
//	    boolean privacyAccettata = a.isPrivacyOk();
//	    a.setPrivacyOk(privacyAccettata);
//      		a.setUtente(u);
//      		     		
//      		a.setAnnoAccademico(annoAccademico);
//      		
//     
//       	
//       	a.setDataIscrizione(dataiscrizione);
//       	
//    	as.saveAlunno(a);
//    				if(u.getUsername().equals("admin")) {
//    					return new ModelAndView("redirect:/admin_page");
//    				}
//    	
//    	return new ModelAndView("redirect:/user_page");
    }
    
    @RequestMapping(value= "/logout", method = RequestMethod.POST)
    public ModelAndView logout(HttpServletRequest rec) throws ParseException {
    	ModelAndView mav = new ModelAndView();
    	rec.getSession().invalidate();
    	mav.setViewName("index");
    	
    	
    	
    	
    	return mav;
    	
    	
    }
    	
    	
    
    @RequestMapping(value= "/stampa_id", method = RequestMethod.POST)
    public ModelAndView stampa(HttpServletRequest rec) throws ParseException {
    	ModelAndView mav = new ModelAndView();


    	String id = rec.getParameter("id");
    	Integer i = 0 ;
    	Alunno alunno = as.findById(i.parseInt(id));
    	mav.addObject("alunno", alunno);
    	mav.setViewName("print_page");
    	return mav;
    	
    	
    	
    }
    
	@RequestMapping(value= "/recupero", method = RequestMethod.GET)
	public ModelAndView recupera(HttpServletRequest rec) throws ParseException {
		ModelAndView mav = new ModelAndView();
		
		
		
		
		
		mav.setViewName("recupero_page");
		return mav;
		
	}
	
   	
	
	@RequestMapping(value= "/recuperopsw", method = RequestMethod.POST)
	public ModelAndView recuperapsw(HttpServletRequest rec,
			@RequestParam("email")String email) throws ParseException {
		ModelAndView mav = new ModelAndView();
		
		System.out.println(email);
		
		
		List<Utente> utenti = ur.findAll();
		
		for(Utente u : utenti) {
			if(u.getEmail().equals(email)) {
				String username = u.getUsername();
				
				 SimpleMailMessage message = new SimpleMailMessage();
			        message.setFrom(env.getProperty("spring.mail.username"));
			        message.setTo(rec.getParameterValues("email"));
			        message.setSubject("Activation Mail System"); 
			       // String url = "http://127.0.0.1:8080/recuperopsw_page?username="+username;
//			        Random randidtoken = new Random();
//					Integer idtoken = randidtoken.nextInt(100000000-9000000) - 900000;//il range del id token, x non sforare le 10 cifre
//					String SIDTOKEN = idtoken.toString();
					rec.getSession();
		//			session.setAttribute("token", SIDTOKEN);
			        
			        String url = "https://mensapoggiomirteto.com/recuperopsw_page?username="+username;
			        message.setText("clicca qui per proseguire con il recupero password:\n"+url);
			        
			        mailSender.send(message);
			        
			}
			
			
			
		}
		
		
	
		mav.setViewName("recupero_page");
		return mav;
		
	}
	
	
	
	@RequestMapping(value= "/recuperopsw_page", method = RequestMethod.GET)
	public ModelAndView recuperapsw_page(HttpServletRequest rec,
			@RequestParam("username") String username, Model mod) throws ParseException {
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = rec.getSession();
		session.setAttribute("username", username);
//		String getoken = session.getAttribute("token").toString();
//		if(getoken.equals(token)){
//			
			mav.setViewName("recuperopsw_page");
			return mav;
//			
//		}
//		
//		mod.addAttribute("errorMessage" , "qualcosa è andato storto con il recupero della password, riprova :)");
//
//		
//		mav.setViewName("errore");
//		return mav;
		
	}
    
	@RequestMapping(value= "/nuovapassword", method = RequestMethod.POST)
	public ModelAndView recuperapsw_page(HttpServletRequest rec,Model mod) throws ParseException {
		ModelAndView mav = new ModelAndView();
		HttpSession session = rec.getSession();
		String password = rec.getParameter("password");
		String passwordconfermata = rec.getParameter("passwordconfermata");
		
		if(passwordconfermata.equals(password)){
			
			String username = session.getAttribute("username").toString();
			Utente u = us.findByUsername(username);
			String hashedPassword = uj.hashPassword(password);
			u.setPassword(hashedPassword);
			
			us.saveUser(u);
			

			mav.setViewName("login");
			return mav;
			
		}
		mod.addAttribute("errorMessage" , "qualcosa è andato storto con il reset della password, riprova :)");

			mav.setViewName("errore");
			return mav;
			
		
	}
    
	
	
	
	
}


