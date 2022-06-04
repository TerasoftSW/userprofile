package com.terasoft.userprofile.command.application.validators;

import com.terasoft.userprofile.command.application.dtos.request.EditCustomerRequest;
import com.terasoft.common.application.Notification;
import com.terasoft.userprofile.command.domain.entities.Customer;
import com.terasoft.userprofile.command.infrastructure.repositories.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class EditCustomerValidator {
    private final CustomerRepository customerRepository;

    public EditCustomerValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Notification validate(EditCustomerRequest editCustomerRequest){
        Notification notification = new Notification();
        
        String customerId = editCustomerRequest.getId().trim();
        if (customerId.isEmpty()) {
            notification.addError("Customer id is required");
        }
        Optional<Customer> customerIdOptional = customerRepository.findById(UUID.fromString(customerId));
        if (customerIdOptional.isPresent()) {
            notification.addError("Customer not found");
            return notification;
        }

        String userName = editCustomerRequest.getUserName() != null ? editCustomerRequest.getUserName().trim() : "";
        if (userName.isEmpty()) {
            notification.addError("Username is required");
        }
        String password = editCustomerRequest.getPassword() != null ? editCustomerRequest.getPassword().trim() : "";
        if (password.isEmpty()) {
            notification.addError("Password is required");
        }
        String firstName = editCustomerRequest.getFirstName() != null ? editCustomerRequest.getFirstName().trim() : "";
        if (firstName.isEmpty()) {
            notification.addError("Customer firstname is required");
        }
        String lastName = editCustomerRequest.getLastName() != null ? editCustomerRequest.getLastName().trim() : "";
        if (lastName.isEmpty()) {
            notification.addError("Customer lastname is required");
        }
        String email = editCustomerRequest.getEmail() != null ? editCustomerRequest.getEmail().trim() : "";
        if (email.isEmpty()) {
            notification.addError("Customer email is required");
        }
        if (notification.hasErrors()) {
            return notification;
        }

        Optional<Customer> customerUserNameOptional = customerRepository.findByUserName(userName);
        if (customerUserNameOptional.isPresent()) {
            notification.addError("UserName is taken");
        }
        Optional<Customer> emailCustomerOptional = customerRepository.findByEmail(email);
        if (emailCustomerOptional.isPresent()) {
            notification.addError("Customer email is taken");
        }

        return notification;
    }
}
