package com.example.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exceptions.CartDoesNotExist;
import com.example.exceptions.CartDoesNotMatchUser;
import com.example.product.Product;
import com.example.product.ProductRepository;
import com.example.record.Pair;
import com.example.userInfo.UserInfo;
import com.example.userInfo.UserInfoRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserInfoRepository userInfoRepository;
    private final ProductRepository productRepository;

    @Autowired

    public CartService(CartRepository cartRepository, UserInfoRepository userInfoRepository,
            ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userInfoRepository = userInfoRepository;
        this.productRepository = productRepository;
    }

    // get

    public Cart getCart(String username, Integer cartId) throws CartDoesNotMatchUser, CartDoesNotExist {

        Pair<UserInfo, Cart> pair = verify(username, cartId);
        return pair.other();
    }

    public List<Product> getCartContents(String username, Integer cartId)
            throws CartDoesNotMatchUser, CartDoesNotExist {

        Pair<UserInfo, Cart> pair = null;

        pair = verify(username, cartId);
        return pair.other().getItems();

    }

    // set

    public void addCart(String username, Cart cart) {

        UserInfo user = userInfoRepository.findByUsername(username).get();
        cart.setUser(user);
        cartRepository.save(cart);

    }

    public void addProduct(String username, Integer cartId, Integer productId) {

        Pair<UserInfo, Cart> pair = null;
        try {
            pair = verify(username, cartId);
            List<Product> products = null;
            products = pair.other().getItems();
            if (!productRepository.existsById(productId)) {
                // throw product not found
            }
            Product product = productRepository.findById(productId).get();
            products.add(product);
            pair.other().setItems(products);
            cartRepository.save(pair.other());

        } catch (Exception e) {
            System.err.println(e.getMessage() + "++++++++++++++++++++++++++++++++++++++++++ urg");
        }
    }

    // update

    public void updateCart(String username, Cart cart) throws CartDoesNotMatchUser, CartDoesNotExist {

        verify(username, cart.getId());
        cartRepository.save(cart);

    }

    // delete

    public void deleteCart(String username, Cart cart) throws CartDoesNotMatchUser, CartDoesNotExist {

        verify(username, cart.getId());
        cartRepository.delete(cart);

    }

    public Pair<UserInfo, Cart> verify(String Username, Integer cartId) throws CartDoesNotMatchUser, CartDoesNotExist {
        if (!cartRepository.existsById(cartId)) {
            throw new CartDoesNotExist(
                    String.format("Cart %d does not exist", cartId));
        }
        Cart cart = cartRepository.findById(cartId).get();
        UserInfo user = userInfoRepository.findByUsername(Username).get();
        if (cart.getUser().getId() != user.getId()) {
            throw new CartDoesNotMatchUser(
                    String.format("User %s does not own cart %d", user.getUsername(), cart.getId()));
        }

        return new Pair<UserInfo, Cart>(user, cart);

    }

}
