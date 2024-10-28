package com.Saransh.E_Commerce.repository;

import com.Saransh.E_Commerce.Model.Order;
import com.Saransh.E_Commerce.dto.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);
}
