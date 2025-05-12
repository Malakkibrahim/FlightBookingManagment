import java.util.*;

import javax.management.relation.InvalidRoleValueException;

import shared.Role;
import shared.SecurityLevel;
import shared.UserStatus;

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

    @Override
    public void showMenu(){
        System.out.println("---- Administrator Menu ----");
        System.out.println("1. Create New User");
        System.out.println("2. Modify System Settings");
        System.out.println("3. View System Logs");
        System.out.println("4. Manage User Access");
        System.out.println("5. Update Profile");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
    }

    private User CreateAdminstrator(String username, String password, String name, String email, String contactInfo, Role role)
    {
        return new Administrator(SecurityLevel.LOW, username, password, name, email, contactinfo);
    }

    private User CreateAgent(String department, double commision, String username, String password, String name, String email, String contactInfo, Role role)
    {
        return new Agent(username, password, name, email, contactinfo, department, commision);
    }

    private User CreateCustomer(String address, String preferance, String username, String password, String name, String email, String contactInfo, Role role)
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
