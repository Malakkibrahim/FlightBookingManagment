package payment;

public class CreditCardPaymentProcessor implements IPaymentProcessor {
  @Override
  public boolean processPayment(PaymentDetails details) {
      if (!(details instanceof CreditCardPaymentDetails)) {
          System.out.println("Invalid payment details for credit card processing.");
          return false;
      }
      CreditCardPaymentDetails ccDetails = (CreditCardPaymentDetails) details;
      // Simulate validation and processing
      if (ccDetails.cardNumber.length() != 16) {
          System.out.println("Invalid card number.");
          return false;
      }
      if (ccDetails.amount <= 0) {
          System.out.println("Invalid payment amount.");
          return false;
      }
      System.out.println("Processing credit card payment for " + ccDetails.cardHolder + ": $" + ccDetails.amount);
      System.out.println("Payment successful!");
      return true;
  }
}