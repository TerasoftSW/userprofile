package com.terasoft.userprofile.command.application.dtos.request;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

public class EditCustomerRequest {
    private @Setter @Getter String id;
    private @Getter String userName;
    private @Getter String password;
    private @Getter String firstName;
    private @Getter String lastName;
    private @Getter String email;
}
