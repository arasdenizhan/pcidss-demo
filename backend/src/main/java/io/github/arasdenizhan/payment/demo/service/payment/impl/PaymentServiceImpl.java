package io.github.arasdenizhan.payment.demo.service.payment.impl;

import io.github.arasdenizhan.payment.demo.datamodel.CardPayment;
import io.github.arasdenizhan.payment.demo.datamodel.enums.Status;
import io.github.arasdenizhan.payment.demo.dto.payment.CardPaymentDto;
import io.github.arasdenizhan.payment.demo.repository.PaymentRepository;
import io.github.arasdenizhan.payment.demo.service.payment.PaymentService;
import io.github.arasdenizhan.payment.demo.service.provider.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final Provider dummyPaymentProvider;

    @Override
    public void cardPayment(String idempotencyKey, CardPaymentDto cardPaymentDto) {
        log.info("Card Payment request received, request key={}", idempotencyKey);
        String paymentId = dummyPaymentProvider.doCardPayment(cardPaymentDto);
        CardPayment savedEntity = repository.save(getPaymentEntity(cardPaymentDto, paymentId));
        log.info("Card Payment with id={} and key={} processed and saved successfully.", savedEntity.getId(), idempotencyKey);
    }

    private CardPayment getPaymentEntity(CardPaymentDto cardPaymentDto, String paymentId) {
        return CardPayment.builder()
                .token(cardPaymentDto.getEncryptedNumber())
                .status(Status.PENDING)
                .amount(cardPaymentDto.getAmount())
                .providerId(paymentId)
                .build();
    }
}
