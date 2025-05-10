import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Payment {
    private String paymentId;
    private String bookingReference;
    private double amount;
    private String method;
    private String status;
    private String transactionDate;
    private static final String PAYMENTS_FILE = "payments.txt";

    public Payment(String paymentId, String bookingReference, double amount, String method,String satus, String transactionDate) {
        this.paymentId = paymentId;
        this.bookingReference = bookingReference;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.transactionDate = transactionDate;
    }

    public Payment(String bookingReference, double amount, String method) {
        this.paymentId = "PM" + System.currentTimeMillis();
        this.bookingReference = bookingReference;
        this.amount = amount;
        this.method = method;
        this.status = "Pending";
        this.transactionDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public static String getPAYMENTS_FILE() {
        return PAYMENTS_FILE;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String toFileString() {
        return paymentId + "," + bookingReference + "," + amount + "," + method + "," + status + "," + transactionDate;
    }

    public static Payment fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 6) {
            return new Payment(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3], parts[4], parts[5]);
        }
        return null;
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PAYMENTS_FILE, true))) {
            writer.write(this.toFileString());
            writer.newLine();
            System.out.println("Payment saved successfully.");
        } catch (IOException e) {
            System.out.println(" Error saving payment.");
        }
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
        updatePaymentInFile();
    }
    public boolean validatePaymentDetails() {
        return amount > 0 && (method.equalsIgnoreCase("CreditCard") || method.equalsIgnoreCase("BankTransfer"));
    }


    public boolean processPayment() {
        if (!validatePaymentDetails()) {
            System.out.println(" Invalid payment details.");
            this.status = "Failed";
            saveToFile();
            return false;
        }

        this.status = "Paid";
        saveToFile();
        return true;
    }

    private void updatePaymentInFile() {
        try {
            File inputFile = new File(PAYMENTS_FILE);
            File tempFile = new File("payments_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                Payment p = Payment.fromFileString(line);
                if (p != null && p.paymentId.equals(this.paymentId)) {
                    writer.write(this.toFileString());
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }

            writer.close();
            reader.close();

            // Replace original file
            inputFile.delete();
            tempFile.renameTo(inputFile);

        } catch (IOException e) {
            System.out.println(" Error updating payment status in file.");
        }
    }
}