package store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
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

import com.example.exceptions.StoreDoesNotExist;
import com.example.product.Product;
import com.example.store.Store;
import com.example.store.StoreRepository;
import com.example.store.StoreService;
import com.example.userInfo.UserInfo;
import com.example.userInfo.UserInfoRepository;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    StoreService storeService;

    @BeforeEach
    void setUp() {
        storeService = new StoreService(storeRepository, userInfoRepository);
    }

    // add
    @Test
    void canAddStore() {
        // given
        Set<Product> products = new HashSet<>();
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(null, "store", "sells things", user, products);

        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        // when
        storeService.addStore(user.getUsername(), store);

        // then
        ArgumentCaptor<Store> storeArgumentCaptor = ArgumentCaptor.forClass(Store.class);
        verify(storeRepository).save(storeArgumentCaptor.capture());
        Store capturedStore = storeArgumentCaptor.getValue();
        assertEquals(store, capturedStore);

    }

    // get
    @Test
    void canGetStore() {
        // given
        Set<Product> products = new HashSet<>();
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "sells things", user, products);

        BDDMockito.given(storeRepository.findById(store.getId())).willReturn(Optional.of(store));

        Store s = null;
        // when
        try {
            s = storeService.getStore(store.getId());
        } catch (Exception e) {
            // TODO: handle exception
        }

        // then
        ArgumentCaptor<Integer> storeIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(storeRepository).findById(storeIdArgumentCaptor.capture());
        Integer capturedStoreId = storeIdArgumentCaptor.getValue();
        assertEquals(store.getId(), capturedStoreId);
        assertEquals(store, s); // this runs even though store should have a user and s user should be null.
                                // both values are null on inspection
    }

    @Test
    void canNotGetStore() {
        // given
        Set<Product> products = new HashSet<>();
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "sells things", user, products);

        BDDMockito.given(storeRepository.findById(store.getId())).willReturn(Optional.empty());

        // when
        // then
        Exception e = assertThrows(StoreDoesNotExist.class, () -> {
            storeService.getStore(store.getId());
        }, "Store " + store.getId() + " does not exist in database");
        assertEquals(e.getMessage(), "Store " + store.getId() + " does not exist in database");
    }

    // update

    // delete

    // helper
}
