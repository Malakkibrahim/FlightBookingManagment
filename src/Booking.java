import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;






 class SeatSelection {
    private String seatClass;
    private int quantity;

    public SeatSelection(String seatClass, int quantity) {
        this.seatClass = seatClass.trim().toLowerCase();
        this.quantity = quantity;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public int getQuantity() {
        return quantity;
    }
}

public class Booking {

        private static final String BOOKINGS_FILE = "bookings.txt"; // اسم الملف اللي هنخزن فيه الحجوزات
        private String bookingReference;
        private Customer customer;
        private Flight flight;
        private List<Passenger> passengers;
        private Map<Passenger, SeatSelection> seatSelections;
        private String status;
        private String paymentStatus;
        private double totalPrice;
        private String seatClass;

        // كونستركتور لإنشاء حجز جديد
        public Booking(Customer customer, Flight flight, List<Passenger> passengers, Map<Passenger, SeatSelection> seatSelections) {
            this.bookingReference = generateBookingReference();
            this.customer = customer;
            this.flight = flight;
            this.passengers = passengers;
            this.seatSelections = seatSelections;
            this.status = "مؤكد";
            this.paymentStatus = "قيد الدفع";
            this.totalPrice = calculateTotalPrice();
            saveBookingToFile(); // حفظ الحجز الجديد في الملف
        }
    public Booking() {
        this.bookingReference = generateBookingReference();
    }
    private String generateBookingReference() {
        return "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private void saveBookingToFile() {
        // مثال بسيط لحفظ الحجز في ملف
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bookings.txt", true))) {
            writer.write(toFileString());
            writer.newLine();
            System.out.println("✅ Booking saved.");
        } catch (IOException e) {
            System.out.println("⚠ Error saving booking.");
        }
    }
    public String toFileString() {
        String passengerNames = "";
        for (Passenger passenger : passengers) {
            passengerNames += passenger.getpassengerName() + ", ";
        }
        if (!passengerNames.isEmpty()) {
            passengerNames = passengerNames.substring(0, passengerNames.length() - 2); // إزالة الفاصلة الأخيرة
        }

        return bookingReference + "," + status + "," + paymentStatus + "," + flight.getFlightnumber() + "," + passengerNames;
    }



    // كونستركتور عشان نقرا حجز موجود من الملف
        public Booking(String bookingReference, Customer customer, Flight flight, List<Passenger> passengers, Map<Passenger, SeatSelection> seatSelections, String status, String paymentStatus, double totalPrice) {
            this.bookingReference = bookingReference;
            this.customer = customer;
            this.flight = flight;
            this.passengers = passengers;
            this.seatSelections = seatSelections;
            this.status = status;
            this.paymentStatus = paymentStatus;
            this.totalPrice = totalPrice;
        }

        public static String getBOOKINGS_FILE() {
            return BOOKINGS_FILE;
        }

        public String getBookingReference() {
            return bookingReference;
        }

        public Customer getCustomer() {
            return customer;
        }

        public Flight getFlight() {
            return flight;
        }

        public List<Passenger> getPassengers() {
            return passengers;
        }

        public Map<Passenger, SeatSelection> getSeatSelections() {
            return seatSelections;
        }

        public String getStatus() {
            return status;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setBookingReference(String bookingReference) {
            this.bookingReference = bookingReference;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public void setFlight(Flight flight) {
            this.flight = flight;
        }

        public void setPassengers(List<Passenger> passengers) {
            this.passengers = passengers;
        }

        public void setSeatSelections(Map<Passenger, SeatSelection> seatSelections) {
            this.seatSelections = seatSelections;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }
        public boolean addPassenger(String passengerName, String flightNumber, String destination, String date) {
            File flightFile = new File("flights.txt");
            List<String> updatedLines = new ArrayList<>();
            boolean found = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(flightFile))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");

                    if (parts[0].equalsIgnoreCase(flightNumber)
                            && parts[3].equalsIgnoreCase(destination)
                            && parts[4].equals(date)) {

                        int availableSeats = Integer.parseInt(parts[7]);

                        if (availableSeats > 0) {
                            found = true;
                            availableSeats -= 1;
                            parts[7] = String.valueOf(availableSeats);

                            // Add passenger to the booking
                            Passenger newPassenger = new Passenger(passengerName);
                            this.passengers.add(newPassenger);

                            // Optional: Save passenger in passengers.txt
                            savePassengerToFile(newPassenger, this.bookingReference);

                            // Rebuild updated line
                            line = String.join(",", parts);
                        } else {
                            System.out.println(" لا يوجد مقاعد متاحة!");
                            return false;
                        }
                    }

                    updatedLines.add(line);
                }
                // Write back the updated file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(flightFile))) {
                    for (String updatedLine : updatedLines) {
                        writer.write(updatedLine);
                        writer.newLine();
                    }
                }

                if (found) {
                    System.out.println(" تم إضافة الراكب وتحديث عدد المقاعد.");
                    return true;
                } else {
                    System.out.println(" الرحلة غير موجودة.");
                    return false;
                }

            } catch (IOException e) {
                System.out.println(" حصل خطأ في قراءة أو كتابة الملف.");
                e.printStackTrace();
                return false;
            }
        }

        private void savePassengerToFile(Passenger p, String bookingRef) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("passengers.txt", true))) {
                writer.write(bookingRef + "," + p.getpassengerName());
                writer.newLine();
            } catch (IOException e) {
                System.out.println(" مشكلة في حفظ بيانات الراكب.");
                e.printStackTrace();
            }
        }


    public double calculateTotalPrice() {
        double total = 0.0;

        for (Map.Entry<Passenger, SeatSelection> entry : seatSelections.entrySet()) {
            SeatSelection selection = entry.getValue();
            String seatClass = selection.getSeatClass();
            int quantity = selection.getQuantity();

            switch (seatClass) {
                case "economy":
                    total += flight.getEconomyPrice() * quantity;
                    break;
                case "business":
                    total += flight.getBusinessPrice() * quantity;
                    break;
                case "first class":
                    total += flight.getFirstClassPrice() * quantity;
                    break;
                default:
                    System.out.println("⚠ نوع غير معروف للراكب: " + seatClass);
            }
        }

        return total;
    }

    public boolean addBookingAvailability() {
        if (flight != null && seatClass != null) {
            switch (seatClass.toLowerCase()) {
                case "economy":
                    flight.increaseEconomySeats();
                    return true;
                case "business":
                    flight.increaseBusinessSeats();
                    return true;
                case "first":
                    flight.increaseFirstClassSeats();
                    return true;
                default:
                    System.out.println("⚠ Invalid seat class.");
                    return false;
            }
        } else {
            System.out.println("⚠ Flight or seat class not set.");
            return false;
        }
    }


    public boolean confirmBooking() {
            // 1. شوف حالة الحجز والدفع
            if (this.status.equalsIgnoreCase("Reserved") && this.paymentStatus.equalsIgnoreCase("Paid")) {
                String flightnumber = this.flight.getFlightnumber();
                int numberOfPassengers = this.passengers.size();
                String passengerNames = "";
                for (Passenger passenger : this.passengers) {
                    passengerNames += passenger.getpassengerName() + ", "; // نفترض إن كلاس Passenger فيه ميثود getName()
                }
                if (!passengerNames.isEmpty()) {
                    passengerNames = passengerNames.substring(0, passengerNames.length() - 2); // إزالة الفاصلة الأخيرة
                }

                boolean flightupdate= flight.getAvailableSeats(flightnumber, numberOfPassengers, true); // true يعني نقص
                boolean bookingAdded = addBookingAvailability();

                if (flightupdate && bookingAdded) {
                    System.out.println("تم تأكيد الحجز بنجاح!");
                    System.out.println("رقم مرجع الحجز: " + this.bookingReference);
                    System.out.println("المسافرون: " + passengerNames);
                    System.out.println("رقم الرحلة: " + flightnumber);
                    return true;
                } else {
                    System.out.println("حدث خطأ أثناء تحديث بيانات الرحلة أو إضافة سجل الحجز.");
                    return false;
                }
            } else {
                System.out.println("لا يمكن تأكيد الحجز. حالة الحجز: " + this.status + "، حالة الدفع: " + this.paymentStatus + ". يجب أن يكون الحجز 'Reserved' والدفع 'Paid'.");
                return false;
            }
        }


        public void cancelBooking()
        {

        }

        public String generateItinerary()
        {
            String itinerary = "Booking Reference:"+bookingReference+"\n"+"Customer:"+customer.getName()+"/n"+
                    "Flight:"+flight.getFlightnumber() + flight.getOrigin() + "to" +flight.getDestination() + "/n" +
                    "Arrival:"+flight.getArrivalTime()+"/n"+"Passengers:/n";
            for(Passenger p:passengers){
                itinerary += "" + p.getpassengerName() + " (Passport: " + p.getPassportNumber() + ")\n";
            }
            itinerary+="Status:"+status+"/n"+"Payment"+paymentStatus;
            return itinerary;
        }
    }

