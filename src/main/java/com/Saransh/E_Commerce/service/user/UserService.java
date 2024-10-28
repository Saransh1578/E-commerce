package com.Saransh.E_Commerce.service.user;

import com.Saransh.E_Commerce.Model.User;
import com.Saransh.E_Commerce.dto.UserDTO;
import com.Saransh.E_Commerce.request.CreateUserRequest;
import com.Saransh.E_Commerce.request.UpdateUserRequest;

public interface UserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);

    UserDTO convertUserToDTO(User user);

    User getAuthenticatedUser();


}
