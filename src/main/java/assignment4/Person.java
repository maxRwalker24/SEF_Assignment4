package assignment4;

import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
// import java.util.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import java.io.PrintWriter;

// import java.io.IOException;
// import java.text.SimpleDateFormat;

public class Person {

    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private HashMap<LocalDate, Integer> demeritPoints = new HashMap<LocalDate, Integer>(); // A variable that holds the demerit points with the offense day
    private boolean isSuspended = false;
   
 
    public boolean addPerson (String personID, String firstName, String lastName, String address, String birthdate) {

        InputValidator iv = new InputValidator();

        if (iv.isValidDate(birthdate) && iv.isValidID(personID) && iv.isValidAddress(address)) {
            this.personID = personID;
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.birthdate = birthdate;
            this.demeritPoints = new HashMap<>(); 
            this.isSuspended = false; // default value  


            try {
                FileOutputStream fileStream = new FileOutputStream("Person.txt", true);
                PrintWriter outFS = new PrintWriter(fileStream);
                outFS.println(personID + " " + firstName + " " + lastName + " " + address + " " + birthdate);
                outFS.close();
            } catch (FileNotFoundException e) {
                System.out.println("File could not be created or opened: " + e.getMessage());
            }

            return true;
        }
        
        return false;
    
    }

    
    public boolean updatePersonalDetails(String id, String firstName, String lastName, String address, String birthdate, String fileName) {
        InputValidator iv = new InputValidator(); //Create an input validator to check conditions from addPerson function
        boolean somethingChanged = false; //Boolean to keep track if something was changed

        if(!birthdate.equals("NULL")){ 
            if (!id.equals("NULL") || !firstName.equals("NULL") || !lastName.equals("NULL") || !address.equals("NULL")){
                return false; // Makes sure that birthday as well as another field cant be changed at the same time
            }
        }
        else if(Character.isDigit(this.personID.charAt(0)) && Character.getNumericValue(this.personID.charAt(0)) % 2 == 0 && !id.equals("NULL")){
            return false; // Makes sure person with an id that starts with an even number cant change their id
        }
        else if(iv.isUnder18(this.birthdate) && !address.equals("NULL")){
            return false; //makes sure a person thats under 18 cant change their addess
        }
        else{ //Changes persons details if they're valid
            if (!id.equals("NULL") && iv.isValidID(id)){
                this.personID  = id;
                somethingChanged = true; //something has changed
            }

            if (!firstName.equals("NULL")){
                this.firstName  = firstName;
                somethingChanged = true; //something has changed
            }

            if (!lastName.equals("NULL")){
                this.lastName  = lastName;
                somethingChanged = true; //something has changed
            }

            if (!address.equals("NULL") && iv.isValidAddress(address)){
                this.address  = address;
                somethingChanged = true; //something has changed
            }

            if (!birthdate.equals("NULL") && iv.isValidDate(birthdate)){
                this.birthdate  = birthdate;
                somethingChanged = true; //something has changed
            }

        if(somethingChanged == true){ //if something has changed
            try {
                //Write the update to the file
                FileOutputStream fileStream = new FileOutputStream(fileName, true);
                PrintWriter outFS = new PrintWriter(fileStream);
                outFS.println("UPDATED: " + this.personID + " " + this.firstName + " " + this.lastName + " " + this.address + " " + this.birthdate);
                outFS.close();
            } catch (Exception e) {
                System.out.println("File could not be created or opened: " + e.getMessage());
            }

            return true; //return true if something was successfully changed
        }
        }

    return false;
    }
    
    
    public String addDemeritPoints (String date, int points) {
    // Create a new object of inputValidator
    InputValidator iv = new InputValidator();
    
    if (iv.isValidDate(date) && iv.isValidPoints(points)) {
        // Firstly convert the string date to a date function to allow storage in hashMap
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // Create a variable to store the new date
        LocalDate offenseDate;
        // Try conversion of date
        try {
            offenseDate = LocalDate.parse(date, dateFormat);
        }
        catch (Exception e) {
            return "Failed";
        }

       
        
        this.demeritPoints.put(offenseDate, points);
        


        // Now the check for license suspension is started
        int userAge;

        // Compare the users birthdate to the current date
        LocalDate currentDate = LocalDate.now();
        LocalDate userBirthdate = LocalDate.parse(this.birthdate, dateFormat);
        userAge = Period.between(userBirthdate, currentDate).getYears();

        // Figure out how many existing demerit points the driver has in the last two years
        LocalDate twoYearsAgo = currentDate.minusYears(2);
        int driversRecentDemeritPoints = 0;

        // Loop through all hash map entries but only add entries from the last two years
        for (Map.Entry<LocalDate, Integer> entry : this.demeritPoints.entrySet()) {
            LocalDate dateInccured = entry.getKey();
            int pointsIncurred = entry.getValue();

            if (!dateInccured.isBefore(twoYearsAgo)) {
                driversRecentDemeritPoints += pointsIncurred;
            }
        }

        // Check the demerits of the driver and set to isSuspended if needed
        // Start with under 21's
        if (userAge < 21) {
            if (driversRecentDemeritPoints > 6) {
                this.isSuspended = true;
            }
        }
        // Moving on to all remaining drivers
        else {
            if (driversRecentDemeritPoints > 12) {
                this.isSuspended = true;
            }
        }

        // Print to a txt file
        try {
                FileOutputStream fileStream = new FileOutputStream("Demerit.txt", true);
                PrintWriter outFS = new PrintWriter(fileStream);
                outFS.println(personID + " " + offenseDate.format(dateFormat) + " " + points);
                outFS.close();
            } catch (FileNotFoundException e) {
                System.out.println("File could not be created or opened: " + e.getMessage());
            }
        return "Success";
        }
        else {
            return "Failed";
        }
    }



    // Getters
    public String getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public HashMap<LocalDate, Integer> getDemeritPoints() {
        return demeritPoints;
    }

    public boolean isSuspended() {
        return isSuspended;
    }


}