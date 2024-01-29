package com.example.store;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (!user.getUsername().equals(username)) {
            // trow error
        }
        storeRepository.save(store);
    }

    public void deleteStore(String username, Store store) {
        UserInfo user = userInfoRepository.findByUsername(username).get();
        if (!user.getUsername().equals(username)) {
            // trow error
        }
        storeRepository.delete(store);
    }

    public List<Store> getAllUserID(Integer userId) {
        // TODO Auto-generated method stub
        return storeRepository.findAllByUserId(userId);
    }

}
