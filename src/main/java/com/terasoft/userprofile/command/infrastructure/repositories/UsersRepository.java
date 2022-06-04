package com.terasoft.userprofile.command.infrastructure.repositories;

import com.terasoft.userprofile.command.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersRepository<T extends User> extends JpaRepository<T, UUID> {
}
