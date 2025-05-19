package comsep_23.JetEco.service;

import comsep_23.JetEco.entity.User;
import comsep_23.JetEco.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createNewUser(User user) {
        if (userRepository.findByPhone(user.getPhone()).isPresent()) {
            throw new RuntimeException("User with this phone number already exists.");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            throw new RuntimeException("Name must not be empty.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegisteredAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User with id " + user.getId() + " not found"));

        existingUser.setName(user.getName());
        existingUser.setPhone(user.getPhone());
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(existingUser);
    }

    public Optional<User> getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with id " + id + " not found.");
        }
    }

}


