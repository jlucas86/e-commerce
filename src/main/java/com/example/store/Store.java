package com.example.store;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Objects;

@Entity
public class Store {

    @Id
    @SequenceGenerator(name = "store_id_sequence", sequenceName = "store_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "store_id_sequence")
    private Integer id;
    private String name;
    private String description;

    // @ManyToMany() // needs chaged to one to many
    // @JoinTable(name = "store_product", joinColumns = @JoinColumn(name =
    // "store_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    // private Set<Product> products = new HashSet<>();

    @ManyToOne()
    // @JoinTable(name = "store_user", joinColumns = @JoinColumn(name = "store_id"),
    // inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JoinColumn(name = "user_id")
    private UserInfo user;

    @OneToMany(mappedBy = "store")
    private Set<Product> products = new HashSet<>();

    public Store() {
    }

    public Store(Integer id, String name, String description, UserInfo user, Set<Product> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user = user;
        this.products = products;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserInfo getUser() {
        return this.user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Store id(Integer id) {
        setId(id);
        return this;
    }

    public Store name(String name) {
        setName(name);
        return this;
    }

    public Store description(String description) {
        setDescription(description);
        return this;
    }

    public Store user(UserInfo user) {
        setUser(user);
        return this;
    }

    public Store products(Set<Product> products) {
        setProducts(products);
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
                && Objects.equals(description, store.description) && Objects.equals(user, store.user)
                && Objects.equals(products, store.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, user, products);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                ", user='" + getUser() + "'" +
                ", products='" + getProducts() + "'" +
                "}";
    }

}
