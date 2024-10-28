package com.Saransh.E_Commerce.controller;

import com.Saransh.E_Commerce.Model.Product;
import com.Saransh.E_Commerce.dto.ProductDTO;
import com.Saransh.E_Commerce.exceptions.AlreadyExistsException;
import com.Saransh.E_Commerce.exceptions.ResourceNotFoundException;
import com.Saransh.E_Commerce.request.AddProductRequest;
import com.Saransh.E_Commerce.request.UpdateProductRequest;
import com.Saransh.E_Commerce.response.ApiResponse;
import com.Saransh.E_Commerce.service.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductServiceImpl productService;

    @GetMapping("/product/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id)
    {
        try{
            Product product=productService.getProductById(id);
            ProductDTO productDTO=productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Succss!",productDTO));

        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts()
    {
            List<Product> products=productService.getAllProducts();
            List<ProductDTO>convertedProducts=productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Success!",convertedProducts));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request)
    {
        try{
            Product product=productService.addProduct(request);
            ProductDTO productDTO=productService.convertToDto(product);

            return ResponseEntity.ok(new ApiResponse("Succss!",productDTO));

        }
        catch (AlreadyExistsException e)
        {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/product/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest request, @PathVariable Long id)
    {
        try{
            Product product=productService.updateProductById(request,id);
            ProductDTO productDTO=productService.convertToDto(product);

            return ResponseEntity.ok(new ApiResponse("Success!",productDTO));

        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/product/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id)
    {
        try{
            productService.deleteProductById(id);

            return ResponseEntity.ok(new ApiResponse("Success!",id));

        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestBody String brand, @RequestBody String name)
    {
        try{
            List<Product> products=productService.getProductByBrandAndName(brand,name);
            List<ProductDTO>convertedProducts=productService.getConvertedProducts(products);

            if(products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not found!",null));
            }
            return ResponseEntity.ok(new ApiResponse("Succss!",convertedProducts));

        }
        catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestBody String category, @RequestBody String brand)
    {
        try{
            List<Product> products=productService.getProductsByCategoryAndBrand(category,brand);
            List<ProductDTO>convertedProducts=productService.getConvertedProducts(products);

            if (products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Succss!",convertedProducts));

        }
        catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));

        }
    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductsByName(@RequestBody String name)
    {
        try{
            List<Product> products=productService.getProductByName(name);
            List<ProductDTO>convertedProducts=productService.getConvertedProducts(products);

            if(products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not found",null));

            }
            return ResponseEntity.ok(new ApiResponse("Success!",convertedProducts));

        }
        catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand)
    {
        try{
            List<Product> products=productService.getProductByBrand(brand);
            List<ProductDTO>convertedProducts=productService.getConvertedProducts(products);

            if (products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Succss!",convertedProducts));

        }
        catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category)
    {
        try{
            List<Product> products=productService.getProductsByCategory(category);
            List<ProductDTO>convertedProducts=productService.getConvertedProducts(products);

            if (products.isEmpty())
            {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not found",null));

            }
            return ResponseEntity.ok(new ApiResponse("Succss!",convertedProducts));

        }
        catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name)
    {
        try{
            Long count=productService.countProductByBrandAndName(brand,name);
            return ResponseEntity.ok(new ApiResponse("Success",count));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
