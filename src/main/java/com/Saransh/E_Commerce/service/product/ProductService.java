package com.Saransh.E_Commerce.service.product;

import com.Saransh.E_Commerce.Model.Product;
import com.Saransh.E_Commerce.dto.ProductDTO;
import com.Saransh.E_Commerce.request.AddProductRequest;
import com.Saransh.E_Commerce.request.UpdateProductRequest;

import java.util.List;

public interface ProductService {
    Product addProduct(AddProductRequest product);   //Use DTO
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProductById(UpdateProductRequest product, Long id);          //Use DTOS
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category,String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndName(String brand,String name);
    Long countProductByBrandAndName(String brand,String name);

    List<ProductDTO> getConvertedProducts(List<Product> products);

    ProductDTO convertToDto(Product product);

    //2 methods
}
