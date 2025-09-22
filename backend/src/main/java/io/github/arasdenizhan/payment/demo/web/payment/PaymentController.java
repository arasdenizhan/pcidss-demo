package io.github.arasdenizhan.payment.demo.web.payment;

import io.github.arasdenizhan.payment.demo.datamodel.enums.PaymentType;
import io.github.arasdenizhan.payment.demo.dto.payment.CardPaymentDto;
import io.github.arasdenizhan.payment.demo.service.encryption.EncryptionService;
import io.github.arasdenizhan.payment.demo.service.idempotency.IdempotencyService;
import io.github.arasdenizhan.payment.demo.service.payment.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final IdempotencyService idempotencyService;
    private final EncryptionService encryptionService;

    @PostMapping("/card")
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<Void> cardPayment(@Valid @NotBlank @RequestHeader(value = "Idempotency-Key") String idempotencyKey,
                                     @RequestBody CardPaymentDto cardPaymentDto){
        idempotencyService.checkKey(idempotencyKey, PaymentType.CARD);
        encryptionService.encryptCard(cardPaymentDto);
        paymentService.cardPayment(idempotencyKey, cardPaymentDto);
        return ResponseEntity.ok().build();
    }
}
