package com.Saransh.E_Commerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String LastName;
    private String email;
    private List<OrderDTO> orders;
    private CartDTO cart;
}
