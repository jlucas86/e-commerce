package order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.exceptions.OrderDoesNotExist;
import com.example.exceptions.PaymentMethodDoesNotExist;
import com.example.exceptions.PaymentMethodsDoNotMatch;
import com.example.exceptions.ProductNotFound;
import com.example.exceptions.UserDoesNotOwnOrder;
import com.example.exceptions.UserDoesNotOwnPaymentMethod;
import com.example.order.Order;
import com.example.order.OrderRepository;
import com.example.order.OrderService;
import com.example.paymentMethod.PaymentMethod;
import com.example.paymentMethod.PaymentMethodRepository;
import com.example.paymentMethod.PaymentMethodService;
import com.example.product.Product;
import com.example.product.ProductRepository;
import com.example.record.Pair;
import com.example.store.Store;
import com.example.userInfo.UserInfo;
import com.example.userInfo.UserInfoRepository;
import com.example.userInfo.UserInfoService;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Mock
    private PaymentMethodService paymentMethodService;

    OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository, userInfoRepository, productRepository, paymentMethodRepository,
                paymentMethodService);
    }

    // add function to test ehn payment method can not be found
    @Test
    void canAddOrder() {

        // give
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        Product product = new Product(1, "thing", "thingomobbober", "does stuff", 20.56, null, null, null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        PaymentMethod paymentMethod = new PaymentMethod(1, "jessie james", "1234567891233", date, "123", user, null,
                null);
        Order order = new Order(1, date, "complete", products, null, user, paymentMethod);

        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(productRepository.existsById(product.getId())).willReturn(true);
        BDDMockito.given(paymentMethodRepository.existsById(paymentMethod.getId())).willReturn(true);
        // BDDMockito.given(orderRepository.existsById(order.getId())).willReturn(true);
        // BDDMockito.given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));

        // when
        try {
            orderService.addOrder(user.getUsername(), order);
        } catch (Exception e) {
            fail();
        }

        // then

        // ArgumentCaptor<Integer> orderIdExistsArgumentCaptor =
        // ArgumentCaptor.forClass(Integer.class);
        // verify(orderRepository).existsById(orderIdExistsArgumentCaptor.capture());
        // Integer capturedOrderIdExists = orderIdExistsArgumentCaptor.getValue();
        // assertEquals(capturedOrderIdExists, order.getId());

        // ArgumentCaptor<Integer> orderIdArgumentCaptor =
        // ArgumentCaptor.forClass(Integer.class);
        // verify(orderRepository).findById(orderIdArgumentCaptor.capture());
        // Integer capturedOrderId = orderIdArgumentCaptor.getValue();
        // assertEquals(capturedOrderId, order.getId());

        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userInfoRepository).findByUsername(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertEquals(capturedUsername, user.getUsername());

        ArgumentCaptor<Integer> productIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(productRepository).existsById(productIdArgumentCaptor.capture());
        Integer capturedProductId = productIdArgumentCaptor.getValue();
        assertEquals(capturedProductId, product.getId());

        ArgumentCaptor<Integer> paymentMethodIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(paymentMethodRepository).existsById(paymentMethodIdArgumentCaptor.capture());
        Integer capturedPaymentMethodId = paymentMethodIdArgumentCaptor.getValue();
        assertEquals(capturedPaymentMethodId, paymentMethod.getId());

    }

    // get
    @Test
    void canGetOrder() {

        // note: the returned order that goes in "o" is a shallow copy of "order"

        // give
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        Product product = new Product(1, "thing", "thingomobbober", "does stuff", 20.56, null, null, null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        PaymentMethod paymentMethod = new PaymentMethod(1, "jessie james", "1234567891233", date, "123", user, null,
                null);
        Order order = new Order(1, date, "complete", products, null, user, paymentMethod);
        Order order1 = new Order(1, date, "complete", products, null, user, paymentMethod);
        Order o = null;

        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(orderRepository.existsById(order.getId())).willReturn(true);
        BDDMockito.given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));

        // when
        try {
            o = orderService.getOrder(user.getUsername(), order.getId());
        } catch (Exception e) {
            fail();
        }

        // then

        ArgumentCaptor<Integer> orderIdExistsArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(orderRepository).existsById(orderIdExistsArgumentCaptor.capture());
        Integer capturedOrderIdExists = orderIdExistsArgumentCaptor.getValue();
        assertEquals(capturedOrderIdExists, order.getId());

        ArgumentCaptor<Integer> orderIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(orderRepository).findById(orderIdArgumentCaptor.capture());
        Integer capturedOrderId = orderIdArgumentCaptor.getValue();
        assertEquals(capturedOrderId, order.getId());

        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userInfoRepository).findByUsername(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertEquals(capturedUsername, user.getUsername());

        order1.setPaymentMethod(null);
        assertEquals(o, order1);

    }

    @Test
    void canGetOrderEnsureTheValueOfPaymentMethodIsChangedToNull() {

        // note: the returned order that goes in "o" is a shallow copy of "order"

        // give
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        Product product = new Product(1, "thing", "thingomobbober", "does stuff", 20.56, null, null, null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        PaymentMethod paymentMethod = new PaymentMethod(1, "jessie james", "1234567891233", date, "123", user, null,
                null);
        Order order = new Order(1, date, "complete", products, null, user, paymentMethod);
        Order order1 = new Order(1, date, "complete", products, null, user, paymentMethod);
        Order o = null;

        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(orderRepository.existsById(order.getId())).willReturn(true);
        BDDMockito.given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));

        // when
        try {
            o = orderService.getOrder(user.getUsername(), order.getId());
        } catch (Exception e) {
            fail();
        }

        // then

        ArgumentCaptor<Integer> orderIdExistsArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(orderRepository).existsById(orderIdExistsArgumentCaptor.capture());
        Integer capturedOrderIdExists = orderIdExistsArgumentCaptor.getValue();
        assertEquals(capturedOrderIdExists, order.getId());

        ArgumentCaptor<Integer> orderIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(orderRepository).findById(orderIdArgumentCaptor.capture());
        Integer capturedOrderId = orderIdArgumentCaptor.getValue();
        assertEquals(capturedOrderId, order.getId());

        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userInfoRepository).findByUsername(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertEquals(capturedUsername, user.getUsername());

        assertNotEquals(o.getPaymentMethod(), order1.getPaymentMethod());

    }

    @Test
    void canGetAllOrderByUserId() {

        // note: the returned order that goes in "o" is a shallow copy of "order"

        // give
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        Product product = new Product(1, "thing", "thingomobbober", "does stuff", 20.56, null, null, null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        PaymentMethod paymentMethod = new PaymentMethod(1, "jessie james", "1234567891233", date, "123", user, null,
                null);
        Order order = new Order(1, date, "complete", products, null, user, paymentMethod);
        Order order1 = new Order(2, date, "complete", products, null, user, paymentMethod);
        Order order2 = new Order(3, date, "complete", products, null, user, paymentMethod);
        Order order3 = new Order(4, date, "complete", products, null, user, paymentMethod);

        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        List<Order> o = null;

        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(orderRepository.findAllByUserId(user.getId())).willReturn(orders);

        // when
        try {
            o = orderService.getAllOrders(user.getUsername());
        } catch (Exception e) {
            fail();
        }

        // then
        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userInfoRepository).findByUsername(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertEquals(capturedUsername, user.getUsername());

        ArgumentCaptor<Integer> userIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(orderRepository).findAllByUserId(userIdArgumentCaptor.capture());
        Integer capturedUserId = userIdArgumentCaptor.getValue();
        assertEquals(capturedUserId, user.getId());

        assertEquals(o, orders);

    }

    @Test
    void canGetAllByPaymentMethod() {
        // note: the returned order that goes in "o" is a shallow copy of "order"

        // give
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        Product product = new Product(1, "thing", "thingomobbober", "does stuff", 20.56, null, null, null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        PaymentMethod paymentMethod = new PaymentMethod(1, "jessie james", "1234567891233", date, "123", user, null,
                null);
        Order order = new Order(1, date, "complete", products, null, user, paymentMethod);
        Order order1 = new Order(2, date, "complete", products, null, user, paymentMethod);
        Order order2 = new Order(3, date, "complete", products, null, user, paymentMethod);
        Order order3 = new Order(4, date, "complete", products, null, user, paymentMethod);

        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        List<Order> o = null;

        BDDMockito.given(paymentMethodRepository.existsById(paymentMethod.getId())).willReturn(true);
        BDDMockito.given(paymentMethodRepository.findById(paymentMethod.getId()))
                .willReturn(Optional.of(paymentMethod));
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(orderRepository.findAllByPaymentMethodId(paymentMethod.getId())).willReturn(orders);

        // when
        try {
            o = orderService.getAllByPaymentMethod(user.getUsername(), paymentMethod.getId());
        } catch (Exception e) {
            fail();
        }

        // then
        ArgumentCaptor<Integer> paymentMethodIdExistsArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(paymentMethodRepository).existsById(paymentMethodIdExistsArgumentCaptor.capture());
        Integer capturedPaymentMethodIdExists = paymentMethodIdExistsArgumentCaptor.getValue();
        assertEquals(capturedPaymentMethodIdExists, paymentMethod.getId());

        ArgumentCaptor<Integer> paymentMethodIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(paymentMethodRepository).existsById(paymentMethodIdArgumentCaptor.capture());
        Integer capturedPaymentMethodId = paymentMethodIdArgumentCaptor.getValue();
        assertEquals(capturedPaymentMethodId, paymentMethod.getId());

        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userInfoRepository).findByUsername(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertEquals(capturedUsername, user.getUsername());

        ArgumentCaptor<Integer> paymentMethodIdGetAllArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(orderRepository).findAllByPaymentMethodId(paymentMethodIdGetAllArgumentCaptor.capture());
        Integer capturedPaymentMethodIdgetAll = paymentMethodIdGetAllArgumentCaptor.getValue();
        assertEquals(capturedPaymentMethodIdgetAll, paymentMethod.getId());

        assertEquals(orders, o);
    }

    @Test
    void canNotGetAllByPaymentMethodPaymentMethodDoesNotExist() {
        // note: the returned order that goes in "o" is a shallow copy of "order"

        // give
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        Product product = new Product(1, "thing", "thingomobbober", "does stuff", 20.56, null, null, null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        PaymentMethod paymentMethod = new PaymentMethod(1, "jessie james", "1234567891233", date, "123", user, null,
                null);

        BDDMockito.given(paymentMethodRepository.existsById(paymentMethod.getId())).willReturn(false);

        // when
        // then

        Exception e = assertThrows(PaymentMethodDoesNotExist.class, () -> {
            orderService.getAllByPaymentMethod(user.getUsername(), paymentMethod.getId());
        }, "PaymentMethod " + paymentMethod.getId() + " not found");

        assertEquals(e.getMessage(), "PaymentMethod " + paymentMethod.getId() + " not found");
    }

    @Test
    void canNotGetAllByPaymentMethodUserDoesNotOwnPaymentMethod() {
        // note: the returned order that goes in "o" is a shallow copy of "order"

        // give
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        UserInfo user1 = new UserInfo(2, "email1", "username1", "password", null, null);
        Date date = new Date(0);
        Product product = new Product(1, "thing", "thingomobbober", "does stuff", 20.56, null, null, null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        PaymentMethod paymentMethod = new PaymentMethod(1, "jessie james", "1234567891233", date, "123", user, null,
                null);

        BDDMockito.given(paymentMethodRepository.existsById(paymentMethod.getId())).willReturn(true);
        BDDMockito.given(paymentMethodRepository.findById(paymentMethod.getId()))
                .willReturn(Optional.of(paymentMethod));
        BDDMockito.given(userInfoRepository.findByUsername(user1.getUsername())).willReturn(Optional.of(user1));

        // when
        // then

        Exception e = assertThrows(UserDoesNotOwnPaymentMethod.class, () -> {
            orderService.getAllByPaymentMethod(user1.getUsername(), paymentMethod.getId());
        }, "User " + user1.getUsername() + " does not own paymentMethod " + paymentMethod.getId());

        assertEquals(e.getMessage(),
                "User " + user1.getUsername() + " does not own paymentMethod " + paymentMethod.getId());

    }

    // update

    @Test
    void canUpdateOrder() {
        // note: the returned order that goes in "o" is a shallow copy of "order"

        // give
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        Product product = new Product(1, "thing", "thingomobbober", "does stuff", 20.56, null, null, null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        PaymentMethod paymentMethod = new PaymentMethod(1, "jessie james", "1234567891233", date, "123", user, null,
                null);
        Order order = new Order(1, date, "complete", products, null, user, paymentMethod);
        Order order1 = new Order(1, date, "complete", products, null, user, paymentMethod);
        Order o = null;

        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(orderRepository.existsById(order.getId())).willReturn(true);
        BDDMockito.given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));

        // when
        try {
            orderService.updateOrder(user.getUsername(), order);
        } catch (Exception e) {
            fail();
        }

        // then

        ArgumentCaptor<Integer> orderIdExistsArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(orderRepository).existsById(orderIdExistsArgumentCaptor.capture());
        Integer capturedOrderIdExists = orderIdExistsArgumentCaptor.getValue();
        assertEquals(capturedOrderIdExists, order.getId());

        ArgumentCaptor<Integer> orderIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(orderRepository).findById(orderIdArgumentCaptor.capture());
        Integer capturedOrderId = orderIdArgumentCaptor.getValue();
        assertEquals(capturedOrderId, order.getId());

        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userInfoRepository).findByUsername(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertEquals(capturedUsername, user.getUsername());

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order capturedOrder = orderArgumentCaptor.getValue();
        assertEquals(capturedOrder, order);

    }

    @Test
    void canNotUpdateOrderPaymentMenthodsDoNotMatch() {
        // note: the returned order that goes in "o" is a shallow copy of "order"

        // give
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        Product product = new Product(1, "thing", "thingomobbober", "does stuff", 20.56, null, null, null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        PaymentMethod paymentMethod = new PaymentMethod(1, "jessie james", "1234567891233", date, "123", user, null,
                null);
        PaymentMethod paymentMethod1 = new PaymentMethod(1, "jessie james", "1234591233", date, "123", user, null,
                null);
        Order order = new Order(1, date, "complete", products, null, user, paymentMethod);
        Order order1 = new Order(1, date, "complete", products, null, user, paymentMethod1);

        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(orderRepository.existsById(order.getId())).willReturn(true);
        BDDMockito.given(orderRepository.findById(order.getId())).willReturn(Optional.of(order1));

        // when
        // then
        Exception e = assertThrows(PaymentMethodsDoNotMatch.class, () -> {
            orderService.updateOrder(user.getUsername(), order);
        }, "PaymentMethod does not match new paymentMethod");

        assertEquals(e.getMessage(), "PaymentMethod does not match new paymentMethod");

    }

    // delete

    // helper

    @Test
    void canVerify() {

        // give
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        Product product = new Product(1, "thing", "thingomobbober", "does stuff", 20.56, null, null, null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        Order order = new Order(1, date, "complete", products, null, user, null);
        Pair<UserInfo, Order> pair = new Pair<UserInfo, Order>(user, order);
        Pair<UserInfo, Order> p = null;

        BDDMockito.given(orderRepository.existsById(order.getId())).willReturn(true);
        BDDMockito.given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        // when
        try {
            p = orderService.verify(user.getUsername(), order.getId());
        } catch (Exception e) {
            fail();
        }

        // then
        ArgumentCaptor<Integer> orderIdExistsArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(orderRepository).existsById(orderIdExistsArgumentCaptor.capture());
        Integer capturedOrderIdExists = orderIdExistsArgumentCaptor.getValue();
        assertEquals(capturedOrderIdExists, order.getId());

        ArgumentCaptor<Integer> orderIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(orderRepository).findById(orderIdArgumentCaptor.capture());
        Integer capturedOrderId = orderIdArgumentCaptor.getValue();
        assertEquals(capturedOrderId, order.getId());

        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userInfoRepository).findByUsername(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertEquals(capturedUsername, user.getUsername());

        assertEquals(pair, p);
    }

    @Test
    void canNotVerifyOrderDoesNotExist() {

        // give
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        Product product = new Product(1, "thing", "thingomobbober", "does stuff", 20.56, null, null, null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        Order order = new Order(1, date, "complete", products, null, user, null);
        Pair<UserInfo, Order> pair = new Pair<UserInfo, Order>(user, order);
        Pair<UserInfo, Order> p = null;

        BDDMockito.given(orderRepository.existsById(order.getId())).willReturn(false);

        // when
        // then
        Exception e = assertThrows(OrderDoesNotExist.class, () -> {
            orderService.verify(user.getUsername(), order.getId());
        }, "Order " + order.getId() + " does not exist in database");

        assertEquals(e.getMessage(), "Order " + order.getId() + " does not exist in database");
    }

    @Test
    void canNotVerifyUserDoesNotOwnOrder() {
        // give
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        UserInfo user1 = new UserInfo(2, "email", "username1", "password", null, null);
        Date date = new Date(0);
        Product product = new Product(1, "thing", "thingomobbober", "does stuff", 20.56, null, null, null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        Order order = new Order(1, date, "complete", products, null, user, null);
        Pair<UserInfo, Order> pair = new Pair<UserInfo, Order>(user, order);
        Pair<UserInfo, Order> p = null;

        BDDMockito.given(orderRepository.existsById(order.getId())).willReturn(true);
        BDDMockito.given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));
        BDDMockito.given(userInfoRepository.findByUsername(user1.getUsername())).willReturn(Optional.of(user1));

        // when
        // then
        Exception e = assertThrows(UserDoesNotOwnOrder.class, () -> {
            orderService.verify(user1.getUsername(), order.getId());
        }, "User " + user1.getUsername() + " does not own order " + order.getId());

        assertEquals(e.getMessage(), "User " + user1.getUsername() + " does not own order " + order.getId());
    }

    @Test
    void canVerifyProduct() {
        // give
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date(0);
        Product product = new Product(1, "thing", "thingomobbober", "does stuff", 20.56, null, null, null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        Order order = new Order(1, date, "complete", products, null, user, null);

        BDDMockito.given(productRepository.existsById(product.getId())).willReturn(true);

        // when
        try {
            orderService.verifyProducts(product);
        } catch (Exception e) {
            fail();
        }

        // then
        ArgumentCaptor<Integer> productIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(productRepository).existsById(productIdArgumentCaptor.capture());
        Integer capturedProductId = productIdArgumentCaptor.getValue();
        assertEquals(capturedProductId, product.getId());
    }

    @Test
    void canNotVerifyProductProductNotFound() {
        // give

        Product product = new Product(1, "thing", "thingomobbober", "does stuff", 20.56, null, null, null);
        List<Product> products = new ArrayList<>();
        products.add(product);

        BDDMockito.given(productRepository.existsById(product.getId())).willReturn(false);

        // when
        // then
        Exception e = assertThrows(ProductNotFound.class, () -> {
            orderService.verifyProducts(product);
        }, "Product " + product.getId() + " not found");

        assertEquals(e.getMessage(), "Product " + product.getId() + " not found");
    }

}
