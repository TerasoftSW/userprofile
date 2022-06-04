package com.terasoft.userprofile.command.infrastructure.repositories;

import com.terasoft.userprofile.command.domain.entities.Lawyer;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LawyerRepository extends UsersRepository<Lawyer> {
    Optional<Lawyer> findByEmailValue(String email);
    Optional<Lawyer> findByUserNameValue(String userName);
    Optional<Lawyer> findByIdValue(UUID id);
}
