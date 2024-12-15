package com.example.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.Product;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
        
    }

    @GetMapping("/{username}/getCart/{id}")
    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_CUSTOMR')")
    public Cart getCart(@PathVariable("username") String username, @PathVariable("id") Integer id) {
        try {
            return cartService.getCart(username, id);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    @GetMapping("/{username}/getCartContents/{id}")
    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_CUSTOMR')")
    public List<Product> getCartContents(@PathVariable("username") String username, @PathVariable("id") Integer id) {
        try {
            return cartService.getCartContents(username, id);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;

    }

    @PostMapping("/{username}/addCart")
    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_CUSTOMR')")
    public void addCart(@PathVariable("username") String username, @RequestBody Cart cart) {
        cartService.addCart(username, cart);
    }

    @PutMapping("/{username}/addProduct/{cartId}/{productId}")
    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_CUSTOMR')")
    public void addProduct(@PathVariable("username") String username, @PathVariable("cartId") Integer cartId,
            @PathVariable("productId") Integer productId) {
        cartService.addProduct(username, cartId, productId);
    }

    @PutMapping("/{username}/updateCart")
    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_CUSTOMR')")
    public void updateCart(@PathVariable("username") String username, @RequestBody Cart cart) {
        try {
            cartService.updateCart(username, cart);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @DeleteMapping("/{username}/deleteCart")
    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_CUSTOMR')")
    public void deleteCart(@PathVariable("username") String username, @RequestBody Cart cart) {
        try {
            cartService.deleteCart(username, cart);
        } catch (

        Exception e) {
            // TODO: handle exception
        }

    }
}
