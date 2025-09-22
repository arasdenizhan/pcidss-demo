package io.github.arasdenizhan.payment.demo.exception;

public class NotUniqueOperationException extends RuntimeException {
    public NotUniqueOperationException(String key) {
        super(String.format("Operation with %s key is done before!", key));
    }
}
