package io.github.arasdenizhan.payment.demo.web.authentication;

import io.github.arasdenizhan.payment.demo.dto.authentication.AuthenticationDto;
import io.github.arasdenizhan.payment.demo.dto.authentication.UserDto;
import io.github.arasdenizhan.payment.demo.service.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping
    ResponseEntity<Void> authenticate(@RequestBody AuthenticationDto authenticationDto){
        HttpHeaders httpHeaders = authenticationService.authenticate(authenticationDto);
        return ResponseEntity.ok().headers(httpHeaders).build();
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAnyAuthority('USER', 'DATABASE_ADMIN', 'PAYMENT_ADMIN')")
    ResponseEntity<Void> logout(){
        HttpHeaders httpHeaders = authenticationService.logout();
        return ResponseEntity.ok().headers(httpHeaders).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'DATABASE_ADMIN', 'PAYMENT_ADMIN')")
    ResponseEntity<UserDto> info(){
        return ResponseEntity.ok(authenticationService.info());
    }
}
