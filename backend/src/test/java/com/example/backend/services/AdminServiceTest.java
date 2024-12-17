package com.example.backend.services;

import com.example.backend.dtos.*;
import com.example.backend.entities.*;
import com.example.backend.exceptions.*;
import com.example.backend.repositories.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsersSingleUser() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("user1@gmail.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("user2@gmail.com");

        UserDTO userDTO1 = new UserDTO(user1.getId(), user1.getEmail());

        when(userService.getUserId()).thenReturn(2L);
        when(adminRepository.findAll()).thenReturn(Collections.emptyList());
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        List<UserDTO> users = adminService.getAllUsers();
        assertEquals(1, users.size());
        assertEquals(users.getFirst().getId(), userDTO1.getId());
        assertEquals(users.getFirst().getEmail(), userDTO1.getEmail());
    }

    @Test
    void getAllUsersCurrentUser() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("user1@gmail.com");
        UserDTO userDTO1 = new UserDTO(1L, "user1@gmail.com");

        when(userService.getUserId()).thenReturn(1L);
        when(adminRepository.findAll()).thenReturn(Collections.emptyList());
        when(userRepository.findAll()).thenReturn(List.of(user1));
        List<UserDTO> users = adminService.getAllUsers();
        assertEquals(0, users.size());
        assertFalse(users.contains(userDTO1));
    }

    @Test
    void getAllUsersMultipleAdmins() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("user1@gmail.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("user2@gmail.com");

        User adminUser1 = new User();
        adminUser1.setId(3L);
        adminUser1.setEmail("user3@gmail.com");

        User adminUser2 = new User();
        adminUser2.setId(4L);
        adminUser2.setEmail("user4@gmail.com");

        UserDTO userDTO1 = new UserDTO(1L, "user1@gmail.com");

        Admin admin1 = new Admin();
        admin1.setUser(adminUser1);

        Admin admin2 = new Admin();
        admin2.setUser(adminUser2);

        when(userService.getUserId()).thenReturn(2L);
        when(adminRepository.findAll()).thenReturn(List.of(admin1, admin2));
        when(userRepository.findAll()).thenReturn(List.of(user1, user2, adminUser1, adminUser2));
        List<UserDTO> users = adminService.getAllUsers();
        assertEquals(1, users.size());
        assertEquals(users.getFirst().getId(), userDTO1.getId());
        assertEquals(users.getFirst().getEmail(), userDTO1.getEmail());
    }



    @Test
    void getAllAdminsCurrentAdmin() {
        User adminUser = new User();
        adminUser.setId(1L);
        Admin admin = new Admin();
        admin.setId(1L);
        admin.setUser(adminUser);
        when(userService.getUserId()).thenReturn(1L);
        when(adminRepository.findAll()).thenReturn(List.of(admin));
        List<AdminDTO> admins = adminService.getAllAdmins();
        assertEquals(0, admins.size());
    }


    @Test
    void getAllAdminsMultipleAdmins() {
        User adminUser1 = new User();
        adminUser1.setId(1L);
        Admin admin1 = new Admin();
        admin1.setId(1L);
        admin1.setUser(adminUser1);
        User adminUser2 = new User();
        adminUser2.setId(2L);
        Admin admin2 = new Admin();
        admin2.setId(2L);
        admin2.setUser(adminUser2);
        User adminUser3 = new User();
        adminUser3.setId(3L);
        Admin admin3 = new Admin();
        admin3.setId(3L);
        admin3.setUser(adminUser3);
        when(userService.getUserId()).thenReturn(3L);
        when(adminRepository.findAll()).thenReturn(List.of(admin1, admin2, admin3));
        List<AdminDTO> admins = adminService.getAllAdmins();
        assertEquals(2, admins.size());
        assertEquals(1L, admins.get(0).getId());
        assertEquals(2L, admins.get(1).getId());
    }


    @Test
    void promoteToAdminValidUser() {
        long userId = 1L;
        User user = new User();
        user.setId(userId);
        Admin admin = new Admin();
        admin.setUser(user);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);
        adminService.promoteToAdmin(userId);
        verify(adminRepository).save(any(Admin.class));
        assertEquals(user, admin.getUser());
    }

    @Test
    void promoteToAdminUserNotFound() {
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> adminService.promoteToAdmin(userId));
    }

    @Test
    void demoteToUserValidAdmin() {
        long adminId = 1L;
        Admin admin = new Admin();
        admin.setId(adminId);
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
        adminService.demoteToUser(adminId);
        verify(adminRepository).delete(admin);
    }

    @Test
    void demoteToUserAdminNotFound() {
        long adminId = 1L;
        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> adminService.demoteToUser(adminId));
    }
}