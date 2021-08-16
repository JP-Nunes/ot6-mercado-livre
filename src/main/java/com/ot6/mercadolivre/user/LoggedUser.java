package com.ot6.mercadolivre.user;

import com.ot6.mercadolivre.user.User;
import io.jsonwebtoken.lang.Assert;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class LoggedUser implements UserDetails {

    private String username;
    private String password;
    private List<Profile> profiles = new ArrayList<Profile>();

    public LoggedUser(String username, String password, List<Profile> profiles) {
        this.username = username;
        this.password = password;
        profiles.forEach(profile -> { this.profiles.add(profile); });
    }

    public static User getLoggedUserFromAuthContext(UserRepository userRepository) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails loggedUser = (UserDetails) auth.getPrincipal();
        String email = loggedUser.getUsername();

        Optional<User> user = userRepository.findByEmail(email);

        Assert.state(user.isPresent(), "Nenhum usuário logado");

        return user.get();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.profiles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
        return true;
    }
}
