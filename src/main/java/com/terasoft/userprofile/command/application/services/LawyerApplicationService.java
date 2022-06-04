package com.terasoft.userprofile.command.application.services;

import com.terasoft.userprofile.command.application.dtos.request.EditLawyerRequest;
import com.terasoft.userprofile.command.application.dtos.request.RegisterLawyerRequest;
import com.terasoft.userprofile.command.application.dtos.response.EditLawyerResponse;
import com.terasoft.userprofile.command.application.dtos.response.RegisterLawyerResponse;
import com.terasoft.common.application.Notification;
import com.terasoft.common.application.Result;
import com.terasoft.common.application.ResultType;
import com.terasoft.userprofile.command.application.validators.EditLawyerValidator;
import com.terasoft.userprofile.command.application.validators.RegisterLawyerValidator;
import com.terasoft.userprofile.contracts.commands.EditLawyer;
import com.terasoft.userprofile.contracts.commands.RegisterLawyer;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class LawyerApplicationService {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final RegisterLawyerValidator registerLawyerValidator;
    private final EditLawyerValidator editLawyerValidator;

    public LawyerApplicationService(CommandGateway commandGateway, QueryGateway queryGateway, RegisterLawyerValidator registerLawyerValidator, EditLawyerValidator editLawyerValidator) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
        this.registerLawyerValidator = registerLawyerValidator;
        this.editLawyerValidator = editLawyerValidator;
    }
    
    public Result<RegisterLawyerResponse, Notification> register(RegisterLawyerRequest registerLawyerRequest) throws Exception {
        Notification notification = this.registerLawyerValidator.validate(registerLawyerRequest);
        if(notification.hasErrors()){
            return Result.failure(notification);
        }
        String lawyerId = UUID.randomUUID().toString();
        RegisterLawyer registerLawyer = new RegisterLawyer(
                lawyerId,
                registerLawyerRequest.getUserName().trim(),
                registerLawyerRequest.getPassword().trim(),
                registerLawyerRequest.getFirstName().trim(),
                registerLawyerRequest.getLastName().trim(),
                registerLawyerRequest.getEmail().trim(),
                registerLawyerRequest.getAddress().trim(),
                registerLawyerRequest.getUniversity().trim(),
                registerLawyerRequest.getSpecialization(),
                registerLawyerRequest.getLawyerPrice()
        );

        CompletableFuture<Object> future = commandGateway.send(registerLawyer);
        CompletableFuture<ResultType> futureResult = future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if(resultType == ResultType.FAILURE){
            throw new Exception();
        }
        RegisterLawyerResponse registerLawyerResponse = new RegisterLawyerResponse(
                registerLawyer.getLawyerId(),
                registerLawyer.getUserName(),
                registerLawyer.getFirstName(),
                registerLawyer.getLastName(),
                registerLawyer.getEmail(),
                registerLawyer.getAddress(),
                registerLawyer.getUniversity(),
                registerLawyer.getSpecialization(),
                registerLawyer.getLawyerPrice()
        );
        return Result.success(registerLawyerResponse);
    }

    public Result<EditLawyerResponse, Notification> edit (EditLawyerRequest editLawyerRequest) throws Exception{
        Notification notification = this.editLawyerValidator.validate(editLawyerRequest);
        if (notification.hasErrors()){
            return Result.failure(notification);
        }
        EditLawyer editLawyer = new EditLawyer(
                editLawyerRequest.getId().trim(),
                editLawyerRequest.getUserName().trim(),
                editLawyerRequest.getPassword().trim(),
                editLawyerRequest.getFirstName().trim(),
                editLawyerRequest.getLastName().trim(),
                editLawyerRequest.getEmail().trim(),
                editLawyerRequest.getAddress().trim(),
                editLawyerRequest.getUniversity().trim(),
                editLawyerRequest.getSpecialization(),
                editLawyerRequest.getLawyerPrice()
        );
        CompletableFuture<Object> future = commandGateway.send(editLawyer);
        CompletableFuture<ResultType> futureResult = future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if(resultType == ResultType.FAILURE){
            throw new Exception();
        }
        EditLawyerResponse editLawyerResponse = new EditLawyerResponse(
                editLawyer.getLawyerId().trim(),
                editLawyer.getUserName().trim(),
                editLawyer.getFirstName().trim(),
                editLawyer.getLastName().trim(),
                editLawyer.getEmail().trim(),
                editLawyer.getAddress().trim(),
                editLawyer.getUniversity().trim(),
                editLawyer.getSpecialization(),
                editLawyer.getLawyerPrice()
        );
        return Result.success(editLawyerResponse);
    }
}
