//package com.projeto.order;
//
//import com.projeto.order.domain.enums.Perfil;
//import com.projeto.order.security.UserSS;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//@TestConfiguration
//public class SpringSecurityWebAuxTestConfig {
//
//    @Bean
//    @Primary
//    public UserDetailsService userDetailsService() {
//        User basicUser = new User("user@company.com", "password", Collections.singletonList(
//                new SimpleGrantedAuthority("ROLE_ADMIN")
//        ));
//
//        User managerUser  = new User("user@company.com", "password", Arrays.asList(
//                new SimpleGrantedAuthority("ROLE_ADMIN"),
//                new SimpleGrantedAuthority("ROLE_PESSOA_FISICA")
//        ));
//
//        return new InMemoryUserDetailsManager(Arrays.asList(
//                basicUser, managerUser
//        ));
//    }
//}