package com.example.auth;

import static com.example.security.ApplicationUserRole.ADMIN;
import static com.example.security.ApplicationUserRole.CUSTOMER;
import static com.example.security.ApplicationUserRole.SELLER;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

@Repository("fake")
public class EcommerceApplicaionUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EcommerceApplicaionUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationByUsername(String username) {
        return getApplicaionUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicaionUsers() {
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(ADMIN.getGrantedAuthorities(), passwordEncoder.encode("password"),
                        "admin", true,
                        true, true, true),
                new ApplicationUser(CUSTOMER.getGrantedAuthorities(), passwordEncoder.encode("password"),
                        "customer", true,
                        true, true, true),
                new ApplicationUser(SELLER.getGrantedAuthorities(), passwordEncoder.encode("password"),
                        "seller", true,
                        true, true, true));
        return applicationUsers;
    }

}
