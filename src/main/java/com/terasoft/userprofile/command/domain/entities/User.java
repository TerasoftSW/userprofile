package com.terasoft.userprofile.command.domain.entities;

import com.terasoft.common.domain.enums.UserState;
import com.terasoft.userprofile.command.domain.values.*;
import lombok.Data;
import org.axonframework.modelling.command.AggregateIdentifier;

import javax.persistence.*;

@Entity(name = "User")
@Table(name = "users")
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        discriminatorType = DiscriminatorType.INTEGER,
        name = "user_type_id",
        columnDefinition = "TINYINT(1)"
)
public class User {
    @AggregateIdentifier
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "BINARY(16)"))
    })
    protected UserId id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "username", length = 32, nullable = false))
    })
    protected UserName userName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "password", length = 150, nullable = false))
    })
    protected Password password;

    @Embedded
    protected FullName fullName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "email", length = 150, nullable = false))
    })
    protected Email email;

    @Column(name = "user_state_id", columnDefinition = "TINYINT(1) UNSIGNED", nullable = false)
    protected UserState state;

    @Embedded
    protected AuditTrail auditTrail;

    public User(UserId userId, UserName userName, Password password ,FullName fullName, Email email, UserState userState, AuditTrail auditTrail){
        setFullName(fullName);
        setId(userId);
        setUserName(userName);
        setPassword(password);
        setEmail(email);
        setState(userState);
        setAuditTrail(auditTrail);
    }

    protected User(){
    }

}
