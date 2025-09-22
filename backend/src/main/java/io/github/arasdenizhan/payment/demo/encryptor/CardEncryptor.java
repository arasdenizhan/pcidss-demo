package io.github.arasdenizhan.payment.demo.encryptor;


public class CardEncryptor {
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12; // 96-bit nonce
    private static final int AES_KEY_BIT = 256;


}
