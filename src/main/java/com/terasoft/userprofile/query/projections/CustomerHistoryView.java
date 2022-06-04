package com.terasoft.userprofile.query.projections;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class CustomerHistoryView {
    @Id @GeneratedValue @Getter @Setter
    private Long customerHistoryId;
    @Column(length=36) @Getter
    @Setter
    private String customerId;
    @Column(length=50) @Getter @Setter
    private String userName;
    @Column(length=50) @Getter @Setter
    private String firstName;
    @Column(length=50) @Getter @Setter
    private String lastName;
    @Column(length=150) @Getter @Setter
    private String email;
    @Column(length=20) @Getter @Setter
    private String state;
    @Getter @Setter
    private Instant createdAt;
    @Column(nullable = true) @Getter @Setter
    private Instant updatedAt;


    public CustomerHistoryView() {

    }

    public CustomerHistoryView(String customerId, String userName, String firstName, String lastName, String email, String state, Instant createdAt){
        this.customerId = customerId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.state = state;
        this.createdAt = createdAt;
    }

    public CustomerHistoryView(CustomerHistoryView customerHistoryView) {
        this.customerId =  customerHistoryView.getCustomerId();
        this.userName = customerHistoryView.getUserName();
        this.firstName = customerHistoryView.getFirstName();
        this.lastName = customerHistoryView.getLastName();
        this.email = customerHistoryView.getEmail();
        this.state = customerHistoryView.getState();
        this.createdAt = customerHistoryView.getCreatedAt();
    }
}
