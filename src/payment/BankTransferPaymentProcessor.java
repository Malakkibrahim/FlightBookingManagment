package payment;

public class BankTransferPaymentProcessor implements IPaymentProcessor {
  @Override
  public boolean processPayment(PaymentDetails details) {
      if (!(details instanceof BankTransferPaymentDetails)) {
          System.out.println("Invalid payment details for bank transfer processing.");
          return false;
      }
      BankTransferPaymentDetails btDetails = (BankTransferPaymentDetails) details;
      // Simulate validation and processing
      if (btDetails.accountNumber.length() < 8 || btDetails.accountNumber.length() > 20) {
          System.out.println("Invalid bank account number.");
          return false;
      }
      if (btDetails.amount <= 0) {
          System.out.println("Invalid payment amount.");
          return false;
      }
      if (btDetails.bankCode.length() != 8) {
          System.out.println("Invalid bank code.");
          return false;
      }
      System.out.println("Processing bank transfer for " + btDetails.accountHolder + ": $" + btDetails.amount);
      System.out.println("Reference: " + btDetails.reference);
      System.out.println("Bank transfer successful!");
      return true;
  }
}