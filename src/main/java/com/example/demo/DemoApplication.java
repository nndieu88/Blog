package com.example.demo;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner initUser(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            roleRepository.save(new Role(1L, "ROLE_ADMIN"));
            roleRepository.save(new Role(2L, "ROLE_USER"));
            userRepository.save(new User(1L, "admin00", "Hà Nội", "0901234567", "admin@gmail.com", BCrypt.hashpw("123456", BCrypt.gensalt(12)), new Role(1L, "ROLE_ADMIN")));
        };
    }
}
