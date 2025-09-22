package io.github.arasdenizhan.payment.demo.service.jwt.impl;

import io.github.arasdenizhan.payment.demo.config.properties.JwtProperties;
import io.github.arasdenizhan.payment.demo.service.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Clock;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static io.github.arasdenizhan.payment.demo.config.properties.JwtProperties.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private static final String ROLES_STRING = "roles";
    private static final String ROLE_PREFIX = "^ROLE_";
    private final JwtProperties jwtProperties;
    private final Clock clock;

    @Override
    public String generate(UserDetails userDetails) {
        Date date = Date.from(Instant.now(clock));
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(date)
                .expiration(new Date(date.getTime() + jwtProperties.getValidity()))
                .claim(ROLES_STRING, getAuthorities(userDetails.getAuthorities()))
                .signWith(Objects.requireNonNull(getPrivateKey(), "Key couldn't be null for signing JWT!"))
                .compact();
    }

    private PublicKey getPublicKey() {
        try {
            String pem = new String(Files.readAllBytes(Paths.get(jwtProperties.getPublicPemPath())));
            pem = pem.replace(START_PUBLIC_TEXT, Strings.EMPTY)
                    .replace(END_PUBLIC_TEXT, Strings.EMPTY)
                    .replaceAll(WHITESPACE_REGEX, Strings.EMPTY);
            byte[] decoded = Base64.getDecoder().decode(pem);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance(ALGORITHM_NAME);
            return kf.generatePublic(keySpec);
        } catch (IOException exception){
            log.error("PEM File read operation failed for JWT key signing!", exception);
        } catch (NoSuchAlgorithmException exception){
            log.error("Algorithm not found for JWT key sign operation!", exception);
        } catch (InvalidKeySpecException exception) {
            log.error("KeySpec is invalid for generating private key!", exception);
        }
        return null;
    }

    private PrivateKey getPrivateKey() {
        try {
            String pem = new String(Files.readAllBytes(Paths.get(jwtProperties.getPemPath())));
            pem = pem.replace(START_TEXT, Strings.EMPTY)
                    .replace(END_TEXT, Strings.EMPTY)
                    .replaceAll(WHITESPACE_REGEX, Strings.EMPTY);
            byte[] decoded = Base64.getDecoder().decode(pem);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance(ALGORITHM_NAME);
            return kf.generatePrivate(keySpec);
        } catch (IOException exception){
            log.error("PEM File read operation failed for JWT key signing!", exception);
        } catch (NoSuchAlgorithmException exception){
            log.error("Algorithm not found for JWT key sign operation!", exception);
        } catch (InvalidKeySpecException exception) {
            log.error("KeySpec is invalid for generating private key!", exception);
        }
        return null;
    }

    @Override
    public boolean validateToken(UserDetails userDetails, Claims payload) {
        String username = payload.get(Claims.SUBJECT, String.class);
        boolean usernameMatched = userDetails.getUsername().equals(username);
        return usernameMatched && payload.get(Claims.EXPIRATION, Date.class).after(Date.from(Instant.now(clock)));
    }

    @Override
    public Claims getClaims(String jwtToken) {
        return Jwts.parser()
                .decryptWith(getPrivateKey())
                .verifyWith(getPublicKey())
                .build().parseSignedClaims(jwtToken).getPayload();
    }

    private Set<String> getAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority)
                .map(s -> s.replaceFirst(ROLE_PREFIX, Strings.EMPTY)).collect(Collectors.toSet());
    }
}
