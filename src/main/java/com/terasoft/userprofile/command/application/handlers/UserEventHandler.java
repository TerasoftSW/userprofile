package com.terasoft.userprofile.command.application.handlers;

import com.terasoft.userprofile.command.infrastructure.UserEmail;
import com.terasoft.userprofile.command.infrastructure.repositories.UserEmailRepository;
import com.terasoft.userprofile.contracts.events.CustomerEdited;
import com.terasoft.userprofile.contracts.events.CustomerRegistered;
import com.terasoft.userprofile.contracts.events.LawyerEdited;
import com.terasoft.userprofile.contracts.events.LawyerRegistered;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ProcessingGroup("userEmail")
public class UserEventHandler {
    private final UserEmailRepository userEmailRepository;

    public UserEventHandler(UserEmailRepository userEmailRepository) {
        this.userEmailRepository = userEmailRepository;
    }

    @EventHandler
    public void on (CustomerRegistered event){
        userEmailRepository.save(new UserEmail(event.getId(), event.getEmail()));
    }

    @EventHandler
    public void on (LawyerRegistered event){
        userEmailRepository.save(new UserEmail(event.getId(), event.getEmail()));
    }

    @EventHandler
    public void on (CustomerEdited event){
        Optional<UserEmail> UserEmailOptional = userEmailRepository.getEmailByUserId(event.getId());
        UserEmailOptional.ifPresent(userEmailRepository::delete);
        userEmailRepository.save(new UserEmail(event.getId(), event.getEmail()));
    }

    public void on (LawyerEdited event){
        Optional<UserEmail> UserEmailOptional = userEmailRepository.getEmailByUserId(event.getId());
        UserEmailOptional.ifPresent(userEmailRepository::delete);
        userEmailRepository.save(new UserEmail(event.getId(), event.getEmail()));
    }
}
