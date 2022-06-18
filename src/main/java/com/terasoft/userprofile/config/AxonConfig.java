package com.terasoft.userprofile.config;

import com.terasoft.userprofile.command.domain.entities.Customer;
import com.terasoft.userprofile.command.domain.entities.Lawyer;
import com.thoughtworks.xstream.XStream;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.spring.config.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.axonframework.modelling.command.Repository;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AxonConfig {

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();

        xStream.allowTypesByWildcard(new String[] {
                "com.terasoft.userprofile.contracts.**"
        });
        return xStream;
    }
    @Bean
    public CommandBus commandBus(PlatformTransactionManager platformTransactionManager) {
        return SimpleCommandBus.builder()
                .transactionManager(new SpringTransactionManager(platformTransactionManager))
                .build();
    }

    @Bean
    public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
        return new AnnotationCommandHandlerBeanPostProcessor();
    }

    @Bean
    public EventBus eventBus() {
        return SimpleEventBus.builder().build();
    }

    @Bean
    public Repository<Customer> eventSourcingCustomerRepository(EventStore eventStore){
        return EventSourcingRepository.builder(Customer.class)
                .eventStore(eventStore)
                .build();
    }

    @Bean
    public Repository<Lawyer> eventSourcingLawyerRepository(EventStore eventStore){
        return EventSourcingRepository.builder(Lawyer.class)
                .eventStore(eventStore)
                .build();
    }
}
