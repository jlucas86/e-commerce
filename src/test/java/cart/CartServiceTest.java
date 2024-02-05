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
import com.example.product.Product;
import com.example.product.ProductRepository;
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
        UserInfo user = setUpUser("email", "username", "password", null, null);

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
        UserInfo user = setUpUser("email", "username", "password", null, null);
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
        UserInfo user = setUpUser("email", "username", "password", null, null);
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

    // update

    // delete

    // helper

    UserInfo setUpUser(String email, String username, String password, Set<Role> roles, Set<Store> stores) {
        UserInfo user = new UserInfo();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        if (roles == null) {
            roles = new HashSet<>();
        }
        user.setRoles(roles);
        if (stores == null) {
            stores = new HashSet<>();
        }
        user.setStores(stores);
        return user;
    }

    public Cart setCart(UserInfo user, List<Product> items) {
        Date date = new Date();
        date.setTime(0);

        if (items == null)
            items = new ArrayList<>();
        Cart cart = new Cart(null, date, items, user);
        return cart;
    }
}
