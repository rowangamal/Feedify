package com.example.backend.services;

import com.example.backend.entities.Admin;
import com.example.backend.entities.User;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repositories.AdminRepository;
import com.example.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    private User testUser(){
        User user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setEmail("test");
        user.setPassword("test");
        return user;
    }
    private Admin setAdmin(User user){
        Admin admin = new Admin();
        admin.setUser(user);
        return admin;
    }
    @Test
    void getUserByEmail() {
        User user = testUser();
        when(userRepository.findUsersByEmail("test")).thenReturn(Optional.of(user));
        User user1 = userService.getUserByEmail("test");
        assertNotNull(user1);
        assertEquals(user1.getEmail(), user.getEmail());
      }
    @Test
    void getUserByEmailNotFound() {
        User user = testUser();
        when(userRepository.findUsersByEmail("notfound")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUserByEmail("notfound"));
        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail("notfound"));
    }
    @Test
    void getUserByEmailNullPointer() {
        User user = testUser();
        when(userRepository.findUsersByEmail("notfound")).thenReturn(null);
        assertThrows(RuntimeException.class, () -> userService.getUserByEmail("notfound"));
        assertThrows(NullPointerException.class, () -> userService.getUserByEmail("notfound"));
    }
    @Test
    void getUserByUsername() {
        User user = testUser();
        when(userRepository.findUsersByUsername("test")).thenReturn(Optional.of(user));
        User user1 = userService.getUserByUsername("test");
        assertNotNull(user1);
        assertEquals(user1.getUsername(), user.getUsername());
      }
    @Test
    void getUserByUsernameNotFound() {
        User user = testUser();
        when(userRepository.findUsersByUsername("notfound")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUserByUsername("notfound"));
        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername("notfound"));
    }
    @Test
    void getUserByUsernameNullPointer() {
        User user = testUser();
        when(userRepository.findUsersByUsername("notfound")).thenReturn(null);
        assertThrows(RuntimeException.class, () -> userService.getUserByUsername("notfound"));
        assertThrows(NullPointerException.class, () -> userService.getUserByUsername("notfound"));
    }

    @Test
    void isAdminReturnsTrue() {
        User user = testUser();
        Admin admin = setAdmin(user);
        when(adminRepository.findByUser(user)).thenReturn(admin);
        assertTrue(userService.isAdmin(user));
      }
    @Test
    void isAdminReturnsFalse() {
        User user = testUser();
        when(adminRepository.findByUser(user)).thenReturn(null);
        assertFalse(userService.isAdmin(user));
    }

    @Test
    void getUserById() {
        User user = testUser();
        when(userRepository.findUserById(1)).thenReturn(Optional.of(user));
        User user1 = userService.getUserById(1);
        assertNotNull(user1);
        assertEquals(user1.getId(), user.getId());
      }

    @Test
    void getUserByIdNotFound() {
        when(userRepository.findUserById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUserById(1));
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1));
    }

    @Test
    void getUserByIdNullPointer() {
        when(userRepository.findUserById(1)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> userService.getUserById(1));
        assertThrows(NullPointerException.class, () -> userService.getUserById(1));
    }

    @Test
    void getUserRole() {
        User user = testUser();
        Admin admin = setAdmin(user);
        when(adminRepository.findByUser(user)).thenReturn(admin);
        assertEquals("ADMIN", userService.getUserRole(user).name());
      }

    @Test
    void getUserRoleReturnsUser() {
        User user = testUser();
        when(adminRepository.findByUser(user)).thenReturn(null);
        assertEquals("USER", userService.getUserRole(user).name());
        assertNotEquals("ADMIN", userService.getUserRole(user).name());
      }

}