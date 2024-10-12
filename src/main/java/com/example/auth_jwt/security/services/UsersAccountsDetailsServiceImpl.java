package com.example.auth_jwt.security.services;

import com.example.auth_jwt.security.UsersAccountsDetails.UsersAccountsDetails;
import com.example.auth_jwt.security.dtos.AccountsRequestDto;
import com.example.auth_jwt.usersAccount.entities.UsersAccounts;
import com.example.auth_jwt.usersAccount.servicesInterfaces.IUsersAccountsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UsersAccountsDetailsServiceImpl implements UserDetailsService {

    private final IUsersAccountsService accountsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersAccountsDetailsServiceImpl(IUsersAccountsService accountsService,
                                           PasswordEncoder passwordEncoder) {
        this.accountsService = accountsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Trying to find account with email {}", email);
        Optional<UsersAccounts> account = accountsService.findByEmail(email);
        return account.map(usersAccounts -> new UsersAccountsDetails(usersAccounts.getEmail(),
                        usersAccounts.getPassword(),
                        usersAccounts.isIsactive()))
                .orElseThrow(() -> new UsernameNotFoundException("Account with email "+email+"is not found"));
    }

    public void addAccount(AccountsRequestDto dto) {
        log.info("Trying to add account with email {}", dto.getEmail());
        try {
            UsersAccounts entity = new UsersAccounts();
            entity.setEmail(dto.getEmail());
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
            entity.setIsactive(true);
            accountsService.saveUserAccount(entity);
            log.info("User added with email {}", dto.getEmail());
        } catch (Exception e) {
            throw new RuntimeException("Failed to add new user");
        }
    }
}
