package com.example.cart;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exceptions.CartDoesNotExist;
import com.example.exceptions.CartDoesNotMatchUser;
import com.example.userInfo.UserInfo;
import com.example.userInfo.UserInfoRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public CartService(CartRepository cartRepository, UserInfoRepository userInfoRepository) {
        this.cartRepository = cartRepository;
        this.userInfoRepository = userInfoRepository;
    }

    public Optional<Cart> getCart(Integer id) {
        return cartRepository.findById(id);
    }

    public void addCart(String username, Cart cart) {

        UserInfo user = userInfoRepository.findByUsername(username).get();
        cart.setUser(user);
        cartRepository.save(cart);

    }

    public void updateCart(String username, Cart cart) {
        try {
            verify(username, cart);
            cartRepository.save(cart);
        } catch (Exception e) {
            System.err.println(e.getMessage() + "++++++++++++++++++++++++++++++++++++++++++ urg");
        }
    }

    public void deleteCart(String username, Cart cart) {

        try {
            verify(username, cart);
            cartRepository.delete(cart);
        } catch (Exception e) {
            System.err.println(e.getMessage() + "++++++++++++++++++++++++++++++++++++++++++ urg");
        }
    }

    public void verify(String Username, Cart cart) throws CartDoesNotMatchUser, CartDoesNotExist {
        if (!cartRepository.existsById(cart.getId())) {
            throw new CartDoesNotExist(
                    String.format("Cart %i does not exist", cart.getId()));
        }
        UserInfo user = userInfoRepository.findByUsername(Username).get();
        if (cart.getUser().getId() != user.getId()) {
            throw new CartDoesNotMatchUser(
                    String.format("User %s does not own cart %i", user.getUsername(), cart.getId()));
        }

        // return user;

    }

}
