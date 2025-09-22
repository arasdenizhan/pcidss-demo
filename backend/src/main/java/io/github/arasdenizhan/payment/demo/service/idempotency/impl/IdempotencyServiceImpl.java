package io.github.arasdenizhan.payment.demo.service.idempotency.impl;

import io.github.arasdenizhan.payment.demo.datamodel.enums.PaymentType;
import io.github.arasdenizhan.payment.demo.exception.NotUniqueOperationException;
import io.github.arasdenizhan.payment.demo.service.idempotency.IdempotencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdempotencyServiceImpl implements IdempotencyService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void checkKey(String idempotencyKey, PaymentType type) {
        log.info("Checking key={} for payment", idempotencyKey);
        Boolean isAbsent = redisTemplate.opsForValue().setIfAbsent(idempotencyKey, type.name(), Duration.of(24, ChronoUnit.HOURS));
        if(Boolean.TRUE.equals(isAbsent)){
            log.info("Payment with key={} is not done before, check is successful.", idempotencyKey);
            return;
        }
        log.error("Operation with key={} is done before, stopping payment operation!", idempotencyKey);
        throw new NotUniqueOperationException(idempotencyKey);
    }
}
