package io.github.arasdenizhan.payment.demo.web.test;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<String> testUser(){
        return ResponseEntity.ok("Hello user!");
    }

    @GetMapping("/database-admin")
    @PreAuthorize("hasAuthority('DATABASE_ADMIN')")
    ResponseEntity<String> testDbAdmin(){
        return ResponseEntity.ok("Hello db admin!");
    }

    @GetMapping("/payment-admin")
    @PreAuthorize("hasAuthority('PAYMENT_ADMIN')")
    ResponseEntity<String> testPaymentAdmin(){
        return ResponseEntity.ok("Hello payment admin!");
    }
}
