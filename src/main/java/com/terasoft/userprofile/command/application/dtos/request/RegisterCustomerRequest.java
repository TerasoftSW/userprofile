package com.terasoft.userprofile.command.application.dtos.request;

import lombok.Value;

@Value
public class RegisterCustomerRequest {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
}
