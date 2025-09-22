package io.github.arasdenizhan.payment.demo.service.idempotency;

import io.github.arasdenizhan.payment.demo.datamodel.enums.PaymentType;

public interface IdempotencyService {
    void checkKey(String idempotencyKey, PaymentType type);
}
