package com.ot6.mercadolivre.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ot6.mercadolivre.user.dtos.NewUserRequest;
import com.ot6.mercadolivre.user.dtos.NewUserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper jsonMapper;

    @Autowired
    UserRepository userRepository;

    @Test
    void shouldCreateANewUser() throws Exception {
        mockMvc.perform(
                post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(new NewUserRequest("joao.test@zup.com", "123456")))
        ).andExpect(status().isOk());

        List<User> users = userRepository.findAllByEmail("joao.test@zup.com");

        assertTrue(users.size() == 1);

        User user = users.get(0);
        NewUserResponse userResponse = user.toNewUserResponse();

        assertEquals("joao.test@zup.com", userResponse.getEmail());
    }

    @Test
    void shouldNotAllowTwoUsersWithSameEmail() throws Exception {
        NewUserRequest newUser = new NewUserRequest("joao.email@zup.com", "123456");
        NewUserRequest newUserWithSameEmail = new NewUserRequest("joao.email@zup.com", "654321");

        mockMvc.perform(
                post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(newUser))
        ).andExpect(status().isOk());

        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(newUserWithSameEmail))
        ).andExpect(status().isBadRequest());

        List<User> users = userRepository.findAllByEmail("joao.email@zup.com");

        assertTrue(users.size() == 1);

        User user = users.get(0);
        NewUserResponse userResponse = user.toNewUserResponse();

        assertEquals("joao.email@zup.com", userResponse.getEmail());
    }

    private String toJson(NewUserRequest newUserRequest) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(newUserRequest);
    }
}
