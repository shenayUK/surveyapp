package com.turkcell.pollservice.repository;

import com.turkcell.pollservice.domain.Role;
import com.turkcell.pollservice.domain.enums.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RolesEnum name);
}
