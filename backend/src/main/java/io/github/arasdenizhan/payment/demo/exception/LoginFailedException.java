package io.github.arasdenizhan.payment.demo.exception;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message) {
        super(message);
    }
}
