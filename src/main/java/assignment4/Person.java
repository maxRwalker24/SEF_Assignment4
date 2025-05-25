package assignment4;

import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import java.io.PrintWriter;
// import java.io.FileOutputStream;
// import java.io.IOException;
import java.text.SimpleDateFormat;

public class Person {

    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private HashMap<LocalDate, Integer> demeritPoints; // A variable that holds the demerit points with the offense day
    private boolean isSuspended;

    // public Person(String personID, String firstName, String lastName, String address, String birthdate) {
    //     this.personID = personID;
    //     this.firstName = firstName;
    //     this.lastName = lastName;
    //     this.address = address;
    //     this.birthdate = birthdate;
    //     this.demeritPoints = new HashMap<>(); 
    //     this.isSuspended = false; // default value
    // }

    // Max to implement
    public boolean addPerson (String personID, String firstName, String lastName, String address, String birthdate) {

        InputValidator iv = new InputValidator();

        if (iv.isValidDate(birthdate) && iv.isValidID(personID) && iv.isValidAddress(address)) {

            Person person = new Person();
            person.personID = personID;
            person.firstName = firstName;
            person.lastName = lastName;
            person.address = address;
            person.birthdate = birthdate;

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

    
    public boolean updatePersonalDetails(String id, String firstName, String lastName, String address, String birthdate) {
    //TODO: This method allows updating a given person's ID, firstName, lastName, address and birthday in a TXT file.
    //Changing personal details will not affect their demerit points or the suspension status.
    // All relevant conditions discussed for the addPerson function also need to be considered and checked in the updaterson function.
    //Condition 1: If a person is under 18, their address cannot be changed.
    //Condition 2: If a person's birthday is going to be changed, then no other personal detail (i.e, person's ID, firstName, lastName, address) can be changed.
    //Condition 3: If the first character/digit of a person's ID is an even number, then their ID cannot be changed.
    //Instruction: If the Person's updated information meets the above conditions and any other conditions you may want to consider,
    //the Person's information should be updated in the TXT file with the updated information, and the updatePersonalDetails function should return true.
    //Otherwise, the Person's updated information should not be updated in the TXT file, and the updatePersonalDetails function should return false.





    return true;
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

        // Grab the existing hashmap to allow for updating
        HashMap<LocalDate, Integer> existingPoints = getDemeritPoints();
        // If the person doesnt have any points create a new hashmap
        if(existingPoints == null) {
            existingPoints = new HashMap<>();
        }

        // Insert new demerit into the hashMap
        existingPoints.put(offenseDate, points);
        setDemeritPoints(existingPoints);

        // Now the check for license suspension is started
        int userAge;

        // Compare the users birthdate to the current date
        LocalDate currentDate = LocalDate.now();
        LocalDate userBirthdate = LocalDate.parse(getBirthdate(), dateFormat);
        userAge = Period.between(userBirthdate, currentDate).getYears();

        // Figure out how many existing demerit points the driver has in the last two years
        LocalDate twoYearsAgo = currentDate.minusYears(2);
        int driversRecentDemeritPoints = 0;

        // Loop through all hash map entries but only add entries from the last two years
        for (Map.Entry<LocalDate, Integer> entry : existingPoints.entrySet()) {
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
                setSuspended(true);
            }
        }
        // Moving on to all remaining drivers
        else {
            if (driversRecentDemeritPoints > 12) {
                setSuspended(true);
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

    // Setters
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setDemeritPoints(HashMap<LocalDate, Integer> demeritPoints) {
        this.demeritPoints = demeritPoints;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }



}