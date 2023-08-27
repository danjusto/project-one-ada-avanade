package com.ada_avanada.project_one.service;

import com.ada_avanada.project_one.dto.UserEditDTO;
import com.ada_avanada.project_one.dto.UserRequestDTO;
import com.ada_avanada.project_one.dto.UserResponseDTO;
import com.ada_avanada.project_one.entity.User;
import com.ada_avanada.project_one.exception.AppException;
import com.ada_avanada.project_one.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
        var checkUserExists = this.userRepository.findByUsernameOrCpfOrEmail(dto.username(), dto.cpf(), dto.email());
        if (checkUserExists.isPresent()) {
            throw new AppException("User already exists.");
        }
        var user = new User(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var registeredUser = this.userRepository.save(user);
        return registeredUser.dto();
    }

    public List<UserResponseDTO> getAll() {
        return this.userRepository.findAllByOrderByIdAsc().stream().map(User::dtoToGetAll).toList();
    }

    public UserResponseDTO getOne(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found.");
        }
        return userOptional.get().dto();
    }

    @Transactional
    public void remove(Long id) {
        this.userRepository.deleteById(id);
    }

    @Transactional
    public UserResponseDTO edit(Long id, UserEditDTO dto) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found.");
        }
        userOptional.get().edit(dto);
        userRepository.save(userOptional.get());
        return userOptional.get().dto();
    }
}
