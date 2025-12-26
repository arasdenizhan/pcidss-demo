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
    @Length(min = 16, max = 16, message = "Card number is not valid!")
    private String number;

    @NotBlank(message = "Name can not blank!")
    @Setter
    private String name;

    @NotNull(message = "Year can not blank!")
    @Min(value = 25, message = "Year can be min 25")
    @Max(value = 40, message = "Year can be max 40")
    @Setter
    private int year;

    @NotNull(message = "Month can not blank!")
    @Min(value = 1, message = "Month can be min 1")
    @Max(value = 12, message = "Month can be max 12")
    @Setter
    private int month;

    @NotNull(message = "CVC can not blank!")
    @Min(value = 100, message = "CVC can be min 100")
    @Max(value = 999, message = "CVC can be max 999")
    @Setter
    private int cvc;

    @Positive(message = "Amount can be positive only!")
    @NotNull(message = "Amount can not be blank!")
    private BigDecimal amount;

    @JsonIgnore
    @Setter
    private String encryptedNumber;
}