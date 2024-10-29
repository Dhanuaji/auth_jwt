package com.example.auth_jwt.masters.usersAccount.servicesInterfaces;

import com.example.auth_jwt.masters.usersAccount.entities.UsersAccounts;

import java.util.Optional;

public interface IUsersAccountsService {
    Optional<UsersAccounts> findByEmail(String email);
    void saveUserAccount(UsersAccounts entity);
}
