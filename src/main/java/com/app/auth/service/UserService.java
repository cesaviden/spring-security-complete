package com.app.auth.service;

import com.app.auth.persistence.entities.UserEntity;
import com.app.auth.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public UserEntity updateUser(Long id, UserEntity user) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserEntity existingUser = userOptional.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setEnabled(user.isEnabled());
            existingUser.setAccountNoExpired(user.isAccountNoExpired());
            existingUser.setAccountNoLocked(user.isAccountNoLocked());
            existingUser.setCredentialNoExpired(user.isCredentialNoExpired());
            existingUser.setRoles(user.getRoles()); // Actualizar roles según sea necesario
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
