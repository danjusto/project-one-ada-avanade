package com.ada_avanada.project_one.service;

import com.ada_avanada.project_one.dto.AddressRequestDTO;
import com.ada_avanada.project_one.dto.AddressResponseDTO;
import com.ada_avanada.project_one.entity.Address;
import com.ada_avanada.project_one.repository.AddressRepository;
import com.ada_avanada.project_one.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public AddressResponseDTO create(AddressRequestDTO dto) {
        var userOp = this.userRepository.findById(dto.userId());
        if (userOp.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        var address = new Address(dto, userOp.get());
        var registeredAddress = this.addressRepository.save(address);
        return registeredAddress.dto();
    }

    public AddressResponseDTO getOne(Long id) {
        Optional<Address> addressOptional = this.addressRepository.findById(id);
        if (addressOptional.isEmpty()) {
            throw new EntityNotFoundException("Address not found.");
        }
        return addressOptional.get().dto();
    }

    @Transactional
    public void remove(Long id) {
        this.addressRepository.deleteById(id);
    }

    @Transactional
    public AddressResponseDTO edit(Long id, AddressRequestDTO dto) {
        Optional<Address> addressOptional = this.addressRepository.findById(id);
        if (addressOptional.isEmpty()) {
            throw new EntityNotFoundException("Address not found.");
        }
        addressOptional.get().edit(dto);
        this.addressRepository.save(addressOptional.get());
        return addressOptional.get().dto();
    }
}
