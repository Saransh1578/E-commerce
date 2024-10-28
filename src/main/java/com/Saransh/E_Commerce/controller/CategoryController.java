package com.Saransh.E_Commerce.controller;

import com.Saransh.E_Commerce.Model.Category;
import com.Saransh.E_Commerce.exceptions.AlreadyExistsException;
import com.Saransh.E_Commerce.exceptions.ResourceNotFoundException;
import com.Saransh.E_Commerce.response.ApiResponse;
import com.Saransh.E_Commerce.service.category.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try{
            List<Category> categories=categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Done!",categories));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error!",INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse>addCategory(@RequestBody Category name){
        try{
            Category category=categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Success!",category));
        }
        catch (AlreadyExistsException e){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId)
    {
        try{
            Category category=categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Success!",category));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name)
    {
        try{
            Category category=categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Success!",category));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping ("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId)
    {
        try{
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(new ApiResponse("Success!",null));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@RequestBody Category category)
    {
        try
        {
            Category newCategory=categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new ApiResponse("Success!",newCategory));

        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

}
