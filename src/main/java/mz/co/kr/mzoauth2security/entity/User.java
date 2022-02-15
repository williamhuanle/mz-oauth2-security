package mz.co.kr.mzoauth2security.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import mz.co.kr.mzoauth2security.enums.OAuth2Provider;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Email
    @Column(nullable = false)
    private String email;
    private String imageUrl;
    @Column(nullable = false)
    private Boolean emailVerified = false;
    @JsonIgnore
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;
    private String providerId;

}
