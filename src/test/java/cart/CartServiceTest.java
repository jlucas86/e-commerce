package cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.cart.Cart;
import com.example.cart.CartRepository;
import com.example.cart.CartService;
import com.example.exceptions.CartDoesNotExist;
import com.example.exceptions.CartDoesNotMatchUser;
import com.example.product.Product;
import com.example.product.ProductRepository;
import com.example.record.Pair;
import com.example.role.Role;
import com.example.store.Store;
import com.example.userInfo.UserInfo;
import com.example.userInfo.UserInfoRepository;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private ProductRepository productRepository;

    CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = new CartService(cartRepository, userInfoRepository, productRepository);
    }

    // add

    @Test
    void canAddCart() {

        // given
        UserInfo user = setUpUser(1, "email", "username", "password", null, null);

        Cart cart = setCart(user, null);

        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        // when
        cartService.addCart(user.getUsername(), cart);

        // then
        ArgumentCaptor<Cart> cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);

        verify(cartRepository).save(cartArgumentCaptor.capture());

        Cart capturedCart = cartArgumentCaptor.getValue();
        assertEquals(capturedCart, cart);
    }

    // get

    @Test
    void canGetCart() {

        // given
        UserInfo user = setUpUser(1, "email", "username", "password", null, null);
        Cart cart = setCart(user, null);
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(cartRepository.existsById(cart.getId())).willReturn(true);
        BDDMockito.given(cartRepository.findById(cart.getId())).willReturn(Optional.of(cart));

        // when
        try {
            cartService.getCart(user.getUsername(), cart.getId());
        } catch (Exception e) {
            // TODO: handle exception
        }

        // then

        ArgumentCaptor<Integer> cartArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        // ArgumentCaptor<Integer> cartArgumentCaptor =
        // ArgumentCaptor.forClass(Integer.class);
        // ArgumentCaptor<Integer> cartArgumentCaptor =
        // ArgumentCaptor.forClass(Integer.class);

        verify(cartRepository).findById(cartArgumentCaptor.capture());

        // Cart capturedCart = cartArgumentCaptor.getValue();
        assertEquals(cartArgumentCaptor.getValue(), cart.getId());
    }

    @Test
    void canNotGetCartCartDoesNotExist() {

        // given
        UserInfo user = setUpUser(1, "email", "username", "password", null, null);
        Cart cart = setCart(user, null);
        // BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(cartRepository.existsById(cart.getId())).willReturn(false);
        // BDDMockito.given(cartRepository.findById(cart.getId())).willReturn(Optional.of(cart));

        // when
        // cartService.getCart(user.getUsername(), cart.getId());

        // then

        Exception e = assertThrows(CartDoesNotExist.class, () -> {
            cartService.getCart(user.getUsername(), cart.getId());
        }, "Cart " + cart.getId() + " does not exist");
        assertEquals(e.getMessage(), "Cart " + cart.getId() + " does not exist");
    }

    @Test
    void canNotGetCartCartDoesNotMatchUser() {

        // given
        UserInfo user = setUpUser(1, "email", "username", "password", null, null);
        UserInfo user1 = setUpUser(2, "email", "username", "password", null, null);
        user1.setId(2);
        Cart cart = setCart(user, null);
        BDDMockito.given(userInfoRepository.findByUsername(user1.getUsername())).willReturn(Optional.of(user1));
        BDDMockito.given(cartRepository.existsById(cart.getId())).willReturn(true);
        BDDMockito.given(cartRepository.findById(cart.getId())).willReturn(Optional.of(cart));

        // when
        // cartService.getCart(user.getUsername(), cart.getId());

        // then

        Exception e = assertThrows(CartDoesNotMatchUser.class, () -> {
            cartService.getCart(user1.getUsername(), cart.getId());
        }, "User " + user.getUsername() + " does not own cart " + cart.getId());
        assertEquals(e.getMessage(), "User " + user.getUsername() + " does not own cart " + cart.getId());
    }

    // update

    @Test
    void canUpdateCart() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date();
        date.setTime(0);
        Cart cart = new Cart(1, date, null, user);
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(cartRepository.existsById(cart.getId())).willReturn(true);
        BDDMockito.given(cartRepository.findById(cart.getId())).willReturn(Optional.of(cart));

        // when
        try {
            cartService.updateCart(user.getUsername(), cart);
        } catch (Exception e) {
            // TODO: handle exception
        }

        // then
        ArgumentCaptor<Cart> cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(cartArgumentCaptor.capture());
        Cart capturedCart = cartArgumentCaptor.getValue();
        assertEquals(capturedCart, cart);
    }

    // delete

    @Test
    void canDeleteCart() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date();
        date.setTime(0);
        Cart cart = new Cart(1, date, null, user);
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(cartRepository.existsById(cart.getId())).willReturn(true);
        BDDMockito.given(cartRepository.findById(cart.getId())).willReturn(Optional.of(cart));

        // when
        try {
            cartService.deleteCart(user.getUsername(), cart);
        } catch (Exception e) {
            // TODO: handle exception
        }

        // then
        ArgumentCaptor<Cart> cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).delete(cartArgumentCaptor.capture());
        Cart capturedCart = cartArgumentCaptor.getValue();
        assertEquals(capturedCart, cart);
    }

    // helper

    @Test
    void canVerify() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date();
        date.setTime(0);
        Cart cart = new Cart(1, date, null, user);

        Pair<UserInfo, Cart> pair = new Pair<>(user, cart);

        Pair<UserInfo, Cart> p = null;

        BDDMockito.given(cartRepository.existsById(cart.getId())).willReturn(true);
        BDDMockito.given(cartRepository.findById(cart.getId())).willReturn(Optional.of(cart));
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        // when
        try {
            p = cartService.verify(user.getUsername(), cart.getId());
        } catch (Exception e) {
            // TODO: handle exception
        }

        // then
        assertEquals(pair, p);
    }

    @Test
    void canNotVerifyCartDoesNotExist() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Date date = new Date();
        date.setTime(0);
        Cart cart = new Cart(1, date, null, user);

        BDDMockito.given(cartRepository.existsById(cart.getId())).willReturn(false);

        // when
        // then
        Exception e = assertThrows(CartDoesNotExist.class, () -> {
            cartService.verify(user.getUsername(), cart.getId());
        }, "Cart " + cart.getId() + " does not exist");
        assertEquals(e.getMessage(), "Cart " + cart.getId() + " does not exist");
    }

    @Test
    void canNotVerifyCartDoesNotMatchUser() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        UserInfo user1 = new UserInfo(2, "email1", "username1", "password", null, null);
        Date date = new Date();
        date.setTime(0);
        Cart cart = new Cart(1, date, null, user);

        BDDMockito.given(cartRepository.existsById(cart.getId())).willReturn(true);
        BDDMockito.given(cartRepository.findById(cart.getId())).willReturn(Optional.of(cart));
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user1));

        // when
        // then
        Exception e = assertThrows(CartDoesNotMatchUser.class, () -> {
            cartService.verify(user.getUsername(), cart.getId());
        }, "User " + user1.getUsername() + " does not own cart " + cart.getId());
        assertEquals(e.getMessage(), "User " + user1.getUsername() + " does not own cart " + cart.getId());
    }
}
