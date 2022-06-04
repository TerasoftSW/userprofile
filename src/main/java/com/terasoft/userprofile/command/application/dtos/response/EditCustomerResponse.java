package com.terasoft.userprofile.command.application.dtos.response;

import lombok.Value;

@Value
public class EditCustomerResponse {
    private String id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
}
