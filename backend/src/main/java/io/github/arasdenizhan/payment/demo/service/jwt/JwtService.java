package io.github.arasdenizhan.payment.demo.service.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generate(UserDetails userDetails);
    boolean validateToken(UserDetails userDetails, Claims payload);
    Claims getClaims(String jwtToken);
}
