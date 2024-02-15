package com.example.paymentMethod;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exceptions.PaymentMethodDoesNotExist;
import com.example.exceptions.UserDoesNotOwnPaymentMethod;
import com.example.userInfo.UserInfo;
import com.example.userInfo.UserInfoRepository;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public PaymentMethodService(PaymentMethodRepository paymantMethodRepository,
            UserInfoRepository userInfoRepository) {
        this.paymentMethodRepository = paymantMethodRepository;
        this.userInfoRepository = userInfoRepository;
    }

    // get

    public PaymentMethod getPaymentMethod(String username, Integer id)
            throws PaymentMethodDoesNotExist, UserDoesNotOwnPaymentMethod {

        verify(username, id);
        PaymentMethod p = paymentMethodRepository.findById(id).get();
        p.setCardNumber("************" + p.getCardNumber().substring(12));
        p.setCvc("***");
        return p;

    }

    public List<PaymentMethod> getPaymentMethods(String username) {

        UserInfo user = userInfoRepository.findByUsername(username).get();
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAllByUserId(user.getId());
        if (paymentMethods == null)
            return null;
        for (PaymentMethod p : paymentMethods) {
            p.setCardNumber("************" + p.getCardNumber().substring(12));
            p.setCvc("***");
        }
        return paymentMethods;
    }

    // set

    public void addPaymentMethod(String username, PaymentMethod paymentMethod) {

        // check for identical payment methods
        UserInfo user = userInfoRepository.findByUsername(username).get();
        paymentMethod.setUser(user);

        paymentMethodRepository.save(paymentMethod);
    }

    // update

    public void updatePaymentMethod(String username, PaymentMethod paymentMethod)
            throws PaymentMethodDoesNotExist, UserDoesNotOwnPaymentMethod {

        verify(username, paymentMethod.getId());

        PaymentMethod p = paymentMethodRepository.findById(paymentMethod.getId()).get();
        p.setCvc(paymentMethod.getCvc());
        paymentMethodRepository.save(p);

    }

    // delete

    public void deletePaymentMethod(String username, PaymentMethod paymentMethod)
            throws PaymentMethodDoesNotExist, UserDoesNotOwnPaymentMethod {

        // this should be updated to not delete the payment method but insed just
        // disable it from being quired by a user

        verify(username, paymentMethod.getId());
        paymentMethodRepository.delete(paymentMethod);

    }

    // helper

    public void verify(String username, Integer paymentMethodId)
            throws PaymentMethodDoesNotExist, UserDoesNotOwnPaymentMethod {

        if (!paymentMethodRepository.existsById(paymentMethodId)) {
            throw new PaymentMethodDoesNotExist(String.format("PaymentMethod %d not found", paymentMethodId));
        }

        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId).get();
        UserInfo user = userInfoRepository.findByUsername(username).get();

        if (paymentMethod.getUser().getId() != user.getId()) {
            throw new UserDoesNotOwnPaymentMethod(
                    String.format("User %s does not own paymentMethod %d", username, paymentMethodId));
        }
    }

}
