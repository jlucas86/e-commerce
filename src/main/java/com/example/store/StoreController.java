package com.example.store;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public Store getStore(@PathVariable("id") Integer id) {
        try {
            return storeService.getStore(id);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    // @PreAuthorize("#username == authentication.principal.username")
    @GetMapping("/getAllStore/{username}")
    // @PreAuthorize("#username == authentication.principal.username")
    public List<Store> getAllStoresUser(@PathVariable("username") String username) {
        try {
            return storeService.getAllStoresUser(username);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
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

    @PutMapping("/updateStore/{username}")
    // @PreAuthorize("#username == authentication.principal.username and
    // hasRole('ROLE_SELLER')")
    public void updateStore(@PathVariable("username") String username, @RequestBody Store store) {
        try {
            storeService.updateStore(username, store);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @DeleteMapping("/deleteStore/{username}/{storeId}")
    // @PreAuthorize("#username == authentication.principal.username and
    // hasRole('ROLE_SELLER')")
    public void deleteStore(@PathVariable("username") String username, @PathVariable("storeId") Integer storeId) {
        try {
            storeService.deleteStore(username, storeId);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
