package com.example.order;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exceptions.OrderDoesNotExist;
import com.example.exceptions.PaymentMethodDoesNotExist;
import com.example.exceptions.ProductNotFound;
import com.example.exceptions.UserDoesNotOwnOrder;
import com.example.exceptions.UserDoesNotOwnPaymentMethod;
import com.example.paymentMethod.PaymentMethod;
import com.example.paymentMethod.PaymentMethodRepository;
import com.example.paymentMethod.PaymentMethodService;
import com.example.product.Product;
import com.example.product.ProductRepository;
import com.example.record.Pair;
import com.example.userInfo.UserInfo;
import com.example.userInfo.UserInfoRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserInfoRepository userInfoRepository;
    private final ProductRepository productRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodService paymentMethodService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserInfoRepository userInfoRepository,
            ProductRepository productRepository, PaymentMethodRepository paymentMethodRepository,
            PaymentMethodService paymentMethodService) {
        this.orderRepository = orderRepository;
        this.userInfoRepository = userInfoRepository;
        this.productRepository = productRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodService = paymentMethodService;
    }

    // get

    public Order getOrder(String username, Integer orderId) throws OrderDoesNotExist, UserDoesNotOwnOrder {

        Pair<UserInfo, Order> pair = verify(username, orderId);
        pair.other().setPaymentMethod(null);
        return pair.other();

    }

    public List<Order> getAllOrders(String username) {
        UserInfo user = userInfoRepository.findByUsername(username).get();
        return orderRepository.findAllByUserId(user.getId());

    }

    public List<Order> getAllByPaymentMethod(String username, Integer paymentMethodId)
            throws PaymentMethodDoesNotExist, UserDoesNotOwnPaymentMethod {

        paymentMethodService.verify(username, paymentMethodId);
        return orderRepository.findAllByPaymentMethodId(paymentMethodId);
    }

    // add

    public void addOrder(String username, Order order)
            throws OrderDoesNotExist, UserDoesNotOwnOrder, ProductNotFound, PaymentMethodDoesNotExist {
        UserInfo user = userInfoRepository.findByUsername(username).get();
        order.setUser(user);

        for (Product p : order.getProducts()) {
            verifyProducts(p);
        }

        if (!paymentMethodRepository.existsById(order.getPaymentMethod().getId()))
            throw new PaymentMethodDoesNotExist(
                    String.format("PaymentMethod %i not found", order.getPaymentMethod().getId()));
        orderRepository.save(order);

    }

    // update

    public void updateOrder(String username, Order order) throws OrderDoesNotExist, UserDoesNotOwnOrder {

        verify(username, order.getId());
        orderRepository.save(order);

    }

    // delete

    public void deleteOrder(String username, Order order) throws OrderDoesNotExist, UserDoesNotOwnOrder {

        verify(username, order.getId());
        orderRepository.delete(order);

    }

    // helper

    /**
     * verifys order exists and user ownes said order
     * 
     * @param username
     * @param orderId
     * @return
     * @throws OrderDoesNotExist
     * @throws UserDoesNotOwnOrder
     */
    public Pair<UserInfo, Order> verify(String username, Integer orderId) // add some way to validate payment method
            throws OrderDoesNotExist, UserDoesNotOwnOrder {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderDoesNotExist(String.format("Order %d does not exist in database", orderId));
        }

        Order order = orderRepository.findById(orderId).get();
        UserInfo user = userInfoRepository.findByUsername(username).get();

        if (order.getId() != user.getId()) {
            throw new UserDoesNotOwnOrder(String.format("User %s does not own order %d", user.getUsername(), orderId));
        }

        return new Pair<UserInfo, Order>(user, order);
    }

    public void verifyProducts(Product product) throws ProductNotFound {

        if (!productRepository.existsById(product.getId())) {
            throw new ProductNotFound(String.format("Product %d not found", product.getId()));
        }

    }

}
