package product;

import com.example.product.ProductRepository;
import com.example.product.ProductService;
import com.example.store.StoreRepository;
import com.example.store.StoreService;
import com.example.userInfo.UserInfoRepository;

public class ProductServiceTest {

    ProductRepository productRepository;

    StoreService storeService;

    StoreRepository storeRepository;

    UserInfoRepository userInfoRepository;

    ProductService productService;

    void setUp() {
        productService = new ProductService(productRepository, storeRepository, userInfoRepository, storeService);
    }
}
