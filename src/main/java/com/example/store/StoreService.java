package com.example.store;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exceptions.InvalidStoreOwner;
import com.example.exceptions.StoreDoesNotExist;
import com.example.exceptions.UserDoesNotExist;
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

    public Store getStore(Integer id) throws StoreDoesNotExist {
        Optional<Store> store = storeRepository.findById(id);
        if (store.isPresent()) {
            Store s = store.get();
            s.setUser(null);
            return s;
        }
        throw new StoreDoesNotExist(String.format("Store %d does not exist in database", id));
    }

    public List<Store> getAllStoresUser(String username) throws UserDoesNotExist {
        if (!userInfoRepository.existsByUsername(username))
            throw new UserDoesNotExist(String.format("User %s does not exist", username));
        UserInfo u = userInfoRepository.findByUsername(username).get();

        return storeRepository.findAllByUserId(u.getId());
    }

    public void addStore(String username, Store store) {
        UserInfo user = userInfoRepository.findByUsername(username).get();
        store.setUser(user);
        storeRepository.save(store);
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

    public void validateStoreOwner(UserInfo user, Store store) throws InvalidStoreOwner, StoreDoesNotExist {
        if (storeRepository.existsById(store.getId()) == false)
            throw new StoreDoesNotExist(String.format("Store %s does not exist in database", store.getName()));
        if (user.getId() != store.getUser().getId()) {
            throw new InvalidStoreOwner(
                    String.format("Username %s does not own store %d", user.getUsername(), store.getId()));
        }
    }

}
