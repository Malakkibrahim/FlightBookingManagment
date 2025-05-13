package eg.alex.fcds.models;
import java.util.*;

import javax.management.relation.InvalidRoleValueException;

import eg.alex.fcds.BookingSystem;
import eg.alex.fcds.models.shared.Role;
import eg.alex.fcds.models.shared.SecurityLevel;
import eg.alex.fcds.models.shared.UserStatus;

public class Administrator extends User {
    private UUID adminId;
    private SecurityLevel securityLevel;

    public Administrator(UUID adminId, SecurityLevel securityLevel, UUID userId, String username, String password, 
    String name, String email, String contactInfo, UserStatus status) {
        super(userId, username, password, name, email, contactInfo, Role.ADMINSTRITOR, status);
        this.adminId = adminId;
    }

    public Administrator(SecurityLevel securityLevel, String username, String password, String name, String email, String contactInfo)
    {
        super(username, password, name, email, contactInfo, Role.ADMINSTRITOR);
        this.adminId = UUID.randomUUID();
        this.securityLevel = securityLevel;
    }
Scanner scanner = new Scanner(System.in);
    @Override
    public void showMenu(){
        System.out.println("---- Administrator Menu ----");
        System.out.println("1. Create New User");
        System.out.println("2. Modify System Settings");
        System.out.println("3. View System Logs");
        System.out.println("4. Manage User Access");
        System.out.print("Enter your choice: ");
        int rkm  = scanner.nextInt();

        switch (rkm) {
           case 1:
        scanner.nextLine(); 
        System.out.println("Enter the type of user you want to create (admin/agent/customer): ");
        String type = scanner.nextLine().trim().toLowerCase();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine();

        User newUser = null;

        switch (type) {
            case "admin":
                System.out.print("Enter security level (HIGH, MEDUIM, LOW): ");
        String levelStr = scanner.nextLine().trim().toUpperCase();

        SecurityLevel level;
        try {
            level = SecurityLevel.valueOf(levelStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid security level entered. Defaulting to LOW.");
            level = SecurityLevel.LOW;
        }

        newUser = new Administrator(level, username, password, name, email, contactInfo);
        BookingSystem.getInstance().getAdmins().add((Administrator) newUser);
        System.out.println("Administrator created successfully!");
        break;

            case "agent":
                System.out.print("Enter department: ");
                String department = scanner.nextLine();
                System.out.print("Enter commission: ");
                double commission = scanner.nextDouble();
                scanner.nextLine(); 
                newUser = CreateAgent(department, commission, username, password, name, email, contactInfo);
                BookingSystem.getInstance().getAgents().add((Agent) newUser);
                System.out.println("Agent created successfully!");
                break;

            case "customer":
                System.out.print("Enter address: ");
                String address = scanner.nextLine();
                System.out.print("Enter preference: ");
                String preference = scanner.nextLine();
                newUser = CreateCustomer(address, preference, username, password, name, email, contactInfo);
                BookingSystem.getInstance().getCustomers().add((Customer) newUser);
                System.out.println("Customer created successfully!");
                break;

            default:
                System.out.println("Invalid user type!");
                break;
}
        break;
        case 2 :
        System.out.println("Enter number of system settings to modify: ");
                int count = scanner.nextInt();
                scanner.nextLine(); 
                Map<String, String> updates = new HashMap<>();

                for (int i = 0; i < count; i++) {
                    System.out.print("Enter setting key: ");
                    String key = scanner.nextLine();
                    System.out.print("Enter new value: ");
                    String value = scanner.nextLine();
                    updates.put(key, value);
                }

                modifySystemSettings(updates);
                System.out.println("System settings updated successfully.");
                break;

            case 3:
                System.out.println("System Logs:");
                viewSystemLogs();
                break;

            case 4:
                System.out.print("Enter username of user to modify: ");
                String targetUsername = scanner.nextLine();
                User targetUser = null;

                // Search in agents
                for (Agent agent : BookingSystem.getInstance().getAgents()) {
                    if (agent.getUsername().equals(targetUsername)) {
                        targetUser = agent;
                        break;
                    }
                }

                // If not found, search in customers
                if (targetUser == null) {
                    for (Customer customer : BookingSystem.getInstance().getCustomers()) {
                        if (customer.getUsername().equals(targetUsername)) {
                            targetUser = customer;
                            break;
                        }
                    }
                }

                if (targetUser == null) {
                    System.out.println("User not found.");
                    break;
                }

                System.out.print("Do you want to (1) Activate or (2) Deactivate this user? ");
                int action = scanner.nextInt();
                scanner.nextLine(); // Clear newline

                try {
                    manageUserAccess(targetUser, action == 1);
                    System.out.println("User access updated successfully.");
                } catch (InvalidRoleValueException e) {
                    System.out.println(e.getMessage());
                }
                break;

            default:
                System.out.println("Invalid choice. Please try again.");
        }

        System.out.println();
    }

    



    // private User CreateAdminstrator( SecurityLevel level ,String username, String password, String name, String email, String contactInfo)
    // {
    //     return new Administrator(SecurityLevel.LOW, username, password, name, email, contactinfo);
    // }

    private User CreateAgent(String department, double commision, String username, String password, String name, String email, String contactInfo)
    {
        return new Agent(username, password, name, email, contactinfo, department, commision);
    }

    private User CreateCustomer(String address, String preferance, String username, String password, String name, String email, String contactInfo)
    {
        return new Customer(username, password, name, email, contactinfo, address, preferance);
    }

    public void viewSystemLogs() 
    {
        FileManager.readLogs();
    }

    public void modifySystemSettings(Map<String, String> updates) {
        FileManager.updateSystemSettings(updates);
    }

    public void manageUserAccess(User user, boolean activate) throws InvalidRoleValueException {
        if (user.getRole() == Role.ADMINSTRITOR)
            throw new InvalidRoleValueException("You tried to change a user with administrator role");
    
        if (activate) {
            user.activate();
        } else {
            user.deActivate();
        }
    }

    @Override
    public String toString() {
        return adminId + "," + securityLevel + "," + super.toString();
    }
    }
