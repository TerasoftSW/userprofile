package com.terasoft.userprofile.command.infrastructure;

import com.terasoft.userprofile.command.domain.entities.User;
import com.terasoft.userprofile.command.domain.values.Email;
import com.terasoft.userprofile.command.domain.values.UserId;
import com.terasoft.userprofile.command.domain.values.UserName;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class UserEmail {
    @Id
    private String email;
    private String userId;

    public UserEmail(){
    }
    public UserEmail(String userId, String email){
        this.userId = userId;
        this.email = email;
    }



    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
