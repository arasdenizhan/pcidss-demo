package io.github.arasdenizhan.payment.demo.config;

import io.github.arasdenizhan.payment.demo.config.properties.TimeProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
@RequiredArgsConstructor
public class TimeConfig {
    private final TimeProperties timeProperties;

    @Bean
    Clock clock(){
        return Clock.system(ZoneId.of(timeProperties.getZone()));
    }

}
