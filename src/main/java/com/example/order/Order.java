package com.example.order;

import java.util.HashSet;
import java.util.Set;

import com.example.product.Product;
import com.example.userInfo.UserInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Objects;

@Entity
public class Order {

    @Id
    @SequenceGenerator(name = "order_id_sequence", sequenceName = "order_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_sequence")
    private Integer id;
    private Integer date;
    private String status;

    @ManyToMany()
    @JoinTable(name = "order_product", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();

    @OneToMany()
    @JoinTable(name = "order_payment", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "payment_id"))
    private Set<Product> payment = new HashSet<>();

    @ManyToOne()
    private UserInfo user;

    public Order() {
    }

    public Order(Integer id, Integer date, String status, Set<Product> products) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.products = products;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDate() {
        return this.date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Order id(Integer id) {
        setId(id);
        return this;
    }

    public Order date(Integer date) {
        setDate(date);
        return this;
    }

    public Order status(String status) {
        setStatus(status);
        return this;
    }

    public Order products(Set<Product> products) {
        setProducts(products);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Order)) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(date, order.date) &&
                Objects.equals(status, order.status)
                && Objects.equals(products, order.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, status, products);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", date='" + getDate() + "'" +
                ", status='" + getStatus() + "'" +
                ", products='" + getProducts() + "'" +
                "}";
    }

}
