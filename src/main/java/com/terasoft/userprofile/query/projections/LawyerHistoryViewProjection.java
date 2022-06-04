package com.terasoft.userprofile.query.projections;

import com.terasoft.common.domain.enums.UserState;
import com.terasoft.userprofile.contracts.events.LawyerEdited;
import com.terasoft.userprofile.contracts.events.LawyerRegistered;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class LawyerHistoryViewProjection {
    private final LawyerHistoryViewRepository lawyerHistoryViewRepository;

    public LawyerHistoryViewProjection(LawyerHistoryViewRepository lawyerHistoryViewRepository) {
        this.lawyerHistoryViewRepository = lawyerHistoryViewRepository;
    }

    @EventHandler
    public void on(LawyerRegistered event, @Timestamp Instant timestamp){
        String sp = "";
        switch (event.getSpecialization()){
            case 0:
                sp = "CIVIL LAW";
                break;
            case 1:
                sp = "PENAL LAW";
                break;
            case 2:
                sp = "CORPORATE LAW";
                break;
            case 3:
                sp = "COMMERCIAL LAW";
                break;
        }
        LawyerHistoryView lawyerHistoryView = new LawyerHistoryView(event.getId(),event.getUserName(), event.getFirstName(),
                event.getLastName(), event.getEmail(), UserState.ACTIVE.toString(),event.getAddress(),
                event.getUniversity(), event.getLawyerPrice(), sp, event.getOccurredOn());
        lawyerHistoryViewRepository.save(lawyerHistoryView);
    }

    @EventHandler
    public void on(LawyerEdited event, @Timestamp Instant timestamp) {
        String sp = "";
        switch (event.getSpecialization()) {
            case 0:
                sp = "CIVIL LAW";
                break;
            case 1:
                sp = "PENAL LAW";
                break;
            case 2:
                sp = "CORPORATE LAW";
                break;
            case 3:
                sp = "COMMERCIAL LAW";
                break;
        }

        Optional<LawyerHistoryView> lawyerHistoryViewOptional = lawyerHistoryViewRepository.getLastByLawyerId(event.getId().toString());
        if (lawyerHistoryViewOptional.isPresent()) {
            LawyerHistoryView lawyerHistoryView = lawyerHistoryViewOptional.get();
            lawyerHistoryView.setUserName(event.getUserName());
            lawyerHistoryView.setFirstName(event.getFirstName());
            lawyerHistoryView.setLastName(event.getLastName());
            lawyerHistoryView.setEmail(event.getEmail());
            lawyerHistoryView.setAddress(event.getAddress());
            lawyerHistoryView.setUniversity(event.getUniversity());
            lawyerHistoryView.setLawyer_price(event.getLawyerPrice());
            lawyerHistoryView.setSpecialization(sp);
            lawyerHistoryViewRepository.save(lawyerHistoryView);
        }
    }
}
