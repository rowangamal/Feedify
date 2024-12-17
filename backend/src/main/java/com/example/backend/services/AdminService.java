package com.example.backend.services;
import com.example.backend.dtos.AdminDTO;
import com.example.backend.dtos.UserDTO;
import com.example.backend.entities.Admin;
import com.example.backend.entities.User;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repositories.AdminRepository;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public List<UserDTO> getAllUsers() {
        Set<Long> excludedUserIds = getExcludedUserIds();
        return userRepository.findAll().stream()
                .filter(user -> !excludedUserIds.contains(user.getId()))
                .map(user -> new UserDTO(user.getId(), user.getEmail()))
                .collect(Collectors.toList());
    }

    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .filter(admin -> !isCurrentUser(admin.getUser().getId(), userService.getUserId()))
                .map(admin -> new AdminDTO(admin.getId(), admin.getUser().getEmail()))
                .collect(Collectors.toList());
    }

    public Set<Long> getExcludedUserIds() {
        Set<Long> excluded = adminRepository.findAll().stream()
                .map(admin -> admin.getUser().getId())
                .collect(Collectors.toSet());
        excluded.add(userService.getUserId());
        return excluded;
    }

    public boolean isCurrentUser(long userId, long currentUserId) {
        return userId == currentUserId;
    }

    public void promoteToAdmin(long userId) {
        User user = findUserById(userId);
        Admin admin = new Admin();
        admin.setUser(user);
        adminRepository.save(admin);
    }

    public void demoteToUser(long adminId) {
        Admin admin = findAdminById(adminId);
        adminRepository.delete(admin);
    }

    private User findUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: %d".formatted(userId)));
    }

    private Admin findAdminById(long adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with ID: %d".formatted(adminId)));
    }
}
