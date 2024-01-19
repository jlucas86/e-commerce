package com.example.userInfo;

import com.example.cart.Cart;
import com.example.order.Order;
import com.example.role.Role;
import com.example.store.Store;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity

public class UserInfo {

    @Id
    @SequenceGenerator(name = "user_id_sequence", sequenceName = "user_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
    private Integer id;

    private String email;
    private String username;
    private String password;

    @ManyToMany()
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne
    @JoinTable(name = "user_cart", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "cart_id"))
    private Set<Cart> cart = new HashSet<>();

    @OneToMany
    @JoinTable(name = "user_store", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "store_id"))
    private Set<Store> stores = new HashSet<>();

    @OneToMany
    @JoinTable(name = "user_order", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<Order> orders = new HashSet<>();

    public UserInfo() {
    }

    public UserInfo(Integer id, String email, String username, String password, Set<Role> roles, Set<Cart> cart,
            Set<Store> stores, Set<Order> orders) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.cart = cart;
        this.stores = stores;
        this.orders = orders;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Cart> getCart() {
        return this.cart;
    }

    public void setCart(Set<Cart> cart) {
        this.cart = cart;
    }

    public Set<Store> getStores() {
        return this.stores;
    }

    public void setStores(Set<Store> stores) {
        this.stores = stores;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public UserInfo id(Integer id) {
        setId(id);
        return this;
    }

    public UserInfo email(String email) {
        setEmail(email);
        return this;
    }

    public UserInfo username(String username) {
        setUsername(username);
        return this;
    }

    public UserInfo password(String password) {
        setPassword(password);
        return this;
    }

    public UserInfo roles(Set<Role> roles) {
        setRoles(roles);
        return this;
    }

    public UserInfo cart(Set<Cart> cart) {
        setCart(cart);
        return this;
    }

    public UserInfo stores(Set<Store> stores) {
        setStores(stores);
        return this;
    }

    public UserInfo orders(Set<Order> orders) {
        setOrders(orders);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserInfo)) {
            return false;
        }
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(id, userInfo.id) && Objects.equals(email, userInfo.email)
                && Objects.equals(username, userInfo.username) && Objects.equals(password, userInfo.password)
                && Objects.equals(roles, userInfo.roles) && Objects.equals(cart, userInfo.cart)
                && Objects.equals(stores, userInfo.stores) && Objects.equals(orders, userInfo.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, password, roles, cart, stores, orders);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", email='" + getEmail() + "'" +
                ", username='" + getUsername() + "'" +
                ", password='" + getPassword() + "'" +
                ", roles='" + getRoles() + "'" +
                ", cart='" + getCart() + "'" +
                ", stores='" + getStores() + "'" +
                ", orders='" + getOrders() + "'" +
                "}";
    }

}