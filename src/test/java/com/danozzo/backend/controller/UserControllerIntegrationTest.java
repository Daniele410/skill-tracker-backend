package com.danozzo.backend.controller;

import com.danozzo.backend.model.User;
import com.danozzo.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;

    @Test
    void createUser_shouldReturnCreatedUser() throws Exception {
        // Given
        User user = new User(1L, "Mario", "mario@email.com", "1234567890", "mariou", Set.of("USER"));
        String inputJson = objectMapper.writeValueAsString(user);

        // When
        when(userService.saveUser(any(User.class))).thenReturn(user);

        // Then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("Mario"));
    }

    @Test
    void getUser_shouldReturnUserById() throws Exception {
        // Given
        Long id = 1L;
        User user = new User(id, "Mario", "mario@email.com", "1234567890", "mariou", Set.of("USER"));

        // When
        when(userService.getUserById(id)).thenReturn(user);

        // Then
        mockMvc.perform(get("/api/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value("Mario"));
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() throws Exception {
        // Given
        List<User> users = List.of(
                new User(1L, "Mario", "mario@email.com", "1234567890", "mariou", Set.of("USER")),
                new User(2L, "Luigi", "luigi@email.com", "0987654321", "luigiu", Set.of("ADMIN"))
        );

        // When
        when(userService.getAllUsers()).thenReturn(users);

        // Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].username").value("Mario"))
                .andExpect(jsonPath("$[1].username").value("Luigi"));
    }

    @Test
    void deleteUser_shouldReturnNoContent() throws Exception {
        // Given
        Long id = 1L;

        // When
        doNothing().when(userService).deleteUser(id);

        // Then
        mockMvc.perform(delete("/api/users/{id}", id))
                .andExpect(status().isOk());
    }
}