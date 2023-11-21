package com.example.security;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/csrf")
public class CsrfController {

    // public final CsrfTokenRepository csrfTokenRepository;

    @GetMapping
    public void getCsrfToken(HttpServletRequest request) {
        // https://github.com/spring-projects/spring-security/issues/12094#issuecomment-1294150717
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        csrfToken.getToken();
    }
}
