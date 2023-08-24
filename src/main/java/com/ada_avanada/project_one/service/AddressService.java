package com.ada_avanada.project_one.service;

import com.ada_avanada.project_one.dto.AddressRequestDTO;
import com.ada_avanada.project_one.dto.AddressResponseDTO;
import com.ada_avanada.project_one.dto.UserRequestDTO;
import com.ada_avanada.project_one.dto.UserResponseDTO;
import com.ada_avanada.project_one.entity.Address;
import com.ada_avanada.project_one.entity.User;
import com.ada_avanada.project_one.repository.AddressRepository;
import com.ada_avanada.project_one.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public AddressResponseDTO create(Long userId, AddressRequestDTO dto) {
        var user = this.userRepository.getReferenceById(userId);
        var address = new Address(dto, user);
        var registeredAddress = this.addressRepository.save(address);
        return registeredAddress.dto();
    }

    public AddressResponseDTO getOne(Long id) {
        Optional<Address> userOptional = this.addressRepository.findById(id);
        return userOptional.get().dto();
    }

    public void remove(Long id) {
        this.addressRepository.deleteById(id);
    }

    public AddressResponseDTO edit(Long id, AddressRequestDTO dto) {
        var address = addressRepository.getReferenceById(id);
        address.edit(dto);
        this.addressRepository.save(address);
        return address.dto();
    }
}
