package com.terasoft.userprofile.command.infrastructure.repositories;

import com.terasoft.userprofile.command.domain.values.Email;
import com.terasoft.userprofile.command.domain.values.UserId;
import com.terasoft.userprofile.command.infrastructure.UserEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserEmailRepository extends JpaRepository<UserEmail, String> {
    Optional<UserEmail> getEmailByUserId(String userId);

      //@Query(value = "SELECT * FROM user_email WHERE user_id <> : email AND userId = :email LIMIT 1", nativeQuery = true)
    //Optional<UserEmail> getByEmailForDistinctUserId(String email, String userId);
}
