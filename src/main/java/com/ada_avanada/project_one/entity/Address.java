package com.ada_avanada.project_one.entity;

import com.ada_avanada.project_one.dto.AddressRequestDTO;
import com.ada_avanada.project_one.dto.AddressResponseDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Getter
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String number;
    private String postalCode;
    private String city;
    private String state;
    private String country;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Address (AddressRequestDTO dto, User user) {
        this.street = dto.street();
        this.number = dto.number();
        this.postalCode = dto.postalCode();
        this.city = dto.city();
        this.state = dto.state();
        this.country = dto.country();
        this.user = user;
    }

    public AddressResponseDTO dto() {
        return new AddressResponseDTO(this.id, this.street, this.number, this.postalCode, this.city, this.state, this.country, this.user.getId());
    }

    public void edit(AddressRequestDTO dto) {
        if (dto.street() != null) {
            this.street = dto.street();
        }
        if (dto.number() != null) {
            this.number = dto.number();
        }
        if (dto.postalCode() != null) {
            this.postalCode = dto.postalCode();
        }
        if (dto.city() != null) {
            this.city = dto.city();
        }
        if (dto.state() != null) {
            this.state = dto.state();
        }
        if (dto.country() != null) {
            this.country = dto.country();
        }
    }
}
