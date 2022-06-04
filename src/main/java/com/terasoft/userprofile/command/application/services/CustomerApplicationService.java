package com.terasoft.userprofile.command.application.services;

import com.terasoft.userprofile.command.application.dtos.request.EditCustomerRequest;
import com.terasoft.userprofile.command.application.dtos.request.RegisterCustomerRequest;
import com.terasoft.userprofile.command.application.dtos.response.EditCustomerResponse;
import com.terasoft.userprofile.command.application.dtos.response.RegisterCustomerResponse;
import com.terasoft.common.application.Notification;
import com.terasoft.common.application.Result;
import com.terasoft.common.application.ResultType;
import com.terasoft.userprofile.command.application.validators.EditCustomerValidator;
import com.terasoft.userprofile.command.application.validators.RegisterCustomerValidator;
import com.terasoft.userprofile.contracts.commands.EditCustomer;
import com.terasoft.userprofile.contracts.commands.RegisterCustomer;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class CustomerApplicationService {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final RegisterCustomerValidator registerCustomerValidator;
    private final EditCustomerValidator editCustomerValidator;


    public CustomerApplicationService(CommandGateway commandGateway, QueryGateway queryGateway, RegisterCustomerValidator registerCustomerValidator, EditCustomerValidator editCustomerValidator) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
        this.registerCustomerValidator = registerCustomerValidator;
        this.editCustomerValidator = editCustomerValidator;
    }

    public Result<RegisterCustomerResponse, Notification> register(RegisterCustomerRequest registerCustomerRequest) throws Exception{
        Notification notification = this.registerCustomerValidator.validate(registerCustomerRequest);
        if(notification.hasErrors()){
            return Result.failure(notification);
        }
        String customerId = UUID.randomUUID().toString();
        RegisterCustomer registerCustomer = new RegisterCustomer(
                customerId,
                registerCustomerRequest.getUserName().trim(),
                registerCustomerRequest.getPassword().trim(),
                registerCustomerRequest.getFirstName().trim(),
                registerCustomerRequest.getLastName().trim(),
                registerCustomerRequest.getEmail().trim()
        );
        CompletableFuture<Object> future = commandGateway.send(registerCustomer);
        CompletableFuture<ResultType> futureResult =  future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if(resultType == ResultType.FAILURE) {
            throw new Exception();
        }
        RegisterCustomerResponse registerCustomerResponse = new RegisterCustomerResponse(
          registerCustomer.getCustomerId(),
          registerCustomer.getUserName(),
          registerCustomer.getFirstName(),
          registerCustomer.getLastName(),
          registerCustomer.getEmail()
        );
        return Result.success(registerCustomerResponse);
    }

    public Result<EditCustomerResponse, Notification> edit(EditCustomerRequest editCustomerRequest) throws Exception{
        Notification notification = this.editCustomerValidator.validate(editCustomerRequest);
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        EditCustomer editCustomer = new EditCustomer(
                editCustomerRequest.getId().trim(),
                editCustomerRequest.getUserName().trim(),
                editCustomerRequest.getPassword().trim(),
                editCustomerRequest.getFirstName().trim(),
                editCustomerRequest.getLastName().trim(),
                editCustomerRequest.getEmail().trim()
        );
        CompletableFuture<Object> future = commandGateway.send(editCustomer);
        CompletableFuture<ResultType> futureResult = future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if (resultType == ResultType.FAILURE) {
            throw new Exception();
        }
        EditCustomerResponse editCustomerResponse = new EditCustomerResponse(
                editCustomer.getCustomerId().trim(),
                editCustomer.getUserName().trim(),
                editCustomer.getFirstName().trim(),
                editCustomer.getLastName().trim(),
                editCustomer.getEmail().trim()
        );
        return Result.success(editCustomerResponse);
    }
}
