package com.Saransh.E_Commerce.repository;

import com.Saransh.E_Commerce.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findByUserId(Long userId);
}
