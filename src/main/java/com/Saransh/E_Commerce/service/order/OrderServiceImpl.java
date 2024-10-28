package com.Saransh.E_Commerce.service.order;

import com.Saransh.E_Commerce.Model.Cart;
import com.Saransh.E_Commerce.Model.Order;
import com.Saransh.E_Commerce.Model.OrderItem;
import com.Saransh.E_Commerce.Model.Product;
import com.Saransh.E_Commerce.dto.OrderDTO;
import com.Saransh.E_Commerce.enums.OrderStatus;
import com.Saransh.E_Commerce.exceptions.ResourceNotFoundException;
import com.Saransh.E_Commerce.repository.OrderRepository;
import com.Saransh.E_Commerce.repository.ProductRepository;
import com.Saransh.E_Commerce.service.cart.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;


    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart=cartService.getCartByUserId(userId);

        Order order=createOrder(cart);
        List<OrderItem> orderItemList=createOrderItems(order,cart);

        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder=orderRepository.save(order);

        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    private Order createOrder(Cart cart)
    {
        Order order=new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return  cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return  new OrderItem(
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice(),
                    order,
                    product);
        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> items)
    {
        return items.stream()
        .map(item->item.getPrice()
            .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }




    @Override
    public OrderDTO getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(()->new ResourceNotFoundException("Not found!"));
    }

    @Override
    public List<OrderDTO> getUserOrders(Long userId) {
        List<Order> orders=orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList();
    }

    @Override
    public OrderDTO convertToDto(Order order)
    {
        return modelMapper.map(order,OrderDTO.class);
    }
}
