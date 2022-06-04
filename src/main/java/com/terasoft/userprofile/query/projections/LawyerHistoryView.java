package com.terasoft.userprofile.query.projections;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class LawyerHistoryView {
    @Id @GeneratedValue @Getter @Setter
    private Long lawyerHistoryId;
    @Column(length=36) @Getter @Setter
    private String lawyerId;
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
    @Column(length=150) @Getter @Setter
    private String address;
    @Column(length=50) @Getter @Setter
    private String university;
    @Column(length=20) @Getter @Setter
    private String specialization;
    @Column(columnDefinition = "DECIMAL") @Getter @Setter
    private BigDecimal lawyer_price;
    @Getter @Setter
    private Instant createdAt;
    @Column(nullable = true) @Getter @Setter
    private Instant updatedAt;

    public LawyerHistoryView(){}

    public LawyerHistoryView(String customerId, String userName, String firstName, String lastName, String email, String state, String address, String university, BigDecimal lawyer_price, String specialization, Instant createdAt){
        this.lawyerId = customerId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.state = state;
        this.address = address;
        this.university = university;
        this.specialization = specialization;
        this.lawyer_price =lawyer_price;
        this.createdAt = createdAt;
    }

    /*public LawyerHistoryView(LawyerHistoryView lawyerHistoryView){

    }*/

}
