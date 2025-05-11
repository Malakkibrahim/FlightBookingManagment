import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Flight {
    private String Flightnumber;
    private String seatClass;
    private String Airline;
    private String Origin;
    private String Destination;
    private LocalDateTime DepartureTime;
    private LocalDateTime ArrivalTime;
    private int availableEconomySeats;
    private int availableBusinessSeats;
    private int availableFirstClassSeats;
    private int bookedEconomySeats;
    private int bookedBusinessSeats;
    private int bookedFirstClassSeats;
    private double economyPrice;
    private double businessPrice;
    private double FirstClassPrice;
   private String classType;
    private int  seatsToBook;

   static String filePath = "flights.txt";
    public Flight(String Flightno, String airline, String origin, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        super();
        this.Flightnumber = Flightno;
        this.Airline = airline;
        this.Origin = origin;
        this.Destination = destination;
        this.DepartureTime = departureTime;
        this.ArrivalTime = arrivalTime;

    }

    public Flight( String airline,LocalDateTime arrivalTime, int availableBusinessSeats, int availableEconomySeats, int availableFirstClassSeats, int bookedBusinessSeats, int bookedEconomySeats, int bookedFirstClassSeats, double businessPrice, LocalDateTime departureTime, String destination, double economyPrice, double firstClassPrice, String flightnumber, String origin) {

        Airline = airline;
        ArrivalTime = arrivalTime;
        this.availableBusinessSeats = availableBusinessSeats;
        this.availableEconomySeats = availableEconomySeats;
        this.availableFirstClassSeats = availableFirstClassSeats;
        this.bookedBusinessSeats = bookedBusinessSeats;
        this.bookedEconomySeats = bookedEconomySeats;
        this.bookedFirstClassSeats = bookedFirstClassSeats;
        this.businessPrice = businessPrice;
        DepartureTime = departureTime;
        Destination = destination;
        this.economyPrice = economyPrice;
        FirstClassPrice = firstClassPrice;
        Flightnumber = flightnumber;
        Origin = origin;
    }

    public Flight(String flightNumber, String seatClass, String airline, String origin, String destination,
                  LocalDateTime departureTime, LocalDateTime arrivalTime,
                  int availableEconomySeats, int availableBusinessSeats, int availableFirstClassSeats,
                  int bookedEconomySeats, int bookedBusinessSeats, int bookedFirstClassSeats,
                  double economyPrice, double businessPrice, double firstClassPrice) {

        this.Flightnumber = flightNumber;
        this.seatClass = seatClass;
        this.Airline = airline;
        this.Origin = origin;
        this.Destination = destination;
        this.DepartureTime = departureTime;
        this.ArrivalTime = arrivalTime;
        this.availableEconomySeats = availableEconomySeats;
        this.availableBusinessSeats = availableBusinessSeats;
        this.availableFirstClassSeats = availableFirstClassSeats;
        this.bookedEconomySeats = bookedEconomySeats;
        this.bookedBusinessSeats = bookedBusinessSeats;
        this.bookedFirstClassSeats = bookedFirstClassSeats;
        this.economyPrice = economyPrice;
        this.businessPrice = businessPrice;
        this.FirstClassPrice = firstClassPrice;
    }

    public Flight() {
        // Constructor افتراضي لا يعمل شيء
    }


    public void setDepartureTime(LocalDateTime departureTime) {
        this.DepartureTime = departureTime;
    }

    public void setFlightnumber(String flightnumber) {
        Flightnumber = flightnumber;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.ArrivalTime = arrivalTime;
    }

    public void setAvailableBusinessSeats(int availableBusinessSeats) {
        this.availableBusinessSeats = availableBusinessSeats;
    }

    public void setAirline(String airline) {
        Airline = airline;
    }

    public void setBookedBusinessSeats(int bookedBusinessSeats) {
        this.bookedBusinessSeats = bookedBusinessSeats;
    }

    public void setBookedEconomySeats(int bookedEconomySeats) {
        this.bookedEconomySeats = bookedEconomySeats;
    }

    public void setBookedFirstClassSeats(int bookedFirstClassSeats) {
        this.bookedFirstClassSeats = bookedFirstClassSeats;
    }

    public void setBusinessPrice(double businessPrice) {
        this.businessPrice = businessPrice;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public void setEconomyPrice(double economyPrice) {
        this.economyPrice = economyPrice;
    }

    public void setFirstClassPrice(double firstClassPrice) {
        FirstClassPrice = firstClassPrice;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public void setAvailableEconomySeats(int availableEconomySeats) {
        this.availableEconomySeats = availableEconomySeats;
    }

    public void setAvailableFirstClassSeats(int availableFirstClassSeats) {
        this.availableFirstClassSeats = availableFirstClassSeats;
    }

    public String getAirline() {
        return Airline;
    }

    public LocalDateTime getArrivalTime() {
        return ArrivalTime;
    }

    public int getAvailableBusinessSeats() {
        return availableBusinessSeats;
    }

    public int getAvailableEconomySeats() {
        return availableEconomySeats;
    }

    public int getAvailableFirstClassSeats() {
        return availableFirstClassSeats;
    }

    public int getBookedBusinessSeats() {
        return bookedBusinessSeats;
    }

    public int getBookedEconomySeats() {
        return bookedEconomySeats;
    }

    public int getBookedFirstClassSeats() {
        return bookedFirstClassSeats;
    }

    public double getBusinessPrice() {
        return businessPrice;
    }

    public LocalDateTime getDepartureTime() {
        return DepartureTime;
    }

    public String getDestination() {
        return Destination;
    }

    public double getEconomyPrice() {
        return economyPrice;
    }

    public double getFirstClassPrice() {
        return FirstClassPrice;
    }

    public String getFlightnumber() {
        return Flightnumber;
    }

    public String getOrigin() {
        return Origin;
    }

    public void increaseEconomySeats() {
        availableEconomySeats++;
    }

    public void increaseBusinessSeats() {
        availableBusinessSeats++;
    }

    public void increaseFirstClassSeats() {
        availableFirstClassSeats++;
    }

    public void saveToFile() {
        File file = new File(filePath);
        List<Flight> existingFlights = new ArrayList<>();

        // Step 1: قراءة الرحلات الموجودة
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    existingFlights.add(Flight.fromFileString(line));
                }
            } catch (IOException e) {
                System.out.println("❌ Error reading flights: " + e.getMessage());
                return;
            }
        }

        // Step 2: التأكد إن رقم الرحلة مش موجود
        for (Flight f : existingFlights) {
            if (f.getFlightnumber().equalsIgnoreCase(this.Flightnumber)) {
                System.out.println("⚠️ Flight with number " + this.Flightnumber + " already exists!");
                return;
            }
        }

        // Step 3: إضافة الرحلة للملف
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(this.toFileString());
            writer.newLine();
            System.out.println("✅ Flight saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error writing flight: " + e.getMessage());
        }
    }

    public static Flight fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length < 16) {
            throw new IllegalArgumentException("Invalid flight record: " + line);
        }

        return new Flight(
                parts[0].trim(),  // Flightnumber
                parts[1].trim(),  // seatClass
                parts[2].trim(),  // Airline
                parts[3].trim(),  // Origin
                parts[4].trim(),  // Destination
                LocalDateTime.parse(parts[5].trim()),
                LocalDateTime.parse(parts[6].trim()),
                Integer.parseInt(parts[7].trim()),  // availableEconomySeats
                Integer.parseInt(parts[8].trim()),  // availableBusinessSeats
                Integer.parseInt(parts[9].trim()),  // availableFirstClassSeats
                Integer.parseInt(parts[10].trim()), // bookedEconomySeats
                Integer.parseInt(parts[11].trim()), // bookedBusinessSeats
                Integer.parseInt(parts[12].trim()), // bookedFirstClassSeats
                Double.parseDouble(parts[13].trim()), // economyPrice
                Double.parseDouble(parts[14].trim()), // businessPrice
                Double.parseDouble(parts[15].trim())  // FirstClassPrice
        );
    }






    public boolean getAvailableSeats(String flightNumber, int numberOfSeats, boolean decrease) {
        if (!this.Flightnumber.equals(flightNumber)) {
            System.out.println("رقم الرحلة غير متطابق.");
            return false;
        }

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


    public void SHOWMENU(){
    Scanner scanner = new Scanner(System.in);

        System.out.println("Calc Price...");
        System.out.println("Update Schedule...");
        System.out.println("Reserve Seat....");
        System.out.println("Check Availability....");
        int Ekhtarrkm = scanner.nextInt();
        switch (Ekhtarrkm) {
            case 1:
        CalcPrice();
        break;
            case 2:
                updateScheduleInFile(filePath);
                break;
            case 3:
                ReserveSeat(String classType, int seatsToBook);
                break;
            case 4:
                CheckAvailability(filePath);
                break;
            default:
                System.out.println("Invalid rkm ya ngm");
                SHOWMENU();
                break;

        }
}
    public double CalcPrice(){
        Scanner scanner = new Scanner(System.in);

        // طلب نوع المقعد والتأكد من صحته
        String Classtype;
        while (true) {
            System.out.println("Enter a ClassType (economy, business, or first class):");
            Classtype = scanner.nextLine().trim().toLowerCase();  // تحويل الحروف إلى صغيرة لإزالة تأثير الحروف الكبيرة والصغيرة

            // التحقق من صحة نوع المقعد المدخل
            if (Classtype.equals("economy") || Classtype.equals("business") || Classtype.equals("first class")) {
                break;  // إذا كان النوع صحيحًا، نخرج من الحلقة
            } else {
                System.out.println("Invalid class type. Please enter 'economy', 'business', or 'first class'.");
            }
        }

        // طلب الكمية
        System.out.println("Enter a Quantity:");
        int Quantity = scanner.nextInt();

        // حساب السعر بناءً على نوع المقعد
        if (Classtype.equals("economy")) {
            return economyPrice * Quantity;
        } else if (Classtype.equals("business")) {
            return businessPrice * Quantity;
        } else if (Classtype.equals("first class")) {
            return FirstClassPrice * Quantity;
        }

        return -1;  // في حال كان هناك خطأ آخر
    }

    public String toFileString() {
        return String.join(",",
                Flightnumber, Airline, Origin, Destination,
                DepartureTime.toString(), ArrivalTime.toString(),
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
            flight.setFlightnumber(parts[0]);
            flight.setSeatClass(parts[1]);
            flight.setAirline(parts[2]);
            flight.setOrigin(parts[3]);
            flight.setDestination(parts[4]);
            flight.setDepartureTime(LocalDateTime.parse(parts[5]));
            flight.setArrivalTime(LocalDateTime.parse(parts[6]));
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
            String targetFlightNumber = scanner.nextLine().trim();

            // 2. اقرأ الرحلات من الملف
            List<Flight> flights = new ArrayList<>();
            boolean flightExists = false;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Flight flight = Flight.FromFileString(line);
                    flights.add(flight);
                    if (flight.getFlightnumber().equalsIgnoreCase(targetFlightNumber)) {
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
                if (f.getFlightnumber().equalsIgnoreCase(targetFlightNumber)) {
                    f.setDepartureTime(newDeparture);
                    f.setArrivalTime(newArrival);
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
        String targetFlightNumber = scanner.nextLine().trim();

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
                if (flight.getFlightnumber().equalsIgnoreCase(targetFlightNumber)) {
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

    public void ReserveSeat(String classType, int seatsToBook, String flightFilePath) {
        if (classType.equalsIgnoreCase("economy") && seatsToBook <= availableEconomySeats) {
            availableEconomySeats -= seatsToBook;
        } else if (classType.equalsIgnoreCase("business") && seatsToBook <= availableBusinessSeats) {
            availableBusinessSeats -= seatsToBook;
        } else if (classType.equalsIgnoreCase("first class") && seatsToBook <= availableFirstClassSeats) {
            availableFirstClassSeats -= seatsToBook;
        } else {
            System.out.println("❌ Not enough available seats in " + classType + " class.");
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

                // تحقق إذا كانت هذه الرحلة هي التي تم تحديثها
                if (flight.getFlightnumber().equals(this.getFlightnumber())) {
                    // تم تعديل المقاعد في الرحلة
                    lines.add(flight.toFileString());  // تحديث الرحلة
                } else {
                    lines.add(line);  // إضافة الرحلات الأخرى كما هي
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



}

