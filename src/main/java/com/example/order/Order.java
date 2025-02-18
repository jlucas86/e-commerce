package com.example.order;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.example.paymentMethod.PaymentMethod;
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
import jakarta.persistence.Table;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @SequenceGenerator(name = "order_id_sequence", sequenceName = "order_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_sequence")
    private Integer id;
    private Date date;
    private String status;

    @ManyToMany()
    @JoinTable(name = "order_product", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    @OneToMany()
    @JoinTable(name = "order_payment", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "payment_id"))
    private Set<Product> payment = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserInfo user;

    @ManyToOne()
    @JoinColumn(name = "paymentMethod_id")
    private PaymentMethod paymentMethod;

    public Order() {
    }

    public Order(Integer id, Date date, String status, List<Product> products, Set<Product> payment, UserInfo user,
            PaymentMethod paymentMethod) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.products = products;
        this.payment = payment;
        this.user = user;
        this.paymentMethod = paymentMethod;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Set<Product> getPayment() {
        return this.payment;
    }

    public void setPayment(Set<Product> payment) {
        this.payment = payment;
    }

    public UserInfo getUser() {
        return this.user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Order id(Integer id) {
        setId(id);
        return this;
    }

    public Order date(Date date) {
        setDate(date);
        return this;
    }

    public Order status(String status) {
        setStatus(status);
        return this;
    }

    public Order products(List<Product> products) {
        setProducts(products);
        return this;
    }

    public Order payment(Set<Product> payment) {
        setPayment(payment);
        return this;
    }

    public Order user(UserInfo user) {
        setUser(user);
        return this;
    }

    public Order paymentMethod(PaymentMethod paymentMethod) {
        setPaymentMethod(paymentMethod);
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
        return Objects.equals(id, order.id) && Objects.equals(date, order.date) && Objects.equals(status, order.status)
                && Objects.equals(products, order.products) && Objects.equals(payment, order.payment)
                && Objects.equals(user, order.user) && Objects.equals(paymentMethod, order.paymentMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, status, products, payment, user, paymentMethod);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", date='" + getDate() + "'" +
                ", status='" + getStatus() + "'" +
                ", products='" + getProducts() + "'" +
                ", payment='" + getPayment() + "'" +
                ", user='" + getUser() + "'" +
                ", paymentMethod='" + getPaymentMethod() + "'" +
                "}";
    }

}
