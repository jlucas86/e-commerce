package com.example.store;

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
import jakarta.persistence.SequenceGenerator;
import java.util.Objects;

@Entity
public class Store {

    @Id
    @SequenceGenerator(name = "store_id_sequence", sequenceName = "store_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "store_id_sequence")
    private Integer id;
    private String name;
    private String desciption;

    @ManyToMany()
    @JoinTable(name = "store_product", joinColumns = @JoinColumn(name = "store_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();

    @ManyToMany
    private Set<UserInfo> userInfo = new HashSet<>();

    public Store() {
    }

    public Store(Integer id, String name, String desciption, Set<Product> products, Set<UserInfo> userInfo) {
        this.id = id;
        this.name = name;
        this.desciption = desciption;
        this.products = products;
        this.userInfo = userInfo;
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

    public String getDesciption() {
        return this.desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<UserInfo> getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(Set<UserInfo> userInfo) {
        this.userInfo = userInfo;
    }

    public Store id(Integer id) {
        setId(id);
        return this;
    }

    public Store name(String name) {
        setName(name);
        return this;
    }

    public Store desciption(String desciption) {
        setDesciption(desciption);
        return this;
    }

    public Store products(Set<Product> products) {
        setProducts(products);
        return this;
    }

    public Store userInfo(Set<UserInfo> userInfo) {
        setUserInfo(userInfo);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Store)) {
            return false;
        }
        Store store = (Store) o;
        return Objects.equals(id, store.id) && Objects.equals(name, store.name)
                && Objects.equals(desciption, store.desciption) && Objects.equals(products, store.products)
                && Objects.equals(userInfo, store.userInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desciption, products, userInfo);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", desciption='" + getDesciption() + "'" +
                ", products='" + getProducts() + "'" +
                ", userInfo='" + getUserInfo() + "'" +
                "}";
    }

}
