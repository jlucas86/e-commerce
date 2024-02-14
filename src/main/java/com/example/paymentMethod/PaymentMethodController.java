package com.example.paymentMethod;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/paymentMethod")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @Autowired
    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    // get

    @GetMapping("/{username}/getPaymentMethod/{id}")
    public PaymentMethod getPaymentMethod(@PathVariable("username") String username,
            @PathVariable("id") Integer id) {
        try {
            return paymentMethodService.getPaymentMethod(username, id);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    @GetMapping("/{username}/getPaymentMethods")
    public List<PaymentMethod> getPaymentMethods(@PathVariable("username") String username) {

        try {
            return paymentMethodService.getPaymentMethods(username);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    // add

    @PostMapping("/{username}/getPaymentMethod/")
    public void addPaymentMethod(@PathVariable("username") String username,
            @RequestBody PaymentMethod PaymentMethod) {
        paymentMethodService.addPaymentMethod(username, PaymentMethod);
    }

    // update

    @PutMapping("/{username}/getPaymentMethod/")
    public void updatePaymentMethod(@PathVariable("username") String username,
            @RequestBody PaymentMethod PaymentMethod) {
        try {
            paymentMethodService.updatePaymentMethod(username, PaymentMethod);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    // delete

    @DeleteMapping("/{username}/getPaymentMethod/")
    public void deletePaymentMethod(@PathVariable("username") String username,
            @RequestBody PaymentMethod PaymentMethod) {
        try {
            paymentMethodService.deletePaymentMethod(username, PaymentMethod);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
