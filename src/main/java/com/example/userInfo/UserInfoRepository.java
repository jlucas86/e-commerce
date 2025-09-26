package com.example.userInfo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByUsername(String username);

    Page<UserInfo> findByUsername(String username, Pageable p);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String Email);
}
