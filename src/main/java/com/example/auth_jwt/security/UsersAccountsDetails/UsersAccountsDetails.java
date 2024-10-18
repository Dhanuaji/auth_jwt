package com.example.auth_jwt.security.UsersAccountsDetails;

import com.example.auth_jwt.usersAccount.entities.UsersAccounts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UsersAccountsDetails implements UserDetails {

    private final String email;
    private final String password;
    private final boolean isactive;
    private final List<GrantedAuthority> authorities;

    public UsersAccountsDetails(UsersAccounts account) {
        this.email = account.getEmail();
        this.password = account.getPassword();
        this.isactive = account.isIsactive();
        this.authorities = Stream.of(account.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isactive;
    }
}
