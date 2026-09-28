package com.fooddelivery.service;

import com.fooddelivery.dto.AddToCartRequest;
import com.fooddelivery.dto.CartResponse;
import com.fooddelivery.dto.CartItemResponse;
import com.fooddelivery.entity.Cart;
import com.fooddelivery.entity.CartItem;
import com.fooddelivery.entity.FoodItem;
import com.fooddelivery.entity.User;
import com.fooddelivery.repository.CartItemRepository;
import com.fooddelivery.repository.CartRepository;
import com.fooddelivery.repository.FoodItemRepository;
import com.fooddelivery.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final FoodItemRepository foodItemRepository;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            UserRepository userRepository,
            FoodItemRepository foodItemRepository) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.foodItemRepository = foodItemRepository;
    }

    public CartResponse addToCart(AddToCartRequest request) {

        User user;
        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } else {
            String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
            user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
        }

        FoodItem foodItem = foodItemRepository.findById(
                        request.getFoodItemId())
                .orElseThrow(() ->
                        new RuntimeException("Food item not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setTotalAmount(BigDecimal.ZERO);
                    return cartRepository.save(newCart);
                });

        CartItem cartItem = cartItemRepository
                .findByCartIdAndFoodItemId(
                        cart.getId(),
                        foodItem.getId())
                .orElse(null);

        if (cartItem != null) {

            cartItem.setQuantity(
                    cartItem.getQuantity() + request.getQuantity()
            );

        } else {

            cartItem = new CartItem();

            cartItem.setCart(cart);
            cartItem.setFoodItem(foodItem);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(foodItem.getPrice());
        }

        cartItemRepository.save(cartItem);

        updateCartTotal(cart);

        return getCartResponseByUserId(user.getId());
    }

    public CartResponse getCartResponseByUserId(Long userId) {
        Cart cart = getCartByUserId(userId);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        List<com.fooddelivery.dto.CartItemResponse> itemResponses = cartItems.stream().map(item -> {
            com.fooddelivery.dto.CartItemResponse resp = new com.fooddelivery.dto.CartItemResponse();
            resp.setId(item.getId());
            if (item.getFoodItem() != null) {
                resp.setFoodItemId(item.getFoodItem().getId());
                resp.setFoodItemName(item.getFoodItem().getName());
                resp.setFoodItemImageUrl(item.getFoodItem().getImageUrl());
            }
            resp.setQuantity(item.getQuantity());
            resp.setPrice(item.getPrice());
            resp.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            return resp;
        }).toList();

        int totalItems = itemResponses.stream().mapToInt(com.fooddelivery.dto.CartItemResponse::getQuantity).sum();
        return new com.fooddelivery.dto.CartResponse(cart.getId(), totalItems, cart.getTotalAmount(), itemResponses, cart.getUpdatedAt());
    }

    public List<CartItem> getCartItems(Long userId) {

        Cart cart = getCartByUserId(userId);

        return cartItemRepository.findByCartId(cart.getId());
    }

    public Cart getCartByUserId(Long userId) {

        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setTotalAmount(BigDecimal.ZERO);
                    return cartRepository.save(newCart);
                });
    }

    public CartResponse updateCartItem(
            Long cartItemId,
            Integer quantity) {

        CartItem cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() ->
                        new RuntimeException("Cart item not found"));

        Cart cart = cartItem.getCart();

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

        updateCartTotal(cart);

        return getCartResponseByUserId(cart.getUser().getId());
    }

    public CartResponse removeCartItem(Long cartItemId) {

        CartItem cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() ->
                        new RuntimeException("Cart item not found"));

        Cart cart = cartItem.getCart();

        cartItemRepository.delete(cartItem);

        updateCartTotal(cart);

        return getCartResponseByUserId(cart.getUser().getId());
    }

    public CartResponse clearCart(Long userId) {

        Cart cart = getCartByUserId(userId);

        List<CartItem> cartItems =
                cartItemRepository.findByCartId(cart.getId());

        cartItemRepository.deleteAll(cartItems);

        cart.setTotalAmount(BigDecimal.ZERO);

        cartRepository.save(cart);

        return getCartResponseByUserId(userId);
    }

    private void updateCartTotal(Cart cart) {

        List<CartItem> cartItems =
                cartItemRepository.findByCartId(cart.getId());

        BigDecimal total = cartItems.stream()
                .map(item ->
                        item.getPrice().multiply(
                                BigDecimal.valueOf(
                                        item.getQuantity()
                                )
                        )
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(total);

        cartRepository.save(cart);
    }
}