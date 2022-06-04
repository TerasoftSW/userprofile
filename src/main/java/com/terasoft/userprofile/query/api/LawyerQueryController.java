package com.terasoft.userprofile.query.api;

import com.terasoft.userprofile.query.projections.LawyerHistoryView;
import com.terasoft.userprofile.query.projections.LawyerHistoryViewRepository;
import com.terasoft.userprofile.query.projections.LawyerView;
import com.terasoft.userprofile.query.projections.LawyerViewRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lawyers")
@Tag(name= "Lawyers")
public class LawyerQueryController {
    private final LawyerViewRepository lawyerViewRepository;
    private final LawyerHistoryViewRepository lawyerHistoryViewRepository;

    public LawyerQueryController(LawyerViewRepository lawyerViewRepository, LawyerHistoryViewRepository lawyerHistoryViewRepository) {
        this.lawyerViewRepository = lawyerViewRepository;
        this.lawyerHistoryViewRepository = lawyerHistoryViewRepository;
    }

    @GetMapping("")
    @Operation(summary = "Get all lawyers")
    public ResponseEntity<List<LawyerView>> getAll(){
        try {
            return new ResponseEntity<List<LawyerView>>(lawyerViewRepository.findAll(), HttpStatus.OK);
        } catch( Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get lawyer by id")
    public ResponseEntity<LawyerView> getById(@PathVariable("id") String id) {
        try {
            Optional<LawyerView> lawyerViewOptional = lawyerViewRepository.findById(id);
            if (lawyerViewOptional.isPresent()) {
                return new ResponseEntity<LawyerView>(lawyerViewOptional.get(), HttpStatus.OK);
            }
            return new ResponseEntity("NOT_FOUND", HttpStatus.NOT_FOUND);
        } catch( Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping("/history/{id}")
    @Operation(summary = "Get lawyer history")
    public ResponseEntity<List<LawyerHistoryView>> getHistoryById(@PathVariable("id") String id) {
        try {
            List<LawyerHistoryView> lawyers = lawyerHistoryViewRepository.getHistoryByLawyerId(id);
            return new ResponseEntity<List<LawyerHistoryView>>(lawyers, HttpStatus.OK);
        } catch( Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
