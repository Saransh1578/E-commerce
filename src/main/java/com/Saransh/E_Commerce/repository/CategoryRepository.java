package com.Saransh.E_Commerce.repository;

import com.Saransh.E_Commerce.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);

    boolean existsByName(String name);
}
