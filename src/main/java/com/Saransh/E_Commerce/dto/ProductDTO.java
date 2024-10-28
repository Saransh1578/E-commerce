package com.Saransh.E_Commerce.dto;

import com.Saransh.E_Commerce.Model.Category;
import com.Saransh.E_Commerce.Model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDTO> images;
}
