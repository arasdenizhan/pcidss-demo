package io.github.arasdenizhan.payment.demo.service.encryption;

import io.github.arasdenizhan.payment.demo.dto.payment.CardPaymentDto;

public interface EncryptionService {
    void encryptCard(CardPaymentDto cardPaymentDto);
}
