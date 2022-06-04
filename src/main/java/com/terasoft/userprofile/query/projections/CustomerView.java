package com.terasoft.userprofile.query.projections;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class CustomerView {
    @Id @Column(length=36) @Getter @Setter
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

    public CustomerView(String customerId, String userName, String firstName, String lastName, String email, String state, Instant createdAt){
        this.customerId = customerId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.state = state;
        this.createdAt = createdAt;
    }

    public CustomerView() {

    }
}
