package com.erivan.emailmain.repositories;

import com.erivan.emailmain.models.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository  extends JpaRepository<EmailModel, Long> {
}
