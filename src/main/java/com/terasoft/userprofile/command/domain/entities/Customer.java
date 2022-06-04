package com.terasoft.userprofile.command.domain.entities;

import com.terasoft.common.domain.enums.UserState;
import com.terasoft.userprofile.command.domain.values.*;
import com.terasoft.userprofile.contracts.commands.EditCustomer;
import com.terasoft.userprofile.contracts.commands.RegisterCustomer;
import com.terasoft.userprofile.contracts.events.CustomerEdited;
import com.terasoft.userprofile.contracts.events.CustomerRegistered;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;

import javax.persistence.*;

import java.time.Instant;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Entity
@Data
@DiscriminatorValue(value = "0") //Customer
public class Customer extends User {

    public Customer(UserId userId, UserName userName, Password password , FullName fullName, Email email, UserState userState, AuditTrail auditTrail){
        super(userId, userName, password, fullName, email, userState,auditTrail);
    }

    public Customer() {
    }

    /*@CommandHandler
    public Customer(RegisterCustomer command){
        Instant now = Instant.now();
        apply(
                new CustomerRegistered(
                        command.getCustomerId(),
                        command.getUserName(),
                        command.getFirstName(),
                        command.getLastName(),
                        command.getEmail(),
                        now
                )
        );
    }*/

    @CommandHandler
    public void handle(EditCustomer command){
        Instant now = Instant.now();
        apply(
                new CustomerEdited(
                        command.getCustomerId(),
                        command.getUserName(),
                        command.getFirstName(),
                        command.getLastName(),
                        command.getEmail(),
                        now
                )
        );
    }


    /*@EventSourcingHandler
    protected void on(CustomerEdited event){
    }*/
}
