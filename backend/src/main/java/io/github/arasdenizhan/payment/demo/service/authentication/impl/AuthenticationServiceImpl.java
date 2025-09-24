package io.github.arasdenizhan.payment.demo.service.authentication.impl;

import io.github.arasdenizhan.payment.demo.dto.authentication.AuthenticationDto;
import io.github.arasdenizhan.payment.demo.exception.LoginFailedException;
import io.github.arasdenizhan.payment.demo.service.authentication.AuthenticationService;
import io.github.arasdenizhan.payment.demo.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final InMemoryUserDetailsManager userDetailsManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public String authenticate(AuthenticationDto authenticationDto) {
        UserDetails userDetails = userDetailsManager.loadUserByUsername(authenticationDto.getUsername());
        if (passwordEncoder.matches(authenticationDto.getPassword(), userDetails.getPassword())) {
            return jwtService.generate(userDetails);
        }
        throw new LoginFailedException("User credentials are wrong, authentication failed!");
    }
}
