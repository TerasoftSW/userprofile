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
public class LawyerViewProjection {
    private final LawyerViewRepository lawyerViewRepository;

    public LawyerViewProjection(LawyerViewRepository lawyerViewRepository) {
        this.lawyerViewRepository = lawyerViewRepository;
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
        LawyerView lawyerView = new LawyerView(event.getId(),event.getUserName(), event.getFirstName(),
                event.getLastName(), event.getEmail(), UserState.ACTIVE.toString(),event.getAddress(),
                event.getUniversity(), event.getLawyerPrice(), sp, event.getOccurredOn());
        lawyerViewRepository.save(lawyerView);
    }

    @EventHandler
    public void on(LawyerEdited event, @Timestamp Instant timestamp){
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

        Optional<LawyerView> lawyerViewOptional = lawyerViewRepository.findById(event.getId().toString());
        if (lawyerViewOptional.isPresent()){
            LawyerView lawyerView = lawyerViewOptional.get();
            lawyerView.setUserName(event.getUserName());
            lawyerView.setFirstName(event.getFirstName());
            lawyerView.setLastName(event.getLastName());
            lawyerView.setEmail(event.getEmail());
            lawyerView.setAddress(event.getAddress());
            lawyerView.setUniversity(event.getUniversity());
            lawyerView.setLawyer_price(event.getLawyerPrice());
            lawyerView.setSpecialization(sp);
            lawyerViewRepository.save(lawyerView);
        }
    }
}
