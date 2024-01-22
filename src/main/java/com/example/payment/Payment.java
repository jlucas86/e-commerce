package com.example.payment;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import java.util.Objects;

@Entity
public class Payment {

    @Id
    @SequenceGenerator(name = "product_id_sequence", sequenceName = "product_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_sequence")
    private Integer id;
    private Date date;
    private Double paymentNumber;

    public Payment() {
    }

    public Payment(Integer id, Date date, Double paymentNumber) {
        this.id = id;
        this.date = date;
        this.paymentNumber = paymentNumber;
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

    public Double getPaymentNumber() {
        return this.paymentNumber;
    }

    public void setPaymentNumber(Double paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public Payment id(Integer id) {
        setId(id);
        return this;
    }

    public Payment date(Date date) {
        setDate(date);
        return this;
    }

    public Payment paymentNumber(Double paymentNumber) {
        setPaymentNumber(paymentNumber);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Payment)) {
            return false;
        }
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) && Objects.equals(date, payment.date)
                && Objects.equals(paymentNumber, payment.paymentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, paymentNumber);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", date='" + getDate() + "'" +
                ", paymentNumber='" + getPaymentNumber() + "'" +
                "}";
    }

}
