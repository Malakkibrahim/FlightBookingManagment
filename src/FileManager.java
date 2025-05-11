import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileManager {
    private static final String ADMIN_FILE = "administrators.txt";
    private static final String AGENT_FILE = "agents.txt";
    private static final String Flight_FILE = "flight.txt";
    private static final String PAYMENTS_FILE = "payments.txt";
    public void saveAdminToFile(Administrator administrator) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE, true))) {
            writer.write(administrator.toString());
            writer.newLine();
            System.out.println("✅ Administrator saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving administrator: " + e.getMessage());
        }
    }


    public static List<Administrator> loadAdminsFromFile() {
        List<Administrator> admins = new ArrayList<>();
        File file = new File(ADMIN_FILE);

        if (!file.exists()) {
            return admins; // لو الملف مش موجود بيرجع ليست فاضية
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 9) {
                    UUID adminId = UUID.fromString(data[0]);
                    int securityLevel = Integer.parseInt(data[1]);
                    String id = data[2];
                    String username = data[3];
                    String password = data[4];
                    String name = data[5];
                    String email = data[6];
                    String contact = data[7];
                    Role role = Role.valueOf(data[8]);

                    admins.add(new Administrator(adminId, securityLevel, id, username, password, name, email, contact, role));
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading administrator file: " + e.getMessage());
        }

        return admins;
    }


    public void saveAgentToFile(Agent agent) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AGENT_FILE, true))) {
            writer.write(agent.toString());
            writer.newLine();
            System.out.println("✅ Agent saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving Agent: " + e.getMessage());
        }
    }


    public static List<Agent> loadAgentFromFile() {
        List<Agent> agents = new ArrayList<>();
        File file = new File(AGENT_FILE);

        if (!file.exists()) {
            return agents; // لو الملف مش موجود بيرجع ليست فاضية
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 10) {
                    UUID agentId = UUID.fromString(data[0]);
                    String depart = data[1];
                    double commision = Double.parseDouble(data[2]);
                    String userid = data[3];
                    String username = data[4];
                    String password = data[5];
                    String name = data[6];
                    String email = data[7];
                    String contactinfo = data[8];
                    Role role = Role.valueOf(data[9]);

                    agents.add(new Agent( depart, commision, userid,  username,  password,  name,  email,  contactinfo,  role,  agentId));
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading agent file: " + e.getMessage());
        }

        return agents;
    }


    public void saveFlightToFile(Flight flight){

    }

    public void removeFlightFromFile( String flightNumber){

    }

    public static List<Flight> loadFlightFromFile() {
        List<Flight> flights = new ArrayList<>();
        File file = new File(Flight_FILE);

        if (!file.exists()) {
            return flights; // لو الملف مش موجود بيرجع ليست فاضية
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 10) {
                    UUID agentId = UUID.fromString(data[0]);
                    String depart = data[1];
                    double commision = Double.parseDouble(data[2]);
                    String userid = data[3];
                    String username = data[4];
                    String password = data[5];
                    String name = data[6];
                    String email = data[7];
                    String contactinfo = data[8];
                    Role role = Role.valueOf(data[9]);

                    flights.add(new Flight( depart, commision, userid,  username,  password,  name,  email,  contactinfo,  role,  agentId));
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading agent file: " + e.getMessage());
        }

        return agents;
    }

    public static void savePayments(List<Payment> payments , String PAYMENTS_FILE) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("payments.txt", true))) {
            // For each payment in the list, write its details to the file
            for (Payment payment : payments) {
                writer.write(payment.toFileString());
                writer.newLine(); // Add a newline between each payment record
            }
            System.out.println("Payments have been saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving payment data.");
            e.printStackTrace();
        }
    }


    public static void updateFlightFile(Flight flight, String flightFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader("flights.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("flights.txt"))) {

            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                flight = Flight.fromFileString(line);

                // تحقق إذا كانت هذه الرحلة هي التي تم تحديثها
                if (flight.getFlightnumber().equals(flight.getFlightnumber())) {
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


    public static void saveBookings(List<Booking> bookings, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Booking booking : bookings) {
                writer.write(booking.toFileString()); // لازم تكون عملت toFileString() في كلاس Booking
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving bookings to file.");
            e.printStackTrace();
        }
    }


    public static void saveCustomerToFile(Customer customer, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(customer.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving customer data to file.");
        }
    }

    // Method to load all customers from a file
    public static List<Customer> loadCustomersFromFile(String filePath) {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Customer customer = Customer.fromFileString(line);  // assuming a method to parse customer from file
                customers.add(customer);
            }
        } catch (IOException e) {
            System.out.println("Error reading customer data from file.");
        }
        return customers;
    }



}




