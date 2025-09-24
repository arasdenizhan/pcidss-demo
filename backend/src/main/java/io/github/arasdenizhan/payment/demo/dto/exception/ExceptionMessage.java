package io.github.arasdenizhan.payment.demo.dto.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionMessage {
    private String message;
}
