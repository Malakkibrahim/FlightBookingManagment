
import java.util.Scanner;
enum Role{

    CUSTOMER,
    AGENT,
    ADMINSTRITOR
}
public abstract class  User {

        protected String userId;
        protected String userName;
        protected String password;
        protected String name;
        protected String email;
        protected String contactinfo;
        protected Role role;

        public User(String userId, String userName, String password, String name, String email, String contactinfo, String role) {
        }

        User(){

        }

        public User(String userId, String userName, String password, String name, String email, String contactinfo, Role role) {
            this.userId = userId;
            this.userName = userName;
            this.password = password;
            this.name = name;
            this.email = email;
            this.contactinfo = contactinfo;
            this.role = role;
        }

        public String getContactinfo() {
            return contactinfo;
        }


        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public Role getRole() {
            return role;
        }

        public String getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public boolean LOGIN(String password, String userName) {
            if (this.password.equals(password) && this.userName.equals(userName)) {
                System.out.println("You login Successfully ✅");


                SHOWMENU();
                return true;
            } else {
                System.out.println("❌ Incorrect username or password");
                return false;
            }
        }
        public abstract void SHOWMENU();


        public void displayProfile() {
            System.out.println("Username: " + userName);
            System.out.println("Full Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Role: " + role);
            System.out.println("UserID: " + userId);
            System.out.println("Contactinfo: " + contactinfo);

        }
        public boolean ChangePass(String oldone,String newone){
            if(this.password.equals(oldone)){
                if(IsValidPass(newone)){
                    this.password = newone;
                    return true;
                }
            }
            return false;
        }
        public boolean IsValidPass(String password) {
            // طول الباسورد على الأقل 6
            if (password.length() < 6) {
                System.out.println("Invalid pass characters");
                return false;
            }

            // يحتوي على حرف على الأقل
            boolean hasLetter = password.matches(".*[A-Za-z].*");

            // يحتوي على رقم على الأقل
            boolean hasDigit = password.matches(".*[0-9].*");

            return hasLetter && hasDigit;
        }



        public void logout() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Are you sure that you want to logout? (yes/no): ");
            String answer = scanner.nextLine().trim().toLowerCase(); // نحول الإجابة لحروف صغيرة ونحذف المسافات

            if (answer.equals("yes") || answer.equals("y")) {
                System.out.println("👋 Logged out successfully.");
                // هنا تقدر تضيف كود الخروج من البرنامج أو الرجوع للقائمة الرئيسية
                System.exit(0); // لو عايز تقفل البرنامج تمامًا
            } else if (answer.equals("no") || answer.equals("n")) {
                System.out.println("🔁 Logout cancelled. Returning to menu...");
                SHOWMENU(); // يرجعه للمينيو تاني
            } else {
                System.out.println("❌ Invalid input. Please type 'yes' or 'no'.");
                logout(); // تعيد السؤال لو الإجابة مش مفهومة
            }
        }

        public void updateprofile(){
            Scanner scanner = new Scanner(System.in);

            System.out.println("🔧 Update Profile");
            System.out.println("-----Enter a new name----");
            String newname = scanner.nextLine();
            this.name= newname;
            System.out.println("----Enter your new pass----");
            String newpass = scanner.nextLine();
            if (IsValidPass(newpass)) {
                this.password = newpass;
                System.out.println("✅ Password updated successfully.");
            } else {
                System.out.println("❌ Password must be at least 6 characters long and contain both letters and numbers.");
            }
            System.out.println("----Enter your new contactinfo----");
            String newcontactinfo = scanner.nextLine();
            this.contactinfo = newcontactinfo;
            System.out.println("Your Profile updated successfully");


            System.out.println("Do you want to see your updates");
            String yes = scanner.nextLine();
            if (yes.equals("yes") || yes.equals("y")){
                displayProfile();
            }
            else{
                System.out.println("Thank you sir!");
            }



        }



}


