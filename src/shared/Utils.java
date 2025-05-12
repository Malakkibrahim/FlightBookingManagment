package shared;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utils {
  public static boolean isValidPassword(String password)
  {
    if (password.length() < 6) {
      return false;
    }

    return password.matches("[a-zA-Z0-9]+");
  }

  public static boolean isValidEmail(String email) {
    return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
  }

  public static String hashPassword(String password) {
    try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("Error: SHA-256 algorithm not found");
    }
  }
}
