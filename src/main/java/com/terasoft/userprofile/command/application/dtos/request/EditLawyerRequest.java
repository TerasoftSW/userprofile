package com.terasoft.userprofile.command.application.dtos.request;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.math.BigDecimal;

public class EditLawyerRequest {
    private @Getter @Setter String id;
    private @Getter String userName;
    private @Getter String password;
    private @Getter String firstName;
    private @Getter String lastName;
    private @Getter String email;
    private @Getter String address;
    private @Getter String university;
    private @Getter int specialization;
    private @Getter BigDecimal lawyerPrice;
}
