package eg.alex.fcds.models.payment;

public class CreditCardPaymentDetails extends PaymentDetails {
  public double amount;
  public String cardNumber;
  public String cardHolder;
  public String expiryDate;
  public String cvv;

  public CreditCardPaymentDetails(double amount, String cardNumber, String cardHolder, String expiryDate, String cvv) {
      this.amount = amount;
      this.cardNumber = cardNumber;
      this.cardHolder = cardHolder;
      this.expiryDate = expiryDate;
      this.cvv = cvv;
  }
}