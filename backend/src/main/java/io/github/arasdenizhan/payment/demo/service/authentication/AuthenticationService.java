package io.github.arasdenizhan.payment.demo.service.authentication;

import io.github.arasdenizhan.payment.demo.dto.authentication.AuthenticationDto;
import io.github.arasdenizhan.payment.demo.dto.authentication.UserDto;
import org.springframework.http.HttpHeaders;

public interface AuthenticationService {
    HttpHeaders authenticate(AuthenticationDto authenticationDto);
    HttpHeaders logout();
    UserDto info();
}
