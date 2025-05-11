import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Flight {
    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private FlightSeat economyFilghtSeats;
    private FlightSeat businessFilghtSeats;
    private FlightSeat firstClassFilghtSeats;

    public Flight(String flightNumber, String airline, String origin, String destination, 
    LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Flight(String flightNumber, String airline, String origin, String destination, 
    LocalDateTime departureTime, LocalDateTime arrivalTime,
    int availableEconomySeats, int bookedEconomySeats, double economyPrice, 
    int availableBusinessSeats, int bookedBusinessSeats, double businessPrice,
    int availableFirstClassSeats,  int bookedFirstClassSeats,  double firstClassPrice) {
        this(flightNumber, airline, origin, destination, departureTime, arrivalTime);
        this.economyFilghtSeats = new FlightSeat(SeatClass.Economy, economyPrice, availableEconomySeats, bookedEconomySeats);
        this.businessFilghtSeats = new FlightSeat(SeatClass.Business, businessPrice, availableBusinessSeats, bookedBusinessSeats);
        this.firstClassFilghtSeats = new FlightSeat(SeatClass.FIrstClass, firstClassPrice, availableFirstClassSeats, bookedFirstClassSeats);
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setEconomyPrice(double price) {
        if(price < 0)
            System.out.println("Neagtive value not allowed");
        else:
            this.price = price;
    }

    public void setAvailableEconomySeats(int available) {
        if(available < 0)
            System.out.println("Neagtive value not allowed");
        else
            economyFilghtSeats.available = available;
    }
    
    public void setBookedEconomySeats(int booked) {
        if(booked < 0)
            System.out.println("Neagtive value not allowed");
        else
            economyFilghtSeats.booked = booked;
    }

    public void setBusinessPrice(double price) {
        if(price < 0)
            System.out.println("Neagtive value not allowed");
        else:
            businessFilghtSeats.price = price;
    }

    public void setAvailableBusinessSeats(int available) {
        if(available < 0)
            System.out.println("Neagtive value not allowed");
        else
            businessFilghtSeats.available = available;
    }
    
    public void setBookedBusinessSeats(int booked) {
        if(booked < 0)
            System.out.println("Neagtive value not allowed");
        else
            businessFilghtSeats.booked = booked;
    }

    public void setFirstClassPrice(double price) {
        if(price < 0)
            System.out.println("Neagtive value not allowed");
        else
            firstClassFilghtSeats.price = price;
    }

    public void setAvailableFirstClassSeats(int available) {
        if(available < 0)
            System.out.println("Neagtive value not allowed");
        else
            firstClassFilghtSeats.available = available;
    }
    
    public void setBookedFirstClassSeats(int booked) {
        if(booked < 0)
            System.out.println("Neagtive value not allowed");
        else
            firstClassFilghtSeats.booked = booked;
    }
    
    public String getAirline() {
        return airline;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public String getDestination() {
        return destination;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public boolean getAvailableSeats(int numberOfSeats, boolean decrease) {
        // تحديد نوع الكرسي
        String seatClass = this.seatClass; // تأكد إنك عندك seatClass معرفة في Flight
        if (seatClass.equalsIgnoreCase("Economy")) {
            if (decrease) {
                if (this.availableEconomySeats >= numberOfSeats) {
                    this.availableEconomySeats -= numberOfSeats;
                    return true;
                } else {
                    System.out.println("لا توجد مقاعد Economy كافية.");
                    return false;
                }
            } else {
                this.availableEconomySeats += numberOfSeats;
                return true;
            }
        } else if (seatClass.equalsIgnoreCase("Business")) {
            if (decrease) {
                if (this.availableBusinessSeats >= numberOfSeats) {
                    this.availableBusinessSeats -= numberOfSeats;
                    return true;
                } else {
                    System.out.println("لا توجد مقاعد Business كافية.");
                    return false;
                }
            } else {
                this.availableBusinessSeats += numberOfSeats;
                return true;
            }
        } else {
            System.out.println("نوع المقعد غير معروف.");
            return false;
        }
    }

    public double calcPrice(SeatClass seatClass, int quantity) {
        switch (seatClass) {
            case Economy:
                return economyFilghtSeats.price * quantity;
            case Business:
                return businessFilghtSeats.price * quantity;
            case FirstClass:
                return firstClassFilghtSeats.price * quantity;
            default:
                throw new IllegalArgumentException("This seat type is not supported");
        }
    }

    public String toFileString() {
        return String.join(",",
                flightNumber, airline, origin, destination,
                departureTime.toString(), arrivalTime.toString(),
                String.valueOf(availableEconomySeats), String.valueOf(availableBusinessSeats), String.valueOf(availableFirstClassSeats),
                 String.valueOf(bookedEconomySeats), String.valueOf(bookedBusinessSeats), String.valueOf(bookedFirstClassSeats)
                ,String.valueOf(economyPrice), String.valueOf(businessPrice),
                String.valueOf(FirstClassPrice) // إضافة سعر الفئة الأولى إذا كنت بحاجة له
        );
    }


    public static Flight FromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 16) {
            Flight flight = new Flight();
            flight.setflightNumber(parts[0]);
            flight.setSeatClass(parts[1]);
            flight.setairline(parts[2]);
            flight.setorigin(parts[3]);
            flight.setdestination(parts[4]);
            flight.setdepartureTime(LocalDateTime.parse(parts[5]));
            flight.setarrivalTime(LocalDateTime.parse(parts[6]));
            flight.setAvailableEconomySeats(Integer.parseInt(parts[7]));
            flight.setAvailableBusinessSeats(Integer.parseInt(parts[8]));
            flight.setAvailableFirstClassSeats(Integer.parseInt(parts[9]));
            flight.setBookedEconomySeats(Integer.parseInt(parts[10]));
            flight.setBookedBusinessSeats(Integer.parseInt(parts[11]));
            flight.setBookedFirstClassSeats(Integer.parseInt(parts[12]));
            flight.setEconomyPrice(Double.parseDouble(parts[13]));
            flight.setBusinessPrice(Double.parseDouble(parts[14]));
            flight.setFirstClassPrice(Double.parseDouble(parts[15]));
            return flight;
        } else {
            System.err.println("Error: Expected 16 parts in line but found " + parts.length + ": " + line);
            return null;
        }
    }



    public static void updateScheduleInFile(String filePath) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try {
            // 1. خدي رقم الرحلة من المستخدم
            System.out.print("🔍 Enter flight number to update: ");
            String targetflightNumber = scanner.nextLine().trim();

            // 2. اقرأ الرحلات من الملف
            List<Flight> flights = new ArrayList<>();
            boolean flightExists = false;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Flight flight = Flight.FromFileString(line);
                    flights.add(flight);
                    if (flight.getflightNumber().equalsIgnoreCase(targetflightNumber)) {
                        flightExists = true; // إذا كانت الرحلة موجودة في الملف
                    }
                }
            }

            if (!flightExists) {
                System.out.println("❌ Flight number not found in the file.");
                return; // إذا لم يتم العثور على الرحلة، نتوقف
            }

            // 3. خدي المواعيد الجديدة
            System.out.print("✈️ Enter new departure time (yyyy-MM-dd HH:mm): ");
            String depInput = scanner.nextLine().trim();
            LocalDateTime newDeparture = LocalDateTime.parse(depInput, formatter);

            System.out.print("🛬 Enter new arrival time (yyyy-MM-dd HH:mm): ");
            String arrInput = scanner.nextLine().trim();
            LocalDateTime newArrival = LocalDateTime.parse(arrInput, formatter);

            if (newDeparture.isBefore(LocalDateTime.now())) {
                System.out.println("❌ Departure time must be in the future.");
                return;
            } else if (newArrival.isBefore(newDeparture)) {
                System.out.println("❌ Arrival time must be after departure time.");
                return;
            }

            // 4. عدل الرحلة المطلوبة
            boolean updated = false;
            for (Flight f : flights) {
                if (f.getflightNumber().equalsIgnoreCase(targetflightNumber)) {
                    f.setdepartureTime(newDeparture);
                    f.setarrivalTime(newArrival);
                    updated = true;
                    break;
                }
            }

            // 5. اكتب الرحلات المعدلة في الملف
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (Flight f : flights) {
                    writer.write(f.toFileString());
                    writer.newLine();
                }
            }

            if (updated) {
                System.out.println("✅ Schedule updated and saved to file.");
            }

        } catch (Exception e) {
            System.out.println("❌ Invalid input. Please use correct date format (yyyy-MM-dd HH:mm).");
        }
    }

    public static void CheckAvailability(String filePath) {
        Scanner scanner = new Scanner(System.in);

        // 1. اسأل المستخدم عن رقم الرحلة والفئة
        System.out.print("🔍 Enter flight number to check availability: ");
        String targetflightNumber = scanner.nextLine().trim();

        System.out.print("🛫 Enter the class (economy, business, or first class): ");
        String classType = scanner.nextLine().trim().toLowerCase();

        // تحقق من الفئة المدخلة
        if (!(classType.equals("economy") || classType.equals("business") || classType.equals("first class"))) {
            System.out.println("❌ Invalid class type. Please enter 'economy', 'business', or 'first class'.");
            return;
        }

        // 2. قراءة الرحلات من الملف
        List<Flight> flights = new ArrayList<>();
        boolean flightExists = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Flight flight = Flight.FromFileString(line);
                flights.add(flight);

                // 3. تحقق إذا كانت الرحلة موجودة في الملف
                if (flight.getflightNumber().equalsIgnoreCase(targetflightNumber)) {
                    flightExists = true;

                    // 4. حسب الفئة المدخلة، طبع الأماكن المتاحة
                    if (classType.equals("economy")) {
                        System.out.println("🚀 Available Economy Seats: " + flight.getAvailableEconomySeats());
                    } else if (classType.equals("business")) {
                        System.out.println("🚀 Available Business Seats: " + flight.getAvailableBusinessSeats());
                    } else if (classType.equals("first class")) {
                        System.out.println("🚀 Available First Class Seats: " + flight.getAvailableFirstClassSeats());
                    }
                    break; // لا داعي للاستمرار في البحث بعد إيجاد الرحلة المطلوبة
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error reading file or parsing flight data.");
        }

        // 5. إذا لم يتم العثور على الرحلة في الملف
        if (!flightExists) {
            System.out.println("❌ Flight number not found.");
        }
    }

    public static void ReserveSeat(String filePath) {
        Scanner scanner = new Scanner(System.in);

        // 1. اسأل المستخدم عن رقم الرحلة والفئة
        System.out.print("🔍 Enter flight number to reserve seats: ");
        String targetflightNumber = scanner.nextLine().trim();

        System.out.print("🛫 Enter the class (economy, business, or first class): ");
        String classType = scanner.nextLine().trim().toLowerCase();

        // تحقق من الفئة المدخلة
        if (!(classType.equals("economy") || classType.equals("business") || classType.equals("first class"))) {
            System.out.println("❌ Invalid class type. Please enter 'economy', 'business', or 'first class'.");
            return;
        }

        // بعد الحجز، قم بتحديث ملف الرحلات باستخدام الكائن الحالي
        updateFlightFile(this, flightFilePath);
    }


    // هذه الطريقة لتحديث بيانات الرحلات في الملف بعد التعديل
    public void updateFlightFile(Flight flight, String flightFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader("flights.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("flights.txt"))) {

            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                flight = Flight.fromFileString(line);

                // 3. تحقق إذا كانت الرحلة موجودة في الملف
                if (flight.getflightNumber().equalsIgnoreCase(targetflightNumber)) {
                    flightExists = true;

                    // تحقق من المقاعد المتاحة في الفئة المدخلة
                    if (classType.equals("economy") && flight.getAvailableEconomySeats() > 0) {
                        seatsAvailable = true;
                    } else if (classType.equals("business") && flight.getAvailableBusinessSeats() > 0) {
                        seatsAvailable = true;
                    } else if (classType.equals("first class") && flight.getAvailableFirstClassSeats() > 0) {
                        seatsAvailable = true;
                    }

                    if (!seatsAvailable) {
                        System.out.println("❌ No available seats in the " + classType + " class.");
                        return;
                    }

                    // 4. حجز المقاعد
                    System.out.print("✈️ Enter number of seats to reserve: ");
                    int seatsToReserve = scanner.nextInt();

                    // تحقق من عدد المقاعد المتاحة
                    if (seatsToReserve <= 0) {
                        System.out.println("❌ Please enter a valid number of seats.");
                        return;
                    }

                    // تحقق من أن عدد المقاعد المطلوبة لا يتجاوز العدد المتاح
                    if ((classType.equals("economy") && seatsToReserve > flight.getAvailableEconomySeats()) ||
                            (classType.equals("business") && seatsToReserve > flight.getAvailableBusinessSeats()) ||
                            (classType.equals("first class") && seatsToReserve > flight.getAvailableFirstClassSeats())) {
                        System.out.println("❌ Not enough available seats.");
                        return;
                    }

                    // تحديث المقاعد المتاحة بناءً على الفئة المدخلة
                    if (classType.equals("economy")) {
                        flight.setAvailableEconomySeats(flight.getAvailableEconomySeats() - seatsToReserve);
                    } else if (classType.equals("business")) {
                        flight.setAvailableBusinessSeats(flight.getAvailableBusinessSeats() - seatsToReserve);
                    } else if (classType.equals("first class")) {
                        flight.setAvailableFirstClassSeats(flight.getAvailableFirstClassSeats() - seatsToReserve);
                    }

                    System.out.println("✅ Successfully reserved " + seatsToReserve + " seats in " + classType + " class.");
                    break; // لا داعي للاستمرار في البحث بعد إيجاد الرحلة
                }
            }

            // الكتابة مرة أخرى في الملف
            for (String fileLine : lines) {
                writer.write(fileLine);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("❌ Error while updating flight data.");
        }
    }

    @Override
    public String toString() {
        return flightNumber + "," +
               seatClass + "," +
               airline + "," +
               origin + "," +
               destination + "," +
               departureTime + "," +
               arrivalTime + "," +
               economyFilghtSeats.getAvailable() + "," +
               businessFilghtSeats.getAvailable() + "," +
               firstClassFilghtSeats.getAvailable() + "," +
               economyFilghtSeats.getBooked() + "," +
               businessFilghtSeats.getBooked() + "," +
               firstClassFilghtSeats.getBooked() + "," +
               economyFilghtSeats.getPrice() + "," +
               businessFilghtSeats.getPrice() + "," +
               firstClassFilghtSeats.getPrice();
    }
}

