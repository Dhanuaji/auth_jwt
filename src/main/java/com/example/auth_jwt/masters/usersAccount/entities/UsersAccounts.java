package com.example.auth_jwt.masters.usersAccount.entities;

import com.example.auth_jwt.masters.usersDatas.entities.UserData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "A01")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersAccounts {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email", unique = true, length = 150)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role", length = 10)
    private String role;

    @CreationTimestamp
    private Date created_at;

    @Column(name = "isactive")
    private boolean isactive;

    @OneToOne(mappedBy = "usersAccounts", cascade = CascadeType.REMOVE)
    private UserData usersData;
}
