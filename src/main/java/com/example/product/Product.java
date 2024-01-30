package com.example.product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

import java.util.Objects;

import com.example.store.Store;

@Entity
public class Product {

    @Id
    @SequenceGenerator(name = "product_id_sequence", sequenceName = "product_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_sequence")
    private Integer id;
    private String name;
    private String type;
    private String description;
    private Double price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private Store storeOwner;

    public Product() {
    }

    public Product(Integer id, String name, String type, String description, Double price, Store storeOwner) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.storeOwner = storeOwner;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Store getStoreOwner() {
        return this.storeOwner;
    }

    public void setStoreOwner(Store storeOwner) {
        this.storeOwner = storeOwner;
    }

    public Product id(Integer id) {
        setId(id);
        return this;
    }

    public Product name(String name) {
        setName(name);
        return this;
    }

    public Product type(String type) {
        setType(type);
        return this;
    }

    public Product description(String description) {
        setDescription(description);
        return this;
    }

    public Product price(Double price) {
        setPrice(price);
        return this;
    }

    public Product storeOwner(Store storeOwner) {
        setStoreOwner(storeOwner);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name)
                && Objects.equals(type, product.type) && Objects.equals(description, product.description)
                && Objects.equals(price, product.price) && Objects.equals(storeOwner, product.storeOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, description, price, storeOwner);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", type='" + getType() + "'" +
                ", description='" + getDescription() + "'" +
                ", price='" + getPrice() + "'" +
                ", storeOwner='" + getStoreOwner() + "'" +
                "}";
    }

}
