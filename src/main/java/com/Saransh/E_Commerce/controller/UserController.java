package com.Saransh.E_Commerce.controller;

import com.Saransh.E_Commerce.Model.User;
import com.Saransh.E_Commerce.dto.UserDTO;
import com.Saransh.E_Commerce.exceptions.AlreadyExistsException;
import com.Saransh.E_Commerce.exceptions.ResourceNotFoundException;
import com.Saransh.E_Commerce.request.CreateUserRequest;
import com.Saransh.E_Commerce.request.UpdateUserRequest;
import com.Saransh.E_Commerce.response.ApiResponse;
import com.Saransh.E_Commerce.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId)
    {
        try
        {
            User user=userService.getUserById(userId);
            UserDTO userDTO=userService.convertUserToDTO(user);
            return ResponseEntity.ok(new ApiResponse("Success!",userDTO));

        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request)
    {
        try
        {
            User user=userService.createUser(request);
            UserDTO userDTO=userService.convertUserToDTO(user);
            return ResponseEntity.ok(new ApiResponse("Created Successfully!",userDTO));
        }
        catch (AlreadyExistsException e)
        {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request,@PathVariable Long userId)
    {
        try
        {
            User user=userService.updateUser(request,userId);
            UserDTO userDTO=userService.convertUserToDTO(user);
            return ResponseEntity.ok(new ApiResponse("Updated successfully!",userDTO));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId)
    {
        try
        {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Deleted successfully!",null));
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }

}
