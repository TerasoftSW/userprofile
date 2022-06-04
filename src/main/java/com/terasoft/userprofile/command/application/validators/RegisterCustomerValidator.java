package com.terasoft.userprofile.command.application.validators;

import com.terasoft.common.application.Notification;
import com.terasoft.userprofile.command.application.dtos.request.RegisterCustomerRequest;
import com.terasoft.userprofile.command.domain.entities.Customer;
import com.terasoft.userprofile.command.infrastructure.repositories.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegisterCustomerValidator {
    private final CustomerRepository customerRepository;


    public RegisterCustomerValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Notification validate(RegisterCustomerRequest registerCustomerRequest){
        Notification notification = new Notification();

        String userName = registerCustomerRequest.getUserName() != null ? registerCustomerRequest.getUserName().trim() : "";
        if (userName.isEmpty()) {
            notification.addError("Username is required");
        }
        String password = registerCustomerRequest.getPassword() != null ? registerCustomerRequest.getPassword().trim() : "";
        if (password.isEmpty()) {
            notification.addError("Password is required");
        }
        String firstName = registerCustomerRequest.getFirstName() != null ? registerCustomerRequest.getFirstName().trim() : "";
        if (firstName.isEmpty()) {
            notification.addError("Customer firstname is required");
        }
        String lastName = registerCustomerRequest.getLastName() != null ? registerCustomerRequest.getLastName().trim() : "";
        if (lastName.isEmpty()) {
            notification.addError("Customer lastname is required");
        }
        String email = registerCustomerRequest.getEmail() != null ? registerCustomerRequest.getEmail().trim() : "";
        if (email.isEmpty()) {
            notification.addError("Customer email is required");
        }
        if (notification.hasErrors()) {
            return notification;
        }

        Optional<Customer> customerOptional = customerRepository.findByUserNameValue(userName);
        if (customerOptional.isPresent()) {
            notification.addError("UserName is taken");
        }
        Optional<Customer> emailCustomerOptional = customerRepository.findByEmailValue(email);
        if (emailCustomerOptional.isPresent()) {
            notification.addError("Customer email is taken");
        }
        return notification;
    }
}
