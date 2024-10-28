package com.Saransh.E_Commerce.service.product;

import com.Saransh.E_Commerce.Model.Category;
import com.Saransh.E_Commerce.Model.Image;
import com.Saransh.E_Commerce.Model.Product;
import com.Saransh.E_Commerce.dto.ImageDTO;
import com.Saransh.E_Commerce.dto.ProductDTO;
import com.Saransh.E_Commerce.exceptions.AlreadyExistsException;
import com.Saransh.E_Commerce.exceptions.ResourceNotFoundException;
import com.Saransh.E_Commerce.repository.CategoryRepository;
import com.Saransh.E_Commerce.repository.ImageRepository;
import com.Saransh.E_Commerce.repository.ProductRepository;
import com.Saransh.E_Commerce.request.AddProductRequest;
import com.Saransh.E_Commerce.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor


public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    public Product addProduct(AddProductRequest request) {

        if(productExists(request.getName(),request.getBrand()))
        {
            throw new AlreadyExistsException(request.getBrand()+" "+request.getName()+" already exists!");
        }

        Category category= Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory=new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
          request.setCategory(category);
          return productRepository.save(createProduct(request,category));
    }

    private boolean productExists(String name, String brand)
    {
        return productRepository.existsByNameAndBrand(name,brand);
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        ()->{
                            throw new ResourceNotFoundException("Product not found!");
                        });
    }

    @Override
    public Product updateProductById(UpdateProductRequest product, Long id) {
       return productRepository.findById(id)
               .map(existingProduct->updateExistingProduct(existingProduct,product))
               .map(productRepository::save)
               .orElseThrow(()->new ResourceNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request)
    {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category=categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDTO> getConvertedProducts(List<Product> products)
    {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDTO convertToDto(Product product)
    {
        ProductDTO productDTO=modelMapper.map(product,ProductDTO.class);
        List<Image> images=imageRepository.findByProductId(product.getId());
        List<ImageDTO> imageDTOS=images.stream()
                .map(image->modelMapper.map(image,ImageDTO.class))
                .toList();
        productDTO.setImages(imageDTOS);
        return productDTO;
    }
}
