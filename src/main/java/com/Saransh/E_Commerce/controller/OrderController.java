package com.Saransh.E_Commerce.controller;

import com.Saransh.E_Commerce.Model.Order;
import com.Saransh.E_Commerce.dto.OrderDTO;
import com.Saransh.E_Commerce.exceptions.ResourceNotFoundException;
import com.Saransh.E_Commerce.response.ApiResponse;
import com.Saransh.E_Commerce.service.order.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId)
    {
        try{
            Order order=orderService.placeOrder(userId);
            OrderDTO orderDTO=orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Success!",orderDTO));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error",e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId)
    {
        try
        {
            OrderDTO order=orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Success!",order));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/user/{userId}/order")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId)
    {
        try
        {
            List<OrderDTO> orders=orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Success!",orders));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
