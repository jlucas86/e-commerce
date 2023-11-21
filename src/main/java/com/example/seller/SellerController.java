package com.example.seller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seller")
public class SellerController {

    private final SellerServive sellerServive;

    @Autowired
    public SellerController(SellerServive sellerServive) {
        this.sellerServive = sellerServive;
    }

    @GetMapping("/getSellers")
    public List<Seller> getSellers() {
        return sellerServive.getSellers();
    }

    @GetMapping("/getSeller/{sellerId}")
    public Optional<Seller> getSeller(@PathVariable("sellerId") Integer id) {
        return sellerServive.getSeller(id);
    }

    @GetMapping("/getSellerByName/{name}")
    public List<Seller> getSeller(@PathVariable("name") String name) {
        return sellerServive.getSellerByName(name);
    }

    @PostMapping("/addSeller")
    public void addSeller(@RequestBody NewSellerRequest request) {
        sellerServive.addSeller(request);
    }

    @DeleteMapping("/deleteSeller/{sellerId}")
    public void deleteSeller(@PathVariable("sellerId") Integer id) {
        sellerServive.deleteSeller(id);
    }

    @PutMapping("/updateSeller/{sellerId}")
    public void updateSeller(@RequestBody NewSellerRequest request,
            @PathVariable("sellerId") Integer id) {
        sellerServive.updateSeller(request, id);
    }
}
