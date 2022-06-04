package com.terasoft.userprofile.command.domain.values;

import com.terasoft.common.application.Notification;
import com.terasoft.common.application.Result;
import lombok.Value;

import javax.persistence.Embeddable;

@Embeddable
@Value
public class Password {
    private String value;

    private final static int MIN_LENGTH = 8;

    private Password(String password) {
        value = password;
    }

    protected Password() {
        this.value = "";
    }

    public static Result<Password, Notification> create(String password) {
        Notification notification = new Notification();
        password = password == null ? "" : password.trim();
        if (password.isEmpty()) {
            notification.addError("password is required", null);
        }
        if (password.length() < MIN_LENGTH) {
            notification.addError("The minimum length of an password is " + MIN_LENGTH + " characters.", null);
        }
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        return Result.success(new Password(password));
    }
}
