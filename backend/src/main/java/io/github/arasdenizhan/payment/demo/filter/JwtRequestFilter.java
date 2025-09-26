package io.github.arasdenizhan.payment.demo.filter;

import io.github.arasdenizhan.payment.demo.service.jwt.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static io.github.arasdenizhan.payment.demo.service.jwt.impl.JwtServiceImpl.JWT_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final String AUTH_PATH = "/api/v1/auth";

    private final JwtService jwtService;
    private final InMemoryUserDetailsManager userDetailsManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkIfAuthenticationRequest(request, response, filterChain)) return;

        final Cookie jwtCookie = getJwtCookie(request);
        if (jwtCookie != null ) {
            Claims payload = jwtService.getClaims(jwtCookie.getValue());
            String username = payload.get(Claims.SUBJECT, String.class);
            if (Strings.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
                if (jwtService.validateToken(userDetails, payload)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private Cookie getJwtCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> JWT_COOKIE_NAME.equals(cookie.getName()))
                .findFirst().orElse(null);
    }

    private boolean checkIfAuthenticationRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String path = request.getRequestURI();
        if (AUTH_PATH.equals(path) && HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }
}
