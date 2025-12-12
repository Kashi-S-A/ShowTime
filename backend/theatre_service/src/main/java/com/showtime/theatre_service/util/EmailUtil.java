package com.showtime.theatre_service.util;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class EmailUtil {
	
	 	@Autowired
	    private JavaMailSender mailSender;

	    @Autowired
	    private TemplateEngine templateEngine;

	    public void sendEmail(String to, String subject, String templateName, Map<String, Object> model) {
	        try {
	            MimeMessage message = mailSender.createMimeMessage();
	            MimeMessageHelper helper =
	                    new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

	            // Load HTML template
	            Context context = new Context();
	            context.setVariables(model);

	            String htmlContent = templateEngine.process(templateName, context);
	            System.out.println("EMAIL CONTENT = " + htmlContent);
	            
	            helper.setTo(to);
	            helper.setSubject(subject);
	            helper.setText(htmlContent, true);

	            mailSender.send(message);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
