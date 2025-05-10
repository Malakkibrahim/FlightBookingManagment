import java.time.LocalDate;
import java .util.UUID;
import java.io.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class Passenger {
    private String passengerId;
    private String passengerName;
    private String passportNumber;
    private LocalDate dateOfbirth;
    private String specialRequests;
    private static final String PASSENGERS_FILE = "passengers.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //بنعمله عشان نعمل  passenger جديد// ✅ حفظ الراكب في الملف
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PASSENGERS_FILE, true))) {
            writer.write(toFileString());
            writer.newLine();
            System.out.println("✅ Passenger saved successfully.");
        } catch (IOException e) {
            System.out.println("⚠ Error saving passenger.");
        }
    }
    private static Passenger fromFileString(String line) {
        try {
            String[] parts = line.split(",", -1);
            if (parts.length == 5) {
                String id = parts[0];
                String name = parts[1];
                String passport = parts[2];
                LocalDate dob = LocalDate.parse(parts[3], formatter);
                String requests = parts[4];
                return new Passenger(id, name, passport, dob, requests);
            }
        } catch (Exception e) {
            System.out.println("⚠ Error parsing line: " + line);
        }
        return null;
    }



    public Passenger(String passengerId, String passengerName, String passportNumber, LocalDate dateOfbirth, String specialRequests) {
        this.passengerId = passengerId;
        this.passengerName = passengerName;
        this.passportNumber = passportNumber;
        this.dateOfbirth = dateOfbirth;
        this.specialRequests = specialRequests;
    }

    public Passenger(String passengerName, String passportNumber, LocalDate dateOfbirth, String specialRequests) {
        this.passengerId = "P" + System.currentTimeMillis();
        this.passengerName = passengerName;
        this.passportNumber = passportNumber;
        this.dateOfbirth = dateOfbirth;
        this.specialRequests = specialRequests;
    }

    public Passenger(String passengerName) {
        this.passengerName = passengerName;
    }


    public String getPassengerId() {
        return passengerId;
    }

    public String getpassengerName() {
        return passengerName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public LocalDate getDateOfbirth() {
        return dateOfbirth;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public void setName(String passengerName) {
        this.passengerName = passengerName;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void setDateOfbirth(LocalDate dateOfbirth) {
        this.dateOfbirth = dateOfbirth;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public void updateInfo(String newName, String newPassport, LocalDate newDob, String newRequests) {
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(PASSENGERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Passenger p = fromFileString(line);
                if (p != null && p.getPassengerId().equals(this.passengerId)) {
                    // تعديل البيانات
                    p.setName(newName);
                    p.setPassportNumber(newPassport);
                    p.setDateOfbirth(newDob);
                    p.setSpecialRequests(newRequests);
                    updatedLines.add(p.toFileString());
                } else {
                    updatedLines.add(line);   }
            }
        } catch (IOException e) {
            System.out.println("⚠ Error reading passengers file.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PASSENGERS_FILE))) {
            for (String updated : updatedLines) {
                writer.write(updated);
                writer.newLine();
            }
            System.out.println("✅ Passenger info updated.");
        } catch (IOException e) {
            System.out.println("⚠ Error updating passengers file.");
        }
    }
    public String toFileString() {
        return passengerId + "," + passengerName + "," + passportNumber + "," + dateOfbirth.format(formatter) + "," + specialRequests;
    }

}