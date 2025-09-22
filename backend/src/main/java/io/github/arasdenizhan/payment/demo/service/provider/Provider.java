package io.github.arasdenizhan.payment.demo.service.provider;

import io.github.arasdenizhan.payment.demo.dto.payment.CardPaymentDto;

public interface Provider {
    String doCardPayment(CardPaymentDto cardPaymentDto);
}
