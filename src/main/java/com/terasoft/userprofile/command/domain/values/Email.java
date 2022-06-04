package com.terasoft.userprofile.command.domain.values;

import com.terasoft.common.application.Notification;
import com.terasoft.common.application.Result;
import lombok.Value;

import javax.persistence.Embeddable;

@Embeddable
@Value
public class Email {
    private String value;

    private final static int MAX_LENGTH = 150;

    private Email(String email) {
        value = email;
    }

    protected Email() {
        this.value = "";
    }

    public static Result<Email, Notification> create(String email) {
        Notification notification = new Notification();
        email = email == null ? "" : email.trim();
        if (email.isEmpty()) {
            notification.addError("email is required", null);
        }
        if (email.length() > MAX_LENGTH) {
            notification.addError("The maximum length of an email is " + MAX_LENGTH + " characters including spaces", null);
        }
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        return Result.success(new Email(email));
    }
}
