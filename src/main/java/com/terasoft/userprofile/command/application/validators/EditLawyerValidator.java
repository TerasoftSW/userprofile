package com.terasoft.userprofile.command.application.validators;

import com.terasoft.common.application.Notification;
import com.terasoft.userprofile.command.application.dtos.request.EditLawyerRequest;
import com.terasoft.userprofile.command.domain.entities.Lawyer;

import com.terasoft.userprofile.command.infrastructure.repositories.LawyerRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class EditLawyerValidator {
    private final LawyerRepository lawyerRepository;

    public EditLawyerValidator(LawyerRepository lawyerRepository) {
        this.lawyerRepository = lawyerRepository;
    }

    public Notification validate(EditLawyerRequest editLawyerRequest){
        Notification notification = new Notification();

        String lawyerId = editLawyerRequest.getId().trim();
        if (lawyerId.isEmpty()) {
            notification.addError("Lawyer id is required");
        }
        Optional<Lawyer> lawyerIdOptional = lawyerRepository.findByIdValue(UUID.fromString(lawyerId));
        if (lawyerIdOptional.isPresent()) {
            notification.addError("Lawyer not found");
            return notification;
        }

        String userName = editLawyerRequest.getUserName() != null ? editLawyerRequest.getUserName().trim() : "";
        if (userName.isEmpty()) {
            notification.addError("Username is required");
        }
        String password = editLawyerRequest.getPassword() != null ? editLawyerRequest.getPassword().trim() : "";
        if (password.isEmpty()) {
            notification.addError("Password is required");
        }
        String firstName = editLawyerRequest.getFirstName() != null ? editLawyerRequest.getFirstName().trim() : "";
        if (firstName.isEmpty()) {
            notification.addError("Lawyer firstname is required");
        }
        String lastName = editLawyerRequest.getLastName() != null ? editLawyerRequest.getLastName().trim() : "";
        if (lastName.isEmpty()) {
            notification.addError("Lawyer lastname is required");
        }
        String email = editLawyerRequest.getEmail() != null ? editLawyerRequest.getEmail().trim() : "";
        if (email.isEmpty()) {
            notification.addError("Lawyer email is required");
        }

        String university = editLawyerRequest.getUniversity() != null ? editLawyerRequest.getUniversity().trim() : "";
        if (university.isEmpty()) {
            notification.addError("Lawyer university is required");
        }

        int specialization = editLawyerRequest.getSpecialization();
        if(specialization < 0 || specialization >= 4){
            notification.addError("Invalid Specialization");
        }

        BigDecimal lawyerPrice = editLawyerRequest.getLawyerPrice();
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
