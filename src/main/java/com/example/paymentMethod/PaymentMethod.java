package com.example.paymentMethod;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.payment.Payment;
import com.example.userInfo.UserInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Objects;

@Entity
public class PaymentMethod {

    @Id
    @SequenceGenerator(name = "product_id_sequence", sequenceName = "product_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_sequence")
    private Integer id;
    private String nameOnCard;
    private Double cardNumber;
    private Date date;
    private Integer cvc;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    @OneToMany(mappedBy = "paymentMethod")
    private Set<Payment> payments = new HashSet<>();

    public PaymentMethod() {
    }

    public PaymentMethod(Integer id, String nameOnCard, Double cardNumber, Date date, Integer cvc, UserInfo user,
            Set<Payment> payments) {
        this.id = id;
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.date = date;
        this.cvc = cvc;
        this.user = user;
        this.payments = payments;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameOnCard() {
        return this.nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public Double getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(Double cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCvc() {
        return this.cvc;
    }

    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }

    public UserInfo getUser() {
        return this.user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Set<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public PaymentMethod id(Integer id) {
        setId(id);
        return this;
    }

    public PaymentMethod nameOnCard(String nameOnCard) {
        setNameOnCard(nameOnCard);
        return this;
    }

    public PaymentMethod cardNumber(Double cardNumber) {
        setCardNumber(cardNumber);
        return this;
    }

    public PaymentMethod date(Date date) {
        setDate(date);
        return this;
    }

    public PaymentMethod cvc(Integer cvc) {
        setCvc(cvc);
        return this;
    }

    public PaymentMethod user(UserInfo user) {
        setUser(user);
        return this;
    }

    public PaymentMethod payments(Set<Payment> payments) {
        setPayments(payments);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PaymentMethod)) {
            return false;
        }
        PaymentMethod paymentMethod = (PaymentMethod) o;
        return Objects.equals(id, paymentMethod.id) && Objects.equals(nameOnCard, paymentMethod.nameOnCard)
                && Objects.equals(cardNumber, paymentMethod.cardNumber) && Objects.equals(date, paymentMethod.date)
                && Objects.equals(cvc, paymentMethod.cvc) && Objects.equals(user, paymentMethod.user)
                && Objects.equals(payments, paymentMethod.payments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameOnCard, cardNumber, date, cvc, user, payments);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", nameOnCard='" + getNameOnCard() + "'" +
                ", cardNumber='" + getCardNumber() + "'" +
                ", date='" + getDate() + "'" +
                ", cvc='" + getCvc() + "'" +
                ", user='" + getUser() + "'" +
                ", payments='" + getPayments() + "'" +
                "}";
    }

}
