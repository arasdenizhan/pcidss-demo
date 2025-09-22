package io.github.arasdenizhan.payment.demo.service.authentication;

import io.github.arasdenizhan.payment.demo.dto.authentication.AuthenticationDto;

public interface AuthenticationService {
    String authenticate(AuthenticationDto authenticationDto);
}
