package store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
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

import com.example.exceptions.InvalidStoreOwner;
import com.example.exceptions.StoreDoesNotExist;
import com.example.exceptions.UserDoesNotExist;
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
            fail();
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

    @Test
    void canGetAllStoresFromUsername() {
        Set<Product> products = new HashSet<>();
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "sells things", user, products);
        Store store1 = new Store(2, "store", "sells things", user, products);
        Store store2 = new Store(3, "store", "sells things", user, products);
        Store store4 = new Store(5, "store", "sells things", user, products);

        List<Store> stores = new ArrayList<>();
        List<Store> s = null;

        stores.add(store);
        stores.add(store1);
        stores.add(store2);
        stores.add(store4);

        BDDMockito.given(userInfoRepository.existsByUsername(user.getUsername())).willReturn(true);
        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(storeRepository.findAllByUserId(user.getId())).willReturn(stores);

        // when
        try {
            s = storeService.getAllStoresUser(user.getUsername());
        } catch (Exception e) {
            fail();
        }

        // then
        ArgumentCaptor<Integer> userIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(storeRepository).findAllByUserId(userIdArgumentCaptor.capture());
        Integer capturedUserId = userIdArgumentCaptor.getValue();
        assertEquals(user.getId(), capturedUserId);
        assertEquals(stores, s);
    }

    @Test
    void canNotGetAllStoresFromUsernameUserDoesNotExist() {
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);

        BDDMockito.given(userInfoRepository.existsByUsername(user.getUsername())).willReturn(false);

        // when
        // then
        Exception e = assertThrows(UserDoesNotExist.class, () -> {
            storeService.getAllStoresUser(user.getUsername());
        }, "User " + user.getUsername() + " does not exist");
        assertEquals(e.getMessage(), "User " + user.getUsername() + " does not exist");
    }

    // update

    @Test
    void canUpdateStore() {
        Set<Product> products = new HashSet<>();
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "sells things", user, products);

        BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(true);

        // when
        try {
            storeService.updateStore(user.getUsername(), store);
        } catch (Exception e) {
            fail();
        }

        // then
        ArgumentCaptor<Store> storeArgumentCaptor = ArgumentCaptor.forClass(Store.class);
        verify(storeRepository).save(storeArgumentCaptor.capture());
        Store capturedStore = storeArgumentCaptor.getValue();
        assertEquals(store, capturedStore);

    }

    // delete

    @Test
    void canDeleteStore() {

        /**
         * chaged value added from a store to an integer represtinting the storeID
         */

        // Set<Product> products = new HashSet<>();
        // UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        // Store store = new Store(1, "store", "sells things", user, products);

        // BDDMockito.given(userInfoRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        // BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(true);

        // // when
        // try {
        // storeService.deleteStore(user.getUsername(), store);
        // } catch (Exception e) {
        // fail();
        // }

        // // then
        // ArgumentCaptor<Store> storeArgumentCaptor =
        // ArgumentCaptor.forClass(Store.class);
        // verify(storeRepository).delete(storeArgumentCaptor.capture());
        // Store capturedStore = storeArgumentCaptor.getValue();
        // assertEquals(store, capturedStore);
    }

    // helper

    @Test
    void canValidateStoreOwner() {
        Set<Product> products = new HashSet<>();
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "sells things", user, products);

        BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(true);

        // when
        try {
            storeService.validateStoreOwner(user, store);
        } catch (Exception e) {
            fail();
        }

        // then
        ArgumentCaptor<Integer> storeIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(storeRepository).existsById(storeIdArgumentCaptor.capture());
        Integer capturedStoreId = storeIdArgumentCaptor.getValue();
        assertEquals(store.getId(), capturedStoreId);
    }

    @Test
    void canNotValidateStoreOwnerStoreDoesNotExist() {
        Set<Product> products = new HashSet<>();
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        Store store = new Store(1, "store", "sells things", user, products);

        BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(false);

        // when
        // then
        Exception e = assertThrows(StoreDoesNotExist.class, () -> {
            storeService.validateStoreOwner(user, store);
        }, "Store store does not exist in database");
        assertEquals(e.getMessage(), "Store store does not exist in database");
    }

    @Test
    void canNotValidateStoreOwnerUserDoesNotOwnStore() {
        Set<Product> products = new HashSet<>();
        UserInfo user = new UserInfo(1, "email", "username", "password", null, null);
        UserInfo user1 = new UserInfo(2, "email", "username1", "password", null, null);
        Store store = new Store(1, "store", "sells things", user, products);

        BDDMockito.given(storeRepository.existsById(store.getId())).willReturn(true);

        // when
        // then
        Exception e = assertThrows(InvalidStoreOwner.class, () -> {
            storeService.validateStoreOwner(user1, store);
        }, "Username username1 does not own store " + store.getId());
        assertEquals("Username username1 does not own store " + store.getId(), e.getMessage());
    }

}
