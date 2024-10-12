package com.example.auth_jwt.usersAccount.servicesInterfaces;

import com.example.auth_jwt.usersAccount.entities.UsersAccounts;

import java.util.Optional;

public interface IUsersAccountsService {
    Optional<UsersAccounts> findByEmail(String email);
    void saveUserAccount(UsersAccounts entity);
}
