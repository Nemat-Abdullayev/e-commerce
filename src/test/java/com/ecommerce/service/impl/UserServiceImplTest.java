package com.ecommerce.service.impl;

import com.ecommerce.dto.request.DepositRequestDTO;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.exception.custom.EntityNotFoundException;
import com.ecommerce.exception.custom.SystemException;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.RefreshTokenService;
import com.ecommerce.service.redis.UserCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class UserServiceImplTest {


    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RefreshTokenService refreshTokenService;
    @Mock
    private UserCacheService userCacheService;


    private UserServiceImpl userServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userServiceImplUnderTest = new UserServiceImpl(
                userRepository, roleRepository,
                productRepository, passwordEncoder,
                refreshTokenService, userCacheService
        );
    }

    @Test
    void addDepositTest() {
        //given


        User mockUser = User.builder()
                .deposit(125d)
                .password("123")
                .username("john")
                .build();

        DepositRequestDTO mockDepositRequestDTO = DepositRequestDTO.builder()
                .deposit(5d)
                .build();

        given(userRepository.findByUsername(null)).willReturn(Optional.of(mockUser));

        //when

        ApiResponse<?> response = userServiceImplUnderTest.addDeposit(mockDepositRequestDTO);

        //then
        assertTrue(response.isSuccess());

    }

    @Test
    void addDepositTestNotMultiplesOfFive() {
        //given


        User mockUser = User.builder()
                .deposit(125d)
                .password("123")
                .username("john")
                .build();

        DepositRequestDTO mockDepositRequestDTO = DepositRequestDTO.builder()
                .deposit(3d)
                .build();

        given(userRepository.findByUsername(null)).willReturn(Optional.of(mockUser));

        //when

        //then
        assertThatThrownBy(
                () -> userServiceImplUnderTest.addDeposit(mockDepositRequestDTO))
                .isInstanceOf(SystemException.class)
                .hasMessageContaining("machine should only accept 5, 10, 20, 50 and 100 cent coins");

    }


    @Test
    void addDepositTestBuyerNotFound() {
        //given


        User mockUser = User.builder()
                .deposit(125d)
                .password("123")
                .username("john")
                .build();

        DepositRequestDTO mockDepositRequestDTO = DepositRequestDTO.builder()
                .deposit(5d)
                .build();

        given(userRepository.findByUsername("john")).willReturn(Optional.of(mockUser));

        //when
        //then
        assertThatThrownBy(
                () -> userServiceImplUnderTest.addDeposit(mockDepositRequestDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User was not found for parameters [username, " + null + "]");

    }
}