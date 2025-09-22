package io.github.arasdenizhan.payment.demo.service.encryption.impl;

import io.github.arasdenizhan.payment.demo.config.properties.EncryptionProperties;
import io.github.arasdenizhan.payment.demo.dto.payment.CardPaymentDto;
import io.github.arasdenizhan.payment.demo.service.encryption.EncryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTransitOperations;

@Slf4j
@Service
@RequiredArgsConstructor
public class EncryptionServiceImpl implements EncryptionService {
    private final VaultTransitOperations vaultTransitOperations;
    private final EncryptionProperties encryptionProperties;

    @Override
    public void encryptCard(CardPaymentDto cardPaymentDto) {
        String encryptedNumber = vaultTransitOperations.encrypt(encryptionProperties.getKey(), cardPaymentDto.getNumber());
        cardPaymentDto.setNumber(null);
        log.info("Encryption of card number done successfully, unmasked card number removed from request(memory)");
        cardPaymentDto.setEncryptedNumber(encryptedNumber);
    }
}
