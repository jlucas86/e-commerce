package com.example.order;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exceptions.OrderDoesNotExist;
import com.example.exceptions.UserDoesNotOwnOrder;
import com.example.record.Pair;
import com.example.userInfo.UserInfo;
import com.example.userInfo.UserInfoRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserInfoRepository userInfoRepository) {
        this.orderRepository = orderRepository;
        this.userInfoRepository = userInfoRepository;
    }

    // get

    public Order getOrder(String username, Integer orderId) {
        try {
            Pair<UserInfo, Order> pair = verify(username, orderId);
            return pair.other();
        } catch (Exception e) {
            System.err.println(e.getMessage() + "++++++++++++++++++++++++++++++++++++++++++ urg");
        }
        return null;
    }

    public List<Order> getAllOrders(String username) {
        UserInfo user = userInfoRepository.findByUsername(username).get();
        return orderRepository.findAllByUserId(user.getId());

    }

    // add

    public void addOrder(String username, Order order) {
        UserInfo user = userInfoRepository.findByUsername(username).get();
        order.setUser(user);
        orderRepository.save(order);
    }

    // update

    public void updateOrder(String username, Order order) {
        try {
            verify(username, order.getId());
            orderRepository.save(order);

        } catch (Exception e) {
            System.err.println(e.getMessage() + "++++++++++++++++++++++++++++++++++++++++++ urg");
        }
    }

    // delete

    public void deleteOrder(String username, Order order) {
        try {
            verify(username, order.getId());
            orderRepository.delete(order);
        } catch (Exception e) {
            System.err.println(e.getMessage() + "++++++++++++++++++++++++++++++++++++++++++ urg");
        }
    }

    public Pair<UserInfo, Order> verify(String username, Integer orderId)
            throws OrderDoesNotExist, UserDoesNotOwnOrder {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderDoesNotExist(String.format("order %i does not exist in database", orderId));
        }

        Order order = orderRepository.findById(orderId).get();
        UserInfo user = userInfoRepository.findByUsername(username).get();

        if (order.getId() != user.getId()) {
            throw new UserDoesNotOwnOrder(String.format("User %s does not own order %i", user.getUsername(), orderId));
        }

        return new Pair<UserInfo, Order>(user, order);
    }

}
