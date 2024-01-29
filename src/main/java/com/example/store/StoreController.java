package com.example.store;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/store")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/getStore/{id}")
    public Optional<Store> getStore(@PathVariable("id") Integer id) {
        return storeService.getStore(id);
    }

    @GetMapping("/getAllStore/{id}")
    public List<Store> getAllStore(@PathVariable("id") Integer userId) {
        return storeService.getAllUserID(userId);
    }

    /*
     * for all 3 of the next functions the user must be verified as the store owner
     * 
     * to do this make sure that there is a matching quiry in the
     */

    // @PreAuthorize("#username == authentication.principal.username and
    // hasRole('ROLE_SELLER')")
    @PostMapping("/addStore/{username}")
    public void addStore(@PathVariable("username") String username, @RequestBody Store store) {
        storeService.addStore(username, store);
    }

    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_SELLER')")
    public void updateStore(@PathVariable("username") String username, @RequestBody Store store) {
        storeService.updateStore(username, store);
    }

    @PreAuthorize("#username == authentication.principal.username and hasRole('ROLE_SELLER')")
    public void deleteStore(@PathVariable("username") String username, @RequestBody Store store) {
        storeService.deleteStore(username, store);
    }

}
