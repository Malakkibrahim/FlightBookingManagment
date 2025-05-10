import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Administrator extends User {

    private UUID adminId;
    private int securityLevel;
    private Set<User> users = new HashSet<>();

    public UUID getAdminId() {
        return adminId;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Administrator(UUID adminId, int securityLevel, String id, String username, String password, String name, String email, String contact, Role role) {
        super(id, username, password, name, email, contact, role);
        this.adminId = adminId;
        this.securityLevel = securityLevel;
    }






    @Override
    public void SHOWMENU(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("---- Administrator Menu ----");
        System.out.println("1. Create New User");
        System.out.println("2. Modify System Settings");
        System.out.println("3. View System Logs");
        System.out.println("4. Manage User Access");
        System.out.println("5. Update Profile");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        switch(choice){
            case 1:
                System.out.println("Creating A New User now....");
                createUser(); // method to be defined in the class
                break;
            case 2:
                System.out.println("Modifying System....");
                modifySystemSettings();
                break;
            case 3:
                System.out.println("Viewing all settings....");
                viewSystemLogs();
                break;
            case 4:
                System.out.println("managing users");
                manageUserAccess();
                break;

            case 5:
                updateprofile();
                break;
            case 6:
                logout();
                break;

        }
    }

    public void createUser(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("👤 Create New User");
        System.out.print("Enter User ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        // 🔐 Password validation loop
        String password;
        while (true) {
            System.out.print("Enter Password (min 6 chars, must include letters and numbers): ");
            password = scanner.nextLine();

            if (IsValidPass(password)) {
                break;
            } else {
                System.out.println("❌ Invalid password. It must be at least 6 characters and contain both letters and numbers.");
            }
        }

        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Contact Info: ");
        String contact = scanner.nextLine();

        System.out.print("Enter Role (1 = CUSTOMER, 2 = AGENT, 3 = ADMINSTRITOR): ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Role role;
        switch (roleChoice) {
            case 1:
                role = Role.CUSTOMER;
                //users.add(new Customer(id, username, password, name, email, contact, role));
                break;
            case 2:
                role = Role.AGENT;
                System.out.println("Enter your depart: ");
                String depart = scanner.nextLine();
                System.out.println("Enter your commision");
                double commision = scanner.nextDouble();
                System.out.println("Enter your AgentID");
                String agentid = scanner.nextLine();
                users.add(new Agent(depart,commision,id, username, password, name, email, contact, role,agentid));
                break;
            case 3:
                role = Role.ADMINSTRITOR;
                System.out.println("Enter the securityLevel: ");
                int securityLevel = scanner.nextInt();
                users.add(new Administrator(UUID.randomUUID(),securityLevel,id, username, password, name, email, contact, role)); //String adminId,int securityLevel,String id, String username, String password, String name, String email, String contact, Role role
                break;
            default:
                System.out.println("❌ Invalid role choice.");
                return;
        }

        System.out.println("✅ User created successfully!");
    }
    public void modifySystemSettings() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("🔧 Enter setting key to modify: ");
        String key = scanner.nextLine();

        System.out.print("✏️ Enter new value: ");
        String newValue = scanner.nextLine();

        File file = new File("settings.txt");
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(key + "=")) {
                    lines.add(key + "=" + newValue);
                    found = true;
                } else {
                    lines.add(line);
                }
            }

            if (!found) {
                lines.add(key + "=" + newValue); // Add new setting if not found
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String updatedLine : lines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            }

            System.out.println("✅ Setting updated successfully.");

        } catch (IOException e) {
            System.out.println("❌ Error updating settings: " + e.getMessage());
        }
    }
    public void viewSystemLogs() {
        File logFile = new File("logs.txt");

        System.out.println("📄 System Logs:");
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("🔹 " + line);
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading logs: " + e.getMessage());
        }
    }
    public void manageUserAccess() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("🔎 Enter username to manage: ");
        String usernameToEdit = scanner.nextLine();

        boolean found = false;
        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(usernameToEdit)) {
                found = true;
                System.out.println("👤 User found: " + user.getName());
                System.out.print("🛑 Do you want to disable this user? (yes/no): ");
                String choice = scanner.nextLine();

                if (choice.equalsIgnoreCase("yes")) {
                    System.out.println("❌ User disabled.");
                } else {
                    System.out.println("✅ User enabled.");
                }
                break;
            }
        }

        if (!found) {
            System.out.println("❌ User not found.");
        }
    }


@Override
    public String toString(){
        return adminId + "," + securityLevel + "," + userId + "," +userName + "," + password + "," +
                name + "," + email + "," + contactinfo + "," + role ;
}
}
