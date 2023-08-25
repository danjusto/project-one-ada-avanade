package com.ada_avanada.project_one.service;

import com.ada_avanada.project_one.dto.UserRequestDTO;
import com.ada_avanada.project_one.dto.UserResponseDTO;
import com.ada_avanada.project_one.entity.User;
import com.ada_avanada.project_one.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDTO create(UserRequestDTO dto) {
        var user = new User(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var registeredUser = this.userRepository.save(user);
        return registeredUser.dto();
    }

    public List<UserResponseDTO> getAll() {
        return this.userRepository.findAll().stream().map(User::dto).toList();
    }

    public UserResponseDTO getOne(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.get().dto();
    }

    @Transactional
    public void remove(Long id) {
        this.userRepository.deleteById(id);
    }

    @Transactional
    public UserResponseDTO edit(Long id, UserRequestDTO dto) {
        var user = userRepository.getReferenceById(id);
        user.edit(dto);
        userRepository.save(user);
        return user.dto();
    }
}
