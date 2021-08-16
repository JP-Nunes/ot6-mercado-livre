package com.ot6.mercadolivre.config.security;

import com.ot6.mercadolivre.user.LoggedUser;
import com.ot6.mercadolivre.user.User;
import com.ot6.mercadolivre.user.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationByTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UserRepository userRepository;

    public AuthenticationByTokenFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filter
    ) throws ServletException, IOException {
        String token = retrieveToken(request);

        if (tokenService.isValid(token)) {
            authenticateUser(token);
        }

        filter.doFilter(request, response);
    }

    private String retrieveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7);
    }

    private void authenticateUser(String token) {
        String email = tokenService.getUserEmailFrom(token);
        User user = userRepository.findByEmail(email).get();
        UserDetails loggedUser = user.toLoggedUser();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        loggedUser, null, loggedUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
