package com.terasoft.userprofile.command.application.handlers;

import com.terasoft.common.application.Notification;
import com.terasoft.common.application.Result;
import com.terasoft.common.domain.enums.LawSpecialization;
import com.terasoft.common.domain.enums.UserState;
import com.terasoft.userprofile.command.domain.entities.Lawyer;
import com.terasoft.userprofile.command.domain.values.*;
import com.terasoft.userprofile.command.infrastructure.repositories.LawyerRepository;
import com.terasoft.userprofile.contracts.commands.EditLawyer;
import com.terasoft.userprofile.contracts.commands.RegisterLawyer;
import com.terasoft.userprofile.contracts.events.LawyerEdited;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Component
public class RegisterLawyerHandler {
    private LawyerRepository lawyerRepository;

    public RegisterLawyerHandler(LawyerRepository lawyerRepository){
        this.lawyerRepository = lawyerRepository;
    }

    @CommandHandler
    public Result<Lawyer, Notification> handle(RegisterLawyer command){
        Notification notification = new Notification();
        UserId userId = UserId.of(UUID.fromString(command.getLawyerId()));
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
        Result<Address, Notification> addressResult = Address.create(command.getAddress());
        if(addressResult.isFailure()){
            notification.addError(addressResult.getFailure().errorMessage());
        }
        Result<University, Notification> universityResult = University.create(command.getUniversity());
        if(universityResult.isFailure()){
            notification.addError(universityResult.getFailure().errorMessage());
        }

        Result<AuditTrail, Notification> auditTrailResult = AuditTrail.create(UUID.randomUUID());
        if (auditTrailResult.isFailure()) {
            notification.addError(auditTrailResult.getFailure().errorMessage());
        }

        Result<Password, Notification> passwordResult = Password.create(command.getPassword());
        if(passwordResult.isFailure()){
            notification.addError(passwordResult.getFailure().errorMessage());
        }

        Money lawyerPriceResult = Money.soles(command.getLawyerPrice().doubleValue());

        if(notification.hasErrors()){
            return Result.failure(notification);
        }

        LawSpecialization sp = LawSpecialization.CIVIL_LAW;
        switch (command.getSpecialization()){
            case 0:
                sp = LawSpecialization.CIVIL_LAW;
                break;
            case 1:
                sp = LawSpecialization.PENAL_LAW;
                break;
            case 2:
                sp = LawSpecialization.CORPORATE_LAW;
                break;
            case 3:
                sp = LawSpecialization.COMMERCIAL_LAW;
                break;
        }

        Lawyer lawyer = new Lawyer(
                userId,
                userNameResult.getSuccess(),
                passwordResult.getSuccess(),
                fullNameResult.getSuccess(),
                emailResult.getSuccess(),
                UserState.ACTIVE,
                auditTrailResult.getSuccess(),
                addressResult.getSuccess(),
                universityResult.getSuccess(),
                sp,
                lawyerPriceResult );

        // EVENT
        /*Instant now = Instant.now();
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
        );*/

        lawyerRepository.save(lawyer);
        return Result.success(lawyer);
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
