package payment;
public interface IPaymentProcessor {
  public boolean processPayment(PaymentDetails details);
}
