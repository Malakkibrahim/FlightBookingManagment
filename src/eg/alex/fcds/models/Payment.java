package eg.alex.fcds.models;
import java.time.LocalDateTime;

import eg.alex.fcds.models.payment.IPaymentProcessor;
import eg.alex.fcds.models.payment.PaymentDetails;
import eg.alex.fcds.models.payment.PaymentProcessorFactory;
import eg.alex.fcds.models.shared.*;

public class Payment {
    private String paymentId;
    private String bookingReferenceNumber;
    private double amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private LocalDateTime transactionDate;
    private IPaymentProcessor processor;

    public Payment(String paymentId, String bookingReference, double amount, PaymentMethod method, PaymentStatus status, LocalDateTime transactionDate) {
        this(bookingReference, amount, method, status, transactionDate);
        this.paymentId = paymentId;
        this.status = status;
    }

    public Payment(String bookingReference, double amount, PaymentMethod method, PaymentStatus status, LocalDateTime transactionDate) {
        this.paymentId = "PM" + System.currentTimeMillis();
        this.bookingReferenceNumber = bookingReference;
        this.amount = amount;
        this.method = method;
        this.status = PaymentStatus.SUSPENDED;
        this.transactionDate = LocalDateTime.now();
        this.processor = new PaymentProcessorFactory().getPaymentProcessor(this.method);
    }

    public String getBookingReferenceNumber() {
        return bookingReferenceNumber;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void updateStatus(PaymentStatus status) {
        this.status = status;
    }

    private boolean validatePaymentDetails(PaymentDetails details) {
        return this.processor.processPayment(details);
    }

    public boolean processPayment(PaymentDetails details) {
        if (!validatePaymentDetails(details)) {
            System.out.println(" Invalid payment details.");
            this.status = PaymentStatus.FAILED;
            return false;
        }

        this.status = PaymentStatus.SUCCESS;
        return true;
    }

    @Override
    public String toString() {
        return paymentId + "," + bookingReferenceNumber + "," + amount + "," + method + "," + status + "," + transactionDate;
    }
}