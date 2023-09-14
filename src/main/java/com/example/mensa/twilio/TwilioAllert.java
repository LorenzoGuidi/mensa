package com.example.mensa.twilio;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class TwilioAllert {
	
	
	 public static final String ACCOUNT_SID = "ACdffa7b06132b48ad44cb39d6807031b3";
	 public static final String AUTH_TOKEN = "7d5a59d90ded6cc1d281d71c1c4ffce3";
	 
	 
	 public void segnala(String telefono) {
		 
		
		 Twilio.init(ACCOUNT_SID ,AUTH_TOKEN);
		 Message message = Message.creator(
	               new com.twilio.type.PhoneNumber("+39"+telefono),
	               new com.twilio.type.PhoneNumber("+17628008011"), //triplo sette everywhere.... al rilascio scrivere telefono :D
	               "Questo è un messaggio per ricordarti che sta per scadere la rata del pagamento per la mensa")
	           .create();

		
	       System.out.println(message.getSid());
		 
	 }
	 
	 
	  


}
