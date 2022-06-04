package com.terasoft.userprofile.command.application.handlers;

import com.terasoft.common.application.Notification;
import com.terasoft.common.application.Result;
import com.terasoft.common.domain.enums.UserState;
import com.terasoft.userprofile.command.domain.entities.Customer;
import com.terasoft.userprofile.command.domain.values.*;
import com.terasoft.userprofile.command.infrastructure.repositories.CustomerRepository;
import com.terasoft.userprofile.contracts.commands.RegisterCustomer;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegisterCustomerHandler {
    private CustomerRepository customerRepository;
    public  RegisterCustomerHandler (CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @CommandHandler
    public Result<Customer, Notification> handle (RegisterCustomer command){
        Notification notification = new Notification();
        UserId userId = UserId.of(UUID.fromString(command.getCustomerId()));
        Result<UserName, Notification> userNameResult = UserName.create(command.getUserName());
        if(userNameResult.isFailure()){
            notification.addError(userNameResult.getFailure().errorMessage());
        }
        Result<FullName, Notification> fullNameResult = FullName.create(command.getFirstName(), command.getLastName());
        if(fullNameResult.isFailure()){
            notification.addError(fullNameResult.getFailure().errorMessage());
        }
        Result<Email, Notification> emailResult = Email.create(command.getEmail());
        if(emailResult.isFailure()){
            notification.addError(emailResult.getFailure().errorMessage());
        }

        Result<Password, Notification> passwordResult = Password.create(command.getPassword());
        if(passwordResult.isFailure()){
            notification.addError(passwordResult.getFailure().errorMessage());
        }

        Result<AuditTrail, Notification> auditTrailResult = AuditTrail.create(UUID.randomUUID());
        if (auditTrailResult.isFailure()) {
            notification.addError(auditTrailResult.getFailure().errorMessage());
        }

        if(notification.hasErrors()){
            return Result.failure(notification);
        }

        Customer customer = new Customer(
                userId,
                userNameResult.getSuccess(),
                passwordResult.getSuccess(),
                fullNameResult.getSuccess(),
                emailResult.getSuccess(),
                UserState.ACTIVE,
                auditTrailResult.getSuccess()
        );

        customerRepository.save(customer);
        return Result.success(customer);
    }

}