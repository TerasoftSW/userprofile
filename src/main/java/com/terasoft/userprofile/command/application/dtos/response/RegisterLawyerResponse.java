package com.terasoft.userprofile.command.application.dtos.response;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class RegisterLawyerResponse {
    private String id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String university;
    private int specialization;
    private BigDecimal lawyerPrice;
}
