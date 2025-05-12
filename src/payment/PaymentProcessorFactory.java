package payment;

import shared.*;

public class PaymentProcessorFactory {
  public IPaymentProcessor getPaymentProcessor(PaymentMethod method)
  {
    if(method == PaymentMethod.CREDITCARD)
      return new CreditCardPaymentProcessor();
    else if(method == PaymentMethod.BANK_TRANSFFER)
      return new BankTransferPaymentProcessor();
    return null;
  }
}
