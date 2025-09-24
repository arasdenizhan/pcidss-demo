package io.github.arasdenizhan.payment.demo.web.authentication;

import io.github.arasdenizhan.payment.demo.dto.authentication.AuthenticationDto;
import io.github.arasdenizhan.payment.demo.service.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping
    ResponseEntity<String> authenticate(@RequestBody AuthenticationDto authenticationDto){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationDto));
    }
}
