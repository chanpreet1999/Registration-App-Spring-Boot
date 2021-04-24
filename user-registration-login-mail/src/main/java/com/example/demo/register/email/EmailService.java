package com.example.demo.register.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

	private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EmailService.class);
	private static final String EMAIL_SUBJECT = "Confirm Your Account";
	private static final String EMAIL_FROM = "hello@chanpreet.com ";

	@Bean
	public JavaMailSender javaMailSender() {
		return new JavaMailSenderImpl();
	}

	@Override
	@Async
	public void send(String to, String email) {
		try {
			JavaMailSender mailSender = javaMailSender();
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setText(email, true);
			helper.setTo(to);
			helper.setSubject(EMAIL_SUBJECT);
			helper.setFrom(EMAIL_FROM);
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			LOGGER.info(e.getMessage());
		}
	}

}