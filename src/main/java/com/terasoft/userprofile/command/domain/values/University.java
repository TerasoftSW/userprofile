package com.terasoft.userprofile.command.domain.values;

import com.terasoft.common.application.Notification;
import com.terasoft.common.application.Result;
import lombok.Value;

import javax.persistence.Embeddable;

@Embeddable
@Value
public class University {
    private String value;

    private final static int MAX_LENGTH = 20;

    private University(String university) {
        value = university;
    }

    protected University() {
        this.value = "";
    }

    public static Result<University, Notification> create(String university) {
        Notification notification = new Notification();
        university = university == null ? "" : university.trim();
        if (university.isEmpty()) {
            notification.addError("university is required", null);
        }
        if (university.length() > MAX_LENGTH) {
            notification.addError("The maximum length of an university is " + MAX_LENGTH + " characters including spaces", null);
        }
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        return Result.success(new University(university));
    }
}
