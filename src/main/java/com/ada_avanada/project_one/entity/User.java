package com.ada_avanada.project_one.entity;

import com.ada_avanada.project_one.dto.UserRequestDTO;
import com.ada_avanada.project_one.dto.UserResponseDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String cpf;
    private String email;
    private String phone;
    private LocalDate registerDate;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<Address>();

    public User(UserRequestDTO dto) {
        this.name = dto.name();
        this.username = dto.username();
        this.password = dto.password();
        this.cpf = dto.cpf();
        this.email = dto.email();
        this.phone = dto.phone();
        this.registerDate = LocalDate.now();
        this.addresses.add(new Address(dto.address(), this));
    }

    public UserResponseDTO dto() {
        return new UserResponseDTO(this.id, this.name, this.username, this.cpf, this.email, this.phone, this.registerDate, this.addresses.stream().map(Address::dto).toList());
    }

    public void edit(UserRequestDTO dto) {
        if (dto.name() != null) {
            this.name = dto.name();
        }
        if (dto.email() != null) {
            this.email = dto.email();
        }
        if (dto.password() != null) {
            this.password = dto.password();
        }
        if (dto.phone() != null) {
            this.phone = dto.phone();
        }
    }
}
