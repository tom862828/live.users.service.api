package com.tom.process.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailService {
	
	@Autowired
	private JavaMailSender sender;
	
	private ArrayList<String> receiver;
	private ArrayList<String> ccReceiver;
	private ArrayList<String> bccReceiver;
	private String mailSubject  = "";
	private String mailBody = "";
	private String cssLink = "<link rel=\"stylesheet\" type=\"text/css\" title=\"Style\" href=\"https://github.com/tom862828/TestWeb/blob/main/TestWeb/wwwroot/css/signin.css\">";
	
	public void setConfig(String mailSubject, String mailBody, ArrayList<String> receiver, ArrayList<String> ccReceiver, ArrayList<String> bccReceiver) {
		this.mailSubject = mailSubject;
		this.mailBody = this.cssLink + mailBody;
		this.receiver = receiver;
		this.ccReceiver = ccReceiver;
		this.bccReceiver = bccReceiver;
	}
	
	public void sendMessage() {
		
		try {
			MimeMessage message = this.sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom("User Service <tom862828@gmail.com>");
			helper.setSubject(this.mailSubject);
			helper.setText(this.mailBody, true);
			
			if(this.receiver != null) {
				for(String toEmail : this.receiver) {				
					helper.setTo(toEmail);
				}
			}
			
			if(ccReceiver != null) {
				for(String ccEmail : this.ccReceiver) {
					helper.setCc(ccEmail);
				}
			}
			
			if(bccReceiver != null) {
				for(String bccEmail : this.bccReceiver) {
					helper.setBcc(bccEmail);
				}
			}
			
			this.sender.send(message);
			
		}catch (Exception e) {
			// TODO: handle exception
			log.debug(e.toString());
		}
	}
}
