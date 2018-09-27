package com.economic.mail;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class Mailer {

	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private TemplateEngine thymeleaf;
	
	public void sendMail(String from, List<String> to, 
			String subject, String template, Map<String, Object> variables) {
		
		Context context = new Context(new Locale("pt", "BR"));
		variables.entrySet().forEach(
				e -> context.setVariable(e.getKey(), e.getValue()));
		
		String message = thymeleaf.process(template, context);
		this.sendMail(from, to, subject, message);
	}
	
	public void sendMail(String from, List<String> to, String subject, String message) {
		try {
			MimeMessage mimeMessage = sender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(from);
			helper.setTo(to.toArray(new String[0]));
			helper.setSubject(subject);
			helper.setText(message, true);
			
			sender.send(mimeMessage);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao enviar e-mail", e);
		}
	}
	
}
