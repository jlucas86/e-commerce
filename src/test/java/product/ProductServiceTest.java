package product;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

import com.example.exceptions.CartDoesNotExist;
import com.example.exceptions.InvalidStoreOwner;
import com.example.exceptions.ProductNotFound;
import com.example.exceptions.StoreDoesNotExist;
import com.example.exceptions.StoreDoesNotOwnProduct;
import com.example.product.Product;
import com.example.product.ProductRepository;
import com.example.product.ProductService;
import com.example.record.Products;
import com.example.store.Store;
import com.example.store.StoreRepository;
import com.example.store.StoreService;
import com.example.userInfo.UserInfo;
import com.example.userInfo.UserInfoRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, storeRepository, userInfoRepository, storeService);
    }

    // add

    @Test
    void canAddProduct() {
        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "i sell things", user, null);
        Product product = new Product(1, "product", "thing", "does stuff", 2.50, store, null, null);

        // BDDMockito.given(userInfoRepository.existsByUsername(user.getUsername())).willReturn(true);
        BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(true);
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));

        // when
        try {
            productService.createProduct("username", 1, product);
        } catch (Exception e) {
            // TODO: handle exception
        }

        // then
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

        verify(productRepository).save(productArgumentCaptor.capture());

        Product capturedProduct = productArgumentCaptor.getValue();
        assertEquals(capturedProduct, product);
    }

    @Test
    void canNotAddProductStoreDoesNotExist() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "i sell things", user, null);
        Product product = new Product(1, "product", "thing", "does stuff", 2.50, store, null, null);

        BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(false);

        // when
        // then

        Exception e = assertThrows(StoreDoesNotExist.class, () -> {
            productService.createProduct(user.getUsername(), store.getId(), product);
        }, "Store " + store.getId() + " not found");
        assertEquals(e.getMessage(), "Store " + store.getId() + " not found");
    }

    @Test
    void canNotAddProductUserDoesNotOwnStore() {
        // given

        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        UserInfo user1 = new UserInfo(2, "email", "username1", "password", null, null);
        Store store = new Store(1, "store", "i sell things", user, null);
        Product product = new Product(1, "product", "thing", "does stuff", 2.50, store, null, null);

        BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(true);
        BDDMockito.given(userInfoRepository.findByUsername(user1.getUsername())).willReturn(Optional.of(user1));
        BDDMockito.given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));

        // when
        // then

        Exception e = assertThrows(InvalidStoreOwner.class, () -> {
            productService.createProduct(user1.getUsername(), store.getId(), product);
        }, "Username username1 does not own store " + store.getId());
        assertEquals(e.getMessage(), "Username username1 does not own store " + store.getId());
    }

    // get
    @Test
    void canGetProduct() {
        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "i sell things", user, null);
        Product product = new Product(1, "product", "thing", "does stuff", 2.50, store, null, null);

        // BDDMockito.given(userInfoRepository.existsByUsername(user.getUsername())).willReturn(true);
        // BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(true);
        // BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        // BDDMockito.given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));
        BDDMockito.given(productRepository.findById(product.getId())).willReturn(Optional.of(product));

        // when
        try {
            productService.getProduct(product.getId());
        } catch (Exception e) {
            // TODO: handle exception
        }

        // then
        ArgumentCaptor<Integer> productIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(productRepository).findById(productIdArgumentCaptor.capture());

        Integer capturedProductId = productIdArgumentCaptor.getValue();
        assertEquals(capturedProductId, product.getId());
    }

    @Test
    void canGetProductsSameType() {
        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "i sell things", user, null);
        Product product = new Product(1, "product", "thing", "does stuff", 2.50, store, null, null);
        Product product1 = new Product(2, "product1", "thing", "does stuff", 2.50, store, null, null);
        Product product2 = new Product(3, "product2", "thing", "does stuff", 2.50, store, null, null);
        Product product3 = new Product(4, "product3", "thing", "does stuff", 2.50, store, null, null);
        Product product4 = new Product(5, "product4", "thing", "does stuff", 2.50, store, null, null);

        List<String> l = new ArrayList<>();
        List<Product> list = new ArrayList<>();
        List<Product> list1 = new ArrayList<>();
        list1.add(product);
        l.add(product.getType());
        Products p = new Products(l, 1, 0);

        // List<Product> products = new ArrayList<Product>();
        // BDDMockito.given(userInfoRepository.existsByUsername(user.getUsername())).willReturn(true);
        // BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(true);
        // BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        // BDDMockito.given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));
        // BDDMockito.given(productRepository.findById(product.getId())).willReturn(Optional.of(product));

        BDDMockito.given(productRepository.findByTypeAndIdAfter(p.type().get(0), p.start()))
                .willReturn(Optional.of(product));

        // when
        try {
            list = productService.getProducts(p);
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("wtf");
        }

        // then
        ArgumentCaptor<String> productTypeArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> startArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(productRepository).findByTypeAndIdAfter(productTypeArgumentCaptor.capture(),
                startArgumentCaptor.capture());

        String capturedProductType = productTypeArgumentCaptor.getValue();
        Integer capturedStart = startArgumentCaptor.getValue();
        assertEquals(p.type().get(0), capturedProductType);
        assertEquals(0, capturedStart);
        assertEquals(list1, list);
    }

    // update

    @Test
    void canUpdateProduct() {
        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "i sell things", user, null);
        Product product = new Product(1, "product", "thing", "does stuff", 2.50, store, null, null);

        BDDMockito.given(productRepository.existsById(product.getId())).willReturn(true);
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(true);
        BDDMockito.given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));
        BDDMockito.given(productRepository.findById(product.getId())).willReturn(Optional.of(product));

        // when
        try {
            productService.updateProduct("username", 1, product);
        } catch (Exception e) {
            // TODO: handle exception
        }

        // then
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

        verify(productRepository).save(productArgumentCaptor.capture());

        Product capturedProduct = productArgumentCaptor.getValue();
        assertEquals(capturedProduct, product);
    }

    // delete

    @Test
    void canDeleteProduct() {
        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "i sell things", user, null);
        Product product = new Product(1, "product", "thing", "does stuff", 2.50, store, null, null);

        BDDMockito.given(productRepository.existsById(product.getId())).willReturn(true);
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(true);
        BDDMockito.given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));
        // BDDMockito.given(productRepository.findById(product.getId())).willReturn(Optional.of(product));

        // when
        try {
            productService.deleteProduct(user.getUsername(), store.getId(), product);
        } catch (Exception e) {
            // TODO: handle exception
        }

        // then
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

        verify(productRepository).delete(productArgumentCaptor.capture());

        Product capturedProduct = productArgumentCaptor.getValue();
        assertEquals(capturedProduct, product);
    }

    // helper function

    @Test
    void canVerifyProduct() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "i sell things", user, null);
        Product product = new Product(1, "product", "thing", "does stuff", 2.50, store, null, null);

        BDDMockito.given(productRepository.existsById(product.getId())).willReturn(true);
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(true);
        BDDMockito.given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));
        // BDDMockito.given(productRepository.findById(product.getId())).willReturn(Optional.of(product));

        // when
        try {
            productService.verifiedProduct(product, store.getId(), user.getUsername());
        } catch (Exception e) {
            // TODO: handle exception
        }
        // then
        assertDoesNotThrow(() -> productService.verifiedProduct(product, store.getId(), user.getUsername()));

    }

    @Test
    void canNotVerifyProductProductDoesNotExist() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "i sell things", user, null);
        Product product = new Product(1, "product", "thing", "does stuff", 2.50, store, null, null);

        BDDMockito.given(productRepository.existsById(product.getId())).willReturn(false);

        // when
        // then

        Exception e = assertThrows(ProductNotFound.class, () -> {
            productService.verifiedProduct(product, store.getId(), user.getUsername());
        }, "Product " + product.getId() + " not found");
        assertEquals(e.getMessage(), "Product " + product.getId() + " not found");
    }

    @Test
    void canNotVerifyProductStoreDoesNotExist() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "i sell things", user, null);
        Product product = new Product(1, "product", "thing", "does stuff", 2.50, store, null, null);

        BDDMockito.given(productRepository.existsById(product.getId())).willReturn(true);
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(false);

        // when
        // then

        Exception e = assertThrows(StoreDoesNotExist.class, () -> {
            productService.verifiedProduct(product, store.getId(), user.getUsername());
        }, "Store " + product.getId() + " not found");
        assertEquals(e.getMessage(), "Store " + product.getId() + " not found");
    }

    @Test
    void canNotVerifyProductStoreDoesNotOwnProduct() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "i sell things", user, null);
        Store store1 = new Store(2, "store1", "i sell things", user, null);
        Product product = new Product(1, "product", "thing", "does stuff", 2.50, store, null, null);

        BDDMockito.given(productRepository.existsById(product.getId())).willReturn(true);
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(true);
        BDDMockito.given(storeRepository.findById(store.getId())).willReturn(Optional.of(store1));

        // when
        // then

        Exception e = assertThrows(StoreDoesNotOwnProduct.class, () -> {
            productService.verifiedProduct(product, store.getId(), user.getUsername());
        }, "Store " + store1.getId() + " does not own product " + product.getId());
        assertEquals(e.getMessage(), "Store " + store.getId() + " does not own product " + product.getId());
    }

    @Test
    void canNotVerifyProductUserDoesNotOwnStore() {

        // given
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        UserInfo user1 = new UserInfo(2, "email", "username1", "password", null, null);
        Store store = new Store(1, "store", "i sell things", user, null);
        Product product = new Product(1, "product", "thing", "does stuff", 2.50, store, null, null);

        BDDMockito.given(productRepository.existsById(product.getId())).willReturn(true);
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user1));
        BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(true);
        BDDMockito.given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));

        // when
        // then

        Exception e = assertThrows(InvalidStoreOwner.class, () -> {
            productService.verifiedProduct(product, store.getId(), user.getUsername());
        }, "Username username does not own store store");
        assertEquals(e.getMessage(), "Username username1 does not own store store");
    }
}
