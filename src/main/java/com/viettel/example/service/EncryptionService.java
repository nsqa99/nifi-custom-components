package com.viettel.example.service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author anhnsq@viettel.com.vn
 */
public class EncryptionService {
  private static final byte[] secretKey = "1234567689Aa@aA987654321".getBytes();
  private static final byte[] iv = "a76nb5h9".getBytes();

  public static String encrypt(byte[] msgBytes) {
    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "TripleDES");
    IvParameterSpec ivSpec = new IvParameterSpec(iv);
    Cipher encryptCipher;

    try {
      encryptCipher = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
      encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
      byte[] encryptedMessageBytes = encryptCipher.doFinal(msgBytes);

      return Base64.getEncoder().encodeToString(encryptedMessageBytes);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException |
             InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
      throw new RuntimeException(e);
    }
  }
}
