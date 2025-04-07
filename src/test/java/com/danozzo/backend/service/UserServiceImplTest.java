package com.danozzo.backend.service;

import com.danozzo.backend.model.User;
import com.danozzo.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void saveUser_shouldReturnSavedUser() {
        // Given
        User user = new User(1L, "username", "mail@test.com", "pass", "Mario", Set.of("USER"));
        when(userRepository.save(user)).thenReturn(user);

        // When
        User saved = userService.saveUser(user);

        // Then
        assertEquals("username", saved.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserById_shouldReturnUserById() {
        // Given
        User user = new User(1L, "user", "email@test.com", "pass", "Mario", Set.of("USER"));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // When
        User result = userService.getUserById(1L);

        // Then
        assertEquals("user", result.getUsername());
        verify(userRepository).findById(1L);
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        // Given
        User user = new User(1L, "user", "email@test.com", "pass", "Mario", Set.of("USER"));
        User user2 = new User(2L, "user2", "email@test.com", "pass", "Mario", Set.of("USER"));
        when(userRepository.findAll()).thenReturn(List.of(user, user2));

        // When
        List<User> result = userService.getAllUsers();

        // Then
        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void deleteUser_shouldDeleteUser() {
        // Given
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository).deleteById(userId);
        verifyNoMoreInteractions(userRepository);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Given
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> userService.getUserById(99L));
        verify(userRepository).findById(99L);
    }

}