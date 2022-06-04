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
public class CustomerHistoryViewProjection {
    private final CustomerHistoryViewRepository customerHistoryViewRepository;

    public CustomerHistoryViewProjection(CustomerHistoryViewRepository customerHistoryViewRepository) {
        this.customerHistoryViewRepository = customerHistoryViewRepository;
    }

    @EventHandler
    public void on(CustomerRegistered event, @Timestamp Instant timestamp){
        CustomerHistoryView customerHistoryView = new CustomerHistoryView(event.getId(), event.getUserName(), event.getFirstName(), event.getLastName(), event.getEmail(), UserState.ACTIVE.toString(), event.getOccurredOn());
        customerHistoryViewRepository.save(customerHistoryView);

    }

    @EventHandler
    public void on(CustomerEdited event, @Timestamp Instant timestamp){
        Optional<CustomerHistoryView> customerHistoryViewOptional = customerHistoryViewRepository.getLastByCustomerId(event.getId().toString());
        if (customerHistoryViewOptional.isPresent()){
            CustomerHistoryView customerHistoryView = customerHistoryViewOptional.get();
            customerHistoryView.setUserName(event.getUserName());
            customerHistoryView.setFirstName(event.getFirstName());
            customerHistoryView.setLastName(event.getLastName());
            customerHistoryView.setEmail(event.getEmail());
            customerHistoryView.setUpdatedAt(event.getOccurredOn());
            customerHistoryViewRepository.save(customerHistoryView);
        }
    }
}
