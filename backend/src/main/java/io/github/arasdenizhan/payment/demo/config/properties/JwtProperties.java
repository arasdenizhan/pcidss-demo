package io.github.arasdenizhan.payment.demo.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    public static final String START_TEXT = "-----BEGIN PRIVATE KEY-----";
    public static final String END_TEXT = "-----END PRIVATE KEY-----";
    public static final String START_PUBLIC_TEXT = "-----BEGIN PUBLIC KEY-----";
    public static final String END_PUBLIC_TEXT = "-----END PUBLIC KEY-----";
    public static final String WHITESPACE_REGEX = "\\s";
    public static final String ALGORITHM_NAME = "RSA";

    private Long validity;
    private String pemPath;
    private String publicPemPath;
}
