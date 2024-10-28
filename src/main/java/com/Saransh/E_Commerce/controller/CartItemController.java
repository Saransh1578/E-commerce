package com.Saransh.E_Commerce.controller;

import com.Saransh.E_Commerce.Model.Cart;
import com.Saransh.E_Commerce.Model.User;
import com.Saransh.E_Commerce.exceptions.ResourceNotFoundException;
import com.Saransh.E_Commerce.response.ApiResponse;
import com.Saransh.E_Commerce.service.cart.CartService;
import com.Saransh.E_Commerce.service.cart.CartServiceImpl;
import com.Saransh.E_Commerce.service.cartitem.CartItemService;
import com.Saransh.E_Commerce.service.user.UserServiceImpl;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

    private final CartItemService cartItemService;
    private final CartService cartService;
    private final UserServiceImpl userService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId,@RequestParam int quantity){
        try
        {
            User user=userService.getAuthenticatedUser();
            Cart cart=cartService.initializeNewCart(user);

            cartItemService.addItemToCart(cart.getId(),productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Success!",null));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(),null));
        }
        catch (JwtException e)
        {
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,@PathVariable Long itemId){
        try
        {
            cartItemService.removeItemFromCart(cartId,itemId);
            return ResponseEntity.ok(new ApiResponse("Success!",null));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,@PathVariable Long itemId, @RequestParam int quantity)
    {
        try
        {
            cartItemService.updateItemQuantity(cartId,itemId,quantity);
            return ResponseEntity.ok(new ApiResponse("Updated successfully!",null));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
