package io.github.arasdenizhan.payment.demo.dto.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class CardPaymentDto {
    @NotBlank
    @Setter
    @Length(min = 16, max = 16)
    private String number;

    @NotBlank
    @Setter
    private String name;

    @NotNull
    @Max(value = 2040)
    @Setter
    private int year;

    @NotNull
    @Min(value = 1)
    @Max(value = 12)
    @Setter
    private int month;

    @NotNull
    @Min(value = 100)
    @Max(value = 999)
    @Setter
    private int cvc;

    @Positive
    @NotNull
    private BigDecimal amount;

    @JsonIgnore
    @Setter
    private String encryptedNumber;
}