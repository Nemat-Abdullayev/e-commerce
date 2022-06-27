package com.ecommerce.repository;

import com.ecommerce.enums.RoleName;
import com.ecommerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByNameAndActiveTrue(RoleName name);
}
