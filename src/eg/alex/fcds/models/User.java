package eg.alex.fcds.models;

import java.util.Scanner;
import java.util.UUID;

import eg.alex.fcds.models.shared.*;

public abstract class User {
    protected UUID userId;
    protected String username;
    protected String password;
    protected String name;
    protected String email;
    protected String contactinfo;
    protected Role role;
    protected UserStatus status;

    public User(UUID userId, String username, String password, String name, String email, String contactinfo, Role role, UserStatus status) {
        this(username, password, name, email, contactinfo, role);
        this.userId = userId;
        this.status = status;
    }

    public User(String username, String password, String name, String email, String contactinfo, Role role) {
        this.userId = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contactinfo = contactinfo;
        this.role = role;
        this.status = UserStatus.ACTIVE;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactinfo() {
        return contactinfo;
    }

    public Role getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public abstract void showMenu();

    // public boolean LOGIN(String userName, String password) {
    //     if (this.password.equals(password) && this.username.equals(userName)) {
    //         System.out.println("You login Successfully ✅");

    //         showMenu();
    //         return true;
    //     } else {
    //         System.out.println("❌ Incorrect username or password");
    //         return false;
    //     }
    // }

    public void logout() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Are you sure that you want to logout? (yes/no): ");
        String answer = scanner.nextLine().trim().toLowerCase(); 

        if (answer.equals("yes") || answer.equals("y")) {
            System.out.println("👋 Logged out successfully.");
            System.exit(0); 
        } else if (answer.equals("no") || answer.equals("n")) {
            System.out.println("🔁 Logout cancelled. Returning to menu...");
            showMenu(); 
        } else {
            System.out.println("❌ Invalid input. Please type 'yes' or 'no'.");
            logout();
        }
        
    }

    public void updateprofile(String username, String password, String name, String email, String contactinfo) {
        this.username = username;
        this.name = name;
        this.contactinfo = contactinfo;
        this.setEmail(email);
        this.setPassword(password);
    }

    public void activate() {
        this.status = UserStatus.ACTIVE;
    }

    public void deActivate() {
        this.status = UserStatus.INACTIVE;
    }

    public boolean validatePassword(String password) 
    {
        return this.password.contentEquals(Utils.hashPassword(password));
    }

    private void setPassword(String password) {
        if(!Utils.isValidPassword(password))
            throw new IllegalArgumentException(password);
        this.password = Utils.hashPassword(password);
    }

    private void setEmail(String email) {
        if(!Utils.isValidEmail(email))
            throw new IllegalArgumentException(email);
        this.email = email;
    }

    @Override
    public String toString()
    {
        return userId + "," + username + "," + password + "," +
        name + "," + email + "," + contactinfo  + "," + status;
    }
}


