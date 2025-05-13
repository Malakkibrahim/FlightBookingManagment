package eg.alex.fcds.models.payment;
public interface IPaymentProcessor {
  public boolean processPayment(PaymentDetails details);
}
