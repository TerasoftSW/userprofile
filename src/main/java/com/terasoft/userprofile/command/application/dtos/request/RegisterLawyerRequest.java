package com.terasoft.userprofile.command.application.dtos.request;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class RegisterLawyerRequest {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String university;
    private int specialization;
    private BigDecimal lawyerPrice;
}
