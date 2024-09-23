package ru.petrov.springjwt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.petrov.springjwt.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
