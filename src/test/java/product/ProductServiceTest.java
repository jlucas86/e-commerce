package product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.product.Product;
import com.example.product.ProductRepository;
import com.example.product.ProductService;
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
        productService.createProduct("username", 1, product);

        // then
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

        verify(productRepository).save(productArgumentCaptor.capture());

        Product capturedProduct = productArgumentCaptor.getValue();
        assertEquals(capturedProduct, product);
    }

    // get

    // update

    // delete
}
