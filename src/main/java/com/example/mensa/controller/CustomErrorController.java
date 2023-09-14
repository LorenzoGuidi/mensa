package com.example.mensa.controller;


import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;



import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;


import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Ottieni gli attributi dell'errore dalla richiesta
        Map<String, Object> errorAttributes =
            new DefaultErrorAttributes().getErrorAttributes(
                new ServletWebRequest(request),
                ErrorAttributeOptions.of(Include.EXCEPTION, Include.MESSAGE, Include.STACK_TRACE, Include.BINDING_ERRORS)
            );

        // Ottieni l'eccezione e il messaggio dell'errore
        Object error = errorAttributes.get("javax.servlet.error.exception");
        String errorMessage = (String) errorAttributes.get("message");

        // Aggiungi gli attributi del modello per la pagina HTML
        if (error != null) {
            model.addAttribute("errorType", error.getClass().getName());
            if (error instanceof ServletException) {
                model.addAttribute("stackTrace", ((ServletException) error).getRootCause().toString());
            }
        }
        model.addAttribute("errorMessage", errorMessage);

        // Restituisci il nome della pagina HTML personalizzata
        return "errore";
    }

   
    public String getErrorPath() {
        return "/error";
    }
}




