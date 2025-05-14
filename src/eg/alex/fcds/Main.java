package eg.alex.fcds;

public class Main {
    public static void main(String[] args) {
        BookingSystem app = null;

        try {
			app = BookingSystem.getInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
}
