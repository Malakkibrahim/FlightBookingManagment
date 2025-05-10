import java.util.*;
import java.io.*;
public class BookingSystem {
    private String usersFile = "users.txt";
    private String flightsFile = "flights.txt";
    private String bookingsFile = "bookings.txt";
    private String paymentsFile = "payments.txt";

    public BookingSystem() {
        System.out.println("Booking System Started!");
    }

    public void searchFlights(String origin, String destination, String date) {
        try (BufferedReader reader = new BufferedReader(new FileReader(flightsFile))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    String flightNumber = parts[0];
                    String from = parts[2];
                    String to = parts[3];
                    String depDate = parts[4];
                    String time = parts[5];
                    int seats = Integer.parseInt(parts[7]);

                    if (from.equalsIgnoreCase(origin)
                            && to.equalsIgnoreCase(destination)
                            && depDate.equals(date)) {

                        System.out.println("✈ Flight: " + flightNumber + " | " + from + " ➡ " + to + " | Date: " + depDate + " | Time: " + time + " | Seats: " + seats);
                        found = true;
                    }
                }
            }

            if (!found) {
                System.out.println(" لا توجد رحلات مطابقة.");
            }

        } catch (IOException e) {
            System.out.println(" خطأ في قراءة بيانات الرحلات.");
        }
    }


    public void createBooking(String customerUsername, String flightNumber) {
        String bookingRef = "BK" + System.currentTimeMillis();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bookingsFile, true))) {
            writer.write(bookingRef + "," + customerUsername + "," + flightNumber + ",Reserved,Pending");
            writer.newLine();
            System.out.println(" تم إنشاء الحجز برقم: " + bookingRef);
        } catch (IOException e) {
            System.out.println(" خطأ أثناء حفظ الحجز.");
        }
    }


    public void processPayment(String bookingRef, String method, double amount) {
        List<String> updatedBookings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(bookingsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(bookingRef)) {
                    parts[3] = "Confirmed";
                    parts[4] = "Paid";
                    line = String.join(",", parts);
                }
                updatedBookings.add(line);
            }
        } catch (IOException e) {
            System.out.println(" خطأ في قراءة الحجوزات.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bookingsFile))) {
            for (String updated : updatedBookings) {
                writer.write(updated);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(" خطأ أثناء تحديث حالة الحجز.");
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(paymentsFile, true))) {
            String paymentId = "PM" + System.currentTimeMillis();
            writer.write(paymentId + "," + bookingRef + "," + amount + "," + method + ",Paid");
            writer.newLine();
            System.out.println(" تم الدفع بنجاح!");
        } catch (IOException e) {
            System.out.println(" خطأ أثناء حفظ الدفع.");
        }
    }


    public void generateTicket(String bookingRef) {
        try (BufferedReader reader = new BufferedReader(new FileReader(bookingsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(bookingRef)) {
                    System.out.println(" تذكرة السفر ");
                    System.out.println("رقم الحجز: " + parts[0]);
                    System.out.println("العميل: " + parts[1]);
                    System.out.println("رقم الرحلة: " + parts[2]);
                    System.out.println("الحالة: " + parts[3]);
                    System.out.println("الدفع: " + parts[4]);
                    return;
                }
            }
            System.out.println(" لم يتم العثور على الحجز.");
        } catch (IOException e) {
            System.out.println(" خطأ في قراءة التذكرة.");
        }
    }


    private void updateFlightSeats(String flightNumber, int change) {
        List<String> updatedFlights = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(flightsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(flightNumber)) {
                    int seats = Integer.parseInt(parts[7]);
                    seats += change;
                    if (seats < 0) seats = 0;  // ما ينفعش يكون بالسالب
                    parts[7] = String.valueOf(seats);
                    line = String.join(",", parts);
                }
                updatedFlights.add(line);
            }
        } catch (IOException e) {
            System.out.println("  خط ف قراءة   flight.txt.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(flightsFile))) {
            for (String f : updatedFlights) {
                writer.write(f);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(" خطأ في حفظ flight.txt بعد تعديل المقاعد.");
        }
    }
}