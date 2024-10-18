package com.example.auth_jwt.security.controllers;

import com.example.auth_jwt.BaseApiConstants.BaseApiConstants;
import com.example.auth_jwt.security.dtos.AccountsRequestDto;
import com.example.auth_jwt.security.services.JwtService;
import com.example.auth_jwt.security.services.UsersAccountsDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(BaseApiConstants.contextAuth)
@Slf4j
public class AuthController {

    private final UsersAccountsDetailsServiceImpl authServices;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthController(UsersAccountsDetailsServiceImpl authServices,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.authServices = authServices;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping("/welcome")
    public String index() {
        return "WELCOME";
    }

    @GetMapping("/needLogin")
    public String needLogin() {
        return "LOGIN SUCCESS";
    }

    @GetMapping("/owner/login")
    public String needLoginOwner() {
        return "LOGIN SUCCESS AS OWNER";
    }

    @GetMapping("/admin/login")
    public String needLoginAdmin() {
        return "LOGIN SUCCESS AS ADMIN";
    }

    @PostMapping("/addNewUser")
    public void addNewUser(@RequestBody AccountsRequestDto dto) {
        authServices.addAccount(dto);
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AccountsRequestDto dto) {
        log.info("Trying to generate token");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(dto.getEmail());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
