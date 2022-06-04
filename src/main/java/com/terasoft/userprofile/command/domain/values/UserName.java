package com.terasoft.userprofile.command.domain.values;

import com.terasoft.common.application.Notification;
import com.terasoft.common.application.Result;
import lombok.Value;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Value
public class UserName {
    private String value;

    private final static int MAX_LENGTH = 32;

    private UserName(String username) {
        value = username;
    }

    protected UserName() {
        this.value = "";
    }

    public static Result<UserName, Notification> create(String username) {
        Notification notification = new Notification();
        username = username == null ? "" : username.trim();
        if (username.isEmpty()) {
            notification.addError("username is required", null);
        }
        if (username.length() > MAX_LENGTH) {
            notification.addError("The maximum length of an username is " + MAX_LENGTH + " characters including spaces", null);
        }
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        return Result.success(new UserName(username));
    }
}
