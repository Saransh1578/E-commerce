package com.Saransh.E_Commerce.repository;

import com.Saransh.E_Commerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
