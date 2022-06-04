package com.terasoft.userprofile.query.projections;

import com.terasoft.common.domain.enums.UserState;
import com.terasoft.userprofile.contracts.events.CustomerEdited;
import com.terasoft.userprofile.contracts.events.CustomerRegistered;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class CustomerViewProjection {
    private final CustomerViewRepository customerViewRepository;

    public CustomerViewProjection(CustomerViewRepository customerViewRepository) {
        this.customerViewRepository = customerViewRepository;
    }

    @EventHandler
    public void on(CustomerRegistered event, @Timestamp Instant timestamp){
        CustomerView customerView = new CustomerView(event.getId(), event.getUserName(), event.getFirstName(), event.getLastName(), event.getEmail(), UserState.ACTIVE.toString(), event.getOccurredOn());
        customerViewRepository.save(customerView);
    }

    @EventHandler
    public void on(CustomerEdited event, @Timestamp Instant timestamp){
        Optional<CustomerView> customerViewOptional = customerViewRepository.findById(event.getId().toString());
        if (customerViewOptional.isPresent()){
            CustomerView customerView = customerViewOptional.get();
            customerView.setUserName(event.getUserName());
            customerView.setFirstName(event.getFirstName());
            customerView.setLastName(event.getLastName());
            customerView.setEmail(event.getEmail());
            customerView.setUpdatedAt(event.getOccurredOn());
            customerViewRepository.save(customerView);
        }
    }
}
