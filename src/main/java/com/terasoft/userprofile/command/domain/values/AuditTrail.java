package com.terasoft.userprofile.command.domain.values;

import com.terasoft.common.application.Notification;
import com.terasoft.common.application.Result;
import lombok.Value;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Embeddable
@Value
public class AuditTrail {
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "created_by"))
    })
    private UserId createdBy;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "updated_by"))
    })
    private UserId updatedBy;

    private AuditTrail(LocalDateTime createdAt, LocalDateTime updatedAt, UserId createdBy, UserId updatedBy) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    protected AuditTrail() {
        this.createdAt = null;
        this.updatedAt = null;
        this.createdBy = null;
        this.updatedBy = null;
    }

    public static Result<AuditTrail, Notification> create(UUID createdBy) {
        /*Notification notification = new Notification();
        //UserId createdBy = UserId.of(createdBy);
        //address = address == null ? "" : address.trim();
        if (address.isEmpty()) {
            notification.addError("address is required", null);
            return Result.failure(notification);
        }
        if (address.length() > MAX_LENGTH) {
            notification.addError("The maximum length of an address is " + MAX_LENGTH + " characters including spaces", null);
        }
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }*/
        return Result.success(new AuditTrail(LocalDateTime.now(ZoneOffset.UTC), null, UserId.of(createdBy), null));
    }
}