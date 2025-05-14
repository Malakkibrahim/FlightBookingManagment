package eg.alex.fcds.models;

import java.util.*;

import javax.management.relation.InvalidRoleValueException;

import eg.alex.fcds.BookingSystem;
import eg.alex.fcds.models.shared.Role;
import eg.alex.fcds.models.shared.SecurityLevel;
import eg.alex.fcds.models.shared.UserStatus;
import eg.alex.fcds.view.Console;

public class Administrator extends User {
    private UUID adminId;
    private SecurityLevel securityLevel;

    public Administrator(UUID adminId, SecurityLevel securityLevel, UUID userId, String username, String password,
            String name, String email, String contactInfo, UserStatus status) {
        super(userId, username, password, name, email, contactInfo, Role.ADMINSTRITOR, status);
        this.adminId = adminId;
    }

    public Administrator(SecurityLevel securityLevel, String username, String password, String name, String email,
            String contactInfo) {
        super(username, password, name, email, contactInfo, Role.ADMINSTRITOR);
        this.adminId = UUID.randomUUID();
        this.securityLevel = securityLevel;
    }

    @Override
    public void showMenu() {
        List<String> mainMenu = new ArrayList<>() {
            {
                add("Create New User");
                add("Modify System Settings");
                add("View System Logs");
                add("Manage User Access");
            }
        };
        int choice = Console.displayMenu("Adminstrator Menu", mainMenu);

        /*
         * USER Type
         * [1] admin
         * [2] agent
         * [3] customer
         * Enter user role
         * Role:
         */
        switch (choice) {
            case 1:
                int roleChoice = Console.displayMenu("User Role", List.of("admin", "agent", "customer"));
                Role newUserRole = null;
                if(roleChoice == 1)
                    newUserRole = Role.ADMINSTRITOR;
                else if(roleChoice == 2)
                    newUserRole = Role.AGENT;
                else
                    newUserRole = Role.CUSTOMER;
                
                this.createUser(newUserRole);
                break;
            case 2:
                this.modifySystemSettings();
                break;
            case 3:
                this.viewSystemLogs();
                break;
            case 4:
                String username = Console.displayForm("Enter user name", List.of("username")).get(0);
                int activationChoice = Console.displayMenu("Activation options", List.of("activate", "deactivate"));
                try {
                    this.manageUserAccess(username, activationChoice == 1);
                } catch(Exception e) {
                    Console.printInline(e.getMessage());
                }
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    /*
     * Enter User Info
     * username: kamal
     * password: kamal9
     * name: mahmoud
     * email: mk@gmail.com
     * contact info: fdfspoj
     * 
     * responses = [kamal, kamal9, mahmoud, ....]
     */
    private void createUser(Role userRole) {
        List<String> responses = Console.displayForm("Enter User Info",
                List.of("username", "password", "name", "email", "contact info"));
        if (userRole == Role.ADMINSTRITOR)
            CreateAdminstrator(responses);
        else if (userRole == Role.AGENT)
            CreateAgent(responses);
        else
            CreateCustomer(responses);
    }

    private void CreateAdminstrator(List<String> userInfo) {
        int choice = Console.displayMenu("Adminstrator Security Levels", List.of("high", "meduim", "low"));;
        SecurityLevel level = choice == 1 ? SecurityLevel.HIGH : choice == 2 ? SecurityLevel.MEDUIM : SecurityLevel.LOW;

        String username = userInfo.get(0);
        String password = userInfo.get(1);
        String name = userInfo.get(2);
        String email = userInfo.get(3);
        String contactInfo = userInfo.get(4);

        Administrator administrator = new Administrator(level, username, password, name, email, contactInfo);
        this.app.saveAdminstrator(administrator);
    }

    private void CreateAgent(List<String> userInfo) {
        List<String> responses = Console.displayForm("Enter Agent Info", List.of("department", "commission"));
        String username = userInfo.get(0);
        String password = userInfo.get(1);
        String name = userInfo.get(2);
        String email = userInfo.get(3);
        String contactInfo = userInfo.get(4);
        String department = responses.get(0);
        double commision = Double.parseDouble(responses.get(1));

        Agent agent = new Agent(username, password, name, email, contactInfo, department, commision);
        this.app.saveAgent(agent);
    }

    private void CreateCustomer(List<String> userInfo) {
        List<String> responses = Console.displayForm("Enter Customer Info", List.of("address", "prefernces"));
        String username = userInfo.get(0);
        String password = userInfo.get(1);
        String name = userInfo.get(2);
        String email = userInfo.get(3);
        String contactInfo = userInfo.get(4);
        String address = responses.get(0);
        String prefernces = responses.get(1);

        Customer customer = new Customer(username, password, name, email, contactInfo, address, prefernces);
        this.app.saveCustomer(customer);
    }

    public void viewSystemLogs() {
        FileManager.readLogs();
    }

    public void modifySystemSettings() {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> settings = new HashMap<>();
        System.out.println("Enter settings in format key=value");

        String input = scanner.nextLine();
        while(!input.equals("")) {
            String[] settingItem = input.split("=");
            if(settingItem.length != 2) {
                Console.printInline("The previous setting is incorrect");
            } else {
                settings.put(settingItem[0], settingItem[1]);
            }
            input = scanner.nextLine();
        } 
        
        FileManager.updateSystemSettings(settings);
    }

    public void manageUserAccess(String username, boolean activate) throws InvalidRoleValueException {
        User user = this.app.searchUser(username);
        if (user == null || user.getRole() == Role.ADMINSTRITOR)
            throw new InvalidRoleValueException("You tried to change a user with administrator role");

        if (activate) {
            user.activate();
        } else {
            user.deActivate();
        }

        this.app.updateUsers(user.getRole());
    }

    @Override
    public String toString() {
        return adminId + "," + securityLevel + "," + super.toString();
    }
}