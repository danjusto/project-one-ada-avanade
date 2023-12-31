package com.ada_avanada.project_one.repository;

import com.ada_avanada.project_one.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findAllByOrderByIdAsc();

    Optional<User> findByUsernameOrCpfOrEmail(String username, String cpf, String email);
}
