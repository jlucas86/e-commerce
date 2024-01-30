package com.example.cart;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/getCart/{id}")
    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_CUSTOMR')")
    public Optional<Cart> getCart(@PathVariable("id") Integer id) {
        return cartService.getCart(id);
    }

    @PostMapping("/addCart/{username}")
    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_CUSTOMR')")
    public void addCart(@PathVariable("username") String username, @RequestBody Cart cart) {
        cartService.addCart(username, cart);
    }

    @PostMapping("/updateCart/{username}")
    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_CUSTOMR')")
    public void updateCart(@PathVariable("username") String username, @RequestBody Cart cart) {
        cartService.updateCart(username, cart);
    }

    @DeleteMapping("/deleteCart/{username}")
    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_CUSTOMR')")
    public void deleteCart(@PathVariable("username") String username, @RequestBody Cart cart) {
        cartService.deleteCart(username, cart);
    }

}
