package com.economic.mail;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class Mailer {

	@Autowired
	private JavaMailSender sender;
	
	public void send(String from, List<String> to, String subject, String message) {
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
