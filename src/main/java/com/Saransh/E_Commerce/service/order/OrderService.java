package com.Saransh.E_Commerce.service.order;

import com.Saransh.E_Commerce.Model.Order;
import com.Saransh.E_Commerce.dto.OrderDTO;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId);
    OrderDTO getOrder(Long orderId);
    List<OrderDTO> getUserOrders(Long userId);

    OrderDTO convertToDto(Order order);
}
