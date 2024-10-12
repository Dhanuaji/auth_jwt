package com.example.auth_jwt.usersAccount.repositories;

import com.example.auth_jwt.usersAccount.entities.UsersAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersAccountsRepository extends JpaRepository<UsersAccounts, UUID> {
    Optional<UsersAccounts> findByEmail(String email);
}
