package com.example.userInfo.authentication;



import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.role.Role;
import com.example.role.RoleService;
import com.example.userInfo.UserInfo;
import com.example.userInfo.UserInfoService;

@Service("securityService")
public class SecurityService {
    
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    RoleService roleService;
    Logger logger = LoggerFactory.getLogger(SecurityService.class);
    Authentication authentication;

    public boolean isUser(String username){
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName().equals(username); // || user has role of adin;
    }

    public boolean hasAuth(){

        Optional<Role> r = roleService.getRole(1);
        if(!r.isPresent()){
            // throw error for role not found
            return false;
        }
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo u = (UserInfo) authentication.getPrincipal();
        return u.getRoles().contains(r.get());
    }

}
