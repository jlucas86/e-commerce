package com.example.userInfo.authentication;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.userInfo.UserInfoService;

@Service("securityService")
public class SecurityService {
    
    @Autowired
    UserInfoService userInfoService;
    Logger logger = LoggerFactory.getLogger(SecurityService.class);
    Authentication authentication;

    public boolean isUser(String username){
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName().equals(username); // || user has role of adin;
    }

}
