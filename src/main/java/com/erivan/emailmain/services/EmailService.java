package com.erivan.emailmain.services;

import com.erivan.emailmain.enums.StatusEmail;
import com.erivan.emailmain.models.EmailModel;
import com.erivan.emailmain.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public EmailModel sendEmail(EmailModel emailModel){
        emailModel.setSendDateEmail(LocalDateTime.now());

        try{

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT); //se enviou seta com SENT

        }catch (MailException e){
            emailModel.setStatusEmail(StatusEmail.ERROR); //se não enviou seta com ERROR

        }finally {
            return emailRepository.save(emailModel); //ele salva no banco de dados enviando ou não
        }
    }

    public Page<EmailModel> findAll(Pageable pageable){
        return emailRepository.findAll(pageable);
    }

}
