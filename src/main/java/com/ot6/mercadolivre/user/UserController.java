package com.ot6.mercadolivre.user;

import com.ot6.mercadolivre.user.dtos.NewUserResponse;
import com.ot6.mercadolivre.user.dtos.NewUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<NewUserResponse> criar(@RequestBody @Valid NewUserRequest userRequest) {
        User user = userRequest.toEntity();
        userRepository.save(user);
        return ResponseEntity.ok(user.toNewUserResponse());
    }
}
