package com.ot6.mercadolivre.user;

import com.ot6.mercadolivre.user.dtos.NewUserResponse;
import com.ot6.mercadolivre.user.dtos.UserDetailsResponse;
import com.ot6.mercadolivre.user.helpers.CleanPassword;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotNull
    private LocalDateTime creationInstant = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Profile> profiles = new ArrayList<>();

    @Deprecated
    public User() {}

    public User(String email, CleanPassword cleanPassword) {
        this.email = email;
        this.password = cleanPassword.encode();
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return this.id;
    }

    public NewUserResponse toNewUserResponse() {
        return new NewUserResponse(this.id, this.email);
    }

    public UserDetails toLoggedUser() {
        return new LoggedUser(this.email, this.password, this.profiles);
    }

    public UserDetailsResponse toUserDetailsResponse() {
        return new UserDetailsResponse(this.id, this.email);
    }
}
