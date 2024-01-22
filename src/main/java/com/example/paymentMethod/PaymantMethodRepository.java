package com.example.paymentMethod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymantMethodRepository extends JpaRepository<PaymentMethod, Integer> {

}
