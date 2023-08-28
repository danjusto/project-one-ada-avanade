package com.ada_avanada.project_one.entity;

import com.ada_avanada.project_one.dto.UserEditDTO;
import com.ada_avanada.project_one.dto.UserRequestDTO;
import com.ada_avanada.project_one.dto.UserResponseDTO;
import com.ada_avanada.project_one.infra.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String cpf;
    @Column(unique = true)
    private String email;
    private String phone;
    @CreationTimestamp
    private LocalDateTime registerDate;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<Address>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Order> orders = new ArrayList<Order>();
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(UserRequestDTO dto) {
        this.name = dto.name();
        this.username = dto.username();
        this.password = dto.password();
        this.cpf = dto.cpf();
        this.email = dto.email();
        this.phone = dto.phone();
        this.addresses.add(new Address(dto.address(), this));
        this.role = Role.CLIENT;
    }

    public UserResponseDTO dto() {
        return new UserResponseDTO(this.id, this.name, this.username, this.cpf, this.email, this.phone, this.registerDate, this.addresses.stream().map(Address::dto).toList(), this.orders.stream().map(Order::dto).toList());
    }

    public UserResponseDTO dtoToGetAll() {
        return new UserResponseDTO(this.id, this.name, this.username, this.cpf, this.email, this.phone, this.registerDate);
    }

    public void setAdmin(Role role) {
        this.role = Role.ADMIN;
    }

    public void edit(UserEditDTO dto) {
        if (dto.name() != null) {
            this.name = dto.name();
        }
        if (dto.email() != null) {
            this.email = dto.email();
        }
        if (dto.phone() != null) {
            this.phone = dto.phone();
        }
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(getRole().toString()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
