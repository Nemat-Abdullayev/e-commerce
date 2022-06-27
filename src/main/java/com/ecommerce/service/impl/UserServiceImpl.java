package com.ecommerce.service.impl;

import com.ecommerce.dto.request.DepositRequestDTO;
import com.ecommerce.dto.request.UserRequestDTO;
import com.ecommerce.dto.request.UserUpdateRequestDTO;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.UserResponseDTO;
import com.ecommerce.exception.custom.EntityNotFoundException;
import com.ecommerce.exception.custom.SystemException;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.RefreshTokenService;
import com.ecommerce.service.UserService;
import com.ecommerce.service.redis.UserCacheService;
import com.ecommerce.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenService refreshTokenService;

    private final UserCacheService userCacheService;


    @Override
    @Transactional
    public ApiResponse<?> create(UserRequestDTO requestDTO) {
        log.info("create user by requested data {}", requestDTO);
        if (Objects.isNull(requestDTO)) throw new SystemException("requested data is null");
        Role role = roleRepository.findByNameAndActiveTrue(requestDTO.getRole());
        User user = UserMapper.INSTANCE.mapToEntity(requestDTO);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        userRepository.save(user);
        return new ApiResponse<>(true);
    }

    @Override
    @Transactional
    public ApiResponse<?> update(UserUpdateRequestDTO userUpdateRequestDTO) {
        log.info("update user by requested data {}", userUpdateRequestDTO);
        if (Objects.isNull(userUpdateRequestDTO)) throw new SystemException("requested data is null");
        Long userId = userUpdateRequestDTO.getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class, "id", String.valueOf(userId)));
        Role role = new Role();
        role.setId(userUpdateRequestDTO.getId());

        user.setRole(role);
        user.setDeposit(userUpdateRequestDTO.getDeposit());
        user.setUsername(userUpdateRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userUpdateRequestDTO.getPassword()));
        return new ApiResponse<>(true);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        log.info("find user by username {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public ApiResponse<?> findById(Long id) {
        log.info("find user by user id {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, "id", String.valueOf(id)));
        UserResponseDTO userResponseDTO = UserMapper.INSTANCE.mapToDto(user);
        return new ApiResponse<>(true, userResponseDTO);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        log.info("find user by id for auth {}", id);
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public ApiResponse<?> delete(Long id) {
        log.info("delete user by id before this delete products of user");
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(User.class, "id", String.valueOf(id)));
        productRepository.deleteAllBySellerId(user.getId());
        userRepository.delete(user);
        return new ApiResponse<>(true);
    }

    @Override
    @Transactional
    public ApiResponse<?> addDeposit(DepositRequestDTO depositRequestDTO) {
        log.info("add deposit for buyer");
        String username = AuthUtil.getUsername();
        User buyer = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(User.class, "username", username));
        if (depositRequestDTO.getDeposit() % 5 != 0)
            throw new SystemException("machine should only accept 5, 10, 20, 50 and 100 cent coins");
        Double buyerDeposit = Objects.nonNull(buyer.getDeposit())
                ? buyer.getDeposit() + depositRequestDTO.getDeposit() : depositRequestDTO.getDeposit();
        buyer.setDeposit(buyerDeposit);
        userRepository.save(buyer);
        return new ApiResponse<>(true);
    }

    @Override
    @Transactional
    public ApiResponse<?> resetDeposit() {
        log.info("reset deposit buy the buyer");
        String username = AuthUtil.getUsername();
        User buyer = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(User.class, "id", username));
        buyer.setDeposit(0d);
        userRepository.save(buyer);
        return new ApiResponse<>(true);
    }

    @Override
    public ApiResponse<?> logoutAllActiveSessions(String username) {
        log.info("logout all active sessions of the account");
        String refreshToken = refreshTokenService.refreshToken(username);
        userCacheService.deleteByRefreshToken(refreshToken);
        return new ApiResponse<>(true);
    }
}
