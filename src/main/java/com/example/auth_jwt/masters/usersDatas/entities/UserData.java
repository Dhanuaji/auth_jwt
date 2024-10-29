package com.example.auth_jwt.masters.usersDatas.entities;

import com.example.auth_jwt.masters.usersAccount.entities.UsersAccounts;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "B01")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "fullname", length = 150)
    private String fullname;

    @Column(name = "phone", length = 12)
    private String phone;

    @CreationTimestamp
    private Date created_at;

    @Column(name = "isactive")
    private boolean isactive;

    @OneToOne
    @JoinColumn(name = "email", nullable = false, referencedColumnName = "email")
    private UsersAccounts usersAccounts;
}
