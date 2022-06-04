package com.terasoft.userprofile.command.domain.values;

import lombok.Value;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Value(staticConstructor = "of")
public class UserId implements Serializable {
    private static final long serialVersionUID = 6852344466437776829L;
    private UUID value;

    private UserId(UUID value) {
        this.value = value;
    }

    protected UserId() {
        this.value = UUID.randomUUID();
    }
}
