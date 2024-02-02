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
        return paymentMethodService.getPaymentMethod(username, id);
    }

    @GetMapping("/{username}/getPaymentMethods")
    public List<PaymentMethod> getPaymentMethods(@PathVariable("username") String username) {
        return paymentMethodService.getPaymentMethods(username);
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
        paymentMethodService.updatePaymentMethod(username, PaymentMethod);
    }

    // delete

    @DeleteMapping("/{username}/getPaymentMethod/")
    public void deletePaymentMethod(@PathVariable("username") String username,
            @RequestBody PaymentMethod PaymentMethod) {
        paymentMethodService.deletePaymentMethod(username, PaymentMethod);
    }

}
