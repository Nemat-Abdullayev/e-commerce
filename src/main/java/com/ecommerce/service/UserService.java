package com.ecommerce.service;

import com.ecommerce.dto.request.DepositRequestDTO;
import com.ecommerce.dto.request.UserRequestDTO;
import com.ecommerce.dto.request.UserUpdateRequestDTO;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.model.User;

import java.util.Optional;

public interface UserService {

    ApiResponse<?> create(UserRequestDTO requestDTO);

    ApiResponse<?> update(UserUpdateRequestDTO userUpdateRequestDTO);

    Optional<User> findByUsername(String username);

    ApiResponse<?> findById(Long id);

    Optional<User> findUserById(Long id);

    ApiResponse<?> delete(Long id);

    ApiResponse<?> addDeposit(DepositRequestDTO depositRequestDTO);

    ApiResponse<?> resetDeposit();

    ApiResponse<?> logoutAllActiveSessions(String username);


}
