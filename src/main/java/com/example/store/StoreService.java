package com.example.store;

import java.util.Optional;

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
        UserInfo user = userInfoRepository.findByUsername(username).get();
        if (!user.getUsername().equals(username)) {
            // trow error
        }
        storeRepository.save(store);
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

}
