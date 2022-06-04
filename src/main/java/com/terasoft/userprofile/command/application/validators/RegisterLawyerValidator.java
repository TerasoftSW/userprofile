package com.terasoft.userprofile.command.application.validators;

import com.terasoft.common.application.Notification;
import com.terasoft.userprofile.command.application.dtos.request.RegisterLawyerRequest;
import com.terasoft.userprofile.command.domain.entities.Lawyer;
import com.terasoft.userprofile.command.infrastructure.repositories.LawyerRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class RegisterLawyerValidator {
    private final LawyerRepository lawyerRepository;

    public RegisterLawyerValidator(LawyerRepository lawyerRepository) {
        this.lawyerRepository = lawyerRepository;
    }

    public Notification validate(RegisterLawyerRequest registerLawyerRequest){
        Notification notification = new Notification();

        String userName = registerLawyerRequest.getUserName() != null ? registerLawyerRequest.getUserName().trim() : "";
        if (userName.isEmpty()) {
            notification.addError("Username is required");
        }
        String password = registerLawyerRequest.getPassword() != null ? registerLawyerRequest.getPassword().trim() : "";
        if (password.isEmpty()) {
            notification.addError("Password is required");
        }
        String firstName = registerLawyerRequest.getFirstName() != null ? registerLawyerRequest.getFirstName().trim() : "";
        if (firstName.isEmpty()) {
            notification.addError("Lawyer firstname is required");
        }
        String lastName = registerLawyerRequest.getLastName() != null ? registerLawyerRequest.getLastName().trim() : "";
        if (lastName.isEmpty()) {
            notification.addError("Lawyer lastname is required");
        }
        String email = registerLawyerRequest.getEmail() != null ? registerLawyerRequest.getEmail().trim() : "";
        if (email.isEmpty()) {
            notification.addError("Lawyer email is required");
        }

        String university = registerLawyerRequest.getUniversity() != null ? registerLawyerRequest.getUniversity().trim() : "";
        if (university.isEmpty()) {
            notification.addError("Lawyer university is required");
        }

        int specialization = registerLawyerRequest.getSpecialization();
        if(specialization < 0 || specialization >= 4){
            notification.addError("Invalid Specialization");
        }

        BigDecimal lawyerPrice = registerLawyerRequest.getLawyerPrice();
        if(lawyerPrice.compareTo(BigDecimal.ZERO) < 0 ){
            notification.addError("Invalid Lawyer Price");
        }

        if (notification.hasErrors()) {
            return notification;
        }

        Optional<Lawyer> lawyerOptional = lawyerRepository.findByUserNameValue(userName);
        if (lawyerOptional.isPresent()) {
            notification.addError("UserName is taken");
        }
        Optional<Lawyer> emailLawyerOptional = lawyerRepository.findByEmailValue(email);
        if (emailLawyerOptional.isPresent()) {
            notification.addError("Lawyer email is taken");
        }
        return notification;
    }
}