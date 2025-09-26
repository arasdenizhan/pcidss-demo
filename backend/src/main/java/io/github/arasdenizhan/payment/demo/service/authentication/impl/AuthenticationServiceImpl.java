package io.github.arasdenizhan.payment.demo.service.authentication.impl;

import io.github.arasdenizhan.payment.demo.dto.authentication.AuthenticationDto;
import io.github.arasdenizhan.payment.demo.dto.authentication.UserDto;
import io.github.arasdenizhan.payment.demo.exception.LoginFailedException;
import io.github.arasdenizhan.payment.demo.service.authentication.AuthenticationService;
import io.github.arasdenizhan.payment.demo.service.jwt.JwtService;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.arasdenizhan.payment.demo.service.jwt.impl.JwtServiceImpl.JWT_COOKIE_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final InMemoryUserDetailsManager userDetailsManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public HttpHeaders authenticate(AuthenticationDto authenticationDto) {
        UserDetails userDetails = userDetailsManager.loadUserByUsername(authenticationDto.getUsername());
        if (passwordEncoder.matches(authenticationDto.getPassword(), userDetails.getPassword())) {
            String generatedJwt = jwtService.generate(userDetails);
            HttpHeaders httpHeaders = new HttpHeaders();
            ResponseCookie cookie = ResponseCookie.from(JWT_COOKIE_NAME, generatedJwt)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(1800000)
                    .sameSite("None")
                    //.sameSite("Strict")   // TODO: CSRF OPEN
                    .build();
            httpHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());
            return httpHeaders;
        }
        throw new LoginFailedException("User credentials are wrong, authentication failed!");
    }

    @Override
    public HttpHeaders logout() {
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseCookie cookie = ResponseCookie.from(JWT_COOKIE_NAME, Strings.EMPTY)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                //.sameSite("Strict")   // TODO: CSRF OPEN
                .build();
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());
        return httpHeaders;
    }

    @Override
    public UserDto info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return UserDto.builder()
                .username(principal.getUsername())
                .roles(roles)
                .build();
    }
}
