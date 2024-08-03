package com.personal.soshoestore_be.service.impl;

import com.personal.soshoestore_be.dto.CartDTO;
import com.personal.soshoestore_be.dto.CartDetailDTO;
import com.personal.soshoestore_be.repository.CartRepository;
import com.personal.soshoestore_be.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;


    @Override
    public CartDTO getCart(long cartId) throws Exception {
        return cartRepository.findById(cartId);
    }

    @Override
    public CartDTO addToCart(long cartId, CartDetailDTO cartDetailDto) throws Exception {
        log.info("Adding to cart: {}", cartDetailDto);
        List<CartDetailDTO> CartDetailsDto = cartRepository.findById(cartId).getCartDetailsDto();
        CartDetailsDto.stream().filter(s -> Objects.equals(s.getShoeId(), cartDetailDto.getShoeId()) && Objects.equals(s.getSize().getId(), cartDetailDto.getSize().getId())).findFirst()
                .ifPresentOrElse(
                        (value) -> {
                            value.setQuantity(value.getQuantity() + 1);
                            },
                        () -> {
                            CartDetailsDto.add(cartDetailDto);
                        }
                );
        CartDTO cartDto = CartDTO.builder()
                .id(cartId)
                .cartDetailsDto(CartDetailsDto)
                .build();
        cartRepository.save(cartDto);
        return cartDto;
    }

    @Override
    public CartDTO updateToCart(long cartId, CartDetailDTO cartDetailDto) throws Exception {
        log.info("Update cart: {}", cartDetailDto);
        List<CartDetailDTO> CartDetailsDto = cartRepository.findById(cartId).getCartDetailsDto();
        CartDetailsDto.stream().filter(s -> Objects.equals(s.getShoeId(), cartDetailDto.getShoeId()) && Objects.equals(s.getSize().getId(), cartDetailDto.getSize().getId())).findFirst()
                .ifPresentOrElse(
                        (value) -> {
                            value.setQuantity(cartDetailDto.getQuantity());
                        },
                        () -> {
                            CartDetailsDto.add(cartDetailDto);
                        }
                );
        CartDTO cartDto = CartDTO.builder()
                .id(cartId)
                .cartDetailsDto(CartDetailsDto)
                .build();
        cartRepository.save(cartDto);
        return cartDto;
    }

    @Override
    public CartDTO removeFromCart(long cartId, int shoeId) throws Exception {
        List<CartDetailDTO> CartDetailsDto = cartRepository.findById(cartId).getCartDetailsDto();
        CartDTO cartDto = CartDTO.builder()
                .id(cartId)
                .cartDetailsDto(CartDetailsDto.stream().filter(cI -> !(cI.getShoeId() == shoeId)).toList())
                .build();
        cartRepository.save(cartDto);
        return cartDto;
    }

    @Override
    public CartDTO clearCart(long cartId) {
        CartDTO cartDto = CartDTO.builder()
                .id(cartId)
                .cartDetailsDto(List.of())
                .build();
        cartRepository.save(cartDto);
        return cartDto;
    }
}
