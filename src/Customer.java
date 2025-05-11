import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
    public class Customer extends User {
        private String address;
        private String prefrence;
        private List<Booking> bookings;

        public Customer(String userId, String username, String password, String name, String email, String contactInfo, String address,String prefrence, Role role , List<Booking> bookings) {
            super(userId, username, password, name, email, contactInfo,role);
            this.address = address;
            this.bookings = new ArrayList<>();

        }

        public String getUsername() {
            return userName;
        }



        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void SHOWMENU(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("---- Customer Menu ----");
            System.out.println("1. Search for a flight");
            System.out.println("2. Modify System Settings");
            System.out.println("3. View System Logs");
            System.out.println("4. Manage User Access");
            System.out.println("5. Update Profile");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch(choice){
                case 1:


            }
        }


        // 1. Search flights
        public static void searchFlightByNumber(String filePath) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("🔍 Enter flight number to search: ");
            String targetFlightNumber = scanner.nextLine().trim();

            boolean found = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Flight flight = Flight.fromFileString(line);
                    if (flight.getFlightnumber().equalsIgnoreCase(targetFlightNumber)) {
                        System.out.println("✅ Flight found!");
                        System.out.println("✈️ Departure Time: " + flight.getDepartureTime());
                        System.out.println("🛬 Arrival Time  : " + flight.getArrivalTime());
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("❌ Flight not found.");
                }

            } catch (IOException e) {
                System.out.println("❌ Error reading file ");
            }
        }


        // 2. Create booking
        public void createBooking(List<Booking> bookings, List<Payment> payments, String flightFilePath, String passengerFilePath , String paymentFilePath) {
            Scanner scanner = new Scanner(System.in);

            // Step 1: طلب رقم الرحلة من المستخدم
            System.out.print("Enter flight number to book: ");
            String flightNumber = scanner.nextLine().trim();

            Flight selectedFlight = null;

            // Step 2: البحث عن الرحلة في ملف الرحلات
            try (BufferedReader reader = new BufferedReader(new FileReader(flightFilePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Flight flight = Flight.fromFileString(line);
                    if (flight.getFlightnumber().equalsIgnoreCase(flightNumber)) {
                        selectedFlight = flight;
                        break; // إذا وجدنا الرحلة، نوقف البحث
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading flight data from file.");
                return;
            }

            // Step 3: إذا لم يتم العثور على الرحلة
            if (selectedFlight == null) {
                System.out.println("❌ Flight not found.");
                return;
            }

            // Step 4: عرض بعض المعلومات عن الرحلة
            System.out.println("✔️ Flight found: " + selectedFlight.getFlightnumber());
            System.out.println("✈️ Airline: " + selectedFlight.getAirline());
            System.out.println("📍 Origin: " + selectedFlight.getOrigin() + " -> " + selectedFlight.getDestination());
            System.out.println("⏰ Departure: " + selectedFlight.getDepartureTime());
            System.out.println("🛬 Arrival: " + selectedFlight.getArrivalTime());
            System.out.println("Seats available in Economy: " + selectedFlight.getAvailableEconomySeats());
            System.out.println("Seats available in Business: " + selectedFlight.getAvailableBusinessSeats());
            System.out.println("Seats available in First Class: " + selectedFlight.getAvailableFirstClassSeats());

            // Step 5: التأكد من توفر المقاعد في الفئة المطلوبة
            System.out.print("Enter seat class (Economy/Business/First): ");
            String seatClass = scanner.nextLine().trim();

            // التحقق من الفئة المختارة
            while (!seatClass.equalsIgnoreCase("Economy") &&
                    !seatClass.equalsIgnoreCase("Business") &&
                    !seatClass.equalsIgnoreCase("First")) {
                System.out.println("❌ Invalid seat class. Please enter Economy, Business, or First.");
                seatClass = scanner.nextLine().trim();
            }

            int availableSeats = 0;
            if (seatClass.equalsIgnoreCase("Economy")) {
                availableSeats = selectedFlight.getAvailableEconomySeats();
            } else if (seatClass.equalsIgnoreCase("Business")) {
                availableSeats = selectedFlight.getAvailableBusinessSeats();
            } else if (seatClass.equalsIgnoreCase("First")) {
                availableSeats = selectedFlight.getAvailableFirstClassSeats();
            }

            if (availableSeats <= 0) {
                System.out.println("❌ No available seats in " + seatClass + " class.");
                return;
            }

            // Step 6: طلب عدد المقاعد التي يرغب المستخدم في حجزها
            System.out.print("How many seats would you like to book? ");
            int seatsToBook = scanner.nextInt();

            if (seatsToBook > availableSeats) {
                System.out.println("❌ Not enough available seats.");
                return;
            }

            // Step 7: إضافة تفاصيل الراكب
            scanner.nextLine();  // للانتقال للسطر التالي بعد قراءة عدد المقاعد
            System.out.print("Passenger name: ");
            String pname = scanner.nextLine();
            System.out.print("Passport number: ");
            String passport = scanner.nextLine();
            System.out.print("Date of birth (YYYY-MM-DD): ");
            String dobString = scanner.nextLine();  // قراءة تاريخ الميلاد كـ String

            // تحويل النص إلى LocalDate باستخدام DateTimeFormatter
            LocalDate dob = LocalDate.parse(dobString, Passenger.formatter);
            System.out.print("Any special requests? (leave blank if none): ");
            String specialRequest = scanner.nextLine();

            // إضافة المسافر إلى الحجز
            Passenger p = new Passenger(pname, passport, dob, specialRequest);
            Booking b = new Booking("BKG" + System.currentTimeMillis(), selectedFlight, p, seatClass, selectedFlight.CalcPrice(seatClass, seatsToBook));

            bookings.add(b);
            selectedFlight.ReserveSeat(seatClass, seatsToBook , flightFilePath);  // تحديث المقاعد المحجوزة في الرحلة

            System.out.println("Booking successful! Your reference: " + b.getBookingReference());

            // Step 8: حفظ بيانات الحجز في ملف الركاب
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(passengerFilePath, true))) {
                writer.write(p.toFileString());
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Error saving passenger data.");
            }

            // Step 9: عملية الدفع
            System.out.print("Enter payment method (Credit/Debit/Cash): ");
            String paymentMethod = scanner.nextLine().trim();
            String paymentId = "PAY" + System.currentTimeMillis();
            String transactionDate = java.time.LocalDate.now().toString();

            Payment payment = new Payment(paymentId, b.getBookingReference(), b.getTotalPrice(), paymentMethod, "Confirmed", transactionDate);
            payments.add(payment);
            FileManager.savePayments(payments , paymentFilePath);  // حفظ المدفوعات في الملف

            System.out.println("Payment successful. Reference: " + paymentId);
        }


        // 3. View bookings
        public void viewBookings(List<Booking> bookings) {
            System.out.println("\n--- Your Bookings ---");
            boolean found = false;
            for (Booking b : bookings) {
                if (b.getCustomer().getUsername().equals(this.userName)) {
                    System.out.println(b);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No bookings found.");
            }
        }

        // 4. Cancel booking
        public void cancelBooking(List<Booking> bookings, List<Payment> payments, String bookingFilePath, String paymentFilePath, String flightFilePath) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter booking reference to cancel: ");
            String ref = scanner.nextLine().trim();

            Booking toCancel = null;
            for (Booking b : bookings) {
                if (b.getBookingReference().equalsIgnoreCase(ref)
                        && b.getCustomer().getUsername().equals(this.userName)) {
                    toCancel = b;
                    break;
                }
            }

            if (toCancel != null) {
                // 1. تعديل المقاعد المتاحة في الرحلة
                Flight flight = toCancel.getFlight();
                String seatClass = toCancel.getSeatClass();

                switch (seatClass.toLowerCase()) {
                    case "economy":
                        flight.setAvailableEconomySeats(flight.getAvailableEconomySeats() + 1);
                        break;
                    case "business":
                        flight.setAvailableBusinessSeats(flight.getAvailableBusinessSeats() + 1);
                        break;
                    case "first":
                        flight.setAvailableFirstClassSeats(flight.getAvailableFirstClassSeats() + 1);
                        break;
                }

                // 2. إزالة الحجز من القائمة
                bookings.remove(toCancel);

                // 3. حذف أي مدفوعات مرتبطة بالحجز
                payments.removeIf(p -> p.getBookingReference().equalsIgnoreCase(ref));

                // 4. تحديث ملف الرحلات
                FileManager.updateFlightFile(flight, flightFilePath);

                // 5. حفظ قائمة الحجوزات الجديدة
                FileManager.saveBookings(bookings, bookingFilePath);

                // 6. حفظ قائمة المدفوعات الجديدة
                FileManager.savePayments(payments, paymentFilePath);

                System.out.println("✅ Booking canceled and seat returned to flight.");
            } else {
                System.out.println("❌ No booking found with this reference under your username.");
            }
        }


        @Override
        public String getUserId() {
            return userId;
        }

        public static Customer fromFileString(String fileString) {
            String[] data = fileString.split(",");
            String userId = data[0];
            String userName = data[1];
            String password = data[2];
            String name = data[3];
            String email = data[4];
            String contactInfo = data[5];
            String address = data[6];
            String prefrence = data[7];
            Role role = Role.valueOf(data[8]);  // Assuming you have an enum for roles
            List<Booking> bookings = new ArrayList<>(); // Need to implement logic to load bookings if necessary
            return new Customer(userId, userName, password, name, email, contactInfo, address,prefrence, role, bookings);
        }

        public String toFileString() {
            // Convert the customer data into a comma-separated string format
            return userId + "," +
                    userName + "," +
                    password + "," +
                    name + "," +
                    email + "," +
                    contactinfo + "," +
                    address + "," +
                    prefrence +","+
                    role.toString() + "," +
                    bookings.size();  // Optionally, you can add a count of bookings or the bookings' details
        }


    }

