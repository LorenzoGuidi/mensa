package com.example.mensa.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.mensa.model.Alunno;
import com.example.mensa.model.Utente;
import com.example.mensa.repository.AlunnoRepository;
import com.example.mensa.service.AlunnoService;
import com.example.mensa.service.AlunnoSpecification;
import com.example.mensa.service.ExcelService;
import com.example.mensa.service.UtenteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Controller
public class AdminController {


	
	@Autowired
	HttpSession session; 
	
	@Autowired
	AlunnoService as;
	
	@Autowired
	AlunnoRepository ar; 
	
	@Autowired
	UtenteService us ; 
	
	@Autowired
	ExcelService excelService;
	

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	


    @RequestMapping(value= "/admin_page", method = RequestMethod.GET)
    public ModelAndView adminPage(HttpServletRequest rec,Model mod) {
    	 
    	ModelAndView mav = new ModelAndView();
    
    	 session = rec.getSession();
    	String username = session.getAttribute("username").toString();
   
    	List<Alunno> alunni = as.getAllAlunni();
    	
    	mav.addObject("alunno", alunni);
    	
    	if( username.equals("admin")) {
    		
    		mav.setViewName("admin_page");
    		
    		return mav;
    	}
    	
		mod.addAttribute("errorMessage" , "non hai permessi da admin per entrare in questa pagina");
		mav.setViewName("errore");
    	return mav;
    	
    }
    
    
    @RequestMapping(value= "/result_page", method = RequestMethod.GET)
    public ModelAndView resultPpage(HttpServletRequest rec) {
    	
    	ModelAndView mav = new ModelAndView();
    	
    	session = rec.getSession();
    	
    	List<Alunno> alunniNeg = new ArrayList<Alunno>();
    	List<Alunno> alunni = as.getAllAlunni();
    	for(Alunno a : alunni) {
    		if(a.getResiduoUtente()<0) {
    			alunniNeg.add(a);
    		}
    	}
    	mav.addObject("alunni", alunniNeg);

    	return mav;
    	
  
    	
    }
    
    
    @RequestMapping(value= "/search", method = RequestMethod.POST)
    public ModelAndView cerca(HttpServletRequest rec) throws ParseException {
    	ModelAndView mav = new ModelAndView();
    	
    	session = rec.getSession();
    	
    	
    	String ricerca = rec.getParameter("ricerca");
    	session.setAttribute("ricerca", ricerca);
    	String anno = rec.getParameter("anno1");
    	
//    	 List<String> anniAccademici = as.getAnniAccademico(); // Assumi che sia implementato
//
//         Passiamo la lista di anni accademici al template Thymeleaf
//          mav.addObject("anno", anniAccademici);
//    	
    	
    	
    	Specification<Alunno> spec = AlunnoSpecification.getCodiceFiscaleAndSezioneAndAnnoAccademico(ricerca, anno);
 	    List<Alunno> storeItemList = ar.findAll(spec);
 	    mav.addObject("alunno", storeItemList);
 	
    	mav.setViewName("admin_page");
    	return mav;
    
    }
    

    @RequestMapping(value= "/delete/{id}", method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable Integer id,HttpServletRequest rec) {
    	ModelAndView mav = new ModelAndView();
    
    	Alunno a = ar.getOne(id.toString());
    	String annoAccademico = a.getAnnoAccademico();
    	String codiceFiscale = a.getCodiceFiscale();
    	ar.delete(a);
  
    	
    	
    	
    		Object ricerca = session.getAttribute("ricerca");
    
    
    	
    	if(ricerca != null) {

    		
    		Specification<Alunno> spec = AlunnoSpecification.getCodiceFiscaleAndSezioneAndAnnoAccademico(ricerca.toString() , annoAccademico);
    		List<Alunno> alunni = ar.findAll(spec); 
    		mav.addObject("alunno", alunni);
    		mav.setViewName("admin_page");	
    		return mav ; 
        }else {
        	mav.addObject("alunno", as.getAllAlunni());
        	mav.setViewName("admin_page");
        	return mav;
    	}
    }
    
    @RequestMapping(value= "/modifica/{id}", method = RequestMethod.POST)
    public ModelAndView modifica(@PathVariable Integer id,HttpServletRequest rec) {
    	ModelAndView mav = new ModelAndView();
    	
    	Alunno a = as.findById(id);
    	mav.addObject("alunno", a);
    	mav.setViewName("modifica_page");
    	
    	return mav;
    	
    
    }
    
    @RequestMapping(value= "/save", method = RequestMethod.POST)
    public ModelAndView save(Alunno alunno,HttpServletRequest rec) throws ParseException {
    	

	   
        
        
    	Alunno a = as.findById(alunno.getId());
    	
    	String username = rec.getParameter("username");
    	String luogoNascitaRichidente = rec.getParameter("luogoNascitaRichidente");
    	String indirizzoRichidente = rec.getParameter("indirizzoRichidente");
    	String civicoRichidente = rec.getParameter("civicoRichidente");
    	String codiceFiscaleRichiedente = rec.getParameter("codiceFiscaleRichiedente");
    	
    	
    	
    	
    	//Dati che prendiamo per fare i conti automatizzati
    	
    	String ImpPagato1 = rec.getParameter("impPagato1");
    	double ImpPagato1Double = Double.parseDouble(ImpPagato1);
    	
    	String ImpPagato2 = rec.getParameter("impPagato2");
    	double ImpPagato2Double = Double.parseDouble(ImpPagato2);
    	
    	String ImpPagato3 = rec.getParameter("impPagato3");
    	double ImpPagato3Double = Double.parseDouble(ImpPagato3);
    
    	String ImpPagato4 = rec.getParameter("impPagato4");
    	double ImpPagato4Double = Double.parseDouble(ImpPagato4);

    	String ImpPagato5 = rec.getParameter("impPagato5");
    	double ImpPagato5Double = Double.parseDouble(ImpPagato5);
    	
    	double PastoPagato1 = ImpPagato1Double / alunno.getQuotaUtente();
    	alunno.setPastoPagato1(PastoPagato1);
    	double PastoPagato2 = ImpPagato2Double / alunno.getQuotaUtente();
    	alunno.setPastoPagato2(PastoPagato2);
    	double PastoPagato3 = ImpPagato3Double / alunno.getQuotaUtente();
    	alunno.setPastoPagato3(PastoPagato3);
    	double PastoPagato4 = ImpPagato4Double / alunno.getQuotaUtente();
    	alunno.setPastoPagato4(PastoPagato4);
    	double PastoPagato5 = ImpPagato5Double / alunno.getQuotaUtente();
    	alunno.setPastoPagato5(PastoPagato5);
    	
//    	
//    	String presenzaOttobre = rec.getParameter("presenzaOttobre");
//    	double presenzaOttobreDouble = Double.parseDouble(presenzaOttobre);
//    	alunno.setDovutoUtenteOttobre(presenzaOttobreDouble*alunno.getQuotaUtente());
//    	alunno.setQuotaComuneOttobre((4.7952 * 1.04) - alunno.getQuotaUtente());
//    	alunno.setDovutoComuneOttobre(presenzaOttobreDouble*alunno.getQuotaComuneOttobre());
//    	
//    	
//    	
//    	String presenzaNovembre = rec.getParameter("presenzaNovembre");
//    	double presenzaNovembreDouble = Double.parseDouble(presenzaNovembre);
//    	alunno.setDovutoUtenteNovembre(presenzaNovembreDouble*alunno.getQuotaUtente());
//    	alunno.setQuotaComuneNovembre((4.7952 * 1.04) - alunno.getQuotaUtente());
//    	alunno.setDovutoComuneNovembre(presenzaNovembreDouble*alunno.getQuotaComuneNovembre());
//    	
//    	
//    	
//    	
//    	String presenzaDicembre = rec.getParameter("presenzaDicembre");
//    	double presenzaDicembreDouble = Double.parseDouble(presenzaDicembre);
//    	alunno.setDovutoUtenteDicembre(presenzaDicembreDouble*alunno.getQuotaUtente());
//    	alunno.setQuotaComuneDicembre((4.7952 * 1.04) - alunno.getQuotaUtente());
//    	alunno.setDovutoComuneDicembre(presenzaDicembreDouble*alunno.getQuotaComuneDicembre());
//    	
//    	
//    	
//    	
//    	
//    	String presenzaGennaio = rec.getParameter("presenzaGennaio");
//    	double presenzaGennaioDouble = Double.parseDouble(presenzaGennaio);
//    	alunno.setDovutoUtenteGennaio(presenzaGennaioDouble*alunno.getQuotaUtente());
//    	alunno.setQuotaComuneGennaio((4.7952 * 1.04) - alunno.getQuotaUtente());
//    	alunno.setDovutoComuneGennaio(presenzaGennaioDouble*alunno.getQuotaComuneGennaio());
//    	
//    	
//    	
//    	
//    	String presenzaFebbraio = rec.getParameter("presenzaFebbraio");
//    	double presenzaFebbraioDouble = Double.parseDouble(presenzaFebbraio);
//    	alunno.setDovutoUtenteFebbraio(presenzaFebbraioDouble*alunno.getQuotaUtente());
//    	alunno.setQuotaComuneFebbraio((4.7952 * 1.04) - alunno.getQuotaUtente());
//    	alunno.setDovutoComuneFebbraio(presenzaFebbraioDouble*alunno.getQuotaComuneFebbraio());
//    	
//    	
//    	
//    	
//    	String presenzaMarzo = rec.getParameter("presenzaMarzo");
//    	double presenzaMarzoDouble = Double.parseDouble(presenzaMarzo);
//    	alunno.setDovutoUtenteMarzo(presenzaMarzoDouble*alunno.getQuotaUtente());
//    	alunno.setQuotaComuneMarzo((4.7952 * 1.04) - alunno.getQuotaUtente());
//    	alunno.setDovutoComuneMarzo(presenzaMarzoDouble*alunno.getQuotaComuneMarzo());
//    	
//    	
//    	
//    	String presenzaAprile = rec.getParameter("presenzaAprile");
//    	double presenzaAprileDouble = Double.parseDouble(presenzaAprile);
//    	alunno.setDovutoUtenteAprile(presenzaAprileDouble*alunno.getQuotaUtente());
//    	alunno.setQuotaComuneAprile((4.7952 * 1.04) - alunno.getQuotaUtente());
//    	alunno.setDovutoComuneAprile(presenzaAprileDouble*alunno.getQuotaComuneAprile());
//    	
//    	
//    	
//    	String presenzaMaggio = rec.getParameter("presenzaMaggio");
//    	double presenzaMaggioDouble = Double.parseDouble(presenzaMaggio);
//    	alunno.setDovutoUtenteMaggio(presenzaMaggioDouble*alunno.getQuotaUtente());
//    	alunno.setQuotaComuneMaggio((4.7952 * 1.04) - alunno.getQuotaUtente());
//    	alunno.setDovutoComuneMaggio(presenzaMaggioDouble*alunno.getQuotaComuneMaggio());
//    	
//    	
//    	
//    	String presenzaGiugno = rec.getParameter("presenzaGiugno");
//    	double presenzaGiugnoDouble = Double.parseDouble(presenzaGiugno);
//    	alunno.setDovutoUtenteGiugno(presenzaGiugnoDouble*alunno.getQuotaUtente());
//    	alunno.setQuotaComuneGiugno((4.7952 * 1.04) - alunno.getQuotaUtente());
//    	alunno.setDovutoComuneGiugno(presenzaGiugnoDouble*alunno.getQuotaComuneGiugno());
//    	
//    
//    	
//    	
//    	
//    	
//    	
//    	
//    	alunno.setPresenzeRegistrate(presenzaOttobreDouble + presenzaNovembreDouble
//    			+ presenzaDicembreDouble + presenzaGennaioDouble + presenzaFebbraioDouble + presenzaMarzoDouble +
//    			presenzaAprileDouble + presenzaMaggioDouble + presenzaGiugnoDouble);
//    	
//    	alunno.setPastiPagati(PastoPagato1 + PastoPagato2 + PastoPagato3
//    			+ PastoPagato4 + PastoPagato5);
//    	
//    	
//    	alunno.setDifferenzaPasti(alunno.getPresenzeRegistrate() - alunno.getPastiPagati());
//    	
//    	
//    	alunno.setDovutoUtente(alunno.getDovutoUtenteOttobre() + alunno.getDovutoUtenteNovembre() + alunno.getDovutoUtenteDicembre() + 
//    			alunno.getDovutoUtenteGennaio() + alunno.getDovutoUtenteFebbraio() + 
//    			alunno.getDovutoUtenteMarzo() + alunno.getDovutoUtenteAprile() + 
//    			alunno.getDovutoUtenteMaggio() + alunno.getDovutoUtenteGiugno() );
//    
//    	alunno.setPagatoUtente(ImpPagato1Double + ImpPagato2Double + ImpPagato3Double + ImpPagato4Double + ImpPagato5Double);
//    	alunno.setResiduoUtente(alunno.getDifferenzaPasti() * alunno.getQuotaUtente());
//    	
    	
        Utente u =	us.findByUsername(username);

    	alunno.setDataIscrizione(a.getDataIscrizione());
    	alunno.setDataNascitaRichidente(a.getDataNascitaRichidente());
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	alunno.setCodiceFiscaleRichiedente(codiceFiscaleRichiedente);
    	alunno.setLuogoNascitaRichidente(luogoNascitaRichidente);
    	alunno.setIndirizzoRichidente(indirizzoRichidente);
    	alunno.setCivicoRichidente(civicoRichidente);
    	
    	alunno.setDataNascita(a.getDataNascita());
    	alunno.setGradoScuola(a.getGradoScuola());
    	alunno.setIscritto(a.isIscritto());
    	alunno.setPrivacyOk(a.isPrivacyOk());
    	alunno.setUtente(u);

    	alunno.setAnnoAccademico(a.getAnnoAccademico());
    	
    	
        //Conti di dati automatizzati

    	
    	
    	as.saveAlunno(alunno);

    	return new ModelAndView("redirect:/admin_page");
    	
    }
    
    @RequestMapping(value= "/report", method = RequestMethod.GET)
    public void getAlunniByDateRange(@RequestParam("dataInizio") String dataInizio,
                                     @RequestParam("dataFine")String dataFine,
                                     HttpServletResponse response) throws ParseException {  
    	
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	
    
    	
    	Date datein = formatter.parse(dataInizio);
    	Date datefin= formatter.parse(dataFine);
    	
    	System.out.println(datein);
    	System.out.println(datefin);

    	
		//LocalDate dataInizioConv = datein.format(formatter);
       // LocalDate dataFineConv = datefin.format(formatter);
        
       
	
  		List<Alunno> alunni = ar.findByDataBetween(datein, datefin);
		
    	for (Alunno alunno : alunni) {
    	    System.out.println(alunno.getCognome() + " - " + alunno.getNome());
    	}
    	
    	
        response.setContentType("application/octet-stream");
		ExcelService excelService = new ExcelService(alunni/* , dataInizioConv, dataFineConv */);
        try {
			excelService.generateExcelFile(response, dataInizio, dataFine);
		} catch (IOException e) {
			System.out.println("Errore nella generazione dell'excel con messaggio: " + e.getMessage());
		}
    }
    
    
    @RequestMapping(value= "/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();

            // Create Workbook instance for XLSX file format
            Workbook workbook = new XSSFWorkbook(inputStream);

            // Get the first sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);

            // Create a list to store the Alunno objects
            List<Alunno> alunni = new ArrayList<>();

            // Iterate over rows of the sheet
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Skip the header row
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Create a new Alunno object
                Alunno alunno = new Alunno();

                // Read the values from the cells
                Cell nomeCell = row.getCell(0);
                Cell cognomeCell = row.getCell(1);
                Cell codiceFiscaleCell = row.getCell(2);
                
                
                System.out.println(" ecco il dato: " + nomeCell);

                // Set the values in the Alunno object
                alunno.setNome(nomeCell.getStringCellValue());
                alunno.setCognome(cognomeCell.getStringCellValue());
                alunno.setCodiceFiscale(codiceFiscaleCell.getStringCellValue());
                Utente u = us.findByUsername("admin");
                alunno.setUtente(u);
               

                // Add the Alunno object to the list
                alunni.add(alunno);
            }

            // Save the Alunno objects to the database using AlunnoRepository
            ar.saveAll(alunni);

            // Close the workbook
            workbook.close();

            return "redirect:/admin_page"; // Redirect back to the admin page after successful upload
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Errore di upload dell'excel, dettagli: " + e.getMessage());
            return "redirect:/admin_page?error"; // Redirect to the admin page with an error flag
        }
    }

    

    
    
}

