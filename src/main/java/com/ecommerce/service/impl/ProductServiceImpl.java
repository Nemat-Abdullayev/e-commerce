package com.ecommerce.service.impl;

import com.ecommerce.dto.request.BuyProductRequestDTO;
import com.ecommerce.dto.request.ProductRequestDTO;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.ProductResponseDTO;
import com.ecommerce.dto.response.PurchasedResponseDTO;
import com.ecommerce.exception.custom.*;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.ProductService;
import com.ecommerce.util.AuthUtil;
import com.ecommerce.util.CoinChange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ApiResponse<?> create(ProductRequestDTO productRequestDTO) {
        log.info("save product bu requested data {}", productRequestDTO);
        double cost = productRequestDTO.getCost();
        String username = AuthUtil.getUsername();
        Long sellerId = userRepository.getUserId(username);
        if (cost % 5 != 0)
            throw new SystemException("cost should be in multiples of 5");
        Product product = ProductMapper.INSTANCE.mapToEntity(productRequestDTO);
        product.setSellerId(sellerId);
        productRepository.save(product);
        return new ApiResponse<>(true);
    }

    @Override
    public ApiResponse<?> findById(Long id) {
        log.info("find product byd id {}", id);
        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(Product.class, "id", String.valueOf(id)));
        ProductResponseDTO productResponseDTO = ProductMapper.INSTANCE.mapToDto(product);
        return new ApiResponse<>(true, productResponseDTO);
    }

    @Override
    public ApiResponse<?> findAll() {
        log.info("find all products");
        List<ProductResponseDTO> productResponseDTOS = productRepository.findAll()
                .stream()
                .map(ProductMapper.INSTANCE::mapToDto)
                .toList();
        return new ApiResponse<>(true, productResponseDTOS);
    }

    @Override
    @Transactional
    public ApiResponse<?> update(ProductRequestDTO productRequestDTO) {
        log.info("find product by id and update {}", productRequestDTO);

        String username = AuthUtil.getUsername();
        Long sellerId = userRepository.getUserId(username);

        if (Objects.isNull(productRequestDTO)) throw new SystemException("requested data is null");

        Long productId = productRequestDTO.getProductId();
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new EntityNotFoundException(Product.class, "id", String.valueOf(productId)));

        if (!product.getSellerId().equals(sellerId))
            throw new SellerIsNotOfTheProductException("seller id isn't match with the product's seller");

        product.setProductName(productRequestDTO.getProductName());
        product.setCost(productRequestDTO.getCost());
        product.setAmountAvailable(productRequestDTO.getAmountAvailable());

        productRepository.save(product);
        return new ApiResponse<>(true);
    }

    @Override
    @Transactional
    public ApiResponse<?> delete(Long id) {
        log.info("delete product by id");
        String username = AuthUtil.getUsername();
        Long sellerId = userRepository.getUserId(username);
        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(Product.class, "id", String.valueOf(id)));
        if (!product.getSellerId().equals(sellerId))
            throw new SellerIsNotOfTheProductException("seller id isn't match with the product seller id");
        productRepository.delete(product);
        return new ApiResponse<>(true);
    }

    @Override
    public ApiResponse<?> buyProduct(BuyProductRequestDTO productRequestDTO) {
        log.info("buy product by requested data {}", productRequestDTO);
        Long productId = productRequestDTO.getProductId();
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new EntityNotFoundException(Product.class, "id", String.valueOf(productId)));
        String username = AuthUtil.getUsername();
        User userBuyer = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new EntityNotFoundException(User.class, "username", username));

        double costOfProduct = product.getCost();
        int countOfProducts = productRequestDTO.getCountOfProducts();
        double totalCostOfProduct = costOfProduct * countOfProducts;
        double deposit = userBuyer.getDeposit();
        if (totalCostOfProduct > deposit)
            throw new NotEnoughDepositException("You don't have enough deposit to make this purchase");

        Integer productAmountAvailable = product.getAmountAvailable();
        if (countOfProducts > productAmountAvailable) {
            throw new NotAmountAvailableException("the product amount available is less than requested count , so" +
                    " available amount for the product is : " + productAmountAvailable);
        }
        int changeCountsOfProduct = productAmountAvailable - countOfProducts;
        double change = deposit - totalCostOfProduct;

        product.setAmountAvailable(changeCountsOfProduct);
        userBuyer.setDeposit(change);

        userRepository.save(userBuyer);
        productRepository.save(product);

        CoinChange coinChange = new CoinChange();
        PurchasedResponseDTO purchasedResponseDTO = PurchasedResponseDTO.builder()
                .spent(totalCostOfProduct)
                .change(coinChange.depositChange(change))
                .build();

        return new ApiResponse<>(true, purchasedResponseDTO);
    }
}
