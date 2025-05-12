
package payment;

public class BankTransferPaymentDetails extends PaymentDetails {
  public double amount;
  public String accountNumber;
  public String accountHolder;
  public String bankCode;
  public String reference;

  public BankTransferPaymentDetails(double amount, String accountNumber, String accountHolder, String bankCode, String reference) {
      this.amount = amount;
      this.accountNumber = accountNumber;
      this.accountHolder = accountHolder;
      this.bankCode = bankCode;
      this.reference = reference;
  }
}