package utilities;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

// why am i have to do this?
public class Encrypt {

  private static final String AES_SECRET_KEY = "KhuatHieu1234567"; 

  public static String encrypt(String key) throws Exception {
    SecretKeySpec secretKey = new SecretKeySpec(AES_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encryptedBytes = cipher.doFinal(key.getBytes(StandardCharsets.UTF_8));
    return Base64.getUrlEncoder().withoutPadding().encodeToString(encryptedBytes);
  }

  public static String decrypt(String encKey) throws Exception {
    SecretKeySpec secretKey = new SecretKeySpec(AES_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] decryptedBytes = cipher.doFinal(Base64.getUrlDecoder().decode(encKey));
    return new String(decryptedBytes, StandardCharsets.UTF_8);
  }
}
