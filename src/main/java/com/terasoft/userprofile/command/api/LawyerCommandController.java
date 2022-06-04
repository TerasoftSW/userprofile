package com.terasoft.userprofile.command.api;

import com.terasoft.common.api.ApiController;
import com.terasoft.userprofile.command.application.dtos.request.EditLawyerRequest;
import com.terasoft.userprofile.command.application.dtos.request.RegisterLawyerRequest;
import com.terasoft.userprofile.command.application.dtos.response.EditLawyerResponse;
import com.terasoft.userprofile.command.application.dtos.response.RegisterLawyerResponse;
import com.terasoft.userprofile.command.application.services.LawyerApplicationService;
import com.terasoft.userprofile.command.infrastructure.repositories.UserEmailRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.terasoft.common.application.Notification;
import com.terasoft.common.application.Result;

@RestController
@RequestMapping("/lawyers")
@Tag(name = "Lawyers")
public class LawyerCommandController {
    private final LawyerApplicationService lawyerApplicationService;
    private final CommandGateway commandGateway;
    private final UserEmailRepository userEmailRepository;

    public LawyerCommandController(LawyerApplicationService lawyerApplicationService, CommandGateway commandGateway, UserEmailRepository userEmailRepository) {
        this.lawyerApplicationService = lawyerApplicationService;
        this.commandGateway = commandGateway;
        this.userEmailRepository = userEmailRepository;
    }
    
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerLawyer(@RequestBody RegisterLawyerRequest registerLawyerRequest){
        try {
            Result<RegisterLawyerResponse, Notification> result = lawyerApplicationService.register(registerLawyerRequest);
            if(result.isSuccess()){
                return ApiController.created(result.getSuccess());
            }
            return ApiController.error(result.getFailure().getErrors());
        } catch (Exception e) {
            return ApiController.serverError();
        }
    }

    @PutMapping("/{lawyerId}")
    public ResponseEntity<Object> edit(@PathVariable("lawyerId") String lawyerId, @RequestBody EditLawyerRequest editLawyerRequest) {
        try {
            editLawyerRequest.setId(lawyerId);
            Result<EditLawyerResponse, Notification> result = lawyerApplicationService.edit(editLawyerRequest);
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
