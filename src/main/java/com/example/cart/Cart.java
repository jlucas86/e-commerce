package com.example.cart;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.product.Product;
import com.example.userInfo.UserInfo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import java.util.Objects;

@Entity
public class Cart {

    @Id
    @SequenceGenerator(name = "cart_id_sequence", sequenceName = "cart_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id_sequence")
    private Integer id;

    private Date createdDate;

    @ManyToMany
    @JoinTable(name = "cart_product", joinColumns = @JoinColumn(name = "cart_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> items = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserInfo user;

    public Cart() {
    }

    public Cart(Integer id, Date createdDate, Set<Product> items, UserInfo user) {
        this.id = id;
        this.createdDate = createdDate;
        this.items = items;
        this.user = user;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Product> getItems() {
        return this.items;
    }

    public void setItems(Set<Product> items) {
        this.items = items;
    }

    public UserInfo getUser() {
        return this.user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Cart id(Integer id) {
        setId(id);
        return this;
    }

    public Cart createdDate(Date createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public Cart items(Set<Product> items) {
        setItems(items);
        return this;
    }

    public Cart user(UserInfo user) {
        setUser(user);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Cart)) {
            return false;
        }
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(createdDate, cart.createdDate)
                && Objects.equals(items, cart.items) && Objects.equals(user, cart.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate, items, user);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", createdDate='" + getCreatedDate() + "'" +
                ", items='" + getItems() + "'" +
                ", user='" + getUser() + "'" +
                "}";
    }

}
