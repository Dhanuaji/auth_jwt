package com.example.auth_jwt.masters.usersAccount.services;

import com.example.auth_jwt.masters.usersAccount.entities.UsersAccounts;
import com.example.auth_jwt.masters.usersAccount.repositories.UsersAccountsRepository;
import com.example.auth_jwt.masters.usersAccount.servicesInterfaces.IUsersAccountsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UsersAccountsService implements IUsersAccountsService {

    private final UsersAccountsRepository usersAccountsRepository;

    @Autowired
    public UsersAccountsService (UsersAccountsRepository usersAccountsRepository) {
        this.usersAccountsRepository = usersAccountsRepository;
    }

    @Override
    public Optional<UsersAccounts>  findByEmail(String email) {
        log.info("Trying to find user by email: {}", email);
        Optional<UsersAccounts> account = usersAccountsRepository.findByEmail(email);
        if (account.isPresent()) {
            log.info("Found user by email: {}", email);
        } else {
            log.info("User not found by email: {}", email);
        }
        return account;
    }

    @Override
    public void saveUserAccount(UsersAccounts entity) {
        usersAccountsRepository.save(entity);
    }

}
