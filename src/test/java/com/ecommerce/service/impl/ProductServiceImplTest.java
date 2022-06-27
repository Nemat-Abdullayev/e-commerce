package com.ecommerce.service.impl;

import com.ecommerce.dto.request.BuyProductRequestDTO;
import com.ecommerce.exception.custom.EntityNotFoundException;
import com.ecommerce.exception.custom.NotEnoughDepositException;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

class ProductServiceImplTest {


    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    private ProductServiceImpl productServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productServiceImplUnderTest = new ProductServiceImpl(productRepository, userRepository);
    }

    @Test
    void buyProductBuyerNotFoundTest() {
        //given
        Product mockProduct = Product.builder()
                .amountAvailable(5)
                .cost(100)
                .productName("test")
                .sellerId(1L)
                .build();
        long productId = 1L;
        mockProduct.setId(productId);

        User sellerMock = User.builder()
                .username("john")
                .password("123")
                .deposit(200d)
                .build();

        sellerMock.setId(1L);

        BuyProductRequestDTO mockBuyProductRequestDTO = BuyProductRequestDTO.builder()
                .productId(1L)
                .countOfProducts(2)
                .build();

        given(productRepository.findById(productId)).willReturn(Optional.of(mockProduct));
        given(userRepository.findByUsername("john")).willReturn(Optional.of(sellerMock));

        assertThatThrownBy(
                () -> productServiceImplUnderTest.buyProduct(mockBuyProductRequestDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User was not found for parameters [username, " + null + "]");


        //when


        //then
    }
}