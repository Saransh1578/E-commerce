package com.Saransh.E_Commerce.dto;

import com.Saransh.E_Commerce.Model.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDTO {
    private Long cartId;
    private Set <CartItemDTO> items;
    private BigDecimal totalAmount;
}
