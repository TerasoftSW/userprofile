package com.terasoft.userprofile.command.domain.values;

import com.terasoft.common.application.Notification;
import com.terasoft.common.application.Result;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Value
public class FullName {
    @Column(name = "first_name", length = 75)
    private String firstName;

    @Column(name = "last_name", length = 75)
    private String lastName;

    private final static int MAX_LENGTH = 75;

    private FullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    protected FullName() {
        this.firstName = "";
        this.lastName = "";
    }

    public static Result<FullName, Notification> create(String firstName, String lastName) {
        Notification notification = new Notification();
        firstName = firstName == null ? "" : firstName.trim();
        lastName = lastName == null ? "" : lastName.trim();
        if (firstName.isEmpty()) {
            notification.addError("first name is required", null);
        }
        if (lastName.isEmpty()) {
            notification.addError("last name is required", null);
        }
        if (firstName.length() > MAX_LENGTH) {
            notification.addError("The maximum length of a first name is " + MAX_LENGTH + " characters including spaces", null);
        }
        if (lastName.length() > MAX_LENGTH) {
            notification.addError("The maximum length of a last name is " + MAX_LENGTH + " characters including spaces", null);
        }
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        return Result.success(new FullName(firstName, lastName));
    }
}
