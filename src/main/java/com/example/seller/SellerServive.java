package com.example.seller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServive {

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerServive(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public List<Seller> getSellers() {

        return sellerRepository.findAll();
    }

    public Optional<Seller> getSeller(Integer id) {

        // Seller seller = new Seller();
        // seller.setName("ahhh");
        // return seller; sellerRepository.
        return sellerRepository.findById(id);
    }

    public void addSeller(NewSellerRequest request) {
        Seller seller = new Seller();
        seller.setName(request.name());
        sellerRepository.save(seller);
    }

    public void deleteSeller(Integer id) {
        if (sellerRepository.existsById(id))
            sellerRepository.deleteById(id);

    }

    public void updateSeller(NewSellerRequest request, Integer id) {
        Seller seller = new Seller();

        if (sellerRepository.existsById(id)) {
            seller.setName(request.name());
            seller.setId(id);

            sellerRepository.save(seller);
        } else {
            System.err.println("falled to find matching id");
        }

    }

    List<Seller> getSellerByName(String name) {
        return sellerRepository.findByName(name);
    }

}