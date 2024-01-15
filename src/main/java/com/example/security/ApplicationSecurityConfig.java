package com.example.security;

import org.hibernate.dialect.PostgreSQLDriverKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.auth.ApplicationUser;
// import com.example.auth.ApplicationUser;
import com.example.auth.ApplicationUserService;
import com.example.permission.Permission;
import com.example.role.Role;
import com.example.seller.Seller;
import com.example.userInfo.UserInfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.springframework.security.config.Customizer.withDefaults;

import static com.example.security.ApplicationUserRole.*;
import static com.example.security.ApplicationUserPermission.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import javax.sql.DataSource;

// import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ApplicationSecurityConfig {

        private final PasswordEncoder passwordEncoder;
        // private final ApplicationUserService applicationUserService;

        @Autowired
        ApplicationUserService applicationUserService;

        @Autowired
        public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
                        ApplicationUserService applicationUserService) {
                this.passwordEncoder = passwordEncoder;
                this.applicationUserService = applicationUserService;
        }

        @Bean
        public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
                // CsrfTokenRequestAttributeHandler requestHandler = new
                // CsrfTokenRequestAttributeHandler();
                // // set the name of the attribute the CsrfToken will be populated on
                // requestHandler.setCsrfRequestAttributeName("_csrf");
                http
                                .csrf(csrf -> csrf.disable())
                                // .csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler))
                                .cors(cors -> cors.disable())
                                // .csrf((csrf) ->
                                // csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                                // //BREACH
                                // // .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())) // no
                                // BREACH
                                // .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler()))
                                .authorizeHttpRequests((authorize) -> authorize
                                                .requestMatchers("/").permitAll()
                                                .requestMatchers("index").permitAll()
                                                .requestMatchers("/css/*").permitAll()
                                                .requestMatchers("/js/*").permitAll()
                                                .requestMatchers("/greeting").permitAll()
                                                .requestMatchers("/home").permitAll()
                                                .requestMatchers("/api/v1/user/addUser").permitAll()
                                // .requestMatchers("/csrf").permitAll()
                                // .requestMatchers("/admin/**").hasRole(ADMIN.name())
                                // .requestMatchers(HttpMethod.DELETE, "/admin/**")
                                // .hasAuthority(PRODUCT_WRITE.getPermission())
                                // .requestMatchers(HttpMethod.POST, "/admin/**")
                                // .hasAuthority(PRODUCT_WRITE.getPermission())
                                // .requestMatchers(HttpMethod.PUT, "/admin/**")
                                // .hasAuthority(PRODUCT_WRITE.getPermission())
                                // .requestMatchers(HttpMethod.GET, "/admin/**")
                                // .hasAuthority(PRODUCT_READ.getPermission())
                                // .requestMatchers("/admin/**").permitAll()
                                // .anyRequest().authenticated()
                                )
                                // .httpBasic(withDefaults())
                                .formLogin(form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/home",
                                                true))
                                .rememberMe((remember) -> remember.rememberMeServices(/* rememberMeServices */null)/*
                                                                                                                    * .tokenValiditySeconds
                                                                                                                    * (
                                                                                                                    * 0)
                                                                                                                    */)
                                .logout((logout) -> logout.logoutUrl("/logout").clearAuthentication(true)
                                                .invalidateHttpSession(true).deleteCookies("remember-me", "JSESSIONID")
                                                .logoutSuccessUrl("/login"));

                // .authorizeHttpRequests((authz)->{
                // authz
                // .anyRequest()
                // .authenticated()
                // .
                // })
                // .httpBasic(withDefaults());
                return http.build();
        }

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider() {
                DaoAuthenticationProvider authProvieder = new DaoAuthenticationProvider();

                authProvieder.setUserDetailsService(applicationUserService);
                authProvieder.setPasswordEncoder(passwordEncoder);

                return authProvieder;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
                return authConfig.getAuthenticationManager();
        }

        // @Bean
        // public UserDetailsService users() {

        // Permission customer_read = new Permission(0, CUSTOMER_READ);

        // Role admin = new Role(0, ADMIN, Set.of(customer_read));

        // User jim = new User(0, "email", "jim", passwordEncoder.encode("password"),
        // Set.of(admin));

        // ApplicationUser test = new ApplicationUser(ADMIN.getGrantedAuthorities(),
        // jim.getId(), jim.getEmail(),
        // jim.getUsername(), jim.getPassword(), true, true, true, true);

        // UserDetails seller = User.builder()
        // .username("seller")
        // .password(passwordEncoder.encode("password"))
        // // .roles(SELLER.name())
        // .authorities(SELLER.getGrantedAuthorities())
        // .build();
        // UserDetails customer = User.builder()
        // .username("customer")
        // .password(passwordEncoder.encode("password"))
        // // .roles(CUSTOMER.name())
        // .authorities(CUSTOMER.getGrantedAuthorities())
        // .build();
        // UserDetails admin = User.builder()
        // .username("admin")
        // .password(passwordEncoder.encode("password"))
        // // .roles(ADMIN.name())
        // .authorities(ADMIN.getGrantedAuthorities())
        // .build();
        // return new InMemoryUserDetailsManager(seller, customer, admin);
        // }

        // @Bean
        // public DaoAuthenticationProvider daoAuthenticationProvider() {
        // DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // provider.setPasswordEncoder(passwordEncoder);
        // provider.setUserDetailsService(applicationUserService);
        // CUSTOMER.getGrantedAuthorities();

        // return provider;
        // }

        // @Bean
        // public UserDetailsService users() {
        // UserDetails seller = User.builder()
        // .username("seller")
        // .password(passwordEncoder.encode("password"))
        // // .roles(SELLER.name())
        // .authorities(SELLER.getGrantedAuthorities())
        // .build();
        // UserDetails customer = User.builder()
        // .username("customer")
        // .password(passwordEncoder.encode("password"))
        // // .roles(CUSTOMER.name())
        // .authorities(CUSTOMER.getGrantedAuthorities())
        // .build();
        // UserDetails admin = User.builder()
        // .username("admin")
        // .password(passwordEncoder.encode("password"))
        // // .roles(ADMIN.name())
        // .authorities(ADMIN.getGrantedAuthorities())
        // .build();
        // return new InMemoryUserDetailsManager(seller, customer, admin);
        // }

        // @Bean
        // public UserDetailsService users() {
        // // The builder will ensure the passwords are encoded before saving in memory
        // UserBuilder users = User..withDefaultPasswordEncoder();
        // UserDetails user = users
        // .username("user")
        // .password("password")
        // .roles("USER")
        // .build();
        // UserDetails admin = users
        // .username("admin")
        // .password("password")
        // .roles("USER", "ADMIN")
        // .build();
        // return new InMemoryUserDetailsManager(user, admin);
        // }

        // @Bean
        // public UserDetailsService users() {

        // UserDetails user = User.builder()

        // // User u = new User();
        // // u.setName("jimmy");
        // // u.setPassword("password");
        // // List<String> r = new ArrayList<>();
        // // r.add("SELLER");
        // // u.setRoles(r);

        // // UserDetailsService

        // // UserDetails u = new
        // org.springframework.security.core.userdetails.User("jimmy", "password",
        // null);
        // // return new InMemoryUserDetailsManager(u);

        // }

}

// from
// https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html#csrf-integration-javascript-spa

final class SpaCsrfTokenRequestHandler extends
                CsrfTokenRequestAttributeHandler {
        private final CsrfTokenRequestHandler delegate = new XorCsrfTokenRequestAttributeHandler();

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response,
                        Supplier<CsrfToken> csrfToken) {
                /*
                 * Always use XorCsrfTokenRequestAttributeHandler to provide BREACH protection
                 * of
                 * the CsrfToken when it is rendered in the response body.
                 */
                this.delegate.handle(request, response, csrfToken);
        }

        @Override
        public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
                /*
                 * If the request contains a request header, use
                 * CsrfTokenRequestAttributeHandler
                 * to resolve the CsrfToken. This applies when a single-page application
                 * includes
                 * the header value automatically, which was obtained via a cookie containing
                 * the
                 * raw CsrfToken.
                 */
                if (StringUtils.hasText(request.getHeader(csrfToken.getHeaderName()))) {
                        return super.resolveCsrfTokenValue(request, csrfToken);
                }
                /*
                 * In all other cases (e.g. if the request contains a request parameter), use
                 * XorCsrfTokenRequestAttributeHandler to resolve the CsrfToken. This applies
                 * when a server-side rendered form includes the _csrf request parameter as a
                 * hidden input.
                 */
                return this.delegate.resolveCsrfTokenValue(request, csrfToken);
        }
}

final class CsrfCookieFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                        HttpServletResponse response,
                        FilterChain filterChain)
                        throws ServletException, IOException {
                CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
                // Render the token value to a cookie by causing the deferred token to be
                // loaded
                csrfToken.getToken();

                filterChain.doFilter(request, response);
        }
}
