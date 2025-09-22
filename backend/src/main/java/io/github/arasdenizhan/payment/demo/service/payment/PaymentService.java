package io.github.arasdenizhan.payment.demo.service.payment;

import io.github.arasdenizhan.payment.demo.dto.payment.CardPaymentDto;

public interface PaymentService {
    void cardPayment(String idempotencyKey, CardPaymentDto cardPaymentDto);
}
