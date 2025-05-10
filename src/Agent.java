import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.*;


public class Agent extends User {
    private UUID AgentID;
    private String Depart;
    private double Commision;
    private List<Flight> flights = new ArrayList<>();


    public Agent(String Depart,double Commision,String userId, String userName, String password, String name, String email, String contactinfo, Role role, UUID agentID) {
        super(userId, userName, password, name, email, contactinfo, role);
        this.AgentID = agentID;
        this.Commision = Commision;
        this.Depart = Depart;

    }



    public void setAgentID(UUID AgentID) {
        this.AgentID = AgentID;
    }

    public void setDepart(String Depart) {
        this.Depart = Depart;
    }

    public void setCommision(double Commision) {
        this.Commision = Commision;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactinfo(String contactinfo) {
        this.contactinfo = contactinfo;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UUID getAgentID() {
        return AgentID;
    }

    public String getDepart() {
        return Depart;
    }

    public double getCommision() {
        return Commision;
    }


    @Override
    public void SHOWMENU() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---- Agent Menu ----");
        System.out.println("1. Manage Flights");
        System.out.println("2. Create Booking for Customer");
        System.out.println("3. Modify Booking");
        System.out.println("4. Generate Reports");
        System.out.println("5. Update Profile");
        System.out.println("6. Logout");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                manageFlights();
                break;
            case 2:
                System.out.println("Creating Booking for Customer...");
                createBooking();


                break;
            case 3:
                // Call a method to modify booking (to be implemented)
                System.out.println("Modifying Booking...");
                break;
            case 4:
                generateReports();
                break;
            case 5:
                updateprofile();
                break;
            case 6:
                logout();
                break;
            default:
                System.out.println("Invalid option. Try again.");
                SHOWMENU();
                break;
        }

    }


    // Method to manage flights
    public void manageFlights() {
        System.out.println("Managing flights...");
        Scanner scanner = new Scanner(System.in);
        System.out.println("---- Manage Flights ----");
        System.out.println("1. Add New Flight");
        System.out.println("2. Edit Existing Flight");
        System.out.println("3. Remove Flight");
        System.out.println("4. View All Flights");
        System.out.println("5. Back to Menu");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addNewFlight();
                break;
            case 2:
                editFlight();
                break;
            case 3:
                removeFlight();
                break;
            case 4:
                viewFlights();
                break;
            case 5:
                SHOWMENU();  // العودة إلى القائمة الرئيسية
                break;
            default:
                System.out.println("Invalid option. Try again.");
                manageFlights();
                break;
        }
    }
    Scanner scanner = new Scanner(System.in);
    public Flight addNewFlight() {

        System.out.println("Enter flight number: ");
        String flightNumber = scanner.nextLine();
        System.out.println("Enter airline: ");
        String airline = scanner.nextLine();
        System.out.println("Enter origin city: ");
        String origin = scanner.nextLine();
        System.out.println("Enter destination city: ");
        String destination = scanner.nextLine();
        System.out.println("Enter departure time (yyyy-MM-dd HH:mm): ");
       LocalDateTime departureTime = LocalDateTime.parse(scanner.nextLine());
        System.out.println("Enter arrival time (yyyy-MM-dd HH:mm): ");
       LocalDateTime arrivalTime = LocalDateTime.parse(scanner.nextLine());


        Flight newFlight = new Flight(flightNumber, airline, origin, destination, departureTime, arrivalTime);
                flights.add(newFlight);

        System.out.println("New flight added successfully!");
        return newFlight;
    }

    public List<Integer> getSeatNumbers(String seatClassName, Scanner scanner) {
        List<Integer> seatNumbers = new ArrayList<>();
        System.out.println("Enter seat numbers for " + seatClassName + " (type -1 to finish):");

        while (true) {
            System.out.print("Seat number: ");
            int seat = scanner.nextInt();

            if (seat == -1) {
                break;
            }

            if (seat < 1) {
                System.out.println("❌ Invalid seat number. Try again.");
            } else {
                seatNumbers.add(seat);
            }
        }

        return seatNumbers;
    }

    public void editFlight() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter flight number to edit: ");
        String flightNumber = scanner.nextLine();

        Flight flightToEdit = findFlight(flightNumber);

        if (flightToEdit != null) {
            System.out.println("Editing flight: " + flightNumber);

            // السؤال إذا كان المستخدم يريد تغيير أوقات المغادرة والوصول
            System.out.println("Do you want to change the departure and arrival time? (yes/no)");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes") || response.equals("y")) {
                System.out.println("Enter new departure time (yyyy-MM-dd HH:mm): ");
                String newDepartureTime = scanner.nextLine();
                System.out.println("Enter new arrival time (yyyy-MM-dd HH:mm): ");
                String newArrivalTime = scanner.nextLine();

                // تحديث الأوقات
                flightToEdit.setDepartureTime(LocalDateTime.parse(newDepartureTime));
                flightToEdit.setArrivalTime(LocalDateTime.parse(newArrivalTime));
                System.out.println("✅ Flight schedule updated successfully.");
            } else {
                System.out.println("⏳ Keeping the old schedule.");
            }

            System.out.println("Do you want to edit the available seats for a class? (yes/no)");
            String seatEditResponse = scanner.nextLine().trim().toLowerCase();

            if (seatEditResponse.equals("yes") || seatEditResponse.equals("y")) {
                System.out.println("Which class do you want to update? (Economy/Business/First): ");
                String flightClass = scanner.nextLine().trim().toLowerCase();

                System.out.println("Enter new number of available seats: ");
                int newSeatCount = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (flightClass) {
                    case "economy":
                        flightToEdit.setAvailableEconomySeats(newSeatCount);
                        break;
                    case "business":
                        flightToEdit.setAvailableBusinessSeats(newSeatCount);
                        break;
                    case "first":
                        flightToEdit.setAvailableFirstClassSeats(newSeatCount);
                        break;
                    default:
                        System.out.println("❌ Invalid class.");
                        return;
                }
                System.out.println("✅ Seat count updated successfully.");
            }

            // TODO: Save the updated flight info to the file
            updateFlightInFile(flightToEdit);

        } else {
            System.out.println("❌ Flight not found.");
        }
    }
    public void updateFlightInFile(Flight updatedFlight) {
        List<Flight> flights = new ArrayList<>();
        boolean flightUpdated = false;

        // قراءة كل الرحلات من الملف
        try (BufferedReader reader = new BufferedReader(new FileReader("flights.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Flight flight = Flight.FromFileString(line);

                // تحديث الرحلة المطابقة
                if (flight.getFlightnumber().equals(updatedFlight.getFlightnumber())) {
                    flights.add(updatedFlight);  // استبدال الرحلة القديمة بالمعدّلة
                    flightUpdated = true;
                } else {
                    flights.add(flight);  // إضافة باقي الرحلات كما هي
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error reading the flights file: " + e.getMessage());
            return;
        }

        // الكتابة إلى الملف بعد التعديل
        if (flightUpdated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("flights.txt"))) {
                for (Flight flight : flights) {
                    writer.write(flight.toFileString());
                    writer.newLine();
                }
                System.out.println("✅ Flight updated in the file.");
            } catch (Exception e) {
                System.out.println("❌ Error writing to the flights file: " + e.getMessage());
            }
        } else {
            System.out.println("❌ Flight not found to update.");
        }
    }




    public Flight findFlight(String flightNumber) {
        for (Flight f : flights) {
            if (f.getFlightnumber().equals(flightNumber)) {
                return f;
            }
        }
        return null;
    }

    public void removeFlight() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("✈ Remove Flight");
        System.out.print("Enter flight number to remove: ");
        String flightNumber = scanner.nextLine();

        Flight flightToRemove = findFlight(flightNumber);

        if (flightToRemove != null) {
            System.out.print("Are you sure you want to delete this flight? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("yes") || confirm.equals("y")) {

                flights.remove(flightToRemove);
                System.out.println("✅ Flight " + flightNumber + " removed successfully.");
            } else {
                System.out.println("❌ Flight deletion canceled.");
            }
        } else {
            System.out.println("❌ Flight not found.");
        }
    }

    public void viewFlights() {
        if (flights.isEmpty()) {
            System.out.println("✈ No flights available.");
        } else {
            System.out.println("📋 List of All Flights:");
            for (Flight flight : flights) {
                System.out.println("------------------------------");
                System.out.println("Flight Number: " + flight.getFlightnumber());
                System.out.println("Airline: " + flight.getAirline());
                System.out.println("Origin: " + flight.getOrigin());
                System.out.println("Destination: " + flight.getDestination());
                System.out.println("Departure Time: " + flight.getDepartureTime());
                System.out.println("Arrival Time: " + flight.getArrivalTime());
                System.out.println("Economy Seats: " + flight.getAvailableEconomySeats());
                System.out.println("Business Seats: " + flight.getAvailableBusinessSeats());
                System.out.println("First Class Seats: " + flight.getAvailableFirstClassSeats());
            }
            System.out.println("------------------------------");
        }
    }


    public void createBooking() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter flight number: ");
        String flightNumber = scanner.nextLine();

        Flight selectedFlight = findFlight(flightNumber);
        if (selectedFlight == null) {
            System.out.println("❌ Flight not found.");
            return;
        }

        System.out.println("Choose seat class (economy/business/first): ");
        String seatClass = scanner.nextLine().trim().toLowerCase();

        boolean success = false;

        // التحقق من نوع المقعد الذي اختاره العميل
        switch (seatClass.toLowerCase()) {
            case "economy":
                if (selectedFlight.getAvailableEconomySeats() > 0) {
                    selectedFlight.setAvailableEconomySeats(selectedFlight.getAvailableEconomySeats() - 1); // تحديث المقاعد
                    selectedFlight.setAvailableBusinessSeats(selectedFlight.getAvailableEconomySeats() + 1);
                    success = true;
                } else {
                    System.out.println("Unavailable seats in economic class");
                }
                break;

            case "business":
                if (selectedFlight.getAvailableBusinessSeats() > 0) {
                    selectedFlight.setAvailableBusinessSeats(selectedFlight.getAvailableBusinessSeats() - 1); // تحديث المقاعد
                    selectedFlight.setAvailableBusinessSeats(selectedFlight.getBookedBusinessSeats() + 1);
                    success = true;
                } else {
                    System.out.println("There is no available seats in business class ");
                }
                break;

            case "first":
                if (selectedFlight.getAvailableFirstClassSeats() > 0) {
                    selectedFlight.setAvailableFirstClassSeats(selectedFlight.getAvailableFirstClassSeats() - 1); // تحديث المقاعد
                    selectedFlight.setAvailableBusinessSeats(selectedFlight.getAvailableFirstClassSeats() + 1);
                    success = true;
                } else {
                    System.out.println("Unavailable seats in First Class");
                }
                break;

            default:
                System.out.println("❌ Invalid seat class.");
                return;
        }

        if (success) {
            System.out.println("✅ Booking successful for " + customerName + " on flight " + flightNumber +".");
        }
    }


    public void modifyBooking() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter flight number: ");
        String flightNumber = scanner.nextLine();

        Flight flight = findFlight(flightNumber);
        if (flight == null) {
            System.out.println("❌ Flight not found.");
            return;
        }

        System.out.println("Do you want to keep the same flight? (yes/no)");
        String sameFlight = scanner.nextLine().trim().toLowerCase();
        if (sameFlight.equals("no")) {
            System.out.print("Enter new flight number: ");
            String newFlightNumber = scanner.nextLine();
            Flight newFlight = findFlight(newFlightNumber);
            if (newFlight == null) {
                System.out.println("❌ New flight not found.");
                return;
            }
            flight = newFlight;
            System.out.println("✅ Flight changed.");
        }

        System.out.println("Do you want to change the date/time? (yes/no)");
        String changeTime = scanner.nextLine().trim().toLowerCase();
        if (changeTime.equals("yes")) {
            try {
                System.out.print("Enter new departure time (yyyy-MM-dd HH:mm): ");
                String dep = scanner.nextLine();
                System.out.print("Enter new arrival time (yyyy-MM-dd HH:mm): ");
                String arr = scanner.nextLine();

                flight.setDepartureTime(LocalDateTime.parse(dep));
                flight.setArrivalTime(LocalDateTime.parse(arr));
                System.out.println("✅ Schedule updated.");
            } catch (Exception e) {
                System.out.println("❌ Invalid date format.");
                return;
            }
        }

        System.out.println("Do you want to change seat class? (yes/no)");
        String changeClass = scanner.nextLine().trim().toLowerCase();
        if (changeClass.equals("yes")) {
            System.out.print("Enter your current seat class (economy/business/first): ");
            String oldClass = scanner.nextLine().trim().toLowerCase();

            System.out.print("Enter new seat class (economy/business/first): ");
            String newClass = scanner.nextLine().trim().toLowerCase();

            // تحقق من توفر مقعد في الكلاس الجديد
            boolean canChange = false;

            if (newClass.equals("economy") && flight.getAvailableEconomySeats() > 0) {
                flight.setAvailableEconomySeats(flight.getAvailableEconomySeats() - 1);
                canChange = true;
            } else if (newClass.equals("business") && flight.getAvailableBusinessSeats() > 0) {
                flight.setAvailableBusinessSeats(flight.getAvailableBusinessSeats() - 1);
                canChange = true;
            } else if (newClass.equals("first") && flight.getAvailableFirstClassSeats() > 0) {
                flight.setAvailableFirstClassSeats(flight.getAvailableFirstClassSeats() - 1);
                canChange = true;
            }

            if (canChange) {
                // استرجاع المقعد من الكلاس القديم
                if (oldClass.equals("economy")) {
                    flight.setAvailableEconomySeats(flight.getAvailableEconomySeats() + 1);
                } else if (oldClass.equals("business")) {
                    flight.setAvailableBusinessSeats(flight.getAvailableBusinessSeats() + 1);
                } else if (oldClass.equals("first")) {
                    flight.setAvailableFirstClassSeats(flight.getAvailableFirstClassSeats() + 1);
                }

                System.out.println("✅ Seat class changed successfully.");
            } else {
                System.out.println("❌ No available seats in new class.");
            }
        } else {
            System.out.println("⏳ Keeping the same seat class.");
        }

        // تحديث الرحلة في الملف (لو عندك الدالة دي)
        // updateFlightInFile(flight);
    }

    public void generateReports () {
            Scanner scanner = new Scanner(System.in);

            System.out.println("---- Generate Report Menu ----");
            System.out.println("1. View All Flights");
            System.out.println("2. View Booked Seats");
            System.out.println("3. View Available Seats");
            System.out.println("4. Back to Agent Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewFlights();
                    break;
                case 2:
                    viewBookedSeats();
                    break;
                case 3:
                    viewAvailableSeats();
                    break;
                case 4:
                    SHOWMENU();  // العودة إلى القائمة الرئيسية
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }

    public void viewBookedSeats() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter flight number to view booked seats: ");
        String flightNumber = scanner.nextLine();

        // البحث عن الرحلة في الملف أو في قاعدة البيانات
        Flight flight = findFlight(flightNumber);

        if (flight != null) {
            System.out.println("---- Booked Seats for Flight " + flightNumber + " ----");
            System.out.println("Economy: " + flight.getBookedEconomySeats());
            System.out.println("Business: " + flight.getBookedBusinessSeats());
            System.out.println("First Class: " + flight.getBookedFirstClassSeats());
        } else {
            System.out.println("❌ Flight not found.");
        }
    }

    public void viewAvailableSeats() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter flight number to view available seats: ");
        String flightNumber = scanner.nextLine();

        // البحث عن الرحلة في الملف أو في قاعدة البيانات
        Flight flight = findFlight(flightNumber);

        if (flight != null) {
            System.out.println("---- Available Seats for Flight " + flightNumber + " ----");
            System.out.println("Economy: " + flight.getAvailableEconomySeats());
            System.out.println("Business: " + flight.getAvailableBusinessSeats());
            System.out.println("First Class: " + flight.getAvailableFirstClassSeats());
        } else {
            System.out.println("Flight not found.");
        }
    }

    @Override
    public String toString(){
        return AgentID + "," + Depart + "," +Commision+ "," + userId + "," +userName + "," + password + "," +
                name + "," + email + "," + contactinfo + "," + role ;
    }
    }

