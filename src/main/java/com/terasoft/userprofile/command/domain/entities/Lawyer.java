package com.terasoft.userprofile.command.domain.entities;

import com.terasoft.common.domain.enums.LawSpecialization;
import com.terasoft.common.domain.enums.UserState;
import com.terasoft.userprofile.command.domain.values.*;
import com.terasoft.userprofile.contracts.commands.EditLawyer;
import com.terasoft.userprofile.contracts.commands.RegisterLawyer;
import com.terasoft.userprofile.contracts.events.LawyerEdited;
import com.terasoft.userprofile.contracts.events.LawyerRegistered;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;

import javax.persistence.*;

import java.time.Instant;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Entity
@Data
@DiscriminatorValue(value = "1")
public class Lawyer extends User {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "address", length = 150, nullable = false))
    })
    private Address address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "university", length = 150, nullable = false))
    })
    private University university;

    @Column(name = "lawyer_specialization", columnDefinition = "TINYINT(1) UNSIGNED", nullable = false)
    private LawSpecialization specialization;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="amount",
                    column=@Column(name = "lawyer_price_amount",columnDefinition = "DECIMAL",nullable = false)),
            @AttributeOverride(name="currency",
                    column=@Column(name = "lawyer_price_currency", length = 50 ,nullable = false))
    })
    private Money lawyerPrice;

    public Lawyer(UserId userId, UserName userName, Password password , FullName fullName, Email email, UserState userState, AuditTrail auditTrail, Address address, University university, LawSpecialization specialization, Money lawyerPrice){
        super(userId, userName, password, fullName, email, userState,auditTrail);
        setAddress(address);
        setUniversity(university);
        setSpecialization(specialization);
        setLawyerPrice(lawyerPrice);
    }

    protected Lawyer() {
    }

    @CommandHandler
    public Lawyer(RegisterLawyer command){
        Instant now = Instant.now();
        apply(
                new LawyerRegistered(
                        command.getLawyerId(),
                        command.getUserName(),
                        command.getFirstName(),
                        command.getLastName(),
                        command.getEmail(),
                        command.getAddress(),
                        command.getUniversity(),
                        command.getSpecialization(),
                        command.getLawyerPrice(),
                        now
                )
        );
    }
    @CommandHandler
    public void handle(EditLawyer command){
        Instant now = Instant.now();
        apply(
                new LawyerEdited(
                        command.getLawyerId(),
                        command.getUserName(),
                        command.getFirstName(),
                        command.getLastName(),
                        command.getEmail(),
                        command.getAddress(),
                        command.getUniversity(),
                        command.getSpecialization(),
                        command.getLawyerPrice(),
                        now
                )
        );
    }
}
