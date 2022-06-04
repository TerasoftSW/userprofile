package com.terasoft.userprofile.query.api;

import com.terasoft.userprofile.query.projections.CustomerHistoryView;
import com.terasoft.userprofile.query.projections.CustomerHistoryViewRepository;
import com.terasoft.userprofile.query.projections.CustomerView;
import com.terasoft.userprofile.query.projections.CustomerViewRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customers")
public class CustomerQueryController {
    private final CustomerViewRepository customerViewRepository;
    private final CustomerHistoryViewRepository customerHistoryViewRepository;

    public CustomerQueryController(CustomerViewRepository customerViewRepository, CustomerHistoryViewRepository customerHistoryViewRepository) {
        this.customerViewRepository = customerViewRepository;
        this.customerHistoryViewRepository = customerHistoryViewRepository;
    }

    @GetMapping("")
    @Operation(summary = "Get all customers")
    public ResponseEntity<List<CustomerView>> getAll(){
        try{
            return new ResponseEntity<List<CustomerView>>(customerViewRepository.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Get customer by id")
    public ResponseEntity<CustomerView> getById(@PathVariable("id") String id){
        try {
            Optional<CustomerView> customerViewOptional = customerViewRepository.findById(id);
            if(customerViewOptional.isPresent()){
                return new ResponseEntity<CustomerView>(customerViewOptional.get(), HttpStatus.OK);
            }
            return new ResponseEntity("NOT_FOUND", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/history/{id}")
    @Operation(summary = "Get customer history")
    public ResponseEntity<List<CustomerHistoryView>> getHistoryById(@PathVariable("id") String id){
        try {
            List<CustomerHistoryView> customers = customerHistoryViewRepository.getHistoryByCustomerId(id);
            return new ResponseEntity<List<CustomerHistoryView>>(customers, HttpStatus.OK);
        } catch( Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
