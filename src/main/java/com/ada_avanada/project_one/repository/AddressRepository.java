package com.ada_avanada.project_one.repository;

import com.ada_avanada.project_one.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByOrderByIdAsc();
}
