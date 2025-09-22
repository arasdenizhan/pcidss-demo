package io.github.arasdenizhan.payment.demo.dto.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthenticationDto {
    private String username;
    private String password;
}
