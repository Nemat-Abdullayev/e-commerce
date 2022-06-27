package com.ecommerce;

import com.ecommerce.enums.RoleName;
import com.ecommerce.model.Role;
import com.ecommerce.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;

@EnableCaching
@SpringBootApplication
@RequiredArgsConstructor
public class ECommerceApplication implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }

    @Override
    @SneakyThrows
    public void run(String... args) {

        Role roleAdmin = Role.builder().name(RoleName.SELLER).active(true).build();
        Role roleManager = Role.builder().name(RoleName.BUYER).active(true).build();
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) roleRepository.saveAll(List.of(roleAdmin, roleManager));
    }
}
