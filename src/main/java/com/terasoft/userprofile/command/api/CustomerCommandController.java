package com.terasoft.userprofile.command.api;

import com.terasoft.common.api.ApiController;
import com.terasoft.common.application.Notification;
import com.terasoft.common.application.Result;
import com.terasoft.userprofile.command.application.dtos.request.EditCustomerRequest;
import com.terasoft.userprofile.command.application.dtos.request.RegisterCustomerRequest;
import com.terasoft.userprofile.command.application.dtos.response.EditCustomerResponse;
import com.terasoft.userprofile.command.application.dtos.response.RegisterCustomerResponse;
import com.terasoft.userprofile.command.application.services.CustomerApplicationService;
import com.terasoft.userprofile.command.infrastructure.repositories.UserEmailRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/customers")
//@Tag(name = "Customers")
public class CustomerCommandController {
    private final CustomerApplicationService customerApplicationService;
    private final CommandGateway commandGateway;
    private final UserEmailRepository userEmailRepository;

    public CustomerCommandController(CustomerApplicationService customerApplicationService, CommandGateway commandGateway, UserEmailRepository userEmailRepository) {
        this.customerApplicationService = customerApplicationService;
        this.commandGateway = commandGateway;
        this.userEmailRepository = userEmailRepository;
    }

    @PostMapping(path= "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register(@RequestBody RegisterCustomerRequest registerCustomerRequest){
        try {
            Result<RegisterCustomerResponse, Notification> result = customerApplicationService.register(registerCustomerRequest);
            if (result.isSuccess()) {
                return ApiController.created(result.getSuccess());
            }
            return ApiController.error(result.getFailure().getErrors());
        } catch (Exception e) {
            return  ApiController.serverError();
        }
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Object> edit(@PathVariable("customerId") String customerId, @RequestBody EditCustomerRequest editCustomerRequest) {
        try {
            editCustomerRequest.setId(customerId);
            Result<EditCustomerResponse, Notification> result = customerApplicationService.edit(editCustomerRequest);
            if (result.isSuccess()) {
                return ApiController.ok(result.getSuccess());
            }
            return ApiController.error(result.getFailure().getErrors());
        } catch (AggregateNotFoundException exception) {
            return ApiController.notFound();
        } catch(Exception e) {
            return ApiController.serverError();
        }
    }
}
