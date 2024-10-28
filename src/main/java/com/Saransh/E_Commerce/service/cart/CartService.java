package com.Saransh.E_Commerce.service.cart;

import com.Saransh.E_Commerce.Model.Cart;
import com.Saransh.E_Commerce.Model.User;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);



    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
