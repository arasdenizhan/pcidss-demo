package io.github.arasdenizhan.payment.demo.config;

import io.github.arasdenizhan.payment.demo.config.properties.RedisProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;

import java.util.List;

import static io.github.arasdenizhan.payment.demo.model.UserRole.DATABASE_ADMIN;
import static io.github.arasdenizhan.payment.demo.model.UserRole.PAYMENT_ADMIN;
import static io.github.arasdenizhan.payment.demo.model.UserRole.USER;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
public class BeanConfig {
    private final RedisProperties redisProperties;

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    InMemoryUserDetailsManager userDetailsManager(BCryptPasswordEncoder bCryptPasswordEncoder){
        UserDetails user = User.builder().username("user")
                .password(bCryptPasswordEncoder.encode("user"))
                .authorities(List.of(new SimpleGrantedAuthority(USER.name()))).build();
        UserDetails paymentAdmin = User.builder().username("paymentAdmin")
                .password(bCryptPasswordEncoder.encode("paymentAdmin"))
                .authorities(List.of(new SimpleGrantedAuthority(PAYMENT_ADMIN.name()))).build();
        UserDetails databaseAdmin = User.builder().username("databaseAdmin")
                .password(bCryptPasswordEncoder.encode("databaseAdmin"))
                .authorities(List.of(new SimpleGrantedAuthority(DATABASE_ADMIN.name()))).build();
        UserDetails superUser = User.builder().username("superUser")
                .password(bCryptPasswordEncoder.encode("superUser"))
                .authorities(List.of(
                        new SimpleGrantedAuthority(USER.name()),
                        new SimpleGrantedAuthority(DATABASE_ADMIN.name()),
                        new SimpleGrantedAuthority(PAYMENT_ADMIN.name())
                )).build();
        return new InMemoryUserDetailsManager(List.of(user, paymentAdmin, databaseAdmin, superUser));
    }

    @Bean
    VaultTransitOperations vaultTransitOperations(VaultTemplate vaultTemplate){
        return vaultTemplate.opsForTransit();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
        config.setPassword(RedisPassword.of(redisProperties.getPassword()));
        return new LettuceConnectionFactory(config);
    }
}
