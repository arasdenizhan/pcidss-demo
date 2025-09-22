package io.github.arasdenizhan.payment.demo.service.provider.impl;

import io.github.arasdenizhan.payment.demo.dto.payment.CardPaymentDto;
import io.github.arasdenizhan.payment.demo.service.provider.Provider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class DummyPaymentProvider implements Provider {
    @Override
    public String doCardPayment(CardPaymentDto cardPaymentDto) {
        log.info("Dummy payment provider processed request successfully!");
        return UUID.randomUUID().toString();
    }
}
