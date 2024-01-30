package com.example.store;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exceptions.InvalidStoreOwner;
import com.example.exceptions.StoreDoesNotExist;
import com.example.userInfo.UserInfo;
import com.example.userInfo.UserInfoRepository;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository, UserInfoRepository userInfoRepository) {
        this.storeRepository = storeRepository;
        this.userInfoRepository = userInfoRepository;
    }

    public Optional<Store> getStore(Integer id) {
        return storeRepository.findById(id);
    }

    public void addStore(String username, Store store) {
        // UserInfo user = userInfoRepository.findByUsername(username).get();

        UserInfo user = userInfoRepository.findByUsername(username).get();
        store.setUser(user);
        // Set<Store> stores = user.getStores();
        // stores.add(store);
        storeRepository.save(store);

        // store.setUserInfo(user);
        // storeRepository.save(store);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^" + user.getStores());
        // Set<Store> stores = user.getStores();
        // stores.add(store);
        // userInfoRepository.save(user);
        // UserInfo user2 = userInfoRepository.findByUsername(username).get();
        // Set<UserInfo> stores = new HashSet<UserInfo>();
        // user2.setStores(storeRepository.findById(store.getId()).get());
    }

    public void updateStore(String username, Store store) {
        UserInfo user = userInfoRepository.findByUsername(username).get();
        try {
            validateStoreOwner(user, store);
            // (mayber???? make sure store names match)
            storeRepository.save(store);
        } catch (Exception e) {
            System.err.println(e.getMessage() + "++++++++++++++++++++++++++++++++++++++++++ urg");
        }

    }

    public void deleteStore(String username, Store store) {
        UserInfo user = userInfoRepository.findByUsername(username).get();
        try {
            validateStoreOwner(user, store);
            storeRepository.delete(store);
        } catch (Exception e) {
            System.err.println(e.getMessage() + "++++++++++++++++++++++++++++++++++++++++++ urg");
        }

    }

    public List<Store> getAllUserID(Integer userId) {
        // TODO Auto-generated method stub
        return storeRepository.findAllByUserId(userId);
    }

    public void validateStoreOwner(UserInfo user, Store store) throws InvalidStoreOwner, StoreDoesNotExist {
        if (storeRepository.existsById(store.getId()) == false)
            throw new StoreDoesNotExist(String.format("Store %s does not exist in database", store.getName()));
        if (user.getId() != store.getUser().getId()) {
            throw new InvalidStoreOwner(
                    String.format("Username %s does not own store %s", user.getUsername(), store.getName()));
        }
    }

}
